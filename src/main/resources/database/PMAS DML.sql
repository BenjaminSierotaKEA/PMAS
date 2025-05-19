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
INSERT INTO projects (name, description, timeBudget, deadline, completed)
VALUES ('Website Redesign', 'Redesigning the company website.', 500, '2021-07-14', false),
       ('Mobile App', 'Developing the new company mobile app.', 800, '2022-03-09', false);

-- Insert Subprojects
INSERT INTO subprojects (name, description, timeBudget, completed, projectID)
VALUES ('UI Overhaul', 'Update the UI/UX of the website.', 200, false, 1),
       ('Backend API', 'Develop new APIs for the app.', 300, false, 2),
       ('App UI Design', 'Create new design layouts for the app.', 250, false, 2);

-- Insert Tasks
INSERT INTO tasks (name, description, priorityLevel, timeBudget, completed, deadline, subProjectID)
VALUES ('Create Mockups', 'Create mockup screens for new website design.', 'LOW', 40, false, '2023-11-17', 1),
       ('Implement Login API', 'Develop authentication endpoints.', 'MEDIUM', 60, false, '2021-04-05', 2),
       ('Build Profile Screen', 'Create profile page design for app.', 'HIGH', 50, false, '2024-08-29', 3),
        ('Create Structure for backend', 'Make packages ready', 'HIGH', 30, true, '2024-08-29', 3);

-- Populate userprojects
INSERT INTO userprojects (projectid, userid)
VALUES (1, 1),
       (1, 2),
       (2, 2),
       (2, 3);

-- Populate usertasks
INSERT INTO usertasks (userid, taskid)
VALUES (1, 1),
       (2, 2),
       (3, 3);
