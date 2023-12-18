DELETE
FROM user_role;
DELETE
FROM restaurant;
DELETE
FROM dish;
DELETE
FROM users;

ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password, ID)
VALUES ('User', 'user@yandex.ru', '{noop}password', 100000),
       ('Admin', 'admin@gmail.com', '{noop}admin', 100001);

INSERT INTO user_role (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO restaurant (name, ID)
VALUES ('shashlikoff', 100002),
       ('mishiko', 100003),
       ('adj', 100004);

INSERT INTO dish (name, price, restaurant_id, ID)
VALUES ('яичница', 100, 100002, 100005),
       ('картофель фри', 250, 100002, 100006),
       ('бургер', 300, 100003, 100007),
       ('пюре с котлетой', 500, 100004, 100008);

INSERT INTO VOTE (user_id, restaurant_id, VOTED, ID)
VALUES (100000, 100002, '2023-12-01', 100009),
       (100001, 100002, '2023-12-01', 100010);

