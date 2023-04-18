-- <IdentityVerification> --
insert into identity_verification (id, birth_date, code, mobile, name)
values (1, '19990101', 'abcdefghijklmnopqrstuvwxyz', '01012345678', 'user01'),
       (2, '19990101', 'sadfsadfsadfsadfsdaf', '01012345678', 'user02'),
       (3, '19990101', 'sadfsadfsadfsadfsdaf', '01012345678', 'user03'),
       (4, '19990101', 'sadfsadfsadfsadfsdaf', '01012345678', 'user04'),
       (5, '19990101', 'sadfsadfsadfsadfsdaf', '01012345678', 'user05'),
       (6, '19990101', 'sadfsadfsadfsadfsdaf', '01012345678', 'user06'),
       (7, '19990101', 'sadfsadfsadfsadfsdaf', '01012345678', 'user07'),
       (8, '19990101', 'sadfsadfsadfsadfsdaf', '01012345678', 'user08'),
       (9, '19990101', 'sadfsadfsadfsadfsdaf', '01012345678', 'user09');

-- <Account> --
insert into account(id, account_numbers, bank_type)
values (1, '123456789', 'KDB'),
       (2, '123456789', 'KDB'),
       (3, '123456789', 'KDB'),
       (4, '123456789', 'KDB'),
       (5, '123456789', 'KDB'),
       (6, '123456789', 'KDB'),
       (7, '123456789', 'KDB'),
       (8, '123456789', 'KDB'),
       (9, '123456789', 'KDB');



insert into account_connection_map(account_id, connection_map, connection_map_key)
values (1, 'abcdef', 'CODEF'),
       (2, 'abcdef', 'CODEF'),
       (3, 'abcdef', 'CODEF'),
       (4, 'abcdef', 'CODEF'),
       (5, 'abcdef', 'CODEF'),
       (6, 'abcdef', 'CODEF'),
       (7, 'abcdef', 'CODEF'),
       (8, 'abcdef', 'CODEF'),
       (9, 'abcdef', 'CODEF');

-- <User> --
insert into user (id, created_time, modified_time, agreement, email, nickname, password, profile_image,
                  identity_verification_id, account_id)
values (1, '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873', true, 'hansol8701@naver.com', 'user01',
        '{bcrypt}$2a$10$9WYLiZLDXSkF15LFXnjppOKS/Ae6SGkWtHZRZte3IN0TfByYufxmK', 'image-url', 1, 1),
       (2, '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873', true, 'hansol8701@naver.com', 'user02',
        '{bcrypt}$2a$10$9WYLiZLDXSkF15LFXnjppOKS/Ae6SGkWtHZRZte3IN0TfByYufxmK', 'image-url', 2, 2),
       (3, '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873', true, 'hansol8701@naver.com', 'user03',
        '{bcrypt}$2a$10$9WYLiZLDXSkF15LFXnjppOKS/Ae6SGkWtHZRZte3IN0TfByYufxmK', 'image-url', 3, 3),
       (4, '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873', true, 'hansol8701@naver.com', 'user04',
        '{bcrypt}$2a$10$9WYLiZLDXSkF15LFXnjppOKS/Ae6SGkWtHZRZte3IN0TfByYufxmK', 'image-url', 4, 4),
       (5, '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873', true, 'hansol8701@naver.com', 'user05',
        '{bcrypt}$2a$10$9WYLiZLDXSkF15LFXnjppOKS/Ae6SGkWtHZRZte3IN0TfByYufxmK', 'image-url', 5, 5),
       (6, '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873', true, 'hansol8701@naver.com', 'user06',
        '{bcrypt}$2a$10$9WYLiZLDXSkF15LFXnjppOKS/Ae6SGkWtHZRZte3IN0TfByYufxmK', 'image-url', 6, 6),
       (7, '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873', true, 'hansol8701@naver.com', 'user07',
        '{bcrypt}$2a$10$9WYLiZLDXSkF15LFXnjppOKS/Ae6SGkWtHZRZte3IN0TfByYufxmK', 'image-url', 7, 7),
       (8, '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873', true, 'hansol8701@naver.com', 'user08',
        '{bcrypt}$2a$10$9WYLiZLDXSkF15LFXnjppOKS/Ae6SGkWtHZRZte3IN0TfByYufxmK', 'image-url', 8, 8),
       (9, '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873', true, 'hansol8701@naver.com', 'user09',
        '{bcrypt}$2a$10$9WYLiZLDXSkF15LFXnjppOKS/Ae6SGkWtHZRZte3IN0TfByYufxmK', 'image-url', 9, 9);


