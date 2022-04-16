alter table anime
    add column if not exists
        season varchar not null default '';

alter table anime
    add column if not exists
        age_rating varchar not null default '';

alter table anime
    add column if not exists
        episode_duration varchar not null default '';

alter table anime
    add column if not exists
        voice varchar not null default '';