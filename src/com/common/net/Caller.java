package com.common.net;

import com.common.util.IdUtils;
import com.common.util.Logger;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.zip.GZIPInputStream;

public class Caller {
    private static final int HTTP_OK = 200;

    private static RequestCache requestCache = new RequestCache();

    public static String doGet(String url, HashMap<String, String> map) throws ClientProtocolException, IOException, IllegalStateException {
        String data = null;

        if (requestCache != null) {
            data = requestCache.get(url);
            if (data != null) {
                return data;
            }
        }

        HttpParams params = new BasicHttpParams();

        HttpConnectionParams.setConnectionTimeout(params, 30 * 1000);
        HttpConnectionParams.setSoTimeout(params, 30 * 1000);
        HttpConnectionParams.setSocketBufferSize(params, 8192);

        HttpClientParams.setRedirecting(params, true);

        HttpClient httpClient = new DefaultHttpClient(params);

        HttpGet httpGet = new HttpGet(url);
        if (map != null && map.size() > 0) {
            for (String key : map.keySet()) {
                httpGet.setHeader(key, map.get(key));
            }
        }

        HttpResponse httpResponse = httpClient.execute(httpGet);
        if (httpResponse != null) {
            int code = httpResponse.getStatusLine().getStatusCode();
            if (code == HTTP_OK) {
                HttpEntity httpEntity = httpResponse.getEntity();
                if (httpEntity != null) {
                    InputStream inputStream = httpEntity.getContent();
                    data = convertStreamToString(inputStream);
                    if (requestCache != null) {
                        requestCache.put(url, data);
                    }
                }
            }
        }

        Logger.error("Http Get: responseText = " + data);
        return data;
    }

    public static String doPost(String url, HashMap<String, Object> params, HashMap<String, byte[]> files) throws ClientProtocolException, IOException {
        Random random = new Random();
        int randomNumber = random.nextInt(9999) + 1;
        params.put("skid" + randomNumber, IdUtils.getSign(params));
        Logger.error("Http Post: requestParams = " + (params == null ? "null" : params.toString()));
        if (files == null || files.size() == 0) {
            // 不带文件的
            HttpPost request = makeHttpPost(params, url);
            request.addHeader("accept-encoding", "gzip");
            HttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParams, 30 * 1000);
            HttpConnectionParams.setSoTimeout(httpParams, 30 * 1000);
            HttpConnectionParams.setSocketBufferSize(httpParams, 8192);
            HttpClientParams.setRedirecting(httpParams, true);
            DefaultHttpClient client = new DefaultHttpClient();
            client.setParams(httpParams);

            // client.setHttpRequestRetryHandler(new RetryHandler(3));

            ResponseHandler<String> reshandler = new StringResponseHandler();
            String responseText = client.execute(request, reshandler);
            Logger.error("Http Post: responseText = " + responseText);
            return responseText;
        } else {
            String BOUNDARY = java.util.UUID.randomUUID().toString();
            String PREFIX = "--", LINEND = "\r\n";
            String MULTIPART_FROM_DATA = "multipart/form-data";
            String CHARSET = "UTF-8";

            URL uri = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
            conn.setReadTimeout(30 * 1000);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("Charsert", "UTF-8");
            conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA + ";boundary=" + BOUNDARY);

