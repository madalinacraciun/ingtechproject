/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

CREATE DATABASE IF NOT EXISTS `project` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `project`;

CREATE TABLE IF NOT EXISTS `invalid_tokens` (
  `id_invalid_token` int NOT NULL AUTO_INCREMENT,
  `token` varchar(256) NOT NULL,
  PRIMARY KEY (`id_invalid_token`),
  INDEX `idx_token` (`token`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
  `email` varchar(50) DEFAULT NULL,
  `age` int DEFAULT NULL,
  `country` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`user_id`, `username`, `password`, `email`, `age`, `country`) VALUES
	(2, 'alabala', '$2a$10$CTRP5tNm9c0db/PYz1k1XO1D/ColcJ1rwyBwKhPerTVY4WtLFwshK', 'alabala@gmail.com', 18, 'Italy'),
	(3, 'johnsnow', '$2a$10$o4yksXj.M/hf20liczhDzeA7NRnprhmpfeuOLqMIQxlrGe1q6cBhG', 'johnsnow@gmail.com', 10, 'Romania'),
	(5, 'madalinacraciun', '$2a$10$5Jpb7WWqugaXvHKYrqFykejqaLyvlmqADZ/dYSW6BKb8GFq0iWdeW', 'madalinacraciun97@gmail.com', 24, 'Romania');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
