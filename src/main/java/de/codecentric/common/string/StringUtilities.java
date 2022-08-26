package de.codecentric.common.string;

/**
 * A simple utility class for {@link String} operations.
 *
 * @author Felix Riess <felix@felix-riess.de>
 * @since 25 Okt 2021
 */
public final class StringUtilities {

    // private constructor to hide implicit public one.
    private StringUtilities() {

    }

    /**
     * Check if provided {@link String} is {@code null} or empty.
     *
     * @param str the {@link String} to be checked.
     * @return {@code true} if provided string is {@code null} or empty, {@code false} otherwise.
     */
    public static boolean isNullOrEmpty(final String str) {
        return (str == null) || (str.isEmpty());
    }
}
