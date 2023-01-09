package com.spotrum.messagebroker.services;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageServiceTest {

    @Test
    void calcAlpha() {
        System.out.println("hex: "+MessageService.calcAlpha("2023-01-10 19:50:04 +0500"));
    }
}