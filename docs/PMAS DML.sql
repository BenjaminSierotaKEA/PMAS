USE
PMASDatabase;

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
INSERT INTO projects (name, description, timeBudget, deadline)
VALUES ('Website Redesign', 'Redesigning the company website.', 500, '2021-07-14'),
       ('Mobile App', 'Developing the new company mobile app.', 800, '2022-03-09');

-- Insert Subprojects
INSERT INTO subprojects (name, description, timeBudget, completed, timeTaken, projectID)
VALUES ('UI Overhaul', 'Update the UI/UX of the website.', 200, false, NULL, 1),
       ('Backend API', 'Develop new APIs for the app.', 300, false, NULL, 2),
       ('App UI Design', 'Create new design layouts for the app.', 250, false, NULL, 2);

-- Insert Tasks
INSERT INTO tasks (name, description, timeBudget, completed, timeTaken, deadline, subProjectID)
VALUES ('Create Mockups', 'Create mockup screens for new website design.', 40, false, NULL, '2023-11-17', 1),
       ('Implement Login API', 'Develop authentication endpoints.', 60, false, NULL, '2021-04-05', 2),
       ('Build Profile Screen', 'Create profile page design for app.', 50, false, NULL, '2024-08-29', 3);

-- Populate userprojects
INSERT INTO userprojects (projectid, userid)
VALUES (1, 1),
       (1, 2),
       (2, 2),
       (2, 3);

-- Populate subprojecttasks
-- INSERT INTO subprojecttasks (subprojectid, taskid)
-- VALUES (1, 1),
--        (2, 2),
--        (3, 3);

-- Populate usertasks
INSERT INTO usertasks (userid, taskid)
VALUES (1, 1),
       (2, 2),
       (3, 3);
