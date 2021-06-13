package entities;

public class User {
    private Long id;
    private Location location;
    private String availability;
    private String type;
    private String destination;

    public User(Long id, Location location, String availability, String type,String destination) {
        this.id = id;
        this.location = location;
        this.availability = availability;
        this.type = type;
        this.destination=destination;
    }

    @Override
    public String toString() {
        return "{\"id\":" + id + ",\n" +
                "\"latitude\":" + location.getLatitude() + ",\n" +
                "\"longitude\":" + location.getLongitude() + ",\n" +
                "\"status\":\""+availability+"\",\n" +
                "\"type\":\""+type+"\",\n" +
                "\"destination\":\""+destination+"\"\n" +
                "}";
    }
}
