package com.spotrum.messagebroker.Entities;


import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "c_chats")
public class CChat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "description")
    public String description;

    @ElementCollection
    @CollectionTable(name="c_subscribers",
            joinColumns = @JoinColumn(name="chat_id"))
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name="id_subscriber",nullable = false)
    public Set<String> id_subscriber;

    public void addSub(String sub){
        if (id_subscriber == null) {
            id_subscriber = new HashSet<>();
        }
        id_subscriber.add(sub);
    }
}
