-- <IdentityVerification> --

insert into identity_verification (id, birth_date, code, mobile, name, created_time, modified_time)  -- 비밀번호 모두 123456으로 동일 --
values (1, '19990101', 'abcxxxxxabc', '01012345671', '김철수', '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873'),
       (2, '19990101', 'defxxxxxdef', '01012345672', '홍길동', '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873'),
       (3, '19990101', 'ghixxxxxghi', '01099998883', '박지원', '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873'),
       (4, '19990101', 'jklxxxxxjkl', '01012345674', '최현수', '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873'),
       (5, '19990101', 'mnoxxxxxmno', '01012345675', '이민우', '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873'),
       (6, '19990101', 'pqrxxxxxpqr', '01012345676', '정형식', '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873'),
       (7, '19990101', 'stuxxxxxstu', '01011112227', '이재우', '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873'),
       (8, '19990101', 'vwxxxxxxvwx', '01012345678', '배주현', '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873'),
       (9, '19990101', 'yzaxxxxxyza', '01012345679', '김다은', '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873');

-- <Account> --
insert into account(id, account_numbers, bank_type)
values (1, '123456781', 'KDB'),
       (2, '123456782', 'IBK'),
       (3, '123456783', 'KB'),
       (4, '123456784', 'KDB'),
       (5, '123456785', 'SUHYUP'),
       (6, '123456786', 'NONGHYUP'),
       (7, '123456787', 'WOORI'),
       (8, '123456788', 'SC'),
       (9, '123456789', 'CITY');


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

-- <User> --
insert into user (id, created_time, modified_time, agreement, email, nickname, password, store_image_name,
                  original_image_name, image_path,
                  identity_verification_id, account_id)
values (1, '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873', true, 'test01@naver.com', '철수',
        '{bcrypt}$2a$10$yQZvUKRebIw8NcO8bRsuiewJib4zZQ5Pi.GTbodIjDHK5h3icbzrO', null, null, null, 1, 1),
       (2, '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873', true, 'test02@naver.com', '길동',
        '{bcrypt}$2a$10$yQZvUKRebIw8NcO8bRsuiewJib4zZQ5Pi.GTbodIjDHK5h3icbzrO', null, null, null, 2, 2),
       (3, '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873', true, 'test03@naver.com', '지원',
        '{bcrypt}$2a$10$yQZvUKRebIw8NcO8bRsuiewJib4zZQ5Pi.GTbodIjDHK5h3icbzrO', null, null, null, 3, 3),
       (4, '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873', true, 'test04@naver.com', '현수',
        '{bcrypt}$2a$10$yQZvUKRebIw8NcO8bRsuiewJib4zZQ5Pi.GTbodIjDHK5h3icbzrO', null, null, null, 4, 4),
       (5, '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873', true, 'test05@naver.com', '민우',
        '{bcrypt}$2a$10$yQZvUKRebIw8NcO8bRsuiewJib4zZQ5Pi.GTbodIjDHK5h3icbzrO', null, null, null, 5, 5),
       (6, '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873', true, 'test06@naver.com', '형식',
        '{bcrypt}$2a$10$yQZvUKRebIw8NcO8bRsuiewJib4zZQ5Pi.GTbodIjDHK5h3icbzrO', null, null, null, 6, 6),
       (7, '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873', true, 'test07@naver.com', '재우',
        '{bcrypt}$2a$10$yQZvUKRebIw8NcO8bRsuiewJib4zZQ5Pi.GTbodIjDHK5h3icbzrO', null, null, null, 7, 7),
       (8, '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873', true, 'test08@naver.com', '주현',
        '{bcrypt}$2a$10$yQZvUKRebIw8NcO8bRsuiewJib4zZQ5Pi.GTbodIjDHK5h3icbzrO', null, null, null, 8, 8),
       (9, '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873', true, 'test09@naver.com', '다은',
        '{bcrypt}$2a$10$yQZvUKRebIw8NcO8bRsuiewJib4zZQ5Pi.GTbodIjDHK5h3icbzrO', null, null, null, 9, 9);


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


