package com.spotrum.messagebroker.Entities;

public class PreviewEventDTO {
    public String ownerId;
    public String id;
    public String createrId;
    public String status;
    public String duration;
    public String title;
    public String body;
    public String dateStart;
    public String isPublicEvent;

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreaterId() {
        return createrId;
    }

    public void setCreaterId(String createrId) {
        this.createrId = createrId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getIsPublicEvent() {
        return isPublicEvent;
    }

    public void setIsPublicEvent(String isPublicEvent) {
        this.isPublicEvent = isPublicEvent;
    }

    @Override
    public String toString() {
        return "PreviewEventDTO{" +
                "'ownerId'='" + ownerId + '\'' +
                ", 'id'='" + id + '\'' +
                ", 'createrId'='" + createrId + '\'' +
                ", 'status'='" + status + '\'' +
                ", 'duration'='" + duration + '\'' +
                ", 'title='" + title + '\'' +
                ", 'body='" + body + '\'' +
                ", 'dateStart'='" + dateStart + '\'' +
                ", 'isPublicEvent'='" + isPublicEvent + '\'' +
                '}';
    }
}
