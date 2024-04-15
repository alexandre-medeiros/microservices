package com.himax.ead.authuser.api.exceptionhandler;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.IgnoredPropertyException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Handle errors using the "Problem Details for HTTP APIs" standardized
 * following RFC 9457 - Problem Details for HTTP APIs.
 *
 * @see <a href="https://www.rfc-editor.org/info/rfc9457">RFC 9457</a>
 */
@Log4j2
@Service
public class ErrorHandler {

    public static final String FIELDS = "fields";
    public static final String INVALID_PARAMS = "invalid params";
    public static final String INVALID_BODY = "Invalid body";

    public ProblemDetail handleException(Exception ex, HttpStatus status, HttpServletRequest servletRequest, MessageSource messageSource) {
        ProblemDetail problemDetail = setupProblemDetail(ex, status, servletRequest, messageSource);
        log.debug("Exception {} problem detail {} trace {}", getErrorName(ex), problemDetail.toString(), ex.toString());
        return problemDetail;
    }

    private ProblemDetail setupProblemDetail(Exception ex, HttpStatus status, HttpServletRequest servletRequest, MessageSource messageSource) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(getHttpStatus(ex));
        setTitleAndDetail(problemDetail, ex, status);

        if (hasFields(ex)) {
            problemDetail.setProperty(FIELDS, handleFields(ex, messageSource));
            problemDetail.setDetail(INVALID_PARAMS);
        }

        /*
         * This url could be created in application to provide human-readable documentation for the
         * problem type. If not, its value is assumed to be "about:blank".
         * Remove this url defining null if url will not be created
         */
        String url = servletRequest.getRequestURL().toString().concat(("/error"));
        problemDetail.setType(URI.create(url));

        return problemDetail;
    }

    private void setTitleAndDetail(ProblemDetail problemDetail, Exception ex, HttpStatus status) {
        if (getRootCause(ex) instanceof JsonParseException) {
            problemDetail.setTitle(INVALID_BODY);
            problemDetail.setDetail(getJsonParseExceptionMessage(ex));
            return;
        }

        if (getRootCause(ex) instanceof InvalidFormatException) {
            problemDetail.setTitle(INVALID_BODY);
            problemDetail.setDetail(getInvalidFormatExceptionMessage(ex));
            return;
        }

        if (getRootCause(ex) instanceof UnrecognizedPropertyException) {
            problemDetail.setTitle(INVALID_BODY);
            problemDetail.setDetail(getUnrecognizedPropertyExceptionMessage(ex));
            return;
        }

        if (getRootCause(ex) instanceof IgnoredPropertyException) {
            problemDetail.setTitle(INVALID_BODY);
            problemDetail.setDetail(getIgnoredPropertyExceptionMessage(ex));
            return;
        }

        if (ex instanceof ResourceAccessException) {
            problemDetail.setDetail("The server is not responding ");
            return;
        }

        if (ex instanceof MethodArgumentTypeMismatchException) {
            problemDetail.setTitle(INVALID_BODY);
            problemDetail.setDetail(getMethodArgumentTypeMismatchExceptionMessage(ex));
            return;
        }

        problemDetail.setTitle(status.getReasonPhrase());
        problemDetail.setDetail(ex.getMessage());
    }

    private boolean hasFields(Exception ex) {
        return ex instanceof MethodArgumentNotValidException;
    }

    public HttpStatus getHttpStatus(Exception ex) {
        return switch (getErrorName(ex)) {
            case "ChildNotFoundException", "BusinessException", "UnrecognizedPropertyException", "IgnoredPropertyException" ->
                    HttpStatus.BAD_REQUEST;
            case "EntityInUseException", "AlreadyExistsException", "DataIntegrityViolationException" ->
                    HttpStatus.CONFLICT;
            case "EntityNotFoundException" -> HttpStatus.NOT_FOUND;
            case "SocketTimeoutException" -> HttpStatus.GATEWAY_TIMEOUT;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }

    private Map<String, String> handleFields(Exception ex, MessageSource messageSource) {
        MethodArgumentNotValidException error = (MethodArgumentNotValidException) ex;
        /*
         * List of invalid params and theirs error messages customized at
         * messages.properties file
         */
        return error
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(FieldError::getField, e -> messageSource.getMessage(e, LocaleContextHolder.getLocale())));
    }

    private String getErrorName(Exception exception) {
        Throwable rootCause = ExceptionUtils.getRootCause(exception);
        return rootCause.getClass().getSimpleName();
    }

    private Throwable getRootCause(Exception exception) {
        return ExceptionUtils.getRootCause(exception);
    }

    private String getInvalidFormatExceptionMessage(Exception ex) {
        InvalidFormatException error = (InvalidFormatException) getRootCause(ex);
        String property = error.getPath().stream()
                .map(JsonMappingException.Reference::getFieldName)
                .collect(Collectors.joining("."));

        String value = error.getValue().toString();
        String type = error.getTargetType().getSimpleName();
        return String.format("The property '%s' received the value '%s' that is invalid. Send a compatible value with '%s'", property, value, type);
    }

    private String getJsonParseExceptionMessage(Exception ex) {
        JsonParseException error = (JsonParseException) getRootCause(ex);
        return error.getMessage();
    }

    private String getUnrecognizedPropertyExceptionMessage(Exception ex) {
        UnrecognizedPropertyException error = (UnrecognizedPropertyException) getRootCause(ex);
        return error.getMessage()
                .split("\\(")[0]
                .replace("\"", "'");
    }

    private String getIgnoredPropertyExceptionMessage(Exception ex) {
        IgnoredPropertyException error = (IgnoredPropertyException) getRootCause(ex);
        return error.getMessage()
                .split("\\(")[0]
                .replace("\"", "'");
    }

    private String getMethodArgumentTypeMismatchExceptionMessage(Exception ex) {
        MethodArgumentTypeMismatchException error = (MethodArgumentTypeMismatchException) ex;
        String parameter = error.getName();
        String value = (String) error.getValue();
        return String.format("The path parameter '%s' received the value '%s' with invalid type. The correct type is Long.", parameter, value);
    }

}
