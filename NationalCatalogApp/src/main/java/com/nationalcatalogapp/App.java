package com.nationalcatalogapp;

import com.nationalcatalogapp.api.APIGISMTService;
import com.nationalcatalogapp.models.Article;
import com.nationalcatalogapp.services.ArticleService;
import com.nationalcatalogapp.services.CSVService;
import com.nationalcatalogapp.services.UserService;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArticleService articleService = new ArticleService();
        UserService userService = new UserService();
        CSVService csvService = new CSVService();

        // Получаем данные для подключения к API от пользователя
        System.out.println("Enter your omsId:");
        String omsId = scanner.nextLine();

        System.out.println("Enter your connectionId:");
        String connectionId = scanner.nextLine();

        System.out.println("Enter your certificate (cert):");
        String cert = scanner.nextLine();

        // Создаем экземпляр сервиса API ГИС-МТ с введенными данными
        APIGISMTService apiService = new APIGISMTService(omsId, connectionId, cert);
        apiService.connectToGisMt();

        // Главное меню
        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Manage Articles");
            System.out.println("2. Manage Users");
            System.out.println("3. Import/Export CSV");
            System.out.println("4. Fetch Articles from GIS-MT");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    manageArticles(scanner, articleService);
                    break;
                case 2:
                    manageUsers(scanner, userService);
                    break;
                case 3:
                    handleCSV(scanner, articleService, csvService);
                    break;
                case 4:
                    apiService.fetchArticlesFromGisMt();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // Подменю для управления артикулами
    private static void manageArticles(Scanner scanner, ArticleService articleService) {
        while (true) {
            System.out.println("\nArticle Management:");
            System.out.println("1. Add Article");
            System.out.println("2. Edit Article");
            System.out.println("3. Delete Article");
            System.out.println("4. View All Articles");
            System.out.println("5. Back to Main Menu");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter Article ID: ");
                    String id = scanner.nextLine();
                    System.out.print("Enter Article Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Article Description: ");
                    String description = scanner.nextLine();
                    System.out.print("Enter Article Price: ");
                    double price = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline
                    Article article = new Article(id, name, description, price);
                    articleService.addArticle(article);
                    break;
                case 2:
                    System.out.print("Enter Article ID to Edit: ");
                    String editId = scanner.nextLine();
                    System.out.print("Enter New Name: ");
                    String newName = scanner.nextLine();
                    System.out.print("Enter New Description: ");
                    String newDescription = scanner.nextLine();
                    System.out.print("Enter New Price: ");
                    double newPrice = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline
                    articleService.editArticle(editId, newName, newDescription, newPrice);
                    break;
                case 3:
                    System.out.print("Enter Article ID to Delete: ");
                    String deleteId = scanner.nextLine();
                    articleService.deleteArticle(deleteId);
                    break;
                case 4:
                    System.out.println("Articles:");
                    articleService.getAllArticles().forEach(System.out::println);
                    break;
                case 5:
                    return; // Вернуться в главное меню
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // Подменю для управления пользователями
    private static void manageUsers(Scanner scanner, UserService userService) {
        while (true) {
            System.out.println("\nUser Management:");
            System.out.println("1. Register User");
            System.out.println("2. Login User");
            System.out.println("3. Delete User");
            System.out.println("4. View All Users");
            System.out.println("5. Back to Main Menu");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter Username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter Password: ");
                    String password = scanner.nextLine();
                    System.out.print("Enter Role (admin/user): ");
                    String role = scanner.nextLine();
                    userService.registerUser(username, password, role);
                    break;
                case 2:
                    System.out.print("Enter Username: ");
                    String loginUsername = scanner.nextLine();
                    System.out.print("Enter Password: ");
                    String loginPassword = scanner.nextLine();
                    userService.loginUser(loginUsername, loginPassword);
                    break;
                case 3:
                    System.out.print("Enter Username to Delete: ");
                    String deleteUsername = scanner.nextLine();
                    userService.deleteUser(deleteUsername);
                    break;
                case 4:
                    System.out.println("Users:");
                    userService.getAllUsers().forEach(System.out::println);
                    break;
                case 5:
                    return; // Вернуться в главное меню
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // Подменю для работы с CSV
    private static void handleCSV(Scanner scanner, ArticleService articleService, CSVService csvService) {
        while (true) {
            System.out.println("\nCSV Import/Export:");
            System.out.println("1. Export Articles to CSV");
            System.out.println("2. Import Articles from CSV");
            System.out.println("3. Back to Main Menu");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter file path for export: ");
                    String exportPath = scanner.nextLine();
                    csvService.exportArticlesToCSV(exportPath, articleService.getAllArticles());
                    break;
                case 2:
                    System.out.print("Enter file path for import: ");
                    String importPath = scanner.nextLine();
                    articleService.getAllArticles().addAll(csvService.importArticlesFromCSV(importPath));
                    break;
                case 3:
                    return; // Вернуться в главное меню
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
