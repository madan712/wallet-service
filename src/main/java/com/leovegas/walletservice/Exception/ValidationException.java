package com.leovegas.walletservice.Exception;

public class ValidationException {

	public static class NullException extends BaseException {
		private static final long serialVersionUID = -2493940917683853616L;

		public NullException(String message) {
			super(message);
		}
	}

	public static class InvalidException extends BaseException {
		private static final long serialVersionUID = 3535705804276173495L;

		public InvalidException(String message) {
			super(message);
		}
	}

}
