create table webhook_table
(
    id            bigint primary key references object_table(id) on update cascade on delete cascade,
    guild_id      bigint  not null,
    channel_id    bigint  not null,
    webhook_id    bigint  not null unique,
    webhook_token text    not null unique,
    is_enabled    boolean not null default true
);

create table suggest_webhook_table
(
    id bigint primary key references webhook_table(id) on update cascade on delete cascade
);

create table publish_webhook_table
(
    id bigint primary key references webhook_table(id) on update cascade on delete cascade
);
