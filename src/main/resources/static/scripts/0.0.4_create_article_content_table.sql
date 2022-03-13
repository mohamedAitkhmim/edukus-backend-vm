
create table if not exists article_content
(
    id      bigint primary key,
    content text   not null,
    constraint fk_article_id_user_id
    foreign key (id) references article (id)
);
