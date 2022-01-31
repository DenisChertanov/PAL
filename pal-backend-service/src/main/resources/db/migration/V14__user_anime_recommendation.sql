create table if not exists user_anime_recommendation
(
    user_id                 uuid,
    anime_id                uuid,
    recommendation_mark     double precision    default 0.0 not null,
    constraint user_anime_recommendation_pk primary key (user_id, anime_id),
    constraint user_id_fk foreign key (user_id) references user_info(user_id) on delete cascade,
    constraint anime_id_fk foreign key (anime_id) references anime(anime_id) on delete cascade
);