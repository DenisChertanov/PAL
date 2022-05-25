create table if not exists anime_rating
(
    anime_id    uuid primary key,
    week_views  int not null default 0,
    month_views int not null default 0,
    year_views  int not null default 0,
    constraint anime_id_fk foreign key (anime_id) references anime (anime_id) on delete cascade
);