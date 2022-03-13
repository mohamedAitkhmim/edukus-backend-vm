create table if not exists app_setting
(
    id    bigint       not null
        primary key,
    label varchar(255) null
);

create table if not exists st_prandial
(
    id    bigint       not null primary key,-- AUTO_INCREMENT
    code  varchar(255) not null unique,
    label varchar(255) not null
);

CREATE INDEX  FK_st_prandial_medicine ON st_prandial (code);

create table if not exists hibernate_sequence
(
    next_val bigint null
);

create table if not exists measure_type
(
    id                    bigint       not null
        primary key,
    code                  varchar(255) not null,
    diastolic             float        null,
    label                 varchar(255) not null,
    max                   float        null,
    max_ref_post_prandial float        null,
    max_ref_pre_prandial  float        null,
    min                   float        null,
    min_ref_post_prandial float        null,
    min_ref_pre_prandial  float        null,
    systolic              float        null,
    unit                  varchar(255) not null
);

create table if not exists role
(
    id             bigint       not null
        primary key,
    code           varchar(255) not null,
    value          varchar(255) not null,
    app_setting_id bigint       null,
    constraint FK79qpy0sf2jfbu96r8mi43twkv
        foreign key (app_setting_id) references app_setting (id)
);

create table if not exists user
(
    email   varchar(255) not null
        primary key,
    user_id varchar(255) not null unique,
    role_id bigint       not null,
    constraint UK_a3imlf41l37utmxiquukk8ajc
        unique (user_id),
    constraint FKn82ha3ccdebhokx3a8fgdqeyy
        foreign key (role_id) references role (id)
);

create table if not exists association
(
    id      bigint       not null
        primary key,
    status  varchar(255) null,
    doctor  varchar(255) null,
    patient varchar(255) null,
    constraint FKis7ylkjp4aopwmn9w1h1iru84
        foreign key (patient) references user (email),
    constraint FKlmfpkpa9ut1bg017ge4u7puig
        foreign key (doctor) references user (email)
);

create table if not exists measure
(
    id              bigint       not null
        primary key,
    date_time       datetime(6)  not null,
    prandial        varchar(255) null,
    reason          varchar(255) null,
    value           double       not null,
    measure_type_id bigint       not null,
    email           varchar(255) not null,
    constraint FK2merkfyt7udl8y3g0hsip3rdm
        foreign key (measure_type_id) references measure_type (id),
    constraint FK2y5trxxt05w554tudhc7k0fnf
        foreign key (email) references user (email),
    constraint FK2y5trdssd5wfdfsudhc7k0fnf
        foreign key (prandial) references st_prandial (code)
);

create table if not exists registration
(
    id                          bigint       not null
        primary key,
    registration_challenge      varchar(255) null,
    registration_challenge_date datetime(6)  null,
    token_hash                  varchar(255) null,
    email                       varchar(255) not null,
    constraint FK4wl7pkealdw5d1pt6ofg9g77t
        foreign key (email) references user (email)
);

create table if not exists user_file
(
    id     bigint       not null
        primary key,
    active bit          not null,
    name   varchar(255) null,
    email  varchar(255) not null,
    constraint FKjfyq30ns8axeqbb4nub1346e6
        foreign key (email) references user (email)
);

create table if not exists user_profile
(
    id               bigint       not null
        primary key,
    address          varchar(255) null,
    birth_date       datetime(6)  null,
    description      varchar(255) null,
    diabetes_type    varchar(255) null,
    education        varchar(255) null,
    email            varchar(255) not null,
    experience       varchar(255) null,
    full_name        varchar(255) null,
    gender           varchar(255) null,
    infection_date   datetime(6)  null,
    length           varchar(255) null,
    phone_number     varchar(255) null,
    spoken_languages varchar(255) null,
    weight           varchar(255) null,
    constraint FK97845ptm4fqko9i7n2ftq45vd
        foreign key (email) references user (email)
);

