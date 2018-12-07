INSERT INTO permission.module (id, title, description, url, version, is_active, inactive_message) VALUES ('6c37e40e-e22f-4fe2-9161-721d5a97c912', 'permission-service', null, '/permission/v1', 'v1', true, null);
INSERT INTO permission.module (id, title, description, url, version, is_active, inactive_message) VALUES ('d26d4f95-a9fb-4878-aa07-019e399bd15c', 'account-service', null, '/account/v1', 'v1', true, null);

INSERT INTO permission.endpoint (id, title, description, url, method, version, is_active, inactive_message, module_id) VALUES ('b45ef269-3418-4c2c-af7a-d5427ecf22c0', null, null, '/endpoint', 'PUT', 'v1', true, null, '6c37e40e-e22f-4fe2-9161-721d5a97c912');
INSERT INTO permission.endpoint (id, title, description, url, method, version, is_active, inactive_message, module_id) VALUES ('39b0f64b-93a8-41d0-9bed-943163d0f973', null, null, '/endpoint/*', 'GET', 'v1', true, null, '6c37e40e-e22f-4fe2-9161-721d5a97c912');
INSERT INTO permission.endpoint (id, title, description, url, method, version, is_active, inactive_message, module_id) VALUES ('a39c3dd9-ba40-4a4a-b41d-4b3e1c37392d', null, null, '/endpoint/pack', 'POST', 'v1', true, null, '6c37e40e-e22f-4fe2-9161-721d5a97c912');
INSERT INTO permission.endpoint (id, title, description, url, method, version, is_active, inactive_message, module_id) VALUES ('44f5b283-e7ec-4f2b-b399-541d54e90637', null, null, '/group', 'PUT', 'v1', true, null, '6c37e40e-e22f-4fe2-9161-721d5a97c912');
INSERT INTO permission.endpoint (id, title, description, url, method, version, is_active, inactive_message, module_id) VALUES ('80b6e09a-6dff-499a-9a53-1ea29ce4a145', null, null, '/group/*', 'GET', 'v1', true, null, '6c37e40e-e22f-4fe2-9161-721d5a97c912');
INSERT INTO permission.endpoint (id, title, description, url, method, version, is_active, inactive_message, module_id) VALUES ('04e52a91-1151-49b8-9d1a-1b0c13c5a7ea', null, null, '/group/permission-map', 'GET', 'v1', true, null, '6c37e40e-e22f-4fe2-9161-721d5a97c912');
INSERT INTO permission.endpoint (id, title, description, url, method, version, is_active, inactive_message, module_id) VALUES ('d28fc761-b690-4e16-99aa-0709647d44f3', null, null, '/group-user', 'POST', 'v1', true, null, '6c37e40e-e22f-4fe2-9161-721d5a97c912');
INSERT INTO permission.endpoint (id, title, description, url, method, version, is_active, inactive_message, module_id) VALUES ('393e7e58-bd0e-44db-ba1b-b61f757f48e1', null, null, '/group-user/my-group', 'GET', 'v1', true, null, '6c37e40e-e22f-4fe2-9161-721d5a97c912');
INSERT INTO permission.endpoint (id, title, description, url, method, version, is_active, inactive_message, module_id) VALUES ('fe985db0-b3c6-4b04-ac05-04a15f0af20a', null, null, '/module', 'PUT', 'v1', true, null, '6c37e40e-e22f-4fe2-9161-721d5a97c912');
INSERT INTO permission.endpoint (id, title, description, url, method, version, is_active, inactive_message, module_id) VALUES ('0a021234-334f-462e-8558-cfcea5998b6e', null, null, '/module/*', 'GET', 'v1', true, null, '6c37e40e-e22f-4fe2-9161-721d5a97c912');
INSERT INTO permission.endpoint (id, title, description, url, method, version, is_active, inactive_message, module_id) VALUES ('2e48450e-8693-4eb0-8a7b-d75bbd6e1ea5', null, null, '/auth/check', 'GET', 'v1', true, null, 'd26d4f95-a9fb-4878-aa07-019e399bd15c');
INSERT INTO permission.endpoint (id, title, description, url, method, version, is_active, inactive_message, module_id) VALUES ('4720765e-d211-4300-a5e0-07e5c7d97940', null, null, '/auth/signin', 'POST', 'v1', true, null, 'd26d4f95-a9fb-4878-aa07-019e399bd15c');
INSERT INTO permission.endpoint (id, title, description, url, method, version, is_active, inactive_message, module_id) VALUES ('7b27722b-46c2-4095-8bd9-0acea8a552f5', null, null, '/auth/signup', 'POST', 'v1', true, null, 'd26d4f95-a9fb-4878-aa07-019e399bd15c');
INSERT INTO permission.endpoint (id, title, description, url, method, version, is_active, inactive_message, module_id) VALUES ('7cbe8357-4543-40b6-a407-95b7c7e60724', null, null, '/user', 'PUT', 'v1', true, null, 'd26d4f95-a9fb-4878-aa07-019e399bd15c');
INSERT INTO permission.endpoint (id, title, description, url, method, version, is_active, inactive_message, module_id) VALUES ('5bf2e936-8dfd-4441-a457-d07a8740e799', null, null, '/user/*', 'DELETE', 'v1', true, null, 'd26d4f95-a9fb-4878-aa07-019e399bd15c');
INSERT INTO permission.endpoint (id, title, description, url, method, version, is_active, inactive_message, module_id) VALUES ('f1afdef5-5b55-46b0-9deb-a855ba47a6c1', null, null, '/user/me', 'GET', 'v1', true, null, 'd26d4f95-a9fb-4878-aa07-019e399bd15c');
INSERT INTO permission.endpoint (id, title, description, url, method, version, is_active, inactive_message, module_id) VALUES ('686bafab-c066-4a86-8a5b-1de0cb7f7e1b', null, null, '/user/ban/*', 'GET', 'v1', true, null, 'd26d4f95-a9fb-4878-aa07-019e399bd15c');
INSERT INTO permission.endpoint (id, title, description, url, method, version, is_active, inactive_message, module_id) VALUES ('60266f15-33e5-4212-875b-aacf3ad79063', null, null, '/user/business', 'PUT', 'v1', true, null, 'd26d4f95-a9fb-4878-aa07-019e399bd15c');
INSERT INTO permission.endpoint (id, title, description, url, method, version, is_active, inactive_message, module_id) VALUES ('510dd40f-2e84-43c3-bf27-6bb81ef128d0', null, null, '/user/isExist', 'GET', 'v1', true, null, 'd26d4f95-a9fb-4878-aa07-019e399bd15c');
INSERT INTO permission.endpoint (id, title, description, url, method, version, is_active, inactive_message, module_id) VALUES ('381a103e-7fd2-4485-b676-ccf2b790daf9', null, null, '/user/isKYCPassed', 'GET', 'v1', true, null, 'd26d4f95-a9fb-4878-aa07-019e399bd15c');
INSERT INTO permission.endpoint (id, title, description, url, method, version, is_active, inactive_message, module_id) VALUES ('ffe2acf1-b527-474a-ab6c-51e774d8d755', null, null, '/email', 'PUT', 'v1', true, null, 'd26d4f95-a9fb-4878-aa07-019e399bd15c');
INSERT INTO permission.endpoint (id, title, description, url, method, version, is_active, inactive_message, module_id) VALUES ('065c38fc-aeb4-4c1f-865d-72e8cddfe36b', null, null, '/email/*', 'DELETE', 'v1', true, null, 'd26d4f95-a9fb-4878-aa07-019e399bd15c');
INSERT INTO permission.endpoint (id, title, description, url, method, version, is_active, inactive_message, module_id) VALUES ('1716f4d2-b8a7-4de9-a784-0371f7654666', null, null, '/email/code', 'GET', 'v1', true, null, 'd26d4f95-a9fb-4878-aa07-019e399bd15c');
INSERT INTO permission.endpoint (id, title, description, url, method, version, is_active, inactive_message, module_id) VALUES ('a7f22ad9-d173-4ceb-b8ab-941a6de618fb', null, null, '/email/by/user', 'GET', 'v1', true, null, 'd26d4f95-a9fb-4878-aa07-019e399bd15c');
INSERT INTO permission.endpoint (id, title, description, url, method, version, is_active, inactive_message, module_id) VALUES ('9638d351-683d-493f-8d75-7f325efb0ec5', null, null, '/phone', 'PUT', 'v1', true, null, 'd26d4f95-a9fb-4878-aa07-019e399bd15c');
INSERT INTO permission.endpoint (id, title, description, url, method, version, is_active, inactive_message, module_id) VALUES ('6eccf847-16cb-48a3-943e-2816a7bc3552', null, null, '/phone/*', 'DELETE', 'v1', true, null, 'd26d4f95-a9fb-4878-aa07-019e399bd15c');
INSERT INTO permission.endpoint (id, title, description, url, method, version, is_active, inactive_message, module_id) VALUES ('8369c1b5-e828-45db-a599-2e058a557eec', null, null, '/phone/code', 'GET', 'v1', true, null, 'd26d4f95-a9fb-4878-aa07-019e399bd15c');
INSERT INTO permission.endpoint (id, title, description, url, method, version, is_active, inactive_message, module_id) VALUES ('e956bc00-e96e-4507-8b8b-d187202ec894', null, null, '/phone/by/user', 'GET', 'v1', true, null, 'd26d4f95-a9fb-4878-aa07-019e399bd15c');