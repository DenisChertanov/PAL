create table if not exists anime_sort
(
    anime_sort_id   uuid    default uuid_generate_v4()  primary key,
    sort            varchar unique not null
);

insert into anime_sort(sort) values
('Рейтингу'),
('Любимым жанрам'),
('Дате выхода'),
('Алфавиту от А до Я'),
('Алфавиту от Я до А');