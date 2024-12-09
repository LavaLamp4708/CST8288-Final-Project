package AcademicExchangePlatform.dbenum;

public enum DeliveryMethod {
    In_Person,
    Remote,
    Hybrid;

    @Override
    public String toString() {
        return name().replace("_", "-"); // Adjust enum to match database values
    }
}
