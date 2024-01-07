package com.flovvorkServer.flovvorkServer.Controllers;

import com.flovvorkServer.flovvorkServer.DTO.MessageDTO;
import com.flovvorkServer.flovvorkServer.Service.DocumentRepository;
import com.flovvorkServer.flovvorkServer.Service.MessageRepository;
import com.flovvorkServer.flovvorkServer.Service.UserDetailsRepository;
import com.flovvorkServer.flovvorkServer.Service.UserRepository;
import com.flovvorkServer.flovvorkServer.entity.Message;
import com.flovvorkServer.flovvorkServer.entity.User;
import com.flovvorkServer.flovvorkServer.entity.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProfileController {
    private final DocumentRepository documentRepository;

    private final UserRepository userRepository;

    private final MessageRepository messageRepository;

    private final UserDetailsRepository userDetailsRepository;

    @Autowired
    public ProfileController(DocumentRepository documentRepository, UserRepository userRepository, MessageRepository messageRepository, UserDetailsRepository userDetailsRepository) {
        this.documentRepository = documentRepository;
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
        this.userDetailsRepository = userDetailsRepository;
    }

    @GetMapping("/profile")
    public String profileResponse(Authentication authentication, Model model) {
        String username = authentication.getName();

        User user = userRepository.findByUsername(username);
        if (user != null) {
            model.addAttribute("user", user);
        } else
            return "accessDenied";

        List<Message> messageList = messageRepository.findLatestMessagesToReceiver(user);
        if (!messageList.isEmpty()) {
            List<MessageDTO> messageDTOList = new ArrayList<>();
            for (Message message : messageList) {
                messageDTOList.add(new MessageDTO(message.getSender().getUsername(), message.getReceiver().getUsername(), message.getContent(), message.getTimestamp(), message.getSender().getIdUser(), message.getReceiver().getIdUser()));
            }
            model.addAttribute("messageList", messageDTOList);
        }
        return "profile";
    }

    @GetMapping("/userEdit")
    public String editProfile(Authentication authentication, Model model) {

        String username = authentication.getName();
        User user = userRepository.findByUsername(username);
        UserDetails userDetails = user.getUserDetails();
        if (user != null) {
            model.addAttribute("user", user);
        } else
            return "accessDenied";
        if (userDetails != null) {
            model.addAttribute("userDetails", userDetails);
        }
        return "userEdit";
    }

    @PostMapping("/saveProfileEdit")
    public String saveProfileEdit(@ModelAttribute("userDetails") UserDetails userDetails, Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName());
        if (user != null) {
            userDetailsRepository.save(userDetails);
        } else {
            return "accessDenied";
        }
        return "redirect:/profile";
    }

    @PostMapping("/saveDetailsEdit")
    public String saveDetailsEdit(@ModelAttribute("user") User user, @RequestParam("currentPassword") String currentPassword,@RequestParam("newPassword") String newPassword,
                                  @RequestParam("confirmNewPassword") String confirmNewPassword, Authentication authentication)
    {
        User logedUser = userRepository.findByUsername(authentication.getName());
        if(logedUser!= null)
        {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            if(bCryptPasswordEncoder.matches(currentPassword,user.getPassword())) {
                if(user.getPassword() != null && newPassword.equals(confirmNewPassword))
                {
                    user.setPassword(newPassword);
                    userRepository.save(user);
                }
                else
                {
                    userRepository.save(user);
                }
            }
            else
                return "redirect:/accessDenied";

        }
        return "redirect:/profile";
    }

    @GetMapping("/editAuthentication")
    public String editAuthentication(Authentication authentication, Model model)
    {
        User user = userRepository.findByUsername(authentication.getName());
        if (user != null)
        {
            model.addAttribute("user",user);
        }
        else
        {
            return "accessDenied";
        }
        return "editAuthentication";
    }
}
