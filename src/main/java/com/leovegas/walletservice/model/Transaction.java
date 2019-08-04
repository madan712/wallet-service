package com.leovegas.walletservice.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Transaction")
public class Transaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "transaction_id")
	private Long transactionId;

	@OneToOne
	@JoinColumn(name = "player_id", nullable = false)
	@JsonBackReference
	private Player player;

	@Column(name = "transaction_key", unique = true)
	private String transactionKey;

	@Column(name = "amount")
	private BigDecimal amount;

	@Column(name = "transaction_type")
	private TransactionType transactionType;

}
