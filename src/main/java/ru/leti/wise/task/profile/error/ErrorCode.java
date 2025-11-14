package ru.leti.wise.task.profile.error;

import lombok.Getter;

@Getter
public enum ErrorCode {
    PROFILE_NOT_FOUND("Профиль не найден."),
    INVALID_PASSWORD("Неправильный пароль."),
    EMAIL_ALREADY_TAKEN("Пользователь с данной электронной почтой уже существует."),
    UNKNOWN_LINK("Некорректный токен."),
    EMPTY_FIELDS("Не все поля заполнены."),
    MANY_REQUESTS("Слишком много запросов.");
    final String message;

    ErrorCode(String message) {
        this.message = message;
    }
}
