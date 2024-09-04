create table MEAL
(
    ID   int auto_increment
        primary key,
    Name varchar(50) not null
);

create table MENU
(
    ID  int auto_increment
        primary key,
    Day date not null
);

create table MENU_MEAL_TYPE
(
    ID        int auto_increment
        primary key,
    Meal_type varchar(50) not null
);

create table PRODUCT
(
    ID   int auto_increment
        primary key,
    Name varchar(50) not null
);

create table MEAL_PRODUCT
(
    ID         int auto_increment
        primary key,
    Meal_ID    int null,
    Product_ID int null,
    constraint MEAL_PRODUCT_MEAL_ID_fk
        foreign key (Meal_ID) references MEAL (ID),
    constraint MEAL_PRODUCT_PRODUCT_ID_fk
        foreign key (Product_ID) references PRODUCT (ID)
);

create table MENU_ITEM
(
    ID         int auto_increment
        primary key,
    Menu_ID    int not null,
    Meal_ID    int null,
    Product_ID int null,
    constraint MENU_ITEM_MEAL_ID_fk
        foreign key (Meal_ID) references MEAL (ID),
    constraint MENU_ITEM_MENU_ID_fk
        foreign key (Menu_ID) references MENU (ID),
    constraint MENU_ITEM_PRODUCT_ID_fk
        foreign key (Product_ID) references PRODUCT (ID)
);

