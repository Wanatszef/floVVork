package com.flovvorkServer.flovvorkServer.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthenticateController
{
    @GetMapping("/authenticatePage")
    public String loginController()
    {
        return "plain-login";
    }
}
