package com.spotrum.messagebroker.Entities;

public class CEvent {
    public String id;
    public String ownerId;
    public String title;
    public String description;
    public String startTime;
    public String duration;
    public String chatId;
    public String latitude;
    public String longitude;

    @Override
    public String toString() {
        return "CEvent{" +
                "id='" + id + '\'' +
                ", ownerId='" + ownerId + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startTime='" + startTime + '\'' +
                ", duration='" + duration + '\'' +
                ", chatId='" + chatId + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                '}';
    }
}
