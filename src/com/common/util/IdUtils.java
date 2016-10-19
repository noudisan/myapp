package com.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.Map.Entry;

public class IdUtils {

    public static String getSign(HashMap<String, Object> params) {
        List<String> paramNameList = new ArrayList<String>();
        Iterator<Entry<String, Object>> entryKeyIterator = params.entrySet().iterator();
        while (entryKeyIterator.hasNext()) {
            Entry<String, Object> e = entryKeyIterator.next();
            paramNameList.add(e.getKey());
        }
        Collections.sort(paramNameList);
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < paramNameList.size(); i++) {
            String value = params.get(paramNameList.get(i)) + "";
            if (value.length() > 10) {
                value = value.substring(0, 10);
            }
            buffer.append(value);
        }
        String ticket = encode(buffer.toString());
        if (ticket.length() > 45) {
            ticket = ticket.substring(13, 45);
        }
        ticket = Utils.encodeByMD5(ticket);
        if (ticket.length() > 29) {
            ticket = ticket.substring(4, 29);
        }
        return ticket;
    }

    private static String encode(String originString) {
        if (originString != null) {
            try {
                MessageDigest digest = MessageDigest.getInstance("MD5");
                digest.reset();
                digest.update(originString.getBytes());
                String resultString = encode(digest.digest());
                return resultString.toLowerCase();
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }

        return originString;
    }

    private static final String DIGITS[] = {"7T", "bN", "Lx", "Wd", "Qe", "r9", "f4", "DP", "o0", "fZ", "V8", "4x", "Tm", "g3", "mw", "aI"};

    private static String encode(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            sb.append(DIGITS[(bytes[i] & 0xf0) >>> 4]);
            sb.append(DIGITS[bytes[i] & 0x0f]);
        }
        return sb.toString();
    }
}
