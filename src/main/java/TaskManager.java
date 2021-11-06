import pl.coderslab.ConsoleColors;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class TaskManager {

    public static void main(String[] args) {
        tasksDownload();
        selectAnOption();
    }

    public static void tasksDownload() {
        File file = new File("tasks.csv");
        int linesCounter = 0;

//liczy linie z wczytywanego pliku:
        try (Scanner scan1 = new Scanner(file)) {
            while (scan1.hasNextLine()) {
                scan1.nextLine();
                linesCounter++;
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(linesCounter);

// liczy kolumny z wczytywanego pliku i tworzy tablice jednowymiarową z liniami wczytanymi z pliku:
        String[] lines = new String[linesCounter];
        try (Scanner scan2 = new Scanner(file)) {
            while (scan2.hasNextLine()) {
                for (int i = 0; i < linesCounter; i++) {
                    lines[i] = scan2.nextLine();
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        int columnsCounter = 0;
        for (int i = 0; i < linesCounter; i++) {
            String[] columns = lines[i].split(", ");
            columnsCounter = columns.length;
        }
        System.out.println(columnsCounter);

// wczytuje dane do tablicy dwuwymiarowej:
        String[][] tasks = new String[linesCounter][columnsCounter];
        try (Scanner scan2 = new Scanner(file)) {
            for (int i = 0; i < tasks.length; i++) {
                String[] columns = lines[i].split(", ");
                for (int j = 0; j < columns.length; j++) {
                    tasks[i][j] = columns[j];
                    System.out.println(tasks[i][j]);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

    }


    public static void selectAnOption() {
        System.out.println(pl.coderslab.ConsoleColors.BLUE + "Please select an option:");
        String[] options = {"add", "remove", "list", "exit"};
        for (int i = 0; i < options.length; i++) {
            System.out.println(ConsoleColors.WHITE + options[i]);
        }
        Scanner scan = new Scanner(System.in);
        String optionChosen = scan.nextLine();
        System.out.println(optionChosen);
    }

}