package com.bank.spring.batch;

import java.text.SimpleDateFormat;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.bank.spring.batch.dao.BankTransaction;

@Component
public class BankTransactionItemProcessor  implements ItemProcessor<BankTransaction	, BankTransaction>{

	
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy-HH:mm");
	@Override
	public BankTransaction process(BankTransaction bankTransaction) throws Exception {
		/*parse String to date*/
		/*faire le traitement sur l'objet Input ,puis reourner un output*/
	bankTransaction.setTransactionDate(dateFormat.parse(bankTransaction.getStrTransactionDate()));
		return bankTransaction;
	}

}
