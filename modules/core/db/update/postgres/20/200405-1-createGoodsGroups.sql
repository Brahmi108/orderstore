create table ORDERSTORE_GOODS_GROUPS (
    ID uuid,
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    --
    OWNER_ID uuid not null,
    NAME varchar(255) not null,
    GCOMMENT varchar(255),
    --
    primary key (ID)
);