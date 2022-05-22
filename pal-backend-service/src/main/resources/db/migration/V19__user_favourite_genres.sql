create table if not exists user_favourite_genres
(
    user_id             uuid,
    anime_tag_id        uuid,
    recommendation_mark double precision default 0.0 not null,
    constraint user_favourite_genres_pk primary key (user_id, anime_tag_id),
    constraint user_id_fk foreign key (user_id) references user_info (user_id) on delete cascade,
    constraint anime_tag_id_fk foreign key (anime_tag_id) references anime_tag (anime_tag_id) on delete cascade
);