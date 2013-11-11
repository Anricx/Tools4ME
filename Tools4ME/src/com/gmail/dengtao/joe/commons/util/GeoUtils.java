package com.gmail.dengtao.joe.commons.util;

public class GeoUtils {
	
	/**
	 * This function converts decimal degrees to radians
	 */
	private static double deg2rad(double deg) {
	    return (deg * Math.PI / 180.0);
	}
	
	/**
	 * Calculate distance between two points in latitude and longitude 
	 * @param lat1 
	 * @param lon1
	 * @param lat2
	 * @param lon2
	 * @return
	 */
	public static double distance(double lat1, double lon1, double lat2, double lon2) {
      double theta = lon1 - lon2;
      double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
      dist = Math.acos(dist);
      dist = rad2deg(dist);
      dist = dist * 60 * 1.1515;
      return (dist);
    }

    /**
     * This function converts radians to decimal degrees
     * @param rad
     */
    private static double rad2deg(double rad) {
      return (rad * 180.0 / Math.PI);
    }

}
