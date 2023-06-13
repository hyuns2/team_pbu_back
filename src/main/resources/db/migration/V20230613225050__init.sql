create table if not exists card
(
    d_type     varchar(31)   not null,
    id         bigint auto_increment
        primary key,
    image_path varchar(5000) not null,
    sentences  varchar(300)  not null,
    sub_title  varchar(30)   not null,
    title      varchar(30)   not null,
    type       varchar(30)   not null
)
    auto_increment = 26;

create table if not exists challenge
(
    id                      bigint auto_increment
        primary key,
    created_time            datetime(6)  null,
    modified_time           datetime(6)  null,
    deleted_at              datetime(6)  null,
    end_date                date         null,
    image_path              varchar(255) null,
    original_image_name     varchar(255) null,
    store_image_name        varchar(255) null,
    limited_number_of_times bigint       null,
    main_title              varchar(255) not null,
    max_saving_amount       bigint       null,
    min_saving_amount       bigint       null,
    start_date              date         null,
    sub_title               varchar(255) null
)
    auto_increment = 7;

create table if not exists challenge_description
(
    challenge_id bigint       not null,
    description  varchar(255) not null,
    constraint fk_challenge_to_description
        foreign key (challenge_id) references challenge (id)
);

create table if not exists coupon_card
(
    how_successive int    not null,
    what_number    int    not null,
    id             bigint not null
        primary key,
    constraint FK8wr0kc2ghfuojksbyi0kspc1y
        foreign key (id) references card (id)
);

create table if not exists identity_verification
(
    id            bigint auto_increment
        primary key,
    created_time  datetime(6) null,
    modified_time datetime(6) null,
    birth_date    varchar(20) not null,
    code          varchar(20) not null,
    mobile        varchar(20) not null,
    name          varchar(50) not null
)
    auto_increment = 10;

create table if not exists notification
(
    id         bigint auto_increment
        primary key,
    content    varchar(1000) not null,
    image_path varchar(5000) not null,
    time_stamp datetime(6)   not null,
    title      varchar(30)   not null,
    type       varchar(10)   not null
)
    auto_increment = 5;

create table if not exists remittance_card
(
    charge int    not null,
    count  int    not null,
    term   int    not null,
    id     bigint not null
        primary key,
    constraint FKhyfm0t92l9gvuwmhsf7dhway4
        foreign key (id) references card (id)
);

create table if not exists shorts
(
    id            bigint auto_increment
        primary key,
    category      varchar(255) null,
    content       varchar(255) null,
    image_path    varchar(255) null,
    uploader_name varchar(255) null,
    video_path    varchar(255) null
)
    auto_increment = 4;

create table if not exists user
(
    id                       bigint auto_increment
        primary key,
    created_time             datetime(6)  null,
    modified_time            datetime(6)  null,
    agreement                bit          not null,
    deleted_at               datetime(6)  null,
    email                    varchar(50)  not null,
    nickname                 varchar(50)  not null,
    password                 varchar(255) not null,
    image_path               varchar(255) null,
    original_image_name      varchar(255) null,
    store_image_name         varchar(255) null,
    identity_verification_id bigint       null,
    constraint fk_user_to_iv
        foreign key (identity_verification_id) references identity_verification (id)
)
    auto_increment = 10;

create table if not exists account
(
    id              bigint auto_increment
        primary key,
    account_numbers varchar(30) not null,
    bank_type       varchar(30) not null,
    user_id         bigint      null,
    constraint fk_account_to_user
        foreign key (user_id) references user (id)
)
    auto_increment = 10;

create table if not exists account_connection_map
(
    account_id      bigint      not null,
    connection_code varchar(30) not null,
    bank_type       varchar(30) not null,
    primary key (account_id, bank_type),
    constraint fk_account_to_connection
        foreign key (account_id) references account (id)
);

create table if not exists inquiry
(
    id         bigint auto_increment
        primary key,
    answer     varchar(1000) null,
    content    varchar(1000) not null,
    time_stamp datetime(6)   not null,
    title      varchar(30)   not null,
    user_id    bigint        null,
    constraint fk_inquiry_to_user
        foreign key (user_id) references user (id)
)
    auto_increment = 5;

create table if not exists likes_shorts
(
    id             bigint auto_increment
        primary key,
    created_time   datetime(6)  null,
    modified_time  datetime(6)  null,
    likes_category varchar(255) null,
    shorts_id      bigint       null,
    user_id        bigint       null,
    constraint fk_likeshorts_to_shorts
        foreign key (shorts_id) references shorts (id),
    constraint fk_likeshorts_to_user
        foreign key (user_id) references user (id)
)
    auto_increment = 4;

create table if not exists participation
(
    id                  bigint auto_increment
        primary key,
    created_time        datetime(6) null,
    modified_time       datetime(6) null,
    deleted_at          datetime(6) null,
    saving_count_of_day bigint      null,
    challenge_id        bigint      not null,
    user_id             bigint      not null,
    constraint fk_participation_to_challenge
        foreign key (challenge_id) references challenge (id),
    constraint fk_participation_to_user
        foreign key (user_id) references user (id)
)
    auto_increment = 11;

