CREATE USER wise_task_profile WITH PASSWORD 'wise_task_profile';
CREATE DATABASE wise_task_profile WITH OWNER = wise_task_profile;
CREATE SCHEMA wise_task_profile;

-- docker run --name profile -e POSTGRES_PASSWORD=wise_task_profile -e POSTGRES_DB=wise_task_profile -e POSTGRES_USER=wise_task_profile -p 5433:5432 -d postgres