-- <Challenge> --
insert into challenge(id, created_time, modified_time, end_date, store_image_name,
                      original_image_name, image_path, main_title,
                      limited_number_of_times,
                      max_saving_amount, min_saving_amount, start_date, sub_title)
values (1, '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873', '2024-07-13', 'challenge1.png', 'challenge1.png',
        'https://server.gasomann.com/resources/static/images/challenges/challenge1.png', '배달팁 아끼기', 2, 4000, 1000,
        '2023-03-13', '배달말고 포장, 배달팁 아끼기!'),
       (2, '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873', '2024-07-13', 'challenge2.png', 'challenge2.png',
        'https://server.gasomann.com/resources/static/images/challenges/challenge2.png', '택시비 아끼기', 3, 6000, 1000,
        '2023-03-13', '걸어서 갈 수 있잖아?'),
       (3, '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873', '2024-07-13', 'challenge3.png', 'challenge3.png',
        'https://server.gasomann.com/resources/static/images/challenges/challenge3.png', '커피값 아끼기', 3, 5000, 1000,
        '2023-03-13', '그 커피 꼭 사야 해?');



insert into challenge_description(challenge_id, description)
values (1, '배달말고 포장해서, 배달팁도 아끼고 건강도 챙기기!'),
       (1, '칼로리 높은 배달음식도 먹을텐데, 조금 걸어두는 편이 좋지 않겠어요?'),
       (2, '배달말고 포장해서, 배달팁도 아끼고 건강도 챙기기!'),
       (2, '칼로리 높은 배달음식도 먹을텐데, 조금 걸어두는 편이 좋지 않겠어요?'),
       (3, '배달말고 포장해서, 배달팁도 아끼고 건강도 챙기기!'),
       (3, '칼로리 높은 배달음식도 먹을텐데, 조금 걸어두는 편이 좋지 않겠어요?');


-- <SavingCount> --
insert into saving_count(id, count)
values (1, 0),
       (2, 0),
       (3, 0),
       (4, 0),
       (5, 0),
       (6, 0),
       (7, 0),
       (8, 0),
       (9, 0),
       (10, 0);


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
values (1, '2023-04-11 11:31:14.982873', '2023-04-01 11:31:14.982873', '배달팁 아끼기', 4000, 1),
       (2, '2023-04-12 11:31:14.982873', '2023-04-01 11:31:14.982873', '배달팁 아끼기', 2000, 2),
       (3, '2023-04-13 11:31:14.982873', '2023-04-01 11:31:14.982873', '배달팁 아끼기', 3000, 3),
       (4, '2023-04-14 11:31:14.982873', '2023-04-01 11:31:14.982873', '배달팁 아끼기', 4000, 4),
       (5, '2023-04-15 11:31:14.982873', '2023-04-01 11:31:14.982873', '배달팁 아끼기', 5000, 5),
       (6, '2023-04-16 11:31:14.982873', '2023-04-01 11:31:14.982873', '배달팁 아끼기', 6000, 6),
       (7, '2023-05-01 11:31:14.982873', '2023-04-01 11:31:14.982873', '배달팁 아끼기', 7000, 7),
       (8, '2023-05-02 11:31:14.982873', '2023-04-01 11:31:14.982873', '배달팁 아끼기', 8000, 8),
       (9, '2023-05-03 11:31:14.982873', '2023-04-01 11:31:14.982873', '배달팁 아끼기', 9000, 9),
       (10, '2023-05-03 11:31:14.982873', '2023-04-01 11:31:14.982873', '배달팁 아끼기', 2000, 1),
       (11, '2023-05-03 11:31:14.982873', '2023-04-01 11:31:14.982873', '택시비 아끼기', 10000, 10);

insert into inquiry(id, answer, content, time_stamp, title, user_id)
values (1, '답변입니다.', '문의 내용입니다.', '2023-04-01 11:31:14.982873', '문의 샘플1', 1),
       (2, null, '문의 내용입니다.', '2023-04-01 11:31:14.982873', '문의 샘플2', 1),
       (3, null, '문의 내용입니다.', '2023-04-01 11:31:14.982873', '문의 샘플3', 2),
       (4, '답변입니다.', '문의 내용입니다.', '2023-04-01 11:31:14.982873', '문의 샘플4', 3);

