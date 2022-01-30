create table if not exists anime_playlist_mapper
(
    anime_playlist_id   uuid,
    anime_id            uuid,
    constraint anime_playlist_mapper_pk primary key (anime_playlist_id, anime_id),
    constraint anime_playlist_id_fk foreign key (anime_playlist_id) references anime_playlist(anime_playlist_id) on delete cascade,
    constraint anime_id_fk foreign key (anime_id) references anime(anime_id) on delete cascade
);