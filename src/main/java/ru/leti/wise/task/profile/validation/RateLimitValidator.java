package ru.leti.wise.task.profile.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.leti.wise.task.profile.error.BusinessException;
import ru.leti.wise.task.profile.error.ErrorCode;
import ru.leti.wise.task.profile.model.PasswordRecoveryEntity;
import ru.leti.wise.task.profile.repository.PasswordRecoveryRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RateLimitValidator {

    private final PasswordRecoveryRepository passwordRecoveryRepository;
    @Value("${reset-password.send-limit}")
    private Integer sendLimitMinutes;

    public void checkRateLimit(String email) {
        Optional<PasswordRecoveryEntity> lastRequest = passwordRecoveryRepository
                .findFirstByEmailOrderByCreatedAtDesc(email);
        LocalDateTime twoMinutesAgo = LocalDateTime.now().minusMinutes(sendLimitMinutes);
        if (lastRequest.isPresent()) {
            LocalDateTime lastRequestTime = lastRequest.get().getCreatedAt().toLocalDateTime();
            if (lastRequestTime.isAfter(twoMinutesAgo)) {
                long secondsLeft = Duration.between(LocalDateTime.now(), lastRequestTime.plusMinutes(sendLimitMinutes)).getSeconds();
                throw new BusinessException(ErrorCode.MANY_REQUESTS, "Слишком частые запросы. Попробуйте через " + secondsLeft + " секунд.");
            }
        }
    }
}