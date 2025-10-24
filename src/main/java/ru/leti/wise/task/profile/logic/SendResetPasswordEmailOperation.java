package ru.leti.wise.task.profile.logic;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


import ru.leti.wise.task.profile.error.BusinessException;
import ru.leti.wise.task.profile.error.ErrorCode;
import ru.leti.wise.task.profile.ProfileGrpc.SendResetPasswordEmailRequest;
import ru.leti.wise.task.profile.model.PasswordRecoveryEntity;
import ru.leti.wise.task.profile.model.ProfileEntity;
import ru.leti.wise.task.profile.notification.MailSender;
import ru.leti.wise.task.profile.repository.PasswordRecoveryRepository;
import ru.leti.wise.task.profile.repository.ProfileRepository;
import ru.leti.wise.task.profile.validation.ProfileValidator;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SendResetPasswordEmailOperation {
    private final String RECOVERY_LINK = "https://wisetask.ru/profile/reset_password?token=";

    private final MailSender mailSender;
    private final ProfileRepository profileRepository;
    private final ProfileValidator profileValidator;
    private final PasswordRecoveryRepository passwordRecoveryRepository;


    private final String emailSubject = "Ваша ссылка для восстановления пароля";

    private String createEmailText(String recoveryToken) {
        return "Тема: Восстановление доступа к вашему аккаунту\n\n" +
               "Здравствуйте!\n\n" +
               "Вы запросили сброс пароля для wise-task.\n\n" +
               "Для установки нового пароля перейдите по ссылке:\n" +
               RECOVERY_LINK + recoveryToken + "\n\n" +
               "Ссылка будет активна в течение 10 минут.\n" +
               "Если вы не запрашивали сброс пароля, проигнорируйте это письмо.\n\n" +
               "С уважением,\n" +
               "Команда wise-task";
    }


    public void activate(SendResetPasswordEmailRequest request) {
        ProfileEntity profile = profileValidator.checkEmailExistence(request.getEmail());

        passwordRecoveryRepository.deleteAllByEmail(profile.getEmail());

        UUID recoveryToken = UUID.randomUUID();
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(10);

        PasswordRecoveryEntity passwordRecoveryEntity = new PasswordRecoveryEntity();
        passwordRecoveryEntity.setEmail(request.getEmail());
        passwordRecoveryEntity.setRecoveryToken(recoveryToken);
        passwordRecoveryEntity.setExpiresAt(Timestamp.valueOf(expiresAt));
        passwordRecoveryRepository.save(passwordRecoveryEntity);


        mailSender.sendEmail(profile.getEmail(), emailSubject, createEmailText(recoveryToken.toString()));
    }
}
