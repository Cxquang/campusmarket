/*
SQLyog v10.2 
MySQL - 5.7.18 : Database - o2o
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`o2o` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `o2o`;

/*Table structure for table `tb_area` */

DROP TABLE IF EXISTS `tb_area`;

CREATE TABLE `tb_area` (
  `area_id` int(2) NOT NULL AUTO_INCREMENT,
  `area_name` varchar(200) NOT NULL,
  `priority` int(2) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `last_edit_time` datetime DEFAULT NULL,
  PRIMARY KEY (`area_id`),
  UNIQUE KEY `UK_AREA` (`area_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `tb_area` */

insert  into `tb_area`(`area_id`,`area_name`,`priority`,`create_time`,`last_edit_time`) values (1,'东苑',1,NULL,NULL),(2,'西苑',2,NULL,NULL);

/*Table structure for table `tb_head_line` */

DROP TABLE IF EXISTS `tb_head_line`;

CREATE TABLE `tb_head_line` (
  `line_id` int(100) NOT NULL,
  `line_name` varchar(1000) DEFAULT NULL,
  `line_link` varchar(2000) NOT NULL,
  `line_img` varchar(2000) NOT NULL,
  `priority` int(2) DEFAULT NULL,
  `enable_Status` int(2) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `last_edit_time` datetime DEFAULT NULL,
  PRIMARY KEY (`line_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_head_line` */

/*Table structure for table `tb_local_auth` */

DROP TABLE IF EXISTS `tb_local_auth`;

CREATE TABLE `tb_local_auth` (
  `local_auth_id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) NOT NULL,
  `username` varchar(128) NOT NULL,
  `password` varchar(128) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `last_edit_time` datetime DEFAULT NULL,
  PRIMARY KEY (`local_auth_id`),
  UNIQUE KEY `uk_local_profile` (`username`),
  KEY `uk_wechatauth_profile` (`user_id`),
  CONSTRAINT `uk_wechatauth_profile` FOREIGN KEY (`user_id`) REFERENCES `tb_person_info` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_local_auth` */

/*Table structure for table `tb_person_info` */

DROP TABLE IF EXISTS `tb_person_info`;

CREATE TABLE `tb_person_info` (
  `user_id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL,
  `profile_img` varchar(1024) DEFAULT NULL,
  `email` varchar(1024) DEFAULT NULL,
  `gender` varchar(2) DEFAULT NULL,
  `enable_Status` int(2) NOT NULL DEFAULT '0' COMMENT '0:禁止使用本商城，1：允许使用',
  `user_type` int(2) NOT NULL DEFAULT '1' COMMENT '1:顾客，2:店家，3:超级管理员',
  `create_time` datetime DEFAULT NULL,
  `last_edit_time` datetime DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `tb_person_info` */

insert  into `tb_person_info`(`user_id`,`name`,`profile_img`,`email`,`gender`,`enable_Status`,`user_type`,`create_time`,`last_edit_time`) values (1,'蔡贤权','test','test','1',1,2,NULL,NULL);

/*Table structure for table `tb_product` */

DROP TABLE IF EXISTS `tb_product`;

CREATE TABLE `tb_product` (
  `product_id` int(100) NOT NULL AUTO_INCREMENT,
  `product_name` varchar(100) NOT NULL,
  `product_desc` varchar(2000) DEFAULT NULL,
  `img_addr` varchar(2000) DEFAULT '',
  `normal_price` varchar(100) DEFAULT NULL,
  `promotion_price` varchar(100) DEFAULT NULL,
  `priority` int(2) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `last_edit_time` datetime DEFAULT NULL,
  `enable_Status` int(2) NOT NULL DEFAULT '0',
  `product_category_id` int(11) DEFAULT NULL,
  `shop_id` int(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`product_id`),
  KEY `fk_product_procate` (`product_category_id`),
  KEY `fk_product_shop` (`shop_id`),
  CONSTRAINT `fk_product_procate` FOREIGN KEY (`product_category_id`) REFERENCES `tb_product_category` (`product_category_id`),
  CONSTRAINT `fk_product_shop` FOREIGN KEY (`shop_id`) REFERENCES `tb_shop` (`shop_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_product` */

/*Table structure for table `tb_product_category` */

DROP TABLE IF EXISTS `tb_product_category`;

CREATE TABLE `tb_product_category` (
  `product_category_id` int(11) NOT NULL AUTO_INCREMENT,
  `product_category_name` varchar(100) NOT NULL,
  `priority` int(2) DEFAULT '0',
  `shop_id` int(20) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`product_category_id`),
  KEY `fk_product_category_shop` (`shop_id`),
  CONSTRAINT `fk_product_category_shop` FOREIGN KEY (`shop_id`) REFERENCES `tb_shop` (`shop_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_product_category` */

/*Table structure for table `tb_product_img` */

DROP TABLE IF EXISTS `tb_product_img`;

CREATE TABLE `tb_product_img` (
  `product_img_id` int(20) NOT NULL AUTO_INCREMENT,
  `img_addr` varchar(2000) NOT NULL,
  `img_desc` varchar(2000) DEFAULT NULL,
  `priority` int(2) DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `product_id` int(20) DEFAULT NULL,
  PRIMARY KEY (`product_img_id`),
  KEY `fk_product_img_product` (`product_id`),
  CONSTRAINT `fk_product_img_product` FOREIGN KEY (`product_id`) REFERENCES `tb_product` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_product_img` */

/*Table structure for table `tb_shop` */

DROP TABLE IF EXISTS `tb_shop`;

CREATE TABLE `tb_shop` (
  `shop_id` int(10) NOT NULL AUTO_INCREMENT,
  `owner_id` int(10) NOT NULL COMMENT '店铺创建人',
  `area_id` int(5) DEFAULT NULL,
  `shop_category_id` int(11) DEFAULT NULL,
  `shop_name` varchar(256) NOT NULL DEFAULT '',
  `shop_desc` varchar(1024) DEFAULT NULL,
  `shop_addr` varchar(200) DEFAULT NULL,
  `phone` varchar(128) DEFAULT NULL,
  `shop_img` varchar(1024) DEFAULT NULL,
  `priority` int(3) DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `last_edit_time` datetime DEFAULT NULL,
  `enable_status` int(2) NOT NULL DEFAULT '0',
  `advice` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`shop_id`),
  KEY `fk_shop_profile` (`owner_id`),
  KEY `fk_shop_area` (`area_id`),
  KEY `fk_shop_category` (`shop_category_id`),
  CONSTRAINT `fk_shop_area` FOREIGN KEY (`area_id`) REFERENCES `tb_area` (`area_id`),
  CONSTRAINT `fk_shop_category` FOREIGN KEY (`shop_category_id`) REFERENCES `tb_shop_category` (`shop_category_id`),
  CONSTRAINT `fk_shop_profile` FOREIGN KEY (`owner_id`) REFERENCES `tb_person_info` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8;

/*Data for the table `tb_shop` */

insert  into `tb_shop`(`shop_id`,`owner_id`,`area_id`,`shop_category_id`,`shop_name`,`shop_desc`,`shop_addr`,`phone`,`shop_img`,`priority`,`create_time`,`last_edit_time`,`enable_status`,`advice`) values (1,1,2,1,'测试的店铺','测试描述','测试地址','13680442679','test',1,'2018-05-27 22:07:49','2018-05-28 14:53:17',1,'审核中'),(2,1,2,1,'测试的店铺','test','test','13680442679','test',1,'2018-05-28 14:47:40','2018-05-28 14:47:40',1,'审核中'),(15,1,1,1,'蔡贤权的店铺','test','test','13680442679','test',1,'2018-05-29 19:44:24','2018-05-29 19:44:24',1,'审核中'),(20,1,2,1,'测试的店铺2','test1','test1','test1',NULL,NULL,'2018-05-29 20:02:16',NULL,0,'审核中'),(23,1,1,1,'蔡贤权的店铺','test','test','13680442679','test',1,'2018-05-29 20:12:52','2018-05-29 20:12:52',1,'审核中'),(24,1,2,1,'测试的店铺2','test1','test1','test1',NULL,NULL,'2018-05-29 20:20:10',NULL,0,'审核中'),(26,1,1,1,'蔡贤权的店铺','test','test','13680442679','test',1,'2018-05-29 20:31:15','2018-05-29 20:31:15',1,'审核中'),(27,1,2,1,'测试的店铺2','test1','test1','test1',NULL,NULL,'2018-05-29 20:34:19',NULL,0,'审核中'),(32,1,2,1,'测试的店铺2','test1','test1','test1','upload\\item\\shop\\32\\2018052922283942341.jpg',NULL,'2018-05-29 22:28:39',NULL,0,'审核中'),(34,1,2,1,'测试的店铺3','test3','test3','test3','upload\\item\\shop\\34\\2018053117455337151.jpg',NULL,'2018-05-31 17:45:54','2018-05-31 17:45:54',0,'审核中'),(35,1,2,2,'蔡贤权的商铺','48栋202','五邑大学','13680442679','upload\\item\\shop\\35\\2018060220293243924.jpg',NULL,'2018-06-02 20:29:23','2018-06-02 20:29:23',0,NULL),(36,1,2,2,'蔡贤权的店铺2','牛奶咖啡','五邑大学','13680442679','upload\\item\\shop\\36\\2018060314071513638.jpg',NULL,'2018-06-03 14:07:16','2018-06-03 14:07:16',0,NULL),(37,1,2,2,'蔡贤权的店铺2','洗衣液','五邑大学','13680442679','upload\\item\\shop\\37\\2018060314084754469.jpg',NULL,'2018-06-03 14:08:48','2018-06-03 14:08:48',0,NULL);

/*Table structure for table `tb_shop_category` */

DROP TABLE IF EXISTS `tb_shop_category`;

CREATE TABLE `tb_shop_category` (
  `shop_category_id` int(11) NOT NULL AUTO_INCREMENT,
  `shop_category_name` varchar(100) NOT NULL DEFAULT '',
  `shop_category_desc` varchar(1000) DEFAULT '',
  `shop_category_img` varchar(2000) DEFAULT NULL,
  `priority` int(2) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `last_edit_time` datetime DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`shop_category_id`),
  KEY `fk_shop_category_self` (`parent_id`),
  CONSTRAINT `fk_shop_category_self` FOREIGN KEY (`parent_id`) REFERENCES `tb_shop_category` (`shop_category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `tb_shop_category` */

insert  into `tb_shop_category`(`shop_category_id`,`shop_category_name`,`shop_category_desc`,`shop_category_img`,`priority`,`create_time`,`last_edit_time`,`parent_id`) values (1,'咖啡奶茶','咖啡奶茶','test',1,NULL,NULL,NULL),(2,'咖啡','测试类别','test2',0,NULL,NULL,1);

/*Table structure for table `tb_wechat_auth` */

DROP TABLE IF EXISTS `tb_wechat_auth`;

CREATE TABLE `tb_wechat_auth` (
  `wechat_auth_id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) NOT NULL,
  `open_id` varchar(1024) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`wechat_auth_id`),
  UNIQUE KEY `open_id` (`open_id`),
  KEY `fk_wechatauth_profile` (`user_id`),
  CONSTRAINT `fk_wechatauth_profile` FOREIGN KEY (`user_id`) REFERENCES `tb_person_info` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_wechat_auth` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
