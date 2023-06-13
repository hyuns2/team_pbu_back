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
INSERT INTO challenge (id, created_time, modified_time, deleted_at, end_date, image_path,
                       original_image_name, store_image_name, limited_number_of_times, main_title,
                       max_saving_amount, min_saving_amount, start_date, sub_title)
VALUES (1, '2023-06-13 09:33:01.569808', '2023-06-13 09:33:01.569808', NULL, '2023-06-30',
        'https://server.gasomann.com/resources/static/images/challenges/a6e032ec-be74-428d-8310-a07b04bb90a5.png',
        'a6e032ec-be74-428d-8310-a07b04bb90a5.png', '번개술값아끼기.png', 1, '번개 술 값 아끼기', 20000, 10000, '2023-06-13',
        '내일 아침에 후회하지 말고,'),
       (2, '2023-06-13 09:44:12.071086', '2023-06-13 09:44:12.071086', NULL, '2023-06-30',
        'https://server.gasomann.com/resources/static/images/challenges/65a569f0-e01d-4254-a7ab-1eeb8c6441b5.png',
        '65a569f0-e01d-4254-a7ab-1eeb8c6441b5.png', '배달팁 아끼기.png', 2, '배달팁 아끼기', 4000, 1000, '2023-06-14', '배달 말고 포장'),
       (3, '2023-06-13 09:47:38.305288', '2023-06-13 09:47:38.305288', NULL, '2023-06-30',
        'https://server.gasomann.com/resources/static/images/challenges/70486967-0dc1-45d7-9bbe-0393b6451be6.png',
        '70486967-0dc1-45d7-9bbe-0393b6451be6.png', '커피값 아끼기.png', 3, '커피값 아끼기', 6000, 1500, '2023-06-14',
        '그 커피, 꼭 마셔야해?'),
       (4, '2023-06-13 09:48:42.374387', '2023-06-13 09:48:42.374387', NULL, '2023-06-30',
        'https://server.gasomann.com/resources/static/images/challenges/cb2f8f64-9b06-4884-bd24-b31cfd487770.png',
        'cb2f8f64-9b06-4884-bd24-b31cfd487770.png', '택시비 아끼기.png', 2, '택시비 아끼기', 15000, 4000, '2023-06-14',
        '택시타면 만원, 버스타면 천원'),
       (5, '2023-06-13 09:49:22.643894', '2023-06-13 09:49:22.643894', NULL, '2023-06-30',
        'https://server.gasomann.com/resources/static/images/challenges/9fd0810a-60e6-4486-9245-e93fdd464850.png',
        '9fd0810a-60e6-4486-9245-e93fdd464850.png', '킥보드값 아끼기.png', 4, '전동 킥보드 값 아끼기', 2000, 1000, '2023-06-14',
        '그거 꼭 타야겠어요?'),
       (6, '2023-06-13 09:49:58.676972', '2023-06-13 09:49:58.676972', NULL, '2023-06-30',
        'https://server.gasomann.com/resources/static/images/challenges/f28da6f0-b916-455d-b0f3-ae20a894245a.png',
        'f28da6f0-b916-455d-b0f3-ae20a894245a.png', '오천원 아끼기.png', 1, '냅다 오천원 아끼기', 5000, 5000, '2023-06-14',
        '무지성 절약은 어때요?');


