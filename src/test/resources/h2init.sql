CREATE TABLE projects
(
    id          INT                 NOT NULL UNIQUE AUTO_INCREMENT,
    name        VARCHAR(200) UNIQUE NOT NULL,
    description VARCHAR(200),
    timeBudget  INT                 NOT NULL,
    PRIMARY KEY (id)

);

CREATE TABLE subprojects
(
    id          INT                 NOT NULL UNIQUE AUTO_INCREMENT,
    name        VARCHAR(200) UNIQUE NOT NULL,
    description VARCHAR(200),
    timeBudget  INT                 NOT NULL,
    completed   BOOL,
    timeTaken   INT,
    projectID   INT                 NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (projectID) REFERENCES projects (id) ON DELETE CASCADE

);

CREATE TABLE tasks
(
    id           INT                 NOT NULL UNIQUE AUTO_INCREMENT,
    name         VARCHAR(200) UNIQUE NOT NULL,
    description  VARCHAR(200),
    timeBudget   INT                 NOT NULL,
    completed    BOOL,
    timeTaken    INT,
    subProjectID INT                 NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (subProjectID) REFERENCES subprojects (id) ON DELETE CASCADE
);

CREATE TABLE roles
(
    id   INT                NOT NULL UNIQUE AUTO_INCREMENT,
    name VARCHAR(30) UNIQUE NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE users
(
    id       INT                 NOT NULL UNIQUE AUTO_INCREMENT,
    name     VARCHAR(200) UNIQUE NOT NULL,
    email    VARCHAR(200) UNIQUE,
    password VARCHAR(30)         NOT NULL,
    role     INT                 NOT NULL,
    picture  VARCHAR(200),
    PRIMARY KEY (id),
    FOREIGN KEY (role) REFERENCES roles (id)
);


/* many to many relation tables:*/
CREATE TABLE projectsubprojects
(
    projectid    INT NOT NULL,
    subprojectid INT NOT NULL,
    FOREIGN KEY (projectid) REFERENCES projects (id) ON DELETE CASCADE,
    FOREIGN KEY (subprojectid) REFERENCES subprojects (id) ON DELETE CASCADE
);

CREATE TABLE userprojects
(
    projectid INT NOT NULL,
    userid    INT NOT NULL,
    FOREIGN KEY (projectid) REFERENCES projects (id) ON DELETE CASCADE,
    FOREIGN KEY (userid) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE subprojecttasks
(
    subprojectid INT NOT NULL,
    taskid       INT NOT NULL,
    FOREIGN KEY (subprojectid) REFERENCES subprojects (id) ON DELETE CASCADE,
    FOREIGN KEY (taskid) REFERENCES tasks (id) ON DELETE CASCADE
);

CREATE TABLE usertasks
(
    userid INT NOT NULL,
    taskid INT NOT NULL,
    FOREIGN KEY (userid) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (taskid) REFERENCES tasks (id) ON DELETE CASCADE
);

-- Insert Roles
INSERT INTO roles (name)
VALUES ('Admin'),
       ('Project Manager'),
       ('Employee');

-- Insert Users
INSERT INTO users (name, email, password, role, picture)
VALUES ('Rebecca Black', 'Rebecca@example.com', 'password123', 1, 'Rebecca.jpg'),
       ('John Smith', 'John@example.com', 'password123', 2, 'John.jpg'),
       ('CharlieXcX', 'charlie@example.com', 'password123', 3, 'Charlie.jpg');

-- Insert Projects
INSERT INTO projects (name, description, timeBudget)
VALUES ('Website Redesign', 'Redesigning the company website.', 500),
       ('Mobile App', 'Developing the new company mobile app.', 800);

-- Insert Subprojects
INSERT INTO subprojects (name, description, timeBudget, completed, timeTaken, projectID)
VALUES ('UI Overhaul', 'Update the UI/UX of the website.', 200, false, NULL, 1),
       ('Backend API', 'Develop new APIs for the app.', 300, false, NULL, 2),
       ('App UI Design', 'Create new design layouts for the app.', 250, false, NULL, 2);

-- Insert Tasks
INSERT INTO tasks (name, description, timeBudget, completed, timeTaken, subProjectID)
VALUES ('Create Mockups', 'Create mockup screens for new website design.', 40, false, NULL, 1),
       ('Implement Login API', 'Develop authentication endpoints.', 60, false, NULL, 2),
       ('Build Profile Screen', 'Create profile page design for app.', 50, false, NULL, 3);

-- Populate projectsubprojects
INSERT INTO projectsubprojects (projectid, subprojectid)
VALUES (1, 1),
       (2, 2),
       (2, 3);

-- Populate userprojects
INSERT INTO userprojects (projectid, userid)
VALUES (1, 1),
       (1, 2),
       (2, 2),
       (2, 3);

-- Populate subprojecttasks
INSERT INTO subprojecttasks (subprojectid, taskid)
VALUES (1, 1),
       (2, 2),
       (3, 3);

-- Populate usertasks
INSERT INTO usertasks (userid, taskid)
VALUES (1, 1),
       (2, 2),
       (3, 3);
