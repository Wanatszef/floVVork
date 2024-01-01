package com.flovvorkServer.flovvorkServer.Service;

import com.flovvorkServer.flovvorkServer.entity.Message;
import com.flovvorkServer.flovvorkServer.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Long>
{
    List<Message> findByReceiver(User receiver);

    Optional<Message> findById(Long Id);

    @Query("SELECT m FROM Message m WHERE (m.sender = :sender OR m.receiver = :receiver) AND m.timestamp IN (SELECT MAX(m.timestamp) FROM Message m WHERE m.sender = :sender OR m.receiver = :receiver GROUP BY COALESCE(m.sender, m.receiver))")
    List<Message> findLatestMessagesBetweenUsers(@Param("sender") User sender, @Param("receiver") User receiver);

    @Query("SELECT m FROM Message m WHERE m.receiver = :receiver AND m.timestamp IN (SELECT MAX(m.timestamp) FROM Message m WHERE m.receiver = :receiver GROUP BY COALESCE(m.sender, m.receiver))")
    List<Message> findLatestMessagesToReceiver(@Param("receiver") User receiver);


}
