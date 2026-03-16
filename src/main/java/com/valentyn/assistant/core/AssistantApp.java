package com.valentyn.assistant.core;

import com.valentyn.assistant.actions.ActionExecutor;
import com.valentyn.assistant.commands.CommandParser;
import com.valentyn.assistant.model.Command;

import java.util.Scanner;

public class AssistantApp {
    private final CommandParser commandParser = new CommandParser();
    private final ActionExecutor actionExecutor = new ActionExecutor();

    public void start() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ассистент запущен.");
        System.out.println("Примеры команд:");
        System.out.println("- открой браузер");
        System.out.println("- открой telegram");
        System.out.println("- открой vscode");
        System.out.println("- открой калькулятор");
        System.out.println("- открой ютуб");
        System.out.println("- начни запись");
        System.out.println("- останови запись");
        System.out.println("- выход");

        boolean running = true;

        while (running) {
            System.out.print("\nВведите команду: ");
            String input = scanner.nextLine();

            Command command = commandParser.parse(input);
            running = actionExecutor.execute(command);
        }

        scanner.close();
    }
}