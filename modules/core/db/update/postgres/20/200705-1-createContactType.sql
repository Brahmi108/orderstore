create table ORDERSTORE_CONTACT_TYPE (
    ID uuid,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    VERSION integer not null,
    --
    NAME varchar(200) not null,
    CODE varchar(50) not null,
    --
    primary key (ID)
);