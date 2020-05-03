create table ORDERSTORE_STATUS_ENTITY (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    OWNER_ID uuid not null,
    NAME varchar(100) not null,
    ENTITY_TYPE_ID uuid not null,
    --
    primary key (ID)
);