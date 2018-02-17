
CREATE TABLE `user` (
  `user_id` int(20) NOT NULL AUTO_INCREMENT,
  `emailid` varchar(45) NOT NULL,
  `mobileno` tinytext NOT NULL,
  `username` varchar(20) NOT NULL,
  `uuid` varchar(20) DEFAULT NULL,
  `appversion` varchar(255) DEFAULT NULL,
  `gcmregistartionkey` longtext,
  `password` varchar(40) DEFAULT NULL,
  `createddate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`)
) ;


CREATE TABLE `headunit` (
  `headunit_id` int(20) NOT NULL AUTO_INCREMENT,
  `user_id` int(20) NOT NULL,
  `carname` varchar(20) NOT NULL,
  `hduuid` varchar(20) DEFAULT NULL,
  `appversion` varchar(20) DEFAULT NULL,
  `gcmregistartionkey` longtext,
  `createddate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`headunit_id`),
  KEY `HEADUNIT_FK` (`user_id`),
  CONSTRAINT `headunit_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE
) ;


CREATE TABLE `location` (
  `location_id` int(20) NOT NULL AUTO_INCREMENT,
  `latitude` float NOT NULL,
  `altitude` float NOT NULL,
  `longitude` float NOT NULL,
  `headunit_id` int(20) NOT NULL,
  `address` varchar(45) NOT NULL,
  `createddate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`location_id`),
  KEY `headunit_id_idx` (`headunit_id`),
  CONSTRAINT `headunit_id` FOREIGN KEY (`headunit_id`) REFERENCES `headunit` (`headunit_id`) ON DELETE CASCADE
) ;


CREATE TABLE `tripuser` (`tripuser_id` int(20) NOT NULL AUTO_INCREMENT,
`tuuid` varchar(20) DEFAULT NULL,`createddate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`tripuser_id`)
);


CREATE TABLE `tripappdata` (
  `tripappdata_id` int(20) NOT NULL AUTO_INCREMENT,
  `tripid` int(20) NOT NULL,
  `tuuid`  varchar(20) NOT NULL,
   `maxspeed`  varchar(20) DEFAULT NULL,
   `maxrpm`  varchar(20) DEFAULT NULL,
   `startlocation`  varchar(20) DEFAULT NULL,
   `endlocation`  varchar(20) DEFAULT NULL,
   `tripstartdatetime` timestamp NOT NULL,
   `tripenddatetime` timestamp NOT NULL,
   `engineruntime`  varchar(20) DEFAULT NULL,
   `fuellevelstart`  varchar(20) DEFAULT NULL,
   `fuellevelend`  varchar(20) DEFAULT NULL,
   `startdistance`  varchar(20) DEFAULT NULL,
   `enddistance`  varchar(20) DEFAULT NULL,
   `startlatitude`  varchar(20) DEFAULT NULL,
   `endlatitude`  varchar(20) DEFAULT NULL,
   `startlongitude`  varchar(20) DEFAULT NULL,
   `endlongitude`  varchar(20) DEFAULT NULL,
   `createddate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (`tripappdata_id`)
);
