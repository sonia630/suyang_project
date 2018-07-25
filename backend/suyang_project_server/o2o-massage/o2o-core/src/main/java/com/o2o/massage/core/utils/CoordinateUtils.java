package com.o2o.massage.core.utils;

/**
 * 国测局坐标(火星坐标,GCJ02 包括高de地图、谷歌地图) 和 WGS84坐标系 之间的转换
 *
 * Created by lawrence on 17-2-22.
 */
public class CoordinateUtils {

    private static final double PI = 3.1415926535897932384626d;
    private static final double a = 6378245.0d;
    private static final double ee = 0.00669342162296594323d;

    /**
     * WGS84转GCj02
     *
     * @param lng
     * @param lat
     * @returns {*[]}
     */
    public static GeoCoordinate wgs84togcj02(double lng, double lat) {
        if (outOfChina(lng, lat)) {
            return new GeoCoordinate(lng, lat);
        } else {
            double dlat = transformlat(lng - 105.0, lat - 35.0);
            double dlng = transformlng(lng - 105.0, lat - 35.0);
            double radlat = lat / 180.0 * PI;
            double magic = Math.sin(radlat);
            magic = 1 - ee * magic * magic;
            double sqrtmagic = Math.sqrt(magic);
            dlat = (dlat * 180.0) / ((a * (1 - ee)) / (magic * sqrtmagic) * PI);
            dlng = (dlng * 180.0) / (a / sqrtmagic * Math.cos(radlat) * PI);
            double mglat = lat + dlat;
            double mglng = lng + dlng;
            return new GeoCoordinate(mglng, mglat);
        }
    }

    /**
     * GCJ02 转换为 WGS84
     *
     * @param lng
     * @param lat
     * @returns {*[]}
     */
    private static GeoCoordinate gcj02towgs84(double lng, double lat) {
        if (outOfChina(lng, lat)) {
            return new GeoCoordinate(lng, lat);
        } else {
            double dlat = transformlat(lng - 105.0, lat - 35.0);
            double dlng = transformlng(lng - 105.0, lat - 35.0);
            double radlat = lat / 180.0 * PI;
            double magic = Math.sin(radlat);
            magic = 1 - ee * magic * magic;
            double sqrtmagic = Math.sqrt(magic);
            dlat = (dlat * 180.0) / ((a * (1 - ee)) / (magic * sqrtmagic) * PI);
            dlng = (dlng * 180.0) / (a / sqrtmagic * Math.cos(radlat) * PI);
            double mglat = lat + dlat;
            double mglng = lng + dlng;
            return new GeoCoordinate(lng * 2 - mglng, lat * 2 - mglat);
        }
    }

    private static double transformlat(double lng, double lat) {
        double ret = -100.0 + 2.0 * lng + 3.0 * lat + 0.2 * lat * lat + 0.1 * lng * lat + 0.2 * Math.sqrt(Math.abs(lng));
        ret += (20.0 * Math.sin(6.0 * lng * PI) + 20.0 * Math.sin(2.0 * lng * PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(lat * PI) + 40.0 * Math.sin(lat / 3.0 * PI)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(lat / 12.0 * PI) + 320 * Math.sin(lat * PI / 30.0)) * 2.0 / 3.0;
        return ret;
    }

    private static double transformlng(double lng, double lat) {
        double ret = 300.0 + lng + 2.0 * lat + 0.1 * lng * lng + 0.1 * lng * lat + 0.1 * Math.sqrt(Math.abs(lng));
        ret += (20.0 * Math.sin(6.0 * lng * PI) + 20.0 * Math.sin(2.0 * lng * PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(lng * PI) + 40.0 * Math.sin(lng / 3.0 * PI)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(lng / 12.0 * PI) + 300.0 * Math.sin(lng / 30.0 * PI)) * 2.0 / 3.0;
        return ret;
    }

    /**
     * 判断是否在国内，不在国内则不做偏移
     *
     * @param lng
     * @param lat
     * @returns {boolean}
     */
    public static boolean outOfChina(double lng, double lat) {
        return (lng < 72.004 || lng > 137.8347) || ((lat < 0.8293 || lat > 55.8271) || false);
    }

    public static class GeoCoordinate {
        private double lng;
        private double lat;

        public GeoCoordinate(double lng, double lat) {
            this.lat = lat;
            this.lng = lng;
        }

        public double getLng() {
            return lng;
        }

        public double getLat() {
            return lat;
        }

        @Override
        public String toString() {
            return "GeoCoordinate{lat=\"" + lat + "\",lng=\"" + lng + "\"}";
        }
    }

    public static void main(String[] args) {
        /**
         * //wgs84转国测局坐标
         var wgs84togcj02 = coordtransform.wgs84togcj02(116.404, 39.915);
         //国测局坐标转wgs84坐标
         var gcj02towgs84 = coordtransform.gcj02towgs84(116.404, 39.915);
         console.log(wgs84togcj02);
         console.log(gcj02towgs84);
         //result
         //wgs84togcj02:  [ 116.41024449916938, 39.91640428150164 ]
         //gcj02towgs84:  [ 116.39775550083061, 39.91359571849836 ]
         */
        GeoCoordinate geoCoordinate = wgs84togcj02(116.404, 39.915);
        GeoCoordinate geoCoordinate2 = gcj02towgs84(116.404, 39.915);

        System.out.println(geoCoordinate);
        System.out.println(geoCoordinate2);
    }
}
