create table ORDERSTORE_COST_TYPE (
    ID uuid,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    --
    NAME varchar(255) not null,
    OWNER_ID uuid not null,
    DEF boolean,
    --
    primary key (ID)
);