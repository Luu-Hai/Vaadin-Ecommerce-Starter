-- MySQL dump 10.13  Distrib 8.0.39, for Win64 (x86_64)
--
-- Host: localhost    Database: mysql_starter
-- ------------------------------------------------------
-- Server version	9.0.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id` int NOT NULL,
  `name` enum('ADMIN','EMPLOYEE','USER','USER_GOLD','USER_PREMIUM') DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (0,'USER'),(1,'USER_GOLD'),(2,'USER_PREMIUM'),(3,'EMPLOYEE'),(4,'ADMIN');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `score`
--

DROP TABLE IF EXISTS `score`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `score` (
  `last_score` int NOT NULL,
  `max_score` int NOT NULL,
  `score_id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  `created_at` varchar(255) NOT NULL,
  `updated_at` varchar(255) NOT NULL,
  PRIMARY KEY (`score_id`),
  UNIQUE KEY `UKh5v1032alxmvg7bf6hg85pvkk` (`user_id`),
  CONSTRAINT `FKpqss47h2fevnmkh76r14055o0` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `score`
--

LOCK TABLES `score` WRITE;
/*!40000 ALTER TABLE `score` DISABLE KEYS */;
INSERT INTO `score` VALUES (10,90,1,1,'2024-08-11T00:04:44.638419900','2024-08-11T00:04:44.638419900'),(1,1,2,2,'2024-08-11T00:04:44.638419900','2024-08-11T00:04:44.638419900'),(2,20,3,3,'2024-08-11T00:04:44.638419900','2024-08-11T00:04:44.638419900'),(3,12,4,4,'2024-08-11T00:04:44.638419900','2024-08-11T00:04:44.638419900'),(4,9,5,5,'2024-08-11T00:04:44.638419900','2024-08-11T00:04:44.638419900'),(5,56,6,6,'2024-08-11T00:04:44.638419900','2024-08-11T00:04:44.638419900'),(6,9,7,7,'2024-08-11T00:04:44.638419900','2024-08-11T00:04:44.638419900'),(7,34,8,8,'2024-08-11T00:04:44.638419900','2024-08-11T00:04:44.638419900'),(8,9,9,9,'2024-08-11T00:04:44.638419900','2024-08-11T00:04:44.638419900'),(9,9,10,10,'2024-08-11T00:04:44.638419900','2024-08-11T00:04:44.638419900');
/*!40000 ALTER TABLE `score` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `social_accounts`
--

DROP TABLE IF EXISTS `social_accounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `social_accounts` (
  `provider_id` int NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `provider` enum('FACEBOOK','GOOGLE') NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK2uty8llwi3lpahqx7ps5lwolh` (`user_id`),
  CONSTRAINT `FK6rmxxiton5yuvu7ph2hcq2xn7` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `social_accounts`
--

LOCK TABLES `social_accounts` WRITE;
/*!40000 ALTER TABLE `social_accounts` DISABLE KEYS */;
INSERT INTO `social_accounts` VALUES (0,1,1,'hoangdz1604@gmail.com','hoang','GOOGLE'),(1,2,4,'mnhw.0612@gmail.com','nhu','FACEBOOK'),(0,3,5,'doanthibaochau0410@gmail.com','chau','GOOGLE'),(0,4,6,'manhduonglhp4@gmail.com','Duong','GOOGLE'),(0,5,7,'tramsowavy@gmail.com','Tram','GOOGLE');
/*!40000 ALTER TABLE `social_accounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_status`
--

DROP TABLE IF EXISTS `user_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_status` (
  `id` int NOT NULL,
  `name` enum('BANNED','UNVERIFIED','VERIFIED') DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_status`
--

LOCK TABLES `user_status` WRITE;
/*!40000 ALTER TABLE `user_status` DISABLE KEYS */;
INSERT INTO `user_status` VALUES (0,'UNVERIFIED'),(1,'VERIFIED'),(2,'BANNED');
/*!40000 ALTER TABLE `user_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `facebook_id` int DEFAULT NULL,
  `gender` tinyint NOT NULL,
  `google_id` int DEFAULT NULL,
  `role_id` int NOT NULL,
  `status_id` int NOT NULL,
  `subscription` int NOT NULL,
  `user_id` bigint NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `address` varchar(255) NOT NULL,
  `birthday` varchar(255) NOT NULL,
  `created_at` varchar(255) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `updated_at` varchar(255) NOT NULL,
  `avatar_url` mediumblob,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`),
  UNIQUE KEY `UKdu5v5sr43g5bfnji4vb8hg5s3` (`phone`),
  KEY `FKp56c1712k691lhsyewcssf40f` (`role_id`),
  KEY `FK7te4jcygsrgdpp4ivkydots3` (`status_id`),
  CONSTRAINT `FK7te4jcygsrgdpp4ivkydots3` FOREIGN KEY (`status_id`) REFERENCES `user_status` (`id`),
  CONSTRAINT `FKp56c1712k691lhsyewcssf40f` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`),
  CONSTRAINT `users_chk_1` CHECK ((`gender` between 0 and 3))
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (0,0,1,4,0,1,1,'hoang','luu','Duong Vo Van Hat, Ho Chi Minh City','1999-07-01','2024-08-11T00:04:44.205156600','hoangdz1604@gmail.com','$31$16$uuNO3qvmPsHhOYv15xI1ijrb0nDikcacTmjNytiKoAA',NULL,'2024-08-11T00:04:44.205156600',NULL),(0,0,0,0,1,0,2,'duong','nguyen','Thanh Pho Tam Ki, Tinh Quang Nam','1999-07-01','2024-08-11T00:04:44.269029600',NULL,'$31$16$uuNO3qvmPsHhOYv15xI1ijrb0nDikcacTmjNytiKoAA','0987654322','2024-08-11T00:04:44.269029600',NULL),(0,1,0,0,2,0,3,'thu','minh','Quan 9, Thu Duc','1999-07-01','2024-08-11T00:04:44.294420900',NULL,'$31$16$uuNO3qvmPsHhOYv15xI1ijrb0nDikcacTmjNytiKoAA','0987654323','2024-08-11T00:04:44.294420900',NULL),(1,1,0,0,2,0,4,'nhu','minh','Nguyen Tat Thanh St, Da Nang City','2006-12-06','2024-08-11T00:04:44.318774700','mnhw.0612@gmail.com','$31$16$uuNO3qvmPsHhOYv15xI1ijrb0nDikcacTmjNytiKoAA',NULL,'2024-08-11T00:04:44.319369800',NULL),(0,1,1,3,2,0,5,'chau','bao','Hoa Khanh','2004-07-01','2024-08-11T00:04:44.340857800','doanthibaochau0410@gmail.com','$31$16$uuNO3qvmPsHhOYv15xI1ijrb0nDikcacTmjNytiKoAA',NULL,'2024-08-11T00:04:44.340857800',NULL),(0,0,1,1,0,0,6,'Duong','Manh','Hoa Khanh','2004-07-01','2024-08-11T00:04:44.416174600','manhduonglhp4@gmail.com','$31$16$uuNO3qvmPsHhOYv15xI1ijrb0nDikcacTmjNytiKoAA',NULL,'2024-08-11T00:04:44.416174600',NULL),(0,1,1,2,1,0,7,'Tram','Minh','Hoa Xuan, Hoa Vang, Da Nang','2004-07-01','2024-08-11T00:04:44.457485400','tramsowavy@gmail.com','$31$16$uuNO3qvmPsHhOYv15xI1ijrb0nDikcacTmjNytiKoAA',NULL,'2024-08-11T00:04:44.457485400',NULL),(0,1,0,2,1,0,8,'きむら','山口','Tokyo, Japan','2004-07-01','2024-08-11T00:04:44.486957100','test1@yahoo.com','$31$16$uuNO3qvmPsHhOYv15xI1ijrb0nDikcacTmjNytiKoAA',NULL,'2024-08-11T00:04:44.486957100',NULL),(0,0,0,2,1,0,9,'えいみ','ふかだ','Nakayama, Japan','2004-07-01','2024-08-11T00:04:44.507775','test2@gmail.com','$31$16$uuNO3qvmPsHhOYv15xI1ijrb0nDikcacTmjNytiKoAA',NULL,'2024-08-11T00:04:44.507775',NULL),(0,0,0,2,1,0,10,'Hai','Luu Van','Tuy Loan, Hoa Phong','2004-07-01','2024-08-11T00:04:44.531483500','test3@outlook.com','$31$16$uuNO3qvmPsHhOYv15xI1ijrb0nDikcacTmjNytiKoAA',NULL,'2024-08-11T00:04:44.531483500',NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-08-11  0:12:19
