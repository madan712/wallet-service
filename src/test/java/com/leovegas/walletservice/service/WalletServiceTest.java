package com.leovegas.walletservice.service;

import static com.leovegas.walletservice.util.Constant.INSUFFICIENT_FUND;
import static com.leovegas.walletservice.util.Constant.INVALID_PLAYER;
import static com.leovegas.walletservice.util.Constant.INVALID_TRANSACTION_AMOUNT;
import static com.leovegas.walletservice.util.Constant.INVALID_TRANSACTION_KEY;
import static com.leovegas.walletservice.util.Constant.NOT_ABLE_TO_UPDATE_ACCOUNT;
import static com.leovegas.walletservice.util.Constant.PLAYER_ID;
import static com.leovegas.walletservice.util.Constant.PLAYER_NAME;
import static com.leovegas.walletservice.util.Constant.TRANSACTION_ID;
import static com.leovegas.walletservice.util.Constant.TRANSACTION_KEY;
import static com.leovegas.walletservice.util.Constant.TRANSACTION_REQUEST_NULL_MESSAGE;
import static com.leovegas.walletservice.util.Constant.TRANSACTION_SUCCESS_MESSAGE;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

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

@RunWith(MockitoJUnitRunner.class)
public class WalletServiceTest {

	@InjectMocks
	WalletService walletService;

	@Mock
	PlayerRepository playerRepository;

	@Mock
	TransactionRepository transactionRepository;

	@Mock
	AccountRepository accountRepository;

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Test
	public void testTransactWhenNullTransactionRequestThenThrowNullException()
			throws NullException, InvalidException, TransactionException, AccountException {
		expectedException.expect(ValidationException.NullException.class);

		expectedException.expectMessage(TRANSACTION_REQUEST_NULL_MESSAGE);
		walletService.transact(null);
	}

	@Test
	public void testTransactWhenAmountZeroThenThrowInvalidException()
			throws NullException, InvalidException, TransactionException, AccountException {
		expectedException.expect(ValidationException.InvalidException.class);
		expectedException.expectMessage(INVALID_TRANSACTION_AMOUNT);
		TransactionRequest transactionRequest = getTransactionRequest(TRANSACTION_ID, TRANSACTION_KEY, BigDecimal.ZERO,
				TransactionType.CREDIT);
		walletService.transact(transactionRequest);
	}

	@Test
	public void testTransactWhenInvalidPlayerThenThrowInvalidException()
			throws NullException, InvalidException, TransactionException, AccountException {
		expectedException.expect(ValidationException.InvalidException.class);
		expectedException.expectMessage(INVALID_PLAYER);
		TransactionRequest transactionRequest = getTransactionRequest(TRANSACTION_ID, TRANSACTION_KEY, BigDecimal.TEN,
				TransactionType.CREDIT);
		walletService.transact(transactionRequest);
	}

	@Test
	public void testTransactWhenInvalidTransactionKeyThenThrowInvalidException()
			throws NullException, InvalidException, TransactionException, AccountException {
		expectedException.expect(ValidationException.InvalidException.class);
		expectedException.expectMessage(INVALID_TRANSACTION_KEY);

		Long playerId = 1L;
		String transactionKey = "123";

		TransactionRequest transactionRequest = getTransactionRequest(playerId, transactionKey, BigDecimal.TEN,
				TransactionType.CREDIT);

		Optional<Player> optionalPlayer = getOptionalPlayer(PLAYER_ID, PLAYER_NAME, null, LocalDateTime.now());

		Player player = optionalPlayer.get();

		when(playerRepository.findById(playerId)).thenReturn(optionalPlayer);

		when(transactionRepository.findByTransactionKey(transactionRequest.getTransactionKey()))
				.thenReturn(getTransaction(1L, player, transactionKey, BigDecimal.TEN, TransactionType.CREDIT));

		walletService.transact(transactionRequest);
	}