INSERT INTO challenge_description(challenge_id, description)
VALUES (1, '그 술자리 진짜 가고 싶어서 가는거 맞아요? 아니면 후회할텐데…'),
       (1, '내일의 숙취와 후회, 감당 가능한가요?'),
       (1, '간맥의 뜻이 간한테 엿맥이는건가요?'),
       (2, '배달말고 포장해서, 배달팁도 아끼고 건강도 챙기기'),
       (2, '칼로리 높은 배달음식도 먹을텐데, 조금 걸어두는편이 좋지 않겠어요?'),
       (3, '필요 없어도 습관적으로 커피를 소비하고 있지는 않나요?'),
       (3, '불필요한 커피 소비 줄여 돈도 아끼고 건강도 챙기기'),
       (3, '그거 물 마시면서도 할 수 있는데, 꼭 커피에 의존해야겠어요?'),
       (4, '어이구? 저번달에 택시비로 얼마를 쓴거에요?'),
       (4, '택시도 습관입니다.'),
       (4, '택시타면 만원, 버스타면 천원'),
       (4, '30분만 일찍 일어나면 버스 탈 수 있을텐데…'),
       (5, '하루 만 보도 안걷는데 킥보드 타면 불법입니다. (뇌절죄 단속대상)'),
       (5, '10분 산책은 좋아하면서, 10분 걷기 싫어서 킥보드를 탄다구요?'),
       (5, '길바닥에 돈 뿌리지 말고, 조금만 빨리 나와봅시다.'),
       (6, '참여하고 있는 챌린지 말고도, 어디든 낭비를 하고 있지 않겠어요?'),
       (6, '냅다 아끼고, 목돈 마련하는 것도 좋은 방법이에요!');


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
       (10, '2023-04-01 11:31:14.982873', '2023-04-01 11:31:14.982873', 2, 0, 1, null);


-- <Remittance> --
insert into remittance(id, created_time, modified_time, title, amount, participation_id, deleted_at)
values (1, '2023-04-11 11:31:14.982873', '2023-04-01 11:31:14.982873', '배달팁 아끼기', 6000, 1, null),
       (2, '2023-04-12 11:31:14.982873', '2023-04-01 11:31:14.982873', '배달팁 아끼기', 2000, 2, null),
       (3, '2023-04-13 11:31:14.982873', '2023-04-01 11:31:14.982873', '배달팁 아끼기', 3000, 3, null),
       (4, '2023-04-14 11:31:14.982873', '2023-04-01 11:31:14.982873', '배달팁 아끼기', 4000, 4, null),
       (5, '2023-04-15 11:31:14.982873', '2023-04-01 11:31:14.982873', '배달팁 아끼기', 5000, 5, null),
       (6, '2023-04-16 11:31:14.982873', '2023-04-01 11:31:14.982873', '배달팁 아끼기', 6000, 6, null),
       (7, '2023-05-01 11:31:14.982873', '2023-04-01 11:31:14.982873', '배달팁 아끼기', 7000, 7, null),
       (8, '2023-05-02 11:31:14.982873', '2023-04-01 11:31:14.982873', '배달팁 아끼기', 8000, 8, null),
       (9, '2023-05-03 11:31:14.982873', '2023-04-01 11:31:14.982873', '배달팁 아끼기', 9000, 9, null),
       (10, '2023-05-03 11:31:14.982873', '2023-04-01 11:31:14.982873', '택시비 아끼기', 10000, 10, null);

insert into inquiry(id, answer, content, time_stamp, title, user_id)
values (1, '답변입니다.', '문의 내용입니다.', '2023-04-01 11:31:14.982873', '문의 샘플1', 1),
       (2, null, '문의 내용입니다.', '2023-04-01 11:31:14.982873', '문의 샘플2', 1),
       (3, null, '문의 내용입니다.', '2023-04-01 11:31:14.982873', '문의 샘플3', 2),
       (4, '답변입니다.', '문의 내용입니다.', '2023-04-01 11:31:14.982873', '문의 샘플4', 3);

insert into notification(id, content, image_path, type, time_stamp, title)
values (1, '이벤트내용', 'https://server.gasomann.com/resources/static/images/cards/card1.png', 'EVENT',
        '2023-05-24 11:00:14.982873', '이벤트1'),
       (2, '이벤트내용', 'https://server.gasomann.com/resources/static/images/cards/card1.png', 'EVENT',
        '2023-05-24 11:00:14.982873', '이벤트2'),
       (3, '공지내용', 'https://server.gasomann.com/resources/static/images/cards/card1.png', 'NOTICE',
        '2023-05-24 11:00:14.982873', '공지사항1'),
       (4, '공지내용', 'https://server.gasomann.com/resources/static/images/cards/card1.png', 'NOTICE',
        '2023-05-24 11:00:14.982873', '공지사항2');

