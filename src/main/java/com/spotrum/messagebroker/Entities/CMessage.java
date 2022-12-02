package com.spotrum.messagebroker.Entities;

public class CMessage {
    public String id;
    public String messageText;
    public String messageFrom;
    public String messageTo;
    public String createdDatetime;

    @Override
    public String toString() {
        return "CMessage{" +
                "id='" + id + '\'' +
                ", messageText='" + messageText + '\'' +
                ", messageFrom='" + messageFrom + '\'' +
                ", messageTo='" + messageTo + '\'' +
                ", createdDatetime='" + createdDatetime + '\'' +
                '}';
    }
}
