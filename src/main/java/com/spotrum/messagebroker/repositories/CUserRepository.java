package com.spotrum.messagebroker.repositories;

import com.spotrum.messagebroker.Entities.CChat;
import com.spotrum.messagebroker.Entities.CUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface CUserRepository extends CrudRepository<CUser, Long> {

    Optional<CUser> findByUid(String uid);
    Optional<Set<CUser>> findAllByUidIn(Set<String> uids);
}
