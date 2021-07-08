# --- !Ups
CREATE TABLE IF NOT EXISTS `scalatestdb1`.`apprentices` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL DEFAULT NULL,
  `dob` DATE,
  `course` VARCHAR(100) NULL DEFAULT NULL,
  `dlc` VARCHAR(45) DEFAULT `Ali Cankal`,
  PRIMARY KEY (`id`))
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8

# --- !Downs
drop table 'apprentices'