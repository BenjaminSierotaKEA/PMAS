DROP TABLE IF EXISTS PROJECTS CASCADE;
CREATE TABLE PROJECTS
(
    id          INT AUTO_INCREMENT,
    name        VARCHAR(200) UNIQUE NOT NULL,
    description VARCHAR(200),
    timeBudget  INT                 NOT NULL,
    deadline    DATE                NOT NULL,
    PRIMARY KEY (id)

);

DROP TABLE IF EXISTS SUBPROJECTS CASCADE;
CREATE TABLE SUBPROJECTS
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

DROP TABLE IF EXISTS TASKS CASCADE;
CREATE TABLE TASKS
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

DROP TABLE IF EXISTS ROLES CASCADE;
CREATE TABLE ROLES
(
    id   INT AUTO_INCREMENT,
    name VARCHAR(30) UNIQUE NOT NULL,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS USERS CASCADE;
CREATE TABLE USERS
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
DROP TABLE IF EXISTS USERPROJECTS CASCADE;
CREATE TABLE USERPROJECTS
(
    projectid INT,
    userid    INT,
    PRIMARY KEY (projectid, userid),
    FOREIGN KEY (projectid) REFERENCES projects (id) ON DELETE CASCADE,
    FOREIGN KEY (userid) REFERENCES users (id) ON DELETE CASCADE
);

-- DROP TABLE IF EXISTS SUBPROJECTTASKS CASCADE;
-- CREATE TABLE SUBPROJECTTASKS
-- (
--     subprojectid INT,
--     taskid       INT,
--     PRIMARY KEY (subprojectid, taskid),
--     FOREIGN KEY (subprojectid) REFERENCES subprojects (id) ON DELETE CASCADE,
--     FOREIGN KEY (taskid) REFERENCES tasks (id) ON DELETE CASCADE
-- );

DROP TABLE IF EXISTS USERTASKS CASCADE;
CREATE TABLE USERTASKS
(
    userid INT,
    taskid INT,
    PRIMARY KEY (userid, taskid),
    FOREIGN KEY (userid) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (taskid) REFERENCES tasks (id) ON DELETE CASCADE
);
