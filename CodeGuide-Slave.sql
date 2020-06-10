/*
 Code Guide Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 80011
 Source Host           : 127.0.0.1:3306
 Source Schema         : Slave

 Target Server Type    : MySQL
 Target Server Version : 80011
 File Encoding         : 65001

 Date: 08/01/2020 17:48:29
*/

-- DROP DATABASE IF EXISTS Slave;
CREATE DATABASE IF NOT EXISTS Slave DEFAULT CHARSET utf8 COLLATE utf8_general_ci;
-- CREATE DATABASE IF NOT EXISTS Slave CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_unicode_ci';

USE Slave;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `userName` varchar(225) DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) DEFAULT NULL COMMENT '用户密码',
  `age` tinyint(2) unsigned NOT NULL DEFAULT 0 COMMENT '年龄',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `createTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES (1, '猫眼测试用户1', 'MaoYan1', 24, 'Slave', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO `sys_user` VALUES (2, '猫眼测试用户2', 'MaoYan2', 22, 'Slave', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO `sys_user` VALUES (3, '猫眼测试用户3', 'MaoYan3', 23, 'Slave', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
COMMIT;

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `ID` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `NAME` varchar(225) DEFAULT NULL COMMENT '名称',
  `CLASS_NAME` varchar(255) DEFAULT NULL COMMENT '名称1',
  `CREATE_DATE` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `UPDATE_DATE` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='学生';

-- ----------------------------
-- Records of student
-- ----------------------------
BEGIN;
INSERT INTO `student` VALUES (1, '上海市', '11', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO `student` VALUES (2, '武汉市', '12', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO `student` VALUES (3, '北京市', '13', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;

