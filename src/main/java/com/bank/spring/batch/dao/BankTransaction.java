package com.bank.spring.batch.dao;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data

public class BankTransaction {

	private Long id;
	private Long accountID;
	private Date transactionDate;
	@Transient
	private String strTransactionDate;
	private String trasactionType;
	private double amount;
}
