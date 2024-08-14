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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `score`
--

LOCK TABLES `score` WRITE;
/*!40000 ALTER TABLE `score` DISABLE KEYS */;
INSERT INTO `score` VALUES (10,90,1,1,'2024-08-03T09:31:40.307439600','2024-08-03T09:31:40.307439600'),(0,9,2,2,'2024-08-03T09:31:40.307439600','2024-08-03T09:31:40.307439600'),(0,9,3,3,'2024-08-03T09:31:40.307439600','2024-08-03T09:31:40.307439600'),(0,9,4,4,'2024-08-03T09:31:40.307439600','2024-08-03T09:31:40.307439600');
/*!40000 ALTER TABLE `score` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `gender` tinyint NOT NULL,
  `role` tinyint NOT NULL,
  `status` tinyint NOT NULL,
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
  CONSTRAINT `users_chk_1` CHECK ((`gender` between 0 and 2)),
  CONSTRAINT `users_chk_2` CHECK ((`role` between 0 and 4)),
  CONSTRAINT `users_chk_3` CHECK ((`status` between 0 and 2))
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (0,4,0,1,1,'hoang','luu','Duong Vo Van Hat, Ho Chi Minh City','1999-07-01','2024-08-03T09:31:40.126207100','hoangdz1604@gmail.com','$31$16$xj95bcjn4XbStAHrmB271qcaLtxqb02zjI7O1U2NZDE',NULL,'2024-08-03T09:31:40.126207100',NULL),(0,0,1,0,2,'duong','nguyen','Thanh Pho Tam Ki, Tinh Quang Nam','1999-07-01','2024-08-03T09:31:40.126207100',NULL,'$31$16$xj95bcjn4XbStAHrmB271qcaLtxqb02zjI7O1U2NZDE','0987654322','2024-08-03T09:31:40.126207100',NULL),(1,0,2,0,3,'thu','minh','Quan 9, Thu Duc','1999-07-01','2024-08-03T09:31:40.127207600',NULL,'$31$16$xj95bcjn4XbStAHrmB271qcaLtxqb02zjI7O1U2NZDE','0987654323','2024-08-03T09:31:40.127207600',NULL),(1,0,2,0,4,'nhu','minh','Nguyen Tat Thanh St, Da Nang City','2006-12-06','2024-08-03T09:31:40.127207600','mnhw.0612@gmail.com','$31$16$xj95bcjn4XbStAHrmB271qcaLtxqb02zjI7O1U2NZDE',NULL,'2024-08-03T09:31:40.127207600',NULL),(1,0,2,0,5,'chau','bao','Hoa Khanh','2004-07-01','2024-08-03T09:31:40.127207600','doanthibaochau0410@gmail.com','$31$16$xj95bcjn4XbStAHrmB271qcaLtxqb02zjI7O1U2NZDE',NULL,'2024-08-03T09:31:40.127207600',NULL);
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

-- Dump completed on 2024-08-03  9:36:44
