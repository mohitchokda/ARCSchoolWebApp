insert into `holidays` (`day`,`reason`,`type`,`created_at`,`created_by`)
				values ('26-01-2023','Republic Day','Govt',CURDATE(),'DBA'),
					('14-01-2023','Makar Sankranti','Festival',CURDATE(),'DBA'),
					('25-03-2023','Holi','Festival',CURDATE(),'DBA'),
					('02-10-2023','Gandhi Jayanti','Govt',CURDATE(),'DBA'),
					('15-07-2023','Independence Day','Govt',CURDATE(),'DBA');

INSERT INTO `roles` (`role_name`,`created_at`, `created_by`)
  VALUES ('ADMIN',CURDATE(),'DBA');

INSERT INTO `roles` (`role_name`,`created_at`, `created_by`)
  VALUES ('STUDENT',CURDATE(),'DBA');

INSERT INTO `person` (`name`,`email`,`mobile_number`,`pwd`,`role_id`,`created_at`, `created_by`)
  VALUES ('Admin','admin@eazyschool.com','3443434343','admin', 1 ,CURDATE(),'DBA');