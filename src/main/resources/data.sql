-- <IdentityVerification> --
insert into identity_verification (id, birth_date, code, mobile, name)
values (1, '19990101', 'abcdefghijklmnopqrstuvwxyz', '01012345678', '김철수'),
        (2, '19990101', 'sadfsadfsadfsadfsdaf', '01012345678', '김철수');

-- <Account> --
insert into account(id, account_numbers, bank_type)
values (1, '123456789', 'KDB');

insert into account_connection_map(account_id, connection_map, connection_map_key)
values (1, 'abcdef', 'CODEF');

-- <User> --
insert into user (id, created_time, modified_time, agreement, email, nickname, password, profile_image, identity_verification_id, account_id)
values (1, '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873', true, 'hansol8701@naver.com', '철수', '{bcrypt}$2a$10$9WYLiZLDXSkF15LFXnjppOKS/Ae6SGkWtHZRZte3IN0TfByYufxmK','image-url', 1, 1);

insert into user_roles (user_id, roles)
values (1, 'ROLE_USER');




-- <Challenge> --
insert into challenge(id, created_time, modified_time, end_date, image_url, main_title, remittance_available_count,
                      remittance_once_limit, start_date, sub_title)
values (1, '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873', '2024-02-13', 'image', '샘플 챌린지', 10, 1000, '2023-02-13', 'test');


insert into challenge_description(challenge_id, description)
values (1, 'description');

