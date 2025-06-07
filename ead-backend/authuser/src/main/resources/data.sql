delete from users_roles;
delete from roles;
delete from USERS;

INSERT INTO USERS (id, cpf, creation_date, email, full_name, image_url, last_update_date, password, phone_number, user_status, user_type, username)
VALUES
    ('123e4567-e89b-12d3-a456-426614174001', '12345678901', NOW(), 'user1@example.com', 'User One', NULL, NOW(), '$2a$10$eaCkzdxX6IiMMGYx5NZf..TtErTf7/XyN5gQkxLLCOah4p8H7CuDi', '123-456-7890', 'ACTIVE', 'ADMIN', 'user1'),
    ('123e4567-e89b-12d3-a456-426614174002', '23456789012', NOW(), 'user2@example.com', 'User Two', NULL, NOW(), '$2a$10$eaCkzdxX6IiMMGYx5NZf..TtErTf7/XyN5gQkxLLCOah4p8H7CuDi', '234-567-8901', 'ACTIVE', 'STUDENT', 'user2'),
    ('123e4567-e89b-12d3-a456-426614174003', '34567890123', NOW(), 'user3@example.com', 'User Three', NULL, NOW(), '$2a$10$eaCkzdxX6IiMMGYx5NZf..TtErTf7/XyN5gQkxLLCOah4p8H7CuDi', '345-678-9012', 'BLOCKED', 'STUDENT', 'user3'),
    ('123e4567-e89b-12d3-a456-426614174004', '45678901234', NOW(), 'user4@example.com', 'User Four', NULL, NOW(), '$2a$10$eaCkzdxX6IiMMGYx5NZf..TtErTf7/XyN5gQkxLLCOah4p8H7CuDi', '456-789-0123', 'ACTIVE', 'STUDENT', 'user4'),
    ('123e4567-e89b-12d3-a456-426614174005', '56789012345', NOW(), 'user5@example.com', 'User Five', NULL, NOW(), '$2a$10$eaCkzdxX6IiMMGYx5NZf..TtErTf7/XyN5gQkxLLCOah4p8H7CuDi', '567-890-1234', 'BLOCKED', 'STUDENT', 'user5'),
    ('123e4567-e89b-12d3-a456-426614174006', '67890123456', NOW(), 'user6@example.com', 'User Six', NULL, NOW(), '$2a$10$eaCkzdxX6IiMMGYx5NZf..TtErTf7/XyN5gQkxLLCOah4p8H7CuDi', '678-901-2345', 'ACTIVE', 'STUDENT', 'user6');

insert into roles values ('24924ef3-15ca-44e7-b615-d199b8506f65', 'ROLE_ADMIN');
insert into roles values ('91d720a0-a7db-4736-87a7-e1cd4ddabc2f', 'ROLE_INSTRUCTOR');
insert into roles values ('d831507e-37db-48f3-aab7-1a53cca2053f', 'ROLE_STUDENT');
insert into roles values ('9c986ff6-32c8-4457-84da-ce81edcd3736', 'ROLE_USER');

INSERT INTO users_roles (users_id, roles_id)
VALUES
    ('123e4567-e89b-12d3-a456-426614174001', '24924ef3-15ca-44e7-b615-d199b8506f65'),
    ('123e4567-e89b-12d3-a456-426614174002', 'd831507e-37db-48f3-aab7-1a53cca2053f'),
    ('123e4567-e89b-12d3-a456-426614174003', 'd831507e-37db-48f3-aab7-1a53cca2053f'),
    ('123e4567-e89b-12d3-a456-426614174004', 'd831507e-37db-48f3-aab7-1a53cca2053f'),
    ('123e4567-e89b-12d3-a456-426614174005', 'd831507e-37db-48f3-aab7-1a53cca2053f'),
    ('123e4567-e89b-12d3-a456-426614174006', 'd831507e-37db-48f3-aab7-1a53cca2053f');
