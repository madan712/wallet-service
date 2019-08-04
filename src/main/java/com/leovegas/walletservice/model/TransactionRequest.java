package com.leovegas.walletservice.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {
	private Long playerId;
	private String transactionKey;
	private BigDecimal amount;
	private TransactionType transactionType;

	public void negateAmount() {
		amount = amount.negate();
	}
}
