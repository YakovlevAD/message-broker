package com.spotrum.messagebroker.Entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "c_messages")
public class CMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "message_text")
    public String messageText;

    @Column(name = "sender_id")
    public String senderId;

    @Column(name = "chat_id")
    public Long chatId;

    @Column(name = "created_datetime")
    public String createdDatetime;
}
