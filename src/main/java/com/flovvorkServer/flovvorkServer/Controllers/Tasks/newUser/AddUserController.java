package com.flovvorkServer.flovvorkServer.Controllers.Tasks.newUser;

import com.flovvorkServer.flovvorkServer.Service.DocumentRepository;
import com.flovvorkServer.flovvorkServer.Service.DocumentValuesRepository;
import com.flovvorkServer.flovvorkServer.Service.UserRepository;
import com.flovvorkServer.flovvorkServer.entity.Document;
import com.flovvorkServer.flovvorkServer.entity.DocumentValues;
import com.flovvorkServer.flovvorkServer.entity.User;
import com.flovvorkServer.flovvorkServer.entity.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.print.Doc;
import java.time.LocalDate;

@Controller
public class AddUserController
{
    UserRepository userRepository;

    DocumentRepository documentRepository;

    DocumentValuesRepository documentValuesRepository;


    @Autowired
    public AddUserController(UserRepository userRepository, DocumentRepository documentRepository, DocumentValuesRepository documentValuesRepository)
    {
        this.userRepository = userRepository;
        this.documentRepository = documentRepository;
        this.documentValuesRepository = documentValuesRepository;
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

        if(user == null)
        {
            return "accessDenied";
        }

        else
        {
            model.addAttribute(user);
            return "newUser/newUser";
        }
    }

    @PostMapping("saveUserEdit")
    public String saveUserCreation(Authentication authentication, Model model, DocumentValues values, Document document)
    {

        String username = authentication.getName();

        User user = userRepository.findByUsername(username);

        if(user != null)
        {

            document.setDocumentValues(values);

            document.setDocumentName("newUser/newUser");

            document.setActive(1);

            document.setCreateDate(LocalDate.now());

            document.setTitle("New user draft");

            document.setUpdateDate(LocalDate.now());

            model.addAttribute("user", user);

            document.setUser(user);

            document.setPreviousUser(user);
        }

        documentValuesRepository.save(values);

        documentRepository.save(document);

        return "redirect:http://localhost:8080";
    }


    @GetMapping("savedUser/{documentId}")
    public String openSaved(@PathVariable Long documentId, Authentication authentication, Model model)
    {
        Document document = documentRepository.findByDocumentId(documentId);

        DocumentValues documentValues = document.getDocumentValues();

        String username = authentication.getName();

        User user = userRepository.findByUsername(username);

        model.addAttribute("document", document);

        model.addAttribute("values",documentValues);


        if(user == null)
        {
            return "accessDenied";
        }

        else
        {
            model.addAttribute(user);
            return "newUser/newUser";
        }

    }

}

