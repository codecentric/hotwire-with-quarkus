package de.codecentric.common.logging;

/**
 * Base interface for all log events.
 * The {@link #getFormat()} method specifies the format string of the event, which contains placeholder "{}" for substitution with actual arguments.
 *
 * @author Felix Riess <felix@felix-riess.de>
 * @since 25 Okt 2021
 */
public interface ILogEvent {

    /**
     * @return the {@link LogLevel} of the message.
     */
    LogLevel getLevel();

    /**
     * @return the format of the message.
     */
    String getFormat();

    /**
     * @return the id of the message.
     */
    String getId();
}
