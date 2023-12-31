
-- <IdentityVerification> --
insert into identity_verification (id, birth_date, code, mobile, name, created_time, modified_time) -- 비밀번호 모두 123456으로 동일 --
values (1, '19990101', 'abcxxxxxabc', '01012345671', '김철수', '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873'),
       (2, '19990101', 'defxxxxxdef', '01012345672', '홍길동', '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873'),
       (3, '19990101', 'ghixxxxxghi', '01099998883', '박지원', '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873'),
       (4, '19990101', 'jklxxxxxjkl', '01012345674', '최현수', '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873'),
       (5, '19990101', 'mnoxxxxxmno', '01012345675', '이민우', '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873'),
       (6, '19990101', 'pqrxxxxxpqr', '01012345676', '정형식', '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873'),
       (7, '19990101', 'stuxxxxxstu', '01011112227', '이재우', '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873'),
       (8, '19990101', 'vwxxxxxxvwx', '01012345678', '배주현', '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873'),
       (9, '19990101', 'yzaxxxxxyza', '01012345679', '김다은', '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873');


-- <User> --
insert into user (id, created_time, modified_time, agreement, email, nickname, password, store_image_name,
                  original_image_name, image_path,
                  identity_verification_id, deleted_at)
values (1, '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873', true, 'test01@naver.com', '철수',
        '{bcrypt}$2a$10$yQZvUKRebIw8NcO8bRsuiewJib4zZQ5Pi.GTbodIjDHK5h3icbzrO', 'default_user_profile.png',
        'default_user_profile.png',
        'https://server.gasomann.com/resources/static/images/profiles/default_user_profile.png', 1, null),
       (2, '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873', true, 'test02@naver.com', '길동',
        '{bcrypt}$2a$10$yQZvUKRebIw8NcO8bRsuiewJib4zZQ5Pi.GTbodIjDHK5h3icbzrO', 'default_user_profile.png',
        'default_user_profile.png',
        'https://server.gasomann.com/resources/static/images/profiles/default_user_profile.png', 2, null),
       (3, '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873', true, 'test03@naver.com', '지원',
        '{bcrypt}$2a$10$yQZvUKRebIw8NcO8bRsuiewJib4zZQ5Pi.GTbodIjDHK5h3icbzrO', 'default_user_profile.png',
        'default_user_profile.png',
        'https://server.gasomann.com/resources/static/images/profiles/default_user_profile.png', 3, null),
       (4, '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873', true, 'test04@naver.com', '현수',
        '{bcrypt}$2a$10$yQZvUKRebIw8NcO8bRsuiewJib4zZQ5Pi.GTbodIjDHK5h3icbzrO', 'default_user_profile.png',
        'default_user_profile.png',
        'https://server.gasomann.com/resources/static/images/profiles/default_user_profile.png', 4, null),
       (5, '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873', true, 'test05@naver.com', '민우',
        '{bcrypt}$2a$10$yQZvUKRebIw8NcO8bRsuiewJib4zZQ5Pi.GTbodIjDHK5h3icbzrO', 'default_user_profile.png',
        'default_user_profile.png',
        'https://server.gasomann.com/resources/static/images/profiles/default_user_profile.png', 5, null),
       (6, '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873', true, 'test06@naver.com', '형식',
        '{bcrypt}$2a$10$yQZvUKRebIw8NcO8bRsuiewJib4zZQ5Pi.GTbodIjDHK5h3icbzrO', 'default_user_profile.png',
        'default_user_profile.png',
        'https://server.gasomann.com/resources/static/images/profiles/default_user_profile.png', 6, null),
       (7, '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873', true, 'test07@naver.com', '재우',
        '{bcrypt}$2a$10$yQZvUKRebIw8NcO8bRsuiewJib4zZQ5Pi.GTbodIjDHK5h3icbzrO', 'default_user_profile.png',
        'default_user_profile.png',
        'https://server.gasomann.com/resources/static/images/profiles/default_user_profile.png', 7, null),
       (8, '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873', true, 'test08@naver.com', '주현',
        '{bcrypt}$2a$10$yQZvUKRebIw8NcO8bRsuiewJib4zZQ5Pi.GTbodIjDHK5h3icbzrO', 'default_user_profile.png',
        'default_user_profile.png',
        'https://server.gasomann.com/resources/static/images/profiles/default_user_profile.png', 8, null),
       (9, '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873', true, 'test09@naver.com', '다은',
        '{bcrypt}$2a$10$yQZvUKRebIw8NcO8bRsuiewJib4zZQ5Pi.GTbodIjDHK5h3icbzrO', 'default_user_profile.png',
        'default_user_profile.png',
        'https://server.gasomann.com/resources/static/images/profiles/default_user_profile.png', 9, null);


insert into user_roles (user_id, roles)
values (1, 'ROLE_USER'),
       (1, 'ROLE_ADMIN'),
       (2, 'ROLE_USER'),
       (3, 'ROLE_USER'),
       (4, 'ROLE_USER'),
       (5, 'ROLE_USER'),
       (6, 'ROLE_USER'),
       (7, 'ROLE_USER'),
       (8, 'ROLE_USER'),
       (9, 'ROLE_USER');


-- <Account> --
insert into account(id, account_numbers, bank_type, user_id)
values (1, '123456781', 'KDB', 1),
       (2, '123456782', 'IBK', 2),
       (3, '123456783', 'KB', 3),
       (4, '123456784', 'KDB', 4),
       (5, '123456785', 'SUHYUP', 5),
       (6, '123456786', 'NONGHYUP', 6),
       (7, '123456787', 'WOORI', 7),
       (8, '123456788', 'SC', 8),
       (9, '123456789', 'CITY', 9);


