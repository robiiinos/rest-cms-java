CREATE TABLE IF NOT EXISTS `languages` (
    `id` int not null auto_increment,
    `name` varchar(255) not null,
    `locale` char(2) not null,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE INDEX `locale_index` ON `languages` (`locale`);