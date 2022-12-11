package com.spotrum.messagebroker.services;

import com.spotrum.messagebroker.Entities.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MessageService {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void sendMessage(String to, MessageDTO message) {
        jdbcTemplate.update("insert into messages (message_text,message_from,message_to,created_datetime) " +
                "values (?,?,?,current_time )", message.getMessage_text(), message.getMessage_from(), message.getMessage_to());

        var list = jdbcTemplate.queryForList("select * from chats where id_chat='" + message.getMessage_to() + "'");
        var nList = list.stream()
                .map(el -> {
                    var dto = new CChat();
                    dto.chatId = el.get("id_chat").toString();
                    dto.subscribers = el.get("subscribers").toString()
                            .replace("[", "")
                            .replace("]", "")
                            .split(", ");
                    return dto;
                }).collect(Collectors.toList());
        nList.forEach(chat -> {
            Arrays.stream(chat.subscribers).forEach(subscriberId -> {
                log.debug(String.format("WS RS >>> /topic/messages/%s msg:%s", subscriberId, message));
                simpMessagingTemplate.convertAndSend("/topic/messages/" + subscriberId, message);
            });
        });
    }

    public List<Map<String, Object>> getListMessage(@PathVariable("from") Integer from, @PathVariable("to") Integer to) {
        log.debug(String.format("WS RS >>> getListMessage from:%s to:%s", from, to));
        return jdbcTemplate.queryForList("select * from messages where (message_from=? and message_to=?) " +
                "or (message_to=? and message_from=?) order by created_datetime asc", from, to, from, to);
    }


    public List<Map<String, Object>> getListMessageGroups(@PathVariable("groupid") Integer groupid) {
        log.debug(String.format("WS RS >>> /getListMessageGroups grId:" + groupid));
        return jdbcTemplate.queryForList("select gm.*,us.name as name from group_messages gm " +
                "join users us on us.id=gm.user_id " +
                "where gm.group_id=? order by created_datetime asc", groupid);
    }

    public List<MessageDTO> getMessagesById(String id) {
        var list = jdbcTemplate.queryForList("select * from messages where (message_to=?) " +
                "order by created_datetime asc", id);
        var nList = list.stream()
                .map(el -> {
                    var dto = new MessageDTO();
                    dto.setId(el.get("id").toString());
                    dto.setMessage_text(el.get("message_text").toString());
                    dto.setMessage_from(el.get("message_from").toString());
                    dto.setMessage_to(el.get("message_to").toString());
                    dto.setCreated_datetime(el.get("created_datetime").toString());
                    log.debug(String.format("REST RS >>> /getMessagesById dto:%s", dto));
                    return dto;
                }).collect(Collectors.toList());
        return nList;
    }

    public List getChatBySubscriberId(String id) {
        var list = jdbcTemplate.queryForList("select * from chats where subscribers like '%" + id + "%'");
        var nList = list.stream()
                .map(el -> {
                    var dto = new CChat();
                    dto.chatId = el.get("id_chat").toString();
                    dto.subscribers = el.get("subscribers").toString()
                            .replace("[", "")
                            .replace("]", "")
                            .split(", ");
                    log.debug(String.format("REST RS >>> /getChatBySubscriberId dto:%s ", dto));
                    return dto;
                }).collect(Collectors.toList());
        return nList;
    }

    public CChat getChatByChatId(String id) {
        var resultSet = jdbcTemplate.queryForList("select * from chats where id_chat like '" + id + "'");
        var supChat = new CChat();
        supChat.chatId = "1";
        return resultSet.stream()
                .map( el -> {
                    var dto = new CChat();
                    dto.chatId = el.get("id_chat").toString();
                    dto.subscribers = el.get("subscribers").toString()
                            .replace("[", "")
                            .replace("]", "")
                            .split(", ");
                            return dto;
                }).findFirst().orElse(new CChat());
    }

    public void postNewChat(CChat chat) {
        jdbcTemplate.update("insert into chats (id_chat,subscribers) " +
                "values (?,?)", chat.chatId, Arrays.toString(chat.subscribers));
    }

    public void sendEvent(String to, EventDTO eventDTO) {
        jdbcTemplate.update("insert into events (id, title, description, location, likes, timestampStar) " +
                "values (?,?,?,current_time )", eventDTO.id, eventDTO.title, eventDTO.description, eventDTO.location, eventDTO.likes);
        log.debug(String.format("WS RS >>> /topic/events/%s event:%s", to, eventDTO));
        simpMessagingTemplate.convertAndSend("/topic/events/" + to, eventDTO);
    }

    public void sendEvent(CEvent cEvent) {
        jdbcTemplate.update("insert into cEvents (id, ownerId, title, description, startTime, duration, chatId, latitude, longitude)" +
                "values (?,?,?,?,?,?,?,?,?)", cEvent.id, cEvent.ownerId, cEvent.title, cEvent.description, cEvent.startTime, cEvent.duration, cEvent.chatId, cEvent.latitude, cEvent.longitude);
        log.debug(String.format("WS RS >>> /topic/prevEvents/1 prevEvent:%s", cEvent));
        simpMessagingTemplate.convertAndSend("/topic/prevEvents/1", cEvent);
    }

//    public void sendEventV1(CEvent CEvent) {
//        jdbcTemplate.update("insert into events (id, ownerId, title, description, startTime, duration, chatId, latitude, longitude)" +
//                "values (?,?,?,?,?,?,?,?,?)", CEvent.id, CEvent.ownerId, CEvent.title, CEvent.description, CEvent.startTime, CEvent.duration, CEvent.chatId, CEvent.latitude, CEvent.longitude);
//        log.debug(String.format("WS RS >>> /topic/prevEvents/1 prevEvent:%s", CEvent));
//        simpMessagingTemplate.convertAndSend("/topic/prevEvents/1", CEvent);
//    }

    public List<PreviewEventDTO> getAllEvents() {
        var list = jdbcTemplate.queryForList("select * from prevEvents");
        var nList = list.stream()
                .map(el -> {
                    var dto = new PreviewEventDTO();
                    dto.id = el.get("id").toString();
                    dto.ownerId = el.get("ownerId").toString();
                    dto.title = el.get("title").toString();
                    dto.createrId = el.get("createrId").toString();
                    dto.status = el.get("status").toString();
                    dto.duration = el.get("duration").toString();
                    dto.body = el.get("body").toString();
                    dto.dateStart = el.get("dateStart").toString();
                    dto.isPublicEvent = el.get("isPublicEvent").toString();
                    log.debug(String.format("REST RS >>> /v1/getAllEvents event:%s", dto));
                    return dto;
                }).collect(Collectors.toList());
        return nList;
    }

    public List<CEvent> getAllEventsV1() {
        var list = jdbcTemplate.queryForList("select * from cEvents");
        var nList = list.stream()
                .map(el -> {
                    var dto = new CEvent();
                    dto.id = el.get("id").toString();
                    dto.ownerId = el.get("ownerId").toString();
                    dto.title = el.get("title").toString();
                    dto.description = el.get("description").toString();
                    dto.startTime = el.get("startTime").toString();
                    dto.duration = el.get("duration").toString();
                    dto.chatId = el.get("chatId").toString();
                    dto.latitude = el.get("latitude").toString();
                    dto.longitude = el.get("longitude").toString();
                    log.debug(String.format("REST RS >>> /v1/getAllEvents event:%s", dto));
                    return dto;
                }).collect(Collectors.toList());
        return nList;
    }

    public void postNewEvent(PreviewEventDTO pEventDTO) {
        jdbcTemplate.update("insert into prevEvents (ownerId, id, createrId, status, duration, title, body, dateStart, isPublicEvent) " +
                "values (?,?,?,?,?,?,?,?,?)", pEventDTO.ownerId, pEventDTO.id, pEventDTO.createrId, pEventDTO.status, pEventDTO.duration, pEventDTO.title, pEventDTO.body, pEventDTO.dateStart, pEventDTO.isPublicEvent);
        log.debug(String.format("WS RS >>> /topic/prevEvents/1 prevEvent:%s", pEventDTO));
        simpMessagingTemplate.convertAndSend("/topic/prevEvents/1", pEventDTO);
    }
}