	@Test
	public void testTransactWhenInsufficientFundThenThrowInvalidException()
			throws NullException, InvalidException, TransactionException, AccountException {

		expectedException.expect(ValidationException.InvalidException.class);
		expectedException.expectMessage(INSUFFICIENT_FUND);

		TransactionRequest transactionRequest = getTransactionRequest(PLAYER_ID, TRANSACTION_KEY, BigDecimal.TEN,
				TransactionType.DEBIT);

		Optional<Player> optionalPlayer = getOptionalPlayer(PLAYER_ID, PLAYER_NAME, null, LocalDateTime.now());

		Player player = optionalPlayer.get();

		Account account = getAccount(1L, player, BigDecimal.ZERO, LocalDateTime.now());
		player.setAccount(account);

		when(playerRepository.findById(PLAYER_ID)).thenReturn(optionalPlayer);

		walletService.transact(transactionRequest);

	}

	@Test
	public void testTransactWhenValidTransactionAndUnableToUpdateAccountThenThrowAccountException()
			throws NullException, InvalidException, TransactionException, AccountException {

		expectedException.expect(WalletException.AccountException.class);
		expectedException.expectMessage(NOT_ABLE_TO_UPDATE_ACCOUNT);

		TransactionRequest transactionRequest = getTransactionRequest(PLAYER_ID, TRANSACTION_KEY, BigDecimal.TEN,
				TransactionType.DEBIT);

		Optional<Player> optionalPlayer = getOptionalPlayer(PLAYER_ID, PLAYER_NAME, null, LocalDateTime.now());

		Player player = optionalPlayer.get();

		Account account = getAccount(TRANSACTION_ID, player, BigDecimal.TEN, LocalDateTime.now());
		player.setAccount(account);

		when(playerRepository.findById(PLAYER_ID)).thenReturn(optionalPlayer);

		when(transactionRepository.save(Mockito.any(Transaction.class))).thenReturn(getTransaction(TRANSACTION_ID,
				player, TRANSACTION_KEY, BigDecimal.TEN.negate(), TransactionType.DEBIT));

		doThrow(NullPointerException.class).when(accountRepository).save(Mockito.any(Account.class));

		walletService.transact(transactionRequest);

	}

	@Test
	public void testTransactWhenValidTransactionThen()
			throws NullException, InvalidException, TransactionException, AccountException {

		TransactionRequest transactionRequest = getTransactionRequest(PLAYER_ID, TRANSACTION_KEY, BigDecimal.TEN,
				TransactionType.DEBIT);

		Optional<Player> optionalPlayer = getOptionalPlayer(PLAYER_ID, PLAYER_NAME, null, LocalDateTime.now());
		Player player = optionalPlayer.get();

		Account account = getAccount(TRANSACTION_ID, player, BigDecimal.TEN, LocalDateTime.now());
		player.setAccount(account);

		when(playerRepository.findById(PLAYER_ID)).thenReturn(optionalPlayer);
		when(transactionRepository.save(Mockito.any(Transaction.class))).thenReturn(getTransaction(TRANSACTION_ID,
				player, TRANSACTION_KEY, BigDecimal.TEN.negate(), TransactionType.DEBIT));
		when(accountRepository.save(Mockito.any(Account.class))).thenReturn(account);

		TransactionResponse response = walletService.transact(transactionRequest);
		assertEquals(TRANSACTION_ID, response.getTransactionId());
		assertEquals(TRANSACTION_SUCCESS_MESSAGE, response.getMessage());
	}

	private Account getAccount(Long accountId, Player player, BigDecimal balance, LocalDateTime updatedAt) {
		return Account.builder().accountId(accountId).player(player).balance(balance).updatedAt(updatedAt).build();
	}

	private Optional<Player> getOptionalPlayer(Long playerId, String playerName, Account account,
			LocalDateTime createdAt) {
		return Optional.of(getPlayer(playerId, playerName, account, createdAt));
	}

	private Player getPlayer(Long playerId, String playerName, Account account, LocalDateTime createdAt) {
		return Player.builder().playerId(playerId).playerName(playerName).account(account).createdAt(createdAt).build();
	}

	private Transaction getTransaction(Long transactionId, Player player, String transactionKey, BigDecimal amount,
			TransactionType transactionType) {
		return Transaction.builder().transactionId(transactionId).player(player).transactionKey(transactionKey)
				.amount(amount).transactionType(transactionType).build();
	}

	private TransactionRequest getTransactionRequest(Long playerId, String transactionKey, BigDecimal amount,
			TransactionType transactionType) {
		return TransactionRequest.builder().playerId(playerId).transactionKey(transactionKey).amount(amount)
				.transactionType(transactionType).build();
	}
}
