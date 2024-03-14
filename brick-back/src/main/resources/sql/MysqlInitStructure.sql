create table if not exists user
(
    id         int          not null
        primary key auto_increment,
    email      varchar(255) not null,
    username   varchar(255) not null,
    password   varchar(255) not null,
    avatar     varchar(255) null,
    ip         varchar(255) null,
    register   varchar(255) null,
    last_login varchar(255) null
)
    row_format = DYNAMIC;

create table if not exists user_property
(
    id      int          not null
        primary key auto_increment,
    name    varchar(255) null,
    value   varchar(255) null,
    user_id int          null
)
    row_format = DYNAMIC;

create table if not exists profile
(
    id           int          not null
        primary key auto_increment,
    uuid         varchar(255) not null,
    name         varchar(255) not null,
    allow_upload varchar(255) null,
    user_id      int          not null
)
    row_format = DYNAMIC;

create table if not exists texture
(
    id         int          not null
        primary key auto_increment,
    type       varchar(255) not null,
    hash_path  varchar(255) null,
    model      varchar(255) not null,
    profile_id int          null
)
    row_format = DYNAMIC;