-- menu_database.USERS definition

CREATE TABLE `USERS` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `Authentication_Token` varchar(100) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- menu_database.MEAL definition

CREATE TABLE `MEAL` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `Name` varchar(50) NOT NULL,
  `User_ID` int NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `MEAL_Users_FK` (`User_ID`),
  CONSTRAINT `MEAL_Users_FK` FOREIGN KEY (`User_ID`) REFERENCES `USERS` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- menu_database.MENU definition

CREATE TABLE `MENU` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `Day` date NOT NULL,
  `User_ID` int NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `MENU_Users_FK` (`User_ID`),
  CONSTRAINT `MENU_Users_FK` FOREIGN KEY (`User_ID`) REFERENCES `USERS` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- menu_database.PRODUCT definition

CREATE TABLE `PRODUCT` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `Name` varchar(50) NOT NULL,
  `User_ID` int NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `PRODUCT_Users_FK` (`User_ID`),
  CONSTRAINT `PRODUCT_Users_FK` FOREIGN KEY (`User_ID`) REFERENCES `USERS` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- menu_database.MEAL_PRODUCT definition

CREATE TABLE `MEAL_PRODUCT` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `Meal_ID` int NOT NULL,
  `Product_ID` int NOT NULL,
  `Product_Amount` int NOT NULL,
  `Product_Amount_Type` varchar(100) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `MEAL_PRODUCT_MEAL_ID_fk` (`Meal_ID`),
  KEY `MEAL_PRODUCT_PRODUCT_ID_fk` (`Product_ID`),
  CONSTRAINT `MEAL_PRODUCT_MEAL_ID_fk` FOREIGN KEY (`Meal_ID`) REFERENCES `MEAL` (`ID`),
  CONSTRAINT `MEAL_PRODUCT_PRODUCT_ID_fk` FOREIGN KEY (`Product_ID`) REFERENCES `PRODUCT` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- menu_database.MENU_ITEM definition

CREATE TABLE `MENU_ITEM` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `Menu_ID` int NOT NULL,
  `Meal_ID` int DEFAULT NULL,
  `Product_ID` int DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `MENU_ITEM_MEAL_ID_fk` (`Meal_ID`),
  KEY `MENU_ITEM_MENU_ID_fk` (`Menu_ID`),
  KEY `MENU_ITEM_PRODUCT_ID_fk` (`Product_ID`),
  CONSTRAINT `MENU_ITEM_MEAL_ID_fk` FOREIGN KEY (`Meal_ID`) REFERENCES `MEAL` (`ID`),
  CONSTRAINT `MENU_ITEM_MENU_ID_fk` FOREIGN KEY (`Menu_ID`) REFERENCES `MENU` (`ID`),
  CONSTRAINT `MENU_ITEM_PRODUCT_ID_fk` FOREIGN KEY (`Product_ID`) REFERENCES `PRODUCT` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;