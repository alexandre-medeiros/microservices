package com.himax.ead.authuser.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProblemDetail {

    private static final URI BLANK_TYPE = URI.create("about:blank");


    private URI type = BLANK_TYPE;

    @Nullable
    private String title;

    private int status;

    @Nullable
    private String detail;

    @Nullable
    private URI instance;

    @Nullable
    private Map<String, Object> properties;

    protected ProblemDetail(int rawStatusCode) {
        this.status = rawStatusCode;
    }

    /**
     * Copy constructor that a subclass can use to re-create and extend a
     * {@code ProblemDetail} with additional properties.
     */
    protected ProblemDetail(ProblemDetail other) {
        this.type = other.type;
        this.title = other.title;
        this.status = other.status;
        this.detail = other.detail;
        this.instance = other.instance;
        this.properties = (other.properties != null ? new LinkedHashMap<>(other.properties) : null);
    }

    /**
     * No-arg constructor, for deserialization.
     */
    protected ProblemDetail() {
    }


    /**
     * Setter for the {@link #getType() problem type}.
     * <p>By default, this is {@link #BLANK_TYPE}.
     * @param type the problem type
     */
    public void setType(URI type) {
        Assert.notNull(type, "'type' is required");
        this.type = type;
    }

    /**
     * Return the configured {@link #setType(URI) problem type}.
     */
    public URI getType() {
        return this.type;
    }

    /**
     * Setter for the {@link #getTitle() problem title}.
     * <p>By default, if not explicitly set and the status is well-known, this
     * is sourced from the {@link HttpStatus#getReasonPhrase()}.
     * @param title the problem title
     */
    public void setTitle(@Nullable String title) {
        this.title = title;
    }

    /**
     * Return the configured {@link #setTitle(String) problem title}.
     */
    @Nullable
    public String getTitle() {
        if (this.title == null) {
            HttpStatus httpStatus = HttpStatus.resolve(this.status);
            if (httpStatus != null) {
                return httpStatus.getReasonPhrase();
            }
        }
        return this.title;
    }

    /**
     * Setter for the {@link #getStatus() problem status}.
     * @param httpStatus the problem status
     */
    public void setStatus(HttpStatus httpStatus) {
        this.status = httpStatus.value();
    }

    /**
     * Setter for the {@link #getStatus() problem status}.
     * @param status the problem status
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * Return the status associated with the problem, provided either to the
     * constructor or configured via {@link #setStatus(int)}.
     */
    public int getStatus() {
        return this.status;
    }

    /**
     * Setter for the {@link #getDetail() problem detail}.
     * <p>By default, this is not set.
     * @param detail the problem detail
     */
    public void setDetail(@Nullable String detail) {
        this.detail = detail;
    }

    /**
     * Return the configured {@link #setDetail(String) problem detail}.
     */
    @Nullable
    public String getDetail() {
        return this.detail;
    }

    /**
     * Setter for the {@link #getInstance() problem instance}.
     * <p>By default, when {@code ProblemDetail} is returned from an
     * {@code @ExceptionHandler} method, this is initialized to the request path.
     * @param instance the problem instance
     */
    public void setInstance(@Nullable URI instance) {
        this.instance = instance;
    }

    /**
     * Return the configured {@link #setInstance(URI) problem instance}.
     */
    @Nullable
    public URI getInstance() {
        return this.instance;
    }

    public void setProperty(String name, @Nullable Object value) {
        this.properties = (this.properties != null ? this.properties : new LinkedHashMap<>());
        this.properties.put(name, value);
    }

    @Nullable
    public Map<String, Object> getProperties() {
        return this.properties;
    }


    @Override
    public boolean equals(@Nullable Object other) {
        return (this == other || (other instanceof ProblemDetail &&
                getType().equals(getType()) &&
                ObjectUtils.nullSafeEquals(getTitle(), getTitle()) &&
                this.status == status &&
                ObjectUtils.nullSafeEquals(this.detail, detail) &&
                ObjectUtils.nullSafeEquals(this.instance, instance) &&
                ObjectUtils.nullSafeEquals(this.properties, properties)));
    }

    @Override
    public int hashCode() {
        int result = this.type.hashCode();
        result = 31 * result + ObjectUtils.nullSafeHashCode(getTitle());
        result = 31 * result + this.status;
        result = 31 * result + ObjectUtils.nullSafeHashCode(this.detail);
        result = 31 * result + ObjectUtils.nullSafeHashCode(this.instance);
        result = 31 * result + ObjectUtils.nullSafeHashCode(this.properties);
        return result;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[" + initToStringContent() + "]";
    }

    protected String initToStringContent() {
        return "type='" + getType() + "'" +
                ", title='" + getTitle() + "'" +
                ", status=" + getStatus() +
                ", detail='" + getDetail() + "'" +
                ", instance='" + getInstance() + "'" +
                ", properties='" + getProperties() + "'";
    }

    public static ProblemDetail forStatus(HttpStatus status) {
        Assert.notNull(status, "HttpStatusCode is required");
        return forStatus(status.value());
    }

    public static ProblemDetail forStatus(int status) {
        return new ProblemDetail(status);
    }

    public static ProblemDetail forStatusAndDetail(HttpStatus status, String detail) {
        Assert.notNull(status, "HttpStatusCode is required");
        ProblemDetail problemDetail = forStatus(status.value());
        problemDetail.setDetail(detail);
        return problemDetail;
    }

}

