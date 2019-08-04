package com.leovegas.walletservice.service;

import static com.leovegas.walletservice.util.Constant.INSUFFICIENT_FUND;
import static com.leovegas.walletservice.util.Constant.INVALID_PLAYER;
import static com.leovegas.walletservice.util.Constant.INVALID_TRANSACTION_AMOUNT;
import static com.leovegas.walletservice.util.Constant.INVALID_TRANSACTION_KEY;
import static com.leovegas.walletservice.util.Constant.NOT_ABLE_TO_PROCESS_TRANSACTION;
import static com.leovegas.walletservice.util.Constant.NOT_ABLE_TO_UPDATE_ACCOUNT;
import static com.leovegas.walletservice.util.Constant.TRANSACTION_REQUEST_NULL_MESSAGE;
import static com.leovegas.walletservice.util.Constant.TRANSACTION_SUCCESS_MESSAGE;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.leovegas.walletservice.Exception.ValidationException;
import com.leovegas.walletservice.Exception.ValidationException.InvalidException;
import com.leovegas.walletservice.Exception.ValidationException.NullException;
import com.leovegas.walletservice.Exception.WalletException;
import com.leovegas.walletservice.Exception.WalletException.AccountException;
import com.leovegas.walletservice.Exception.WalletException.TransactionException;
import com.leovegas.walletservice.model.Account;
import com.leovegas.walletservice.model.Player;
import com.leovegas.walletservice.model.Transaction;
import com.leovegas.walletservice.model.TransactionRequest;
import com.leovegas.walletservice.model.TransactionResponse;
import com.leovegas.walletservice.model.TransactionType;
import com.leovegas.walletservice.repository.AccountRepository;
import com.leovegas.walletservice.repository.PlayerRepository;
import com.leovegas.walletservice.repository.TransactionRepository;

@Service
public class WalletService {

	@Autowired
	PlayerRepository playerRepository;

	@Autowired
	TransactionRepository transactionRepository;

	@Autowired
	AccountRepository accountRepository;

	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = { WalletException.TransactionException.class,
			WalletException.AccountException.class })
	public TransactionResponse transact(TransactionRequest transactionRequest)
			throws NullException, InvalidException, TransactionException, AccountException {
		Player player = validator(transactionRequest);

		Transaction transaction = createTransaction(transactionRequest, player);

		updateAccount(transactionRequest, player);

		return getTransactionResponse(transaction, TRANSACTION_SUCCESS_MESSAGE);
	}

	private TransactionResponse getTransactionResponse(Transaction transaction, String message) {
		return TransactionResponse.builder().transactionId(transaction.getTransactionId()).message(message).build();
	}

	private void updateAccount(TransactionRequest transactionRequest, Player player) throws AccountException {
		try {
			Account account = player.getAccount();
			account.updateBalance(transactionRequest.getAmount());
			account.setUpdatedAt(LocalDateTime.now());

			accountRepository.save(account);
		} catch (Exception e) {
			throw new WalletException.AccountException(NOT_ABLE_TO_UPDATE_ACCOUNT);
		}
	}

	private Transaction createTransaction(TransactionRequest transactionRequest, Player player)
			throws TransactionException {
		try {
			if (TransactionType.DEBIT == transactionRequest.getTransactionType()) {
				transactionRequest.negateAmount();
			}

			Transaction transaction = Transaction.builder().player(player)
					.transactionKey(transactionRequest.getTransactionKey()).amount(transactionRequest.getAmount())
					.transactionType(transactionRequest.getTransactionType()).build();

			return transactionRepository.save(transaction);
		} catch (DataIntegrityViolationException e) {
			throw new WalletException.TransactionException(INVALID_TRANSACTION_KEY);
		} catch (Exception e) {
			throw new WalletException.TransactionException(NOT_ABLE_TO_PROCESS_TRANSACTION);
		}
	}

	private Player validator(TransactionRequest transactionRequest) throws NullException, InvalidException {
		if (transactionRequest == null) {
			throw new ValidationException.NullException(TRANSACTION_REQUEST_NULL_MESSAGE);
		}

		if (transactionRequest.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
			throw new ValidationException.InvalidException(INVALID_TRANSACTION_AMOUNT);
		}

		Player player;
		try {
			player = playerRepository.findById(transactionRequest.getPlayerId()).get();
		} catch (Exception e) {
			throw new ValidationException.InvalidException(INVALID_PLAYER);
		}

		Transaction transaction = transactionRepository.findByTransactionKey(transactionRequest.getTransactionKey());
		if (transaction != null) {
			throw new ValidationException.InvalidException(INVALID_TRANSACTION_KEY);
		}

		if (TransactionType.DEBIT == transactionRequest.getTransactionType()
				&& player.getAccount().getBalance().compareTo(transactionRequest.getAmount()) == -1) {

			throw new ValidationException.InvalidException(INSUFFICIENT_FUND);
		}
		return player;
	}
}
