package com.spotrum.messagebroker.Entities;

import java.util.Arrays;

public class CChat {
    public String chatId;
    public String[] subscribers;

    @Override
    public String toString() {
        return "ChatDTO{" +
                "chatId='" + chatId + '\'' +
                ", subscribers=" + Arrays.toString(subscribers) +
                '}';
    }
}
