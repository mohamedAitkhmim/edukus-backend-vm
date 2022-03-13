create table if not exists article
(
    id          bigint       not null
        primary key,
    author      varchar(255) not null,
    title       varchar(255) not null,
    description varchar(255) null,
    url_to_image  varchar(255) not null,
    published_at datetime(6)  not null,
    updated_at   datetime(6)  not null,
    email       varchar(255) not null,
    category    varchar(255) not null,
    constraint fk_article_email_user_email
        foreign key (email) references user (email),
    constraint fk_article_category_article_category_code
        foreign key (category) references article_category (code)
);