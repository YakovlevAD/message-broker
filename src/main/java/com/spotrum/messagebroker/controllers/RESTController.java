package com.spotrum.messagebroker.controllers;

import com.spotrum.messagebroker.Entities.CBalance;
import com.spotrum.messagebroker.Entities.CChat;
import com.spotrum.messagebroker.Entities.CEvent;
import com.spotrum.messagebroker.Entities.CLog;
import com.spotrum.messagebroker.services.MessageService;
import com.spotrum.messagebroker.services.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin
public class RESTController {

    @Autowired
    NotificationService notificationService;

    @Autowired
    MessageService messageService;

    //TODO: - вставить id app
    @GetMapping("/")
    public String getMainPage(){
//        messageService.createDemoData();
        notificationService.pushMessage("My title","My body","testappcer.p12", "CBRY875bv45zb2012!","f091b880175a16a4ed7e67e0aa1b5e7f4fbbd4da86ad19e9328b207563f27c67");
        return "<a href=\"https://itunes.apple.com/ru/app/id6444730876\">Download Spotrum app</a>";
    }

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

    private Double balance = 1000.00;
    @GetMapping("/getBalance")
    public CBalance getBalance(@RequestParam("id")String id) {
        System.out.println(">>>>>>>balance");
        balance += balance;
        return new CBalance("$", String.valueOf(balance));
    }
}
