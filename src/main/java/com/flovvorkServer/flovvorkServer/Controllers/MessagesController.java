package com.flovvorkServer.flovvorkServer.Controllers;

import com.flovvorkServer.flovvorkServer.Service.MessageRepository;
import com.flovvorkServer.flovvorkServer.Service.UserRepository;
import com.flovvorkServer.flovvorkServer.entity.Message;
import com.flovvorkServer.flovvorkServer.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/message")
public class MessagesController
{
    private final MessageRepository messageRepository;

    private final UserRepository userRepository;

    @Autowired
    public MessagesController(MessageRepository messageRepository, UserRepository userRepository)
    {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }


    public List<Message> getLastMessages(Authentication authentication)
    {
        User user = userRepository.findByUsername(authentication.getName());
        List<Message> messages = messageRepository.findLatestMessagesToReceiver(user);
        if (!messages.isEmpty())
        {
            return null;
        }

        else
        {
            return messages;
        }

    }

    @PostMapping("/send")
    public String sendMessage(@RequestParam Long receiverId, @RequestParam String content, Authentication authentication) {

        String senderName = authentication.getName();

        User sender = userRepository.findByUsername(senderName);

        User receiver = userRepository.findByIdUser(receiverId);

        if (sender == null || receiver == null) {
            return "Invalid sender or receiver.";
        }

        Message message = new Message(content, LocalDateTime.now(),sender,receiver);
        messageRepository.save(message);
        return "Message sent successfully.";
    }
}
