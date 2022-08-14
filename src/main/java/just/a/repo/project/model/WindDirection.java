package just.a.repo.project.model;

public enum WindDirection {

    N("North", 360),
    E("East", 90),

    S("South", 180),
    W("West", 270),

    SE("South East", 135),
    SW("South West", 225),

    NE("North East", 45),
    NW("North West", 315);

    private final String name;
    private final int degrees;


    WindDirection(String name, int degrees) {
        this.name = name;
        this.degrees = degrees;

    }

    public String getName() {
        return name;
    }

    public int getDegrees() {
        return degrees;
    }
}
