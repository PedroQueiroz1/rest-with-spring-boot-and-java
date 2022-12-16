CREATE TABLE IF NOT EXISTS `books` (
`id` bigint(20) NOT NULL AUTO_INCREMENT,
`title` varchar(80) NOT NULL,
`theme` varchar(30) NOT NULL,
`author` varchar(80) NOT NULL,
`pageQuantity` int NOT NULL,
`difficultyToRead` tinyint NOT NULL,
PRIMARY KEY (id)
);