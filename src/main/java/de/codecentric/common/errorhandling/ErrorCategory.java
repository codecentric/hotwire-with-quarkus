package de.codecentric.common.errorhandling;

/**
 * Enum representing different categories of errors.
 *
 * @author Felix Riess <felix@felix-riess.de>
 * @since 25 Okt 2021
 */
public enum ErrorCategory {

    /**
     * {@link ErrorCategory} for all technical errors.
     */
    TECHNICAL("Technical Error"),
    /**
     * {@link ErrorCategory} for all business errors.
     */
    BUSINESS("Business Error"),
    /**
     * {@link ErrorCategory} for all undefined errors.
     */
    UNDEFINED("Undefined Error"),
    ;

    /**
     * The description of this specific {@link ErrorCategory}.
     */
    private final String description;

    ErrorCategory(final String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return this.description;
    }
}
