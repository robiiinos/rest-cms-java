CREATE TABLE IF NOT EXISTS `articles` (
    `id` int not null auto_increment
        primary key,
    `slug` varchar(255) not null unique
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE INDEX `slug_index` ON `articles` (`slug`);