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
insert into worthy_consumption_condition(id, check_condition_type, convention_end_date, convention_start_date, max_issuance)
values (1, 'AVAILABLE', '2023-12-31', '2023-06-01', 1),
       (2, 'AVAILABLE',  '2023-01-31', '2023-06-01', 3),
       (3, 'AVAILABLE', '2023-01-31', '2023-06-01', 1000);

insert into worthy_consumption_url(id, logo_path, video_thumb_nail_path, detail_background_image_path, detail_image_path, image_path, place_image_path, video_path)
values (1, 'https://server.gasomann.com/resources/static/images/values/valueLogo1.png',        'https://server.gasomann.com/resources/static/images/values/valueVideoThumbNail1.png',        'https://server.gasomann.com/resources/static/images/values/valueDetailBackground1.png',        'https://server.gasomann.com/resources/static/images/values/valueDetail1.png',        'https://server.gasomann.com/resources/static/images/values/valueBackground1.png',        'https://server.gasomann.com/resources/static/images/values/valueDetailMap1.png', 'https://server.gasomann.com/resources/static/videos/values/valuevideo1.mp4'),
       (2, 'https://server.gasomann.com/resources/static/images/values/valueLogo1.png',        'https://server.gasomann.com/resources/static/images/values/valueVideoThumbNail1.png',        'https://server.gasomann.com/resources/static/images/values/valueDetailBackground1.png',        'https://server.gasomann.com/resources/static/images/values/valueDetail1.png',        'https://server.gasomann.com/resources/static/images/values/valueBackground1.png',        'https://server.gasomann.com/resources/static/images/values/valueDetailMap1.png', 'https://server.gasomann.com/resources/static/videos/values/valuevideo1.mp4'),
       (3, 'https://server.gasomann.com/resources/static/images/values/valueLogo1.png',        'https://server.gasomann.com/resources/static/images/values/valueVideoThumbNail1.png',        'https://server.gasomann.com/resources/static/images/values/valueDetailBackground1.png',        'https://server.gasomann.com/resources/static/images/values/valueDetail1.png',        'https://server.gasomann.com/resources/static/images/values/valueBackground1.png',        'https://server.gasomann.com/resources/static/images/values/valueDetailMap1.png', 'https://server.gasomann.com/resources/static/videos/values/valuevideo1.mp4');

insert into worthy_consumption(id, created_time, modified_time, available_place, available_place_detail, original_price, sale_price, title, condition_id, worthy_consumption_url_id)
values (1, '2023-04-01 11:31:14.982873', '2023-04-01 11:31:14.982873', '대구광역시 북구, 수성구', '(복현오거리점, 시지점, 범어점)', 99000, 55000, 'REDGYM', 1, 1),
       (2, '2023-04-01 11:31:14.982873', '2023-04-01 11:31:14.982873', '대구광역시 북구, 중구', '(경북대점, 침산점, 동성로점)', 120000, 78000, '스터디카페 TASS', 2, 2),
       (3, '2023-04-01 11:31:14.982873', '2023-04-01 11:31:14.982873', '대구광역시 북구', '(신청시 구글폼으로 장소 안내)', 55000, 33000, 'Substory', 3, 3);

insert into worthy_consumption_hashtags(worthy_consumption_id, worthy_consumption_hashtags)
values (1, 오운완), (1, '갓생'), (1, '헬린'),
       (2, '빡집중'), (2, '공부환경'),
       (3, '원데이클래스'), (3, '가죽공예'), (3, '취미');

insert into recommendation_reason(id, title, description, worthy_consumption_id)
values (1, '친절한 기본운동지도', '헬스장에 난생 처음가면 뭘 해야할지 모르고 런닝머신만 타고 오는 경우도 있어요. 그런데 레드짐에서는 그럴 일이 없어요. 트레이너분들이 먼저 회원들에게 다가가서 필요한 운동지도가 있는지 물어봐주시거든요.', 1),
       (2, '좋은 시설과 많은 트레이너 수', '레드짐에 처음 가면 \'우와 많다!\'라고 생각하시게 될거에요. 첫 번째는 운동 머신들이고, 두 번째는 항상 회원들을 도와주기 위해 대기하고 있는 트레이너분들이에요. 처음보는 머신이라고 걱정하지 마세요, 옆에 있는 트레이너분들이 친절하게 알려줄거니까요.', 1)
    (3, '공부에 집중할 수 있는 환경', '사람이 많은 도서관은 답답하고, 사람들이 많이 돌아다녀서 집중도 잘 안돼요. 그렇다고 카페에서 공부하기는 너무 시끄럽죠. 스터디카페 TASS에서는 도서관보다 쾌적한 공간에서 집중력을 유지하며 나만의 페이스로 공부할 수 있어요.', 2),
