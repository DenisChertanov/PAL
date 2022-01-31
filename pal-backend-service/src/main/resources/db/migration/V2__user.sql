create table if not exists user_info
(
      user_id       uuid        default uuid_generate_v4()   primary key,
      username      varchar     not null,
      hash_password varchar     not null,
      first_name    varchar     not null,
      last_name     varchar     not null,
      email         varchar     not null,
      phone_number  varchar     not null,
      image_url     varchar     default ''
);