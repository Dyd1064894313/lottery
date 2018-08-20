/*
Navicat MySQL Data Transfer

Source Server         : 本地数据库
Source Server Version : 50721
Source Host           : localhost:3306
Source Database       : lottery

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2018-08-20 23:29:34
*/

SET FOREIGN_KEY_CHECKS=0;
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
  `lottery_sale_amount` varchar(64) DEFAULT '',
  `lottery_pool_amount` varchar(64) DEFAULT '',
  `prize_one_num` bigint(20) DEFAULT '0',
  `prize_one_every_money` bigint(20) DEFAULT '0',
  `prize_one_additional_num` bigint(20) DEFAULT '0',
  `prize_one_additional_every_money` bigint(20) DEFAULT '0',
  `prize_two_num` bigint(20) DEFAULT '0',
  `prize_two_every_money` bigint(20) DEFAULT '0',
  `prize_two_additional_num` bigint(20) DEFAULT '0',
  `prize_two_additional_every_money` bigint(20) DEFAULT '0',
  `prize_three_num` bigint(20) DEFAULT '0',
  `prize_three_every_money` bigint(20) DEFAULT '0',
  `prize_three_additional_num` bigint(20) DEFAULT '0',
  `prize_three_additional_every_money` bigint(20) DEFAULT '0',
  `prize_four_num` bigint(20) DEFAULT '0',
  `prize_four_every_money` bigint(20) DEFAULT '0',
  `prize_four_additional_num` bigint(20) DEFAULT '0',
  `prize_four_additional_every_money` bigint(20) DEFAULT '0',
  `prize_five_num` bigint(20) DEFAULT '0',
  `prize_five_every_money` bigint(20) DEFAULT '0',
  `prize_five_additional_num` bigint(20) DEFAULT '0',
  `prize_five_additional_every_money` bigint(20) DEFAULT '0',
  `prize_six_num` bigint(20) DEFAULT '0',
  `prize_six_every_money` bigint(20) DEFAULT '0',
  `prize_six_additional_num` bigint(20) DEFAULT '0',
  `prize_six_additional_every_money` bigint(20) DEFAULT '0',
  `prize_seven_num` bigint(20) DEFAULT '0',
  `prize_seven_every_money` bigint(20) DEFAULT '0',
  `prize_seven_additional_num` bigint(20) DEFAULT '0',
  `prize_seven_additional_every_money` bigint(20) DEFAULT '0',
  `prize_eight_num` bigint(20) DEFAULT '0',
  `prize_eight_every_money` bigint(20) DEFAULT '0',
  `prize_eight_additional_num` bigint(20) DEFAULT '0',
  `prize_eight_additional_every_money` bigint(20) DEFAULT '0',
  `status` tinyint(1) DEFAULT '1' COMMENT '状态：0 失效 1 有效',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark` varchar(2048) DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of lottery_dlt
-- ----------------------------
