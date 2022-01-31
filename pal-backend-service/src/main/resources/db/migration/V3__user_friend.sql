create table if not exists user_friend
(
    user_id     uuid    default uuid_generate_v4(),
    friend_id   uuid    default uuid_generate_v4(),
    constraint user_friend_pk primary key (user_id, friend_id),
    constraint user_id_fk foreign key (user_id) references user_info(user_id) on delete cascade,
    constraint friend_id_fk foreign key (friend_id) references user_info(user_id) on delete cascade
);