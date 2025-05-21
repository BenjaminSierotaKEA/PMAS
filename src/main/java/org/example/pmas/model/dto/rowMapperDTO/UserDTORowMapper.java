package org.example.pmas.model.dto.rowMapperDTO;

import org.example.pmas.model.*;
import org.example.pmas.model.enums.PriorityLevel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


//Mapper for user with tasks, and projects
public class UserDTORowMapper implements RowMapper<User> {


    //we're using this Map, to ensure we will only get back one user.
    //this was to avoid making a million group concats and splitting many String arrays and assigning them individually to the task attributes
    private final Map<Integer, User> userMap;

    // prevents duplicate Task instances when the result set contains multiple rows for the same task
    //(may up for change, since tasks do not have unique contraints in the db)
    private final Map<Integer, Task> taskMap = new HashMap<>();

    public UserDTORowMapper(Map <Integer, User> userMap) {
        this.userMap = userMap;
    }


    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {


        //here we fint the User objects field attributes:
        int userId = rs.getInt("user_id");

        // Reuse user if we've already created it, and add start adding tasks to it
        User user = userMap.get(userId);
        //if we havent:
        if (user == null) {
            user = new User();
            user.setUserID(userId);
            user.setName(rs.getString("user_name"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setPicture(rs.getString("picture"));


        //here we set the Role object, based on the values of the user in the DB
        Role role = new Role();
        role.setId(rs.getInt("role_id"));
        role.setName(rs.getString("role_name"));
        user.setRole(role);

        //initialize list objects:
        user.setTasks(new ArrayList<>());
        user.setProjects(new ArrayList<>());


        //putting out newly parsed information here:
        userMap.put(userId,user);
        }

        // ----------------- TASK ---------------------
        //check to see if a task is assigned to the user
        Integer taskId = (Integer) rs.getObject("task_id");
        if (taskId != null && !taskMap.containsKey(taskId)) {
            //if not, we make a task object with the contents of the db
            Task task = new Task();
            task.setId(taskId);
            task.setName(rs.getString("task_name"));
            task.setDescription(rs.getString("description"));

            //What is the string value of the priority field:
            String priorityRaw = rs.getString("priorityLevel");

            //depending on what we find we set the enum here:
            if (priorityRaw != null) {

                switch (priorityRaw.toUpperCase()) {
                    case "LOW" -> task.setPriorityLevel(PriorityLevel.LOW);
                    case "MEDIUM" -> task.setPriorityLevel(PriorityLevel.MEDIUM);
                    case "HIGH" -> task.setPriorityLevel(PriorityLevel.HIGH);
                    default -> task.setPriorityLevel(PriorityLevel.LOW);

                }
            }

            //filling out more stuff, about time
            task.setTimeBudget(rs.getDouble("timeBudget"));
            task.setCompleted(rs.getBoolean("completed"));
            if (rs.getDate("deadline") != null) {
                task.setDeadline(rs.getDate("deadline").toLocalDate());
            }

            // --- Users on Task ---
            //here we go and find the users assigned to tasks.
            //the format id ID:Name,ID:Name. This our many to many relationships.

            //here we get the string with the users and the ID's:
            String rawPairs = rs.getString("task_user_pairs");

            if (rawPairs != null) {
                //set to hold user obejcts assigned to task:
                Set<User> taskUsers = new HashSet<>();

                //Seperate users, creating a string array:
                String[] pairs = rawPairs.split(",");

                //for this array, seperate the ids from the user names:
                for (String pair : pairs) {
                    String[] parts = pair.split(":");

                    //check to make sure we have both id and name now:
                    if (parts.length == 2) {
                        int id = Integer.parseInt(parts[0]);
                        String name = parts[1];
                        taskUsers.add(new User(id, name));
                    }
                }
                task.setUsers(taskUsers);
            }


            // --- SubProject ---
            //assigning subproject to a task object. We use this for URL generation though the user-page
            Integer subprojectId = (Integer) rs.getObject("subproject_id");
            if (subprojectId != null) {
                SubProject sub = new SubProject();
                sub.setId(subprojectId);
                sub.setName(rs.getString("subproject_name"));
                sub.setProjectID(rs.getInt("project_id"));
                task.setSubProject(sub);
            }

            taskMap.put(taskId, task);
            //finally add task to the user object's list of tasks
            user.getTasks().add(task);
        }

        // --- PROJECT ---
        //if the user is assigned to a project, we set it here:
        Integer projectId = (Integer) rs.getObject("project_id");
        if (projectId != null && user.getProjects().stream().noneMatch(p -> p.getId() == projectId)) {
            Project p = new Project();
            p.setId(projectId);
            p.setName(rs.getString("project_name"));
            user.getProjects().add(p);
        }


        //returns Map object of user:
        return user;
    }
}
