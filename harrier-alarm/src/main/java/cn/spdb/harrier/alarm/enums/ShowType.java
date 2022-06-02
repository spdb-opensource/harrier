package cn.spdb.harrier.alarm.enums;


public enum ShowType {
    /**
     * 0 TABLE;
     * 1 TEXT;
     * 2 attachment;
     * 3 TABLE+attachment;
     */
    TABLE(0, "table"),
    TEXT(1, "text"),
    ATTACHMENT(2, "attachment"),
    TABLEAT_TACHMENT(3, "table attachment");

    ShowType(int code, String descp) {
        this.code = code;
        this.descp = descp;
    }

    private final int code;
    private final String descp;

    public int getCode() {
        return code;
    }

    public String getDescp() {
        return descp;
    }
}
