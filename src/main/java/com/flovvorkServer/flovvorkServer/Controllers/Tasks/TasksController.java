package com.flovvorkServer.flovvorkServer.Controllers.Tasks;

import com.flovvorkServer.flovvorkServer.Service.DocumentRepository;
import com.flovvorkServer.flovvorkServer.Service.TaskCreatorRepository;
import com.flovvorkServer.flovvorkServer.Service.UserRepository;
import com.flovvorkServer.flovvorkServer.entity.Document;
import com.flovvorkServer.flovvorkServer.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class TasksController
{
    private final UserRepository userRepository;

    private final DocumentRepository documentRepository;

    private final TaskCreatorRepository taskCreatorRepository;

    public TasksController(UserRepository userRepository, DocumentRepository documentRepository, TaskCreatorRepository taskCreatorRepository)
    {
        this.userRepository = userRepository;
        this.documentRepository = documentRepository;
        this.taskCreatorRepository = taskCreatorRepository;
    }

    @GetMapping("/tasks")
    public String tasksToDo(Authentication authentication, Model model)
    {
        String username = authentication.getName();

        User user = userRepository.findByUsername(username);

        model.addAttribute("user",user);

        List<Document> activeDocuments  = documentRepository.findDocumentsByActiveAndUser(1,user);

        model.addAttribute("activeDocuments",activeDocuments);

        return "tasks";
    }
}
