package com.bank.spring.batch;

import org.springframework.batch.item.ItemProcessor;

import com.bank.spring.batch.dao.BankTransaction;

import lombok.Getter;

//@Component
public class BankTransactionItemAnalyticsProcessor implements ItemProcessor<BankTransaction, BankTransaction> {
	@Getter
	private double totalDebit;
	@Getter
	private double totalCredit;

	public BankTransaction process(BankTransaction bankTransaction) throws Exception {

		if (bankTransaction.getTrasactionType().equals("D")) {
			totalDebit += bankTransaction.getAmount();
		} else if (bankTransaction.getTrasactionType().equals("C")) {
			totalCredit += bankTransaction.getAmount();

		}
		return bankTransaction;
	}

}
