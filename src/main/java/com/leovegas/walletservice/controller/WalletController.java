package com.leovegas.walletservice.controller;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leovegas.walletservice.Exception.ValidationException.InvalidException;
import com.leovegas.walletservice.Exception.ValidationException.NullException;
import com.leovegas.walletservice.Exception.WalletException;
import com.leovegas.walletservice.Exception.WalletException.AccountException;
import com.leovegas.walletservice.Exception.WalletException.TransactionException;
import com.leovegas.walletservice.model.Account;
import com.leovegas.walletservice.model.Player;
import com.leovegas.walletservice.model.Transaction;
import com.leovegas.walletservice.model.TransactionRequest;
import com.leovegas.walletservice.repository.PlayerRepository;
import com.leovegas.walletservice.repository.TransactionRepository;
import com.leovegas.walletservice.service.WalletService;
import com.leovegas.walletservice.util.ResponseUtil;

import lombok.extern.slf4j.Slf4j;

@RequestMapping("/api")
@RestController
@Validated
@Slf4j
public class WalletController {

	@Autowired
	PlayerRepository playerRepository;

	@Autowired
	TransactionRepository transactionRepository;

	@Autowired
	WalletService walletService;

	@PostMapping(path = "/players", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createNewPlayer(@RequestBody Player player) {
		try {
			// Initialize new account
			Account newAccount = Account.builder().balance(BigDecimal.ZERO).player(player).build();
			player.setAccount(newAccount);
			playerRepository.save(player);
			return ResponseUtil.get200OkResponse(player);
		} catch (Exception e) {
			log.error("Exception while creating new player:", e);
			return ResponseUtil.get500InternalServerErrorResponse(e.getMessage());
		}
	}

	@GetMapping(path = "/players")
	public ResponseEntity<?> getAllPlayers() {
		try {
			return ResponseUtil.get200OkResponse(playerRepository.findAll());
		} catch (Exception e) {
			log.error("Exception while fetching player list:", e);
			return ResponseUtil.get500InternalServerErrorResponse(e.getMessage());
		}
	}

	@GetMapping("/players/{playerId}")
	public ResponseEntity<?> getPlayer(@PathVariable Long playerId) throws DataException {
		try {
			Player player = playerRepository.findById(playerId)
					.orElseThrow(() -> new WalletException.DataException("Player not found"));
			return ResponseUtil.get200OkResponse(player);
		} catch (Exception e) {
			log.error("Exception while fetching player:", e);
			return ResponseUtil.get500InternalServerErrorResponse(e.getMessage());
		}
	}

	@GetMapping("/players/{playerId}/balance")
	public ResponseEntity<?> getBalance(@PathVariable Long playerId) throws DataException {
		try {
			BigDecimal balance = playerRepository.findById(playerId)
					.orElseThrow(() -> new WalletException.DataException("Player not found")).getAccount().getBalance();
			return ResponseUtil.get200OkResponse(balance);
		} catch (Exception e) {
			log.error("Exception while fetching balance:", e);
			return ResponseUtil.get500InternalServerErrorResponse(e.getMessage());
		}
	}

	@GetMapping("/players/{playerId}/transactions")
	public ResponseEntity<?> getTransactionHistory(@PathVariable Long playerId) {
		try {
			List<Transaction> transactions = transactionRepository.findByPlayer(playerRepository.findById(playerId)
					.orElseThrow(() -> new WalletException.DataException("Player not found")));
			return ResponseUtil.get200OkResponse(transactions);
		} catch (Exception e) {
			log.error("Exception while fetching transactions:", e);
			return ResponseUtil.get500InternalServerErrorResponse(e.getMessage());
		}
	}

	@PostMapping(path = "/transact", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> transact(@RequestBody TransactionRequest transactionRequest) {
		try {
			return ResponseUtil.get200OkResponse(walletService.transact(transactionRequest));
		} catch (NullException | InvalidException | TransactionException | AccountException e) {
			log.error("Exception while transaction execution:", e);
			return ResponseUtil.get500InternalServerErrorResponse(e.getMessage());
		} catch (Exception e) {
			log.error("Exception while feting transactions:", e);
			return ResponseUtil.get500InternalServerErrorResponse(e.getMessage());
		}
	}

}
