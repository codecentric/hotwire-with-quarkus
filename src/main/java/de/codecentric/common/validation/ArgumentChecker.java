package de.codecentric.common.validation;

import de.codecentric.common.errorhandling.ErrorCode;
import de.codecentric.common.errorhandling.exception.BusinessException;
import de.codecentric.common.logging.ILogger;
import de.codecentric.common.string.StringUtilities;

/**
 * Utility class to check arguments.
 *
 * @author Felix Riess <felix@felix-riess.de>
 * @since 25 Okt 2021
 */
public final class ArgumentChecker {

    private static final ILogger LOG = ILogger.getLogger(ArgumentChecker.class);

    /**
     * private constructor to hide implicit public one.
     */
    private ArgumentChecker() {

    }

    private static final String MSG_ARGUMENT_NULL = "The argument '{}' must not be null";
    private static final String MSG_ARGUMENT_NULL_OR_EMPTY = "The argument '{}' must not be null or empty";
    private static final String MSG_ARGUMENT_INT_RANGE = "The argument '{}' must be valid: {}<={}<{}";
    private static final String MSG_ARGUMENT_INT_RANGE_UPPER_BOUND_INCLUDED = "The argument '{}' must be valid: {}<={}<={}";

    /**
     * Checks if given argument is not {@code null}.
     *
     * @param arg     the argument to check.
     * @param argName the name of the argument.
     * @param <T>     the type of the argument.
     * @return the checked argument.
     */
    public static <T> T checkNotNull(final T arg, final String argName) {
        if (arg == null) {
            fail(MSG_ARGUMENT_NULL, argName);
        }
        return arg;
    }

    /**
     * Checks if given array is not {@code null} and all it's elements are not {@code null}.
     *
     * @param args    the array argument to check.
     * @param argName the name of the argument.
     */
    public static void checkNotNullElements(final Object[] args, final String argName) {
        if (args == null) {
            fail(MSG_ARGUMENT_NULL, argName);
        } else {
            for (int i = 0; i < args.length; i++) {
                if (args[i] == null) {
                    fail(MSG_ARGUMENT_NULL, argName + "[" + i + "]");
                }
            }
        }
    }

    /**
     * Checks if given {@link Iterable} argument is not {@code null} and all it's elements are not {@code null}.
     *
     * @param args    the {@link Iterable} to check.
     * @param argName the name of the argument.
     */
    public static void checkNotNullElements(final Iterable<?> args, final String argName) {
        if (args == null) {
            fail(MSG_ARGUMENT_NULL, argName);
        } else {
            int i = 0;
            for (Object object : args) {
                if (object == null) {
                    fail(MSG_ARGUMENT_NULL, argName + "[" + i + "]");
                }
            }
        }
    }

    /**
     * Checks if given argument is not {@code null} of an empty string.
     *
     * @param arg     the argument to check.
     * @param argName the name of the argument.
     * @return the checked argument.
     */
    public static String checkNotEmpty(final String arg, final String argName) {
        if (StringUtilities.isNullOrEmpty(arg)) {
            fail(MSG_ARGUMENT_NULL_OR_EMPTY, argName);
        }
        return arg;
    }

    /**
     * Checks if the given argument value lies in the specified interval.
     * Fails if value is less than the lower bound or if value is greater or equal the upper bound.
     *
     * @param lowerBound the lower bound.
     * @param value      the value to check.
     * @param upperBound the upper bound.
     * @param argName    the name of the argument.
     * @return the checked value.
     */
    public static int checkInterval(final int lowerBound, final int value, final int upperBound, final String argName) {
        if ((value < lowerBound) || (value >= upperBound)) {
            fail(MSG_ARGUMENT_INT_RANGE, argName, lowerBound, value, upperBound);
        }
        return value;
    }

