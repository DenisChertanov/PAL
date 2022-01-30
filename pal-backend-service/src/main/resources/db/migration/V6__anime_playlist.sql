create table if not exists anime_playlist
(
    anime_playlist_id   uuid    default uuid_generate_v4()  primary key,
    user_id             uuid    not null,
    title               varchar default '',
    time_created        timestamp default current_timestamp not null,
    likes               integer default 0 not null,
    constraint anime_playlist_fk foreign key (user_id) references "user"(user_id) on delete cascade
);