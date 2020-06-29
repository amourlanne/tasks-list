package com.codurance.training.tasks;

import java.util.ArrayList;
import java.util.List;

public final class Project {
    private final String slug;
    private List<Task> tasks;

    public Project(String slug) {
        this.slug = slug;
        this.tasks = new ArrayList<>();
    }

    public String getSlug() {
        return slug;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(this.slug);

        tasks.forEach(task -> stringBuilder.append(String.format("    %s)", task)));
        return stringBuilder.toString();
    }
}
