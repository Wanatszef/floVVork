package com.flovvorkServer.flovvorkServer.Controllers.Tasks.salesInvoice;

import com.flovvorkServer.flovvorkServer.Service.*;
import com.flovvorkServer.flovvorkServer.entity.Document;
import com.flovvorkServer.flovvorkServer.entity.DocumentValues;
import com.flovvorkServer.flovvorkServer.entity.Role;
import com.flovvorkServer.flovvorkServer.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("salesInvoice")
public class salesInvoiceController
{
    private final UserRepository userRepository;
    private final DocumentRepository documentRepository;
    private final DocumentValuesRepository documentValuesRepository;
    private final UserDetailsRepository userDetailsRepository;
    private final RoleRepository roleRepository;
    private final TaskCreatorRepository taskCreatorRepository;

    private final TaskRepository taskRepository;


    public salesInvoiceController(UserRepository userRepository, DocumentRepository documentRepository, DocumentValuesRepository documentValuesRepository, UserDetailsRepository userDetailsRepository, RoleRepository roleRepository, TaskCreatorRepository taskCreatorRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.documentRepository = documentRepository;
        this.documentValuesRepository = documentValuesRepository;
        this.userDetailsRepository = userDetailsRepository;
        this.roleRepository = roleRepository;
        this.taskCreatorRepository = taskCreatorRepository;
        this.taskRepository = taskRepository;
    }

    @GetMapping("/newSalesInvoice")
    public String addPurchaseInvoice(Authentication authentication, Model model)
    {
        User user = userRepository.findByUsername(authentication.getName());
        if(taskCreatorRepository.findByUserIdAndTask(user,taskRepository.findByIdTask(2))!=null)
        {
            Document document = new Document();

            DocumentValues documentValues = new DocumentValues();

            model.addAttribute("document",document);

            model.addAttribute("documentValues",documentValues);

            model.addAttribute("user",user);

            return "salesInvoice/salesInvoice";
        }
        else
            return "accessDenied";


    }

    @PostMapping("saveSalesInvoice")
    @Transactional
    public String saveUserCreation(Authentication authentication, @RequestParam("documentID") Long documentID, @ModelAttribute("values") DocumentValues documentValues) {

        User user = userRepository.findByUsername(authentication.getName());
        Document document = documentRepository.findByDocumentId(documentID);
        if(document == null)
        {
            document = new Document();
            System.out.println("savingSalesInvoice");
            document.setUser(user);
            document.setDocumentName("salesInvoice/savedSalesInvoice");
            document.setActive(1);
            document.setTitle("saved Sales Invoice " + documentValues.getText2());
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

    @GetMapping("savedSalesInvoice/{documentId}")
    public String openSaved(@PathVariable Long documentId, Authentication authentication, Model model)
    {

        Document document = documentRepository.findByDocumentId(documentId);

        DocumentValues documentValues = document.getDocumentValues();

        String username = authentication.getName();

        User user = userRepository.findByUsername(username);

        if(document.getUser() == user)
        {
            model.addAttribute("document", document);

            model.addAttribute("documentValues",documentValues);

            model.addAttribute("user",user);

            return "salesInvoice/salesInvoice";
        }
        else
        {
            return "accessDenied";
        }

    }

    @PostMapping("/sendToSupervisor")
    public String userApprove(@RequestParam("documentID") Long documentID, @ModelAttribute("values") DocumentValues documentValues, Authentication authentication)
    {
        User user = userRepository.findByUsername(authentication.getName());
        Document document = documentRepository.findByDocumentId(documentID);
        if(document != null)
        {
            document.setUser(userRepository.findByUserDetails(userDetailsRepository.findByRoleID(roleRepository.findByRoleID(2))));
            document.setTitle("new sales invoice approve");
            document.setDocumentName("salesInvoice/salesInvoiceApproval");
            document.setDocumentValues(documentValues);
            documentRepository.save(document);
            return "redirect:/";
        }
        else
        {
            document = new Document();
            document.setUser(userRepository.findByUserDetails(userDetailsRepository.findByRoleID(roleRepository.findByRoleID(2))));
            document.setDocumentName("salesInvoice/salesInvoiceApproval");
            document.setActive(1);
            document.setTitle("salesInvoiceApproval");
            document.setDocumentValues(documentValues);
            document.setPreviousUser(user);
            document.setExpireDate(LocalDate.now().plusMonths(1));
            document.setCreateDate(LocalDate.now());
            document.setUpdateDate(LocalDate.now());
            documentValuesRepository.save(documentValues);
            documentRepository.save(document);
            return "redirect:/";
        }
    }

    @GetMapping("/salesInvoiceApproval/{documentId}")
    public String approval(Authentication authentication, Model model, @PathVariable Long documentId)
    {
        User user = userRepository.findByUsername(authentication.getName());
        Document document = documentRepository.findByDocumentId(documentId);
        if(user!= null)
        {
            if(document.getUser().getIdUser().equals(user.getIdUser()))
            {
                List<Role> roles = roleRepository.findAll();
                model.addAttribute("roles",roles);
                model.addAttribute("user",user);
                model.addAttribute("document",document);
                DocumentValues documentValues = document.getDocumentValues();
                model.addAttribute("documentValues",documentValues);
                return "salesInvoice/salesInvoiceApproval";
            }

        }
        return "accessDenied";
    }

    @PostMapping("/decline")
    public String decline(@RequestParam("documentID") Long documentID, @ModelAttribute("values") DocumentValues documentValues)
    {
        Document document = documentRepository.findByDocumentId(documentID);
        document.setActive(0);
        documentRepository.save(document);
        return "redirect:/";
    }

    @PostMapping("/accept")
    public String accept(@RequestParam("documentID") Long documentID, @ModelAttribute("values") DocumentValues documentValues)
    {
        Document document = documentRepository.findByDocumentId(documentID);
        document.setActive(0);
        documentRepository.save(document);
        return "redirect:/";
    }

    @PostMapping("sendBack")
    public String sendToCreator(@RequestParam("documentID") Long documentID, @ModelAttribute("values") DocumentValues documentValues, Authentication authentication) {

        Document document = documentRepository.findByDocumentId(documentID);
        document.setUser(document.getPreviousUser());
        document.setTitle("returned invoice document");
        document.setDocumentName("salesInvoice/savedSalesInvoice");
        documentRepository.save(document);
        return "redirect:/";

    }
}
