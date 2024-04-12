create table if not exists user
(
    id         INTEGER not null
        primary key autoincrement,
    email      TEXT    not null,
    username   TEXT    not null,
    password   TEXT    not null,
    role       TEXT    not null,
    avatar     TEXT    null,
    ip         TEXT    null,
    register   TEXT    null,
    last_login TEXT    null,
    disabled   INTEGER not null default 0
);

create table if not exists user_property
(
    id      INTEGER not null
        primary key autoincrement,
    name    TEXT    null,
    value   TEXT    null,
    user_id INTEGER null
);

create table if not exists profile
(
    id           INTEGER not null
        primary key autoincrement,
    uuid         TEXT    not null,
    name         TEXT    not null,
    allow_upload TEXT    null,
    user_id      INTEGER not null
);

create table if not exists texture
(
    id         INTEGER not null
        primary key autoincrement,
    type       TEXT    not null,
    hash_path  TEXT    null,
    model      TEXT    not null,
    profile_id INTEGER null
);