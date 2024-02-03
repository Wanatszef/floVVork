package com.flovvorkServer.flovvorkServer.Controllers.Tasks.purchaseInvoice;

import com.flovvorkServer.flovvorkServer.Service.*;
import com.flovvorkServer.flovvorkServer.entity.*;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

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

            model.addAttribute("documentValues",documentValues);

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

            model.addAttribute("documentValues",documentValues);

            model.addAttribute("user",user);

            return "purchaseInvoice/purchaseInvoice";
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
            document.setUser(userRepository.findByIdUser(1L));
            document.setTitle("new purchase invoice approve");
            document.setDocumentName("purchaseInvoice/purchaseInvoiceApproval");
            document.setDocumentValues(documentValues);
            documentRepository.save(document);
            return "redirect:/";
        }
        else
        {
            document = new Document();
            document.setUser(userRepository.findByIdUser(1L));
            document.setDocumentName("purchaseInvoice/purchaseInvoiceApproval");
            document.setActive(1);
            document.setTitle("purchaseInvoiceApproval");
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

    @GetMapping("/purchaseInvoiceApproval/{documentId}")
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
                return "purchaseInvoice/purchaseInvoiceApproval";
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
        document.setDocumentName("purchaseInvoice/savedPurchaseInvoice");
        documentRepository.save(document);
        return "redirect:/";

    }

}
