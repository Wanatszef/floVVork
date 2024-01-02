package com.flovvorkServer.flovvorkServer.Controllers;

import com.flovvorkServer.flovvorkServer.Service.DocumentRepository;
import com.flovvorkServer.flovvorkServer.Service.TaskCreatorRepository;
import com.flovvorkServer.flovvorkServer.Service.UserRepository;
import com.flovvorkServer.flovvorkServer.entity.Document;
import com.flovvorkServer.flovvorkServer.entity.Message;
import com.flovvorkServer.flovvorkServer.entity.TaskAccess;
import com.flovvorkServer.flovvorkServer.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class BasicController
{
    private final UserRepository userRepository;

    private final DocumentRepository documentRepository;

    private final TaskCreatorRepository taskCreatorRepository;

    private final MessagesController messagesController;

    @Autowired
    public BasicController(UserRepository userRepository, DocumentRepository documentRepository, TaskCreatorRepository taskCreatorRepository, MessagesController messagesController)
    {
        this.documentRepository = documentRepository;
        this.userRepository = userRepository;
        this.taskCreatorRepository = taskCreatorRepository;
        this.messagesController = messagesController;
    }


    @GetMapping("")
    public String dashboard(Authentication authentication, Model model)
    {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);
        //todo zmienic w user repository na findByUser_id
        List<Document> historyDocuments = documentRepository.findDocumentByUserAndActiveIsLike(user,0);

        List<Document> activeDocuments = documentRepository.findDocumentByUserAndActiveIsLike(user,1);

        List<Message> lastMessages = messagesController.getLastMessages(authentication);


        String tasksCount = String.valueOf(activeDocuments.size());
        if(user != null)
        {
            model.addAttribute("user", user);
            if(!historyDocuments.isEmpty())
            {
                model.addAttribute("historyDocuments", historyDocuments);
            }

            if(lastMessages != null)
            {
                if (!lastMessages.isEmpty())
                {
                    model.addAttribute("messages", lastMessages);
                }
            }

            if(!activeDocuments.isEmpty())
            {
                model.addAttribute("activeDocuments", activeDocuments);
                if(activeDocuments.size() >0)
                {
                    if(activeDocuments.size() < 2)
                    {
                        tasksCount = tasksCount + " task";
                    }
                    else
                    {
                        tasksCount = tasksCount + " tasks";
                    }
                    model.addAttribute("taskCount", tasksCount);
                }
            }
            return "dashboard";
        }
        else
        {
            return "error";
        }
    }

    @GetMapping("/createTask")
    public String createTask(Authentication authentication, Model model)
    {
        String username = authentication.getName();

        User user = userRepository.findByUsername(username);


        //todo zmienic w user repository na findByUser_id
        List<Document> historyDocuments = documentRepository.findDocumentByUserAndActiveIsLike(user,0);

        List<TaskAccess> availableTasks = taskCreatorRepository.findDistinctByUserId(user);


        if(user != null)
        {
            model.addAttribute("user", user);
            if(!historyDocuments.isEmpty())
            {
                model.addAttribute("historyDocuments", historyDocuments);
            }

            if(!availableTasks.isEmpty())
            {
                model.addAttribute("availableTasks", availableTasks);
            }
            return "createTask";
        }
        else
        {
            return "error";
        }
    }
}
