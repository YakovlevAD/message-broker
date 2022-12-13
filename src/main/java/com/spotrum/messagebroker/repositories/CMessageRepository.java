package com.spotrum.messagebroker.repositories;

import com.spotrum.messagebroker.Entities.CMessage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CMessageRepository extends CrudRepository<CMessage, Long> {
    List<CMessage> findCMessageByChatId(Long id);
}
