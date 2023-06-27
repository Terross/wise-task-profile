CREATE SCHEMA IF NOT EXISTS wise_task_profile;

CREATE TABLE wise_task_profile.profile  (
    id                  UUID        NOT NULL    PRIMARY KEY,
    email               VARCHAR     NOT NULL,
    profile_password    VARCHAR     NOT NULL,
    first_name          VARCHAR     NOT NULL,
    last_name           VARCHAR     NOT NULL,
    patronymic          VARCHAR,
    profile_role        VARCHAR     NOT NULL

);

CREATE TABLE wise_task_profile.student (
    id                  UUID            NOT NULL PRIMARY KEY,
    student_group       VARCHAR         NOT NULL,
    student_course      INT             NOT NULL,
    profile_id          UUID            NOT NULL,

    CONSTRAINT profile_fk FOREIGN KEY (profile_id) REFERENCES wise_task_profile.profile(id)
);