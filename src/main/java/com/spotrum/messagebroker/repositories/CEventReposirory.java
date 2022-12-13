package com.spotrum.messagebroker.repositories;

import com.spotrum.messagebroker.Entities.CEvent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CEventReposirory extends CrudRepository<CEvent, Long> {
}
