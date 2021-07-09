# --- !Ups
CREATE TABLE IF NOT EXISTS `scalatestdb1`.`apprentice` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL DEFAULT NULL,
  `dob` VARCHAR(45),
  `course` VARCHAR(100),
  `dlc` VARCHAR(45),
  PRIMARY KEY (`id`))
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8

# --- !Downs
drop table 'apprentice'