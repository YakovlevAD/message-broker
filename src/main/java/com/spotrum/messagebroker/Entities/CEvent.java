package com.spotrum.messagebroker.Entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "c_events")
public class CEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "owner_id")
    public String ownerId;

    @Column(name = "color")
    public String color;

    @Column(name = "title")
    public String title;

    @Column(name = "description")
    public String description;

    @Column(name = "start_time")
    public String startTime;

    @Column(name = "duration")
    public String duration;

    @Column(name = "chat_id")
    public Long chatId;

    @Column(name = "latitude")
    public String latitude;

    @Column(name = "longitude")
    public String longitude;
}
