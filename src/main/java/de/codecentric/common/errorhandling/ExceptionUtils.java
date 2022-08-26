package de.codecentric.common.errorhandling;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Provides utility methods for exceptions.
 *
 * @author Felix Riess <felix@felix-riess.de>
 * @since 25 Okt 2021
 */
public final class ExceptionUtils {

    /**
     * A message if no stacktrace is available.
     */
    private static final String NO_STACKTRACE_AVAILABLE = "[NO STACKTRACE AVAILABLE]";

    /**
     * private constructor to hide implicit public one.
     */
    private ExceptionUtils() {

    }

    /**
     * Converts the stacktrace of the provided {@link Throwable} to a {@link String}.
     *
     * @param ex the stacktrace of this {@link Throwable} will be converted.
     * @return the stacktrace as {@link String} or {@link #NO_STACKTRACE_AVAILABLE} if provided {@link Throwable} is {@code null}.
     */
    public static String convertStacktraceToString(final Throwable ex) {
        if (ex != null) {
            final ByteArrayOutputStream stream = new ByteArrayOutputStream();
            ex.printStackTrace(new PrintStream(stream));
            return stream.toString();
        } else {
            return NO_STACKTRACE_AVAILABLE;
        }
    }
}
