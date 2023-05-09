-- <IdentityVerification> --
insert into identity_verification (id, birth_date, code, mobile, name)
values (1, '19990101', 'abcxxxxxabc', '01012345671', '김철수'),
       (2, '19990101', 'defxxxxxdef', '01012345672', '홍길동'),
       (3, '19990101', 'ghixxxxxghi', '01099998883', '박지원'),
       (4, '19990101', 'jklxxxxxjkl', '01012345674', '최현수'),
       (5, '19990101', 'mnoxxxxxmno', '01012345675', '이민우'),
       (6, '19990101', 'pqrxxxxxpqr', '01012345676', '정형식'),
       (7, '19990101', 'stuxxxxxstu', '01011112227', '이재우'),
       (8, '19990101', 'vwxxxxxxvwx', '01012345678', '배주현'),
       (9, '19990101', 'yzaxxxxxyza', '01012345679', '김다은');

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



insert into account_connection_map(account_id, connection_map, connection_map_key)
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
        '{bcrypt}$2a$10$9WYLiZLDXSkF15LFXnjppOKS/Ae6SGkWtHZRZte3IN0TfByYufxmK', null, null, null, 1, 1),
       (2, '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873', true, 'test02@naver.com', '길동',
        '{bcrypt}$2a$10$9WYLiZLDXSkF15LFXnjppOKS/Ae6SGkWtHZRZte3IN0TfByYufxmK', null, null, null, 2, 2),
       (3, '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873', true, 'test03@naver.com', '지원',
        '{bcrypt}$2a$10$9WYLiZLDXSkF15LFXnjppOKS/Ae6SGkWtHZRZte3IN0TfByYufxmK', null, null, null, 3, 3),
       (4, '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873', true, 'test04@naver.com', '현수',
        '{bcrypt}$2a$10$9WYLiZLDXSkF15LFXnjppOKS/Ae6SGkWtHZRZte3IN0TfByYufxmK', null, null, null, 4, 4),
       (5, '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873', true, 'test05@naver.com', '민우',
        '{bcrypt}$2a$10$9WYLiZLDXSkF15LFXnjppOKS/Ae6SGkWtHZRZte3IN0TfByYufxmK', null, null, null, 5, 5),
       (6, '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873', true, 'test06@naver.com', '형식',
        '{bcrypt}$2a$10$9WYLiZLDXSkF15LFXnjppOKS/Ae6SGkWtHZRZte3IN0TfByYufxmK', null, null, null, 6, 6),
       (7, '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873', true, 'test07@naver.com', '재우',
        '{bcrypt}$2a$10$9WYLiZLDXSkF15LFXnjppOKS/Ae6SGkWtHZRZte3IN0TfByYufxmK', null, null, null, 7, 7),
       (8, '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873', true, 'test08@naver.com', '주현',
        '{bcrypt}$2a$10$9WYLiZLDXSkF15LFXnjppOKS/Ae6SGkWtHZRZte3IN0TfByYufxmK', null, null, null, 8, 8),
       (9, '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873', true, 'test09@naver.com', '다은',
        '{bcrypt}$2a$10$9WYLiZLDXSkF15LFXnjppOKS/Ae6SGkWtHZRZte3IN0TfByYufxmK', null, null, null, 9, 9);


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
                      remittance_available_count,
                      remittance_once_limit, start_date, sub_title)
