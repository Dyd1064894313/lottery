/*
Navicat MySQL Data Transfer

Source Server         : 本地数据库
Source Server Version : 50721
Source Host           : localhost:3306
Source Database       : lottery

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2018-08-22 21:44:40
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `lottery_define`
-- ----------------------------
DROP TABLE IF EXISTS `lottery_define`;
CREATE TABLE `lottery_define` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `lottery_code` varchar(128) NOT NULL,
  `lottery_name` varchar(128) NOT NULL DEFAULT '',
  `lottery_desc` varchar(1024) NOT NULL DEFAULT '',
  `begin_year` varchar(128) NOT NULL DEFAULT '',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态：0 失效 1 有效',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark` varchar(2048) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of lottery_define
-- ----------------------------
INSERT INTO `lottery_define` VALUES ('1', 'ssq', '双色球', '双色球', '', '1', '2018-08-06 23:10:31', '2018-08-06 23:10:31', '每周二、四、日开奖');
INSERT INTO `lottery_define` VALUES ('2', 'dlt', '超级大乐透', '超级大乐透', '', '1', '2018-08-06 23:11:34', '2018-08-06 23:11:34', '每周一、三、六开奖');
