
alter table measure
    add column measure_sub_type_id bigint ,
    add constraint FK_measure_measure_sub_type_id_measure_sub_type_id
        foreign key (measure_sub_type_id) references measure_sub_type (id);


