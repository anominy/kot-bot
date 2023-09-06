create table post_table
(
    id          bigint primary key references object_table(id) on update cascade on delete cascade,
    guild_id    bigint  not null,
    message_id  bigint,
    content     text,
    is_reviewed boolean not null default false
);

create table person_post_table
(
    person_id bigint not null references person_table(id) on update cascade on delete cascade,
    post_id   bigint not null references post_table(id) on update cascade on delete cascade
);

create table post_image_table
(
    post_id  bigint not null references post_table(id) on update cascade on delete cascade,
    image_id bigint not null references image_table(id) on update cascade on delete restrict
);
