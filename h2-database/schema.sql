create table T_USER
(
	ID BIGINT auto_increment
		primary key,
	CREATED_AT TIMESTAMP,
	EMAIL VARCHAR(255),
	ENABLED BOOLEAN,
	LOCKED BOOLEAN,
	PASSWORD VARCHAR(255),
	USER_ROLE VARCHAR(255),
	USERNAME VARCHAR(255) not null
		constraint UK_JHIB4LEGEHRM4YSCX9T3LIRQI
			unique
);

create table T_COMMUNITY
(
	ID BIGINT auto_increment
		primary key,
	CREATED_AT TIMESTAMP,
	DESCRIPTION CLOB,
	NAME VARCHAR(255)
		constraint UK_JP5QLQMKF0P264KLB1TNA2FES
			unique,
	CREATED_BY_ID BIGINT,
	constraint FK1UHTRSNPNJ2CU8WKW4DRRW1IQ
		foreign key (CREATED_BY_ID) references T_USER (ID)
);

create table T_CONFIRMATION_TOKEN
(
	ID BIGINT auto_increment
		primary key,
	CONFIRMED_AT TIMESTAMP,
	CREATED_AT TIMESTAMP,
	EXPIRES_AT TIMESTAMP,
	TOKEN VARCHAR(255)
		constraint UK_8K1ETFQFR9TYYAVBUV33OO0YP
			unique,
	USER_ID BIGINT,
	constraint FK2JVWPR7H0AXN8DVH32AGH3UK6
		foreign key (USER_ID) references T_USER (ID)
);

create table T_POST
(
	ID BIGINT auto_increment
		primary key,
	BODY CLOB,
	CREATED_AT TIMESTAMP,
	TITLE VARCHAR(255),
	URL VARCHAR(255),
	VOTE_COUNT INTEGER,
	COMMUNITY_ID BIGINT,
	USER_ID BIGINT,
	constraint FK18F01T655NKCUPTQ9OXP04SGN
		foreign key (USER_ID) references T_USER (ID),
	constraint FK37VFGVK1W6XGHYC80IWLY93BX
		foreign key (COMMUNITY_ID) references T_COMMUNITY (ID)
);

create table T_COMMENT
(
	ID BIGINT auto_increment
		primary key,
	BODY CLOB,
	CREATED_AT TIMESTAMP,
	POST_ID BIGINT,
	USER_ID BIGINT,
	constraint FKSA3HL9A6MU30DCT1JNN2BWVEM
		foreign key (POST_ID) references T_POST (ID),
	constraint FKTAMAOACCTQ4QPKO6BVTV0KE1P
		foreign key (USER_ID) references T_USER (ID)
);

create table T_USER_COMMUNITY
(
	USER_ID BIGINT not null,
	COMMUNITY_ID BIGINT not null,
	primary key (USER_ID, COMMUNITY_ID),
	constraint FK58L0CL8KBURTY2LQUXE59K7DH
		foreign key (COMMUNITY_ID) references T_COMMUNITY (ID),
	constraint FKFQ5KMPO8U2UJPFAKLNW0F8T0T
		foreign key (USER_ID) references T_USER (ID)
);

create table T_VOTE
(
	ID BIGINT auto_increment
		primary key,
	VOTE_TYPE VARCHAR(255),
	POST_ID BIGINT,
	USER_ID BIGINT,
	constraint FK2QOKO87N6900X9B8IQMMD9S7L
		foreign key (POST_ID) references T_POST (ID),
	constraint FKJ7GXL5XKIKTOAN3AB6PD2GYFX
		foreign key (USER_ID) references T_USER (ID)
);

