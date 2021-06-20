package model;

public class Carriage {
    public String id;
    public String name;
    public String from;
    public String to;
    public int traveledDistance;

    public Carriage(String id, String name, String from, String to, int traveledDistance) {
        this.id = id;
        this.name = name;
        this.from = from;
        this.to = to;
        this.traveledDistance = traveledDistance;
    }

    @Override
    public String toString() {
        return "Carriage{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", traveledDistance=" + traveledDistance +
                '}';
    }
}