package me.matteogiovagnotti.springlamiapizzeria.models;

public class AlertMessage {

    public enum AlertMessageType {
        SUCCESS, ERROR
    }

    private AlertMessageType type;
    private String text;

    public AlertMessage(AlertMessageType type, String text) {
        this.type = type;
        this.text = text;
    }

    public AlertMessage() {

    }

    public AlertMessageType getType() {
        return type;
    }

    public void setType(AlertMessageType type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "AlertMessage{" +
                "type=" + type +
                ", text='" + text + '\'' +
                '}';
    }
}
