alter table user_anime_activity
    add column if not exists
        date_time_watched timestamp;