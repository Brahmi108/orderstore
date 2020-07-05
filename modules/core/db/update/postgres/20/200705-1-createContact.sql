create table ORDERSTORE_CONTACT (
    ID uuid,
    VERSION integer not null,
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    --
    CONTACT varchar(200) not null,
    TYPE uuid not null,
    --
    primary key (ID)
);