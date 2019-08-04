package com.leovegas.walletservice.Exception;

public class WalletException {
	public static class TransactionException extends BaseException {
		private static final long serialVersionUID = -791078949618669030L;

		public TransactionException(String message) {
			super(message);
		}
	}

	public static class AccountException extends BaseException {
		private static final long serialVersionUID = 7298443074788909701L;

		public AccountException(String message) {
			super(message);
		}
	}

	public static class DataException extends BaseException {
		private static final long serialVersionUID = 7298443074788909701L;

		public DataException(String message) {
			super(message);
		}
	}

}