insert into card(d_type, id, type, title, sub_title, sentences, image_path)
values ('GeneralCondition', 1, 'GENERAL', '제목1', '부제목1', '명언1, 명언2',
        'https://server.gasomann.com/resources/static/images/cards/card1.png'),
       ('GeneralCondition', 2, 'GENERAL', '제목2', '부제목2', '명언1, 명언2',
        'https://server.gasomann.com/resources/static/images/cards/card1.png'),
       ('GeneralCondition', 3, 'GENERAL', '제목3', '부제목3', '명언1, 명언2',
        'https://server.gasomann.com/resources/static/images/cards/card1.png'),
       ('GeneralCondition', 4, 'GENERAL', '제목4', '부제목4', '명언1, 명언2',
        'https://server.gasomann.com/resources/static/images/cards/card1.png'),
       ('GeneralCondition', 5, 'GENERAL', '제목5', '부제목5', '명언1, 명언2',
        'https://server.gasomann.com/resources/static/images/cards/card1.png'),
       ('GeneralCondition', 6, 'GENERAL', '제목6', '부제목6', '명언1, 명언2',
        'https://server.gasomann.com/resources/static/images/cards/card1.png'),
       ('GeneralCondition', 7, 'GENERAL', '제목7', '부제목7', '명언1, 명언2',
        'https://server.gasomann.com/resources/static/images/cards/card1.png'),
       ('GeneralCondition', 8, 'GENERAL', '제목8', '부제목8', '명언1, 명언2',
        'https://server.gasomann.com/resources/static/images/cards/card1.png'),
       ('GeneralCondition', 9, 'GENERAL', '제목9', '부제목9', '명언1, 명언2',
        'https://server.gasomann.com/resources/static/images/cards/card1.png'),
       ('RemittanceCondition', 10, 'REMITTANCE', '제목10', '부제목10', '명언1, 명언2',
        'https://server.gasomann.com/resources/static/images/cards/card1.png'),
       ('RemittanceCondition', 11, 'REMITTANCE', '제목11', '부제목11', '명언1, 명언2',
        'https://server.gasomann.com/resources/static/images/cards/card1.png'),
       ('CouponCondition', 12, 'COUPON', '제목12', '부제목12', '명언1, 명언2',
        'https://server.gasomann.com/resources/static/images/cards/card1.png');

insert into remittance_card(id, charge, count, term)
values (10, 5000, 0, 0),
       (11, 5000, 5, 0);

insert into coupon_card(id, how_successive, what_number)
values (12, 0, 1);

insert into user_card(id, date, card_id, user_id, is_new)
values (1, '2023-05-18', 1, 2, 0),
       (2, '2023-05-18', 2, 2, 1),
       (3, '2023-05-18', 3, 2, 0),
       (4, '2023-05-18', 4, 1, 1),
       (5, '2023-05-18', 5, 1, 0),
       (6, '2023-05-18', 7, 1, 1),
       (7, '2023-05-18', 9, 1, 0),
       (8, '2023-05-18', 11, 1, 1),
       (9, '2023-05-18', 12, 1, 1);


-- <Worthy Consumption> --
insert into worthy_consumption_condition(id, check_condition_type,
                                         convention_end_date, convention_start_date,
                                         max_issuance)
values (1, 'AVAILABLE', '2023-07-01', '2023-04-01', 5),
       (2, 'AVAILABLE', '2023-07-01', '2023-04-01', 100),
       (3, 'AVAILABLE', '2023-07-01', '2023-04-01', 100);

insert into worthy_consumption_url(id, logo_path, video_thumb_nail_path, detail_background_image_path,
                                   detail_image_path, image_path,
                                   place_image_path, video_path)
