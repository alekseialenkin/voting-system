DELETE
FROM user_role;
DELETE
FROM restaurant;
DELETE
FROM dish;
DELETE
FROM users;

ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin');

INSERT INTO user_role (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO restaurant (name)
VALUES ('shashlikoff'),
       ('mishiko'),
       ('adj');

INSERT INTO dish (name, price, restaurant_id)
VALUES ('яичница', 100, 100002),
       ('картофель фри', 250, 100002),
       ('бургер', 300, 100003),
       ('пюре с котлетой', 500, 100004);

INSERT INTO VOTE (user_id, restaurant_id, VOTED)
VALUES (100000, 100002, '2023-12-01 10:00:00'),
       (100001, 100002, '2023-12-01 10:00:00');