    /**
     * Checks if the given argument value lies in the specified interval.
     * Fails if value is less than the lower bound or if value is greater than the upper bound.
     *
     * @param lowerBound the lower bound.
     * @param value      the value to check.
     * @param upperBound the upper bound.
     * @param argName    the name of the argument.
     * @return the checked value.
     */
    public static int checkIntervalUpperBoundIncluded(final int lowerBound, final int value, final int upperBound, final String argName) {
        if ((value < lowerBound) || (value > upperBound)) {
            fail(MSG_ARGUMENT_INT_RANGE_UPPER_BOUND_INCLUDED, argName, lowerBound, value, upperBound);
        }
        return value;
    }

    /**
     * Checks if the given argument value lies in the specified interval.
     * Fails if value is less than the lower bound or if value is greater or equal the upper bound.
     *
     * @param lowerBound the lower bound.
     * @param value      the value to check.
     * @param upperBound the upper bound.
     * @param argName    the name of the argument.
     * @return the checked value.
     */
    public static long checkInterval(final long lowerBound, final long value, final long upperBound, final String argName) {
        if ((value < lowerBound) || (value >= upperBound)) {
            fail(MSG_ARGUMENT_INT_RANGE, argName, lowerBound, value, upperBound);
        }
        return value;
    }

    /**
     * Checks if the given argument value lies in the specified interval.
     * Fails if value is less than the lower bound or if value is greater than the upper bound.
     *
     * @param lowerBound the lower bound.
     * @param value      the value to check.
     * @param upperBound the upper bound.
     * @param argName    the name of the argument.
     * @return the checked value.
     */
    public static long checkIntervalUpperBoundIncluded(final long lowerBound, final long value, final long upperBound, final String argName) {
        if ((value < lowerBound) || (value > upperBound)) {
            fail(MSG_ARGUMENT_INT_RANGE_UPPER_BOUND_INCLUDED, argName, lowerBound, value, upperBound);
        }
        return value;
    }

    /**
     * Checks if the given argument value lies in the specified interval.
     * Fails if value is less than the lower bound or if value is greater or equal the upper bound.
     *
     * @param lowerBound the lower bound.
     * @param value      the value to check.
     * @param upperBound the upper bound.
     * @param argName    the name of the argument.
     * @return the checked value.
     */
    public static double checkInterval(final double lowerBound, final double value, final double upperBound, final String argName) {
        if ((value < lowerBound) || (value >= upperBound)) {
            fail(MSG_ARGUMENT_INT_RANGE, argName, lowerBound, value, upperBound);
        }
        return value;
    }

    /**
     * Checks if the given argument value lies in the specified interval.
     * Fails if value is less than the lower bound or if value is greater than the upper bound.
     *
     * @param lowerBound the lower bound.
     * @param value      the value to check.
     * @param upperBound the upper bound.
     * @param argName    the name of the argument.
     * @return the checked value.
     */
    public static double checkIntervalUpperBoundIncluded(final double lowerBound, final double value, final double upperBound, final String argName) {
        if ((value < lowerBound) || (value > upperBound)) {
            fail(MSG_ARGUMENT_INT_RANGE_UPPER_BOUND_INCLUDED, argName, lowerBound, value, upperBound);
        }
        return value;
    }

    /**
     * Check that the provided condition is {@code true}.
     *
     * @param condition     the condition to check.
     * @param exceptionText the text for the exception.
     * @param args          additional arguments for the exception.
     */
    public static void checkIsTrue(final boolean condition, final String exceptionText, final Object... args) {
        if (!condition) {
            fail(exceptionText, args);
        }
    }

    /**
     * Makes the argument check fail with the provided text. This method is used by all checks for failing.
     *
     * @param exceptionText the text message for the exception.
     * @param args          additional arguments for the text message.
     * @throws BusinessException with {@link ErrorCode#INVALID_ARGUMENT}.
     */
    private static void fail(final String exceptionText, final Object... args) {
        final String errorMessage = LOG.format(exceptionText, args);
        LOG.warn(errorMessage);
        throw new BusinessException(ErrorCode.INVALID_ARGUMENT, errorMessage);
    }
}