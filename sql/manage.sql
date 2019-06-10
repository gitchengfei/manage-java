/*
 Navicat Premium Data Transfer

 Source Server         :
 Source Server Type    : MySQL
 Source Server Version : 50644
 Source Host           :
 Source Schema         : manage

 Target Server Type    : MySQL
 Target Server Version : 50644
 File Encoding         : 65001

 Date: 10/06/2019 17:52:49
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '账号表主键',
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `status` tinyint(1) NULL DEFAULT NULL COMMENT '状态:是否可用',
  `is_delete` tinyint(1) NULL DEFAULT NULL COMMENT '是否删除',
  `head_portrait` int(11) NULL DEFAULT NULL COMMENT '账号头像:文件表(file)主键',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_id` int(11) NULL DEFAULT NULL COMMENT '创建人ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改人',
  `update_id` int(11) NULL DEFAULT NULL COMMENT '修改人ID',
  `reserved_one` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预留字段1',
  `reserved_two` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预留字段2',
  `reserved_three` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预留字段3',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `account_username_index`(`username`) USING BTREE,
  INDEX `account_phone_index`(`phone`) USING BTREE,
  INDEX `account_email_index`(`email`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO `account` VALUES (1, 'admin', '67f43efc5701784db1504e4993d7e393', '系统超级管理员', NULL, NULL, 1, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for account_role
-- ----------------------------
DROP TABLE IF EXISTS `account_role`;
CREATE TABLE `account_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户角色表主键',
  `account_id` int(11) NULL DEFAULT NULL COMMENT '用户ID',
  `role_id` int(11) NULL DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for db_log
-- ----------------------------
DROP TABLE IF EXISTS `db_log`;
CREATE TABLE `db_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '数据库操作日志表主键',
  `account_id` int(11) NULL DEFAULT NULL COMMENT '操作人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '操作时间',
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据字典中数据库操作日志类型',
  `log` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作内容',
  `reserved_one` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预留字段1',
  `reserved_two` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预留字段2',
  `reserved_three` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预留字段3',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `db_log_type_index`(`type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of db_log
-- ----------------------------
INSERT INTO `db_log` VALUES (1, 1, '2019-06-10 04:52:25', 'SYSTEM_DB_LOG_TYPE_ROLE', '更新了【 系超级管理员 】角色权限！', NULL, NULL, NULL);

-- ----------------------------
-- Table structure for dictionary
-- ----------------------------
DROP TABLE IF EXISTS `dictionary`;
CREATE TABLE `dictionary`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '数据字典表主键',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'key',
  `value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'value',
  `display_order` int(11) NULL DEFAULT NULL COMMENT '排序码',
  `parent_id` int(11) NULL DEFAULT NULL COMMENT '父节点',
  `status` tinyint(1) NULL DEFAULT NULL COMMENT '状态:0不可用,1可用',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_id` int(11) NULL DEFAULT NULL COMMENT '创建人ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `update_id` int(11) NULL DEFAULT NULL COMMENT '修改人ID',
  `reserved_one` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预留字段1',
  `reserved_two` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预留字段2',
  `reserved_three` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预留字段3',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `dictionaries_code_index`(`code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of dictionary
-- ----------------------------
INSERT INTO `dictionary` VALUES (1, '系统', 'SYSTEM', '', 10, 0, 1, '2019-04-23 00:41:21', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `dictionary` VALUES (2, '用户', 'ACCOUNT', '', 8, 0, 1, '2019-04-23 00:41:33', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `dictionary` VALUES (3, '数据库操作日志', 'SYSTEM_DB_LOG', '', 1, 1, 1, '2019-04-23 00:46:04', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `dictionary` VALUES (4, '数据库操作日志删除选项', 'SYSTEM_DB_LOG_DELETE_OPTION', '', 2, 1, 1, '2019-04-23 00:47:34', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `dictionary` VALUES (5, '三个月以前', 'SYSTEM_DB_LOG_DELETE_OPTION_THREE_MONTH', '90', 1, 4, 1, '2019-04-23 00:47:57', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `dictionary` VALUES (6, '一个月以前', 'SYSTEM_DB_LOG_DELETE_OPTION_ONE_MONTH', '30', 2, 4, 1, '2019-04-23 00:48:16', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `dictionary` VALUES (7, '一周以前', 'SYSTEM_DB_LOG_DELETE_OPTION_ONE_WEEK', '7', 3, 4, 1, '2019-04-23 00:48:30', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `dictionary` VALUES (8, '数据库操作日志类型', 'SYSTEM_DB_LOG_TYPE', '', 1, 3, 1, '2019-04-23 00:48:46', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `dictionary` VALUES (9, '菜单', 'SYSTEM_DB_LOG_TYPE_MENU', '', 100, 8, 1, '2019-04-23 00:49:00', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `dictionary` VALUES (10, '数据字典', 'SYSTEM_DB_LOG_TYPE_DICTIONARY', '', 97, 8, 1, '2019-04-23 00:49:10', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `dictionary` VALUES (11, '数据库操作日志', 'SYSTEM_DB_LOG_TYPE_DB_LOG', '', 98, 8, 1, '2019-04-23 00:49:23', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `dictionary` VALUES (12, '权限管理', 'SYSTEM_DB_LOG_TYPE_PERMISSION', '', 99, 8, 1, '2019-04-23 00:50:46', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `dictionary` VALUES (13, '角色管理', 'SYSTEM_DB_LOG_TYPE_ROLE', '', 96, 8, 1, '2019-04-28 16:11:12', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `dictionary` VALUES (14, '账号', 'SYSTEM_DB_LOG_TYPE_ACCOUNT', '', 95, 8, 1, '2019-05-03 01:52:28', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `dictionary` VALUES (15, '文件', 'FILE', '', 9, 0, 1, '2019-06-04 00:50:58', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `dictionary` VALUES (16, '文件管理', 'SYSTEM_DB_LOG_TYPE_FILE', '', 94, 8, 1, '2019-06-04 00:55:25', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `dictionary` VALUES (17, '类型', 'FILE_TYPE', '', 1, 15, 1, '2019-06-04 00:58:43', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `dictionary` VALUES (18, '用途', 'FILE_PURPOSE', '', 2, 15, 1, '2019-06-04 00:59:31', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `dictionary` VALUES (19, '图片', 'FILE_TYPE_IMAGE', '', 1, 17, 1, '2019-06-04 01:01:24', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `dictionary` VALUES (20, '视频', 'FILE_TYPE_VIDEO', '', 2, 17, 1, '2019-06-04 01:01:49', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `dictionary` VALUES (21, '音频', 'FILE_TYPE_AUDIO', '', 3, 17, 1, '2019-06-04 01:02:45', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `dictionary` VALUES (22, '其他', 'FILE_TYPE_OTHER', '', 4, 17, 1, '2019-06-04 01:03:09', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `dictionary` VALUES (23, '账号头像', 'FILE_PURPOSE_ACCOUNT_HEAD_PORTRAIT', '', 1, 18, 1, '2019-06-04 01:04:18', 1, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for file
-- ----------------------------
DROP TABLE IF EXISTS `file`;
CREATE TABLE `file`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '文件表主键',
  `file_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件名称',
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件上传地址',
  `file_extension` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件扩展名',
  `file_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件类型',
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类型(引用数据字典中的固定编码)',
  `file_purpose` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件用途(引用数据字典固定编码)',
  `size` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件大小',
  `time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件时长(视频文件使用)',
  `is_delete` tinyint(1) NULL DEFAULT NULL COMMENT '是否已删除',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_id` int(11) NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `update_id` int(11) NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `file_type_index`(`file_type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键自动递增',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单名',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单转跳URL',
  `parent_id` int(11) NULL DEFAULT NULL COMMENT '上级菜单ID',
  `display_order` int(11) NULL DEFAULT NULL COMMENT '菜单排序码',
  `status` tinyint(1) NULL DEFAULT NULL COMMENT '状态:启用/禁用',
  `has_children` tinyint(1) NULL DEFAULT NULL COMMENT '是否存在子菜单',
  `has_permission` tinyint(1) NULL DEFAULT NULL COMMENT '是否配置了权限',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_id` int(11) NULL DEFAULT NULL COMMENT '创建人ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `update_id` int(11) NULL DEFAULT NULL COMMENT '修改人ID',
  `reserved_one` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预留字段1',
  `reserved_two` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预留字段2',
  `reserved_three` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预留字段3',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES (1, '系统设置', '/system', 0, 10, 1, 1, 0, '2019-04-28 22:47:03', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `menu` VALUES (2, '账号管理', '/account', 0, 1, 1, 1, 0, '2019-04-28 22:50:13', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `menu` VALUES (3, '菜单设置', '/system/menu', 1, 1, 1, 0, 1, '2019-04-28 22:50:50', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `menu` VALUES (4, '权限管理', '/system/permission', 1, 2, 1, 0, 1, '2019-04-28 22:52:15', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `menu` VALUES (5, '数据字典', '/system/dictionary', 1, 3, 1, 0, 1, '2019-04-28 22:52:49', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `menu` VALUES (6, '数据库操作日志', '/system/db/log', 1, 4, 1, 0, 1, '2019-04-28 22:53:14', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `menu` VALUES (7, '接口文档', '/system/api', 1, 5, 1, 0, 0, '2019-04-28 22:54:06', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `menu` VALUES (8, '账号管理', '/account/account', 2, 1, 1, 0, 1, '2019-04-28 22:54:43', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `menu` VALUES (9, '角色管理', '/account/role', 2, 2, 1, 0, 1, '2019-04-28 22:55:06', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `menu` VALUES (13, '文件管理', '/file', 0, 9, 1, 1, 0, '2019-05-30 22:18:46', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `menu` VALUES (14, '文件管理', '/file/file', 13, 1, 1, 0, 1, '2019-05-30 22:28:07', 1, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增长主键',
  `menu_id` int(11) NULL DEFAULT NULL COMMENT '权限所属菜单',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限名',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '该权限绑定的url',
  `display_order` int(255) NULL DEFAULT NULL COMMENT '排序码',
  `status` tinyint(255) NULL DEFAULT NULL COMMENT '状态:启用/禁用',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_id` bigint(20) NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_id` bigint(20) NULL DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `reserved_one` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预留字段1',
  `reserved_two` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预留字段2',
  `reserved_three` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预留字段3',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 36 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES (1, 3, '新增菜单', '/system/menu/save', 2, 1, '是否拥有新增菜单的权限', 1, '2019-04-30 00:06:52', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `permission` VALUES (2, 3, '修改菜单', '/system/menu/update', 3, 1, '是否拥有修改菜单的权限', 1, '2019-04-30 00:23:53', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `permission` VALUES (3, 4, '新增权限', '/system/permission/save', 2, 1, '是否拥有新增权限的权限', 1, '2019-04-30 00:39:34', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `permission` VALUES (4, 3, '修改菜单状态', '/system/menu/update/status', 4, 1, '是否拥有修改菜单状态的权限', 1, '2019-04-30 00:44:10', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `permission` VALUES (5, 4, '修改权限', '/system/permission/update', 3, 1, '是否有用修改权限的权限', 1, '2019-04-30 00:45:40', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `permission` VALUES (6, 5, '新增数据字典', '/system/dictionary/save', 2, 1, '是否拥有新增数据字典的权限', 1, '2019-04-30 00:47:36', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `permission` VALUES (7, 5, '修改数据字典', '/system/dictionary/update', 3, 1, '是否拥有修改数据字典的权限', 1, '2019-04-30 00:48:39', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `permission` VALUES (8, 3, '删除菜单', '/system/menu/delete', 5, 1, '是否拥删除菜单的权限', 1, '2019-04-30 03:09:55', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `permission` VALUES (9, 4, '修改权限状态', '/system/permission/update/status', 4, 1, '是否拥有修改权限状态的权限', 1, '2019-04-30 03:20:30', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `permission` VALUES (10, 4, '删除权限', '/system/permission/delete', 5, 1, '是否拥有删除权限的权限', 1, '2019-04-30 12:22:15', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `permission` VALUES (11, 5, '修改数据字典状态', '/system/dictionary/update/status', 4, 1, '是否拥有修改数据字典状态的权限', 1, '2019-04-30 12:32:20', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `permission` VALUES (12, 5, '删除数据字典', '/system/dictionary/delete', 5, 1, '是否拥有删除数据字典权限', 1, '2019-04-30 12:44:14', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `permission` VALUES (13, 6, '删除数据库操作日志', '/system/db/log/delete', 2, 1, '是否拥有删除数据操作日志权限', 1, '2019-04-30 12:52:36', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `permission` VALUES (14, 8, '新增账号', '/account/account/save', 2, 1, '是否拥有新增账号的权限', 1, '2019-04-30 13:04:06', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `permission` VALUES (15, 8, '修改账号', '/account/account/update', 3, 1, '是否拥有修改账号的权限', 1, '2019-04-30 15:08:51', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `permission` VALUES (16, 8, '修改账号状态', '/account/account/update/status', 4, 1, '是否拥有修改账号状态权限', 1, '2019-05-16 17:05:09', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `permission` VALUES (18, 6, '查看列表', '/system/db/log/list', 1, 1, '是否拥有查看数据库操作日志列表权限', 1, '2019-05-23 21:05:10', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `permission` VALUES (19, 4, '查看列表', '/system/permission/list', 1, 1, '是否拥有查看列表数据的权限', 1, '2019-05-23 21:23:51', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `permission` VALUES (20, 3, '查看列表', '/system/menu/list', 1, 1, '是否拥有查看列表权限', 1, '2019-05-23 21:25:37', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `permission` VALUES (21, 5, '查看列表', '/system/dictionary/list', 1, 1, '是否拥有查看列表的权限', 1, '2019-05-23 21:27:12', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `permission` VALUES (22, 9, '查看列表', '/account/role/list', 1, 1, '是否拥有查看列表的权限', 1, '2019-05-24 01:31:50', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `permission` VALUES (23, 9, '删除角色', '/account/role/delete', 6, 1, '是否拥有删除角色的权限', 1, '2019-05-24 01:32:28', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `permission` VALUES (24, 9, '修改角色状态', '/account/role/update/status', 4, 1, '是否拥有修改角色状态的权限', 1, '2019-05-24 01:33:24', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `permission` VALUES (25, 9, '修改角色', '/account/role/update', 3, 1, '是否拥有修改角色的权限', 1, '2019-05-24 01:34:01', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `permission` VALUES (26, 9, '新增角色', '/account/role/save', 2, 1, '是否拥有新增角色的权限', 1, '2019-05-24 01:34:32', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `permission` VALUES (27, 8, '查看列表', '/account/account/list', 1, 1, '是否拥有查看账号列表的权限', 1, '2019-05-24 01:37:23', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `permission` VALUES (28, 8, '重置账号密码', '/account/account/reset', 5, 1, '是否拥有重置账号密码的权限', 1, '2019-05-24 01:38:28', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `permission` VALUES (29, 8, '设置账号角色', '/account/account/update/role', 6, 1, '是否拥有设置账号角色的权限', 1, '2019-05-24 01:41:01', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `permission` VALUES (30, 8, '删除账号', '/account/account/delete', 7, 1, '是否拥有删除账号的权限', 1, '2019-05-24 01:42:39', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `permission` VALUES (31, 9, '设置角色权限', '/account/role/permission/update', 5, 1, '是否拥有设置角色权限的权限', 1, '2019-05-24 02:02:55', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `permission` VALUES (32, 14, '查看列表', '/file/list', 1, 1, '是否拥有查看文件列表的权限', 1, '2019-06-04 02:35:26', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `permission` VALUES (33, 14, '修改文件名', '/file/update/name', 3, 1, '是否拥有修改文件名的权限', 1, '2019-06-04 02:38:59', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `permission` VALUES (34, 14, '删除文件', '/file/delete', 4, 1, '是否拥有删除文件的权限', 1, '2019-06-04 02:39:28', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `permission` VALUES (35, 14, '下载文件', '/file/download', 2, 1, '是否拥有下载文件的权限', 1, '2019-06-04 05:09:47', NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色表主键',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名',
  `display_order` int(2) NULL DEFAULT NULL COMMENT '排序码',
  `status` tinyint(1) NULL DEFAULT NULL COMMENT '状态',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_id` int(11) NULL DEFAULT NULL COMMENT '创建人ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `update_id` int(11) NULL DEFAULT NULL COMMENT '修改人ID',
  `reserved_one` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预留字段1',
  `reserved_two` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预留字段2',
  `reserved_three` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预留字段3',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, '系超级管理员', 1, 1, '拥有系统所有权限', '2019-04-28 22:59:33', 1, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '菜单吧角色表主键',
  `menu_id` int(11) NULL DEFAULT NULL COMMENT '菜单id',
  `role_id` int(11) NULL DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of role_menu
-- ----------------------------
INSERT INTO `role_menu` VALUES (1, 2, 1);
INSERT INTO `role_menu` VALUES (2, 8, 1);
INSERT INTO `role_menu` VALUES (3, 9, 1);
INSERT INTO `role_menu` VALUES (4, 13, 1);
INSERT INTO `role_menu` VALUES (5, 14, 1);
INSERT INTO `role_menu` VALUES (6, 1, 1);
INSERT INTO `role_menu` VALUES (7, 3, 1);
INSERT INTO `role_menu` VALUES (8, 4, 1);
INSERT INTO `role_menu` VALUES (9, 5, 1);
INSERT INTO `role_menu` VALUES (10, 6, 1);
INSERT INTO `role_menu` VALUES (11, 7, 1);

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色权限表主键',
  `role_id` int(11) NULL DEFAULT NULL COMMENT '角色id',
  `permission_id` int(11) NULL DEFAULT NULL COMMENT '权限ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of role_permission
-- ----------------------------
INSERT INTO `role_permission` VALUES (1, 1, 27);
INSERT INTO `role_permission` VALUES (2, 1, 14);
INSERT INTO `role_permission` VALUES (3, 1, 15);
INSERT INTO `role_permission` VALUES (4, 1, 16);
INSERT INTO `role_permission` VALUES (5, 1, 28);
INSERT INTO `role_permission` VALUES (6, 1, 29);
INSERT INTO `role_permission` VALUES (7, 1, 30);
INSERT INTO `role_permission` VALUES (8, 1, 22);
INSERT INTO `role_permission` VALUES (9, 1, 26);
INSERT INTO `role_permission` VALUES (10, 1, 25);
INSERT INTO `role_permission` VALUES (11, 1, 24);
INSERT INTO `role_permission` VALUES (12, 1, 31);
INSERT INTO `role_permission` VALUES (13, 1, 23);
INSERT INTO `role_permission` VALUES (14, 1, 32);
INSERT INTO `role_permission` VALUES (15, 1, 35);
INSERT INTO `role_permission` VALUES (16, 1, 33);
INSERT INTO `role_permission` VALUES (17, 1, 34);
INSERT INTO `role_permission` VALUES (18, 1, 20);
INSERT INTO `role_permission` VALUES (19, 1, 1);
INSERT INTO `role_permission` VALUES (20, 1, 2);
INSERT INTO `role_permission` VALUES (21, 1, 4);
INSERT INTO `role_permission` VALUES (22, 1, 8);
INSERT INTO `role_permission` VALUES (23, 1, 19);
INSERT INTO `role_permission` VALUES (24, 1, 3);
INSERT INTO `role_permission` VALUES (25, 1, 5);
INSERT INTO `role_permission` VALUES (26, 1, 9);
INSERT INTO `role_permission` VALUES (27, 1, 10);
INSERT INTO `role_permission` VALUES (28, 1, 21);
INSERT INTO `role_permission` VALUES (29, 1, 6);
INSERT INTO `role_permission` VALUES (30, 1, 7);
INSERT INTO `role_permission` VALUES (31, 1, 11);
INSERT INTO `role_permission` VALUES (32, 1, 12);
INSERT INTO `role_permission` VALUES (33, 1, 18);
INSERT INTO `role_permission` VALUES (34, 1, 13);

-- ----------------------------
-- Table structure for unused_file
-- ----------------------------
DROP TABLE IF EXISTS `unused_file`;
CREATE TABLE `unused_file`  (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '未使用文件表主键',
  `file_id` int(20) NULL DEFAULT NULL COMMENT '文件Id',
  `upload_time` datetime(0) NULL DEFAULT NULL COMMENT '上传时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for use_file
-- ----------------------------
DROP TABLE IF EXISTS `use_file`;
CREATE TABLE `use_file`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '已使用文件主键',
  `file_id` int(11) NULL DEFAULT NULL COMMENT '文件Id',
  `table_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '使用表格名称',
  `column_id` int(11) NULL DEFAULT NULL COMMENT '引用文件记录Id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
