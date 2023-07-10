CREATE TABLE task
(
    id        uuid primary key,
    details   varchar not null,
    completed boolean
);