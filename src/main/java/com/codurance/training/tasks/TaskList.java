package com.codurance.training.tasks;

import com.codurance.training.tasks.exceptions.NotEnoughArgumentException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;

public final class TaskList implements Runnable {
    private static final String QUIT = "quit";

    private final List<Project> projects = new ArrayList<>();
    private final BufferedReader in;
    private final PrintWriter out;

    private long lastId = 0;

    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        new TaskList(in, out).run();
    }

    public TaskList(BufferedReader reader, PrintWriter writer) {
        this.in = reader;
        this.out = writer;
    }

    public void run() {
        while (true) {
            out.print("> ");
            out.flush();
            String command;
            try {
                command = in.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (command.equals(QUIT)) {
                break;
            }
            try {
                execute(command);
            } catch (NotEnoughArgumentException e) {
                out.println(e.getMessage());
            }
        }
    }

    private void execute(String commandLine) throws NotEnoughArgumentException {
        String[] commandRest = commandLine.split(" ", 2);
        try {
            String command = commandRest[0];
            switch (command) {
                case "show":
                    show();
                    break;
                case "add":
                    add(commandRest[1]);
                    break;
                case "check":
                    check(commandRest[1]);
                    break;
                case "uncheck":
                    uncheck(commandRest[1]);
                    break;
                case "help":
                    help();
                    break;
                default:
                    commandError(command);
                    break;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new NotEnoughArgumentException();
        }
    }

    private void show() {

        for (Project project: projects) {
            out.println(project);
        }
    }

    private void add(String commandLine) {
        String[] subCommandRest = commandLine.split(" ", 2);
        String subCommand = subCommandRest[0];

        switch (subCommand) {
            case "project":
                addProject(subCommandRest[1]);
                break;
            case "task":
                String[] projectTask = subCommandRest[1].split(" ", 2);
                addTask(projectTask[0], projectTask[1]);
                break;
            default:
                commandError(commandLine);
                break;
        }
    }

    private void addProject(String name) {

        if(projects.stream()
                .anyMatch(streamedProject -> streamedProject.getName().equals(name))) {
            out.printf("A project with the slug \"%s\" already exists.", name);
            out.println();
            return;
        }

        projects.add(new Project(name));
    }

    private void addTask(String projectName, String description) {

        Optional<Project> project = projects.stream()
                .filter(streamedProject -> streamedProject.getName().equals(projectName))
                .findFirst();

        if (!project.isPresent()) {
            out.printf("Could not find a project with the name \"%s\".", projectName);
            out.println();
            return;
        }

        project.get()
                .addTask(new Task(nextId(), description, false));
    }

    private void check(String idString) {
        setDone(idString, true);
    }

    private void uncheck(String idString) {
        setDone(idString, false);
    }

    private void setDone(String idString, boolean done) {
        int id = Integer.parseInt(idString);

        for (Project project: projects) {
            for (Task task: project.getTasks()) {
                if (task.getId() == id) {
                    task.setDone(done);
                    return;
                }
            }
        }

        out.printf("Could not find a task with an ID of %d.", id);
        out.println();
    }

    private void help() {
        out.println("Commands:");
        out.println("  show");
        out.println("  add project <project name>");
        out.println("  add task <project name> <task description>");
        out.println("  check <task ID>");
        out.println("  uncheck <task ID>");
        out.println();
    }

    private void commandError(String command) {
        out.printf("I don't know what the command \"%s\" is.", command);
        out.println();
    }

    private long nextId() {
        return ++lastId;
    }
}
