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

    @Query("SELECT m FROM Message m " +
            "WHERE (m.sender = :user1 AND m.receiver = :user2) OR (m.sender = :user2 AND m.receiver = :user1) " +
            "ORDER BY m.timestamp DESC " +
            "LIMIT 1")
    Message findLatestMessageBetweenUsers(@Param("user1") User user1, @Param("user2") User user2);

    @Query("SELECT m\n" +
            "FROM Message m\n" +
            "WHERE (m.sender = :user OR m.receiver = :user)\n" +
            "      AND m.timestamp = (\n" +
            "          SELECT MAX(m2.timestamp)\n" +
            "          FROM Message m2\n" +
            "          WHERE (m2.sender = :user AND m2.receiver = m.receiver)\n" +
            "             OR (m2.sender = m.receiver AND m2.receiver = :user)\n" +
            "      )")
    List<Message> findLatestMessagesToReceiver(@Param("user") User receiver);

    @Query("SELECT m from Message m where m.receiver = :receiver and m.sender = :sender")
    List<Message> findMessagesReceviedFromSpecifiedSender(@Param("sender") User sender, @Param("receiver") User receiver);

    @Query("SELECT m FROM Message m " +
            "WHERE (m.sender = :user1 AND m.receiver = :user2) OR (m.sender = :user2 AND m.receiver = :user1) " +
            "ORDER BY m.timestamp ASC")
    List<Message> findAllMessagesBetweenUsers(@Param("user1") User user1, @Param("user2") User user2);

}
