-- Disable foreign key checks during initialization
SET REFERENTIAL_INTEGRITY = FALSE;

-- Drop all tables first (in reverse order to avoid foreign key conflicts)
DROP TABLE IF EXISTS INGREDIENT;
DROP TABLE IF EXISTS RECIPE_PRODUCT;
DROP TABLE IF EXISTS PRODUCT;
DROP TABLE IF EXISTS RECIPE;
DROP TABLE IF EXISTS MENU;
DROP TABLE IF EXISTS USERS;

-- Create base tables first (tables with no foreign keys)
CREATE TABLE IF NOT EXISTS USERS (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    Authentication_Token VARCHAR(100) NOT NULL
);

-- Create dependent tables
CREATE TABLE IF NOT EXISTS MENU (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    Menu_Day DATE NOT NULL,
    User_ID INT NOT NULL
);

CREATE TABLE IF NOT EXISTS RECIPE (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(50) NOT NULL,
    User_ID INT NOT NULL,
    Description CLOB,
    ImageURL VARCHAR(100),
    PreperationTime TIME,
    Servings DECIMAL(10,0),
    Difficulty VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS PRODUCT (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(50) NOT NULL,
    UserID INT NOT NULL,
    Description CLOB,
    ImageURL VARCHAR(100),
    Weight BIGINT,
    WeightUnit VARCHAR(100),
    Calories BIGINT NOT NULL
);

CREATE TABLE IF NOT EXISTS RECIPE_PRODUCT (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    Recipe_ID INT,
    Product_ID INT,
    Amount DOUBLE NOT NULL,
    Amount_Unit VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS INGREDIENT (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    Menu_ID INT NOT NULL,
    Recipe_ID INT,
    Product_ID INT
);

-- Add foreign key constraints after all tables are created
ALTER TABLE MENU
    ADD FOREIGN KEY (User_ID)
    REFERENCES USERS(ID);

ALTER TABLE RECIPE
    ADD FOREIGN KEY (User_ID)
    REFERENCES USERS(ID);

ALTER TABLE PRODUCT
    ADD FOREIGN KEY (UserID)
    REFERENCES USERS(ID);

ALTER TABLE RECIPE_PRODUCT
    ADD FOREIGN KEY (Recipe_ID)
    REFERENCES RECIPE(ID);

ALTER TABLE RECIPE_PRODUCT
    ADD FOREIGN KEY (Product_ID)
    REFERENCES PRODUCT(ID);

ALTER TABLE INGREDIENT
    ADD FOREIGN KEY (Menu_ID)
    REFERENCES MENU(ID);

ALTER TABLE INGREDIENT
    ADD FOREIGN KEY (Recipe_ID)
    REFERENCES RECIPE(ID);

ALTER TABLE INGREDIENT
    ADD FOREIGN KEY (Product_ID)
    REFERENCES PRODUCT(ID);

-- Re-enable foreign key checks
SET REFERENTIAL_INTEGRITY = TRUE;