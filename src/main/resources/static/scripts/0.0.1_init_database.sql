
insert  into hibernate_sequence(next_val)
values (0);

insert ignore into app_setting(id, label)
values (1,'global');

insert ignore into role(id, code, value, app_setting_id) values
(1,'R01','PATIENT',1),
(2,'R02','DOCTOR',1);

insert ignore into measure_type (id, code, label,min, max, systolic, diastolic, max_ref_post_prandial, max_ref_pre_prandial, min_ref_post_prandial, min_ref_pre_prandial, unit)
values
(1, 'glc', 'glucose sanguin', null, null, null, null, 1.6, 1.2, 0.8, 0.8, 'g/l'),
(2, 'hb1ac', 'HB1Ac', null, 6.5, null, null, null, null, null, null, '%'),
(3, 'tension', 'Tension artérielle',null, null, 130, 80,null, null, null, null, 'mmHg'),
(4, 'imc', 'Indice de la masse corporelle', 18, 25,null, null, null, null, null, null, 'kg/m²'),
(5, 'ldl', 'LDL',null,1, null, null, null, null, null, null, 'g/l'),
(6, 'hdl', 'HDL',0.5,null, null, null, null, null, null, null, 'g/l'),
(7, 'trig', 'Triglycerides',null, 1.5, null, null, null, null, null, null, 'g/l'),
(8, 'prot', 'Protéinurie', null,30, null, null, null, null, null, null, 'mg/24h'),
(9, 'creatinine', 'Créatinine',5, 12, null, null, null, null, null, null, 'mg/l'),
(10, 'clairance', 'Clairance', 15, null, null, null, null, null, null, null, 'ml/mn');

insert into st_prandial(id, code, label)
values
(1, 'prandial', 'prandial'),
(2, 'preprandial', 'preprandial'),
(3, 'postprandial', 'postprandial');