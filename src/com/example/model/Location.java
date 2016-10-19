package com.example.model;


import com.common.sqlite.Column;
import com.common.sqlite.Id;
import com.common.sqlite.Table;

import java.io.Serializable;

@Table(name = "XQWY_TB_LOCATION")
public class Location implements Serializable {

    private static final long serialVersionUID = -7375344484367615446L;

    @Id(generator = "AUTOINCREMENT")
    @Column(name = "LOCATION_ID", type = "INTEGER")
    private Integer id;

    @Column(name = "LOC_TYPE", type = "TEXT")
    private String locType;

    @Column(name = "LOC_LATITUDE", type = "TEXT")
    private String latitude;

    @Column(name = "LOC_LONGITUDE", type = "TEXT")
    private String longitude;

    @Column(name = "NETWORK_LOCATION_TYPE", type = "TEXT")
    private String netWorkLocationType;

    @Column(name = "LOCATION_TIME", type = "TEXT")
    private String time;

    @Column(name = "LOCATION_RADIUS", type = "TEXT")
    private String radius;

    private String loginName;

    public String getRadius() {
        return radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLocType() {
        return locType;
    }

    public void setLocType(String locType) {
        this.locType = locType;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getNetWorkLocationType() {
        return netWorkLocationType;
    }

    public void setNetWorkLocationType(String netWorkLocationType) {
        this.netWorkLocationType = netWorkLocationType;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("{");
        buffer.append("\"locType\":\"" + locType + "\",");
        buffer.append("\"latitude\":\"" + latitude + "\",");
        buffer.append("\"longitude\":\"" + longitude + "\",");
        buffer.append("\"netWorkLocationType\":\"" + netWorkLocationType + "\",");
        buffer.append("\"radius\":\"" + radius + "\",");
        buffer.append("\"time\":\"" + time + "\",");
        buffer.append("\"loginName\":\"" + loginName + "\"");
        buffer.append("}");

        return buffer.toString();
    }

    public boolean equalsToLocation(Location location) {
        double latitude1 = Double.valueOf(getLatitude());
        double latitude2 = Double.valueOf(location.getLatitude());
        double longitude1 = Double.valueOf(getLongitude());
        double longitude2 = Double.valueOf(location.getLongitude());
        String netWorkLocationType1 = (getNetWorkLocationType() == null ? "" : getNetWorkLocationType());
        String netWorkLocationType2 = (location.getNetWorkLocationType() == null ? "" : location
                .getNetWorkLocationType());
        // netWorkLocationType1.equals(netWorkLocationType2)
        if (Math.abs(latitude1 - latitude2) < 0.0001 && Math.abs(longitude1 - longitude2) < 0.0001
                && netWorkLocationType1.equals(netWorkLocationType2)) {
            return true;
        }

        return false;
    }
}