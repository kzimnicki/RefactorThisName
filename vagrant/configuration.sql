SET foreign_key_checks = 0;
DROP TABLE IF EXISTS `configuration`;
create table configuration (id bigint not null auto_increment, created datetime, updated datetime, isPhrasalVerbAdded bit not null, max integer not null, min integer not null, phrasalVerbTemplate varchar(255) not null, subtitleProcessor varchar(255), subtitleTemplate varchar(255) not null, textTemplate varchar(255) not null, primary key (id)) ENGINE=InnoDB;

LOCK TABLES `configuration` WRITE;
INSERT INTO `configuration`(isPhrasalVerbAdded,phrasalVerbTemplate, subtitleProcessor, id, created, updated,max,min ,subtitleTemplate, textTemplate) VALUES (0,'<font color=\"red\">@@TRANSLATED_TEXT@@</font>','IN_TEXT' ,1,NULL,NULL,100,5,'<font color=\"yellow\">(@@TRANSLATED_TEXT@@)</font>','(@@TRANSLATED_TEXT@@)');
INSERT INTO `configuration`(isPhrasalVerbAdded,phrasalVerbTemplate, subtitleProcessor, id, created, updated,max,min ,subtitleTemplate, textTemplate) VALUES (0,'<font color=\"red\">@@TRANSLATED_TEXT@@</font>','IN_TEXT' ,2,'2012-10-27 19:59:47','2012-10-27 19:59:47',90,5,'<font color=\'yellow\'>(@@TRANSLATED_TEXT@@)</font>','(@@TRANSLATED_TEXT@@)');
INSERT INTO `configuration`(isPhrasalVerbAdded,phrasalVerbTemplate, subtitleProcessor, id, created, updated,max,min ,subtitleTemplate, textTemplate) VALUES (0,'<font color=\"red\">@@TRANSLATED_TEXT@@</font>','IN_TEXT' ,3,'2012-10-27 20:08:53','2012-10-27 20:08:53',90,5,'<font color=\'yellow\'>(@@TRANSLATED_TEXT@@)</font>','(@@TRANSLATED_TEXT@@)');
INSERT INTO `configuration`(isPhrasalVerbAdded,phrasalVerbTemplate, subtitleProcessor, id, created, updated,max,min ,subtitleTemplate, textTemplate) VALUES (0,'<font color=\"red\">@@TRANSLATED_TEXT@@</font>','IN_TEXT' ,4,'2012-10-28 15:25:19','2012-10-28 15:25:19',89,5,'<font color=\"yellow\">(@@TRANSLATED_TEXT@@)</font>','(@@TRANSLATED_TEXT@@)');
INSERT INTO `configuration`(isPhrasalVerbAdded,phrasalVerbTemplate, subtitleProcessor, id, created, updated,max,min ,subtitleTemplate, textTemplate) VALUES (0,'<font color=\"red\">@@TRANSLATED_TEXT@@</font>','IN_TEXT' ,5,'2012-10-28 15:25:29','2012-10-28 15:25:29',89,5,'<font color=\"yellow\">(@@TRANSLATED_TEXT@@)</font>','(@@TRANSLATED_TEXT@@)');
INSERT INTO `configuration`(isPhrasalVerbAdded,phrasalVerbTemplate, subtitleProcessor, id, created, updated,max,min ,subtitleTemplate, textTemplate) VALUES (0,'<font color=\"red\">@@TRANSLATED_TEXT@@</font>','IN_TEXT' ,6,'2012-10-28 15:26:14','2012-10-28 15:26:14',89,5,'<font color=\"yellow\">(@@TRANSLATED_TEXT@@)</font>','(@@TRANSLATED_TEXT@@)');
INSERT INTO `configuration`(isPhrasalVerbAdded,phrasalVerbTemplate, subtitleProcessor, id, created, updated,max,min ,subtitleTemplate, textTemplate) VALUES (0,'<font color=\"red\">@@TRANSLATED_TEXT@@</font>','IN_TEXT' ,7,'2012-10-28 19:25:56','2012-10-28 19:25:56',89,5,'<font color=\"yellow\">(@@TRANSLATED_TEXT@@)</font>','(@@TRANSLATED_TEXT@@)');
INSERT INTO `configuration`(isPhrasalVerbAdded,phrasalVerbTemplate, subtitleProcessor, id, created, updated,max,min ,subtitleTemplate, textTemplate) VALUES (0,'<font color=\"red\">@@TRANSLATED_TEXT@@</font>','IN_TEXT' ,8,'2012-10-28 19:26:15','2012-10-28 19:26:15',89,5,'<font color=\"yellow\">(@@TRANSLATED_TEXT@@)</font>','(@@TRANSLATED_TEXT@@)');
INSERT INTO `configuration`(isPhrasalVerbAdded,phrasalVerbTemplate, subtitleProcessor, id, created, updated,max,min ,subtitleTemplate, textTemplate) VALUES (0,'<font color=\"red\">@@TRANSLATED_TEXT@@</font>','IN_TEXT' ,9,'2012-10-28 21:51:23','2012-10-28 21:51:23',89,5,'<font color=\"yellow\">(@@TRANSLATED_TEXT@@)</font>','(@@TRANSLATED_TEXT@@)');
INSERT INTO `configuration`(isPhrasalVerbAdded,phrasalVerbTemplate, subtitleProcessor, id, created, updated,max,min ,subtitleTemplate, textTemplate) VALUES (0,'<font color=\"red\">@@TRANSLATED_TEXT@@</font>','IN_TEXT' ,10,'2012-10-30 08:53:57','2012-10-30 08:53:57',89,5,'<font color=\"yellow\">(@@TRANSLATED_TEXT@@)</font>','(@@TRANSLATED_TEXT@@)');
INSERT INTO `configuration`(isPhrasalVerbAdded,phrasalVerbTemplate, subtitleProcessor, id, created, updated,max,min ,subtitleTemplate, textTemplate) VALUES (0,'<font color=\"red\">@@TRANSLATED_TEXT@@</font>','IN_TEXT' ,11,'2012-10-30 08:56:17','2012-10-30 08:56:17',89,5,'<font color=\"yellow\">(@@TRANSLATED_TEXT@@)</font>','(@@TRANSLATED_TEXT@@)');
INSERT INTO `configuration`(isPhrasalVerbAdded,phrasalVerbTemplate, subtitleProcessor, id, created, updated,max,min ,subtitleTemplate, textTemplate) VALUES (0,'<font color=\"red\">@@TRANSLATED_TEXT@@</font>','IN_TEXT' ,12,'2012-10-30 18:26:27','2012-10-30 18:26:27',89,5,'<font color=\"yellow\">(@@TRANSLATED_TEXT@@)</font>','(@@TRANSLATED_TEXT@@)');
INSERT INTO `configuration`(isPhrasalVerbAdded,phrasalVerbTemplate, subtitleProcessor, id, created, updated,max,min ,subtitleTemplate, textTemplate) VALUES (0,'<font color=\"red\">@@TRANSLATED_TEXT@@</font>','IN_TEXT' ,13,'2012-10-30 19:26:14','2012-10-30 19:26:14',75,5,'<font color=\"#E4E4E4\">(@@TRANSLATED_TEXT@@)</font>',' (@@TRANSLATED_TEXT@@)');
INSERT INTO `configuration`(isPhrasalVerbAdded,phrasalVerbTemplate, subtitleProcessor, id, created, updated,max,min ,subtitleTemplate, textTemplate) VALUES (0,'<font color=\"red\">@@TRANSLATED_TEXT@@</font>','IN_TEXT' ,14,'2012-10-30 20:03:17','2012-10-30 20:03:17',100,5,'<font color=\"yellow\">(@@TRANSLATED_TEXT@@)</font>','(@@TRANSLATED_TEXT@@)');
INSERT INTO `configuration`(isPhrasalVerbAdded,phrasalVerbTemplate, subtitleProcessor, id, created, updated,max,min ,subtitleTemplate, textTemplate) VALUES (0,'<font color=\"red\">@@TRANSLATED_TEXT@@</font>','IN_TEXT' ,15,'2012-10-30 20:04:30','2012-10-30 20:04:30',92,5,'<font color=\"yellow\">(@@TRANSLATED_TEXT@@)</font>','(@@TRANSLATED_TEXT@@)');
INSERT INTO `configuration`(isPhrasalVerbAdded,phrasalVerbTemplate, subtitleProcessor, id, created, updated,max,min ,subtitleTemplate, textTemplate) VALUES (0,'<font color=\"red\">@@TRANSLATED_TEXT@@</font>','IN_TEXT' ,16,'2012-10-31 13:16:06','2012-10-31 13:16:06',89,5,'<font color=\"yellow\">(@@TRANSLATED_TEXT@@)</font>','(@@TRANSLATED_TEXT@@)');
INSERT INTO `configuration`(isPhrasalVerbAdded,phrasalVerbTemplate, subtitleProcessor, id, created, updated,max,min ,subtitleTemplate, textTemplate) VALUES (0,'<font color=\"red\">@@TRANSLATED_TEXT@@</font>','IN_TEXT' ,17,'2012-10-31 13:17:21','2012-10-31 13:17:21',89,5,'<font color=\"yellow\">(@@TRANSLATED_TEXT@@)</font>','(@@TRANSLATED_TEXT@@)');
INSERT INTO `configuration`(isPhrasalVerbAdded,phrasalVerbTemplate, subtitleProcessor, id, created, updated,max,min ,subtitleTemplate, textTemplate) VALUES (0,'<font color=\"red\">@@TRANSLATED_TEXT@@</font>','IN_TEXT' ,18,'2012-10-31 13:19:18','2012-10-31 13:19:18',89,5,'<font color=\"yellow\">(@@TRANSLATED_TEXT@@)</font>','(@@TRANSLATED_TEXT@@)');
INSERT INTO `configuration`(isPhrasalVerbAdded,phrasalVerbTemplate, subtitleProcessor, id, created, updated,max,min ,subtitleTemplate, textTemplate) VALUES (0,'<font color=\"red\">@@TRANSLATED_TEXT@@</font>','IN_TEXT' ,19,'2012-10-31 13:19:19','2012-10-31 13:19:19',89,5,'<font color=\"yellow\">(@@TRANSLATED_TEXT@@)</font>','(@@TRANSLATED_TEXT@@)');
INSERT INTO `configuration`(isPhrasalVerbAdded,phrasalVerbTemplate, subtitleProcessor, id, created, updated,max,min ,subtitleTemplate, textTemplate) VALUES (0,'<font color=\"red\">@@TRANSLATED_TEXT@@</font>','IN_TEXT' ,20,'2012-10-31 13:19:33','2012-10-31 13:19:33',89,5,'<font color=\"yellow\">(@@TRANSLATED_TEXT@@)</font>','(@@TRANSLATED_TEXT@@)');
INSERT INTO `configuration`(isPhrasalVerbAdded,phrasalVerbTemplate, subtitleProcessor, id, created, updated,max,min ,subtitleTemplate, textTemplate) VALUES (0,'<font color=\"red\">@@TRANSLATED_TEXT@@</font>','IN_TEXT' ,21,'2012-11-02 13:07:57','2012-11-02 13:07:57',85,0,'<font color=\"yellow\">(@@TRANSLATED_TEXT@@)</font>','(@@TRANSLATED_TEXT@@)');
INSERT INTO `configuration`(isPhrasalVerbAdded,phrasalVerbTemplate, subtitleProcessor, id, created, updated,max,min ,subtitleTemplate, textTemplate) VALUES (0,'<font color=\"red\">@@TRANSLATED_TEXT@@</font>','IN_TEXT' ,22,'2012-11-02 14:04:58','2012-11-02 14:04:58',100,0,'(@@TRANSLATED_TEXT@@)','(@@TRANSLATED_TEXT@@)');
INSERT INTO `configuration`(isPhrasalVerbAdded,phrasalVerbTemplate, subtitleProcessor, id, created, updated,max,min ,subtitleTemplate, textTemplate) VALUES (0,'<font color=\"red\">@@TRANSLATED_TEXT@@</font>','IN_TEXT' ,23,'2012-11-02 20:36:02','2012-11-02 20:36:02',89,5,'<font color=\"yellow\">(@@TRANSLATED_TEXT@@)</font>','(@@TRANSLATED_TEXT@@)');
INSERT INTO `configuration`(isPhrasalVerbAdded,phrasalVerbTemplate, subtitleProcessor, id, created, updated,max,min ,subtitleTemplate, textTemplate) VALUES (0,'<font color=\"red\">@@TRANSLATED_TEXT@@</font>','IN_TEXT' ,24,'2012-11-11 10:36:45','2012-11-11 10:36:45',89,5,'<font color=\"yellow\">(@@TRANSLATED_TEXT@@)</font>','(@@TRANSLATED_TEXT@@)');
INSERT INTO `configuration`(isPhrasalVerbAdded,phrasalVerbTemplate, subtitleProcessor, id, created, updated,max,min ,subtitleTemplate, textTemplate) VALUES (0,'<font color=\"red\">@@TRANSLATED_TEXT@@</font>','IN_TEXT' ,25,'2012-11-11 10:36:57','2012-11-11 10:36:57',100,5,'(@@TRANSLATED_TEXT@@)','(@@TRANSLATED_TEXT@@)');
INSERT INTO `configuration`(isPhrasalVerbAdded,phrasalVerbTemplate, subtitleProcessor, id, created, updated,max,min ,subtitleTemplate, textTemplate) VALUES (0,'<font color=\"red\">@@TRANSLATED_TEXT@@</font>','IN_TEXT' ,26,'2012-11-13 15:01:54','2012-11-13 15:01:54',80,0,'<font color=\"yellow\">(@@TRANSLATED_TEXT@@)</font>','(@@TRANSLATED_TEXT@@)');
INSERT INTO `configuration`(isPhrasalVerbAdded,phrasalVerbTemplate, subtitleProcessor, id, created, updated,max,min ,subtitleTemplate, textTemplate) VALUES (0,'<font color=\"red\">@@TRANSLATED_TEXT@@</font>','IN_TEXT' ,27,'2012-11-14 01:09:42','2012-11-14 01:09:42',89,5,'<font color=\"yellow\">(@@TRANSLATED_TEXT@@)</font>','(@@TRANSLATED_TEXT@@)');
INSERT INTO `configuration`(isPhrasalVerbAdded,phrasalVerbTemplate, subtitleProcessor, id, created, updated,max,min ,subtitleTemplate, textTemplate) VALUES (0,'<font color=\"red\">@@TRANSLATED_TEXT@@</font>','IN_TEXT' ,28,'2012-11-14 20:01:46','2012-11-14 20:01:46',89,5,'<font color=\"yellow\">(@@TRANSLATED_TEXT@@)</font>','(@@TRANSLATED_TEXT@@)');
INSERT INTO `configuration`(isPhrasalVerbAdded,phrasalVerbTemplate, subtitleProcessor, id, created, updated,max,min ,subtitleTemplate, textTemplate) VALUES (0,'<font color=\"red\">@@TRANSLATED_TEXT@@</font>','IN_TEXT' ,29,'2012-11-15 22:58:54','2012-11-15 22:58:54',91,10,'<font color=\"yellow\">(@@TRANSLATED_TEXT@@)</font>','(@@TRANSLATED_TEXT@@)');
INSERT INTO `configuration`(isPhrasalVerbAdded,phrasalVerbTemplate, subtitleProcessor, id, created, updated,max,min ,subtitleTemplate, textTemplate) VALUES (0,'<font color=\"red\">@@TRANSLATED_TEXT@@</font>','IN_TEXT' ,30,'2012-11-17 20:57:05','2012-11-17 20:57:05',100,0,'<font color=\"yellow\">(@@TRANSLATED_TEXT@@)</font>','(@@TRANSLATED_TEXT@@)');
INSERT INTO `configuration`(isPhrasalVerbAdded,phrasalVerbTemplate, subtitleProcessor, id, created, updated,max,min ,subtitleTemplate, textTemplate) VALUES (0,'<font color=\"red\">@@TRANSLATED_TEXT@@</font>','IN_TEXT' ,31,'2012-11-26 17:35:06','2012-11-26 17:35:06',89,5,'<<(@@TRANSLATED_TEXT@@)>>','(@@TRANSLATED_TEXT@@)');
INSERT INTO `configuration`(isPhrasalVerbAdded,phrasalVerbTemplate, subtitleProcessor, id, created, updated,max,min ,subtitleTemplate, textTemplate) VALUES (0,'<font color=\"red\">@@TRANSLATED_TEXT@@</font>','IN_TEXT' ,32,'2012-11-26 18:53:45','2012-11-26 18:53:45',89,5,'<font color=\"yellow\">(@@TRANSLATED_TEXT@@)</font>','(@@TRANSLATED_TEXT@@)');
INSERT INTO `configuration`(isPhrasalVerbAdded,phrasalVerbTemplate, subtitleProcessor, id, created, updated,max,min ,subtitleTemplate, textTemplate) VALUES (0,'<font color=\"red\">@@TRANSLATED_TEXT@@</font>','IN_TEXT' ,33,'2012-11-26 19:08:58','2012-11-26 19:08:58',89,1,'<font color=\"yellow\">(@@TRANSLATED_TEXT@@)</font>','(@@TRANSLATED_TEXT@@)');
INSERT INTO `configuration`(isPhrasalVerbAdded,phrasalVerbTemplate, subtitleProcessor, id, created, updated,max,min ,subtitleTemplate, textTemplate) VALUES (0,'<font color=\"red\">@@TRANSLATED_TEXT@@</font>','IN_TEXT' ,34,'2012-11-27 16:45:03','2012-11-27 16:45:03',100,0,'<font color=\"yellow\">(@@TRANSLATED_TEXT@@)</font>','(@@TRANSLATED_TEXT@@)');
INSERT INTO `configuration`(isPhrasalVerbAdded,phrasalVerbTemplate, subtitleProcessor, id, created, updated,max,min ,subtitleTemplate, textTemplate) VALUES (0,'<font color=\"red\">@@TRANSLATED_TEXT@@</font>','IN_TEXT' ,35,'2012-12-02 22:58:53','2012-12-02 22:58:53',89,5,'<font color=\"yellow\">(@@TRANSLATED_TEXT@@)</font>','(@@TRANSLATED_TEXT@@)');
INSERT INTO `configuration`(isPhrasalVerbAdded,phrasalVerbTemplate, subtitleProcessor, id, created, updated,max,min ,subtitleTemplate, textTemplate) VALUES (0,'<font color=\"red\">@@TRANSLATED_TEXT@@</font>','IN_TEXT' ,36,'2012-12-18 20:55:46','2012-12-18 20:55:46',89,5,'<font color=\"yellow\">(@@TRANSLATED_TEXT@@)</font>','(@@TRANSLATED_TEXT@@)');
INSERT INTO `configuration`(isPhrasalVerbAdded,phrasalVerbTemplate, subtitleProcessor, id, created, updated,max,min ,subtitleTemplate, textTemplate) VALUES (0,'<font color=\"red\">@@TRANSLATED_TEXT@@</font>','IN_TEXT' ,37,'2012-12-20 08:03:32','2012-12-20 08:03:32',89,5,'<font color=\"yellow\">(@@TRANSLATED_TEXT@@)</font>','(@@TRANSLATED_TEXT@@)');
INSERT INTO `configuration`(isPhrasalVerbAdded,phrasalVerbTemplate, subtitleProcessor, id, created, updated,max,min ,subtitleTemplate, textTemplate) VALUES (0,'<font color=\"red\">@@TRANSLATED_TEXT@@</font>','IN_TEXT' ,38,'2012-12-20 12:28:10','2012-12-20 12:28:10',100,5,'<font color=\"yellow\">(@@TRANSLATED_TEXT@@)</font>','(@@TRANSLATED_TEXT@@)');
INSERT INTO `configuration`(isPhrasalVerbAdded,phrasalVerbTemplate, subtitleProcessor, id, created, updated,max,min ,subtitleTemplate, textTemplate) VALUES (0,'<font color=\"red\">@@TRANSLATED_TEXT@@</font>','IN_TEXT' ,39,'2012-12-26 10:31:30','2012-12-26 10:31:30',85,0,'<font color=\"yellow\">(@@TRANSLATED_TEXT@@)</font>','(@@TRANSLATED_TEXT@@)');
INSERT INTO `configuration`(isPhrasalVerbAdded,phrasalVerbTemplate, subtitleProcessor, id, created, updated,max,min ,subtitleTemplate, textTemplate) VALUES (0,'<font color=\"red\">@@TRANSLATED_TEXT@@</font>','IN_TEXT' ,40,'2012-12-26 10:31:36','2012-12-26 10:31:36',89,5,'<font color=\"yellow\">(@@TRANSLATED_TEXT@@)</font>','(@@TRANSLATED_TEXT@@)');
INSERT INTO `configuration`(isPhrasalVerbAdded,phrasalVerbTemplate, subtitleProcessor, id, created, updated,max,min ,subtitleTemplate, textTemplate) VALUES (0,'<font color=\"red\">@@TRANSLATED_TEXT@@</font>','IN_TEXT' ,41,'2012-12-26 10:32:06','2012-12-26 10:32:06',89,5,'<font color=\"yellow\">(@@TRANSLATED_TEXT@@)</font>','(@@TRANSLATED_TEXT@@)');
INSERT INTO `configuration`(isPhrasalVerbAdded,phrasalVerbTemplate, subtitleProcessor, id, created, updated,max,min ,subtitleTemplate, textTemplate) VALUES (0,'<font color=\"red\">@@TRANSLATED_TEXT@@</font>','IN_TEXT' ,42,'2012-12-26 10:38:41','2012-12-26 10:38:41',89,5,'<font color=\"yellow\">(@@TRANSLATED_TEXT@@)</font>','(@@TRANSLATED_TEXT@@)');
INSERT INTO `configuration`(isPhrasalVerbAdded,phrasalVerbTemplate, subtitleProcessor, id, created, updated,max,min ,subtitleTemplate, textTemplate) VALUES (0,'<font color=\"red\">@@TRANSLATED_TEXT@@</font>','IN_TEXT' ,43,'2012-12-26 10:38:51','2012-12-26 10:38:51',89,5,'<font color=\"yellow\">(@@TRANSLATED_TEXT@@)</font>','(@@TRANSLATED_TEXT@@)');
INSERT INTO `configuration`(isPhrasalVerbAdded,phrasalVerbTemplate, subtitleProcessor, id, created, updated,max,min ,subtitleTemplate, textTemplate) VALUES (0,'<font color=\"red\">@@TRANSLATED_TEXT@@</font>','IN_TEXT' ,44,'2012-12-26 11:32:05','2012-12-26 11:32:05',89,5,'<font color=\"yellow\">(@@TRANSLATED_TEXT@@)</font>','(@@TRANSLATED_TEXT@@)');
INSERT INTO `configuration`(isPhrasalVerbAdded,phrasalVerbTemplate, subtitleProcessor, id, created, updated,max,min ,subtitleTemplate, textTemplate) VALUES (0,'<font color=\"red\">@@TRANSLATED_TEXT@@</font>','IN_TEXT' ,45,'2012-12-27 21:44:48','2012-12-27 21:44:48',91,5,'<font color=\"yellow\">(@@TRANSLATED_TEXT@@)</font>','(@@TRANSLATED_TEXT@@)');
INSERT INTO `configuration`(isPhrasalVerbAdded,phrasalVerbTemplate, subtitleProcessor, id, created, updated,max,min ,subtitleTemplate, textTemplate) VALUES (0,'<font color=\"red\">@@TRANSLATED_TEXT@@</font>','IN_TEXT' ,46,'2012-12-28 12:04:38','2012-12-28 12:04:38',89,5,'<font color=\"yellow\">(@@TRANSLATED_TEXT@@)</font>','(@@TRANSLATED_TEXT@@)');
INSERT INTO `configuration`(isPhrasalVerbAdded,phrasalVerbTemplate, subtitleProcessor, id, created, updated,max,min ,subtitleTemplate, textTemplate) VALUES (0,'<font color=\"red\">@@TRANSLATED_TEXT@@</font>','IN_TEXT' ,47,'2012-12-29 21:30:11','2012-12-29 21:30:11',89,5,'<font color=\"yellow\">(@@TRANSLATED_TEXT@@)</font>','(@@TRANSLATED_TEXT@@)');
INSERT INTO `configuration`(isPhrasalVerbAdded,phrasalVerbTemplate, subtitleProcessor, id, created, updated,max,min ,subtitleTemplate, textTemplate) VALUES (0,'<font color=\"red\">@@TRANSLATED_TEXT@@</font>','IN_TEXT' ,48,'2013-01-02 19:10:22','2013-01-02 19:10:22',100,1,'<font color=\"yellow\">(@@TRANSLATED_TEXT@@)</font>','(@@TRANSLATED_TEXT@@)');
INSERT INTO `configuration`(isPhrasalVerbAdded,phrasalVerbTemplate, subtitleProcessor, id, created, updated,max,min ,subtitleTemplate, textTemplate) VALUES (0,'<font color=\"red\">@@TRANSLATED_TEXT@@</font>','IN_TEXT' ,49,'2013-01-11 17:00:19','2013-01-11 17:00:19',89,5,'*(@@TRANSLATED_TEXT@@)*','**(@@TRANSLATED_TEXT@@)**');
INSERT INTO `configuration`(isPhrasalVerbAdded,phrasalVerbTemplate, subtitleProcessor, id, created, updated,max,min ,subtitleTemplate, textTemplate) VALUES (0,'<font color=\"red\">@@TRANSLATED_TEXT@@</font>','IN_TEXT' ,50,'2013-01-11 17:52:00','2013-01-11 17:52:00',89,5,'<font color=\"yellow\">(@@TRANSLATED_TEXT@@)</font>','(@@TRANSLATED_TEXT@@)');
INSERT INTO `configuration`(isPhrasalVerbAdded,phrasalVerbTemplate, subtitleProcessor, id, created, updated,max,min ,subtitleTemplate, textTemplate) VALUES (0,'<font color=\"red\">@@TRANSLATED_TEXT@@</font>','IN_TEXT' ,51,'2013-01-26 21:13:10','2013-01-26 21:13:10',89,5,'<font color=\"yellow\">(@@TRANSLATED_TEXT@@)</font>','(@@TRANSLATED_TEXT@@)');
INSERT INTO `configuration`(isPhrasalVerbAdded,phrasalVerbTemplate, subtitleProcessor, id, created, updated,max,min ,subtitleTemplate, textTemplate) VALUES (0,'<font color=\"red\">@@TRANSLATED_TEXT@@</font>','IN_TEXT' ,52,'2013-02-08 23:00:32','2013-02-08 23:00:32',70,5,'<font color=\"yellow\">(@@TRANSLATED_TEXT@@)</font>','(@@TRANSLATED_TEXT@@)');
INSERT INTO `configuration`(isPhrasalVerbAdded,phrasalVerbTemplate, subtitleProcessor, id, created, updated,max,min ,subtitleTemplate, textTemplate) VALUES (0,'<font color=\"red\">@@TRANSLATED_TEXT@@</font>','IN_TEXT' ,53,'2013-02-14 08:49:30','2013-02-14 08:49:30',89,5,'<font color=\"yellow\">(@@TRANSLATED_TEXT@@)</font>','(@@TRANSLATED_TEXT@@)');
INSERT INTO `configuration`(isPhrasalVerbAdded,phrasalVerbTemplate, subtitleProcessor, id, created, updated,max,min ,subtitleTemplate, textTemplate) VALUES (0,'<font color=\"red\">@@TRANSLATED_TEXT@@</font>','IN_TEXT' ,54,'2013-02-15 17:49:19','2013-02-15 17:49:19',89,5,'<font color=\"yellow\">(@@TRANSLATED_TEXT@@)</font>','(@@TRANSLATED_TEXT@@)');
INSERT INTO `configuration`(isPhrasalVerbAdded,phrasalVerbTemplate, subtitleProcessor, id, created, updated,max,min ,subtitleTemplate, textTemplate) VALUES (0,'<font color=\"red\">@@TRANSLATED_TEXT@@</font>','IN_TEXT' ,55,'2013-02-15 17:49:25','2013-02-15 17:49:25',89,5,'<font color=\"yellow\">(@@TRANSLATED_TEXT@@)</font>','(@@TRANSLATED_TEXT@@)');
INSERT INTO `configuration`(isPhrasalVerbAdded,phrasalVerbTemplate, subtitleProcessor, id, created, updated,max,min ,subtitleTemplate, textTemplate) VALUES (0,'<font color=\"red\">@@TRANSLATED_TEXT@@</font>','IN_TEXT' ,56,'2013-02-26 13:35:41','2013-02-26 13:35:41',93,0,'<font color=\"yellow\">(@@TRANSLATED_TEXT@@)</font>','(@@TRANSLATED_TEXT@@)');
INSERT INTO `configuration`(isPhrasalVerbAdded,phrasalVerbTemplate, subtitleProcessor, id, created, updated,max,min ,subtitleTemplate, textTemplate) VALUES (0,'<font color=\"red\">@@TRANSLATED_TEXT@@</font>','IN_TEXT' ,57,'2013-02-26 13:37:44','2013-02-26 13:37:44',89,5,'<font color=\"yellow\">(@@TRANSLATED_TEXT@@)</font>','(@@TRANSLATED_TEXT@@)');
INSERT INTO `configuration`(isPhrasalVerbAdded,phrasalVerbTemplate, subtitleProcessor, id, created, updated,max,min ,subtitleTemplate, textTemplate) VALUES (0,'<font color=\"red\">@@TRANSLATED_TEXT@@</font>','IN_TEXT' ,58,'2013-03-25 10:34:58','2013-03-25 10:34:58',89,5,'<font color=\"yellow\">(@@TRANSLATED_TEXT@@)</font>','(@@TRANSLATED_TEXT@@)');
INSERT INTO `configuration`(isPhrasalVerbAdded,phrasalVerbTemplate, subtitleProcessor, id, created, updated,max,min ,subtitleTemplate, textTemplate) VALUES (0,'<font color=\"red\">@@TRANSLATED_TEXT@@</font>','IN_TEXT' ,59,'2013-03-26 23:16:01','2013-03-26 23:16:01',89,5,'<font color=\"yellow\">(@@TRANSLATED_TEXT@@)</font>','(@@TRANSLATED_TEXT@@)');
INSERT INTO `configuration`(isPhrasalVerbAdded,phrasalVerbTemplate, subtitleProcessor, id, created, updated,max,min ,subtitleTemplate, textTemplate) VALUES (0,'<font color=\"red\">@@TRANSLATED_TEXT@@</font>','IN_TEXT' ,60,'2013-03-28 00:09:41','2013-03-28 00:09:41',89,5,'<font color=\"yellow\">(@@TRANSLATED_TEXT@@)</font>','(@@TRANSLATED_TEXT@@)');
INSERT INTO `configuration`(isPhrasalVerbAdded,phrasalVerbTemplate, subtitleProcessor, id, created, updated,max,min ,subtitleTemplate, textTemplate) VALUES (0,'<font color=\"red\">@@TRANSLATED_TEXT@@</font>','IN_TEXT' ,61,'2013-03-28 17:14:02','2013-03-28 17:14:02',89,5,'<font color=\"yellow\">(@@TRANSLATED_TEXT@@)</font>','(@@TRANSLATED_TEXT@@)');
INSERT INTO `configuration`(isPhrasalVerbAdded,phrasalVerbTemplate, subtitleProcessor, id, created, updated,max,min ,subtitleTemplate, textTemplate) VALUES (0,'<font color=\"red\">@@TRANSLATED_TEXT@@</font>','IN_TEXT' ,62,'2013-04-01 01:32:10','2013-04-01 01:32:10',89,5,'<font color=\"yellow\">(@@TRANSLATED_TEXT@@)</font>','(@@TRANSLATED_TEXT@@)');
INSERT INTO `configuration`(isPhrasalVerbAdded,phrasalVerbTemplate, subtitleProcessor, id, created, updated,max,min ,subtitleTemplate, textTemplate) VALUES (0,'<font color=\"red\">@@TRANSLATED_TEXT@@</font>','IN_TEXT' ,63,'2013-05-06 07:02:43','2013-05-06 07:02:43',89,5,'<font color=\"yellow\">(@@TRANSLATED_TEXT@@)</font>','(@@TRANSLATED_TEXT@@)');
INSERT INTO `configuration`(isPhrasalVerbAdded,phrasalVerbTemplate, subtitleProcessor, id, created, updated,max,min ,subtitleTemplate, textTemplate) VALUES (0,'<font color=\"red\">@@TRANSLATED_TEXT@@</font>','IN_TEXT' ,64,'2013-05-08 15:51:29','2013-05-08 15:51:29',89,5,'<font color=\"yellow\">(@@TRANSLATED_TEXT@@)</font>','(@@TRANSLATED_TEXT@@)');
INSERT INTO `configuration`(isPhrasalVerbAdded,phrasalVerbTemplate, subtitleProcessor, id, created, updated,max,min ,subtitleTemplate, textTemplate) VALUES (0,'<font color=\"red\">@@TRANSLATED_TEXT@@</font>','IN_TEXT' ,65,'2013-05-12 13:06:52','2013-05-12 13:06:52',89,5,'<font color=\"yellow\">(@@TRANSLATED_TEXT@@)</font>','(@@TRANSLATED_TEXT@@)');
INSERT INTO `configuration`(isPhrasalVerbAdded,phrasalVerbTemplate, subtitleProcessor, id, created, updated,max,min ,subtitleTemplate, textTemplate) VALUES (0,'<font color=\"red\">@@TRANSLATED_TEXT@@</font>','IN_TEXT' ,66,'2013-05-12 13:06:53','2013-05-12 13:06:53',89,5,'<font color=\"yellow\">(@@TRANSLATED_TEXT@@)</font>','(@@TRANSLATED_TEXT@@)');
INSERT INTO `configuration`(isPhrasalVerbAdded,phrasalVerbTemplate, subtitleProcessor, id, created, updated,max,min ,subtitleTemplate, textTemplate) VALUES (0,'<font color=\"red\">@@TRANSLATED_TEXT@@</font>','IN_TEXT' ,67,'2013-05-12 13:07:13','2013-05-12 13:07:13',89,5,'<font color=\"yellow\">(@@TRANSLATED_TEXT@@)</font>','(@@TRANSLATED_TEXT@@)');
INSERT INTO `configuration`(isPhrasalVerbAdded,phrasalVerbTemplate, subtitleProcessor, id, created, updated,max,min ,subtitleTemplate, textTemplate) VALUES (0,'<font color=\"red\">@@TRANSLATED_TEXT@@</font>','IN_TEXT' ,68,'2013-05-12 13:07:33','2013-05-12 13:07:33',89,5,'<font color=\"yellow\">(@@TRANSLATED_TEXT@@)</font>','(@@TRANSLATED_TEXT@@)');
INSERT INTO `configuration`(isPhrasalVerbAdded,phrasalVerbTemplate, subtitleProcessor, id, created, updated,max,min ,subtitleTemplate, textTemplate) VALUES (0,'<font color=\"red\">@@TRANSLATED_TEXT@@</font>','IN_TEXT' ,69,'2013-05-12 21:06:57','2013-05-12 21:06:57',80,5,'<font color=\"yellow\">(@@TRANSLATED_TEXT@@)</font>','(@@TRANSLATED_TEXT@@)');
INSERT INTO `configuration`(isPhrasalVerbAdded,phrasalVerbTemplate, subtitleProcessor, id, created, updated,max,min ,subtitleTemplate, textTemplate) VALUES (0,'<font color=\"red\">@@TRANSLATED_TEXT@@</font>','IN_TEXT' ,70,'2013-05-12 21:07:47','2013-05-12 21:07:47',89,5,'<font color=\"yellow\">(@@TRANSLATED_TEXT@@)</font>','(@@TRANSLATED_TEXT@@)');
INSERT INTO `configuration`(isPhrasalVerbAdded,phrasalVerbTemplate, subtitleProcessor, id, created, updated,max,min ,subtitleTemplate, textTemplate) VALUES (0,'<font color=\"red\">@@TRANSLATED_TEXT@@</font>','IN_TEXT' ,71,'2013-05-12 21:07:49','2013-05-12 21:07:49',89,5,'<font color=\"yellow\">(@@TRANSLATED_TEXT@@)</font>','(@@TRANSLATED_TEXT@@)');
INSERT INTO `configuration`(isPhrasalVerbAdded,phrasalVerbTemplate, subtitleProcessor, id, created, updated,max,min ,subtitleTemplate, textTemplate) VALUES (0,'<font color=\"red\">@@TRANSLATED_TEXT@@</font>','IN_TEXT' ,72,'2013-05-12 22:38:30','2013-05-12 22:38:30',89,5,'<font color=\"yellow\">(@@TRANSLATED_TEXT@@)</font>','(@@TRANSLATED_TEXT@@)');
INSERT INTO `configuration`(isPhrasalVerbAdded,phrasalVerbTemplate, subtitleProcessor, id, created, updated,max,min ,subtitleTemplate, textTemplate) VALUES (0,'<font color=\"red\">@@TRANSLATED_TEXT@@</font>','IN_TEXT' ,73,'2013-05-15 12:43:03','2013-05-15 12:43:03',89,5,'<font color=\"#75b9f0\"> (@@TRANSLATED_TEXT@@)</font>','(@@TRANSLATED_TEXT@@)');
UNLOCK TABLES;
SET foreign_key_checks = 1;