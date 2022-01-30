create table if not exists anime
(
    anime_id        uuid    default uuid_generate_v4()  primary key,
    image_url       varchar,
    title           varchar,
    mark            double precision default 0.0 not null,
    anime_state_id  uuid    not null,
    year            integer not null,
    studio          varchar not null,
    director        varchar not null,
    anime_type_id   uuid    not null,
    episodes        integer default 0   not null,
    description     text    not null,
    constraint anime_state_id_fk foreign key (anime_state_id) references anime_state(anime_state_id) on delete cascade,
    constraint anime_type_id_fk foreign key (anime_type_id) references anime_type(anime_type_id) on delete cascade
);