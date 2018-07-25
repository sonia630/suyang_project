package com.o2o.massage.core.common;

import com.o2o.massage.core.constants.DeviceOs;
import com.o2o.massage.core.utils.CoordinateUtils;
import com.o2o.massage.core.utils.JSONUtils;

import java.io.IOException;

public class DeviceInfo {
    private String deviceId="default";  // 设备信息
    private DeviceOs osn = DeviceOs.Android;  // 操作系统
    private String osv;  // 操作系统版本
    private double longitude = -1;  // 经度
    private double latitude = -1;  // 纬度
    public DeviceInfo() {
    }

    public boolean isValidGps() {
        return this.latitude >= 0 && this.longitude >= 0;
    }


    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public DeviceOs getOsn() {
        return osn;
    }

    public void setOsn(DeviceOs osn) {
        this.osn = osn;
    }

    public String getOsv() {
        return osv;
    }

    public void setOsv(String osv) {
        this.osv = osv;
    }


    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }



    public void setLongitude(String longitude) {
        try {
            this.longitude = Double.valueOf(longitude);
        } catch (Exception e) {
            this.longitude = -1;
        }
    }

    public void setLatitude(String latitude) {
        try {
            this.latitude = Double.valueOf(latitude);
        } catch (Exception e) {
            this.latitude = -1;
        }
    }


    @Override
    public String toString() {
        try {
            return JSONUtils.writeValue(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public CoordinateUtils.GeoCoordinate getGcj02Coordinate() {
        return getGcj02Coordinate(longitude, latitude);
    }

    public CoordinateUtils.GeoCoordinate getGcj02Coordinate(double oriLng, double oriLat) {
        if (osn == DeviceOs.iOS) {
            return CoordinateUtils.wgs84togcj02(oriLng, oriLat);
        }
        return new CoordinateUtils.GeoCoordinate(oriLng, oriLat);
    }
}
