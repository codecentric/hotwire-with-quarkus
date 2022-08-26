package de.codecentric.common.errorhandling.exception;

import de.codecentric.common.errorhandling.ExceptionUtils;
import de.codecentric.common.errorhandling.IErrorCode;
import de.codecentric.common.errorhandling.ReferenceCodeGenerator;
import de.codecentric.common.logging.ILogger;

import java.util.Arrays;

/**
 * Base exception to be extended by each exception.
 *
 * @author Felix Riess <felix@felix-riess.de>
 * @since 25 Okt 2021
 */
abstract class BaseException extends RuntimeException {
    /**
     * generated serialVersionUID.
     */
    private static final long serialVersionUID = -2557785484543377620L;

    /**
     * The {@link ILogger} to be used.
     */
    private static final ILogger LOG = ILogger.getLogger(BaseException.class);
    /**
     * The {@link IErrorCode} of this exception.
     */
    private final IErrorCode errorCode;
    /**
     * The reference code of this exception.
     */
    private final String referenceCode;
    /**
     * The parameters for this exception
     */
    private final String[] parameters;

    /**
     * Constructor.
     *
     * @param errorCode the {@link IErrorCode} of this exception.
     * @param message   the error message of this exception as {@link String}.
     */
    BaseException(final IErrorCode errorCode, final String message) {
        super(getFormattedErrorCode(errorCode) + ": " + message);
        this.errorCode = errorCode;
        this.referenceCode = ReferenceCodeGenerator.generateReferenceCode();
        this.parameters = new String[0];
        LOG.debug("{}: {}", getFormattedErrorCode(errorCode), message);
        LOG.info("Generated reference code for exception with error code '{}' is: '{}'", getFormattedErrorCode(errorCode), this.referenceCode);
    }

    /**
     * Constructor.
     *
     * @param errorCode the {@link IErrorCode} of this exception.
     * @param cause     the {@link Throwable} of this exception.
     */
    BaseException(final IErrorCode errorCode, final Throwable cause) {
        this(errorCode, ExceptionUtils.convertStacktraceToString(cause));
    }

    /**
     * Constructor.
     *
     * @param errorCode the {@link IErrorCode} of this exception.
     * @param message   the error message of this exception as {@link String}.
     * @param cause     the {@link Throwable} of this exception.
     */
    BaseException(final IErrorCode errorCode, final String message, final Throwable cause) {
        this(errorCode, message + System.lineSeparator() + ExceptionUtils.convertStacktraceToString(cause));
    }

    /**
     * Constructor.
     *
     * @param errorCode the {@link IErrorCode} of this exception.
     * @param message   the error message of this exception as {@link String}.
     * @param params    additional parameters of this exception.
     */
    BaseException(final IErrorCode errorCode, final String message, final Object... params) {
        super(getFormattedErrorCode(errorCode) + ": " + message);
        this.errorCode = errorCode;
        this.referenceCode = ReferenceCodeGenerator.generateReferenceCode();
        this.parameters = objectParametersToStringArray(params);

        LOG.debug("{}: {} with parameter [{}]", getFormattedErrorCode(errorCode), message, String.join(",", this.parameters));
        LOG.info("Generated reference code for exception with error code '{}' is: '{}'", getFormattedErrorCode(errorCode), this.referenceCode);
    }

    /**
     * Constructor.
     *
     * @param errorCode the {@link IErrorCode} of this exception.
     * @param cause     the {@link Throwable} of this exception.
     * @param params    additional parameters of this exception.
     */
    BaseException(final IErrorCode errorCode, final Throwable cause, final Object... params) {
        this(errorCode, ExceptionUtils.convertStacktraceToString(cause), params);
    }

    /**
     * Constructor.
     *
     * @param errorCode the {@link IErrorCode} of this exception.
     * @param message   the error message of this exception as {@link String}.
     * @param cause     the {@link Throwable} of this exception.
     * @param params    additional parameters of this exception.
     */
    BaseException(final IErrorCode errorCode, final String message, final Throwable cause, final Object... params) {
        this(errorCode, message + System.lineSeparator() + ExceptionUtils.convertStacktraceToString(cause), params);
    }

    /**
     * Get the {@link IErrorCode} of this exception.
     *
     * @return the {@link IErrorCode} of this exception.
     */
    public IErrorCode getErrorCode() {
        return this.errorCode;
    }

    /**
     * Get the reference code of this exception.
     *
     * @return the reference code.
     */
    public String getReferenceCode() {
        return this.referenceCode;
    }

    /**
     * Get the parameters of this exception.
     *
     * @return the parameters.
     */
    public String[] getParameters() {
        return this.parameters;
    }

    /**
     * Formats the given {@link IErrorCode} to a human readable {@link String}.
     *
     * @param errorCode the {@link IErrorCode} to format.
     * @return the formatted {@link String}.
     */
    private static String getFormattedErrorCode(final IErrorCode errorCode) {
        return "[" + errorCode.getErrorCategory() + "] " + errorCode.getDescription();
    }

    /**
     * Converts provided parameters to a {@link String} array.
     *
     * @param params the parameters to be converted.
     * @return a {@link String} array with all parameter values.
     */
    private static String[] objectParametersToStringArray(final Object... params) {
        return Arrays.stream(params).map(String::valueOf).toArray(String[]::new);
    }
}
