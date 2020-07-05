update sec_user set dtype = 'orderstore_ExtUser';
insert into orderstore_status_entity_types (ID, VERSION, CREATE_TS, CREATED_BY, NAME, entity_type)
values('92c98203-398b-4556-8434-d44bd68748c9', 1, now(), 'admin', 'Заказы на производство', 'OrderProduct');
insert into orderstore_address_types (ID, VERSION, CREATE_TS, CREATED_BY, NAME, CODE)
values('d0af1c48-aeef-4685-a84f-1194a9218b92', 1, now(), 'admin', 'Фактический', 'FACT');
insert into orderstore_address_types (ID, VERSION, CREATE_TS, CREATED_BY, NAME, CODE)
values('30514d4a-aa7b-4597-a364-558379dc01c4', 1, now(), 'admin', 'Юридический', 'LEGAL');
insert into orderstore_address_types (ID, VERSION, CREATE_TS, CREATED_BY, NAME, CODE)
values('77a9a44f-b200-4d16-8ed1-18462a8116a5', 1, now(), 'admin', 'Адрес доставки', 'DELIVERY');
insert into orderstore_contact_type (ID, VERSION, CREATE_TS, CREATED_BY, NAME, CODE)
values('e5a1ba08-2c76-b119-275f-005cf98bd39e', 1, now(), 'admin', 'Телефон', 'PHONE');
insert into orderstore_contact_type (ID, VERSION, CREATE_TS, CREATED_BY, NAME, CODE)
values('0dd97829-3aa3-954b-308f-d8599ca5aa80', 1, now(), 'admin', 'Рабочий телефон', 'WORK_PHONE');
insert into orderstore_contact_type (ID, VERSION, CREATE_TS, CREATED_BY, NAME, CODE)
values('ef2ca1ed-4aef-096e-6e27-405c8b40995a', 1, now(), 'admin', 'Сотовый телефон', 'MOBILE');
insert into orderstore_contact_type (ID, VERSION, CREATE_TS, CREATED_BY, NAME, CODE)
values('212e00a9-ea17-4942-8c30-b1ca95a7ca5b', 1, now(), 'admin', 'Электронная почта', 'EMAIL');
