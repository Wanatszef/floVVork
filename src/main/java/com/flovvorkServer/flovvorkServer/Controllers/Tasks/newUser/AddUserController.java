package com.flovvorkServer.flovvorkServer.Controllers.Tasks.newUser;

import com.flovvorkServer.flovvorkServer.Service.*;
import com.flovvorkServer.flovvorkServer.entity.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
public class AddUserController
{
    private final UserRepository userRepository;

    private final DocumentRepository documentRepository;

    private final DocumentValuesRepository documentValuesRepository;

    private final TaskCreatorRepository taskCreatorRepository;

    private final TaskRepository taskRepository;

    private final UserDetailsRepository userDetailsRepository;

    private final RoleRepository roleRepository;


    @Autowired
    public AddUserController(TaskCreatorRepository taskCreatorRepository, UserRepository userRepository, DocumentRepository documentRepository, DocumentValuesRepository documentValuesRepository, TaskRepository taskRepository, UserDetailsRepository userDetailsRepository, RoleRepository roleRepository)
    {
        this.taskCreatorRepository = taskCreatorRepository;
        this.userRepository = userRepository;
        this.documentRepository = documentRepository;
        this.documentValuesRepository = documentValuesRepository;
        this.taskRepository = taskRepository;
        this.userDetailsRepository = userDetailsRepository;
        this.roleRepository = roleRepository;
    }

    @GetMapping("newUser")
    public String AddUserStart(Authentication authentication, Model model)
    {
        Document document = new Document();

        DocumentValues values = new DocumentValues();

        String username = authentication.getName();

        model.addAttribute("document", document);

        model.addAttribute("values",values);

        User user = userRepository.findByUsername(username);

        model.addAttribute("user",user);

        if(user == null)
        {
            return "accessDenied";
        }

        else
        {
            return "newUser/newUser";
        }
    }

    @PostMapping("saveUserEdit")
    @Transactional
    public String saveUserCreation(Authentication authentication, @RequestParam("documentID") Long documentID, @ModelAttribute("values") DocumentValues documentValues) {

        User user = userRepository.findByUsername(authentication.getName());
        Document document = documentRepository.findByDocumentId(documentID);
        if(document == null)
        {
            document = new Document();
            System.out.println("aaaaa");
            document.setUser(user);
            document.setDocumentName("savedUser");
            document.setActive(1);
            document.setTitle("new user request");
            document.setDocumentValues(documentValues);
            document.setPreviousUser(user);
            document.setExpireDate(LocalDate.now().plusMonths(1));
            document.setCreateDate(LocalDate.now());
            document.setUpdateDate(LocalDate.now());
            documentValuesRepository.save(documentValues);
            documentRepository.save(document);

        }
        else
        {
            document.setUpdateDate(LocalDate.now());
            document.setExpireDate(LocalDate.now().plusMonths(1));
            documentRepository.save(document);
                int pastID = document.getDocumentValues().getDocumentValuesId();
            documentValuesRepository.deleteByDocumentValuesId(pastID);
            document.setDocumentValues(documentValues);
            documentValuesRepository.save(documentValues);
        }

        return "redirect:/";
    }



    @GetMapping("savedUser/{documentId}")
    public String openSaved(@PathVariable Long documentId, Authentication authentication, Model model)
    {

        Document document = documentRepository.findByDocumentId(documentId);

        DocumentValues documentValues = document.getDocumentValues();

        String username = authentication.getName();

        User user = userRepository.findByUsername(username);

        if(document.getUser() == user)
        {
            model.addAttribute("document", document);

            model.addAttribute("values",documentValues);

            model.addAttribute("user",user);

            return "newUser/newUser";
        }
        else
        {
            return "accessDenied";
        }

    }

    @PostMapping("/sendToSupervisor")
    public String userApprove(@RequestParam("documentID") Long documentID, @ModelAttribute("values") DocumentValues documentValues) {

        Document document = documentRepository.findByDocumentId(documentID);
        document.setUser(userRepository.findByUserDetails(userDetailsRepository.findByRoleID(roleRepository.findByRoleID(2))));
        System.out.println("userRepository.findByUserDetails(userDetailsRepository.findByRoleID(roleRepository.findByRoleID(2)))");
        document.setTitle("new user approve");
        document.setDocumentName("userApproval");
        documentRepository.save(document);
        return "redirect:/";
    }

    @GetMapping("/userApproval/{documentId}")
    public String approval(Authentication authentication, Model model, @PathVariable Long documentId)
    {
        User user = userRepository.findByUsername(authentication.getName());
        Document document = documentRepository.findByDocumentId(documentId);
        if(user!= null)
        {
            if(document.getUser().getIdUser().equals(user.getIdUser()))
            {
                model.addAttribute("document",document);
                DocumentValues documentValues = document.getDocumentValues();
                model.addAttribute("documentValues",documentValues);
                return "newUser/userApproval";
            }

        }
        return "accessDenied";
    }

}

