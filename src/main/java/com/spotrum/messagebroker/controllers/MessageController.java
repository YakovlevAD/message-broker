package com.spotrum.messagebroker.controllers;

import com.spotrum.messagebroker.Entities.*;
import com.spotrum.messagebroker.services.MessageService;
import com.spotrum.messagebroker.services.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;

import java.time.Period;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAmount;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@CrossOrigin
public class MessageController {

    @Autowired
    MessageService messageService;

    @MessageMapping("/chat/{to}")
    public void sendMessagePersonal(@DestinationVariable String to, CMessage message) {
        log.debug(String.format("WS RQ <<< /chat/%s msg:%s", to, message));
        messageService.sendMessage(to, message);

    }

//    @MessageMapping("/events/{to}")
//    public void sendEvent(@DestinationVariable String to, EventDTO eventDTO){
//        log.debug(String.format("WS RQ <<< /events/%s %s", to, eventDTO));
//        messageService.sendEvent(to, eventDTO);
//    }

    @MessageMapping("/sendNewEvent/{to}")
    public void sendEvent(@DestinationVariable String to, CEvent cEvent){
        log.debug(String.format("WS RQ <<< /sendNewEvent/%s event:%s",to, cEvent));
        messageService.sendNewEvent(cEvent);
    }

    @MessageMapping("/log/{from}")
    public void logFromApp(@DestinationVariable String from, String appLog) {
        log.debug(String.format("LOGAPP [%s] %s", from, appLog));
    }
}