values (1, 'https://server.gasomann.com/resources/static/images/values/valueLogo1.png',
        'https://server.gasomann.com/resources/static/images/values/valueVideoThumbNail1.png',
        'https://server.gasomann.com/resources/static/images/values/valueDetailBackground1.png',
        'https://server.gasomann.com/resources/static/images/values/valueDetail1.png',
        'https://server.gasomann.com/resources/static/images/values/valueBackground1.png',
        'https://server.gasomann.com/resources/static/images/values/valueDetailMap1.png',
        'https://server.gasomann.com/resources/static/videos/values/valuevideo1.mp4'),
       (2, 'https://server.gasomann.com/resources/static/images/values/valueLogo1.png',
        'https://server.gasomann.com/resources/static/images/values/valueVideoThumbNail1.png',
        'https://server.gasomann.com/resources/static/images/values/valueDetailBackground1.png',
        'https://server.gasomann.com/resources/static/images/values/valueDetail1.png',
        'https://server.gasomann.com/resources/static/images/values/valueBackground1.png',
        'https://server.gasomann.com/resources/static/images/values/valueDetailMap1.png',
        'https://server.gasomann.com/resources/static/videos/values/valuevideo1.mp4'),
       (3, 'https://server.gasomann.com/resources/static/images/values/valueLogo1.png',
        'https://server.gasomann.com/resources/static/images/values/valueVideoThumbNail1.png',
        'https://server.gasomann.com/resources/static/images/values/valueDetailBackground1.png',
        'https://server.gasomann.com/resources/static/images/values/valueDetail1.png',
        'https://server.gasomann.com/resources/static/images/values/valueBackground1.png',
        'https://server.gasomann.com/resources/static/images/values/valueDetailMap1.png',
        'https://server.gasomann.com/resources/static/videos/values/valuevideo1.mp4');

insert into worthy_consumption(id, created_time, modified_time, available_place, available_place_detail,
                               original_price, sale_price, title, condition_id, worthy_consumption_url_id)
values (1, '2023-04-01 11:31:14.982873', '2023-04-01 11:31:14.982873', '대구 전 지역', '상세지역', 11000, 1000,
        '레드 짐', 1, 1),
       (2, '2023-04-01 11:31:14.982873', '2023-04-01 11:31:14.982873', '대구 전 지역', '상세지역', 22000, 2000,
        '옐로우 짐', 2, 2),
       (3, '2023-04-01 11:31:14.982873', '2023-04-01 11:31:14.982873', '대구 전 지역', '상세지역', 33000, 3000,
        '블루 짐', 3, 3);

insert into worthy_consumption_hashtags(worthy_consumption_id, worthy_consumption_hashtags)
values (1, 'hashtags1'),
       (1, 'hashtags1'),
       (1, 'hashtags1'),
       (2, 'hashtags2'),
       (2, 'hashtags2'),
       (2, 'hashtags2'),
       (3, 'hashtags3'),
       (3, 'hashtags3'),
       (3, 'hashtags3');



insert into recommendation_reason(id, title, description, worthy_consumption_id)
values (1, 'title1', 'description1', 1),
       (2, 'title2', 'description2', 1),
       (3, 'title3', 'description3', 1),
       (4, 'title1', 'description1', 2),
       (5, 'title2', 'description2', 2),
       (6, 'title3', 'description3', 2),
       (7, 'title1', 'description1', 3),
       (8, 'title2', 'description2', 3),
       (9, 'title3', 'description3', 3);
-- <Shorts> --

insert into shorts(id, category, uploader_name, content, image_path, video_path)
values (1, 'SAVING', '이름1', '내용1', 'https://server.gasomann.com/resources/static/images/shorts/shortImage1.png',
        'https://server.gasomann.com/resources/static/videos/shorts/shortVideo1.mp4'),
       (2, 'SAVING', '이름2', '내용2', 'https://server.gasomann.com/resources/static/images/shorts/shortImage2.png',
        'https://server.gasomann.com/resources/static/videos/shorts/shortVideo2.mp4'),
       (3, 'EDUCATION', '이름3', '내용3', 'https://server.gasomann.com/resources/static/images/shorts/shortImage3.png',
        'https://server.gasomann.com/resources/static/videos/shorts/shortVideo3.mp4');

-- <Coupon> --
insert into coupon(id, created_time, modified_time, image_path, limit_end_date, limit_start_date, pin, title,
                   worthy_consumption_id, issuable_start_date, issuable_end_date, coupon_type, issue_count_limit,
                   how_to_use, price_tag, available_price)
