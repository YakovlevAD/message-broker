package com.spotrum.messagebroker.controllers;

import com.spotrum.messagebroker.Entities.*;
import com.spotrum.messagebroker.services.MessageService;
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


    ////// WebSocket

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

    /////// REST
    //TODO: - вставить id app
    @GetMapping("/")
    public String getMainPage(){
        messageService.createDemoData();
        return "<a href=\"https://itunes.apple.com/ru/app/id6444730876\">Download Spotrum app</a>";
    }
//    @GetMapping("/s")
//    public ResponseEntity getSecondPage(){
//        return ResponseEntity.ok(messageService.getDemoData());
//    }

    @GetMapping("/getAllEvents")
    public List<CEvent> getAllEvents(){
        log.debug(String.format("REST RQ <<< /getAllEvents"));
        return messageService.getAllEvents();
    }

    @GetMapping("/getAllChats")
    public List<CChat> getAllChats(){
        log.debug(String.format("REST RQ <<< /getAllChats"));
        return messageService.getAllChats();
    }

    @GetMapping("/getAllMessagesByChatId/{id}")
    public List getMessagesById(@PathVariable("id")Long id) {
        log.debug(String.format("REST RQ <<< /getAllMessagesByChatId/%s", id));
        return messageService.getAllMessagesByChatId(id);
    }

    @GetMapping("/getChatBySubscriberId")
    public List getChatBySubscriberId(@RequestParam("id")String id) {
        log.debug(String.format("REST RQ <<< /getChatBySubscriberId id=%s", id));
        return messageService.getChatBySubscriberId(id);
    }

    @GetMapping("/getChatByChatId")
    public CChat getChatByChatId(@RequestParam("id")Long id) {
        log.debug(String.format("REST RQ <<< /getChatByChatId id=%s", id));
        return messageService.getChatByChatId(id);
    }

    @GetMapping("/getIndividualChatBetween")
    public CChat getIndividualChatBetwen(@RequestParam("cuId")String cuId, @RequestParam("suId")String suId) {
        log.debug(String.format("REST  RQ <<< /getIndividualChatBetween cuId=%s suId=%s",  cuId, suId));
        return messageService.getIndividualChatBetween(cuId, suId);
    }

    @GetMapping("/getSubscribeToChatByChatId")
    public CChat getSubscribeToChatByChatId(@RequestParam("userId")String userId, @RequestParam("chatId")Long chatId) {
        log.debug(String.format("REST RQ <<< getSubscribeToChatByChatId userId:%s chatId:%s",userId,chatId));
        return messageService.getSubscribeToChatByChatId(userId, chatId);
    }

    @PostMapping("/postNewChat")
    public CChat postNewChat(@RequestBody CChat chat) {
        log.debug(String.format("REST RQ <<< /postNewChat chat:%s", chat));
        var dto = messageService.postNewChat(chat);
        log.debug(String.format("CHAT: %s", dto));
        return dto;
    }

    @PostMapping("/log")
    public void postLog(@RequestBody CLog logg) {
        log.debug(String.format("APPLOGG time:%s userId:%s payload:%s",logg.getTime(), logg.getUserId(), logg.payload));
    }
}

