create table if not exists anime_tag_mapper
(
    anime_id        uuid,
    anime_tag_id    uuid,
    constraint anime_tag_mapper_pk primary key (anime_id, anime_tag_id),
    constraint anime_id_fk foreign key (anime_id) references anime(anime_id) on delete cascade,
    constraint anime_tag_id_fk foreign key (anime_tag_id) references anime_tag(anime_tag_id) on delete cascade
);