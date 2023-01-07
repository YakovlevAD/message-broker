package com.spotrum.messagebroker.Entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "c_users")
public class CUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "uid")
    public String uid;

    @Column(name = "username")
    public String username;

    @Column(name = "email")
    public String email;

    @Column(name = "description")
    public String description;

    @Column(name = "token")
    public String token;

    @Column(name = "status")
    public Integer status;
}
