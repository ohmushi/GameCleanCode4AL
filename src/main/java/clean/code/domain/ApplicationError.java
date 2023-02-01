package clean.code.domain;

public record ApplicationError(String context, String message, Throwable cause) {}