package com.flovvorkServer.flovvorkServer.Controllers;

import com.flovvorkServer.flovvorkServer.Service.MessageRepository;
import com.flovvorkServer.flovvorkServer.Service.UserRepository;
import com.flovvorkServer.flovvorkServer.entity.Message;
import com.flovvorkServer.flovvorkServer.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public String sendMessage(@ModelAttribute("tempMessage") Message message, Authentication authentication) {

        message.setTimestamp(LocalDateTime.now());
        message.setSender(userRepository.findByUsername(authentication.getName()));
        messageRepository.save(message);


        return "redirect:/message/getMessages";
    }

    @GetMapping("getMessages")
    public String getMessages(Authentication authentication, Model model)
    {
        List<Message> messages = new ArrayList<>();
        User user = userRepository.findByUsername(authentication.getName());
        model.addAttribute("user", user);
        model.addAttribute("tempMessage", new Message());

        if (user != null) {
            List<User> restUsers = userRepository.findAll();
            for (User tempUser : restUsers) {
                if (!user.getIdUser().equals(tempUser.getIdUser())) {
                    Message latestMessage = messageRepository.findLatestMessageBetweenUsers(user, tempUser);
                    if (latestMessage != null) {
                        messages.add(latestMessage);
                    }
                }
            }

            if (!messages.isEmpty()) {
                model.addAttribute("messages", messages);
            } else {
                return "accessDenied";
            }
        }


        return "messages";
    }

    /*
    @GetMapping("/message/{messageID}")
    public List<Message> getMessages(@PathVariable long messageID, Authentication authentication, Model model)
    {
        List<Message> messages;
        User user = userRepository.findByUsername(authentication.getName());
        if(user!=null)
        {
            Message tempMessage;
            Optional<Message> message = messageRepository.findById(messageID);
            if(message.isPresent()) {
                tempMessage = message.get();

                if(tempMessage.getSender()==user||tempMessage.getReceiver()==user)
                {
                    messages = messageRepository.findLatestMessagesBetweenUsers(tempMessage.getSender(),tempMessage.getReceiver());
                    if(!messages.isEmpty())
                    {
                        return messages;
                    }
                }
            }
        }
        return null;
    }

     */
}
