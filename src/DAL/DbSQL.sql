CREATE DATABASE  IF NOT EXISTS `apptiavo` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `apptiavo`;
-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: localhost    Database: apptiavo
-- ------------------------------------------------------
-- Server version	8.0.31

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
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cliente` (
  `ClienteID` int NOT NULL AUTO_INCREMENT,
  `Nome` varchar(60) NOT NULL,
  `CPF` varchar(100) NOT NULL,
  `Telefone` varchar(25) NOT NULL,
  PRIMARY KEY (`ClienteID`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` VALUES (9,'Teste4','454.964.108-89','11941668123'),(10,'Teste4','454.964.108-89','11941668123'),(11,'Teste4','454.964.108-89','11941668123'),(12,'Teste4','454.964.108-89','11941668123'),(13,'Teste4','454.964.108-89','11941668123'),(14,'Teste4','454.964.108-89','11941668123'),(15,'Teste4','454.964.108-89','11941668123'),(16,'Teste4','454.964.108-89','11941668123'),(17,'Teste4','454.964.108-89','11941668123'),(18,'Teste4','454.964.108-89','11941668123'),(19,'Teste4','454.964.108-89','11941668123'),(20,'Teste4','454.964.108-89','11941668123'),(21,'Teste4','454.964.108-89','11941668123'),(22,'Teste4','454.964.108-89','11941668123'),(23,'Teste4','454.964.108-89','11941668123'),(24,'Teste4','454.964.108-89','11941668123'),(25,'Teste4','454.964.108-89','11941668123'),(26,'Teste4','454.964.108-89','11941668123'),(27,'Teste4','454.964.108-89','11941668123'),(28,'Teste4','454.964.108-89','11941668123'),(29,'Teste4','454.964.108-89','11941668123'),(30,'Teste4','454.964.108-89','11941668123'),(31,'Teste4','454.964.108-89','11941668123'),(32,'Teste4','454.964.108-89','11941668123'),(33,'Teste4','454.964.108-89','11941668123'),(34,'Teste4','454.964.108-89','11941668123'),(35,'Teste4','454.964.108-89','11941668123'),(36,'Teste4','454.964.108-89','11941668123'),(37,'Teste4','454.964.108-89','11941668123'),(38,'Teste4','454.964.108-89','11941668123'),(39,'Teste4','454.964.108-89','11941668123'),(40,'Teste4','454.964.108-89','11941668123'),(41,'Teste4','454.964.108-89','11941668123'),(42,'Teste4','454.964.108-89','11941668123'),(43,'Teste4','454.964.108-89','11941668123'),(44,'Teste4','454.964.108-89','11941668123'),(45,'Teste4','454.964.108-89','11941668123'),(46,'Teste4','454.964.108-89','11941668123'),(47,'Teste4','454.964.108-89','11941668123'),(48,'Teste4','454.964.108-89','11941668123'),(49,'Teste4','454.964.108-89','11941668123'),(50,'Teste4','454.964.108-89','11941668123'),(51,'Teste4','454.964.108-89','11941668123'),(52,'Teste4','454.964.108-89','11941668123'),(53,'Teste4','454.964.108-89','11941668123'),(54,'Teste4','454.964.108-89','11941668123'),(55,'Teste4','454.964.108-89','11941668123'),(56,'Teste4','454.964.108-89','11941668123'),(57,'Teste4','454.964.108-89','11941668123'),(58,'Teste4','454.964.108-89','11941668123'),(59,'Teste4','454.964.108-89','11941668123'),(60,'Teste4','454.964.108-89','11941668123');
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `endereco`
--

