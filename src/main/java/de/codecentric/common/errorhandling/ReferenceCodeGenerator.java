package de.codecentric.common.errorhandling;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Generator of reference codes for exceptions and log messages.
 *
 * @author Felix Riess <felix@felix-riess.de>
 * @since 25 Okt 2021
 */
public final class ReferenceCodeGenerator {
    /**
     * private constructor to hide implicit public one.
     */
    private ReferenceCodeGenerator() {

    }

    /**
     * Generates a random 6 digit reference code.
     *
     * @return the generated reference code.
     */
    public static String generateReferenceCode() {
        return generateReferenceCode(6);
    }

    /**
     * Generates a random reference code with the provided length.
     *
     * @param length the length of the code to be generated ({@code 0 < length <= 9}.
     * @return the generated reference code.
     */
    public static String generateReferenceCode(final int length) {
        final long generated = ThreadLocalRandom.current().nextLong((long) Math.pow(10, length - 1), (long) (Math.pow(10, length) - 1));
        return String.valueOf(generated);
    }

}
