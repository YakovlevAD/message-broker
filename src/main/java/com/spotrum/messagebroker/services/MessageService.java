package com.spotrum.messagebroker.services;

import com.spotrum.messagebroker.Entities.*;
import com.spotrum.messagebroker.repositories.CChatsRepository;
import com.spotrum.messagebroker.repositories.CEventReposirory;
import com.spotrum.messagebroker.repositories.CMessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MessageService {

    @Autowired
    CEventReposirory cEventReposirory;
    @Autowired
    CChatsRepository cChatsRepository;
    @Autowired
    CMessageRepository cMessageRepository;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    public void sendMessage(String to, CMessage message) {
        var msg = new CMessage();
        msg.setChatId(message.getChatId());
        msg.setSenderId(message.getSenderId());
        msg.setCreatedDatetime(message.getCreatedDatetime());
        msg.setMessageText(message.getMessageText());
        msg.setStatus(message.getStatus());
        msg.setSenderName(message.getSenderName());
        var resultMsg = cMessageRepository.save(msg);

        var chatId = resultMsg.getChatId();
        var chat = cChatsRepository.findById(chatId).orElseThrow();
        chat.getId_subscriber().forEach(subid -> {
            log.debug(String.format("WS RS >>> /topic/messages/%s msg:%s", subid, message));
            simpMessagingTemplate.convertAndSend("/topic/messages/" + subid, message);
        });
    }

//    public List<Map<String, Object>> getListMessage(@PathVariable("from") Integer from, @PathVariable("to") Integer to) {
//        log.debug(String.format("WS RS >>> getListMessage from:%s to:%s", from, to));
//        return jdbcTemplate.queryForList("select * from messages where (message_from=? and message_to=?) " +
//                "or (message_to=? and message_from=?) order by created_datetime asc", from, to, from, to);
//    }


//    public List<Map<String, Object>> getListMessageGroups(@PathVariable("groupid") Integer groupid) {
//        log.debug(String.format("WS RS >>> /getListMessageGroups grId:" + groupid));
//        return jdbcTemplate.queryForList("select gm.*,us.name as name from group_messages gm " +
//                "join users us on us.id=gm.user_id " +
//                "where gm.group_id=? order by created_datetime asc", groupid);
//    }

    public List<CMessage> getAllMessagesByChatId(Long id) {
        log.debug(String.format("CMessages.id: %s", id));
        var listMsg = cMessageRepository.findCMessageByChatId(id);
        log.debug(String.format("CMessages: %s", listMsg));
        return listMsg;
    }

    public List getChatBySubscriberId(String id) {
        return ((List<CChat>) cChatsRepository.findAll()).stream().filter(e -> e.getId_subscriber().contains(id))
                .collect(Collectors.toList());
    }

    public CChat getChatByChatId(Long id) {
        return cChatsRepository.findById(id).orElseThrow();
    }

    public CChat getSubscribeToChatByChatId(String userId, Long chatId) {
        var chat = cChatsRepository.findById(chatId).orElseThrow();
        chat.addSub(userId);
        cChatsRepository.save(chat);
        return chat;
    }

    public CChat postNewChat(CChat chat) {
        log.debug(String.format("RQ << /postNewChat"));
        var newChat = new CChat();
        chat.getId_subscriber().forEach(newChat::addSub);
        newChat.setType(1);
        newChat.setDescription(chat.description);
        return cChatsRepository.save(newChat);
    }

//    public void sendEvent(String to, EventDTO eventDTO) {
//        jdbcTemplate.update("insert into events (id, title, description, location, likes, timestampStar) " +
//                "values (?,?,?,current_time )", eventDTO.id, eventDTO.title, eventDTO.description, eventDTO.location, eventDTO.likes);
//        log.debug(String.format("WS RS >>> /topic/events/%s event:%s", to, eventDTO));
//        simpMessagingTemplate.convertAndSend("/topic/events/" + to, eventDTO);
//    }

    public void sendNewEvent(CEvent cEvent) {

            var newEvent = new CEvent();
            newEvent.setOwnerId(cEvent.ownerId);
            newEvent.setTitle(cEvent.title);
            newEvent.setDescription(cEvent.description);
            newEvent.setDuration(cEvent.duration);
            newEvent.setStartTime(cEvent.startTime);

            newEvent.setLatitude(cEvent.latitude);
            newEvent.setLongitude(cEvent.longitude);

            var newChat = new CChat();
            newChat.setDescription("New Chat");
            newChat.setType(1);
            newChat = cChatsRepository.save(newChat);
            System.out.println("############" + newChat.getId());
            newEvent.setChatId(newChat.getId());
            System.out.println("############" + newEvent.getChatId());
            newEvent = cEventReposirory.save(newEvent);
            log.debug(String.format("WS RS >>> /topic/allEvents/1 prevEvent:%s", newEvent));
            simpMessagingTemplate.convertAndSend("/topic/allEvents/1", newEvent);

    }

//    public void sendEventV1(CEvent CEvent) {
//        jdbcTemplate.update("insert into events (id, ownerId, title, description, startTime, duration, chatId, latitude, longitude)" +
//                "values (?,?,?,?,?,?,?,?,?)", CEvent.id, CEvent.ownerId, CEvent.title, CEvent.description, CEvent.startTime, CEvent.duration, CEvent.chatId, CEvent.latitude, CEvent.longitude);
//        log.debug(String.format("WS RS >>> /topic/prevEvents/1 prevEvent:%s", CEvent));
//        simpMessagingTemplate.convertAndSend("/topic/prevEvents/1", CEvent);
//    }

//    public List<PreviewEventDTO> getAllEvents() {
//        var list = jdbcTemplate.queryForList("select * from prevEvents");
//        var nList = list.stream()
//                .map(el -> {
//                    var dto = new PreviewEventDTO();
//                    dto.id = el.get("id").toString();
//                    dto.ownerId = el.get("ownerId").toString();
//                    dto.title = el.get("title").toString();
//                    dto.createrId = el.get("createrId").toString();
//                    dto.status = el.get("status").toString();
//                    dto.duration = el.get("duration").toString();
//                    dto.body = el.get("body").toString();
//                    dto.dateStart = el.get("dateStart").toString();
//                    dto.isPublicEvent = el.get("isPublicEvent").toString();
//                    log.debug(String.format("REST RS >>> /v1/getAllEvents event:%s", dto));
//                    return dto;
//                }).collect(Collectors.toList());
//        return nList;
//    }

    public List<CEvent> getAllEvents() {
        List<CEvent> list = (List<CEvent>) cEventReposirory.findAll();
        list.stream().forEach(el->{
            log.debug(String.format("########:%s",checkEvents(el)));
        });
        return list.stream().filter(this::checkEvents).collect(Collectors.toList());
    }

    public boolean checkEvents(CEvent ev) {
//        return true;
        var startTime = ev.getStartTime();
        System.out.println("######>>>"+startTime);
//        var dur = Float.parseFloat(ev.getDuration());
        log.debug(String.format("GET DATETIME STRING: %s", startTime));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss Z");
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(startTime, formatter);
        log.debug(String.format("RETURN DATETIME: %s",zonedDateTime.minusSeconds(1000)));
        return !zonedDateTime.minusSeconds(1000).isAfter(ZonedDateTime.now()) && !ev.title.isEmpty() && !ev.description.isEmpty();
    }

//    public void postNewEvent(PreviewEventDTO pEventDTO) {
//        jdbcTemplate.update("insert into prevEvents (ownerId, id, createrId, status, duration, title, body, dateStart, isPublicEvent) " +
//                "values (?,?,?,?,?,?,?,?,?)", pEventDTO.ownerId, pEventDTO.id, pEventDTO.createrId, pEventDTO.status, pEventDTO.duration, pEventDTO.title, pEventDTO.body, pEventDTO.dateStart, pEventDTO.isPublicEvent);
//        log.debug(String.format("WS RS >>> /topic/prevEvents/1 prevEvent:%s", pEventDTO));
//        simpMessagingTemplate.convertAndSend("/topic/prevEvents/1", pEventDTO);
//    }

    public List<CChat> getAllChats() {
        return (List<CChat>) cChatsRepository.findAll();
    }

    public String getDemoData() {
        return "Data: msg:s% chat:s% evnt:s%" + cMessageRepository.findAll() + cChatsRepository.findAll() + cEventReposirory.findAll();
    }

    public void createDemoData() {
        CMessage cMessage = new CMessage();
        cMessage.messageText = "2332";
        cMessage.senderId = "2323";
        cMessage.chatId = 1l;
        cMessage.createdDatetime = "2323";
        cMessageRepository.save(cMessage);


        CChat cChat = cChatsRepository.findById(1l).orElseThrow();
        cChat.addSub("sdfsmkdfsdfs");
        cChat = cChatsRepository.save(cChat);

        CEvent cEvent = new CEvent();
        cEvent.ownerId = "232323";
        cEvent.title = "deed";
        cEvent.description = "dede";
        cEvent.duration = "dede";
        cEvent.startTime = "dede";
        cEvent.chatId = 1l;
        cEvent.latitude = "2323";
        cEvent.longitude = "2342234";
        cEventReposirory.save(cEvent);
    }

    public CChat getIndividualChatBetween(String cuId, String suId) {
        return ((List<CChat>) cChatsRepository.findAll()).stream().filter(ev -> ev.getId_subscriber().contains(cuId) && ev.getId_subscriber().contains(suId) && ev.getType() == 0).findFirst().orElse(this.createNewChat(cuId, suId));
    }

    CChat createNewChat(String curid, String secuid) {
        var chatn = new CChat();
        chatn.setType(0);
        var set = new HashSet<String>();
        set.add(curid);
        set.add(secuid);
        chatn.setDescription("New inChat");
        chatn.setId_subscriber(set);
        var nchat = cChatsRepository.save(chatn);
        log.debug("######NEW N CHAT:" + nchat.getId() + " " + nchat.getId_subscriber().size());
        return nchat;
    }
}

// 0 - individual
// 1 - group