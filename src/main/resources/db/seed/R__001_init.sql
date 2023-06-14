
-- <Challenge> --
INSERT IGNORE INTO challenge (id, created_time, modified_time, deleted_at, end_date, image_path,
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


INSERT IGNORE  INTO challenge_description(challenge_id, description)
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



INSERT IGNORE INTO notification(id, content, image_path, type, time_stamp, title)
values (1, '이벤트내용', 'https://server.gasomann.com/resources/static/images/notifications/b1165218-159a-4838-98a0-dd27bef67989.png', 'EVENT',
        '2023-05-24 11:00:14.982873', '이벤트1'),
       (2, '이벤트내용', 'https://server.gasomann.com/resources/static/images/notifications/b1165218-159a-4838-98a0-dd27bef67989.png', 'EVENT',
        '2023-05-24 11:00:14.982873', '이벤트2'),
       (3, '공지내용', 'https://server.gasomann.com/resources/static/images/notifications/b1165218-159a-4838-98a0-dd27bef67989.png', 'NOTICE',
        '2023-05-24 11:00:14.982873', '공지사항1'),
       (4, '공지내용', 'https://server.gasomann.com/resources/static/images/notifications/b1165218-159a-4838-98a0-dd27bef67989.png', 'NOTICE',
        '2023-05-24 11:00:14.982873', '공지사항2');

INSERT IGNORE INTO `card` (`d_type`, `id`, `image_path`, `sentences`, `sub_title`, `title`, `type`)
VALUES ('GeneralCondition', 13,
        'https://server.gasomann.com/resources/static/images/cards/b4125c05-d3ef-4e4d-8bf1-24107e3dacb7.png',
        '경제적 자유를 위해 생각을 바꿔라., GASOMANN의 시작을 함께해준 당신에게', 'OO', '사전 예약', 'GENERAL'),
       ('RemittanceCondition', 14,
        'https://server.gasomann.com/resources/static/images/cards/11f3c4f7-3c9f-4040-a8ef-28e44afc7e68.png',
        '경제적 자유를 위해 생각을 바꿔라., GASOMANN 챌린지에 처음으로 참여했어요!', 'OO', '절약 1회 달성', 'REMITTANCE'),
       ('RemittanceCondition', 15,
        'https://server.gasomann.com/resources/static/images/cards/6220ceec-7278-49a0-bd6d-2349275e7031.png',
        '경제적 자유를 위해 생각을 바꿔라., 챌린지를 통한 절약을 3회 달성했어요.', 'OO', '절약 3회 달성', 'REMITTANCE'),
       ('RemittanceCondition', 16,
        'https://server.gasomann.com/resources/static/images/cards/2cb1b5ce-5e4f-4316-b9f3-378528a08713.png',
        '경제적 자유를 위해 생각을 바꿔라., 챌린지를 통한 절약을 5회 달성했어요.', 'oo', '절약 5회 달성', 'REMITTANCE'),
       ('RemittanceCondition', 17,
        'https://server.gasomann.com/resources/static/images/cards/2ad44bdd-3118-44f3-8160-634aee42bbd4.png',
        '경제적 자유를 위해 생각을 바꿔라., 절약 챌린지를 벌써 10회나 달성했어요!', 'OO', '절약 10회 달성', 'REMITTANCE'),
       ('RemittanceCondition', 18,
        'https://server.gasomann.com/resources/static/images/cards/0dfdb2f7-7ab5-4606-a916-76b0fc0326e0.png',
        '경제적 자유를 위해 생각을 바꿔라., GASOMANN에서 만원 절약에 성공했어요!', 'oo', '절약 금액 1만원 달성', 'REMITTANCE'),
       ('RemittanceCondition', 19,
        'https://server.gasomann.com/resources/static/images/cards/0d532a13-dee9-4007-9a34-f21b036bc2c9.png',
        '경제적 자유를 위해 생각을 바꿔라., 이때까지 3만원 절약 했어요!', 'OO', '절약 금액 3만원 달성', 'REMITTANCE'),
       ('RemittanceCondition', 20,
        'https://server.gasomann.com/resources/static/images/cards/94ff05da-57b9-4aef-903c-786b71ded8e1.png',
        '경제적 자유를 위해 생각을 바꿔라., 5만원 절약 성공!', 'OO', '절약 금액 5만원 달성', 'REMITTANCE'),
       ('RemittanceCondition', 21,
        'https://server.gasomann.com/resources/static/images/cards/62ac9aec-e9b3-4a64-b74a-5887371da572.png',
        '경제적 자유를 위해 생각을 바꿔라., 10만원을 모아버리셨네요...!', 'ooo', '절약 금액 10만원 달성', 'REMITTANCE'),
       ('RemittanceCondition', 22,
        'https://server.gasomann.com/resources/static/images/cards/87479278-d850-4e8c-8dd2-8fa2805e173b.png',
        '경제적 자유를 위해 생각을 바꿔라., 하루에 챌린지에 3번이나 참여했어요!', 'OO', '하루 3회 절약 달성', 'REMITTANCE'),
       ('RemittanceCondition', 23,
        'https://server.gasomann.com/resources/static/images/cards/ad725a6e-31e8-4709-852f-e197931832a1.png',
        '경제적 자유를 위해 생각을 바꿔라., 일주일에 15번 참여했어요. 프로 절약러네요.', 'oo', '7일 간 15회 절약 달성', 'REMITTANCE'),
       ('CouponCondition', 24,
        'https://server.gasomann.com/resources/static/images/cards/8107bb1f-9ccb-4971-996e-1b564c290218.png',
        '경제적 자유를 위해 생각을 바꿔라., 3달 연속 목표 달성에 성공했어요!', 'OO', '3달 연속 쿠폰 발급', 'COUPON'),
       ('CouponCondition', 25,
        'https://server.gasomann.com/resources/static/images/cards/c4965b7a-b00d-41a6-aa53-aa9537105987.png',
        '경제적 자유를 위해 생각을 바꿔라., 5달 연속 이라...당신 정말 대단하군요!', 'oo', '5달 연속 쿠폰 발급', 'COUPON');



INSERT IGNORE INTO `remittance_card` (`charge`, `count`, `term`, `id`)
VALUES (0, 1, 0, 14),
       (0, 3, 0, 15),
       (0, 5, 0, 16),
       (0, 10, 0, 17),
       (10000, 0, 0, 18),
       (30000, 0, 0, 19),
       (50000, 0, 0, 20),
       (100000, 0, 0, 21),
       (0, 3, 1, 22),
       (0, 15, 7, 23);

INSERT IGNORE INTO `coupon_card` (`how_successive`, `what_number`, `id`)
VALUES (3, 0, 24),
       (5, 0, 25);


-- <Worthy Consumption> --
INSERT IGNORE INTO worthy_consumption_condition(id, check_condition_type, convention_end_date, convention_start_date, max_issuance)
values (1, 'AVAILABLE', '2023-12-31', '2023-06-01', 1),
       (2, 'AVAILABLE',  '2024-01-31', '2023-06-01', 3),
       (3, 'AVAILABLE', '2024-01-31', '2023-06-01', 1000);

INSERT IGNORE INTO worthy_consumption_url(id, logo_path, video_thumb_nail_path, detail_background_image_path, detail_image_path, image_path, place_image_path, video_path)
values (1, 'https://server.gasomann.com/resources/static/images/values/valueLogo1.png',        'https://server.gasomann.com/resources/static/images/values/valueVideoThumbnail1.png',        'https://server.gasomann.com/resources/static/images/values/valueDetailBackground1.png',        'https://server.gasomann.com/resources/static/images/values/valueDetail1.png',        'https://server.gasomann.com/resources/static/images/values/valueBackground1.png',        'https://server.gasomann.com/resources/static/images/values/valueDetailMap1.png', 'https://server.gasomann.com/resources/static/videos/values/valueVideo1.mp4'),
       (2, 'https://server.gasomann.com/resources/static/images/values/valueLogo2.png',        'https://server.gasomann.com/resources/static/images/values/valueVideoThumbnail2.png',        'https://server.gasomann.com/resources/static/images/values/valueDetailBackground2.png',        'https://server.gasomann.com/resources/static/images/values/valueDetail2.png',        'https://server.gasomann.com/resources/static/images/values/valueBackground2.png',        'https://server.gasomann.com/resources/static/images/values/valueDetailMap2.png', 'https://server.gasomann.com/resources/static/videos/values/valueVideo2.mp4'),
       (3, 'https://server.gasomann.com/resources/static/images/values/valueLogo3.png',        'https://server.gasomann.com/resources/static/images/values/valueVideoThumbnail3.png',        'https://server.gasomann.com/resources/static/images/values/valueDetailBackground3.png',        'https://server.gasomann.com/resources/static/images/values/valueDetail3.png',        'https://server.gasomann.com/resources/static/images/values/valueBackground3.png',        'https://server.gasomann.com/resources/static/images/values/valueDetailMap3.png', 'https://server.gasomann.com/resources/static/videos/values/valueVideo3.mp4');

INSERT IGNORE INTO worthy_consumption(id, created_time, modified_time, available_place, available_place_detail, original_price, sale_price, title, condition_id, worthy_consumption_url_id)
values (1, '2023-04-01 11:31:14.982873', '2023-04-01 11:31:14.982873', '대구광역시 북구, 수성구', '(복현오거리점, 시지점, 범어점)', 99000, 55000, 'REDGYM', 1, 1),
       (2, '2023-04-01 11:31:14.982873', '2023-04-01 11:31:14.982873', '대구광역시 북구, 중구', '(경북대점, 침산점, 동성로점)', 120000, 78000, '스터디카페 TASS', 2, 2),
       (3, '2023-04-01 11:31:14.982873', '2023-04-01 11:31:14.982873', '대구광역시 북구', '(신청시 구글폼으로 장소 안내)', 55000, 33000, 'Substory', 3, 3);

INSERT IGNORE INTO worthy_consumption_hashtags(worthy_consumption_id, hashtags)
values (1, '오운완'), (1, '갓생'), (1, '헬린이'),
       (2, '빡집중'), (2, '공부환경'), (2, '시험기간'),
       (3, '원데이클래스'), (3, '가죽공예'), (3, '취미');

INSERT IGNORE INTO recommendation_reason(id, title, description, worthy_consumption_id)
values (1, '친절한 기본운동지도', '헬스장에 난생 처음가면 뭘 해야할지 모르고 런닝머신만 타고 오는 경우도 있어요. 그런데 레드짐에서는 그럴 일이 없어요. 트레이너분들이 먼저 회원들에게 다가가서 필요한 운동지도가 있는지 물어봐주시거든요.', 1),
       (2, '좋은 시설과 많은 트레이너 수', '레드짐에 처음 가면 ''우와 많다!''라고 생각하시게 될거에요. 첫 번째는 운동 머신들이고, 두 번째는 항상 회원들을 도와주기 위해 대기하고 있는 트레이너분들이에요. 처음보는 머신이라고 걱정하지 마세요, 옆에 있는 트레이너분들이 친절하게 알려줄거니까요.', 1),
       (3, '공부에 집중할 수 있는 환경', '사람이 많은 도서관은 답답하고, 사람들이 많이 돌아다녀서 집중도 잘 안돼요. 그렇다고 카페에서 공부하기는 너무 시끄럽죠. 스터디카페 TASS에서는 도서관보다 쾌적한 공간에서 집중력을 유지하며 나만의 페이스로 공부할 수 있어요.', 2),
       (4, '깨끗한 내부공간, 편안한 휴게시설', '주변 환경이 깨끗해야 공부에 집중을 더 잘할 수 있는 것은 이미 잘 알려진 사실이에요. 스터디카페 TASS는 깨끗한 시설을 보유 하면서도, 편안한 휴게시설까지 가지고 있어요. 커피, 간식과 같은 다과류부터 충전기, 독서대와 같은 구비물품까지, TASS에서 이용하세요.', 2),
       (5, '세상에 하나뿐인 나만의 작품', '고급스러운 가죽제품들은 부담스러운 가격에 디자인도 정해져있어 내 입맛에 맞는 제품을 찾기 어려워요. Substory 원데이클래스는 제공되는 기본 디자인에 참여자의 취향을 담아 체험할 수 있어요. Substory를 통해 고급스러우면서 실용적인 나만의 ''작품''을 만들어가세요.', 3),
       (6, '부담없는 새로운 경험', '매일매일 반복되는 일상에 새로움을 더하고 싶은 마음은 굴뚝같지만, 막상 시도하려면 시간이나 금액 부담이 크죠. Substory에서 짧은 시간 안에 합리적인 가격으로 새로운 경험과 내 손으로 만든 작품, 두 가지를 모두 가져가실 수 있어요.', 3);


-- <Coupon> --

INSERT IGNORE INTO coupon(id, created_time, modified_time, image_path, limit_end_date, limit_start_date, pin, title, worthy_consumption_id, issuable_start_date, issuable_end_date, coupon_type, issue_count_limit, how_to_use, price_tag, available_price)
values (1, '2023-04-11 11:31:14.982873', '2023-04-01 11:31:14.982873', 'https://server.gasomann.com/resources/static/images/coupons/coupon1.png',  '2023-07-31', '2023-07-01', '230405', 'REDGYM 회원 할인권 (7월)', 1, '2023-06-10', '2023-07-15', 'EVENT', true, '카운터에서 트레이너에게 직접 쿠폰을 보여주세요.', '(1달회원권, VAT 포함)', 0),
       (2, '2023-04-11 11:31:14.982873', '2023-04-01 11:31:14.982873', 'https://server.gasomann.com/resources/static/images/coupons/coupon2.png', '2023-07-31', '2023-07-01', '230405', '스터디카페 TASS 할인권 (7월)', 2, '2023-06-10', '2023-07-15', 'EVENT', true, '키오스크에서 ''GASOMANN 할인'' 버튼을 눌러서 결제하세요!', '(29일 이용권, VAT 포함)', 0),
       (3, '2023-04-11 11:31:14.982873', '2023-04-01 11:31:14.982873', 'https://server.gasomann.com/resources/static/images/coupons/coupon3.png', '2023-07-31', '2023-07-01', '230405', 'Substory 7월 클래스 할인권', 3, '2023-06-10', '2023-07-15', 'EVENT', false, '원데이클래스 강의 구글폼 신청 시, ''GASOMANN 할인'' 항목을 선택하고, 클래스 당일 강사님께 쿠폰을 직접 보여주세요.', '(1회 클래스, VAT 포함)', 0);
INSERT IGNORE INTO coupon_summary(coupon_id, summary)
values (1, '지금 바로 발급 가능합니다!'), (1, '양질의 트레이너가 기본 운동 지도를!'), (1, '쿠폰 발급 기한: 6/15 ~ 7/15'), (1, '7월 부터 쿠폰 사용 가능'),
       (2, '지금 바로 발급 가능합니다!'), (2, '쾌적한 환경에서, 편안하게 공부하세요'), (2, '쿠폰 발급 기한: 6/15 ~ 7/15'), (2, '7월 부터 쿠폰 사용 가능'),
       (3, '지금 바로 발급 가능합니다!'), (3, '비싼 명품 대신, 나만의 카드지갑 만들기'), (3, '쿠폰 발급 기한: 6/15 ~ 7/15'), (3, '7월 부터 쿠폰 사용 가능');

INSERT IGNORE INTO coupon_caution(coupon_id, caution)
values (1, '정상적으로 쿠폰을 발급하지 않고 할인 가격으로 결제하게 되면, 결제 취소 및 그에 상응하는 법적 조치를 취할 수 있습니다.'), (1, '쿠폰으로 결제한 회원권의 경우, 환불이 불가합니다.'),
       (2, '정상적으로 쿠폰을 발급하지 않고 할인 가격으로 결제하게 되면, 결제 취소 및 그에 상응하는 법적 조치를 취할 수 있습니다.'), (2, '스터디룸 이용을 원하실 경우, 이용권 결제 후 관리자에게 연락하시면 됩니다.'), (2, '쿠폰으로 결제한 회원권의 경우, 환불이 불가합니다.'),
       (3, '쿠폰으로 결제한 회원권의 경우, 환불이 불가합니다');

