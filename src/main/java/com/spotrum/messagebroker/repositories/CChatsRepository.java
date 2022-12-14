package com.spotrum.messagebroker.repositories;

import com.spotrum.messagebroker.Entities.CChat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CChatsRepository extends CrudRepository<CChat, Long> {
    Optional<CChat> findCChatById_subscriber(String id);
}
