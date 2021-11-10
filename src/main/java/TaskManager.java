import org.apache.commons.lang3.ArrayUtils;
import pl.coderslab.ConsoleColors;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;


public class TaskManager {

    public static void main(String[] args) {
        selectAnOption();
    }

    public static String fileName() {
        String pathname = "tasks.csv";
        File file = new File(pathname);
        try {
            if (!file.exists()) {
                file.createNewFile();}
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pathname;
    }

    //liczy linie z wczytywanego pliku:
    public static int numberOflines() {
        File file = new File(fileName());
        int linesCounter = 0;
        try (Scanner scan = new Scanner(file)) {
            while (scan.hasNextLine()) {
                linesCounter++;
                scan.nextLine();}
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
                lines[i] = scan.nextLine();}
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return lines;
    }


    // wczytuje dane do tablicy dwuwymiarowej
    public static String[][] tasks() {
        int columnsCounter = 3;
        String[][] tasks = new String[arrayWithLines().length][columnsCounter];
        for (int i = 0; i < arrayWithLines().length; i++) {
            String[] columns = arrayWithLines()[i].split(", ");
            for (int j = 0; j < columnsCounter; j++) {
                tasks[i][j] = columns[j];}
        }
        return tasks;
    }

    //wybiera opcje i wykonuje zadania:
    public static void selectAnOption() {
        System.out.println(pl.coderslab.ConsoleColors.BLUE + "Please select an option:" + ConsoleColors.RESET);
        String[] options = {"add", "remove", "list", "exit"};
        Arrays.stream(options).forEach(System.out::println);
        Scanner scan = new Scanner(System.in);
        String optionChosen = scan.nextLine();

        switch (optionChosen) {
            case "add": {
                Scanner scan1 = new Scanner(System.in);
                Pattern datePattern = Pattern.compile("20\\d{2}-[0-1]\\d-\\d{2}");

                System.out.println("Please add task description:");
                String description = scan1.nextLine();
                System.out.println("Please add task due date (format: yyyy-mm-dd)");
                String dueDate = scan1.next();
                while (!datePattern.matcher(dueDate).matches()) {
                    System.out.println("Wrong date format! Please add task due date correctly (format: yyyy-mm-dd");
                    dueDate = scan1.nextLine();
                }

                System.out.println("Is your task important: true/false");
                while (!scan1.hasNextBoolean()) {
                    scan1.next();
                    System.out.println("Wrong answer format, please enter true or false");
                }
                boolean importance = scan1.nextBoolean();

                try (FileWriter fileWriter = new FileWriter("tasks.csv", true)) {
                    if (arrayWithLines().length == 0) {
                        fileWriter.append(description + ", " + dueDate + ", " + importance);
                    } else {
                        fileWriter.append("\n" + description + ", " + dueDate + ", " + importance);
                    }
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
                while (num >= numberOflines() || num < 0) {
                    if (numberOflines() == 0) {
                        System.out.println("List of tasks is empty. You cannot remove any task.");
                        selectAnOption();
                    } else if (num >= numberOflines() || num < 0){
                        System.out.println("Number out of range. Please select right number");
                        num = scan2.nextInt();
                    }
                }
                newLines = ArrayUtils.remove(arrayWithLines(), num);
                System.out.println("Value was successfully deleted.");

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
                if (numberOflines() == 0) {
                    System.out.println("List of tasks is empty");
                    selectAnOption();
                } else {
                    System.out.println("List of tasks:");
                    for (int i = 0; i < tasks().length; i++) {
                        System.out.print(i + " : ");
                        for (int j = 0; j < 3; j++) {
                            System.out.print(tasks()[i][j] + " ");
                        }
                        System.out.println();
                    }
                    selectAnOption();
                }
                break;
            }

            case "exit": {
                System.out.print(ConsoleColors.RED + "Bye, bye");
                System.exit(0);
                break;
            }

            default: {
                System.out.println("Option unidentified");
                selectAnOption();
            }

        }
    }
}

