CREATE DATABASE Jukebox;
use jukebox;

desc user;
desc userplaylist;
desc musictable;
desc podcasttable;

alter table user drop index password;


CREATE TABLE `User`(
    `userId` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `userName` VARCHAR(50) NOT NULL,
    `Name` VARCHAR(50) NOT NULL,
    `phoneNo` VARCHAR(50) NOT NULL UNIQUE,
    `Password` VARCHAR(50) NOT NULL 
);
select * from user where phoneNo='3698521478';
delete from user where userid=7 ;
insert into `user`(username,name,phoneno,password) values('bill123','Bill','7894561230','Bill@321');
CREATE TABLE `musicTable`(
    `songid` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY, 
    `artistname` VARCHAR(50) NOT NULL,
    `albumname` VARCHAR(50) NOT NULL,
    `songname` VARCHAR(50) NOT NULL,
    `duration` VARCHAR(5) NOT NULL,
    `genre` VARCHAR(50) NOT NULL,
    `rating` DOUBLE NOT NULL ,
    `URL` VARCHAR(1000) NOT NULL
);
SELECT 
    *
FROM
    musictable;
    select count(songid) from musictable where songid=7;

CREATE TABLE `podcasttable`(
    `podcastid` INT UNSIGNED NOT NULL ,
    `artistname` VARCHAR(50) NOT NULL,
    `podcastname` VARCHAR(150) NOT NULL,
    `genre` VARCHAR(50) NOT NULL,
    `episodeid` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `episodeName` VARCHAR(150) NOT NULL,
    `episodeno` INT NOT NULL,
    `episoderating` DOUBLE NOT NULL,
    `episodeDuration` VARCHAR(10) NOT NULL,
    `episodeReleaseDate` DATE NOT NULL,
    `URL` VARCHAR(1000) NOT NULL
);
select * from podcasttable;
select * from podcasttable  order by podcastname;
CREATE TABLE `userPlaylist`(
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `playlistname` VARCHAR(50) NOT NULL UNIQUE,
    `userid` INT UNSIGNED NOT NULL,
    `playlistid` INT NOT NULL ,
    `songid` INT unsigned NULL,
    `podcastid` INT unsigned NULL
);
alter table userplaylist drop index playlistname;
select * from userplaylist;
select songid from userplaylist where userid=1 and playlistname='Mine';
ALTER TABLE userplaylist
  DROP COLUMN playlistid;

update userplaylist set playlistid=2 where playlistname='Minepod';
select distinct(playlistname),songid from userplaylist where userid=1; 
drop table podcasttable;
ALTER TABLE podcasttable modify column `episodereleasedate` date NOT NULL;
desc userplaylist;
alter table podcasttable drop column rating;
ALTER TABLE
    `userPlaylist` ADD FOREIGN KEY(`userid`) REFERENCES `User`(`userId`);
ALTER TABLE
    `userPlaylist` ADD CONSTRAINT `userplaylist_songid_foreign` FOREIGN KEY(`songid`) REFERENCES `musicTable`(`songid`);
ALTER TABLE
    `userPlaylist` ADD CONSTRAINT `userplaylist_podcastid_foreign` FOREIGN KEY(`podcastid`) REFERENCES `podcasttable`(`episodeid`);
    
select * from musictable;
select songid,podcastid from userPlaylist where playlistname='Mine' and userid=1;
select songid,podcastid from userPlaylist where playlistname='Minepod' and userid=1;
select count(playlistname) from userplaylist where playlistname='Mine';
select songid,podcastid from userPlaylist where podcastid is null;
ALTER TABLE userplaylist
ALTER songid SET DEFAULT null;
ALTER TABLE userplaylist
ALTER songid DROP DEFAULT;
select * from userplaylist where playlistname='Minepod';
select * from musictable;
select count(podcastid) from podcasttable;
delete from userplaylist where id=11;
select count(userName) from user where userName='bill123';
select count(songid) from musictable where songid=6;
select count(userName) from user where userName='qwere';
delete from userplaylist where userid=1 and playlistname='MinePod' and podcastid=21;
select count(songid) from userplaylist where userid=1 and playlistname='MinePod' and songid=6;