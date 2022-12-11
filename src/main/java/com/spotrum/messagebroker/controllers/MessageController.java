package com.spotrum.messagebroker.controllers;

import com.spotrum.messagebroker.Entities.*;
import com.spotrum.messagebroker.services.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin
public class MessageController {

    @Autowired
    MessageService messageService;

    @GetMapping("/")
    public String getMainPage(){
        return "Hello";
    }

    @MessageMapping("/chat/{to}")
    public void sendMessagePersonal(@DestinationVariable String to, MessageDTO message) {
        log.debug(String.format("WS RQ <<< /chat/%s msg:%s", to, message));
        messageService.sendMessage(to, message);

    }

    @MessageMapping("/events/{to}")
    public void sendEvent(@DestinationVariable String to, EventDTO eventDTO){
        log.debug(String.format("WS RQ <<< /events/%s %s", to, eventDTO));
        messageService.sendEvent(to, eventDTO);
    }

    @MessageMapping("/prevEvents/{to}")
    public void sendEvent(@DestinationVariable String to, CEvent cEvent){
        log.debug(String.format("WS RQ <<< /prevEvents/%s event:%s",to, cEvent));
        messageService.sendEvent(cEvent);
    }

    @MessageMapping("/log/{from}")
    public void logFromApp(@DestinationVariable String from, String appLog) {
        log.debug(String.format("LOGAPP [%s] %s", from, appLog));
    }

    @GetMapping("/getAllEvents")
    public List<CEvent> getAllEvents(){
        log.debug(String.format("REST RQ <<< /getAllEvents"));
        return messageService.getAllEventsV1();
    }

    @GetMapping("/v1/getAllEvents")
    public List<CEvent> getAllEventsV1(){
        log.debug(String.format("REST RQ <<< /v1/getAllEvents"));
        return messageService.getAllEventsV1();
    }

    @GetMapping("/getMessagesById")
    public List getMessagesById(@RequestParam("id")String id) {
        log.debug(String.format("REST RQ <<< /getMessagesById id=%s", id));
        return messageService.getMessagesById(id);
    }

    @GetMapping("/getChatBySubscriberId")
    public List getChatBySubscriberId(@RequestParam("id")String id) {
        log.debug(String.format("REST RQ <<< /getChatBySubscriberId id=%s", id));
        return messageService.getChatBySubscriberId(id);
    }

    @GetMapping("/getChatByChatId")
    public CChat getChatByChatId(@RequestParam("id")String id) {
        log.debug(String.format("REST RQ <<< /getChatByChatId id=%s", id));
        return messageService.getChatByChatId(id);
    }

    @PostMapping("/postNewChat")
    public void postNewChat(@RequestBody CChat chat) {
        log.debug(String.format("REST RQ <<< /postNewChat chat:%s", chat));
        messageService.postNewChat(chat);
    }

    @PostMapping("/log")
    public void postLog(@RequestBody CLog logg) {
        log.debug(String.format("APPLOGG time:%s userId:%s payload:%s",logg.getTime(), logg.getUserId(), logg.payload));
    }
}

