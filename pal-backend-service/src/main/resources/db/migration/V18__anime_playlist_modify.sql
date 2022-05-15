alter table anime_playlist
    drop column if exists
        likes;

alter table anime_playlist
    add column if not exists
        description varchar default '';

alter table anime_playlist
    add column if not exists
        image_url varchar;

alter table anime_playlist
    add column if not exists
        mark integer not null default 0;

alter table anime_playlist_mapper
    add column if not exists
        sequence integer not null default 0;

create table if not exists anime_playlist_user_mark
(
    anime_playlist_id uuid,
    user_id           uuid,
    mark              integer not null default 0,
    constraint anime_playlist_user_mark_pk primary key (anime_playlist_id, user_id),
    constraint anime_playlist_id_fk foreign key (anime_playlist_id) references anime_playlist (anime_playlist_id) on delete cascade,
    constraint user_id_fk foreign key (user_id) references user_info (user_id) on delete cascade
);