DROP
DATABASE IF EXISTS PMASDatabase;

CREATE
DATABASE PMASDatabase;

USE
PMASDatabase;

DROP TABLE IF EXISTS projects;
CREATE TABLE projects
(
    id          INT AUTO_INCREMENT,
    name        VARCHAR(200) UNIQUE NOT NULL,
    description VARCHAR(200),
    timeBudget  INT                 NOT NULL,
    deadline    DATE                NOT NULL,
    PRIMARY KEY (id)

);

DROP TABLE IF EXISTS subprojects;
CREATE TABLE subprojects
(
    id          INT AUTO_INCREMENT,
    name        VARCHAR(200) UNIQUE NOT NULL,
    description VARCHAR(200),
    timeBudget  INT                 NOT NULL,
    completed   BOOL,
    timeTaken   INT,
    projectID   INT                 NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (projectID) REFERENCES projects (id) ON DELETE CASCADE

);

DROP TABLE IF EXISTS tasks;
CREATE TABLE tasks
(
    id           INT AUTO_INCREMENT,
    name         VARCHAR(200) UNIQUE NOT NULL,
    description  VARCHAR(200),
    timeBudget   INT                 NOT NULL,
    completed    BOOL,
    timeTaken    INT,
    deadline     DATE,
    subProjectID INT                 NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (subProjectID) REFERENCES subprojects (id) ON DELETE CASCADE
);

DROP TABLE IF EXISTS roles;
CREATE TABLE roles
(
    id   INT AUTO_INCREMENT,
    name VARCHAR(30) UNIQUE NOT NULL,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS users;
CREATE TABLE users
(
    id       INT AUTO_INCREMENT,
    name     VARCHAR(200) UNIQUE NOT NULL,
    email    VARCHAR(200) UNIQUE NOT NULL,
    password VARCHAR(30)         NOT NULL,
    role     INT                 NOT NULL,
    picture  VARCHAR(200),
    PRIMARY KEY (id),
    FOREIGN KEY (role) REFERENCES roles (id)
);



/* many to many relation tables:*/

/*
DROP TABLE IF EXISTS projectssubprojects;
CREATE TABLE projectsubprojects(
                                   projectid INT NOT NULL,
                                   subprojectid INT NOT NULL,
                                    PRIMARY KEY (projectid, subprojectid),
                                   FOREIGN KEY(projectid) REFERENCES projects(id) ON DELETE CASCADE,
                                   FOREIGN KEY(subprojectid) REFERENCES subprojects(id) ON DELETE CASCADE
);

 */

DROP TABLE IF EXISTS userprojects;
CREATE TABLE userprojects
(
    projectid INT,
    userid    INT,
    PRIMARY KEY (projectid, userid),
    FOREIGN KEY (projectid) REFERENCES projects (id) ON DELETE CASCADE,
    FOREIGN KEY (userid) REFERENCES users (id) ON DELETE CASCADE
);

DROP TABLE IF EXISTS subprojectstasks;
CREATE TABLE subprojecttasks
(
    subprojectid INT,
    taskid       INT,
    PRIMARY KEY (subprojectid, taskid),
    FOREIGN KEY (subprojectid) REFERENCES subprojects (id) ON DELETE CASCADE,
    FOREIGN KEY (taskid) REFERENCES tasks (id) ON DELETE CASCADE
);

DROP TABLE IF EXISTS usertasks;
CREATE TABLE usertasks
(
    userid INT,
    taskid INT,
    PRIMARY KEY (userid, taskid),
    FOREIGN KEY (userid) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (taskid) REFERENCES tasks (id) ON DELETE CASCADE
);


