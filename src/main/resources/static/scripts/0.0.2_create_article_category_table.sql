
create table if not exists article_category
(
    id    bigint       not null primary key,
    code  varchar(255) not null unique,
    label varchar(255) not null
);

CREATE INDEX  FK_article_category_article ON article_category (code);

insert into article_category(id, code, label)
values
(1, 'ma', 'Medical articles'),
(2, 'pa', 'Practical advice'),
(3, 'npa', 'Nutrition and physical activity');