package com.bank.spring.batch.dao;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BankTransactionRepository  extends JpaRepository<BankTransaction, Long> {

}