insert into notification(id, content, image_path, type, time_stamp, title)
values (1, '이벤트내용', 'https://server.gasomann.com/resources/static/images/cards/card1.png', 'EVENT', '2023-05-24 11:00:14.982873', '이벤트1'),
       (2, '이벤트내용', 'https://server.gasomann.com/resources/static/images/cards/card1.png', 'EVENT', '2023-05-24 11:00:14.982873', '이벤트2'),
       (3, '공지내용', 'https://server.gasomann.com/resources/static/images/cards/card1.png', 'NOTICE', '2023-05-24 11:00:14.982873', '공지사항1'),
       (4, '공지내용', 'https://server.gasomann.com/resources/static/images/cards/card1.png', 'NOTICE', '2023-05-24 11:00:14.982873', '공지사항2');
       
insert into card(d_type, id, type, title, sub_title, sentence, image_path)
values ('GeneralCondition', 1, 'GENERAL', '제목1', '부제목1', '명언1', 'https://server.gasomann.com/resources/static/images/cards/card1.png'),
       ('GeneralCondition', 2, 'GENERAL', '제목2', '부제목2', '명언2', 'https://server.gasomann.com/resources/static/images/cards/card1.png'),
       ('GeneralCondition', 3, 'GENERAL', '제목3', '부제목3', '명언3', 'https://server.gasomann.com/resources/static/images/cards/card1.png'),
       ('GeneralCondition', 4, 'GENERAL', '제목4', '부제목4', '명언4', 'https://server.gasomann.com/resources/static/images/cards/card1.png'),
       ('GeneralCondition', 5, 'GENERAL', '제목5', '부제목5', '명언5', 'https://server.gasomann.com/resources/static/images/cards/card1.png'),
       ('GeneralCondition', 6, 'GENERAL', '제목6', '부제목6', '명언6', 'https://server.gasomann.com/resources/static/images/cards/card1.png'),
       ('GeneralCondition', 7, 'GENERAL', '제목7', '부제목7', '명언7', 'https://server.gasomann.com/resources/static/images/cards/card1.png'),
       ('GeneralCondition', 8, 'GENERAL', '제목8', '부제목8', '명언8', 'https://server.gasomann.com/resources/static/images/cards/card1.png'),
       ('GeneralCondition', 9, 'GENERAL', '제목9', '부제목9', '명언9', 'https://server.gasomann.com/resources/static/images/cards/card1.png'),
       ('RemittanceCondition', 10, 'REMITTANCE', '제목10', '부제목10', '명언10', 'https://server.gasomann.com/resources/static/images/cards/card1.png'),
       ('RemittanceCondition', 11, 'REMITTANCE', '제목11', '부제목11', '명언11', 'https://server.gasomann.com/resources/static/images/cards/card1.png'),
       ('CouponCondition', 12, 'COUPON', '제목12', '부제목12', '명언12', 'https://server.gasomann.com/resources/static/images/cards/card1.png');

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
                                         last_month_amount, max_issuance)
values (1, 'OK', '2023-07-01', '2023-04-01', 10000, 100),
       (2, 'OK', '2023-07-01', '2023-04-01', 10000, 100),
       (3, 'OK', '2023-07-01', '2023-04-01', 10000, 100);

