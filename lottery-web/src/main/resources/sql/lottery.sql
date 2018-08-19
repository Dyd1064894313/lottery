/*
Navicat MySQL Data Transfer

Source Server         : 本地数据库
Source Server Version : 50721
Source Host           : localhost:3306
Source Database       : lottery

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2018-08-17 21:15:20
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `lottery_data_url`
-- ----------------------------
DROP TABLE IF EXISTS `lottery_data_url`;
CREATE TABLE `lottery_data_url` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `lottery_code` varchar(128) NOT NULL,
  `url` varchar(4096) NOT NULL,
  `param` varchar(4096) NOT NULL DEFAULT '',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态： 0 失效 1 有效',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark` varchar(2048) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of lottery_data_url
-- ----------------------------
INSERT INTO `lottery_data_url` VALUES ('1', 'ssq', 'https://www.cjcp.com.cn/ajax_kj.php?jsoncallback=jsonp1533564691221&ssq_qs={$no$}', '', '1', '2018-08-09 21:35:29', '2018-08-09 21:35:18', '');
INSERT INTO `lottery_data_url` VALUES ('2', 'dlt', 'https://www.cjcp.com.cn/ajax_kj.php?jsoncallback=jsonp1532326738107&dlt_qs={$no$}', '', '1', '2018-08-07 22:18:59', '2018-08-07 22:18:59', '');

-- ----------------------------
-- Table structure for `lottery_define`
-- ----------------------------
DROP TABLE IF EXISTS `lottery_define`;
CREATE TABLE `lottery_define` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `lottery_code` varchar(128) NOT NULL,
  `lottery_name` varchar(128) NOT NULL DEFAULT '',
  `lottery_desc` varchar(1024) NOT NULL DEFAULT '',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态：0 失效 1 有效',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark` varchar(2048) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of lottery_define
-- ----------------------------
INSERT INTO `lottery_define` VALUES ('1', 'ssq', '双色球', '双色球', '1', '2018-08-06 23:10:31', '2018-08-06 23:10:31', '每周二、四、日开奖');
INSERT INTO `lottery_define` VALUES ('2', 'dlt', '超级大乐透', '超级大乐透', '1', '2018-08-06 23:11:34', '2018-08-06 23:11:34', '每周一、三、六开奖');

-- ----------------------------
-- Table structure for `lottery_dlt`
-- ----------------------------
DROP TABLE IF EXISTS `lottery_dlt`;
CREATE TABLE `lottery_dlt` (
  `id` bigint(20) NOT NULL,
  `lottery_no` varchar(64) NOT NULL DEFAULT '',
  `lottery_date` date NOT NULL,
  `red_one` varchar(2) NOT NULL DEFAULT '',
  `red_two` varchar(2) NOT NULL DEFAULT '',
  `red_three` varchar(2) NOT NULL DEFAULT '',
  `red_four` varchar(2) NOT NULL DEFAULT '',
  `red_five` varchar(2) NOT NULL DEFAULT '',
  `blue_one` varchar(2) NOT NULL DEFAULT '',
  `blue_two` varchar(2) NOT NULL DEFAULT '',
  `lottery_sale_amount` varchar(64) NOT NULL DEFAULT '',
  `lottery_pool_amount` varchar(64) NOT NULL DEFAULT '',
  `prize_one_num` bigint(20) NOT NULL DEFAULT '-1',
  `prize_one_every_money` bigint(20) NOT NULL DEFAULT '-1',
  `prize_one_additional_num` bigint(20) NOT NULL DEFAULT '-1',
  `prize_one_additional_every_money` bigint(20) NOT NULL DEFAULT '-1',
  `prize_two_num` bigint(20) NOT NULL DEFAULT '-1',
  `prize_two_every_money` bigint(20) NOT NULL DEFAULT '-1',
  `prize_two_additional_num` bigint(20) NOT NULL DEFAULT '-1',
  `prize_two_additional_every_money` bigint(20) NOT NULL DEFAULT '-1',
  `prize_three_num` bigint(20) NOT NULL DEFAULT '-1',
  `prize_three_every_money` bigint(20) NOT NULL DEFAULT '-1',
  `prize_three_additional_num` bigint(20) NOT NULL DEFAULT '-1',
  `prize_three_additional_every_money` bigint(20) NOT NULL DEFAULT '-1',
  `prize_four_num` bigint(20) NOT NULL DEFAULT '-1',
  `prize_four_every_money` bigint(20) NOT NULL DEFAULT '-1',
  `prize_four_additional_num` bigint(20) NOT NULL DEFAULT '-1',
  `prize_four_additional_every_money` bigint(20) NOT NULL DEFAULT '-1',
  `prize_five_num` bigint(20) NOT NULL DEFAULT '-1',
  `prize_five_every_money` bigint(20) NOT NULL DEFAULT '-1',
  `prize_five_additional_num` bigint(20) NOT NULL DEFAULT '-1',
  `prize_five_additional_every_money` bigint(20) NOT NULL DEFAULT '-1',
  `prize_six_num` bigint(20) NOT NULL DEFAULT '-1',
  `prize_six_every_money` bigint(20) NOT NULL DEFAULT '-1',
  `prize_six_additional_num` bigint(20) NOT NULL DEFAULT '-1',
  `prize_six_additional_every_money` bigint(20) NOT NULL DEFAULT '-1',
  `prize_seven_num` bigint(20) NOT NULL DEFAULT '-1',
  `prize_seven_every_money` bigint(20) NOT NULL DEFAULT '-1',
  `prize_seven_additional_num` bigint(20) NOT NULL DEFAULT '-1',
  `prize_seven_additional_every_money` bigint(20) NOT NULL DEFAULT '-1',
  `prize_eight_num` bigint(20) NOT NULL DEFAULT '-1',
  `prize_eight_every_money` bigint(20) NOT NULL DEFAULT '-1',
  `prize_eight_additional_num` bigint(20) NOT NULL DEFAULT '-1',
  `prize_eight_additional_every_money` bigint(20) NOT NULL DEFAULT '-1',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态：0 失效 1 有效',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark` varchar(2048) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of lottery_dlt
-- ----------------------------

-- ----------------------------
-- Table structure for `lottery_ssq`
-- ----------------------------
DROP TABLE IF EXISTS `lottery_ssq`;
CREATE TABLE `lottery_ssq` (
  `id` bigint(20) NOT NULL,
  `lottery_no` varchar(64) NOT NULL DEFAULT '',
  `lottery_date` date NOT NULL,
  `red_one` varchar(2) NOT NULL DEFAULT '',
  `red_two` varchar(2) NOT NULL DEFAULT '',
  `red_three` varchar(2) NOT NULL DEFAULT '',
  `red_four` varchar(2) NOT NULL DEFAULT '',
  `red_five` varchar(2) NOT NULL DEFAULT '',
  `red_six` varchar(2) NOT NULL DEFAULT '',
  `blue_one` varchar(2) NOT NULL DEFAULT '',
  `lottery_sale_amount` varchar(64) NOT NULL DEFAULT '',
  `lottery_pool_amount` varchar(64) NOT NULL DEFAULT '',
  `prize_one_num` bigint(20) NOT NULL DEFAULT '-1',
  `prize_one_every_money` bigint(20) NOT NULL DEFAULT '-1',
  `prize_two_num` bigint(20) NOT NULL DEFAULT '-1',
  `prize_two_every_money` bigint(20) NOT NULL DEFAULT '-1',
  `prize_three_num` bigint(20) NOT NULL DEFAULT '-1',
  `prize_three_every_money` bigint(20) NOT NULL DEFAULT '-1',
  `prize_four_num` bigint(20) NOT NULL DEFAULT '-1',
  `prize_four_every_money` bigint(20) NOT NULL DEFAULT '-1',
  `prize_five_num` bigint(20) NOT NULL DEFAULT '-1',
  `prize_five_every_money` bigint(20) NOT NULL DEFAULT '-1',
  `prize_six_num` bigint(20) NOT NULL DEFAULT '-1',
  `prize_six_every_money` bigint(20) NOT NULL DEFAULT '-1',
  `prize_seven_num` bigint(20) NOT NULL DEFAULT '-1',
  `prize_seven_every_money` bigint(20) NOT NULL DEFAULT '-1',
  `prize_eight_num` bigint(20) NOT NULL DEFAULT '-1',
  `prize_eight_every_money` bigint(20) NOT NULL DEFAULT '-1',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态：0 失效 1 有效',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark` varchar(2048) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of lottery_ssq
-- ----------------------------

-- ----------------------------
-- Table structure for `test1`
-- ----------------------------
DROP TABLE IF EXISTS `test1`;
CREATE TABLE `test1` (
  `q` int(11) NOT NULL AUTO_INCREMENT,
  `w` int(11) NOT NULL,
  `e` int(11) NOT NULL,
  PRIMARY KEY (`q`)
) ENGINE=InnoDB AUTO_INCREMENT=1470309710 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of test1
-- ----------------------------
INSERT INTO `test1` VALUES ('1', '2', '3');
INSERT INTO `test1` VALUES ('1470309709', '2', '3');
