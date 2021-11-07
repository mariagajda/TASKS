import org.apache.commons.lang3.ArrayUtils;
import pl.coderslab.ConsoleColors;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class TaskManager {

    public static void main(String[] args) {
        tasks();
        selectAnOption();
    }


    //liczy linie z wczytywanego pliku:
    public static int numberOflines() {
        File file = new File("tasks.csv");
        int linesCounter = 0;

        try (Scanner scan = new Scanner(file)) {
            while (scan.hasNextLine()) {
                linesCounter++;
                scan.nextLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        return linesCounter;
    }


    // liczy kolumny z wczytywanego pliku i tworzy tablice jednowymiarową z liniami wczytanymi z pliku:
    public static String[] tableWithLines() {
        File file = new File("tasks.csv");
        String[] lines = new String[numberOflines()];
        try (Scanner scan = new Scanner(file)) {
            for (int i = 0; i < lines.length; i++) {
                lines[i] = scan.nextLine();
            }

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return lines;
    }

    //liczy kolumny:
    public static int columnsCounter() {
        int columnsCounter = 0;
        for (int i = 0; i < numberOflines(); i++) {
            String[] columns = tableWithLines()[i].split(", ");
            columnsCounter = columns.length;
        }
        return columnsCounter;
    }

    public static String[][] tasks() {
// wczytuje dane do tablicy dwuwymiarowej
        String[][] tasks = new String[tableWithLines().length][columnsCounter()];
        for (int i = 0; i < tableWithLines().length; i++) {
            String[] columns = tableWithLines()[i].split(", ");
            for (int j = 0; j < columnsCounter(); j++) {
                tasks[i][j] = columns[j];
            }
        }
        return tasks;
    }


    public static void selectAnOption() {
        System.out.println(pl.coderslab.ConsoleColors.BLUE + "Please select an option:");
        String[] options = {"add", "remove", "list", "exit"};
        for (int i = 0; i < options.length; i++) {
            System.out.println(ConsoleColors.WHITE + options[i]);
        }
        Scanner scan = new Scanner(System.in);
        String optionChosen = scan.nextLine();
        String[][] newTasks;

        while (!optionChosen.equals("quit")) {
            if (optionChosen.equals("add")) {
                Scanner scan1 = new Scanner(System.in);
                newTasks = new String[numberOflines() + 1][columnsCounter()];

                System.out.println("Please add task description:");
                String description = scan1.next();
                System.out.println("Please add task due date");
                String dueDate = scan1.next();
                System.out.println("Is your task important: true/false");
                String importance = scan1.next();

                for (int i = 0; i < newTasks.length; i++) {
                    if (i < newTasks.length - 1) {
                        for (int j = 0; j < columnsCounter(); j++) {
                            newTasks[i][j] = tasks()[i][j];
                        }
                    } else if (i == newTasks.length - 1) {
                        newTasks[i][0] = description;
                        newTasks[i][1] = dueDate;
                        newTasks[i][2] = importance;
                    }
                }/*
                try (FileWriter fileWriter = new FileWriter("task.csv", true)) {
                    fileWriter.append("\n" + description + ", " + dueDate + ", " + importance);
                } catch (IOException e) {
                    System.out.println("File cannot be updated");
                }*/
                selectAnOption();
            }
            if (optionChosen.equals("remove")) {
                Scanner scan2 = new Scanner(System.in);
                System.out.println("Please select number to remove");
                int num = scan2.nextInt();
                newTasks = ArrayUtils.remove(tasks(), num);
                System.out.println(Arrays.toString(newTasks));
                selectAnOption();
            }
            if (optionChosen.equals("list")) {
                System.out.println("List of tasks:");
                for (int i = 0; i < tasks().length; i++) {
                    System.out.print(i + " : ");
                    for (int j = 0; j < columnsCounter(); j++) {
                        System.out.print(tasks()[i][j] + " ");
                    }
                    System.out.println();
                }
                selectAnOption();
            }

            if (optionChosen.equals("exit")) {
                System.out.println(ConsoleColors.RED + "Bye, bye");
            }
        }

    }
}

