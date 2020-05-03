create table ORDERSTORE_STATUS_ENTITY_TYPES (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(100) not null,
    ENTITY_TYPE varchar(100) not null,
    --
    primary key (ID)
);