package com.nationalcatalogapp.api;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class APIGISMTService {

    private String omsId;
    private String connectionId;
    private String cert;
    private String authToken;

    // Конструктор, который принимает данные для подключения к API
    public APIGISMTService(String omsId, String connectionId, String cert) {
        this.omsId = omsId;
        this.connectionId = connectionId;
        this.cert = cert;
    }

    // Метод для подключения к API и получения токена аутентификации
    public String authenticate() {
        String authToken = null;

        try {
            // Формируем URL для запроса авторизации
            String authUrl = "https://example-gis-mt-api-url/authenticate"; // Замените на актуальный URL для
                                                                            // авторизации

            URL url = new URL(authUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");

            // Добавляем необходимые параметры для авторизации
            conn.setRequestProperty("OmsId", omsId);
            conn.setRequestProperty("ConnectionId", connectionId);
            conn.setRequestProperty("Cert", cert);

            // Проверяем код ответа
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Читаем ответ
                Scanner scanner = new Scanner(conn.getInputStream());
                StringBuilder response = new StringBuilder();
                while (scanner.hasNext()) {
                    response.append(scanner.nextLine());
                }
                scanner.close();

                // Парсинг ответа для получения токена
                authToken = parseAuthToken(response.toString());
                System.out.println("Authentication successful. Token: " + authToken);
                this.authToken = authToken;
            } else {
                System.out.println("Failed to authenticate. Response code: " + responseCode);
            }

        } catch (IOException e) {
            System.out.println("Error during authentication: " + e.getMessage());
        }

        return authToken;
    }

    // Метод для получения списка артикулов через API ГИС-МТ
    public void fetchArticlesFromGisMt() {
        if (authToken == null) {
            System.out.println("You must authenticate first.");
            return;
        }

        try {
            // Формируем URL для запроса списка артикулов
            String articlesUrl = "https://example-gis-mt-api-url/articles"; // Замените на актуальный URL для получения
                                                                            // списка артикулов

            URL url = new URL(articlesUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // Добавляем токен авторизации в заголовок запроса
            conn.setRequestProperty("Authorization", "Bearer " + authToken);

            // Проверяем код ответа
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Читаем ответ
                Scanner scanner = new Scanner(conn.getInputStream());
                StringBuilder response = new StringBuilder();
                while (scanner.hasNext()) {
                    response.append(scanner.nextLine());
                }
                scanner.close();

                // Выводим список артикулов
                System.out.println("Articles from GIS-MT:");
                System.out.println(response.toString());
            } else {
                System.out.println("Failed to fetch articles. Response code: " + responseCode);
            }

        } catch (IOException e) {
            System.out.println("Error during fetching articles: " + e.getMessage());
        }
    }

    // Вспомогательный метод для парсинга токена из ответа (зависит от структуры
    // ответа API)
    private String parseAuthToken(String response) {
        // Предположим, что токен приходит в поле "auth_token"
        // Это нужно изменить в зависимости от структуры ответа API
        String token = null;
        if (response.contains("auth_token")) {
            token = response.substring(response.indexOf("auth_token") + 12,
                    response.indexOf(",", response.indexOf("auth_token")) - 1);
        }
        return token;
    }

    // Основной метод, который выполняет подключение к API, получая необходимые
    // параметры
    public void connectToGisMt() {
        // Выполнение подключения с использованием аутентификации
        authenticate();
    }
}
