package de.codecentric.common.errorhandling.exception;

import de.codecentric.common.errorhandling.IErrorCode;

/**
 * Exception for all business errors.
 *
 * @author Felix Riess <felix@felix-riess.de>
 * @since 25 Okt 2021
 */
public class BusinessException extends BaseException {
    /**
     * generated serialVersionUID.
     */
    private static final long serialVersionUID = -6098907512105654367L;

    /**
     * Generates a new {@link BusinessException}.
     *
     * @param errorCode the {@link IErrorCode} of this exception.
     * @param message   the error message of this exception as {@link String}.
     */
    public BusinessException(final IErrorCode errorCode, final String message) {
        super(errorCode, message);
    }

    /**
     * Generates a new {@link BusinessException}.
     *
     * @param errorCode the {@link IErrorCode} of this exception.
     * @param cause     the {@link Throwable} of this exception.
     */
    public BusinessException(final IErrorCode errorCode, final Throwable cause) {
        super(errorCode, cause);
    }

    /**
     * Generates a new {@link BusinessException}.
     *
     * @param errorCode the {@link IErrorCode} of this exception.
     * @param message   the error message of this exception as {@link String}
     * @param cause     the {@link Throwable} of this exception.
     */
    public BusinessException(final IErrorCode errorCode, final String message, final Throwable cause) {
        super(errorCode, message, cause);
    }

    /**
     * Generates a new {@link BusinessException}.
     *
     * @param errorCode the {@link IErrorCode} of this exception.
     * @param message   the error message of this exception as {@link String}.
     * @param params    additional parameters of this exception.
     */
    public BusinessException(final IErrorCode errorCode, final String message, final Object... params) {
        super(errorCode, message, params);
    }

    /**
     * Generates a new {@link BusinessException}.
     *
     * @param errorCode the {@link IErrorCode} of this exception.
     * @param cause     the {@link Throwable} of this exception.
     * @param params    additional parameters of this exception.
     */
    public BusinessException(final IErrorCode errorCode, final Throwable cause, final Object... params) {
        super(errorCode, cause, params);
    }

    /**
     * Generates a new {@link BusinessException}.
     *
     * @param errorCode the {@link IErrorCode} of this exception.
     * @param message   the error message of this exception as {@link String}.
     * @param cause     the {@link Throwable} of this exception.
     * @param params    additional parameters of this exception.
     */
    public BusinessException(final IErrorCode errorCode, final String message, final Throwable cause, final Object... params) {
        super(errorCode, message, cause, params);
    }
}