insert into user_roles (user_id, roles)
values (1, 'ROLE_USER'),
       (2, 'ROLE_USER'),
       (3, 'ROLE_USER'),
       (4, 'ROLE_USER'),
       (5, 'ROLE_USER'),
       (6, 'ROLE_USER'),
       (7, 'ROLE_USER'),
       (8, 'ROLE_USER'),
       (9, 'ROLE_USER');


-- <Challenge> --
insert into challenge(id, created_time, modified_time, end_date, image_url, main_title, remittance_available_count,
                      remittance_once_limit, start_date, sub_title)
values (1, '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873', '2024-02-13', 'image', '샘플 챌린지1', 5, 0,
        '2023-02-13', 'sample1'),
       (2, '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873', '2024-02-13', 'image', '샘플 챌린지2', 5, 0,
        '2023-02-13', 'sample2');


insert into challenge_description(challenge_id, description)
values (1, 'description'),
       (2, 'description');


-- <SavingCount> --
insert into saving_count(id, count)
values (1, 5),
       (2, 5),
       (3, 5),
       (4, 5),
       (5, 5),
       (6, 5),
       (7, 5),
       (8, 5),
       (9, 5),
       (10, 5);


-- <Participation> --
insert into participation(id, created_time, modified_time, challenge_id, saving_count_id, user_id)
values (1, '2023-04-01 11:31:14.982873', '2023-04-01 11:31:14.982873', 1, 1, 1),
       (2, '2023-04-01 11:31:14.982873', '2023-04-01 11:31:14.982873', 1, 2, 2),
       (3, '2023-04-01 11:31:14.982873', '2023-04-01 11:31:14.982873', 1, 3, 3),
       (4, '2023-04-01 11:31:14.982873', '2023-04-01 11:31:14.982873', 1, 4, 4),
       (5, '2023-04-01 11:31:14.982873', '2023-04-01 11:31:14.982873', 1, 5, 5),
       (6, '2023-04-01 11:31:14.982873', '2023-04-01 11:31:14.982873', 1, 6, 6),
       (7, '2023-04-01 11:31:14.982873', '2023-04-01 11:31:14.982873', 1, 7, 7),
       (8, '2023-04-01 11:31:14.982873', '2023-04-01 11:31:14.982873', 1, 8, 8),
       (9, '2023-04-01 11:31:14.982873', '2023-04-01 11:31:14.982873', 1, 9, 9),
       (10, '2023-04-01 11:31:14.982873', '2023-04-01 11:31:14.982873', 2, 10, 1);


-- <Remittance> --
insert into remittance(id, created_time, modified_time, title, amount, participation_id)
values (1, '2023-04-01 11:31:14.982873', '2023-04-01 11:31:14.982873', '샘플 챌린지1', 1000, 1),
       (2, '2023-04-01 11:31:14.982873', '2023-04-01 11:31:14.982873', '샘플 챌린지1', 2000, 2),
       (3, '2023-04-01 11:31:14.982873', '2023-04-01 11:31:14.982873', '샘플 챌린지1', 3000, 3),
       (4, '2023-04-01 11:31:14.982873', '2023-04-01 11:31:14.982873', '샘플 챌린지1', 4000, 4),
       (5, '2023-04-01 11:31:14.982873', '2023-04-01 11:31:14.982873', '샘플 챌린지1', 5000, 5),
       (6, '2023-04-01 11:31:14.982873', '2023-04-01 11:31:14.982873', '샘플 챌린지1', 6000, 6),
       (7, '2023-04-01 11:31:14.982873', '2023-04-01 11:31:14.982873', '샘플 챌린지1', 7000, 7),
       (8, '2023-04-01 11:31:14.982873', '2023-04-01 11:31:14.982873', '샘플 챌린지1', 8000, 8),
       (9, '2023-04-01 11:31:14.982873', '2023-04-01 11:31:14.982873', '샘플 챌린지1', 9000, 9),
       (10, '2023-04-01 11:31:14.982873', '2023-04-01 11:31:14.982873', '샘플 챌린지1', 2000, 1),
       (11, '2023-04-01 11:31:14.982873', '2023-04-01 11:31:14.982873', '샘플 챌린지1', 3000, 1),
       (12, '2023-04-01 11:31:14.982873', '2023-04-01 11:31:14.982873', '샘플 챌린지1', 10000, 10);




