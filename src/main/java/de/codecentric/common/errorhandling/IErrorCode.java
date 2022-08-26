package de.codecentric.common.errorhandling;

/**
 * An interface to be implemented by all error codes.
 *
 * @author Felix Riess <felix@felix-riess.de>
 * @since 25 Okt 2021
 */
public interface IErrorCode {

    /**
     * Get the {@link ErrorCategory} of this specific error.
     *
     * @return the {@link ErrorCategory}.
     */
    ErrorCategory getErrorCategory();

    /**
     * Get a human readable description of this specific error.
     *
     * @return the description as {@link String}.
     */
    String getDescription();
}
