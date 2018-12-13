/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50723
 Source Host           : localhost:3306
 Source Schema         : w2ol

 Target Server Type    : MySQL
 Target Server Version : 50723
 File Encoding         : 65001

 Date: 13/12/2018 23:59:49
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `password` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `name` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `gender` tinyint(255) NULL DEFAULT NULL,
  `phone` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'test', 'test', 'test', -1, '123456789');
INSERT INTO `user` VALUES (5, 'tttt', 'C4CA4238A0B923820DCC509A6F75849B', 'abc', 2, '111');
INSERT INTO `user` VALUES (6, 't2', '76D80224611FC919A5D54F0FF9FBA446', 'qwe', 0, '110');

SET FOREIGN_KEY_CHECKS = 1;
