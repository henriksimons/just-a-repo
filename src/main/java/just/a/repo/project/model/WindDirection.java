package just.a.repo.project.model;

public enum WindDirection {

    N("N", 360),
    E("E", 90),

    S("S", 180),
    W("W", 270),

    SE("S-E", 135),
    SW("S-W", 225),

    NE("N-E", 45),
    NW("N-W", 315);

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
