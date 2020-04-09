create table ORDERSTORE_GOOD_NAME_OPTION (
    ID uuid,
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    --
    NAME varchar(500) not null,
    GOODS_ID uuid,
    --
    primary key (ID)
);