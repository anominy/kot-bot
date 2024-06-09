create table person_table
(
    id      bigint primary key references object_table(id) on update cascade on delete cascade,
    user_id bigint not null unique
);

create table person_info_table
(
    id        bigint primary key references object_table(id) on update cascade on delete cascade,
    person_id bigint not null references person_table(id) on update cascade on delete cascade,
    guild_id  bigint not null
);

create table person_info_role_table
(
    info_id   bigint not null references person_info_table(id) on update cascade on delete cascade,
    role_type int    not null
);
