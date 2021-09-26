-- MySQL dump 10.13  Distrib 8.0.22, for Win64 (x86_64)
--
-- Host: localhost    Database: osnove_softverskih_arhitektura_baza
-- ------------------------------------------------------
-- Server version	8.0.22

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin` (
  `user_id` bigint NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `is_blocked` bit(1) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `UK_gfn44sntic2k93auag97juyij` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `article`
--

DROP TABLE IF EXISTS `article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `article` (
  `article_id` bigint NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `image_path` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`article_id`),
  UNIQUE KEY `UK_9yh3uidjj6cnugskaogt626d5` (`name`),
  KEY `FKfc4bhth1pye5ks34v7x8uqncx` (`user_id`),
  CONSTRAINT `FKfc4bhth1pye5ks34v7x8uqncx` FOREIGN KEY (`user_id`) REFERENCES `seller` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article`
--

LOCK TABLES `article` WRITE;
/*!40000 ALTER TABLE `article` DISABLE KEYS */;
INSERT INTO `article` VALUES (304,'Suncokret jedan','/pictures/slika1.jpg','Suncokret',300,1),(305,'sadda','/pictures/2BHG705.jpg','dsad',1111,1);
/*!40000 ALTER TABLE `article` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `article_discount`
--

DROP TABLE IF EXISTS `article_discount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `article_discount` (
  `article_id` bigint NOT NULL,
  `discount_id` bigint NOT NULL,
  PRIMARY KEY (`article_id`,`discount_id`),
  KEY `FKid8dt2ir6gs10raadm7em663l` (`discount_id`),
  CONSTRAINT `FKid8dt2ir6gs10raadm7em663l` FOREIGN KEY (`discount_id`) REFERENCES `discount` (`discount_id`),
  CONSTRAINT `FKjqjdi20f9skph0ntapwxk2ev4` FOREIGN KEY (`article_id`) REFERENCES `article` (`article_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article_discount`
--

LOCK TABLES `article_discount` WRITE;
/*!40000 ALTER TABLE `article_discount` DISABLE KEYS */;
/*!40000 ALTER TABLE `article_discount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `article_quantity`
--

DROP TABLE IF EXISTS `article_quantity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `article_quantity` (
  `article_quantity_id` bigint NOT NULL,
  `quantity` int NOT NULL,
  `article_id` bigint DEFAULT NULL,
  `order_id` bigint DEFAULT NULL,
  PRIMARY KEY (`article_quantity_id`),
  KEY `FKfnprx68bt5paope3icl70byfk` (`article_id`),
  KEY `FK1l488dmm4obs43l2ra62tdb43` (`order_id`),
  CONSTRAINT `FK1l488dmm4obs43l2ra62tdb43` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`),
  CONSTRAINT `FKfnprx68bt5paope3icl70byfk` FOREIGN KEY (`article_id`) REFERENCES `article` (`article_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article_quantity`
--

LOCK TABLES `article_quantity` WRITE;
/*!40000 ALTER TABLE `article_quantity` DISABLE KEYS */;
/*!40000 ALTER TABLE `article_quantity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `authority`
--

DROP TABLE IF EXISTS `authority`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `authority` (
  `authority_id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`authority_id`)
) ENGINE=InnoDB AUTO_INCREMENT=90 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authority`
--

LOCK TABLES `authority` WRITE;
/*!40000 ALTER TABLE `authority` DISABLE KEYS */;
INSERT INTO `authority` VALUES (1,'ROLE_ADMIN'),(2,'ROLE_SELLER'),(3,'ROLE_BUYER');
/*!40000 ALTER TABLE `authority` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `buyer`
--

DROP TABLE IF EXISTS `buyer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `buyer` (
  `user_id` bigint NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `is_blocked` bit(1) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `UK_df2jf9246bkb964rg04cmucc2` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `buyer`
--

LOCK TABLES `buyer` WRITE;
/*!40000 ALTER TABLE `buyer` DISABLE KEYS */;
INSERT INTO `buyer` VALUES (3,'Buyer1',_binary '\0','Buyer1','$2a$10$TIFcH4rfHiGG39vfCGJ2v.XCXsrNvsAewJip09rt8KxnAfI3dUQw2','buyer1','Buyer 1'),(22,'buyer3',_binary '\0','buyer3','$2a$10$84JwatBwLX09tizQLAsvIOvyim8f3a7lRwcm8n2bo6O4A.u5.Gbe2','buyer3','buyer3');
/*!40000 ALTER TABLE `buyer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `discount`
--

DROP TABLE IF EXISTS `discount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `discount` (
  `discount_id` bigint NOT NULL,
  `end_date` date NOT NULL,
  `percentage` int NOT NULL,
  `start_date` date NOT NULL,
  `text` varchar(255) NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`discount_id`),
  KEY `FKi2gvekdkrl4y62jhhivcgetqg` (`user_id`),
  CONSTRAINT `FKi2gvekdkrl4y62jhhivcgetqg` FOREIGN KEY (`user_id`) REFERENCES `seller` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `discount`
--

LOCK TABLES `discount` WRITE;
/*!40000 ALTER TABLE `discount` DISABLE KEYS */;
/*!40000 ALTER TABLE `discount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (306);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `order_id` bigint NOT NULL,
  `anonymus_comment` bit(1) DEFAULT NULL,
  `archived_comment` bit(1) DEFAULT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `delivered` bit(1) NOT NULL,
  `rating` int DEFAULT NULL,
  `time` datetime(6) NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`order_id`),
  KEY `FK9iwn0xpvhf5fposreptapewhg` (`user_id`),
  CONSTRAINT `FK9iwn0xpvhf5fposreptapewhg` FOREIGN KEY (`user_id`) REFERENCES `buyer` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (285,_binary '',_binary '\0','nakahakdlfl jsjskssj',_binary '',4,'2021-09-03 04:04:21.227784',3),(293,_binary '',_binary '\0','komentarjslshddk',_binary '',4,'2021-09-03 06:38:08.315360',3),(296,_binary '',_binary '\0','hddbdbdjfj',_binary '',3,'2021-09-03 09:17:34.910672',3);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seller`
--

DROP TABLE IF EXISTS `seller`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `seller` (
  `user_id` bigint NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `is_blocked` bit(1) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `registration_date` date DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `UK_3gnjncn8l4no25wl7pyjqrx3p` (`username`),
  UNIQUE KEY `UK_crgbovyy4gvgsum2yyb3fbfn7` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seller`
--

LOCK TABLES `seller` WRITE;
/*!40000 ALTER TABLE `seller` DISABLE KEYS */;
INSERT INTO `seller` VALUES (1,'Seller1',_binary '\0','Seller1','$2a$10$TIFcH4rfHiGG39vfCGJ2v.XCXsrNvsAewJip09rt8KxnAfI3dUQw2','seller1','seller1','seller1@hotmail.com','seller1','2021-06-19'),(2,'Seller2',_binary '\0','Seller2','$2a$10$P6keGKk3Wqbd26nLq3WYg.Amu/HFU6uBW4.BxyFdQgiBfXs0.mgGS','seller2','seller2','seller2@hotmail.com','seller2','2021-06-19'),(12,'seller3',_binary '\0','seller3','$2a$10$/5XUTuThGm7uJ.qnz./YG.lzcbJhtlWConwurFGtmcn88JQaUBPLe','seller3','seller3','seller3@gmail.com','seller3','2021-06-21'),(291,'hskakan',_binary '\0','hdlsjsjs','$2a$10$dGDD4v1o1XBBQlmozIJoOubaaD72LWd94dRu5xNUl6gtFvbFmC8Y6','prodavac','dbjsjs','hsksksjs','hdjdjs','2021-09-03');
/*!40000 ALTER TABLE `seller` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_authorities`
--

DROP TABLE IF EXISTS `user_authorities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_authorities` (
  `user_id` bigint NOT NULL,
  `authoritiy_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`authoritiy_id`),
  KEY `FKhhgglqi0cnvpb4202dw4mhqck` (`authoritiy_id`),
  CONSTRAINT `FKhhgglqi0cnvpb4202dw4mhqck` FOREIGN KEY (`authoritiy_id`) REFERENCES `authority` (`authority_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_authorities`
--

LOCK TABLES `user_authorities` WRITE;
/*!40000 ALTER TABLE `user_authorities` DISABLE KEYS */;
INSERT INTO `user_authorities` VALUES (1,2),(2,2),(12,2),(21,2),(161,2),(179,2),(181,2),(182,2),(185,2),(186,2),(291,2),(3,3),(22,3),(171,3),(174,3),(175,3),(176,3),(177,3),(178,3),(184,3);
/*!40000 ALTER TABLE `user_authorities` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-09-26 12:17:54
