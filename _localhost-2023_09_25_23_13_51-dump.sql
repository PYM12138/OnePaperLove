-- MySQL dump 10.13  Distrib 5.7.41, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: mingblog
-- ------------------------------------------------------
-- Server version	5.7.41-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `article_tags`
--

DROP TABLE IF EXISTS `article_tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `article_tags` (
  `article_id` int(11) NOT NULL,
  `articleId` bigint(20) NOT NULL,
  `tag_id` int(11) NOT NULL,
  PRIMARY KEY (`article_id`,`tag_id`),
  KEY `tag_id` (`tag_id`),
  CONSTRAINT `article_tags_ibfk_1` FOREIGN KEY (`article_id`) REFERENCES `articles` (`id`),
  CONSTRAINT `article_tags_ibfk_2` FOREIGN KEY (`tag_id`) REFERENCES `tags` (`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article_tags`
--

LOCK TABLES `article_tags` WRITE;
/*!40000 ALTER TABLE `article_tags` DISABLE KEYS */;
INSERT INTO `article_tags` VALUES (1,2023072601,1),(1,2023072601,2),(2,2023040503,1),(2,2023040503,2),(3,2023040504,1),(3,2023040504,2),(4,202309171,2),(4,202309171,3),(5,202309172,1),(5,202309172,3),(6,202309173,2),(6,202309173,3);
/*!40000 ALTER TABLE `article_tags` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `articles`
--

DROP TABLE IF EXISTS `articles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `articles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `articleId` bigint(20) NOT NULL,
  `author` varchar(255) NOT NULL,
  `originalAuthor` varchar(255) NOT NULL,
  `title` varchar(255) NOT NULL,
  `content` longtext NOT NULL,
  `type` varchar(255) NOT NULL,
  `publishDate` varchar(255) NOT NULL,
  `lastUpdateDate` varchar(255) NOT NULL,
  `url` varchar(255) NOT NULL,
  `summary` text NOT NULL,
  `likes` int(11) NOT NULL,
  `lastArticleId` bigint(20) DEFAULT NULL,
  `nextArticleId` bigint(20) DEFAULT NULL,
  `category_id` int(11) DEFAULT NULL,
  `isDelete` tinyint(4) DEFAULT '0' COMMENT '0是显示，1是删除',
  PRIMARY KEY (`id`),
  KEY `article_categories_id_fk` (`category_id`),
  CONSTRAINT `article_categories_id_fk` FOREIGN KEY (`category_id`) REFERENCES `categories` (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `articles`
--

LOCK TABLES `articles` WRITE;
/*!40000 ALTER TABLE `articles` DISABLE KEYS */;
INSERT INTO `articles` VALUES (1,2023072601,'yiming','yiming','test','##自我介绍\n我叫张海洋，紧张的张，目前是一名在校大学生，该博客的维护人，技术方向是Web后端开发，由于我大学专业是物联网工程专业，偏软硬件结合，但是众所周知，大学所培养的人才专业学习一般都得等到大二下学期乃至大三，这对于许多人来说也是个魔咒吧，好不容易经历了xx年义务教育，又历经了高考的洗礼，到了大学发现生活是如此的悠哉，没有专业课的紧张学习，难免都会如温水煮青蛙一般，陷入“舒适”的生活无法自拔。\n\n我很幸运，在大学刚开始加入了计科院翼灵物联工作室，也很荣幸成为这个大家庭中的一员。大一下学期结束的那个暑假，自己在家花了20天的时间自学了Java，从此以后，陷入后端无法自拔。\n\n##关于这个博客网站吖\n在高中三年里，我养成了每天写日记的习惯，现在我的家里还有一本记载着我高中记忆的本本，这个习惯陪伴了我高中，然后毕业之后也“成功”的戒掉了这个习惯。若非入了程序猿这行，我想恐怕这辈子也没机会拾起自己的破烂笔杆子吧...\n\n我也没想到自己会花费两个月时间去建一个自己的博客，毕竟对于自己来说，两个月前的我也还算的上是一名前端小白吧，但随着自己一步一步去设计页面的每一个元素，到最后的完成前后端交互，真的理解了许多前端知识。\n\n原本是定于7月5号发布博客的第一版本，但由于本学期欠的一些账弄得期末考试复习花费了大量时间（没错，这应该就是我拖延上线时间的借口吧~~），博客也一直放下，没有太多时间去搭建。放假回家也是完全被假期的愉快感消磨掉了激情，所幸的是，我还并没有放弃，总算是完成了当初给自己定下的目标。\n\n> 有些事情不是看到希望才去坚持，而是坚持了才会看到希望\n\n对于这个网站的搭建自己付出了太多时间了，虽然这也并不是我认为的最好版本，等自己能力以及水平进一步提升之后，我想我应该还会为此折腾吧。\n\n##想想再说点啥吧\n对于这个博客，我也准备借此记录下我的一些学习日志、生活日常、旅行风景等等。大学生活真的是还没怎么享受就快要结束了，没办法，自己选的路，再怎么也得往下走。记录记录人生，去看看世界，给未来的自己留下点青春的影子\n\n当然最重要的还是要借此多总结学习中的一些问题以及学习中踩得一些坑来提升自己的能力吧，在此我也不给自己立啥flag了，反正那些总会倒的，不如看自己的心情了，哪天心情好了，上来写点学习心得呀或是吐槽吐槽今天又在学校食堂里吃出哪样“高蛋白”吖。本人文采一般，向来也不是能一个人哔哔很多话的，万事都有开头，坚持下去，一切都会好起来的。\n\n总之，这个博客也将是我程序猿生涯的一个新的开始吧，保持生活的激情，坚持走下去，程序猿这条路很枯燥、很漫长，只要坚守本心，一切困难与寂寞都将如同泡沫。加油，向着梦想中的bat前进吧。','原创','2023-07-16','2023-07-16','http://localhost:8080/2023071601','概要就是我不知道',0,0,0,1,0),(2,2023040503,'yiming','yiming','test2','自我介绍，我叫彭一鸣，是一名地球人','原创','2023-08-09','2023-08-09','11','自我介绍',0,2023072601,0,2,0),(3,2023040504,'yiming','yiming','test2','自我介绍，我叫彭一鸣，123','原创','2023-08-09','2023-08-09','11','自我介绍',0,2023072601,0,1,0),(4,202309171,'yiming','yiming','Java开发日记1','这是一篇测试文章用于学习模块的测试的文章','原创','2023-09-17','2023-09-17','123','网站开发日记之测试',0,NULL,NULL,3,0),(5,202309172,'yiming','yiming','SpringBoot开发日记1','这是一篇测试文章用于学习模块的测试的文章1','原创','2023-09-17','2023-09-17','123','网站开发日记之测试1',0,NULL,NULL,4,0),(6,202309173,'yiming','yiming','SpringBoot开发日记1','这是一篇测试文章用于学习模块的测试的文章2','原创','2023-09-17','2023-09-17','123','网站开发日记之测试2',0,NULL,NULL,4,0);
/*!40000 ALTER TABLE `articles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `categories` (
  `category_name` varchar(255) NOT NULL,
  `category_id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES ('我的故事',1),('大学生活',2),('Java开发',3),('SpringBoot',4);
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tags`
--

DROP TABLE IF EXISTS `tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tags` (
  `tag_id` int(11) NOT NULL AUTO_INCREMENT,
  `tagName` varchar(255) NOT NULL,
  PRIMARY KEY (`tag_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tags`
--

LOCK TABLES `tags` WRITE;
/*!40000 ALTER TABLE `tags` DISABLE KEYS */;
INSERT INTO `tags` VALUES (1,'生活'),(2,'苦难'),(3,'学习');
/*!40000 ALTER TABLE `tags` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-09-25 23:13:51
