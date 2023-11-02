package com.cydeo.controller;

import com.cydeo.enums.AccountType;
import com.cydeo.model.Account;
import com.cydeo.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Date;
import java.util.UUID;

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
    public String getCreateForm(Model model) {

        //we need to provide empty account object.
        model.addAttribute("account", Account.builder().build()); // it will create empty account object

        //we need to provide accountType enum info filling the dropdown options.
        model.addAttribute("accountTypes", AccountType.values());


        return "account/create-account"; // This should match the name of your HTML template.
    }

    @PostMapping("/create") //create a method to capture information from ui
    public String createAccount(@ModelAttribute("account") Account account) {

        //trigger createNewAccount method, create the account based on the user
            accountService.createNewAccount(account.getBalance(),new Date(),account.getAccountType(),account.getUserId());

        // once user created return back to the index page.
        return "redirect:/index";
    }

    @GetMapping("/delete/{id}")
    public String getDeleteAccount(@PathVariable("id")UUID id){


        accountService.deleteAccount(id);

        return "redirect:/index";
    }

    @GetMapping("/activate/{id}")
    public String activateAccount(@PathVariable("id")UUID id){


        accountService.activateAccount(id);

        return "redirect:/index";
    }
}
