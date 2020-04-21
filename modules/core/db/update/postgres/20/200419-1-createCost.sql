create table ORDERSTORE_COST (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    COST_TYPE_ID uuid not null,
    DATA_BEGIN date not null,
    DATE_END date,
    VALUE_ double precision not null,
    GOOD_ID uuid,
    --
    primary key (ID)
);