package com.flovvorkServer.flovvorkServer.Controllers;

import com.flovvorkServer.flovvorkServer.DTO.MessageDTO;
import com.flovvorkServer.flovvorkServer.Service.DocumentRepository;
import com.flovvorkServer.flovvorkServer.Service.MessageRepository;
import com.flovvorkServer.flovvorkServer.Service.UserRepository;
import com.flovvorkServer.flovvorkServer.entity.Message;
import com.flovvorkServer.flovvorkServer.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProfileController
{
    DocumentRepository documentRepository;

    UserRepository userRepository;

    MessageRepository messageRepository;

    @Autowired
    public ProfileController(DocumentRepository documentRepository, UserRepository userRepository, MessageRepository messageRepository)
    {
        this.documentRepository = documentRepository;
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
    }

    @GetMapping("/profile")
    public String profileResponse(Authentication authentication, Model model)
    {
        String username = authentication.getName();

        User user = userRepository.findByUsername(username);
        if(user != null)
        {
            model.addAttribute("user", user);
        }
        else
            return "accessDenied";

        List<Message> messageList = messageRepository.findLatestMessagesToReceiver(user);
        if(!messageList.isEmpty())
        {
            List<MessageDTO> messageDTOList = new ArrayList<>();
            for(Message message : messageList)
            {
                messageDTOList.add(new MessageDTO(message.getSender().getUsername(), message.getReceiver().getUsername(), message.getContent(),message.getTimestamp(),message.getSender().getIdUser(),message.getReceiver().getIdUser()));
            }
            model.addAttribute("messageList",messageDTOList);
        }
        return "profile";
    }

    @GetMapping("/userEdit")
    public String editProfile(Authentication authentication, Model model)
    {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);
        if(user != null)
        {
            model.addAttribute("user", user);
        }
        else
            return "accessDenied";
        return "userEdit";
    }
}
