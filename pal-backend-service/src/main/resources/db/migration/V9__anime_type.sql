create table if not exists anime_type
(
    anime_type_id   uuid    default uuid_generate_v4()  primary key,
    type            varchar unique not null
);

insert into anime_type(type) values
('Сериал'),
('Полнометражный фильм'),
('Короткометражный фильм'),
('OVA'),
('Special'),
('Малометражный сериал'),
('ONA');