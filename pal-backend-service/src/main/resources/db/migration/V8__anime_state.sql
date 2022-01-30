create table if not exists anime_state
(
    anime_state_id  uuid    default uuid_generate_v4()  primary key,
    state           varchar unique not null
);

insert into anime_state(state) values
('Вышел'),
('Онгоинг'),
('Анонс');