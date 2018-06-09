-- MySQL dump 10.13  Distrib 8.0.11, for macos10.13 (x86_64)
--
-- Host: localhost    Database: 01530693_beer
-- ------------------------------------------------------
-- Server version	8.0.11

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8mb4 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `01530693_beer`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `01530693_beer` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */;

USE `01530693_beer`;

--
-- Table structure for table `beer`
--

DROP TABLE IF EXISTS `beer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `beer` (
  `BId` int(11) NOT NULL,
  `BName` varchar(40) NOT NULL,
  `BPrice` int(11) NOT NULL,
  PRIMARY KEY (`BId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `beer`
--

LOCK TABLES `beer` WRITE;
/*!40000 ALTER TABLE `beer` DISABLE KEYS */;
INSERT INTO `beer` VALUES (1,'Goesser Naturradler',5),(2,'Goesser Naturradler',4),(3,'Stiegl Helles',3),(4,'Zillertaler Weizen',4),(5,'Murauer Helles',3);
/*!40000 ALTER TABLE `beer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `customer` (
  `CId` int(11) NOT NULL,
  `CName` varchar(30) NOT NULL,
  `CCity` varchar(30) NOT NULL,
  `CAge` int(11) NOT NULL,
  `CNumber` varchar(20) NOT NULL,
  PRIMARY KEY (`CId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (1,'Mueller','Graz',20,'00436601111222'),(2,'Franza','Muenchen',30,'00493149264872'),(3,'Maar','Graz',35,'00436602222333'),(4,'Rossi','Rom',50,'00393486805555'),(5,'Pranger','Innsbruck',25,'00436504262333'),(6,'Hager','Wien',45,'00436958728333'),(7,'Muster','Heidelberg',25,'00493149226872'),(8,'Ferrari','Florenz',35,'00393486195255'),(9,'Heine','Graz',27,'00436602182433'),(10,'Musk','Innsbruck',97,'00436605262339');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rating`
--

DROP TABLE IF EXISTS `rating`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `rating` (
  `CId` int(11) NOT NULL,
  `BId` int(11) NOT NULL,
  `RRating` int(11) NOT NULL,
  PRIMARY KEY (`CId`,`BId`),
  KEY `BId` (`BId`),
  CONSTRAINT `rating_ibfk_1` FOREIGN KEY (`CId`) REFERENCES `customer` (`cid`) ON DELETE CASCADE,
  CONSTRAINT `rating_ibfk_2` FOREIGN KEY (`BId`) REFERENCES `beer` (`bid`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rating`
--

LOCK TABLES `rating` WRITE;
/*!40000 ALTER TABLE `rating` DISABLE KEYS */;
INSERT INTO `rating` VALUES (1,1,3),(1,3,2),(2,1,5),(3,2,2),(5,1,4),(5,2,3),(6,5,0),(10,3,2);
/*!40000 ALTER TABLE `rating` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transaction`
--

DROP TABLE IF EXISTS `transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `transaction` (
  `TId` int(11) NOT NULL,
  `CId` int(11) NOT NULL,
  `BId` int(11) NOT NULL,
  `TDate` date NOT NULL,
  `TQnt` int(11) NOT NULL,
  PRIMARY KEY (`TId`),
  KEY `CId` (`CId`),
  KEY `BId` (`BId`),
  CONSTRAINT `transaction_ibfk_1` FOREIGN KEY (`CId`) REFERENCES `customer` (`cid`) ON DELETE CASCADE,
  CONSTRAINT `transaction_ibfk_2` FOREIGN KEY (`BId`) REFERENCES `beer` (`bid`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction`
--

LOCK TABLES `transaction` WRITE;
/*!40000 ALTER TABLE `transaction` DISABLE KEYS */;
INSERT INTO `transaction` VALUES (1,1,1,'2018-04-10',5),(2,1,2,'2018-04-10',3),(3,2,1,'2018-04-15',6),(4,3,2,'2018-04-15',1),(5,6,5,'2018-04-20',10),(6,4,4,'2018-04-25',6),(7,8,5,'2018-05-10',3),(8,8,3,'2018-05-17',7),(9,10,4,'2018-05-30',24),(10,5,2,'2018-06-01',4),(11,3,2,'2018-06-03',9),(12,6,3,'2018-06-03',2);
/*!40000 ALTER TABLE `transaction` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-06-09 14:32:19
