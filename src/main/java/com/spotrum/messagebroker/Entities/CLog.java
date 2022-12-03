package com.spotrum.messagebroker.Entities;

public class CLog {
    public String userId;
    public String payload;
    public String time;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "CLog{" +
                "userId='" + userId + '\'' +
                ", payload='" + payload + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
