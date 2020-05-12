CREATE TABLE IF NOT EXISTS `article_translations` (
    `id` int not null auto_increment
        primary key,
    `article_id` int not null,
    `title` varchar(255) not null,
    `content` longtext not null,
    `locale` char(2) not null,
    foreign key (`article_id`)
        references articles (`id`)
        on delete cascade ,
    foreign key (`locale`)
        references languages (`locale`)
        on delete cascade
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

ALTER TABLE `article_translations` ADD UNIQUE `unique_article_translation_locale`(`article_id`, `locale`);