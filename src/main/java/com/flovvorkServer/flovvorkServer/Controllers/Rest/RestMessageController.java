package com.flovvorkServer.flovvorkServer.Controllers.Rest;

import com.flovvorkServer.flovvorkServer.DTO.MessageDTO;
import com.flovvorkServer.flovvorkServer.Service.MessageRepository;
import com.flovvorkServer.flovvorkServer.Service.UserRepository;
import com.flovvorkServer.flovvorkServer.entity.Message;
import com.flovvorkServer.flovvorkServer.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("api/messages")
public class RestMessageController
{
    private final MessageRepository messageRepository;

    private final UserRepository userRepository;

    public RestMessageController(MessageRepository messageRepository, UserRepository userRepository)
    {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/{messageID}")
    @ResponseBody
    public List<MessageDTO> getMessages(@PathVariable long messageID, Authentication authentication) {
        List<Message> sentMessages;
        List<Message> receivedMessages;
        User user = userRepository.findByUsername(authentication.getName());
        if(user!=null)
        {
            Message tempMessage;
            Optional<Message> message = messageRepository.findById(messageID);
            if(message.isPresent()) {
                tempMessage = message.get();
                if(tempMessage.getSender().getIdUser().equals(user.getIdUser())||tempMessage.getReceiver().getIdUser().equals(user.getIdUser()))
                {
                    List<MessageDTO> messagesDTOList = new ArrayList<>();
                    sentMessages = messageRepository.findAllMessagesBetweenUsers(user, tempMessage.getReceiver());
                    receivedMessages = messageRepository.findAllMessagesBetweenUsers(user,tempMessage.getSender());
                    if(sentMessages!=null && !sentMessages.isEmpty())
                    {
                        for(Message msg: sentMessages)
                        {
                            messagesDTOList.add(new MessageDTO(msg.getSender().getUsername(),msg.getReceiver().getUsername(),msg.getContent(),msg.getTimestamp(), msg.getSender().getIdUser(), msg.getReceiver().getIdUser()));
                        }
                    }
                    if(receivedMessages!=null && !receivedMessages.isEmpty())
                    {
                        for(Message msg: receivedMessages)
                        {
                            messagesDTOList.add(new MessageDTO(msg.getSender().getUsername(),msg.getReceiver().getUsername(),msg.getContent(),msg.getTimestamp(), msg.getSender().getIdUser(), msg.getReceiver().getIdUser()));
                        }
                    }
                    if (!messagesDTOList.isEmpty())
                    {
                        messagesDTOList.sort(Comparator.comparing(MessageDTO::getTimestamp));
                        return messagesDTOList;
                    }

                }
            }
        }
        return null;
    }


}
