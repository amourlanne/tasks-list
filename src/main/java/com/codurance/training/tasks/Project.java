package com.codurance.training.tasks;

import java.util.ArrayList;
import java.util.List;

public final class Project {
    private final String name;
    private List<Task> tasks;

    public Project(String name) {
        this.name = name;
        this.tasks = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(String.format("%s%n", this.name));

        tasks.forEach(task -> stringBuilder.append(String.format("    %s", task)));
        return stringBuilder.toString();
    }
}
