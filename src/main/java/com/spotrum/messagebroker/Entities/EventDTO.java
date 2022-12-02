package com.spotrum.messagebroker.Entities;

public class EventDTO {
    public String id;
    public String title;
    public String description;
    public String location;
    public String timestampStart;
    public String likes;

    @Override
    public String toString() {
        return "EventDTO{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", timestampStart='" + timestampStart + '\'' +
                ", likes='" + likes + '\'' +
                '}';
    }
}