insert into worthy_consumption_url(id, logo_path, video_thumb_nail_path, detail_background_image_path, detail_image_path, image_path,
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

insert into worthy_consumption(id, created_time, modified_time, available_place, available_price,
                               original_price, sale_price, title, condition_id, worthy_consumption_url_id)
values (1, '2023-04-01 11:31:14.982873', '2023-04-01 11:31:14.982873', '대구 전 지역', '1달 회원권', 11000, 1000,
        '레드 짐', 1, 1),
       (2, '2023-04-01 11:31:14.982873', '2023-04-01 11:31:14.982873', '대구 전 지역', '2달 회원권', 22000, 2000,
        '옐로우 짐', 2, 2),
       (3, '2023-04-01 11:31:14.982873', '2023-04-01 11:31:14.982873', '대구 전 지역', '3달 회원권', 33000, 3000,
        '블루 짐', 3, 3);

insert into worthy_consumption_hashtags(worthy_consumption_id, worthy_consumption_hashtags)
values (1, 'hashtags1'), (1, 'hashtags1'), (1, 'hashtags1'),
       (2, 'hashtags2'), (2, 'hashtags2'), (2, 'hashtags2'),
       (3, 'hashtags3'), (3, 'hashtags3'), (3, 'hashtags3');


insert into recommendation_reason(id, title, description, worthy_consumption_id)
values (1, 'title1', 'description1', 1), (2, 'title2', 'description2', 1), (3, 'title3', 'description3', 1),
       (4, 'title1', 'description1', 2), (5, 'title2', 'description2', 2), (6, 'title3', 'description3', 2),
       (7, 'title1', 'description1', 3), (8, 'title2', 'description2', 3), (9, 'title3', 'description3', 3);

-- <Shorts> --

insert into shorts(id, category, uploader_name, content, image_path, video_path)
values (1, 'SAVING', '이름1', '내용1', 'https://3.37.5.91/resources/static/images/shorts/shortImage1.png',
        'https://server.gasomann.com/resources/static/videos/shorts/shortVideo1.mp4'),
       (2, 'SAVING', '이름2', '내용2', 'https://3.37.5.91/resources/static/images/shorts/shortImage2.png',
        'https://server.gasomann.com/resources/static/videos/shorts/shortVideo2.mp4'),
       (3, 'EDUCATION', '이름3', '내용3', 'https://3.37.5.91/resources/static/images/shorts/shortImage3.png',
        'https://server.gasomann.com/resources/static/videos/shorts/shortVideo3.mp4');

-- <Coupon> --
insert into coupon(id, created_time, modified_time, image_path, limit_end_date, limit_start_date, pin, title,
                   worthy_consumption_id, issuable_start_date, issuable_end_date)
values (1, '2023-04-11 11:31:14.982873', '2023-04-01 11:31:14.982873',
        'https://server.gasomann.com/resources/static/images/coupons/coupon1.png',
        '2023-05-31', '2023-05-01', '1234', '레드짐 5월 할인권', 1, '2023-05-01', '2023-05-31'),
       (2, '2023-04-11 11:31:14.982873', '2023-04-01 11:31:14.982873',
        'https://server.gasomann.com/resources/static/images/coupons/coupon1.png',
        '2023-06-30', '2023-06-01', '1234', '레드짐 6월 할인권', 1, '2023-05-01', '2023-05-31'),
       (3, '2023-04-11 11:31:14.982873', '2023-04-01 11:31:14.982873',
        'https://server.gasomann.com/resources/static/images/coupons/coupon1.png',
        '2023-07-31', '2023-07-01', '1234', '레드짐 3월 할인권', 1, '2023-05-01', '2023-05-31'),
       (4, '2023-04-11 11:31:14.982873', '2023-04-01 11:31:14.982873',
        'https://server.gasomann.com/resources/static/images/coupons/coupon1.png',
        '2023-08-31', '2023-08-01', '1234', '레드짐 4월 할인권', 1, '2023-05-01', '2023-05-31'),
       (5, '2023-04-11 11:31:14.982873', '2023-04-01 11:31:14.982873',
        'https://server.gasomann.com/resources/static/images/coupons/coupon1.png',
        '2023-05-31', '2023-05-01', '1234', '옐로우 짐 5월 할인권', 2,'2023-05-01', '2023-05-31'),
       (6, '2023-04-11 11:31:14.982873', '2023-04-01 11:31:14.982873',
        'https://server.gasomann.com/resources/static/images/coupons/coupon1.png',
        '2023-06-30', '2023-06-01', '1234', '옐로우 짐 6월 할인권', 2,'2023-05-01', '2023-05-31'),
       (7, '2023-04-11 11:31:14.982873', '2023-04-01 11:31:14.982873',
        'https://server.gasomann.com/resources/static/images/coupons/coupon1.png',
        '2023-07-31', '2023-07-01', '1234', '옐로우 짐 7월 할인권', 2,'2023-05-01', '2023-05-31'),
       (8, '2023-04-11 11:31:14.982873', '2023-04-01 11:31:14.982873',
        'https://server.gasomann.com/resources/static/images/coupons/coupon1.png',
        '2023-08-31', '2023-08-01', '1234', '옐로우 짐 8월 할인권', 2,'2023-05-01', '2023-05-31'),
       (9, '2023-04-11 11:31:14.982873', '2023-04-01 11:31:14.982873',
        'https://server.gasomann.com/resources/static/images/coupons/coupon1.png',
        '2023-05-31', '2023-05-01', '1234', '블루짐 5월 할인권', 3,'2023-05-01', '2023-05-31'),
       (10, '2023-04-11 11:31:14.982873', '2023-04-01 11:31:14.982873',
        'https://server.gasomann.com/resources/static/images/coupons/coupon1.png',
        '2023-06-30', '2023-06-01', '1234', '블루짐 6월 할인권', 3,'2023-05-01', '2023-05-31'),
       (11, '2023-04-11 11:31:14.982873', '2023-04-01 11:31:14.982873',
        'https://server.gasomann.com/resources/static/images/coupons/coupon1.png',
        '2023-07-31', '2023-07-01', '1234', '블루짐 7월 할인권', 3,'2023-05-01', '2023-05-31'),
       (12, '2023-04-11 11:31:14.982873', '2023-04-01 11:31:14.982873',
        'https://server.gasomann.com/resources/static/images/coupons/coupon1.png',
        '2023-08-31', '2023-08-01', '1234', '블루짐 8월 할인권', 3,'2023-05-01', '2023-05-31');

insert into coupon_summary(coupon_id, coupon_summary)
values (1, 'summary1'), (1, 'summary1'), (1, 'summary1'),
       (2, 'summary2'), (2, 'summary2'), (2, 'summary2'),
       (3, 'summary3'), (3, 'summary3'), (3, 'summary3'),
       (4, 'summary4'), (4, 'summary4'), (4, 'summary4'),
       (5, 'summary5'), (5, 'summary5'), (5, 'summary5'),
       (6, 'summary6'), (6, 'summary6'), (6, 'summary6'),
       (7, 'summary7'), (7, 'summary7'), (7, 'summary7'),
       (8, 'summary8'), (8, 'summary8'), (8, 'summary8'),
       (9, 'summary9'), (9, 'summary9'), (9, 'summary9'),
       (10, 'summary10'), (10, 'summary10'), (10, 'summary10'),
       (11, 'summary11'), (11, 'summary11'), (11, 'summary11'),
       (12, 'summary12'), (12, 'summary12'), (12, 'summary12');


insert into coupon_issuance(id, created_time, modified_time, created, used, coupon_id, user_id)
values (1, '2023-03-08 11:31:14.982873', '2023-03-08 11:31:14.982873', 1, 0, 1, 1),
       (2, '2023-04-08 11:31:14.982873', '2023-04-08 11:31:14.982873', 1, 0, 2, 2),
       (3, '2023-04-08 11:31:14.982873', '2023-04-08 11:31:14.982873', 1, 0, 3, 1),
       (4, '2023-05-08 11:31:14.982873', '2023-05-08 11:31:14.982873', 1, 0, 2, 1);

-- <Likes> --
insert into likes_shorts(id, created_time, modified_time, likes_category, shorts_id, user_id)
values (1,'2023-03-08 11:31:14.982873', '2023-03-08 11:31:14.982873', 'SHORTS_SAVING', 1, 1),
       (2,'2023-03-08 11:31:14.982873', '2023-03-08 11:31:14.982873', 'SHORTS_EDU', 3, 1),
       (3,'2023-03-08 11:31:14.982873', '2023-03-08 11:31:14.982873', 'SHORTS_SAVING', 2, 1);

insert into likes_worthy_consumption(id, created_time, modified_time, likes_category, user_id, worthy_consumption_id)
values (1,'2023-03-08 11:31:14.982873', '2023-03-08 11:31:14.982873', 'WORTHY_CONSUMPTION', 1, 1),
       (2,'2023-03-08 11:31:14.982873', '2023-03-08 11:31:14.982873', 'WORTHY_CONSUMPTION', 1, 2),
       (3,'2023-03-08 11:31:14.982873', '2023-03-08 11:31:14.982873', 'WORTHY_CONSUMPTION', 1, 3);
