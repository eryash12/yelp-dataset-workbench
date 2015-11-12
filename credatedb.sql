set define off
CREATE TABLE "YELP_USERS" 
   (	"USER_ID" VARCHAR2(100 BYTE) NOT NULL ENABLE, 
	"YELPING_SINCE" VARCHAR2(26 BYTE) NOT NULL ENABLE, 
	"NAME" VARCHAR2(255 BYTE) NOT NULL ENABLE, 
	"FANS" NUMBER(*,0) NOT NULL ENABLE, 
	"AVERAGE_STARS" FLOAT(126), 
	"TYPE" VARCHAR2(20 BYTE), 
	"REVIEW_COUNT" NUMBER(*,0), 
	"VOTES_FUNNY" NUMBER(*,0), 
	"VOTES_USEFUL" NUMBER(*,0), 
	"VOTES_COOL" NUMBER(*,0), 
	"ELITE" VARCHAR2(255 BYTE), 
	 PRIMARY KEY ("USER_ID")
  )  ;
  
CREATE TABLE "USERS_FRIENDS" 
   (	"USER_ID" VARCHAR2(100 BYTE) NOT NULL ENABLE, 
	"FRIEND_USER_ID" VARCHAR2(100 BYTE) NOT NULL ENABLE, 
	 FOREIGN KEY ("USER_ID")
	  REFERENCES "YELP_USERS" ("USER_ID") ON DELETE CASCADE ENABLE 
	 
   );
   
CREATE TABLE "USERS_COMPLIMENTS" 
   (	"USER_ID" VARCHAR2(100 BYTE) NOT NULL ENABLE, 
	"COMPLIMENT_TYPE" VARCHAR2(100 BYTE), 
	"COMPLIMENTS_COUNT" NUMBER(*,0), 
	 FOREIGN KEY ("USER_ID")
	  REFERENCES "YELP_USERS" ("USER_ID") ON DELETE CASCADE ENABLE
   )
   
create table yelp_business
(
business_id VARCHAR2(100) NOT NULL PRIMARY KEY,
address VARCHAR2(256) NOT null,
open VARCHAR2(10),
city VARCHAR2(40) not null,
state VARCHAR2(20) not null,
lat number,
lon number,
review_count number,
name VARCHAR2(100) not null,
stars float,
type varchar2(20)
);

create table business_hours
(
business_id VARCHAR2(100) NOT NULL ,
day VARCHAR2(20) not null ,
close VARCHAR2(20),
open VARCHAR2(20),
foreign key (business_id) REFERENCES  yelp_business(business_id) on delete cascade
);

create table business_cat
(
business_id VARCHAR2(100) NOT NULL ,
cat_name VARCHAR2(30) not null,
foreign key (business_id) REFERENCES  yelp_business(business_id) on delete cascade
);
create table business_hood
(
business_id VARCHAR2(100) NOT NULL ,
hood_name VARCHAR2(30) not null,
foreign key (business_id) REFERENCES  yelp_business(business_id) on delete cascade
);
create table business_attributes
(
business_id VARCHAR2(100) NOT NULL ,
att_cat VARCHAR2(30) ,
att_name VARCHAR2(40) not null,
att_value VARCHAR2(30) not null,
foreign key (business_id) REFERENCES  yelp_business(business_id) on delete cascade
);

CREATE TABLE YELP_REVIEWS
(
VOTES_USEFUL INT,
VOTES_FUNNY INT,
VOTES_COOL INT,
USER_ID VARCHAR(255) NOT NULL,
REVIEW_ID VARCHAR(26) NOT NULL PRIMARY KEY,
STARS INT,
DATE_REV VARCHAR(50),
TEXT VARCHAR(4000),
TYPE VARCHAR(20) ,
BUSINESS_ID VARCHAR(40) NOT NULL,

FOREIGN KEY (BUSINESS_ID) REFERENCES YELP_BUSINESS(BUSINESS_ID)
ON DELETE CASCADE,
FOREIGN KEY (USER_ID) REFERENCES YELP_USERS(USER_ID)
ON DELETE CASCADE

);
Create table yelp_checkin
(

business_id varchar(100) Not null,
type Varchar(100),
checkin_days_hrs varchar(10) ,
no_of_checkins int,
FOREIGN KEY (BUSINESS_ID) REFERENCES YELP_BUSINESS(BUSINESS_ID)
ON DELETE CASCADE
);
