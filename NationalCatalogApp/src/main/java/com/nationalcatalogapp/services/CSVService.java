package com.nationalcatalogapp.services;

import com.nationalcatalogapp.models.Article;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVService {

    // Метод для экспорта артикулов в CSV
    public void exportArticlesToCSV(String filePath, List<Article> articles) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {
            // Заголовок CSV файла
            String[] header = { "ID", "Name", "Description", "Price" };
            writer.writeNext(header);

            // Запись каждого артикула в файл
            for (Article article : articles) {
                String[] articleData = { article.getId(), article.getName(), article.getDescription(),
                        String.valueOf(article.getPrice()) };
                writer.writeNext(articleData);
            }

            System.out.println("Export to CSV successful: " + filePath);
        } catch (IOException e) {
            System.out.println("Error while exporting to CSV: " + e.getMessage());
        }
    }

    // Метод для импорта артикулов из CSV
    public List<Article> importArticlesFromCSV(String filePath) {
        List<Article> articles = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] nextLine;
            boolean isFirstLine = true;

            while ((nextLine = reader.readNext()) != null) {
                if (isFirstLine) {
                    // Пропустить первую строку (заголовок)
                    isFirstLine = false;
                    continue;
                }

                // Парсинг данных строки
                String id = nextLine[0];
                String name = nextLine[1];
                String description = nextLine[2];
                double price = Double.parseDouble(nextLine[3]);

                Article article = new Article(id, name, description, price);
                articles.add(article);
            }

            System.out.println("Import from CSV successful: " + filePath);
        } catch (IOException | CsvValidationException | NumberFormatException e) {
            System.out.println("Error while importing from CSV: " + e.getMessage());
        }

        return articles;
    }
}
