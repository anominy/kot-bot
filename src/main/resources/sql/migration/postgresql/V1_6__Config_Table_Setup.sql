create table config_table
(
    id                 bigint primary key references object_table(id) on update cascade on delete cascade,
    guild_id           bigint not null unique,
    suggest_webhook_id bigint unique references suggest_webhook_table(id) on update cascade on delete restrict,
    publish_webhook_id bigint unique references publish_webhook_table(id) on update cascade on delete restrict
);

create table config_join_role_table
(
    config_id bigint not null references config_table(id) on update cascade on delete cascade,
    role_id   bigint not null unique
);
