package life.majiang.community.enums;

public enum NotificationSatusEnum {
    UNREAD(0),READ(1)
    ;
    private int status;

    public int getStatus() {
        return status;
    }

     NotificationSatusEnum(int status) {
        this.status = status;
    }
}
