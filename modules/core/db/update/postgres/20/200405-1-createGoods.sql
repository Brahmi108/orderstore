create table ORDERSTORE_GOODS (
    ID uuid,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    --
    NAME varchar(500) not null,
    CODE varchar(100),
    UNIT_ID uuid not null,
    GROUP_ID uuid not null,
    OWNER_ID uuid not null,
    --
    primary key (ID)
);