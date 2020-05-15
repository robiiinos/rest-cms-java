CREATE TABLE IF NOT EXISTS `users` (
    `id` int not null auto_increment
        primary key,
    `firstName` varchar(255) not null,
    `lastName` varchar(255) not null,
    `username` varchar(64) not null unique,
    `password` text not null
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;