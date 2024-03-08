DROP DATABASE IF EXISTS Project;
CREATE DATABASE Project;

USE Project;

CREATE TABLE Formation (
    numID INT AUTO_INCREMENT,
    formaName VARCHAR(200) NOT NULL,
    promotion ENUM('Initiale', 'Alternance', 'Continue') NOT NULL,
    CONSTRAINT PK_Formation PRIMARY KEY (numID),
    CONSTRAINT Section UNIQUE (formaName,promotion)
);

CREATE TABLE Student (
    numID INT AUTO_INCREMENT,
    lastname VARCHAR(300) NOT NULL,
    firstname VARCHAR(300) NOT NULL,
    formation_id INT,
    CONSTRAINT PK_Student PRIMARY KEY (numID),
    CONSTRAINT FK_Student_Formation_ID FOREIGN KEY (formation_id) REFERENCES Formation(numID) ON DELETE CASCADE
);
CREATE TABLE Project (
    numID INT AUTO_INCREMENT,
    coursName VARCHAR(300) NOT NULL,
    sujet VARCHAR(300) NOT NULL,
    dateRemPrev DATE,
    formation_id INT,
    CONSTRAINT PK_Project PRIMARY KEY (numID),
    CONSTRAINT FK_Project_Formation_ID FOREIGN KEY (formation_id) REFERENCES Formation(numID) ON DELETE CASCADE,
    CONSTRAINT Section UNIQUE (coursName,sujet)
);

CREATE TABLE Binome (
    binomeID INT AUTO_INCREMENT,
    projectID INT ,
    student1ID INT,
    student2ID INT NULL,
    gradeRapport DECIMAL(4, 2),
    dateRemEff DATE,
    CONSTRAINT PK_Binome PRIMARY KEY (binomeID),
    CONSTRAINT FK_Binome_Project_ID FOREIGN KEY (projectID) REFERENCES Project(numID) ON DELETE SET NULL,
    CONSTRAINT FK_Binome_Student1_ID Foreign Key (student1ID) REFERENCES Student(numID) ON DELETE SET NULL,
    CONSTRAINT FK_Binome_Student2_ID Foreign Key (student2ID) REFERENCES Student(numID) ON DELETE SET NULL,
    CONSTRAINT Section UNIQUE (student1ID,student2ID,projectID)  
);

CREATE TABLE Grades (
    gradesID INT AUTO_INCREMENT,
    projectID INT,
    studentID INT,
    gradeSoutenance DECIMAL(4, 2),
    average DECIMAL (4,2),
    CONSTRAINT PK_Grades PRIMARY KEY (gradesID),
    CONSTRAINT FK_Grades_Project_ID FOREIGN KEY (projectID) REFERENCES Project(numID) ON DELETE CASCADE,
    CONSTRAINT FK_Grades_Student_ID FOREIGN KEY (studentID) REFERENCES Student(numID) ON DELETE CASCADE,
    CONSTRAINT Section UNIQUE (studentID,projectID)
);

