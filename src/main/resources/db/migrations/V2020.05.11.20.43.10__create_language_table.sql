CREATE TABLE IF NOT EXISTS `languages` (
    `id` int not null auto_increment
        primary key,
    `name` varchar(255) not null,
    `locale` char(2) not null unique
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE INDEX `locale_index` ON `languages` (`locale`);