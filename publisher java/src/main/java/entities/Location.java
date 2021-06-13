package entities;

public class Location {
    private double latitude;
    private double longitude;

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public static Location randomCoordinates(double minLat,double maxLat,double minLon,double maxLon){
        double latitude = Math.random() * (maxLat - minLat) + minLat;
        double longitude = Math.random() * (maxLon - minLon) + minLon;
        return new Location(latitude,longitude);
    }
}
