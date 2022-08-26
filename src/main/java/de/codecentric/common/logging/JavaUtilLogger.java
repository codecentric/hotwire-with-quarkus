package de.codecentric.common.logging;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementation of {@link ILogger} based on {@link Logger}.
 *
 * @author Felix Riess <felix@felix-riess.de>
 * @since 25 Okt 2021
 */
public class JavaUtilLogger implements ILogger {

    private final Logger logger;

    JavaUtilLogger(final Class<?> clazz) {
        this.logger = Logger.getLogger(clazz.getName());
    }

    /**
     * {@link Level} has no built-in representation of FATAL log level, so we have to extend the existing.
     */
    private static class JavaUtilLoggerLevel extends Level {
        /**
         * generated serialVersionUID.
         */
        private static final long serialVersionUID = -993172905117212180L;
        /**
         * The new FATAL log level.
         */
        private static final Level FATAL = new JavaUtilLoggerLevel("FATAL", Level.SEVERE.intValue() + 100);

        private JavaUtilLoggerLevel(final String name, final int value) {
            super(name, value);
        }
    }

    private boolean isEnabled(final Level level) {
        return this.logger.isLoggable(level);
    }

    @Override
    public boolean isTraceEnabled() {
        return isEnabled(Level.FINEST);
    }

    @Override
    public boolean isDebugEnabled() {
        return isEnabled(Level.FINE);
    }

    @Override
    public void trace(String format, Object... args) {
        if (isTraceEnabled()) {
            this.logger.finest(format(format, args));
        }
    }

    @Override
    public void debug(String format, Object... args) {
        if (isDebugEnabled()) {
            this.logger.fine(format(format, args));
        }
    }

    @Override
    public void info(String format, Object... args) {
        if (isEnabled(Level.INFO)) {
            this.logger.info(format(format, args));
        }
    }

    @Override
    public void warn(String format, Object... args) {
        if (isEnabled(Level.WARNING)) {
            this.logger.warning(format(format, args));
        }
    }

    @Override
    public void error(String format, Object... args) {
        if (isEnabled(Level.SEVERE)) {
            this.logger.severe(format(format, args));
        }
    }

    @Override
    public void fatal(String format, Object... args) {
        if (isEnabled(JavaUtilLoggerLevel.FATAL)) {
            this.logger.log(JavaUtilLoggerLevel.FATAL, format(format, args));
        }
    }
}