values (1, '2023-04-11 11:31:14.982873', '2023-04-01 11:31:14.982873',
        'https://server.gasomann.com/resources/static/images/coupons/coupon1.png',
        '2023-05-31', '2023-05-01', '123456', '레드짐 5월 할인권', 1, '2023-05-01', '2023-05-31', 'EVENT', true, '쓰는 방법',
        '1달 이용권', 10000),
       (2, '2023-04-11 11:31:14.982873', '2023-04-01 11:31:14.982873',
        'https://server.gasomann.com/resources/static/images/coupons/coupon1.png',
        '2023-06-30', '2023-06-01', '123456', '레드짐 6월 할인권', 1, '2023-06-01', '2023-06-30', 'EVENT', true, '쓰는 방법',
        '1달 이용권', 10000),
       (3, '2023-04-11 11:31:14.982873', '2023-04-01 11:31:14.982873',
        'https://server.gasomann.com/resources/static/images/coupons/coupon1.png',
        '2023-07-31', '2023-07-01', '123456', '레드짐 3월 할인권', 1, '2023-03-01', '2023-03-31', 'EVENT', true, '쓰는 방법',
        '1달 이용권', 10000),
       (4, '2023-04-11 11:31:14.982873', '2023-04-01 11:31:14.982873',
        'https://server.gasomann.com/resources/static/images/coupons/coupon1.png',
        '2023-08-31', '2023-08-01', '123456', '레드짐 4월 할인권', 1, '2023-04-01', '2023-04-30', 'EVENT', true, '쓰는 방법',
        '1달 이용권', 10000),
       (5, '2023-04-11 11:31:14.982873', '2023-04-01 11:31:14.982873',
        'https://server.gasomann.com/resources/static/images/coupons/coupon1.png',
        '2023-05-31', '2023-05-01', '123456', '옐로우 짐 5월 할인권', 2, '2023-05-01', '2023-05-31', 'EVENT', false, '쓰는 방법',
        '1달 이용권', 10000),
       (6, '2023-04-11 11:31:14.982873', '2023-04-01 11:31:14.982873',
        'https://server.gasomann.com/resources/static/images/coupons/coupon1.png',
        '2023-06-30', '2023-06-01', '123456', '옐로우 짐 6월 할인권', 2, '2023-06-01', '2023-06-30', 'EVENT', false, '쓰는 방법',
        '1달 이용권', 10000),
       (7, '2023-04-11 11:31:14.982873', '2023-04-01 11:31:14.982873',
        'https://server.gasomann.com/resources/static/images/coupons/coupon1.png',
        '2023-07-31', '2023-07-01', '123456', '옐로우 짐 7월 할인권', 2, '2023-07-01', '2023-07-31', 'ACHIEVEMENT_CONDITION',
        false, '쓰는 방법', '1달 이용권', 10000),
       (8, '2023-04-11 11:31:14.982873', '2023-04-01 11:31:14.982873',
        'https://server.gasomann.com/resources/static/images/coupons/coupon1.png',
        '2023-08-31', '2023-08-01', '123456', '옐로우 짐 8월 할인권', 2, '2023-08-01', '2023-08-31', 'ACHIEVEMENT_CONDITION',
        false, '쓰는 방법', '1달 이용권', 10000),
       (9, '2023-04-11 11:31:14.982873', '2023-04-01 11:31:14.982873',
        'https://server.gasomann.com/resources/static/images/coupons/coupon1.png',
        '2023-05-31', '2023-05-01', '123456', '블루짐 5월 할인권', 3, '2023-05-01', '2023-05-31', 'ACHIEVEMENT_CONDITION',
        false, '쓰는 방법', '1달 이용권', 10000),
       (10, '2023-04-11 11:31:14.982873', '2023-04-01 11:31:14.982873',
        'https://server.gasomann.com/resources/static/images/coupons/coupon1.png',
        '2023-06-30', '2023-06-01', '123456', '블루짐 6월 할인권', 3, '2023-06-01', '2023-06-30', 'ACHIEVEMENT_CONDITION',
        false, '쓰는 방법', '1달 이용권', 10000),
       (11, '2023-04-11 11:31:14.982873', '2023-04-01 11:31:14.982873',
        'https://server.gasomann.com/resources/static/images/coupons/coupon1.png',
        '2023-07-31', '2023-07-01', '123456', '블루짐 7월 할인권', 3, '2023-07-01', '2023-07-31', 'ACHIEVEMENT_CONDITION',
        true, '쓰는 방법', '1달 이용권', 10000),
       (12, '2023-04-11 11:31:14.982873', '2023-04-01 11:31:14.982873',
        'https://server.gasomann.com/resources/static/images/coupons/coupon1.png',
        '2023-08-31', '2023-08-01', '123456', '블루짐 8월 할인권', 3, '2023-08-01', '2023-08-31', 'ACHIEVEMENT_CONDITION',
        true, '쓰는 방법', '1달 이용권', 10000);

