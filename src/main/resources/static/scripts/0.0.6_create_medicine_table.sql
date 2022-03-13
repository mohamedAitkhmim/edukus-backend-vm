-- table medicine
create table if not exists medicine (
	id              bigint       not null primary key,-- AUTO_INCREMENT
	label 		varchar(255) not null,
	qte     double     not null,
	unit varchar(255)     not null,
    prandial        varchar(255) not null,
    email       varchar(255) not null,
    constraint FK2y5trdsr44wfdfsudhc7k0fnf
        foreign key (prandial) references st_prandial (code),
    constraint fk_medicine_email_user_email
        foreign key (email) references user (email)
);
