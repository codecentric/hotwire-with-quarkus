package de.codecentric.common.logging;

import org.jboss.logging.Logger;

/**
 * Implementation of {@link ILogger} based on {@link org.jboss.logging.Logger}.
 *
 * @author Felix Riess, codecentric AG
 * @since 26 Aug 2022
 */
public class JbossLogger implements ILogger {

    private final Logger LOG;

    JbossLogger(Class<?> clazz) {
        LOG = Logger.getLogger(clazz);
    }

    private boolean isEnabled(Logger.Level level) {
        return LOG.isEnabled(level);
    }

    @Override
    public boolean isTraceEnabled() {
        return isEnabled(Logger.Level.TRACE);
    }

    @Override
    public boolean isDebugEnabled() {
        return isEnabled(Logger.Level.DEBUG);
    }

    @Override
    public void trace(String format, Object... args) {
        if (isTraceEnabled()) {
            LOG.trace(format(format, args));
        }
    }

    @Override
    public void debug(String format, Object... args) {
        if (isDebugEnabled()) {
            LOG.debug(format(format, args));
        }
    }

    @Override
    public void info(String format, Object... args) {
        if (LOG.isEnabled(Logger.Level.INFO)) {
            LOG.info(format(format, args));
        }
    }

    @Override
    public void warn(String format, Object... args) {
        if (LOG.isEnabled(Logger.Level.WARN)) {
            LOG.warn(format(format, args));
        }
    }

    @Override
    public void error(String format, Object... args) {
        if (LOG.isEnabled(Logger.Level.ERROR)) {
            LOG.error(format(format, args));
        }
    }

    @Override
    public void fatal(String format, Object... args) {
        if (LOG.isEnabled(Logger.Level.FATAL)) {
            LOG.fatal(format(format, args));
        }
    }
}
