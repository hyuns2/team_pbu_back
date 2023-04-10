-- <IdentityVerification> --
insert into identity_verification (id, birth_date, code, mobile, name)
values (1, '19990101', 'abcdefghijklmnopqrstuvwxyz', '01012345678', '김철수'),
        (2, '19990101', 'sadfsadfsadfsadfsdaf', '01012345678', '김철수');

-- <User> --
insert into user (id, created_time, modified_time, agreement, email, nickname, password, profile_image, identity_verification_id)
values (1, '2023-02-08 11:31:14.982873', '2023-02-08 11:31:14.982873', true, 'hansol8701@naver.com', '철수', '{bcrypt}$2a$10$9WYLiZLDXSkF15LFXnjppOKS/Ae6SGkWtHZRZte3IN0TfByYufxmK','image-url', 1);

insert into user_roles (user_id, roles)
values (1, 'ROLE_USER');
