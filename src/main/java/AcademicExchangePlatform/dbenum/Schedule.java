package AcademicExchangePlatform.dbenum;

public enum Schedule {
    Morning,
    Afternoon,
    Evening;

    @Override
    public String toString() {
        return name();
    }
}
