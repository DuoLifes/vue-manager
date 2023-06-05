/*
 Navicat Premium Data Transfer

 Source Server         : localhost3306
 Source Server Type    : MySQL
 Source Server Version : 80017
 Source Host           : localhost:3306
 Source Schema         : dd

 Target Server Type    : MySQL
 Target Server Version : 80017
 File Encoding         : 65001

 Date: 10/02/2022 15:30:20
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for animation
-- ----------------------------
DROP TABLE IF EXISTS `animation`;
CREATE TABLE `animation`  (
  `id` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '动画id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '动画名称',
  `picture` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预览图',
  `publish_T` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '开播时间',
  `region` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所属国家',
  `play_D` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '播放渠道',
  `number` int(11) NULL DEFAULT NULL COMMENT '集数',
  `sort` tinyint(3) NULL DEFAULT NULL COMMENT '排序',
  `creation_T` date NULL DEFAULT NULL COMMENT '创建时间',
  `change_T` date NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of animation
-- ----------------------------
INSERT INTO `animation` VALUES ('138199', '樱桃小丸子', 'https://z3.ax1x.com/2021/11/27/omUyLR.jpg', '1990-01-07', '日本', '电视', 141, NULL, NULL, NULL);
INSERT INTO `animation` VALUES ('18091', '最游记', 'https://z3.ax1x.com/2021/11/27/omdMvQ.jpg', '2000-04-04', '日本', '电视', 50, NULL, NULL, NULL);
INSERT INTO `animation` VALUES ('205976', '皇家国教骑士团', 'https://z3.ax1x.com/2021/11/27/omwnd1.jpg', '2001-10-18', '日本', '电视', 13, NULL, NULL, NULL);
INSERT INTO `animation` VALUES ('325581', '虫师', 'https://z3.ax1x.com/2021/11/27/omyEQK.jpg', '2005-10-22', '日本', '电视', 26, NULL, NULL, NULL);
INSERT INTO `animation` VALUES ('359969', '天空之城', 'https://z3.ax1x.com/2021/11/27/omaf7q.jpg', '1986-08-02', '日本', '电影', 1, NULL, NULL, NULL);
INSERT INTO `animation` VALUES ('396333', '星际牛仔', 'https://z3.ax1x.com/2021/11/27/omYNdK.jpg', '1998-10-23', '日本', '电视', 26, NULL, NULL, NULL);
INSERT INTO `animation` VALUES ('397528', '火影忍者', 'https://z3.ax1x.com/2021/11/27/omBTaR.jpg', '2002-10-03', '日本', '电视', 220, NULL, NULL, NULL);
INSERT INTO `animation` VALUES ('437832', '新世纪福音战士剧场版 死与新生', 'https://z3.ax1x.com/2021/11/27/omaY1e.jpg', '1997-03-15', '日本', '电影', 1, NULL, NULL, NULL);
INSERT INTO `animation` VALUES ('487831', 'AIR', 'https://z3.ax1x.com/2021/11/27/omyHmD.jpg', '2005-01-06', '日本', '电视', 12, NULL, NULL, NULL);
INSERT INTO `animation` VALUES ('520188', '灼眼的夏娜', 'https://z3.ax1x.com/2021/11/27/omsU8x.jpg', '2005-10-05', '日本', '电视', 24, NULL, NULL, NULL);
INSERT INTO `animation` VALUES ('530864', '人形电脑天使心', 'https://z3.ax1x.com/2021/11/27/omDuon.jpg', '2002-04-02', '日本', '电视', 24, NULL, NULL, NULL);
INSERT INTO `animation` VALUES ('536923', '新世纪福音战士', 'https://z3.ax1x.com/2021/11/27/om8UIg.jpg', '1995-10-04', '日本', '电视', 26, NULL, NULL, NULL);
INSERT INTO `animation` VALUES ('579301', '死神', 'https://z3.ax1x.com/2021/11/27/omrDNn.jpg', '2004-10-05', '日本', '电视', 366, NULL, NULL, NULL);
INSERT INTO `animation` VALUES ('662773', '通灵王', 'https://z3.ax1x.com/2021/11/27/omd7IP.jpg', '2001-07-04', '日本', '电视', 64, NULL, NULL, NULL);
INSERT INTO `animation` VALUES ('665276', '新世纪福音战士剧场版 Air/真心为你', 'https://z3.ax1x.com/2021/11/27/omNFun.jpg', '1997-07-19', '日本', '电影', 2, NULL, NULL, NULL);
INSERT INTO `animation` VALUES ('694817', '千与千寻', 'https://z3.ax1x.com/2021/11/27/om01kq.jpg', '2001-07-20', '日本', '电影', 1, NULL, NULL, NULL);
INSERT INTO `animation` VALUES ('789903', '混沌武士', 'https://z3.ax1x.com/2021/11/27/omrXHH.jpg', '2004-05-19', '日本', '电视', 26, NULL, NULL, NULL);
INSERT INTO `animation` VALUES ('795792', '速写者', 'https://z3.ax1x.com/2021/11/27/omyrlV.jpg', '2005-04-07', '日本', '电视', 24, NULL, NULL, NULL);
INSERT INTO `animation` VALUES ('851704', '钢之炼金术师', 'https://z3.ax1x.com/2021/11/27/omDOkn.jpg', '2003-10-04', '日本', '电视', 51, NULL, NULL, NULL);
INSERT INTO `animation` VALUES ('851989', '地狱少女', 'https://z3.ax1x.com/2021/11/27/oms5qg.jpg', '2005-10-04', '日本', '电视', 26, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `id` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '评论id',
  `user_id` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `name_id` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '动画id',
  `conten` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '评论内容',
  `sort` tinyint(3) NULL DEFAULT NULL COMMENT '排序',
  `creation_T` date NULL DEFAULT NULL COMMENT '创建时间',
  `change_T` date NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `dh`(`name_id`) USING BTREE COMMENT '评论动画id对应 动画id',
  CONSTRAINT `dh` FOREIGN KEY (`name_id`) REFERENCES `animation` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comment
-- ----------------------------

-- ----------------------------
-- Table structure for dd_menu
-- ----------------------------
DROP TABLE IF EXISTS `dd_menu`;
CREATE TABLE `dd_menu`  (
  `id` char(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'id',
  `pid` char(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '父菜单id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '菜单名',
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '菜单类型（目录，按钮）',
  `permission` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '菜单权限',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'url',
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'path',
  `component` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '组件',
  `iconCls` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '图标',
  `keepAlive` tinyint(1) NULL DEFAULT NULL COMMENT '是否保持激活',
  `requireAuth` tinyint(1) NULL DEFAULT NULL COMMENT '是否要求权限',
  `enabled` tinyint(1) NULL DEFAULT NULL COMMENT '是否启用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dd_menu
-- ----------------------------
INSERT INTO `dd_menu` VALUES ('1490549095108968448', '0', '首页', 'menu', 'home', NULL, '/', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `dd_menu` VALUES ('1490549401968443392', '0', '权限管理', 'menu', 'security', NULL, '/security', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `dd_menu` VALUES ('1490549872766484480', '1490549401968443392', '用户管理', 'menu', 'security', NULL, 'user', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `dd_menu` VALUES ('1490549939053264896', '1490549401968443392', '角色管理', 'menu', 'security', NULL, 'role', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `dd_menu` VALUES ('1490550012319367168', '1490549401968443392', '菜单管理', 'menu', 'security', NULL, 'menu', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `dd_menu` VALUES ('1490551718331547648', '1490549872766484480', '添加按钮', 'button', 'btn:security', 'security/dd-user-role/add', '', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `dd_menu` VALUES ('1490633481049341952', '1490549872766484480', '展示权限（用户是否可以浏览此页面）', 'view', '/', NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for dd_role
-- ----------------------------
DROP TABLE IF EXISTS `dd_role`;
CREATE TABLE `dd_role`  (
  `id` char(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '角色名',
  `name_zh` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '角色描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dd_role
-- ----------------------------
INSERT INTO `dd_role` VALUES ('1', 'ROLE_admin', '超级管理员');
INSERT INTO `dd_role` VALUES ('1490553559727472640', 'ROLE_author', '作者');
INSERT INTO `dd_role` VALUES ('2', 'ROLE_user', '普通用户');

-- ----------------------------
-- Table structure for dd_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `dd_role_menu`;
CREATE TABLE `dd_role_menu`  (
  `rid` char(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '角色id',
  `mid` char(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '菜单id'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dd_role_menu
-- ----------------------------
INSERT INTO `dd_role_menu` VALUES ('1', '1490549095108968448');
INSERT INTO `dd_role_menu` VALUES ('1', '1490549401968443392');
INSERT INTO `dd_role_menu` VALUES ('1', '1490549872766484480');
INSERT INTO `dd_role_menu` VALUES ('1', '1490551718331547648');
INSERT INTO `dd_role_menu` VALUES ('1', '1490549939053264896');
INSERT INTO `dd_role_menu` VALUES ('1', '1490550012319367168');
INSERT INTO `dd_role_menu` VALUES ('1490553559727472640', '1490549095108968448');
INSERT INTO `dd_role_menu` VALUES ('2', '1490549095108968448');
INSERT INTO `dd_role_menu` VALUES ('2', '1490633481049341952');
INSERT INTO `dd_role_menu` VALUES ('2', '1490549939053264896');
INSERT INTO `dd_role_menu` VALUES ('2', '1490550012319367168');

-- ----------------------------
-- Table structure for dd_topic
-- ----------------------------
DROP TABLE IF EXISTS `dd_topic`;
CREATE TABLE `dd_topic`  (
  `id` char(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `releaseDate` datetime(0) NULL DEFAULT NULL,
  `imagine` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `subjectId` char(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `sort` int(2) NULL DEFAULT NULL,
  `gmt_create` datetime(0) NULL DEFAULT NULL,
  `gmt_modified` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dd_topic
-- ----------------------------

-- ----------------------------
-- Table structure for dd_user
-- ----------------------------
DROP TABLE IF EXISTS `dd_user`;
CREATE TABLE `dd_user`  (
  `id` char(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户id',
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '密码',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dd_user
-- ----------------------------
INSERT INTO `dd_user` VALUES ('1', 'admin', '$2a$10$/BnlSX.KM14t9c9j5p9TUerKXkpuvcvmvEf5J2i2Jb8OEv9Dp178K');
INSERT INTO `dd_user` VALUES ('1490553814707601408', '游客', '$2a$10$hidoRf81Kpibr1seSl7QVeOrm/3x2rpwzWYkRsKxjcm6sd192ZoN2');
INSERT INTO `dd_user` VALUES ('1490553884043640832', '作者', '$2a$10$TEvZ5Cd/HxItTFzNpC3n3e7AmfA5KVXndvMdo6a99VtN1Rb7EIyyy');

-- ----------------------------
-- Table structure for dd_user_role
-- ----------------------------
DROP TABLE IF EXISTS `dd_user_role`;
CREATE TABLE `dd_user_role`  (
  `uid` char(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户id',
  `rid` char(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '角色id'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dd_user_role
-- ----------------------------
INSERT INTO `dd_user_role` VALUES ('1489965313679163392', '1');
INSERT INTO `dd_user_role` VALUES ('2', '1');
INSERT INTO `dd_user_role` VALUES ('2', '1490188252357853184');
INSERT INTO `dd_user_role` VALUES ('2', '2');
INSERT INTO `dd_user_role` VALUES ('1489978103777198080', '1490188227389161472');
INSERT INTO `dd_user_role` VALUES ('1489978103777198080', '1490188199299907584');
INSERT INTO `dd_user_role` VALUES ('1489978103777198080', '1490188252357853184');
INSERT INTO `dd_user_role` VALUES ('1490553814707601408', '2');
INSERT INTO `dd_user_role` VALUES ('1490553884043640832', '1490553559727472640');
INSERT INTO `dd_user_role` VALUES ('1', '1');
INSERT INTO `dd_user_role` VALUES ('1', '1490553559727472640');
INSERT INTO `dd_user_role` VALUES ('1', '2');
INSERT INTO `dd_user_role` VALUES ('1', '1');
INSERT INTO `dd_user_role` VALUES ('1', '1490553559727472640');
INSERT INTO `dd_user_role` VALUES ('1', '2');

-- ----------------------------
-- Table structure for recommend
-- ----------------------------
DROP TABLE IF EXISTS `recommend`;
CREATE TABLE `recommend`  (
  `A_id` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '需要推荐的动画id',
  `creation_T` date NULL DEFAULT NULL COMMENT '创建时间',
  `change_T` date NULL DEFAULT NULL COMMENT '修改时间',
  `sort` tinyint(3) NULL DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`A_id`) USING BTREE,
  INDEX `dh1`(`A_id`) USING BTREE COMMENT '推荐动画id 对应动画id',
  CONSTRAINT `dh1` FOREIGN KEY (`A_id`) REFERENCES `animation` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of recommend
-- ----------------------------
INSERT INTO `recommend` VALUES ('138199', '2021-12-08', '2021-12-08', 0);
INSERT INTO `recommend` VALUES ('18091', '2021-12-08', '2021-12-08', 1);
INSERT INTO `recommend` VALUES ('205976', '2021-12-08', '2021-12-08', 3);
INSERT INTO `recommend` VALUES ('325581', '2021-12-08', '2021-12-08', 2);
INSERT INTO `recommend` VALUES ('359969', '2021-12-10', '2021-12-10', 6);
INSERT INTO `recommend` VALUES ('396333', '2021-12-10', '2021-12-10', 7);
INSERT INTO `recommend` VALUES ('437832', '2021-12-08', '2021-12-08', 4);
INSERT INTO `recommend` VALUES ('520188', '2021-12-10', '2021-12-10', 5);

-- ----------------------------
-- Table structure for tag
-- ----------------------------
DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag`  (
  `id` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标签id',
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标签类型',
  `sort` tinyint(3) NULL DEFAULT NULL COMMENT '排序',
  `creation_T` date NULL DEFAULT NULL COMMENT '创建时间',
  `change_T` date NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tag
-- ----------------------------
INSERT INTO `tag` VALUES ('1', '家庭', NULL, NULL, NULL);
INSERT INTO `tag` VALUES ('10', '少儿', NULL, NULL, NULL);
INSERT INTO `tag` VALUES ('11', '青春', NULL, NULL, NULL);
INSERT INTO `tag` VALUES ('12', '社会', NULL, NULL, NULL);
INSERT INTO `tag` VALUES ('13', '日常', NULL, NULL, NULL);
INSERT INTO `tag` VALUES ('14', '热血', NULL, NULL, NULL);
INSERT INTO `tag` VALUES ('15', '催泪', NULL, NULL, NULL);
INSERT INTO `tag` VALUES ('16', '治愈', NULL, NULL, NULL);
INSERT INTO `tag` VALUES ('17', '搞笑', NULL, NULL, NULL);
INSERT INTO `tag` VALUES ('18', '励志', NULL, NULL, NULL);
INSERT INTO `tag` VALUES ('19', '悬疑', NULL, NULL, NULL);
INSERT INTO `tag` VALUES ('2', '校园', NULL, NULL, NULL);
INSERT INTO `tag` VALUES ('20', '音乐', NULL, NULL, NULL);
INSERT INTO `tag` VALUES ('21', '美食', NULL, NULL, NULL);
INSERT INTO `tag` VALUES ('22', '运动', NULL, NULL, NULL);
INSERT INTO `tag` VALUES ('23', '战斗', NULL, NULL, NULL);
INSERT INTO `tag` VALUES ('24', '冒险', NULL, NULL, NULL);
INSERT INTO `tag` VALUES ('25', '游戏', NULL, NULL, NULL);
INSERT INTO `tag` VALUES ('26', '推理', NULL, NULL, NULL);
INSERT INTO `tag` VALUES ('27', '战争', NULL, NULL, NULL);
INSERT INTO `tag` VALUES ('28', '历史', NULL, NULL, NULL);
INSERT INTO `tag` VALUES ('29', '哲学', NULL, NULL, NULL);
INSERT INTO `tag` VALUES ('3', '职场', NULL, NULL, NULL);
INSERT INTO `tag` VALUES ('30', '穿越', NULL, NULL, NULL);
INSERT INTO `tag` VALUES ('31', '机战', NULL, NULL, NULL);
INSERT INTO `tag` VALUES ('32', '智斗', NULL, NULL, NULL);
INSERT INTO `tag` VALUES ('33', '偶像', NULL, NULL, NULL);
INSERT INTO `tag` VALUES ('4', '架空', NULL, NULL, NULL);
INSERT INTO `tag` VALUES ('5', '奇幻', NULL, NULL, NULL);
INSERT INTO `tag` VALUES ('6', '科幻', NULL, NULL, NULL);
INSERT INTO `tag` VALUES ('7', '亲情', NULL, NULL, NULL);
INSERT INTO `tag` VALUES ('8', '友情', NULL, NULL, NULL);
INSERT INTO `tag` VALUES ('9', '恋爱', NULL, NULL, NULL);

-- ----------------------------
-- Table structure for tag_animation
-- ----------------------------
DROP TABLE IF EXISTS `tag_animation`;
CREATE TABLE `tag_animation`  (
  `T_id` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标签id',
  `A_id` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '动画id',
  `creation_T` date NULL DEFAULT NULL COMMENT '创建时间',
  `change_T` date NULL DEFAULT NULL COMMENT '修改时间',
  `sort` tinyint(3) NULL DEFAULT NULL COMMENT '排序',
  INDEX `bq`(`T_id`) USING BTREE COMMENT '标签类型id 对应标签类型',
  INDEX `dh2`(`A_id`) USING BTREE COMMENT '标签动画id 对应动画id',
  CONSTRAINT `bq` FOREIGN KEY (`T_id`) REFERENCES `tag` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `dh2` FOREIGN KEY (`A_id`) REFERENCES `animation` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tag_animation
-- ----------------------------
INSERT INTO `tag_animation` VALUES ('1', '138199', '2021-12-08', '2021-12-08', 0);
INSERT INTO `tag_animation` VALUES ('1', '18091', '2021-12-08', '2021-12-08', 1);
INSERT INTO `tag_animation` VALUES ('2', '205976', '2021-12-08', '2021-12-08', 2);
INSERT INTO `tag_animation` VALUES ('3', '138199', '2021-12-08', '2021-12-08', 3);

SET FOREIGN_KEY_CHECKS = 1;
