create table if not exists User (
    id bigint primary key auto_increment,
    username varchar(50) not null,
    password varchar(100) not null,
    full_name varchar(50) not null,
    street varchar(50),
    city varchar(50),
    state char(2),
    zip varchar(10),
    phone_number varchar(20)
);

create table if not exists Taco_Order (
    id bigint primary key auto_increment,
    delivery_name varchar(50) not null,
    delivery_street varchar(50) not null,
    delivery_city varchar(50) not null,
    delivery_state char(2) not null,
    delivery_zip varchar(10) not null,
    cc_number char(16) not null,
    cc_expiration char(5) not null,
    cc_cvv char(3) not null,
    placed_at timestamp not null,
    user_id bigint not null,
    foreign key (user_id) references User (id) on delete cascade
);

create table if not exists Ingredient (
    id varchar(4) primary key,
    name varchar(25) not null,
    type varchar(15) not null
);

create table if not exists Taco (
    id bigint primary key auto_increment,
    name varchar(50) not null,
    order_id bigint not null,
    created_at timestamp not null,
    foreign key (order_id) references Taco_Order (id) on delete cascade
);

create table if not exists Taco_Ingredients (
    taco_id bigint not null,
    ingred_id varchar(4) not null,
    primary key (taco_id, ingred_id),
    foreign key (taco_id) references Taco (id) on delete cascade,
    foreign key (ingred_id) references Ingredient (id) on delete cascade
);
