package com.leovegas.walletservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.leovegas.walletservice.model.Player;
import com.leovegas.walletservice.model.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	List<Transaction> findByPlayer(Player player);

	Transaction findByTransactionKey(String transactionKey);
}
