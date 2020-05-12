CREATE TABLE IF NOT EXISTS `article_translations` (
    `id` int not null auto_increment,
    `parent_id` int not null,
    `title` varchar(255) not null,
    `content` longtext not null,
    `locale` char(2) not null,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`parent_id`)
        REFERENCES articles (`id`)
        ON DELETE CASCADE,
    FOREIGN KEY (`locale`)
        REFERENCES languages (`locale`)
        ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;