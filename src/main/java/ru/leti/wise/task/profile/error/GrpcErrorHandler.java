package ru.leti.wise.task.profile.error;

import io.grpc.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GrpcErrorHandler {
    public Status processError(BusinessException e) {
        return switch (e.getErrorCode()) {
            case PROFILE_NOT_FOUND -> Status.NOT_FOUND;
            case INVALID_PASSWORD -> Status.UNAUTHENTICATED;
            default -> Status.UNKNOWN;
        };
    }
}
