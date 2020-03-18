CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_dt` datetime NOT NULL DEFAULT '0000-01-01 00:00:00',
  `updated_dt` datetime NOT NULL DEFAULT '0000-01-01 00:00:00',
  `telphone` varchar(40) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `password` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `nick_name` varchar(40) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `gender` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `tel_unique_index` (`telphone`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


CREATE TABLE `dianpingdb`.`seller`  (
                                        `id` int(0) NOT NULL AUTO_INCREMENT,
                                        `name` varchar(80) NOT NULL DEFAULT '',
                                        `created_at` datetime(0) NOT NULL DEFAULT '0000-00-00 00:00:00',
                                        `updated_at` datetime(0) NOT NULL DEFAULT '0000-00-00 00:00:00',
                                        `remark_score` decimal(2, 1) NOT NULL DEFAULT 0,
                                        `disabled_flag` int(0) NOT NULL DEFAULT 0,
                                        PRIMARY KEY (`id`)
);


CREATE TABLE `dianpingdb`.`category`  (
                                          `id` int(0) NOT NULL AUTO_INCREMENT,
                                          `created_at` datetime(0) NOT NULL DEFAULT '0000-00-00 00:00:00',
                                          `updated_at` datetime(0) NOT NULL DEFAULT '0000-00-00 00:00:00',
                                          `name` varchar(20) NOT NULL DEFAULT '',
                                          `icon_url` varchar(200) NOT NULL DEFAULT '',
                                          `sort` int(0) NOT NULL DEFAULT 0,
                                          PRIMARY KEY (`id`),
                                          UNIQUE INDEX `name_unique_index`(`name`) USING BTREE
);


CREATE TABLE `dianpingdb`.`shop`  (
                                      `id` int(0) NOT NULL AUTO_INCREMENT,
                                      `created_at` datetime(0) NOT NULL DEFAULT '0000-00-00 00:00:00',
                                      `updated_at` datetime(0) NOT NULL DEFAULT '0000-00-00 00:00:00',
                                      `name` varchar(80) NOT NULL DEFAULT '',
                                      `remark_score` decimal(2, 1) NOT NULL DEFAULT 0,
                                      `price_per_man` int(0) NOT NULL DEFAULT 0,
                                      `latitude` decimal(10, 6) NOT NULL DEFAULT 0,
                                      `longitude` decimal(10, 6) NOT NULL DEFAULT 0,
                                      `category_id` int(0) NOT NULL DEFAULT 0,
                                      `tags` varchar(2000) NOT NULL DEFAULT '',
                                      `start_time` varchar(200) NOT NULL DEFAULT '',
                                      `end_time` varchar(200) NOT NULL DEFAULT '',
                                      `address` varchar(200) NOT NULL DEFAULT '',
                                      `seller_id` int(0) NOT NULL DEFAULT 0,
                                      `icon_url` varchar(100) NOT NULL DEFAULT '',
                                      PRIMARY KEY (`id`)
);