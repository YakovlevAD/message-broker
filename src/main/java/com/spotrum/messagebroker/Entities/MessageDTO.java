package com.spotrum.messagebroker.Entities;

public class MessageDTO {

    private String id;
    private String message_text;
    private String message_from;
    private String message_to;
    private String created_datetime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage_text() {
        return message_text;
    }

    public void setMessage_text(String message_text) {
        this.message_text = message_text;
    }

    public String getMessage_from() {
        return message_from;
    }

    public void setMessage_from(String message_from) {
        this.message_from = message_from;
    }

    public String getMessage_to() {
        return message_to;
    }

    public void setMessage_to(String message_to) {
        this.message_to = message_to;
    }

    public String getCreated_datetime() {
        return created_datetime;
    }

    public void setCreated_datetime(String created_datetime) {
        this.created_datetime = created_datetime;
    }

    @Override
    public String toString() {
        return "MessageDTO{" +
                "id=" + id +
                ", message_text=" + message_text +
                ", message_from=" + message_from +
                ", message_to=" + message_to +
                ", created_datetime=" + created_datetime +
                '}';
    }
}
