create table ORDERSTORE_OWNER (
    ID uuid,
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    --
    NAME varchar(500) not null,
    --
    primary key (ID)
);