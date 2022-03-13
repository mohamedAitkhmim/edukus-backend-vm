-- table medicine
create table if not exists appointment
(
    id       bigint       not null primary key,-- AUTO_INCREMENT
    service  varchar(255),
    location varchar(255),
    note     varchar(255),
    date     datetime(6)  not null,
    email    varchar(255) not null,
    constraint fk_appointment_email_user_email
        foreign key (email) references user (email)
);
