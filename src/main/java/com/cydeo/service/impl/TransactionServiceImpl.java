package com.cydeo.service.impl;

import com.cydeo.enums.AccountType;
import com.cydeo.exception.AccountOwnershipException;
import com.cydeo.exception.BadRequestException;
import com.cydeo.exception.BalanceNotSufficientException;
import com.cydeo.exception.UnderConstructionException;
import com.cydeo.model.Account;
import com.cydeo.model.Transaction;
import com.cydeo.repository.AccountRepository;
import com.cydeo.repository.TransactionRepository;
import com.cydeo.service.TransactionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class TransactionServiceImpl implements TransactionService {

    @Value("${under_construction}")
    private boolean underConstruction;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(AccountRepository accountRepository, TransactionRepository transactionRepository) {

        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Transaction makeTransfer(Account sender, Account receiver, BigDecimal amount, Date creationDate, String message) {
        /** possible obstacle 
         *  - if sender or receiver is null?
         *  -if sender and receiver is the same account?
         *  -if sender has enough balance to make transfer?
         *  -if both accounts are checking, if not, one of them saving, it needs to be same userId.
         * */
        if(!underConstruction) {
            validateAccount(sender, receiver);
            checkAccountOwnership(sender, receiver);
            executeBalanceAndUpdateIfRequired(amount, sender, receiver);


            /**
             * after all validations are completed, and money is transferred,
             * we need to create Transaction object and save/return it.
             * */

            Transaction transaction = Transaction.builder().amount(amount).sender(sender.getId()).receiver(receiver.getId())
                    .createDate(creationDate).message(message).build();

            //save into the db and return it.
            return transactionRepository.save(transaction);
        }else{
            throw new UnderConstructionException("App is under construction, pls try again");
        }
    }

    private void executeBalanceAndUpdateIfRequired(BigDecimal amount, Account sender, Account receiver) {
        if(checkSenderBalance(sender,amount)){
            //update sender and receiver balance

            // Update the sender's balance (subtracting)
            //100-80
            sender.setBalance(sender.getBalance().subtract(amount));

            // Update the receiver's balance (adding)
            //50 + 80
            receiver.setBalance(receiver.getBalance().add(amount));
        }else{
            throw new BalanceNotSufficientException("Balance is not enough for this transfer");
        }
    }

    private boolean checkSenderBalance(Account sender, BigDecimal amount) {
        //verify sender has enough balance to send.
        return sender.getBalance().subtract(amount).compareTo(BigDecimal.ZERO) >=0;


    }

    private void checkAccountOwnership(Account sender, Account receiver) {
        /**
         * write an if statement that checks if one of the account is saving,
         * and user of sender or receiver is not the same,
         * throw AccountOwnershipException
         * */
        //if statement that checks if one of the account is saving,
        if (sender.getAccountType() == AccountType.SAVING ||receiver.getAccountType() == AccountType.SAVING) {
            // and user of sender or receiver is not the same,
            if (!sender.getUserId().equals(receiver.getUserId())) {
                // throw AccountOwnershipException
                throw new AccountOwnershipException("Saving account can only transfer to the same user's account.");
            }
        }
    }

    private void validateAccount(Account sender, Account receiver) {
        /**
         * - if any of the account is null
         * - if account ids are the same (same account)
         * - if the account exist in the database (repository)
         * */
            //- if any of the account is null
        if(sender == null || receiver == null){
            throw new BadRequestException("Sender or Receiver cannot be null");
        }
        //- if accounts are the same throw BadRequestException with saying accounts needs to be different.
        if(sender.getId().equals(receiver.getId())){
            throw new BadRequestException("Sender account needs to be different than receiver account.");

        }

        findAccountById(sender.getId());
        findAccountById(receiver.getId());


    }

    private void findAccountById(UUID id){
        accountRepository.findById(id);
    }

    @Override
    public List<Transaction> findAllTransaction() {
        return transactionRepository.findAll();
    }

    @Override
    public List<Transaction> last10Transactions() {

      return transactionRepository.findLast10Transactions();
    }

    @Override
    public List<Transaction> findTransactionListById(UUID id) {
        return transactionRepository.findTransactionListByAccountId(id);
    }

}
