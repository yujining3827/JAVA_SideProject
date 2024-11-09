package scheduleManagement;

public enum Category {
    SCHOOL('S'),
    APPOINTMENT('A'),
    NOTIFICATION('N'),
    OTHER('O');

    private final char code;

    Category(char code) {
        this.code = code;
    }

    public char getCode() {
        return code;
    }
}
