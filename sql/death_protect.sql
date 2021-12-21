SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `death_protect`;
CREATE TABLE `death_protect`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `uuid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uuid`(`uuid`(191)) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Compact;

INSERT INTO `death_protect` VALUES (1, '927bfaba-6100-4613-8102-1152f72d88c3');
INSERT INTO `death_protect` VALUES (2, '85badb0d-cfdf-420c-89cf-8988f36987a9');
INSERT INTO `death_protect` VALUES (3, '17c5c949-19d0-440b-9ecd-9454dbe26758');
INSERT INTO `death_protect` VALUES (4, 'ea300e16-1d92-4822-8891-76a808e0b6d8');
INSERT INTO `death_protect` VALUES (5, '02b37e00-567a-4828-b72f-5770a5fbdfee');

SET FOREIGN_KEY_CHECKS = 1;