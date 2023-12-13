package com.cydeo.entity;

import com.cydeo.enums.AccountStatus;
import com.cydeo.enums.AccountType;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor //need for jpa
@Data
@Entity
@Table(name = "accounts")

public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;


    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Column(columnDefinition = "TIMESTAMP")
    private Date creationDate;

    private Long userId;

    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;

}
