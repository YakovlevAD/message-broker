package com.spotrum.messagebroker.repositories;

import com.spotrum.messagebroker.Entities.CChat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CChatsRepository extends CrudRepository<CChat, Long> {
}
