create table notification
(
id BIGINT auto_increment,
notifier BIGINT,
receiver bigint,
outerId bigint,
type int not null,
gmt_create bigint,
status int default 0,
constraint notification_pk
primary key (id)
);