values (1, '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873', '2024-07-13', 'challenge1.png', 'challenge1.png',
        'http://3.37.5.91/resources/static/images/challenges/challenge1.png', '배달팁 아끼기', 2, 4000,
        '2023-03-13', '배달말고 포장, 배달팁 아끼기!'),
       (2, '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873', '2024-07-13', 'challenge2.png', 'challenge2.png',
        'http://3.37.5.91/resources/static/images/challenges/challenge2.png', '택시비 아끼기', 3, 6000,
        '2023-03-13', '걸어서 갈 수 있잖아?'),
       (3, '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873', '2024-07-13', 'challenge3.png', 'challenge3.png',
        'http://3.37.5.91/resources/static/images/challenges/challenge3.png', '커피값 아끼기', 3, 5000,
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
       (9, 0);


-- <Participation> --
insert into participation(id, created_time, modified_time, challenge_id, saving_count_id, user_id)
values (1, '2023-04-01 11:31:14.982873', '2023-04-01 11:31:14.982873', 1, 1, 1),
       (2, '2023-04-01 11:31:14.982873', '2023-04-01 11:31:14.982873', 1, 2, 2),
       (3, '2023-04-01 11:31:14.982873', '2023-04-01 11:31:14.982873', 1, 3, 3),
       (4, '2023-04-01 11:31:14.982873', '2023-04-01 11:31:14.982873', 2, 4, 4),
       (5, '2023-04-01 11:31:14.982873', '2023-04-01 11:31:14.982873', 2, 5, 5),
       (6, '2023-04-01 11:31:14.982873', '2023-04-01 11:31:14.982873', 2, 6, 6),
       (7, '2023-04-01 11:31:14.982873', '2023-04-01 11:31:14.982873', 3, 7, 7),
       (8, '2023-04-01 11:31:14.982873', '2023-04-01 11:31:14.982873', 3, 8, 8),
       (9, '2023-04-01 11:31:14.982873', '2023-04-01 11:31:14.982873', 3, 9, 9);


-- <Remittance> --
insert into remittance(id, created_time, modified_time, title, amount, participation_id)
values (1, '2023-04-11 11:31:14.982873', '2023-04-01 11:31:14.982873', '배달팁 아끼기', 1000, 1),
       (2, '2023-04-12 11:31:14.982873', '2023-04-01 11:31:14.982873', '배달팁 아끼기', 2000, 2),
       (3, '2023-04-13 11:31:14.982873', '2023-04-01 11:31:14.982873', '배달팁 아끼기', 3000, 3),
       (4, '2023-04-14 11:31:14.982873', '2023-04-01 11:31:14.982873', '택시비 아끼기', 4000, 4),
       (5, '2023-04-15 11:31:14.982873', '2023-04-01 11:31:14.982873', '택시비 아끼기', 5000, 5),
       (6, '2023-04-16 11:31:14.982873', '2023-04-01 11:31:14.982873', '택시비 아끼기', 6000, 6),
       (7, '2023-05-01 11:31:14.982873', '2023-04-01 11:31:14.982873', '커피값 아끼기', 7000, 7),
       (8, '2023-05-02 11:31:14.982873', '2023-04-01 11:31:14.982873', '커피값 아끼기', 8000, 8),
       (9, '2023-05-03 11:31:14.982873', '2023-04-01 11:31:14.982873', '커피값 아끼기', 9000, 9),
       (10, '2023-05-03 11:31:14.982873', '2023-04-01 11:31:14.982873', '배달팁 아끼기', 2000, 1),
       (11, '2023-05-03 11:31:14.982873', '2023-04-01 11:31:14.982873', '배달팁 아끼기', 3000, 1);

insert into inquiry(id, answer, content, time_stamp, title, user_id)
values (1, null, '문의 내용입니다.', '2023-04-01 11:31:14.982873', '문의 샘플1', 1),
       (2, null, '문의 내용입니다.', '2023-04-01 11:31:14.982873', '문의 샘플2', 2);

insert into shorts(id, category, content, image_url, title, video_url)
values (1, 'SAVING', '', 'http://3.37.5.91/resources/static/images/shorts/shortImage1.png', '샘플 비디오1', 'https://youtu.be/0gY_z7fqPjs'),
       (2, 'EDUCATION', '', 'http://3.37.5.91/resources/static/images/shorts/shortImage2.png', '샘플 비디오2', 'https://youtu.be/0gY_z7fqPjs');



insert into worthy_consumption_condition(id, is_issuable_coupon, issuable_coupon_end_date, issuable_coupon_start_date,
                                         last_month_amount, max_participants)
values (1, false, '2023-05-01', '2023-04-01', '10000', '100');

insert into worthy_consumptionurl(worthy_consumption_url_id, detail_background_image_url, detail_image_url, image_url,
                                  place_image_url, video_url)
values (1, 'http://3.37.5.91/resources/static/images/values/valueDetailBackground1.png', 'http://3.37.5.91/resources/static/images/values/valueDetail1.png',
        'http://3.37.5.91/resources/static/images/values/valueBackground1.png', 'http://3.37.5.91/resources/static/images/values/valueDetailMap1.png', 'https://youtu.be/0gY_z7fqPjs');

insert into worthy_consumption(worthy_consumption_id, created_time, modified_time, original_price, place_tag, price_tag,
                               sale_price, summary, worthy_consumption_title, condition_id, worthy_consumption_url_id)
values (1, '2023-04-01 11:31:14.982873', '2023-04-01 11:31:14.982873', 99000, '대구 전 지역', '1달 회원권', '55000',
        '7월 절약 금액 55,000원 달성 시', '레드 짐', 1, 1);


insert into coupon(coupon_id, created_time, modified_time, image_url, limit_end_date, limit_start_date, pin, title,
                   worthy_consumption_id)
values (1, '2023-04-11 11:31:14.982873', '2023-04-01 11:31:14.982873', 'http://3.37.5.91/resources/static/images/coupons/coupon1.png',
        '2023-08-31', '2023-07-31', '1234', '레드짐 8월 할인권', 1),
       (2, '2023-04-11 11:31:14.982873', '2023-04-01 11:31:14.982873', 'http://3.37.5.91/resources/static/images/coupons/coupon1.png',
        '2023-07-31', '2023-06-30', '1234', '레드짐 7월 할인권', 1);