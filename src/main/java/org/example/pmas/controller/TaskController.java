package org.example.pmas.controller;

import org.example.pmas.model.SubProject;
import org.example.pmas.model.Task;
import org.example.pmas.model.enums.PriorityLevel;
import org.example.pmas.service.TaskService;
import org.example.pmas.util.SessionHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Set;

@Controller
@RequestMapping("projects/{projectId}/subprojects/{subprojectId}/tasks")
public class TaskController {
    private final TaskService taskService;
    private final SessionHandler sessionHandler;

    public TaskController(TaskService taskService, SessionHandler sessionHandler) {
        this.taskService = taskService;
        this.sessionHandler = sessionHandler;
    }

    // Read all tasks from subprojectId
    @GetMapping("/all")
    public String readAll(@PathVariable(value = "projectId") int projectId,
                          @PathVariable(value = "subprojectId") int subprojectId,
                          Model model) {
        boolean loggedIn = sessionHandler.isNotAdmin();
        if (loggedIn) {
            // Adds tasks to HTML
            model.addAttribute("tasks", taskService.getTasksBySubProjectID(subprojectId));
        }

        model.addAttribute("ProjectManager", sessionHandler.isUserProjectManager());
        model.addAttribute("user", sessionHandler.getCurrentUser());
        model.addAttribute("allowAccess", loggedIn);
        return "task-all";
    }

    @GetMapping("new")
    public String getCreateTaskPage(@PathVariable(value = "projectId") int projectId,
                                    @PathVariable(value = "subprojectId") int subprojectId,
                                    Model model) {
        boolean loggedIn = sessionHandler.isNotAdmin();
        if (loggedIn) {
            if (!model.containsAttribute("task"))
                model.addAttribute("task", new Task());
            getUsersOnProjectAndPriority(model, projectId, subprojectId);
        }

        model.addAttribute("allowAccess", loggedIn);
        return "task-new";
    }

    // RedirectAttribute explained
    // stores the attributes in a flashmap (which is internally maintained in the users session and
    // removed once the next redirected request gets fulfilled)
    @PostMapping("create")
    public String createTask(@ModelAttribute Task task,
                             @RequestParam(name = "userIds", required = false) Set<Integer> userIDs,
                             @PathVariable(value = "projectId") int projectId,
                             @PathVariable(value = "subprojectId") int subprojectId,
                             RedirectAttributes redirectAttributes) {
        if (task == null) throw new IllegalArgumentException("Controller error: Something wrong with task.");
        if (userIDs == null || userIDs.isEmpty()) {
            redirectAttributes.addFlashAttribute("task", task);
            redirectAttributes.addFlashAttribute("errorUser", "Please choose at least one user to assign the task to.");
            return "redirect:/projects/" + projectId + "/subprojects/" + subprojectId + "/tasks/new";
        }
        if (sessionHandler.isNotAdmin()) {
            task.setSubProject(new SubProject(subprojectId));
            taskService.create(task, userIDs);
        }

        return "redirect:/projects/" + projectId + "/subprojects/" + subprojectId + "/tasks/all";
    }

    @PostMapping("{id}/delete")
    public String deleteTask(@PathVariable(value = "id") int id,
                             @PathVariable(value = "projectId") int projectId,
                             @PathVariable(value = "subprojectId") int subprojectId) {
        if (id <= 0) throw new IllegalArgumentException("Controller error: Something wrong with taskid: " + id);
        if (sessionHandler.isNotAdmin()) {

            taskService.delete(id);
        }
        return "redirect:/projects/" + projectId + "/subprojects/" + subprojectId + "/tasks/all";
    }

    @GetMapping("{id}/edit")
    public String readSelected(@PathVariable(value = "id") int id,
                               @PathVariable(value = "projectId") int projectId,
                               @PathVariable(value = "subprojectId") int subprojectId,
                               Model model) {
        if (id < 0) throw new IllegalArgumentException("Controller error: Something wrong with taskid");

        boolean loggedIn = sessionHandler.isNotAdmin();
        if (loggedIn) {

            Task task = taskService.readSelected(id);
            model.addAttribute("task", task);
            getUsersOnProjectAndPriority(model, projectId, subprojectId);
            model.addAttribute("ProjectManager", sessionHandler.isUserProjectManager());
        }
        model.addAttribute("allowAccess", loggedIn);
        return "task-update";
    }

    // RedirectAttribute explained
    // stores the attributes in a flashmap (which is internally maintained in the users session and
    // removed once the next redirected request gets fulfilled)
    @PostMapping("update")
    public String updateTask(@ModelAttribute Task task,
                             @RequestParam(name = "userIds", required = false) Set<Integer> userIDs,
                             @PathVariable(value = "projectId") int projectId,
                             @PathVariable(value = "subprojectId") int subprojectId,
                             RedirectAttributes redirectAttributes) {
        if (task == null) throw new IllegalArgumentException("Controller error: Something wrong with task.");
        if (userIDs == null || userIDs.isEmpty()) {
            redirectAttributes.addFlashAttribute("task", task);
            redirectAttributes.addFlashAttribute("errorName", "Please choose at least one user to assign the task to.");
            return "redirect:/projects/" + projectId + "/subprojects/" + subprojectId + "/tasks/" + task.getId() + "/edit";
        }

        if (sessionHandler.isNotAdmin()) {
            task.setSubProject(new SubProject(subprojectId));
            taskService.update(task, userIDs);
        }
        return "redirect:/projects/" + projectId + "/subprojects/" + subprojectId + "/tasks/all";
    }

    private void getUsersOnProjectAndPriority(Model model, int projectId, int subprojectId) {
        model.addAttribute("subprojectId", subprojectId);
        model.addAttribute("projectId", projectId);
        model.addAttribute("users", taskService.getAllUsersOnProject(projectId));
        model.addAttribute("priorities", PriorityLevel.values());
    }
}
