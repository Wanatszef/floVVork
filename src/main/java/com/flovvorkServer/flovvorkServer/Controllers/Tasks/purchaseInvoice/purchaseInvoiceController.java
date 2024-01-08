package com.flovvorkServer.flovvorkServer.Controllers.Tasks.purchaseInvoice;

import com.flovvorkServer.flovvorkServer.Service.*;
import com.flovvorkServer.flovvorkServer.entity.Document;
import com.flovvorkServer.flovvorkServer.entity.DocumentValues;
import com.flovvorkServer.flovvorkServer.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("purchaseInvoice")
public class purchaseInvoiceController
{
    private final UserRepository userRepository;
    private final DocumentRepository documentRepository;
    private final DocumentValuesRepository documentValuesRepository;
    private final UserDetailsRepository userDetailsRepository;
    private final RoleRepository roleRepository;
    private final TaskCreatorRepository taskCreatorRepository;

    private final TaskRepository taskRepository;


    public purchaseInvoiceController(UserRepository userRepository, DocumentRepository documentRepository, DocumentValuesRepository documentValuesRepository, UserDetailsRepository userDetailsRepository, RoleRepository roleRepository, TaskCreatorRepository taskCreatorRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.documentRepository = documentRepository;
        this.documentValuesRepository = documentValuesRepository;
        this.userDetailsRepository = userDetailsRepository;
        this.roleRepository = roleRepository;
        this.taskCreatorRepository = taskCreatorRepository;
        this.taskRepository = taskRepository;
    }

    @GetMapping("/newPurchaseInvoice")
    public String addPurchaseInvoice(Authentication authentication, Model model)
    {
        User user = userRepository.findByUsername(authentication.getName());
        if(taskCreatorRepository.findByUserIdAndTask(user,taskRepository.findByIdTask(2))!=null)
        {
            Document document = new Document();

            DocumentValues documentValues = new DocumentValues();

            model.addAttribute("document",document);

            model.addAttribute("values",documentValues);

            model.addAttribute("user",user);

            return "purchaseInvoice/purchaseInvoice";
        }
        else
            return "accessDenied";


    }

    @PostMapping("savePurchaseInvoice")
    @Transactional
    public String saveUserCreation(Authentication authentication, @RequestParam("documentID") Long documentID, @ModelAttribute("values") DocumentValues documentValues) {

        User user = userRepository.findByUsername(authentication.getName());
        Document document = documentRepository.findByDocumentId(documentID);
        if(document == null)
        {
            document = new Document();
            System.out.println("savingPurchaseInvoice");
            document.setUser(user);
            document.setDocumentName("purchaseInvoice/savedPurchaseInvoice");
            document.setActive(1);
            document.setTitle("saved Purchase Invoice " + documentValues.getText2());
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

    @GetMapping("savedPurchaseInvoice/{documentId}")
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

            return "purchaseInvoice/purchaseInvoice";
        }
        else
        {
            return "accessDenied";
        }

    }
}
