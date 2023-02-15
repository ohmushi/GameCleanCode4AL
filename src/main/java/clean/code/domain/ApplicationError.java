package clean.code.domain;

public record ApplicationError(String message, Object value, Throwable cause) {}