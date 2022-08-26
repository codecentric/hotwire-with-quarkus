package de.codecentric.common.logging;

import de.codecentric.common.errorhandling.ExceptionUtils;

/**
 * A log4j-like interface for logging.
 *
 * @author Felix Riess <felix@felix-riess.de>
 * @since 25 Okt 2021
 */
public interface ILogger {

    /**
     * Returns a new instance of an {@link ILogger}.
     *
     * @param clazz the {@link Class} for which the logger should be instantiated.
     * @param implementation {@link LoggerImplementation} which should be used.
     * @return a {@link ILogger} for the specified {@link Class}.
     */
    static ILogger getLogger(final Class<?> clazz, final LoggerImplementation implementation) {
        switch (implementation) {
            case JBOSS: return new JbossLogger(clazz);
            case JAVA_UTIL: return new JavaUtilLogger(clazz);
        }
        return new JavaUtilLogger(clazz);
    }

    /**
     * Returns a new instance of an {@link ILogger}.
     *
     * @param clazz the {@link Class} for which the logger should be instantiated.
     * @return a {@link ILogger} for the specified {@link Class}.
     */
    static ILogger getLogger(final Class<?> clazz) {
        return new JbossLogger(clazz);
    }

    enum LoggerImplementation {
        JAVA_UTIL, JBOSS;
    }

    /**
     * @return {@code true} if trace level is enabled, {@code false} otherwise.
     */
    boolean isTraceEnabled();

    /**
     * @return {@code true} if debug level is enabled, {@code false} otherwise.
     */
    boolean isDebugEnabled();

    /**
     * Logs an event of the specified type and associated arguments.
     *
     * @param event the event type as {@link ILogEvent}.
     * @param args  additional arguments for the log message.
     */
    default void log(final ILogEvent event, final Object... args) {
        final String format = event.getId() + ": " + event.getFormat();
        switch (event.getLevel()) {
            case TRACE:
                trace(format, args);
                break;
            case DEBUG:
                debug(format, args);
                break;
            case INFO:
                info(format, args);
                break;
            case WARN:
                warn(format, args);
                break;
            case ERROR:
                error(format, args);
                break;
            case FATAL:
                fatal(format, args);
                break;
        }
    }

    /**
     * Logs a message with the provided format and arguments on {@link LogLevel#TRACE}.
     *
     * @param format the format of the message as {@link String}.
     * @param args   optional additional arguments for the message.
     */
    void trace(final String format, final Object... args);

    /**
     * Logs a message with the provided format and arguments on {@link LogLevel#DEBUG}.
     *
     * @param format the format of the message as {@link String}.
     * @param args   optional additional arguments for the message.
     */
    void debug(final String format, final Object... args);

    /**
     * Logs a message with the provided format and arguments on {@link LogLevel#INFO}.
     *
     * @param format the format of the message as {@link String}.
     * @param args   optional additional arguments for the message.
     */
    void info(final String format, final Object... args);

    /**
     * Logs a message with the provided format and arguments on {@link LogLevel#WARN}.
     *
     * @param format the format of the message as {@link String}.
     * @param args   optional additional arguments for the message.
     */
    void warn(final String format, final Object... args);

    /**
     * Logs a message with the provided format and arguments on {@link LogLevel#ERROR}.
     *
     * @param format the format of the message as {@link String}.
     * @param args   optional additional arguments for the message.
     */
    void error(final String format, final Object... args);

    /**
     * Logs a message with the provided format and arguments on {@link LogLevel#FATAL}.
     *
     * @param format the format of the message as {@link String}.
     * @param args   optional additional arguments for the message.
     */
    void fatal(final String format, final Object... args);

    /**
     * Logs the execution time of an operation in milliseconds on {@link LogLevel#INFO}.
     *
     * @param operationName         the name of the operation which was executed.
     * @param executionTimeInMillis the execution time in milliseconds.
     */
    default void logExecutionTime(final String operationName, final long executionTimeInMillis) {
        info("Execution of '{}' took {} ms", operationName, executionTimeInMillis);
    }

    /**
     * Formats the provided {@link String} (with '{}' as placeholders) with its arguments to a {@link String}.
     *
     * @param format the format of the message with placeholders.
     * @param args   optional arguments with which the placeholders should be filled.
     * @return a formatted {@link String} with placeholders replaced by actual arguments.
     */
    default String format(final String format, final Object... args) {
        if (args.length == 0) {
            return format;
        }
        final StringBuilder logBuilder = new StringBuilder();
        int position = 0;
        for (Object arg : args) {
            int index = format.indexOf("{}", position);
            if (index < 0) {
                break;
            }
            logBuilder.append(format, position, index);
            logBuilder.append(arg == null ? "null" :
                    arg instanceof Throwable ? ExceptionUtils.convertStacktraceToString((Throwable) arg) : arg.toString());
            position = index + 2;
        }
        if (format.length() > position) {
            logBuilder.append(format.substring(position));
        }
        return logBuilder.toString();
    }

    /**
     * Formats the provided {@link ILogEvent} with its arguments to a {@link String}.
     *
     * @param event the {@link ILogEvent} for the message.
     * @param args  the arguments with which placeholders should be filled.
     * @return the formatted {@link String} with the placeholders replaced by actual arguments.
     */
    default String format(final ILogEvent event, final Object... args) {
        return format(event.getFormat(), args);
    }
}
