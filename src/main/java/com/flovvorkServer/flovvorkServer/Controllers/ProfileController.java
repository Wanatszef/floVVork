package com.flovvorkServer.flovvorkServer.Controllers;

import com.flovvorkServer.flovvorkServer.Service.DocumentRepository;
import com.flovvorkServer.flovvorkServer.Service.UserRepository;
import com.flovvorkServer.flovvorkServer.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController
{
    DocumentRepository documentRepository;

    UserRepository userRepository;

    @Autowired
    public ProfileController(DocumentRepository documentRepository, UserRepository userRepository)
    {
        this.documentRepository = documentRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/profile")
    public String profileResponse(Authentication authentication, Model model)
    {
        String username = authentication.getName();

        User user = userRepository.findByUsername(username);

        model.addAttribute("user",user);

        return "profile";
    }
}
