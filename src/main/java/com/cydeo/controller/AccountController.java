package com.cydeo.controller;

import com.cydeo.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccountController {
    //for connect to service
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }


    //write a method to return index.html including account list information
    //endpoint:index
    @GetMapping("/index")
    public String getIndexPage (Model model){

        model.addAttribute("accountList",accountService.listAllAccount());
        return "account/index";
    }

    @GetMapping("/create-account")
    public String getCreateForm() {

        return "account/create-account"; // This should match the name of your HTML template.
    }
}
