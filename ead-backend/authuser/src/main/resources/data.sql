delete from users_courses;
delete from USERS;
INSERT INTO USERS (id, cpf, creation_date, email, full_name, image_url, last_update_date, password, phone_number, user_status, user_type, username)
VALUES
    ('123e4567-e89b-12d3-a456-426614174001', '12345678901', NOW(), 'user1@example.com', 'User One', NULL, NOW(), 'password1', '123-456-7890', 'ACTIVE', 'STUDENT', 'user1'),
    ('123e4567-e89b-12d3-a456-426614174002', '23456789012', NOW(), 'user2@example.com', 'User Two', NULL, NOW(), 'password2', '234-567-8901', 'ACTIVE', 'STUDENT', 'user2'),
    ('123e4567-e89b-12d3-a456-426614174003', '34567890123', NOW(), 'user3@example.com', 'User Three', NULL, NOW(), 'password3', '345-678-9012', 'BLOCKED', 'STUDENT', 'user3'),
    ('123e4567-e89b-12d3-a456-426614174004', '45678901234', NOW(), 'user4@example.com', 'User Four', NULL, NOW(), 'password4', '456-789-0123', 'ACTIVE', 'STUDENT', 'user4'),
    ('123e4567-e89b-12d3-a456-426614174005', '56789012345', NOW(), 'user5@example.com', 'User Five', NULL, NOW(), 'password5', '567-890-1234', 'BLOCKED', 'STUDENT', 'user5'),
    ('123e4567-e89b-12d3-a456-426614174006', '67890123456', NOW(), 'user6@example.com', 'User Six', NULL, NOW(), 'password6', '678-901-2345', 'ACTIVE', 'STUDENT', 'user6');

INSERT INTO users_courses (id, course_id, user_id)
VALUES
    ('123e4567-e89b-12d3-a456-426614177896', 'f60a230e-7833-41d0-85e9-b4aa78a6b745', '123e4567-e89b-12d3-a456-426614174001'),
    ('123e4567-e89b-12d3-a456-426614177897', 'f60a230e-7833-41d0-85e9-b4aa78a6b745', '123e4567-e89b-12d3-a456-426614174004'),
    ('123e4567-e89b-12d3-a456-426614177898', 'f60a230e-7833-41d0-85e9-b4aa78a6b745', '123e4567-e89b-12d3-a456-426614174005'),
    ('123e4567-e89b-12d3-a456-426614177899', 'f60a230e-7833-41d0-85e9-b4aa78a6b745', '123e4567-e89b-12d3-a456-426614174006'),
    ('123e4567-e89b-12d3-a456-426614177890', 'f60a230e-7833-41d0-85e9-b4aa78a6b743', '123e4567-e89b-12d3-a456-426614174001');
