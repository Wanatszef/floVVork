package com.flovvorkServer.flovvorkServer.Controllers.Tasks;

import com.flovvorkServer.flovvorkServer.DTO.MessageDTO;
import com.flovvorkServer.flovvorkServer.Service.DocumentRepository;
import com.flovvorkServer.flovvorkServer.Service.MessageRepository;
import com.flovvorkServer.flovvorkServer.Service.TaskCreatorRepository;
import com.flovvorkServer.flovvorkServer.Service.UserRepository;
import com.flovvorkServer.flovvorkServer.entity.Document;
import com.flovvorkServer.flovvorkServer.entity.Message;
import com.flovvorkServer.flovvorkServer.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TasksController
{
    private final UserRepository userRepository;

    private final DocumentRepository documentRepository;

    private final TaskCreatorRepository taskCreatorRepository;

    private final MessageRepository messageRepository;

    public TasksController(UserRepository userRepository, DocumentRepository documentRepository, TaskCreatorRepository taskCreatorRepository, MessageRepository messageRepository)
    {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.documentRepository = documentRepository;
        this.taskCreatorRepository = taskCreatorRepository;
    }

    @GetMapping("/tasks")
    public String tasksToDo(Authentication authentication, Model model)
    {
        String username = authentication.getName();

        User user = userRepository.findByUsername(username);

        if(user != null) {
            model.addAttribute("user", user);
        }
        else {return  "accessDenied";}
        List<Document> activeDocuments  = documentRepository.findDocumentsByActiveAndUser(1,user);

        if(!activeDocuments.isEmpty()) {
            model.addAttribute("activeDocuments", activeDocuments);
        }
        List<User> others = userRepository.findAll();
        List<Message> messageList = new ArrayList<>();
        for(User tempUser : others)
        {
            Message tempMessage = messageRepository.findLatestMessageBetweenUsers(user,tempUser);
            if(tempMessage != null) {
                messageList.add(tempMessage);
            }
        }


        if(!messageList.isEmpty())
        {

            List<MessageDTO> messageDTOList = new ArrayList<>();
            for(Message message : messageList)
            {
                messageDTOList.add(new MessageDTO(message.getSender().getUsername(), message.getReceiver().getUsername(), message.getContent(),message.getTimestamp(),message.getSender().getIdUser(),message.getReceiver().getIdUser()));
            }
            model.addAttribute("messageList",messageDTOList);
        }

        return "tasks";
    }
}
