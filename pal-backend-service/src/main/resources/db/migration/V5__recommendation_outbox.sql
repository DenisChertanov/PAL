create table if not exists recommendation_outbox
(
    event_id        uuid    default uuid_generate_v4()  primary key,
    aggregate_id    varchar not null,
    event_type      varchar not null,
    added_time      timestamp not null default current_timestamp,
    sent_time       timestamp,
    payload         jsonb
);