
insert into app_setting values (1,'global');

insert into role values (1,'R01','PATIENT',1);
insert into role values (2,'R02','DOCTOR',1);

insert into measure_type (id, code, label, max_ref_post_prandial, max_ref_pre_prandial, min_ref_post_prandial, min_ref_pre_prandial, unit) values (1, 'glc', 'glucose sanguin', 1.6, 1.2, 0.8, 0.8, 'g/l');

alter table measure_type add column diastolic float;
alter table measure_type add column max float;
alter table measure_type add column min float;
alter table measure_type add column systolic float;



select * from measure_type



insert into measure_type (id, code, label,min, max, systolic, diastolic, max_ref_post_prandial, max_ref_pre_prandial, min_ref_post_prandial, min_ref_pre_prandial, unit)
values
(1, 'glc', 'glucose sanguin', null, null, null, null, 1.6, 1.2, 0.8, 0.8, 'g/l'),
(2, 'hb1ac', 'HB1Ac', null, 6.5, null, null, null, null, null, null, '%'),
(3, 'tension', 'Tension artérielle',null, null, 130, 80,null, null, null, null, 'mmHg'),
(4, 'imc', 'Indice de la masse corporelle', 18, 25,null, null, null, null, null, null, 'kg/m²'),
(5, 'ldl', 'LDL',null,1, null, null, null, null, null, null, 'g/l'),
(6, 'hdl', 'HDL',0.5,null, null, null, null, null, null, null, 'g/l'),
(7, 'trig', 'Triglycerides',null, 1.5, null, null, null, null, null, null, 'g/l'),
(8, 'prot', 'Protéinurie', null,30, null, null, null, null, null, null, 'mg/24h'),
(9, 'creatinine', 'Créatinine',5, 12, null, null, null, null, null, null, 'mg/dl'),
(10, 'clairance', 'Clairance', 15, null, null, null, null, null, null, null, 'ml/mn'),
(11, 'Ophtalmologique', 'Examen Ophtalmologique',null, null ,null, null, null, null, null, null, null);



/*create table user_profile
(
    id           bigint       not null
        primary key,
    email        varchar(255) null,
    full_name   varchar(255) null,
    length    varchar(255) null,
    weight    varchar(255) null,
    gender    varchar(255) null,
    diabetes_type    varchar(255) null,
    birth_date datetime(6)  null,
    infection_date datetime(6)  null,
    phone_number varchar(255) null,
    constraint FKs0p0a2b5e1ai6lelxehaa6gmv
        foreign key (email) references user (email)
);*/




SET FOREIGN_KEY_CHECKS = 0;
SET GROUP_CONCAT_MAX_LEN=32768;
SET @tables = NULL;
SELECT GROUP_CONCAT('`', table_name, '`') INTO @tables
FROM information_schema.tables
WHERE table_schema = (SELECT DATABASE());
SELECT IFNULL(@tables,'dummy') INTO @tables;

SET @tables = CONCAT('DROP TABLE IF EXISTS ', @tables);
PREPARE stmt FROM @tables;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;
SET FOREIGN_KEY_CHECKS = 1;


create table user_file (id bigint not null, active bit not null, name varchar(255), email varchar(255), primary key (id)) engine=InnoDB
alter table user_file add constraint FKjfyq30ns8axeqbb4nub1346e6 foreign key (email) references user (email)


