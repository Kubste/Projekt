package simulation;

import simulation.map.creator.ISimulationMapCreator;
import simulation.map.creator.SimulationMapCreator;
import simulation.unit.creator.IUnitCreator;
import simulation.unit.creator.UnitCreator;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;


public class Main {

    /**
     * Główna metoda przeprowadzająca działanie programu
     *
     * @param args argumenty przekazywane na początku działaniu programu
     */

    private static final String FILE_LOCATION = "symulacja_BazaDanych.xlsx";
    private static final String SHEET_NAME = "Rezultat symulacji";

    public static void main(String[] args) {

        try {

            Scanner scanner = new Scanner(System.in);

            System.out.print("Podaj rozmiar mapy w osi X: ");
            int mapSizeX = scanner.nextInt();
            System.out.print("Podaj rozmiar mapy w osi Y: ");
            int mapSizeY = scanner.nextInt();

            if(mapSizeX <= 0 || mapSizeY <= 0) {
                System.out.println("Błąd! Rozmiar mapy musi być większy od 0!");
                return;
            }

            System.out.print("Podaj ilość jednostek czołgów: ");
            int numberTank = scanner.nextInt();
            System.out.print("Podaj ilość jednostek piechoty: ");
            int numberInfantry = scanner.nextInt();
            System.out.print("Podaj ilość jednostek artylerii: ");
            int numberArtillery = scanner.nextInt();

            if(numberTank < 0 || numberInfantry < 0 || numberArtillery < 0) {
                System.out.println("Błąd! Liczba jednostek nie może być ujemna!");
                return;
            }

            if(2*(numberTank + numberInfantry + numberArtillery) > mapSizeX * mapSizeY) {
                System.out.println("Błąd! Liczba jednostek jest zbyt duża!");
                return;
            }

            System.out.print("Podaj maksymalną liczbę iteracji: ");
            int maxIterations = scanner.nextInt();
            System.out.println();

            if(maxIterations <= 0) {
                System.out.println("Błąd! Maksymalna liczba iteracji musi być większa od 0!");
                return;
            }

            ISimulationMapCreator mapCreator = new SimulationMapCreator(mapSizeX, mapSizeY);
            IUnitCreator unitCreator = new UnitCreator(numberTank, numberTank, numberInfantry, numberInfantry, numberArtillery, numberArtillery);

            Random random = new Random();

            Simulation simulation = new Simulation(mapCreator, unitCreator, random.nextLong(), maxIterations + 1);
            int iterations = simulation.runSimulation();

            System.out.println("KONIEC SYMULACJI");

            if (iterations == maxIterations - 1) System.out.println("Remis!");
            else if (simulation.isAnyoneAliveA()) System.out.println("Wygrała drużyna A!");
            else if (simulation.isAnyoneAliveB()) System.out.println("Wygrała drużyna B!");
            else System.out.println("Remis!");

            System.out.println("Symulacja trwała: " + (iterations + 1) + " iteracji");
            saveSimulationResult(iterations, maxIterations, simulation.isAnyoneAliveA(), simulation.isAnyoneAliveB());

        } catch(InputMismatchException e) {
            System.out.println("Błąd! Wprowadzane dane muszą być liczbami całkowitymi!");
        }
    }

    /**
     * Metoda zwraca wartość int zależnie od wyniku symulacji
     * @param maxIterations
     * @param iterations
     * @param isAnyoneAliveA
     * @param isAnyoneAliveB
     */
    static int whoWon(int maxIterations, int iterations, boolean isAnyoneAliveA, boolean isAnyoneAliveB) {
        if (iterations == maxIterations - 1) return 3;
        else if(isAnyoneAliveA == true) return 1;
        else if(isAnyoneAliveB == true) return 2;
        else return 3;
    }

    /**
     * Metoda zapisuje rezultat symulacji w pliku .xlsx
     * @param iterations
     * @param maxIterations
     * @param isAnyoneAliveA
     */
    static void saveSimulationResult(int iterations, int maxIterations, boolean isAnyoneAliveA, boolean isAnyoneAliveB) {

        Workbook workbook = getWorkbook(FILE_LOCATION);

        Sheet sheet = workbook.getSheet(SHEET_NAME);

        Row dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
        dataRow.createCell(0).setCellValue(iterations + 1);
        dataRow.createCell(1).setCellValue(maxIterations);
        int Winner = whoWon(maxIterations, iterations, isAnyoneAliveA, isAnyoneAliveB);
        switch (Winner) {
            case 1:
                dataRow.createCell(2).setCellValue("Druzyna A");
                break;
            case 2:
                dataRow.createCell(2).setCellValue("Druzyna B");
                break;
            case 3:
                dataRow.createCell(2).setCellValue("Remis");
                break;
        }

        for (int i = 0; i < 3; i++) {
            sheet.autoSizeColumn(i);
        }

        try (FileOutputStream fos = new FileOutputStream(FILE_LOCATION)) {
            workbook.write(fos);
            workbook.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Rezultat symulacji został pomyślnie zapisany");
    }

    /**
     * Metoda sprawdza czy istnieje arkusz i jeśli trzeba tworzy go
     * @param fileLocation
     * @return
     */
    private static Workbook getWorkbook(String fileLocation) {

        if (!Files.exists(Path.of(fileLocation))) {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet(SHEET_NAME);

            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Iteracje");
            headerRow.createCell(1).setCellValue("Limit Iteracji");
            headerRow.createCell(2).setCellValue("Zwyciezca");
            return workbook;
        } else {
            try (FileInputStream file = new FileInputStream(fileLocation)) {
                return new XSSFWorkbook(file);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

