-- menu_database.USERS definition

CREATE TABLE `USERS` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `Authentication_Token` varchar(100) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- menu_database.RECIPE definition

CREATE TABLE `RECIPE` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `Name` varchar(50) NOT NULL,
  `User_ID` int NOT NULL,
  `Description` blob,
  `ImageURL` varchar(100) DEFAULT NULL,
  `PreperationTime` time DEFAULT NULL,
  `Servings` decimal(10,0) DEFAULT NULL,
  `Difficulty` varchar(100) DEFAULT NULL,
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
  `UserID` int NOT NULL,
  `Description` blob,
  `ImageURL` varchar(100) DEFAULT NULL,
  `Weight` bigint DEFAULT NULL,
  `WeightUnit` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `Calories` bigint NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `PRODUCT_Users_FK` (`UserID`),
  CONSTRAINT `PRODUCT_Users_FK` FOREIGN KEY (`UserID`) REFERENCES `USERS` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- menu_database.RECIPE_PRODUCT definition

CREATE TABLE `RECIPE_PRODUCT` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `Recipe_ID` int DEFAULT NULL,
  `Product_ID` int DEFAULT NULL,
  `Amount` double NOT NULL,
  `Amount_Unit` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `MEAL_PRODUCT_MEAL_ID_fk` (`Recipe_ID`),
  KEY `MEAL_PRODUCT_PRODUCT_ID_fk` (`Product_ID`),
  CONSTRAINT `MEAL_PRODUCT_MEAL_ID_fk` FOREIGN KEY (`Recipe_ID`) REFERENCES `RECIPE` (`ID`),
  CONSTRAINT `MEAL_PRODUCT_PRODUCT_ID_fk` FOREIGN KEY (`Product_ID`) REFERENCES `PRODUCT` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- menu_database.INGREDIENT definition

CREATE TABLE `INGREDIENT` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `Menu_ID` int NOT NULL,
  `Recipe_ID` int DEFAULT NULL,
  `Product_ID` int DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `MENU_ITEM_MEAL_ID_fk` (`Recipe_ID`),
  KEY `MENU_ITEM_MENU_ID_fk` (`Menu_ID`),
  KEY `MENU_ITEM_PRODUCT_ID_fk` (`Product_ID`),
  CONSTRAINT `MENU_ITEM_MEAL_ID_fk` FOREIGN KEY (`Recipe_ID`) REFERENCES `RECIPE` (`ID`),
  CONSTRAINT `MENU_ITEM_MENU_ID_fk` FOREIGN KEY (`Menu_ID`) REFERENCES `MENU` (`ID`),
  CONSTRAINT `MENU_ITEM_PRODUCT_ID_fk` FOREIGN KEY (`Product_ID`) REFERENCES `PRODUCT` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;