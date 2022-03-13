create table if not exists measure_sub_type
(
    id bigint not null
        primary key,
    code varchar(255) not null,
    label varchar(255) not null,
    unit varchar(255) not null,
    measure_type_id bigint not null,
    constraint FK_measure_sub_type_id_measure_type_id
        foreign key (measure_type_id) references measure_type (id)
);