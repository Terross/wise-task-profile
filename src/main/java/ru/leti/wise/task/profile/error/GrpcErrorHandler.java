package ru.leti.wise.task.profile.error;

import io.grpc.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GrpcErrorHandler {
    public Status processError(BusinessException e) {
        String message = e.getErrorCode().getMessage();
        if(e.getMessage() != null){
            message += " " + e.getMessage();
        }

        return switch (e.getErrorCode()) {
            case PROFILE_NOT_FOUND -> Status.NOT_FOUND.withDescription(message);
            case INVALID_PASSWORD -> Status.UNAUTHENTICATED.withDescription(message);
            case EMAIL_ALREADY_TAKEN -> Status.ALREADY_EXISTS.withDescription(message);
            case UNKNOWN_LINK, EMPTY_FIELDS -> Status.INVALID_ARGUMENT.withDescription(message);
            case MANY_REQUESTS -> Status.RESOURCE_EXHAUSTED.withDescription(message);
            default -> Status.UNKNOWN;
        };
    }
}
