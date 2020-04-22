create table ORDERSTORE_STOCK_MOVEMENT (
    ID uuid,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    VERSION integer not null,
    --
    STOCK_ID uuid not null,
    DATE_FACT date not null,
    NUM varchar(255) not null,
    CREATE_TIME timestamp not null,
    PRIORITY bigint not null,
    INCOME boolean not null,
    NOTES varchar(1000),
    --
    primary key (ID)
);