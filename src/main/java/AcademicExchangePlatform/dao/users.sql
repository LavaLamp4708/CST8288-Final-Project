/**
 * Author:  Bob Paul
 */
CREATE DATABASE aep;

USE aep;

-- Table for storing user information
CREATE TABLE Users (
    userId INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    userType ENUM('AcademicProfessional', 'AcademicInstitution') NOT NULL
);

-- Table for storing academic professionals' information
CREATE TABLE AcademicProfessionals (
    profId INT AUTO_INCREMENT PRIMARY KEY,
    userId INT NOT NULL,
    firstName VARCHAR(50) NOT NULL,
    lastName VARCHAR(50) NOT NULL,
    position VARCHAR(100),
    expertise TEXT,
    FOREIGN KEY (userId) REFERENCES Users(userId)
);

-- Table for storing academic institutions' information
CREATE TABLE AcademicInstitutions (
    instId INT AUTO_INCREMENT PRIMARY KEY,
    userId INT NOT NULL,
    institutionName VARCHAR(100) NOT NULL,
    address VARCHAR(255),
    FOREIGN KEY (userId) REFERENCES Users(userId)
);