            // 组拼文本类型的参数
            StringBuilder builder = new StringBuilder();
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                builder.append(PREFIX);
                builder.append(BOUNDARY);
                builder.append(LINEND);
                builder.append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"" + LINEND);
                builder.append("Content-Type: text/plain; charset=" + CHARSET + LINEND);
                builder.append("Content-Transfer-Encoding: 8bit" + LINEND);
                builder.append(LINEND);
                builder.append(entry.getValue());
                builder.append(LINEND);
            }

            DataOutputStream outStream = new DataOutputStream(conn.getOutputStream());
            outStream.write(builder.toString().getBytes());

            // 发送文件数据
            for (Map.Entry<String, byte[]> file : files.entrySet()) {
                if (file.getValue() != null) {
                    StringBuilder sb1 = new StringBuilder();
                    sb1.append(PREFIX);
                    sb1.append(BOUNDARY);
                    sb1.append(LINEND);
                    sb1.append("Content-Disposition: form-data; name=\"file\"; filename=\"" + file.getKey() + "\"" + LINEND);
                    sb1.append("Content-Type: image/jpeg; charset=" + CHARSET + LINEND);
                    sb1.append(LINEND);
                    outStream.write(sb1.toString().getBytes());
                    outStream.write(file.getValue());

                    // InputStream is = new FileInputStream(file.getValue());
                    // byte[] buffer = new byte[1024];
                    // int len = 0;
                    // while ((len = is.read(buffer)) != -1) {
                    // outStream.write(buffer, 0, len);
                    // }
                    // is.close();
                    outStream.write(LINEND.getBytes());
                }
            }

            // 请求结束标志
            byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
            outStream.write(end_data);
            outStream.flush();

            // 得到响应
            String responseText = convertStreamToString(conn.getInputStream());
            Logger.error("Http Post: responseText = " + responseText);
            outStream.close();
            conn.disconnect();

            return responseText;
        }
    }

    public static InputStream doPostInputStream(String url, HashMap<String, Object> params) throws ClientProtocolException, IOException {
        Logger.debug("Http Post: requestParams = " + (params == null ? "null" : params.toString()));
        HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, 30 * 1000);
        HttpConnectionParams.setSoTimeout(httpParams, 30 * 1000);
        HttpConnectionParams.setSocketBufferSize(httpParams, 8192);
        DefaultHttpClient client = new DefaultHttpClient(httpParams);
        HttpPost post = makeHttpPost(params, url);
        HttpResponse response = client.execute(post);
        int responseCode = response.getStatusLine().getStatusCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            Logger.error("ContentType = " + response.getEntity().getContentType());

            return response.getEntity().getContent();
        } else {
            throw new IOException();
        }
    }

    private static HttpPost makeHttpPost(Map<String, Object> params, String url) {
        HttpPost request = new HttpPost(url);
        Vector<NameValuePair> nameValue = new Vector<NameValuePair>();

        if (params != null) {
            Set<String> set = params.keySet();
            Iterator<String> e = set.iterator();
            while (e.hasNext()) {
                String name = e.next();
                String value = "" + params.get(name);
                nameValue.add(new BasicNameValuePair(name, value));
            }
        }
        request.setEntity(makeEntity(nameValue));
        return request;
    }

    private static HttpEntity makeEntity(Vector<NameValuePair> nameValue) {
        HttpEntity result = null;
        try {
            result = new UrlEncodedFormEntity(nameValue, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Logger.warn(e.getMessage(), e);
        }
        return result;
    }

    public static String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            Logger.warn(e.getMessage(), e);
        }

        return sb.toString();
    }

    static class StringResponseHandler implements ResponseHandler<String> {
        public String handleResponse(final HttpResponse response) throws HttpResponseException, IOException {
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() >= 300) {
                throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
            }

            HttpEntity entity = response.getEntity();

            if (entity != null) {
                Header contentEncoding = entity.getContentEncoding();
                if (contentEncoding != null && contentEncoding.getValue() != null && contentEncoding.getValue().equalsIgnoreCase("gzip")) {
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    GZIPInputStream gzipIn = new GZIPInputStream(entity.getContent());
                    byte[] buffer = new byte[256];
                    int n;
                    while ((n = gzipIn.read(buffer)) >= 0) {
                        out.write(buffer, 0, n);
                    }
                    gzipIn.close();

                    return out.toString("UTF-8");
                }
            }

            return entity == null ? null : EntityUtils.toString(entity, "UTF-8");
        }
    }
}
