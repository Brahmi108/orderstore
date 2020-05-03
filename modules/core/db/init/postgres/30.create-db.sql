update sec_user set dtype = 'orderstore_ExtUser';
insert into orderstore_status_entity_types (ID, VERSION, CREATE_TS, CREATED_BY, NAME, entity_type)
values('92c98203-398b-4556-8434-d44bd68748c9', 1, now(), 'admin', 'Заказы на производство', 'OrderProduct');