(4, '깨끗한 내부공간, 편안한 휴게시설', '주변 환경이 깨끗해야 공부에 집중을 더 잘할 수 있는 것은 이미 잘 알려진 사실이에요. 스터디카페 TASS는 깨끗한 시설을 보유 하면서도, 편안한 휴게시설까지 가지고 있어요. 커피, 간식과 같은 다과류부터 충전기, 독서대와 같은 구비물품까지, TASS에서 이용하세요.', 2),
(5, '세상에 하나뿐인 나만의 작품', '고급스러운 가죽제품들은 부담스러운 가격에 디자인도 정해져있어 내 입맛에 맞는 제품을 찾기 어려워요. Substory 원데이클래스는 제공되는 기본 디자인에 참여자의 취향을 담아 체험할 수 있어요. Substory를 통해 고급스러우면서 실용적인 나만의 \'작품\'을 만들어가세요.', 3);
-- <Shorts> --

insert into shorts(id, category, uploader_name, content, image_path, video_path)
values (1, 'SAVING', '이름1', '내용1', 'https://server.gasomann.com/resources/static/images/shorts/shortImage1.png',
        'https://server.gasomann.com/resources/static/videos/shorts/shortVideo1.mp4'),
       (2, 'SAVING', '이름2', '내용2', 'https://server.gasomann.com/resources/static/images/shorts/shortImage2.png',
        'https://server.gasomann.com/resources/static/videos/shorts/shortVideo2.mp4'),
       (3, 'EDUCATION', '이름3', '내용3', 'https://server.gasomann.com/resources/static/images/shorts/shortImage3.png',
        'https://server.gasomann.com/resources/static/videos/shorts/shortVideo3.mp4');

-- <Coupon> --

insert into coupon(id, created_time, modified_time, image_path, limit_end_date, limit_start_date, pin, title, worthy_consumption_id, issuable_start_date, issuable_end_date, coupon_type, issue_count_limit, how_to_use, price_tag, available_price)
values (1, '2023-04-11 11:31:14.982873', '2023-04-01 11:31:14.982873', 'https://server.gasomann.com/resources/static/images/coupons/coupon1.png',  '2023-07-31', '2023-07-01', '230405', 'REDGYM 회원 할인권 (7월)', 1, '2023-06-15', '2023-07-15', 'EVENT', true, '카운터에서 트레이너에게 직접 쿠폰을 보여주세요.', '(1달회원권, VAT 포함)', 0),
       (2, '2023-04-11 11:31:14.982873', '2023-04-01 11:31:14.982873', 'https://server.gasomann.com/resources/static/images/coupons/coupon1.png', '2023-07-31', '2023-07-01', '230405', '스터디카페 TASS 할인권 (7월)', 2, '2023-06-15', '2023-07-15', 'EVENT', true, '키오스크에서 \'GASOMANN 할인\' 버튼을 눌러서 결제하세요!', '(29일 이용권, VAT 포함)', 0),
       (3, '2023-04-11 11:31:14.982873', '2023-04-01 11:31:14.982873', 'https://server.gasomann.com/resources/static/images/coupons/coupon1.png', '2023-07-31', '2023-07-01', '230405', 'Substory 7월 클래스 할인권', 3, '2023-06-15', '2023-07-15', 'EVENT', false, '원데이클래스 강의 구글폼 신청 시, \'GASOMANN 할인\' 항목을 선택하고, 클래스 당일 강사님께 쿠폰을 직접 보여주세요.', '(1회 클래스, VAT 포함)', 0),

insert into coupon_summary(coupon_id, coupon_summary)
values (1, '지금 바로 발급 가능합니다!'), (1, '양질의 트레이너가 기본 운동 지도를!'), (1, '쿠폰 발급 기한: 6/15 ~ 7/15'), (1, '7월 부터 쿠폰 사용 가능'),
    (2, '지금 바로 발급 가능합니다!'), (2, '쾌적한 환경에서, 편안하게 공부하세요'), (2, '쿠폰 발급 기한: 6/15 ~ 7/15'), (2, '7월 부터 쿠폰 사용 가능'),
    (3, '지금 바로 발급 가능합니다!'), (3, '비싼 명품 대신, 나만의 카드지갑 만들기'), (3, '쿠폰 발급 기한: 6/15 ~ 7/15'), (3, '7월 부터 쿠폰 사용 가능');

insert into coupon_caution(coupon_id, coupon_caution)
values (1, '정상적으로 쿠폰을 발급하지 않고 할인 가격으로 결제하게 되면, 결제 취소 및 그에 상응하는 법적 조치를 취할 수 있습니다.'), (1, '쿠폰으로 결제한 회원권의 경우, 환불이 불가합니다.'),
       (2, '정상적으로 쿠폰을 발급하지 않고 할인 가격으로 결제하게 되면, 결제 취소 및 그에 상응하는 법적 조치를 취할 수 있습니다.'), (2, '스터디룸 이용을 원하실 경우, 이용권 결제 후 관리자에게 연락하시면 됩니다.'), (2, '쿠폰으로 결제한 회원권의 경우, 환불이 불가합니다.'),
       (3, '쿠폰으로 결제한 회원권의 경우, 환불이 불가합니다');


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