insert into coupon_summary(coupon_id, coupon_summary)
values (1, 'summary1'),
       (1, 'summary1'),
       (1, 'summary1'),
       (2, 'summary2'),
       (2, 'summary2'),
       (2, 'summary2'),
       (3, 'summary3'),
       (3, 'summary3'),
       (3, 'summary3'),
       (4, 'summary4'),
       (4, 'summary4'),
       (4, 'summary4'),
       (5, 'summary5'),
       (5, 'summary5'),
       (5, 'summary5'),
       (6, 'summary6'),
       (6, 'summary6'),
       (6, 'summary6'),
       (7, 'summary7'),
       (7, 'summary7'),
       (7, 'summary7'),
       (8, 'summary8'),
       (8, 'summary8'),
       (8, 'summary8'),
       (9, 'summary9'),
       (9, 'summary9'),
       (9, 'summary9'),
       (10, 'summary10'),
       (10, 'summary10'),
       (10, 'summary10'),
       (11, 'summary11'),
       (11, 'summary11'),
       (11, 'summary11'),
       (12, 'summary12'),
       (12, 'summary12'),
       (12, 'summary12');

insert into coupon_caution(coupon_id, coupon_caution)
values (1, 'caution1'),
       (1, 'caution1'),
       (1, 'caution1'),
       (2, 'caution2'),
       (2, 'caution2'),
       (2, 'caution2'),
       (3, 'caution3'),
       (3, 'caution3'),
       (3, 'caution3'),
       (4, 'caution4'),
       (4, 'caution4'),
       (4, 'caution4'),
       (5, 'caution5'),
       (5, 'caution5'),
       (5, 'caution5'),
       (6, 'caution6'),
       (6, 'caution6'),
       (6, 'caution6'),
       (7, 'caution7'),
       (7, 'caution7'),
       (7, 'caution7'),
       (8, 'caution8'),
       (8, 'caution8'),
       (8, 'caution8'),
       (9, 'caution9'),
       (9, 'caution9'),
       (9, 'caution9'),
       (10, 'caution10'),
       (10, 'caution10'),
       (10, 'caution10'),
       (11, 'caution11'),
       (11, 'caution11'),
       (11, 'caution11'),
       (12, 'caution12'),
       (12, 'caution12'),
       (12, 'caution12');

insert into coupon_issuance(id, created_time, modified_time, created, used, coupon_id, user_id)
values (1, '2023-03-08 11:31:14.982873', '2023-03-08 11:31:14.982873', 1, 0, 1, 1),
       (2, '2023-04-08 11:31:14.982873', '2023-04-08 11:31:14.982873', 1, 0, 2, 2),
       (3, '2023-04-08 11:31:14.982873', '2023-04-08 11:31:14.982873', 1, 0, 3, 1),
       (4, '2023-05-08 11:31:14.982873', '2023-05-08 11:31:14.982873', 1, 0, 2, 1);

-- <Likes> --
insert into likes_shorts(id, created_time, modified_time, likes_category, shorts_id, user_id)
values (1, '2023-03-08 11:31:14.982873', '2023-03-08 11:31:14.982873', 'SHORTS_SAVING', 1, 1),
       (2, '2023-03-08 11:31:14.982873', '2023-03-08 11:31:14.982873', 'SHORTS_EDU', 3, 1),
       (3, '2023-03-08 11:31:14.982873', '2023-03-08 11:31:14.982873', 'SHORTS_SAVING', 2, 1);

insert into likes_worthy_consumption(id, created_time, modified_time, likes_category, user_id, worthy_consumption_id)
values (1, '2023-03-08 11:31:14.982873', '2023-03-08 11:31:14.982873', 'WORTHY_CONSUMPTION', 1, 1),
       (2, '2023-03-08 11:31:14.982873', '2023-03-08 11:31:14.982873', 'WORTHY_CONSUMPTION', 1, 2),
       (3, '2023-03-08 11:31:14.982873', '2023-03-08 11:31:14.982873', 'WORTHY_CONSUMPTION', 1, 3);
