CREATE TABLE IF NOT EXISTS `tunnel_network` (
  `id` int NOT NULL UNIQUE AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY ( `id`)
);

CREATE TABLE IF NOT EXISTS `line` (
  `id` int NOT NULL UNIQUE AUTO_INCREMENT,
  `name` varchar(16) NOT NULL,
  `type` enum('STANDARD','SEMI_LOOP','LOOP') NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `station` (
  `id` int NOT NULL UNIQUE AUTO_INCREMENT,
  `name` varchar(128) NOT NULL,
  `tunnel_id` int DEFAULT NULL,
  --PRIMARY KEY (`id`),
  --KEY `station_tunnel_network_FK` (`tunnel_id`),
  CONSTRAINT `station_tunnel_network_FK` FOREIGN KEY (`tunnel_id`) REFERENCES `tunnel_network` (`id`)
);

CREATE TABLE IF NOT EXISTS `station_line` (
  `id` int NOT NULL UNIQUE AUTO_INCREMENT,
  `station_id` int NOT NULL,
  `line_id` int NOT NULL,
  `position` int NOT NULL,
  `branch_id` int DEFAULT NULL,
  `direction` enum('BIDIRECTIONAL','INCREASING','DECREASING') NOT NULL,
  PRIMARY KEY (`id`),
  --KEY station_line_station_FK (station_id),
  --KEY station_line_line_FK (line_id),
  CONSTRAINT `station_line_line_FK` FOREIGN KEY (`line_id`) REFERENCES line (`id`),
  CONSTRAINT `station_line_station_FK` FOREIGN KEY (`station_id`) REFERENCES `station` (`id`)
);

CREATE TABLE IF NOT EXISTS `terminus` (
  `id` int NOT NULL UNIQUE AUTO_INCREMENT,
  `line_id` int NOT NULL,
  `station_id` int NOT NULL,
  PRIMARY KEY (`id`),
  --KEY terminus_line_FK (line_id),
  --KEY terminus_station_FK (station_id),
  CONSTRAINT `terminus_line_FK` FOREIGN KEY (`line_id`) REFERENCES line (`id`),
  CONSTRAINT `terminus_station_FK` FOREIGN KEY (`station_id`) REFERENCES `station` (`id`)
);