create table if not exists user_statistic
(
    user_id             uuid    default uuid_generate_v4()  primary key,
    anime_spent_hours   double precision not null default 0,
    anime_count         integer not null default 0,
    constraint user_statistic_fk foreign key (user_id) references user_info(user_id) on delete cascade
);