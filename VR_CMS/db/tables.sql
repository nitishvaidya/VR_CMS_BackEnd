use vr_master_server;

drop table server_ids;
create table server_ids(
`id` Integer not null AUTO_INCREMENT,
`server_id` varchar(100) NOT NULL,
`school_name` varchar(20) NOT NULL,
`address` varchar(100) NOT NULL,
`city` varchar(10) NOT NULL,
`website` varchar(20) NOT NULL,
`phone` varchar(10) NOT NULL,
PRIMARY KEY(id)
);

drop table videos;
create table videos(
id INTEGER not null AUTO_INCREMENT,
name varchar(100) not null,
type varchar(10) not null,
category varchar(10) not null,
imageName varchar(100) not null,
imageDir varchar(100) not null,
videoName varchar(100) not null,
videoDir varchar(100) not null,
description varchar(100) not null,
timestamp long,
PRIMARY KEY(id)
);

drop table servers_to_videos;
create table servers_to_videos(
server_id INTEGER NOT NULL REFERENCES server_ids(id) ON DELETE CASCADE ,
video_id INTEGER NOT NULL REFERENCES videos(id) ON DELETE CASCADE 
);




