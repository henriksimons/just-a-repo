package just.a.repo.project.model;

public enum WindDirection {

    N("North"),
    E("East"),

    S("South"),
    W("West"),

    SE("South East"),
    SW("South West"),

    NE("North East"),
    NW("North West");

    public final String name;

    WindDirection(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
