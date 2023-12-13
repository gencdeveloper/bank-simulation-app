package com.cydeo.entity;

import com.cydeo.dto.AccountDTO;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne
    private Account sender;

    @ManyToOne
    private Account receiver;

    private BigDecimal amount;

    private String message;

    @Column(columnDefinition = "TIMESTAMP")
    private Date createDate;
}
