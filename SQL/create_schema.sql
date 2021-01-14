create schema `project_2`;

use `project_2`;

CREATE TABLE `categories` 
(
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `left` int NOT NULL,
  `right` int NOT NULL,
  `level` tinyint unsigned NOT NULL,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `options` 
(
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `category_id` bigint unsigned NOT NULL,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),  
  CONSTRAINT `options_category_id_foreign` FOREIGN KEY (`category_id`)
  REFERENCES `categories` (`id`) 
  ON DELETE RESTRICT 
  ON UPDATE RESTRICT
);

CREATE TABLE `products` 
(
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `category_id` bigint unsigned NOT NULL,
  `name` varchar(50) NOT NULL,
  `price` decimal(15,0) NOT NULL,
  PRIMARY KEY (`id`),  
  CONSTRAINT `products_category_id_foreign` FOREIGN KEY (`category_id`)
  REFERENCES `categories` (`id`) 
  ON DELETE RESTRICT
  ON UPDATE RESTRICT
);

CREATE TABLE `roles` 
(
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `role` varchar(15) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `users` 
(
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `login` varchar(25) NOT NULL,
  `password` varchar(200) NOT NULL,
  `name` varchar(25) NOT NULL,
  `surname` varchar(25) NOT NULL,
  `role_id` bigint unsigned NOT NULL,
  PRIMARY KEY (`id`),  
  CONSTRAINT `users_role_id_foreign` FOREIGN KEY (`role_id`) 
  REFERENCES `roles` (`id`) 
  ON DELETE RESTRICT 
  ON UPDATE RESTRICT
);

CREATE TABLE `values` 
(
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `product_id` bigint unsigned NOT NULL,
  `option_id` bigint unsigned NOT NULL,
  `value` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id`),  
  CONSTRAINT `values_option_id_foreign` FOREIGN KEY (`option_id`) 
  REFERENCES `options` (`id`) 
  ON DELETE RESTRICT 
  ON UPDATE RESTRICT,
  CONSTRAINT `values_product_id_foreign` FOREIGN KEY (`product_id`) 
  REFERENCES `products` (`id`) 
  ON DELETE RESTRICT 
  ON UPDATE RESTRICT
);

create table `orders`
(
`id` bigint unsigned not null auto_increment,
`user_id` bigint unsigned not null,
`order_date` datetime default now(),
`active` boolean not null default true,
`date_complit` datetime,
`complited` boolean not null default false,
`comment` text,
primary key (`id`),
constraint `orders_user_id_foreign` foreign key (`user_id`) 
references `users` (`id`) 
ON DELETE RESTRICT 
ON UPDATE RESTRICT
);

create table `order_products`
(
`id` bigint unsigned not null auto_increment,
`order_id` bigint unsigned not null,
`product_id` bigint unsigned not null,
`quantity` smallint not null,
primary key (`id`),
constraint `order_products_order_id_foreign` foreign key (`order_id`) 
references `orders` (`id`) 
ON DELETE RESTRICT 
ON UPDATE RESTRICT,
constraint `order_product_product_id_foreign` foreign key (`product_id`) 
references `products` (`id`) 
ON DELETE RESTRICT 
ON UPDATE RESTRICT
);