insert into account_connection_map(account_id, connection_code, bank_type)
values (1, 'abcdea', 'CODEF'),
       (2, 'abcdeb', 'CODEF'),
       (3, 'abcdec', 'CODEF'),
       (4, 'abcded', 'CODEF'),
       (5, 'abcdee', 'CODEF'),
       (6, 'abcdef', 'CODEF'),
       (7, 'abcdeg', 'CODEF'),
       (8, 'abcdeh', 'CODEF'),
       (9, 'abcdei', 'CODEF');


-- <Challenge> --
insert into challenge(id, created_time, modified_time, end_date, store_image_name,
                      original_image_name, image_path, main_title,
                      limited_number_of_times,
                      max_saving_amount, min_saving_amount, start_date, sub_title, deleted_at)
values (1, '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873', '2024-07-13', 'challenge1.png', 'challenge1.png',
        'https://server.gasomann.com/resources/static/images/challenges/challenge1.png', '배달팁 아끼기', 2, 4000, 1000,
        '2023-03-13', '배달말고 포장, 배달팁 아끼기!', null),
       (2, '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873', '2024-07-13', 'challenge2.png', 'challenge2.png',
        'https://server.gasomann.com/resources/static/images/challenges/challenge2.png', '택시비 아끼기', 3, 6000, 1000,
        '2023-03-13', '걸어서 갈 수 있잖아?', null),
       (3, '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873', '2024-07-13', 'challenge3.png', 'challenge3.png',
        'https://server.gasomann.com/resources/static/images/challenges/challenge3.png', '커피값 아끼기', 3, 5000, 1000,
        '2023-03-13', '그 커피 꼭 사야 해?', null);



insert into challenge_description(challenge_id, description)
values (1, '배달말고 포장해서, 배달팁도 아끼고 건강도 챙기기!'),
       (1, '칼로리 높은 배달음식도 먹을텐데, 조금 걸어두는 편이 좋지 않겠어요?'),
       (2, '배달말고 포장해서, 배달팁도 아끼고 건강도 챙기기!'),
       (2, '칼로리 높은 배달음식도 먹을텐데, 조금 걸어두는 편이 좋지 않겠어요?'),
       (3, '배달말고 포장해서, 배달팁도 아끼고 건강도 챙기기!'),
       (3, '칼로리 높은 배달음식도 먹을텐데, 조금 걸어두는 편이 좋지 않겠어요?');





-- <Participation> --
insert into participation(id, created_time, modified_time, challenge_id, saving_count_of_day, user_id, deleted_at)
values (1, '2023-04-01 11:31:14.982873', '2023-04-01 11:31:14.982873', 1, 0, 1, null),
       (2, '2023-04-01 11:31:14.982873', '2023-04-01 11:31:14.982873', 1, 0, 2, null),
       (3, '2023-04-01 11:31:14.982873', '2023-04-01 11:31:14.982873', 1, 0, 3, null),
       (4, '2023-04-01 11:31:14.982873', '2023-04-01 11:31:14.982873', 1, 0, 4, null),
       (5, '2023-04-01 11:31:14.982873', '2023-04-01 11:31:14.982873', 1, 0, 5, null),
       (6, '2023-04-01 11:31:14.982873', '2023-04-01 11:31:14.982873', 1, 0, 6, null),
       (7, '2023-04-01 11:31:14.982873', '2023-04-01 11:31:14.982873', 1, 0, 7, null),
       (8, '2023-04-01 11:31:14.982873', '2023-04-01 11:31:14.982873', 1, 0, 8, null),
       (9, '2023-04-01 11:31:14.982873', '2023-04-01 11:31:14.982873', 1, 0, 9, null),
       (10, '2023-04-01 11:31:14.982873', '2023-04-01 11:31:14.982873', 2, 0, 1, null),
       (11, '2023-04-01 11:31:14.982873', '2023-04-01 11:31:14.982873', 3, 0, 1, null);



-- <Remittance> --
insert into remittance(id, created_time, modified_time, title, amount, participation_id, deleted_at)
values (1, '2023-04-11 11:31:14.982873', '2023-04-01 11:31:14.982873', '배달팁 아끼기', 4000, 1, null),
       (2, '2023-04-12 11:31:14.982873', '2023-04-01 11:31:14.982873', '배달팁 아끼기', 2000, 2, null),
       (3, '2023-04-13 11:31:14.982873', '2023-04-01 11:31:14.982873', '배달팁 아끼기', 3000, 3, null),
       (4, '2023-04-14 11:31:14.982873', '2023-04-01 11:31:14.982873', '배달팁 아끼기', 4000, 4, null),
       (5, '2023-04-15 11:31:14.982873', '2023-04-01 11:31:14.982873', '배달팁 아끼기', 5000, 5, null),
       (6, '2023-04-16 11:31:14.982873', '2023-04-01 11:31:14.982873', '배달팁 아끼기', 6000, 6, null),
       (7, '2023-05-01 11:31:14.982873', '2023-04-01 11:31:14.982873', '배달팁 아끼기', 7000, 7, null),
       (8, '2023-05-02 11:31:14.982873', '2023-04-01 11:31:14.982873', '배달팁 아끼기', 8000, 8, null),
       (9, '2023-05-03 11:31:14.982873', '2023-04-01 11:31:14.982873', '배달팁 아끼기', 9000, 9, null),
       (10, '2023-05-03 11:31:14.982873', '2023-04-01 11:31:14.982873', '배달팁 아끼기', 2000, 1, null),
       (11, '2023-05-03 11:31:14.982873', '2023-04-01 11:31:14.982873', '택시비 아끼기', 10000, 10, null);
