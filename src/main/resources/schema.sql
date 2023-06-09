DROP TABLE IF EXISTS auto_table;

create table auto_table
(
    id            bigint auto_increment primary key,
    created_time  datetime(6)  null,
    modified_time datetime(6)  null,
    main_title    varchar(255) not null
)