create table if not exists remittance
(
    id               bigint auto_increment
        primary key,
    created_time     datetime(6)  null,
    modified_time    datetime(6)  null,
    amount           bigint       not null,
    deleted_at       datetime(6)  null,
    title            varchar(255) not null,
    participation_id bigint       null,
    constraint fk_remittance_to_participation
        foreign key (participation_id) references participation (id)
)
    auto_increment = 11;

create table if not exists user_card
(
    id      bigint auto_increment
        primary key,
    date    date   not null,
    is_new  bit    not null,
    card_id bigint null,
    user_id bigint null,
    constraint fk_usercard_to_card
        foreign key (card_id) references card (id),
    constraint fk_usercard_to_user
        foreign key (user_id) references user (id)
);

create table if not exists user_roles
(
    user_id bigint      not null,
    roles   varchar(30) null,
    constraint fk_user_to_roles
        foreign key (user_id) references user (id)
);

create table if not exists worthy_consumption_condition
(
    id                    bigint auto_increment
        primary key,
    check_condition_type  varchar(255) null,
    convention_end_date   date         null,
    convention_start_date date         null,
    max_issuance          int          null
)
    auto_increment = 4;

create table if not exists worthy_consumption_url
(
    id                           bigint auto_increment
        primary key,
    detail_background_image_path varchar(255) null,
    detail_image_path            varchar(255) null,
    image_path                   varchar(255) null,
    logo_path                    varchar(255) null,
    place_image_path             varchar(255) null,
    video_path                   varchar(255) null,
    video_thumb_nail_path        varchar(255) null
)
    auto_increment = 4;

create table if not exists worthy_consumption
(
    id                        bigint auto_increment
        primary key,
    created_time              datetime(6)  null,
    modified_time             datetime(6)  null,
    available_place           varchar(255) null,
    available_place_detail    varchar(255) null,
    original_price            int          null,
    sale_price                int          null,
    title                     varchar(255) null,
    condition_id              bigint       null,
    worthy_consumption_url_id bigint       null,
    constraint fk_wc_to_condition
        foreign key (condition_id) references worthy_consumption_condition (id),
    constraint fk_wc_to_url
        foreign key (worthy_consumption_url_id) references worthy_consumption_url (id)
)
    auto_increment = 4;

create table if not exists coupon
(
    id                    bigint auto_increment
        primary key,
    created_time          datetime(6)  null,
    modified_time         datetime(6)  null,
    available_price       bigint       null,
    coupon_type           varchar(255) null,
    how_to_use            varchar(255) not null,
    image_path            varchar(255) not null,
    issuable_end_date     date         null,
    issuable_start_date   date         null,
    issue_count_limit     bit          not null,
    limit_end_date        date         not null,
    limit_start_date      date         not null,
    pin                   int          not null,
    price_tag             varchar(255) null,
    title                 varchar(30)  not null,
    worthy_consumption_id bigint       null,
    constraint fk_coupon_to_wc
        foreign key (worthy_consumption_id) references worthy_consumption (id)
)
    auto_increment = 13;

create table if not exists coupon_caution
(
    coupon_id bigint       not null,
    caution   varchar(255) null,
    constraint fk_coupon_to_caution
        foreign key (coupon_id) references coupon (id)
);

create table if not exists coupon_issuance
(
    id            bigint auto_increment
        primary key,
    created_time  datetime(6) null,
    modified_time datetime(6) null,
    created       bit         null,
    used          bit         null,
    coupon_id     bigint      null,
    user_id       bigint      null,
    constraint fk_issuance_to_coupon
        foreign key (coupon_id) references coupon (id),
    constraint fk_issuance_to_user
        foreign key (user_id) references user (id)
)
    auto_increment = 5;

create table if not exists coupon_summary
(
    coupon_id bigint       not null,
    summary   varchar(255) null,
    constraint fk_coupon_to_summary
        foreign key (coupon_id) references coupon (id)
);

create table if not exists likes_worthy_consumption
(
    id                    bigint auto_increment
        primary key,
    created_time          datetime(6)  null,
    modified_time         datetime(6)  null,
    likes_category        varchar(255) null,
    user_id               bigint       null,
    worthy_consumption_id bigint       null,
    constraint fk_likewc_to_user
        foreign key (user_id) references user (id),
    constraint fk_likewc_to_wc
        foreign key (worthy_consumption_id) references worthy_consumption (id)
)
    auto_increment = 4;

create table if not exists recommendation_reason
(
    id                    bigint auto_increment
        primary key,
    description           varchar(255) null,
    title                 varchar(255) null,
    worthy_consumption_id bigint       null,
    constraint fk_reason_to_wc
        foreign key (worthy_consumption_id) references worthy_consumption (id)
)
    auto_increment = 10;

create table if not exists worthy_consumption_hashtags
(
    worthy_consumption_id bigint      not null,
    hashtags              varchar(30) null,
    constraint fk_wc_to_hashtags
        foreign key (worthy_consumption_id) references worthy_consumption (id)
);

