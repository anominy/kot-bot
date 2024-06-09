create table image_table
(
    id  bigint primary key references object_table(id) on update cascade on delete cascade,
    url text not null
);