DROP TABLE IF EXISTS `endereco`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `endereco` (
  `EnderecoID` int NOT NULL AUTO_INCREMENT,
  `ClienteID` int NOT NULL,
  `CEP` varchar(9) NOT NULL,
  `Estado` varchar(2) NOT NULL,
  `Cidade` varchar(50) NOT NULL,
  `Bairro` varchar(50) NOT NULL,
  `Logradouro` varchar(250) NOT NULL,
  `Numero` int NOT NULL,
  `Complemento` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`EnderecoID`),
  KEY `fk_Endereco_Cliente` (`ClienteID`),
  CONSTRAINT `fk_Endereco_Cliente` FOREIGN KEY (`ClienteID`) REFERENCES `cliente` (`ClienteID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `endereco`
--

LOCK TABLES `endereco` WRITE;
/*!40000 ALTER TABLE `endereco` DISABLE KEYS */;
INSERT INTO `endereco` VALUES (1,9,'13226560','SP','Jundiaí','Santa Gestrudes','Rua Pão',29,'dsfsdgdfg');
/*!40000 ALTER TABLE `endereco` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `entrega`
--

DROP TABLE IF EXISTS `entrega`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `entrega` (
  `EntregaID` int NOT NULL AUTO_INCREMENT,
  `Descricao` varchar(50) NOT NULL,
  PRIMARY KEY (`EntregaID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `entrega`
--

LOCK TABLES `entrega` WRITE;
/*!40000 ALTER TABLE `entrega` DISABLE KEYS */;
INSERT INTO `entrega` VALUES (1,'Retirada'),(2,'Envio'),(3,'Entrega Pessoal');
/*!40000 ALTER TABLE `entrega` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `itens_pedido`
--

DROP TABLE IF EXISTS `itens_pedido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `itens_pedido` (
  `Itens_PedidoID` int NOT NULL AUTO_INCREMENT,
  `PedidoID` int NOT NULL,
  `Item` varchar(60) NOT NULL,
  `Quatidade` bigint NOT NULL,
  `Valor` float NOT NULL,
  PRIMARY KEY (`Itens_PedidoID`),
  KEY `fk_Pedido_PedidoID` (`PedidoID`),
  CONSTRAINT `fk_Pedido_PedidoID` FOREIGN KEY (`PedidoID`) REFERENCES `pedido` (`PedidoID`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `itens_pedido`
--

LOCK TABLES `itens_pedido` WRITE;
/*!40000 ALTER TABLE `itens_pedido` DISABLE KEYS */;
/*!40000 ALTER TABLE `itens_pedido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pagamento`
--

DROP TABLE IF EXISTS `pagamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pagamento` (
  `PagamentoID` int NOT NULL AUTO_INCREMENT,
  `Descricao` varchar(50) NOT NULL,
  PRIMARY KEY (`PagamentoID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pagamento`
--

LOCK TABLES `pagamento` WRITE;
/*!40000 ALTER TABLE `pagamento` DISABLE KEYS */;
INSERT INTO `pagamento` VALUES (1,'Pix'),(2,'Cartão');
/*!40000 ALTER TABLE `pagamento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pedido`
--

DROP TABLE IF EXISTS `pedido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pedido` (
  `PedidoID` int NOT NULL AUTO_INCREMENT,
  `EnderecoID` int NOT NULL,
  `ClienteID` int NOT NULL,
  `DataCriacao` date NOT NULL,
  `EntregaID` int NOT NULL,
  `PagamentoID` int NOT NULL,
  `Frete` float DEFAULT NULL,
  `Total` double NOT NULL,
  `StatusID` int NOT NULL,
  PRIMARY KEY (`PedidoID`),
  KEY `fk_Endereco_Pedido` (`EnderecoID`),
  KEY `fk_Cliente_Pedido` (`ClienteID`),
  KEY `fk_Entrega_Pedido` (`EntregaID`),
  KEY `fk_Pagamento_Pedido` (`PagamentoID`),
  KEY `fk_status_pedido` (`StatusID`),
  CONSTRAINT `fk_Cliente_Pedido` FOREIGN KEY (`ClienteID`) REFERENCES `cliente` (`ClienteID`),
  CONSTRAINT `fk_Endereco_Pedido` FOREIGN KEY (`EnderecoID`) REFERENCES `endereco` (`EnderecoID`),
  CONSTRAINT `fk_Entrega_Pedido` FOREIGN KEY (`EntregaID`) REFERENCES `entrega` (`EntregaID`),
  CONSTRAINT `fk_Pagamento_Pedido` FOREIGN KEY (`PagamentoID`) REFERENCES `pagamento` (`PagamentoID`),
  CONSTRAINT `fk_status_pedido` FOREIGN KEY (`StatusID`) REFERENCES `status_pedido` (`StatusID`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pedido`
--

LOCK TABLES `pedido` WRITE;
/*!40000 ALTER TABLE `pedido` DISABLE KEYS */;
INSERT INTO `pedido` VALUES (3,1,9,'2023-01-07',1,1,14.15,14.149999618530273,1),(4,1,9,'2022-01-10',1,1,14.15,14.149999618530273,1),(5,1,9,'2023-01-07',1,1,14.15,14.149999618530273,1),(6,1,9,'2023-01-07',1,1,14.15,14.149999618530273,1);
/*!40000 ALTER TABLE `pedido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `status_pedido`
--

DROP TABLE IF EXISTS `status_pedido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `status_pedido` (
  `StatusID` int NOT NULL AUTO_INCREMENT,
  `Descricao` varchar(50) NOT NULL,
  PRIMARY KEY (`StatusID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `status_pedido`
--

LOCK TABLES `status_pedido` WRITE;
/*!40000 ALTER TABLE `status_pedido` DISABLE KEYS */;
INSERT INTO `status_pedido` VALUES (1,'Sacolina'),(2,'Aguardando Pagamento'),(3,'Enviado'),(4,'Entregue');
/*!40000 ALTER TABLE `status_pedido` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-01-31 11:15:38
