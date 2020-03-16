/*
SQLyog Ultimate v9.50 
MySQL - 5.6.20 : Database - prosoftjun19
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`prosoftjun19` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `prosoftjun19`;

/*Table structure for table `linija` */

DROP TABLE IF EXISTS `linija`;

CREATE TABLE `linija` (
  `LinijaID` int(11) NOT NULL AUTO_INCREMENT,
  `NazivLinije` varchar(255) DEFAULT NULL,
  `PocetnaStanica` int(11) DEFAULT NULL,
  `KrajnjaStanica` int(11) DEFAULT NULL,
  PRIMARY KEY (`LinijaID`),
  KEY `PocetnaStanica` (`PocetnaStanica`),
  KEY `KrajnjaStanica` (`KrajnjaStanica`),
  CONSTRAINT `linija_ibfk_1` FOREIGN KEY (`PocetnaStanica`) REFERENCES `stanica` (`StanicaID`),
  CONSTRAINT `linija_ibfk_2` FOREIGN KEY (`KrajnjaStanica`) REFERENCES `stanica` (`StanicaID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `linija` */

insert  into `linija`(`LinijaID`,`NazivLinije`,`PocetnaStanica`,`KrajnjaStanica`) values (1,'Beograd-Banja Koviljaca',1,4);

/*Table structure for table `linijastanica` */

DROP TABLE IF EXISTS `linijastanica`;

CREATE TABLE `linijastanica` (
  `LinijaID` int(11) NOT NULL,
  `StanicaID` int(11) NOT NULL,
  PRIMARY KEY (`LinijaID`,`StanicaID`),
  KEY `StanicaID` (`StanicaID`),
  CONSTRAINT `linijastanica_ibfk_1` FOREIGN KEY (`LinijaID`) REFERENCES `linija` (`LinijaID`),
  CONSTRAINT `linijastanica_ibfk_2` FOREIGN KEY (`StanicaID`) REFERENCES `stanica` (`StanicaID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `linijastanica` */

insert  into `linijastanica`(`LinijaID`,`StanicaID`) values (1,2),(1,3);

/*Table structure for table `stanica` */

DROP TABLE IF EXISTS `stanica`;

CREATE TABLE `stanica` (
  `StanicaID` int(11) NOT NULL,
  `NazivStanice` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`StanicaID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `stanica` */

insert  into `stanica`(`StanicaID`,`NazivStanice`) values (1,'Beograd'),(2,'Sabac'),(3,'Loznica'),(4,'Banja Koviljaca'),(5,'Smederevo'),(6,'Nis'),(7,'Zlatibor'),(8,'Uzice'),(9,'Cacak'),(10,'Kragujevac');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
