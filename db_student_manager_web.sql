/*
Navicat MySQL Data Transfer

Source Server         : MIS
Source Server Version : 80017
Source Host           : localhost:3306
Source Database       : db_student_manager_web

Target Server Type    : MYSQL
Target Server Version : 80017
File Encoding         : 65001

Date: 2019-12-23 15:21:16
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `s_admin`
-- ----------------------------
DROP TABLE IF EXISTS `s_admin`;
CREATE TABLE `s_admin` (
  `id` int(5) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL,
  `password` varchar(32) NOT NULL,
  `status` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`,`name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_admin
-- ----------------------------
INSERT INTO `s_admin` VALUES ('1', 'admin', 'admin', '1');

-- ----------------------------
-- Table structure for `s_clazz`
-- ----------------------------
DROP TABLE IF EXISTS `s_clazz`;
CREATE TABLE `s_clazz` (
  `id` int(5) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL,
  `info` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_clazz
-- ----------------------------
INSERT INTO `s_clazz` VALUES ('44', '计算机科学与技术1708', '插本班');
INSERT INTO `s_clazz` VALUES ('45', '应用数学1701', '数学');
INSERT INTO `s_clazz` VALUES ('46', '艺术与设计', '');

-- ----------------------------
-- Table structure for `s_student`
-- ----------------------------
DROP TABLE IF EXISTS `s_student`;
CREATE TABLE `s_student` (
  `id` int(5) NOT NULL AUTO_INCREMENT,
  `sn` varchar(32) NOT NULL,
  `name` varchar(32) NOT NULL,
  `password` varchar(32) NOT NULL,
  `clazz_id` int(5) NOT NULL,
  `sex` varchar(5) NOT NULL DEFAULT '男',
  `mobile` varchar(12) DEFAULT NULL,
  `qq` varchar(18) DEFAULT NULL,
  `photo` mediumblob,
  PRIMARY KEY (`id`,`sn`),
  KEY `student_clazz_id_foreign` (`clazz_id`),
  CONSTRAINT `student_clazz_id_foreign` FOREIGN KEY (`clazz_id`) REFERENCES `s_clazz` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_student
-- ----------------------------
INSERT INTO `s_student` VALUES ('16', '173110028', '张秋鑫', '145845', '44', '男', '13600418432', '1458456908', null);
INSERT INTO `s_student` VALUES ('17', '171100000', '张三', '123456', '44', '男', '13456543330', '12345000', null);
INSERT INTO `s_student` VALUES ('18', '170440000', '丽华', '123456', '46', '女', '13600094544', '145454545', null);

-- ----------------------------
-- Table structure for `s_teacher`
-- ----------------------------
DROP TABLE IF EXISTS `s_teacher`;
CREATE TABLE `s_teacher` (
  `id` int(5) NOT NULL AUTO_INCREMENT,
  `sn` varchar(32) NOT NULL,
  `name` varchar(32) NOT NULL,
  `password` varchar(32) NOT NULL,
  `clazz_id` int(5) NOT NULL,
  `sex` varchar(5) NOT NULL DEFAULT '男',
  `mobile` varchar(12) DEFAULT NULL,
  `qq` varchar(18) DEFAULT NULL,
  `photo` mediumblob,
  PRIMARY KEY (`id`,`sn`),
  KEY `student_clazz_id_foreign` (`clazz_id`),
  CONSTRAINT `s_teacher_ibfk_1` FOREIGN KEY (`clazz_id`) REFERENCES `s_clazz` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_teacher
-- ----------------------------
INSERT INTO `s_teacher` VALUES ('21', 'null', '李老师', '111111', '44', '男', '13412312333', '1312313123', null);
