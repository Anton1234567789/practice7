package ua.nure.sokolov.practice7;

public enum XML {
    // elements names
    OLDCARDS("OldCards"), OLDCARD("OldCard"), THEMA("Thema"), TYPE("Type"),
    COUNTRY("Country"), YEAR("Year"),AUTHOR("Author"), VALUABLE("Valuable"),

    //attr
    SENT("sent"), EXIST("exist");

    private String value;

    XML(String value) {
        this.value = value;
    }

    public boolean equalsTo(String name) {
        return value.equals(name);
    }

    public String value() {
        return value;
    }
}
