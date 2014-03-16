-- MySQL dump 10.13  Distrib 5.6.15, for linux-glibc2.5 (x86_64)
--
-- Host: localhost    Database: englishtranslator
-- ------------------------------------------------------
-- Server version	5.6.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `wordrelation`
--

DROP TABLE IF EXISTS `wordrelation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wordrelation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `rootWord_id` bigint(20) NOT NULL,
  `word_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `word_id` (`word_id`,`rootWord_id`),
  KEY `FKB52117069703EC2F` (`rootWord_id`),
  KEY `FKB521170689724DCF` (`word_id`),
  CONSTRAINT `FKB521170689724DCF` FOREIGN KEY (`word_id`) REFERENCES `word` (`id`),
  CONSTRAINT `FKB52117069703EC2F` FOREIGN KEY (`rootWord_id`) REFERENCES `rootword` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=173410 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-03-13 21:41:59
