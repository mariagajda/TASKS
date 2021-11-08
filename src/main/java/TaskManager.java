import org.apache.commons.lang3.ArrayUtils;
import pl.coderslab.ConsoleColors;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class TaskManager {

    public static void main(String[] args) {
        numberOflines();
        arrayWithLines();
        columnsCounter();
        tasks();
        selectAnOption();
    }


    public static String fileName() {
        return "tasks.csv";
    }

    //liczy linie z wczytywanego pliku:
    public static int numberOflines() {
        File file = new File(fileName());
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


    // tworzy tablice jednowymiarową z liniami wczytanymi z pliku:
    public static String[] arrayWithLines() {
        File file = new File(fileName());
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
        int columnsCounter = 3;
        return columnsCounter;
    }

    public static String[][] tasks() {
// wczytuje dane do tablicy dwuwymiarowej
        String[][] tasks = new String[arrayWithLines().length][columnsCounter()];
        for (int i = 0; i < arrayWithLines().length; i++) {
            String[] columns = arrayWithLines()[i].split(", ");
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

        switch (optionChosen) {
            case "add": {
                Scanner scan1 = new Scanner(System.in);
                newTasks = new String[numberOflines() + 1][columnsCounter()];

                System.out.println("Please add task description:");
                String description = scan1.nextLine();
                System.out.println("Please add task due date");
                String dueDate = scan1.nextLine();
                System.out.println("Is your task important: true/false");
                String importance = scan1.nextLine();

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
                }
                try (FileWriter fileWriter = new FileWriter("tasks.csv", true)) {
                    fileWriter.append("\n" + description + ", " + dueDate + ", " + importance);
                } catch (IOException e) {
                    System.out.println("File cannot be updated");
                }
                selectAnOption();
                break;
            }
            case "remove": {
                Scanner scan2 = new Scanner(System.in);
                String newLines[];
                System.out.println("Please select number to remove");
                int num = scan2.nextInt();
                newLines = ArrayUtils.remove(arrayWithLines(), num);

                try (FileWriter fileWriter = new FileWriter("tasks.csv", false)) {
                    for (int i = 0; i < newLines.length; i++) {
                        if (i < newLines.length - 1) {
                            fileWriter.append(newLines[i] + "\n");
                        } else {
                            fileWriter.append(newLines[i]);
                        }
                    }
                } catch (IOException e) {
                    System.out.println("File cannot be updated");
                }
                selectAnOption();
                break;
            }

            case "list": {
                System.out.println("List of tasks:");
                for (int i = 0; i < tasks().length; i++) {
                    System.out.print(i + " : ");
                    for (int j = 0; j < columnsCounter(); j++) {
                        System.out.print(tasks()[i][j] + " ");
                    }
                    System.out.println();
                }
                selectAnOption();
                break;
            }
            case "exit": {
                System.out.print(ConsoleColors.RED + "Bye, bye");
                break;
            }
            default: {
                System.out.println("Option unidentified");
                selectAnOption();
            }

        }
    }
}

