/*
 Navicat Premium Data Transfer

 Source Server         : Udacity Software 2
 Source Server Type    : MySQL
 Source Server Version : 50554
 Source Host           : 52.206.157.109
 Source Database       : U03OVA

 Target Server Type    : MySQL
 Target Server Version : 50554
 File Encoding         : utf-8

 Date: 05/23/2017 18:15:12 PM
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `address`
-- ----------------------------
DROP TABLE IF EXISTS `address`;
CREATE TABLE `address` (
  `addressId` int(10) NOT NULL AUTO_INCREMENT,
  `address` varchar(50) NOT NULL,
  `address2` varchar(50) DEFAULT NULL,
  `cityId` int(10) NOT NULL,
  `postalCode` varchar(10) NOT NULL,
  `phone` varchar(20) NOT NULL,
  `createDate` datetime NOT NULL,
  `createdBy` varchar(40) NOT NULL,
  `lastUpdate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `lastUpdateBy` varchar(40) NOT NULL,
  PRIMARY KEY (`addressId`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=latin1;

-- ----------------------------
--  Records of `address`
-- ----------------------------
BEGIN;
INSERT INTO `address` VALUES ('2', 'Jenkins', '', '1', '92124', '8888888888', '2017-04-02 16:48:48', 'darren', '2017-04-02 16:48:48', 'darren'), ('14', 'Union Jack Ln', 'Suite 1337', '3', 'NW2', '0208889990000', '2017-04-19 03:45:04', 'darren', '2017-04-19 03:45:04', 'darren'), ('15', '1 Police Plaza Path', '', '2', '10001', '19293334444', '2017-04-19 03:46:31', 'darren', '2017-04-19 03:46:31', 'darren'), ('16', '404 St', '#11', '1', '85001', '16027008000', '2017-04-19 03:47:51', 'darren', '2017-04-19 03:47:51', 'darren'), ('17', 'Mississippi Blvd', '', '2', '55555', '18003334444', '2017-04-20 02:43:07', 'test', '2017-04-20 02:43:07', 'test'), ('18', '767 103rd Ave Ne', '', '2', '55445454', '76866666', '2017-04-22 16:33:00', 'test', '2017-04-22 16:33:00', 'test');
COMMIT;

-- ----------------------------
--  Table structure for `appointment`
-- ----------------------------
DROP TABLE IF EXISTS `appointment`;
CREATE TABLE `appointment` (
  `appointmentId` int(10) NOT NULL AUTO_INCREMENT,
  `customerId` int(10) NOT NULL,
  `title` varchar(255) NOT NULL,
  `description` text NOT NULL,
  `location` text NOT NULL,
  `contact` text NOT NULL,
  `url` varchar(255) NOT NULL,
  `start` datetime NOT NULL,
  `end` datetime NOT NULL,
  `createDate` datetime NOT NULL,
  `createdBy` varchar(40) NOT NULL,
  `lastUpdate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `lastUpdateBy` varchar(40) NOT NULL,
  PRIMARY KEY (`appointmentId`),
  KEY `createdBy` (`createdBy`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=latin1;

-- ----------------------------
--  Records of `appointment`
-- ----------------------------
BEGIN;
INSERT INTO `appointment` VALUES ('16', '14', 'London Trade Meeting', 'Important Stuff', 'The Office', 'Jack Doe @ jackdoe@gmail.com', 'http://www.london.com', '2017-04-24 17:00:00', '2017-04-25 18:00:00', '2017-04-19 03:48:54', 'darren', '2017-04-19 03:48:54', 'darren'), ('17', '15', 'Trading Platform', 'Discuss software reqs for trading platform', 'Bloomberg tower', 'Tom @ tom.lee@gmail.com', 'https://www.bloomberg.com/', '2017-04-22 20:00:00', '2017-04-22 21:00:00', '2017-04-19 03:50:12', 'darren', '2017-04-19 03:50:12', 'darren'), ('18', '16', 'Materials Cost', 'Discuss materials cost', 'Office', 'Jane @ jane.doe@gmail.com', 'http://www.google.com', '2018-04-01 20:00:00', '2018-04-01 22:00:00', '2017-04-19 04:06:23', 'darren', '2017-04-19 04:06:23', 'darren'), ('19', '15', 'Mayor Stuff', 'Duties', 'wherever', 'Darren', 'https://www.bloomberg.com', '2017-05-02 16:00:00', '2017-05-03 17:00:00', '2017-04-19 04:44:54', 'darren', '2017-04-19 04:44:54', 'darren'), ('20', '17', 'Reading', 'He will read', 'The River', 'Jim Bob', 'http://www.goodreads.com', '2017-07-01 20:00:00', '2017-07-01 22:00:00', '2017-04-20 02:44:08', 'test', '2017-04-20 02:44:08', 'test'), ('21', '18', 'GREAT', 'GREATQ', 'GREAT', 'FELIXA', 'URL', '2017-04-23 15:40:00', '2017-04-23 16:40:00', '2017-04-22 16:41:01', 'test', '2017-04-22 16:41:01', 'test');
COMMIT;

-- ----------------------------
--  Table structure for `city`
-- ----------------------------
DROP TABLE IF EXISTS `city`;
CREATE TABLE `city` (
  `cityId` int(10) NOT NULL AUTO_INCREMENT,
  `city` varchar(50) NOT NULL,
  `countryId` int(10) NOT NULL,
  `createDate` datetime NOT NULL,
  `createdBy` varchar(40) NOT NULL,
  `lastUpdate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `lastUpdateBy` varchar(40) NOT NULL,
  PRIMARY KEY (`cityId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

-- ----------------------------
--  Records of `city`
-- ----------------------------
BEGIN;
INSERT INTO `city` VALUES ('1', 'Phoenix', '1', '2017-03-30 20:51:50', 'darren', '2017-04-13 03:27:21', 'darren'), ('2', 'New York', '1', '2017-04-12 20:28:11', 'darren', '2017-04-12 20:28:17', 'darren'), ('3', 'London', '2', '2017-04-12 20:28:28', 'darren', '2017-04-12 20:28:35', 'darren');
COMMIT;

-- ----------------------------
--  Table structure for `country`
-- ----------------------------
DROP TABLE IF EXISTS `country`;
CREATE TABLE `country` (
  `countryId` int(10) NOT NULL AUTO_INCREMENT,
  `country` varchar(50) NOT NULL,
  `createDate` datetime NOT NULL,
  `createdBy` varchar(40) NOT NULL,
  `lastUpdate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `lastUpdateBy` varchar(40) NOT NULL,
  PRIMARY KEY (`countryId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- ----------------------------
--  Records of `country`
-- ----------------------------
BEGIN;
INSERT INTO `country` VALUES ('1', 'United States', '2017-03-30 20:50:23', 'darren', '2017-04-04 03:56:39', 'darren'), ('2', 'England', '2017-04-12 20:27:38', 'darren', '2017-04-13 03:28:04', 'darren');
COMMIT;

-- ----------------------------
--  Table structure for `customer`
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `customerId` int(10) NOT NULL AUTO_INCREMENT,
  `customerName` varchar(45) NOT NULL,
  `addressId` int(10) NOT NULL,
  `active` tinyint(1) NOT NULL,
  `createDate` datetime NOT NULL,
  `createdBy` varchar(40) NOT NULL,
  `lastUpdate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `lastUpdateBy` varchar(40) NOT NULL,
  PRIMARY KEY (`customerId`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=latin1;

-- ----------------------------
--  Records of `customer`
-- ----------------------------
BEGIN;
INSERT INTO `customer` VALUES ('14', 'John Doe', '14', '1', '2017-04-19 03:45:04', 'darren', '2017-04-19 03:45:04', 'darren'), ('15', 'Mayor Bloomberg', '15', '1', '2017-04-19 03:46:31', 'darren', '2017-04-19 03:46:31', 'darren'), ('16', 'Jane Doe', '16', '1', '2017-04-19 03:47:52', 'darren', '2017-04-19 03:47:52', 'darren'), ('17', 'Mark Twain', '17', '1', '2017-04-20 02:43:07', 'test', '2017-04-20 02:43:07', 'test'), ('18', 'Felixa', '18', '1', '2017-04-22 16:33:00', 'test', '2017-04-22 16:33:00', 'test');
COMMIT;

-- ----------------------------
--  Table structure for `incrementtypes`
-- ----------------------------
DROP TABLE IF EXISTS `incrementtypes`;
CREATE TABLE `incrementtypes` (
  `incrementTypeId` int(11) NOT NULL,
  `incrementTypeDescription` varchar(45) NOT NULL,
  PRIMARY KEY (`incrementTypeId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
--  Table structure for `reminder`
-- ----------------------------
DROP TABLE IF EXISTS `reminder`;
CREATE TABLE `reminder` (
  `reminderId` int(10) NOT NULL AUTO_INCREMENT,
  `reminderDate` datetime NOT NULL,
  `snoozeIncrement` int(11) DEFAULT NULL,
  `snoozeIncrementTypeId` int(11) DEFAULT NULL,
  `appointmentId` int(10) NOT NULL,
  `createdBy` varchar(45) NOT NULL,
  `createdDate` datetime NOT NULL,
  `remindercol` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`reminderId`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;

-- ----------------------------
--  Records of `reminder`
-- ----------------------------
BEGIN;
INSERT INTO `reminder` VALUES ('8', '2017-04-24 16:45:00', null, null, '16', 'darren', '2017-04-19 03:48:54', null), ('9', '2017-04-22 19:45:00', null, null, '17', 'darren', '2017-04-19 03:50:12', null), ('10', '2018-04-01 19:45:00', null, null, '18', 'darren', '2017-04-19 04:06:23', null), ('11', '2017-05-02 15:45:00', null, null, '19', 'darren', '2017-04-19 04:44:54', null), ('12', '2017-07-01 19:45:00', null, null, '20', 'test', '2017-04-20 02:44:08', null), ('13', '2017-04-23 15:25:00', null, null, '21', 'test', '2017-04-22 16:41:02', null);
COMMIT;

-- ----------------------------
--  Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `userId` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `active` tinyint(4) NOT NULL,
  `createBy` varchar(40) NOT NULL,
  `createDate` datetime NOT NULL,
  `lastUpdate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `lastUpdatedBy` varchar(50) NOT NULL,
  PRIMARY KEY (`userId`),
  KEY `userName` (`userName`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- ----------------------------
--  Records of `user`
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES ('1', 'darren', 'password', '1', 'darren', '2017-03-27 20:46:10', '2017-03-28 03:47:45', 'darren'), ('2', 'test', 'test', '1', 'darren', '2017-04-19 18:26:35', '2017-04-19 18:26:37', '');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
