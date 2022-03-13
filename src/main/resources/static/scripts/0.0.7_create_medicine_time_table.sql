-- table dose
create table if not exists medicine_time
(

    id      bigint,
    time datetime(6) ,
    constraint Pk_medicine_time_id
        primary key (id, time),
    constraint fk_medicine_time_id_medicine_id
        foreign key (id) references medicine (id)
);