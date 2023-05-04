/*
 Navicat Premium Data Transfer

 Source Server         : mysql5.6_20210415
 Source Server Type    : MySQL
 Source Server Version : 50620
 Source Host           : localhost:3306
 Source Schema         : course_order_db

 Target Server Type    : MySQL
 Target Server Version : 50620
 File Encoding         : 65001

 Date: 17/05/2021 16:08:18
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `username` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `password` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('a', 'a');

-- ----------------------------
-- Table structure for t_leaveword
-- ----------------------------
DROP TABLE IF EXISTS `t_leaveword`;
CREATE TABLE `t_leaveword`  (
  `leaveWordId` int(11) NOT NULL AUTO_INCREMENT COMMENT '留言id',
  `leaveTitle` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '留言标题',
  `leaveContent` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '留言内容',
  `userObj` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '留言人',
  `leaveTime` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '留言时间',
  `replyContent` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '管理回复',
  `replyTime` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '回复时间',
  PRIMARY KEY (`leaveWordId`) USING BTREE,
  INDEX `userObj`(`userObj`) USING BTREE,
  CONSTRAINT `t_leaveword_ibfk_1` FOREIGN KEY (`userObj`) REFERENCES `t_userinfo` (`user_name`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_leaveword
-- ----------------------------
INSERT INTO `t_leaveword` VALUES (1, '同学交流可以找老师吗', '我和几个老同学有个项目要谈，找老师吗', '13688886666', '2021-03-21 21:05:20', '可以的哈', '2021-03-21 21:05:21');
INSERT INTO `t_leaveword` VALUES (3, '我肚子痛就不来了', '今天不舒服，不来上课了，请个假', '13590120934', '2021-03-21 21:48:08', '没得问题', '2021-03-22 12:19:18');
INSERT INTO `t_leaveword` VALUES (4, 'aaaaa', 'bbbbbb', '13590120934', '2021-04-06 21:24:17', '--', '--');

-- ----------------------------
-- Table structure for t_notice
-- ----------------------------
DROP TABLE IF EXISTS `t_notice`;
CREATE TABLE `t_notice`  (
  `noticeId` int(11) NOT NULL AUTO_INCREMENT COMMENT '公告id',
  `title` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标题',
  `content` varchar(800) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '公告内容',
  `publishDate` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发布时间',
  PRIMARY KEY (`noticeId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_notice
-- ----------------------------
INSERT INTO `t_notice` VALUES (1, '学生考勤小程序上线了', '老师上课需要签到了，经常迟到的同学扣分哈', '2021-03-21 21:25:27');
INSERT INTO `t_notice` VALUES (2, '课程考勤流程', '老师上课的时候启动服务器，学生拿出手机扫码绑定手机号，填写个人姓名信息，上课前5分钟开始考勤签到', '2021-03-21 21:57:35');

-- ----------------------------
-- Table structure for t_orderinfo
-- ----------------------------
DROP TABLE IF EXISTS `t_orderinfo`;
CREATE TABLE `t_orderinfo`  (
  `orderNo` int(11) NOT NULL AUTO_INCREMENT COMMENT '订单编号',
  `userObj` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '下单用户',
  `courseObj` int(11) NOT NULL COMMENT '预约会议室',
  `orderDate` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预定日期',
  `intervalObj` int(11) NOT NULL COMMENT '预约时段',
  `totalMoney` float NOT NULL COMMENT '订单总金额',
  `payWayObj` int(11) NOT NULL COMMENT '支付方式',
  `payAccount` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '支付账号',
  `orderStateObj` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单状态',
  `orderTime` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '下单时间',
  `receiveName` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '收货人',
  `telephone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '收货人电话',
  `address` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '收货人地址',
  `orderMemo` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单备注',
  PRIMARY KEY (`orderNo`) USING BTREE,
  INDEX `userObj`(`userObj`) USING BTREE,
  INDEX `courseObj`(`courseObj`) USING BTREE,
  INDEX `intervalObj`(`intervalObj`) USING BTREE,
  INDEX `payWayObj`(`payWayObj`) USING BTREE,
  CONSTRAINT `t_orderinfo_ibfk_1` FOREIGN KEY (`userObj`) REFERENCES `t_userinfo` (`user_name`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `t_orderinfo_ibfk_2` FOREIGN KEY (`courseObj`) REFERENCES `t_course` (`courseId`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `t_orderinfo_ibfk_3` FOREIGN KEY (`intervalObj`) REFERENCES `t_timeinterval` (`intervalId`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `t_orderinfo_ibfk_4` FOREIGN KEY (`payWayObj`) REFERENCES `t_payway` (`payWayId`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_orderinfo
-- ----------------------------
INSERT INTO `t_orderinfo` VALUES (11, '13688886666', 1, '2021-04-16', 2, 40, 1, 'dashen@163.com', '正常', '2021-04-16 09:58:11', '--', '--', '--', 'ok');
INSERT INTO `t_orderinfo` VALUES (12, '13590120934', 1, '2021-04-13', 1, 40, 1, 'xiaoniug@163.com', '迟到', '2021-04-13 08:12:11', '--', '--', '--', '迟到了12分钟');
INSERT INTO `t_orderinfo` VALUES (13, '13590120934', 3, '2021-04-08', 4, 50, 3, '6222024412342342293', '正常', '2021-04-08 15:57:22', '--', '--', '--', '不错');
INSERT INTO `t_orderinfo` VALUES (14, '13590120934', 3, '2021-04-08', 5, 75, 1, '111sss', '迟到', '2021-04-08 19:05:23', '--', '--', '--', '迟到5分钟');

-- ----------------------------
-- Table structure for t_payway
-- ----------------------------
DROP TABLE IF EXISTS `t_payway`;
CREATE TABLE `t_payway`  (
  `payWayId` int(11) NOT NULL AUTO_INCREMENT COMMENT '支付方式id',
  `payWayName` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '支付方式名称',
  PRIMARY KEY (`payWayId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_payway
-- ----------------------------
INSERT INTO `t_payway` VALUES (1, '支付宝');
INSERT INTO `t_payway` VALUES (2, '微信');
INSERT INTO `t_payway` VALUES (3, '银行卡');

-- ----------------------------
-- Table structure for t_course
-- ----------------------------
DROP TABLE IF EXISTS `t_course`;
CREATE TABLE `t_course`  (
  `courseId` int(11) NOT NULL AUTO_INCREMENT COMMENT '会议室id',
  `courseTypeObj` int(11) NOT NULL COMMENT '会议室类型',
  `courseName` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '会议室名称',
  `coursePhoto` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '会议室照片',
  `personNum` int(11) NOT NULL COMMENT '容纳人数',
  `courseLocation` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '会议室位置',
  `price` float NOT NULL COMMENT '会议室单价',
  `courseDesc` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '详细介绍',
  `addTime` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发布时间',
  PRIMARY KEY (`courseId`) USING BTREE,
  INDEX `courseTypeObj`(`courseTypeObj`) USING BTREE,
  CONSTRAINT `t_course_ibfk_1` FOREIGN KEY (`courseTypeObj`) REFERENCES `t_coursetype` (`courseTypeId`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_course
-- ----------------------------
INSERT INTO `t_course` VALUES (1, 1, 'c语言程序设计', 'upload/ae621981-fa07-4521-90ab-d1904bee0f64.jpg', 50, '6A-101', 3.5, '讲解c语言编程知识', '2021-03-21 21:04:01');
INSERT INTO `t_course` VALUES (2, 3, '邓小平理论', 'upload/3611956f-c44e-4471-936f-1f94819ed236.jpg', 35, '6B-202', 2, '邓小平理论邓小平理论邓小平理论邓小平理论邓小平理论邓小平理论邓小平理论', '2021-03-21 21:52:13');
INSERT INTO `t_course` VALUES (3, 1, '计算机组成原理', 'upload/836606ac-38fb-4a1a-882b-6884fd75e733.jpg', 48, '6B-206', 2.5, '计算机组成原理计算机组成原理计算机组成原理计算机组成原理计算机组成原理', '2021-03-22 12:15:31');

-- ----------------------------
-- Table structure for t_coursetype
-- ----------------------------
DROP TABLE IF EXISTS `t_coursetype`;
CREATE TABLE `t_coursetype`  (
  `courseTypeId` int(11) NOT NULL AUTO_INCREMENT COMMENT '会议室类型id',
  `courseTypeName` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '会议室类型名称',
  `courseTypeDesc` varchar(800) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '会议室类型说明',
  PRIMARY KEY (`courseTypeId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_coursetype
-- ----------------------------
INSERT INTO `t_coursetype` VALUES (1, '专业课', '专业课专业课专业课专业课专业课');
INSERT INTO `t_coursetype` VALUES (2, '选修课', '选修课选修课选修课');
INSERT INTO `t_coursetype` VALUES (3, '公共课', '公共课公共课公共课公共课');

-- ----------------------------
-- Table structure for t_timeinterval
-- ----------------------------
DROP TABLE IF EXISTS `t_timeinterval`;
CREATE TABLE `t_timeinterval`  (
  `intervalId` int(11) NOT NULL AUTO_INCREMENT COMMENT '时段id',
  `intervalName` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '时段名称',
  `product` float NOT NULL COMMENT '商品数量',
  PRIMARY KEY (`intervalId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_timeinterval
-- ----------------------------
INSERT INTO `t_timeinterval` VALUES (1, '上午08:00~10:00', 2);
INSERT INTO `t_timeinterval` VALUES (2, '上午10:00~12:00', 2);
INSERT INTO `t_timeinterval` VALUES (3, '下午13:00~16:00', 3);
INSERT INTO `t_timeinterval` VALUES (4, '下午16:00~18:00', 2);
INSERT INTO `t_timeinterval` VALUES (5, '晚上19:00~22:00', 3);

-- ----------------------------
-- Table structure for t_userinfo
-- ----------------------------
DROP TABLE IF EXISTS `t_userinfo`;
CREATE TABLE `t_userinfo`  (
  `user_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'user_name',
  `password` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登录密码',
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '姓名',
  `gender` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '性别',
  `birthDate` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出生日期',
  `userPhoto` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户照片',
  `telephone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '联系电话',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '邮箱',
  `address` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '家庭地址',
  `regTime` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '注册时间',
  `openid` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`user_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_userinfo
-- ----------------------------
INSERT INTO `t_userinfo` VALUES ('13590120934', '--', '李霞', '男', '2021-03-21', 'upload/2d4c8e66-b19c-4c79-aa24-0aa2820b0616.jpg', '--', '--', '--', '2021-03-21 21:18:05', 'oM7Mu5XyeVJSc8roaUCRlcz_IP9k');
INSERT INTO `t_userinfo` VALUES ('13688886666', '123', '李忠杰', '男', '2021-03-16', 'upload/5ca1de55-d516-47ac-b7ce-b32dcfa51eac.jpg', '13508102342', 'zhongjie@126.com', '四川成都红星路5号', '2021-03-21 19:33:37', NULL);

SET FOREIGN_KEY_CHECKS = 1;
