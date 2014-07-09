/*
Navicat MySQL Data Transfer

Source Server         : ds_conn
Source Server Version : 50536
Source Host           : localhost:3306
Source Database       : db_news

Target Server Type    : MYSQL
Target Server Version : 50536
File Encoding         : 65001

Date: 2014-07-08 22:18:42
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `news`
-- ----------------------------
DROP TABLE IF EXISTS `news`;
CREATE TABLE `news` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `class` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '新闻类别',
  `url` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'url地址',
  `url_md5` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '对url计算md5值',
  `title` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '新闻标题',
  `source` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '新闻来源',
  `publish_time` datetime DEFAULT NULL COMMENT '新闻发布时间',
  `body` text COLLATE utf8_unicode_ci COMMENT '新闻正文',
  `future` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '预留字段',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of news
-- ----------------------------

-- ----------------------------
-- Table structure for `seed`
-- ----------------------------
DROP TABLE IF EXISTS `seed`;
CREATE TABLE `seed` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '初始URL种子',
  `class` varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '新闻类别',
  `status` tinyint(4) DEFAULT '0' COMMENT '标记是否被爬去   0：未被爬取   1：正在被爬取  2：爬取结束',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of seed
-- ----------------------------
INSERT INTO `seed` VALUES ('1', 'http://www.cas.cn/xw/zyxw/yw/', '要闻', '0');
INSERT INTO `seed` VALUES ('2', 'http://www.cas.cn/ky/kyjz/', '科研进展', '0');
INSERT INTO `seed` VALUES ('3', 'http://www.cas.cn/xw/kjsm/', '科技动态', '0');
INSERT INTO `seed` VALUES ('4', 'http://www.cas.cn/xw/yxdt/', '综合报道', '0');
INSERT INTO `seed` VALUES ('5', 'http://www.cas.cn/xw/rcjy/', '人才教育', '0');
INSERT INTO `seed` VALUES ('6', 'http://www.cas.cn/xw/zjsd/', '专家视点', '0');
INSERT INTO `seed` VALUES ('7', 'http://www.cas.cn/hzjl/ydhz/hzdt/', '院地合作', '0');
INSERT INTO `seed` VALUES ('8', 'http://www.cas.cn/hzjl/gjjl/hzdt/', '国际交流', '0');
INSERT INTO `seed` VALUES ('9', 'http://www.cas.cn/hy/xshd/', '学术活动', '0');
INSERT INTO `seed` VALUES ('10', 'http://www.cas.cn/hy/hyyg/', '会议信息', '0');
INSERT INTO `seed` VALUES ('11', 'http://www.cas.cn/hy/hyyg/', '传媒扫描', '0');
INSERT INTO `seed` VALUES ('12', 'http://www.cas.cn/kxcb/kpdt/', '科普动态', '0');
