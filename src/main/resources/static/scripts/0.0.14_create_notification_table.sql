-- table medicine
create table if not exists notification
(
    id       bigint       not null primary key,-- AUTO_INCREMENT
    time  int not null,
    appointment_id bigint,
    constraint fk_notification_id_appointment_id
        foreign key (appointment_id) references appointment (id)
);
