package com.cydeo;

import com.cydeo.enums.AccountType;
import com.cydeo.model.Account;
import com.cydeo.service.AccountService;
import com.cydeo.service.TransactionService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.math.BigDecimal;
import java.util.Date;

@SpringBootApplication
public class BankSimulationAppApplication {

    public static void main(String[] args) {
        //This is your configuration class and runner class with SpringBoot
        ConfigurableApplicationContext container = SpringApplication.run(BankSimulationAppApplication.class, args);

        //get account and transaction service beans
        AccountService accountService = container.getBean(AccountService.class);
        TransactionService transactionService = container.getBean(TransactionService.class);

        //create 2 accounts sender and receiver
        Account sender = accountService.createNewAccount(BigDecimal.valueOf(70), new Date(), AccountType.CHECKING, 1L);
        Account receiver = accountService.createNewAccount(BigDecimal.valueOf(50), new Date(), AccountType.CHECKING, 2L);
        Account receive2 = accountService.createNewAccount(BigDecimal.valueOf(150), new Date(), AccountType.SAVING, 2L);
        Account receive3 = accountService.createNewAccount(BigDecimal.valueOf(250), new Date(), AccountType.CHECKING, 2L);
        Account receive4 = accountService.createNewAccount(BigDecimal.valueOf(550), new Date(), AccountType.SAVING, 2L);

        // Account receiver2 = null;
        //with happy path test
       // transactionService.makeTransfer(sender,receiver,new BigDecimal(40),new Date(),"Transaction 1");

//        System.out.println(transactionService.findAllTransaction().get(0));
//
//        accountService.listAllAccount().forEach(System.out::println);

        //with negative path test for BalanceNotSufficientException

//        transactionService.makeTransfer(sender,receiver,new BigDecimal(400),new Date(),"Transaction 1");
//
//        System.out.println(transactionService.findAllTransaction().get(0));
//
//        accountService.listAllAccount().forEach(System.out::println);


    }

}
