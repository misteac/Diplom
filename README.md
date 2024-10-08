## Данный дипломный проект позволяет реализовать управление артикулами в национальном каталоге Честного знака
## Программа подключается к личному кабинету пользователя по API и вносить корректировки в данные артикула
## Основной класс — App.java
## Это главный класс, который управляет запуском программы и выводит консольное меню для взаимодействия с пользователем. Вот что он делает:
##    1. Подключение к API:
##        ◦ Программа запрашивает у пользователя ввод параметров omsId, connectionId и cert (сертификат).
##       ◦ Эти параметры передаются в сервис для подключения к API ГИС-МТ через объект APIGISMTService.
## APIGISMTService apiService = new APIGISMTService(omsId, connectionId, cert);
## apiService.connectToGisMt();
##    2. Главное меню: Программа запускает бесконечный цикл, который выводит основное меню с вариантами действий:
##        ◦ Управление артикулами.
##        ◦ Управление пользователями.
##        ◦ Импорт/экспорт данных через CSV.
##        ◦ Получение списка артикулов через API ГИС-МТ.
##       ◦ Выход из программы.
## Пользователь выбирает действие, и программа вызывает соответствующие методы.
## while (true) {
##    System.out.println("Menu:");
##    System.out.println("1. Manage Articles");
##    System.out.println("2. Manage Users");
##    System.out.println("3. Import/Export CSV");
##    System.out.println("4. Fetch Articles from GIS-MT");
##    System.out.println("5. Exit");
## }
## Управление артикулами — manageArticles()
## Этот метод отвечает за операции с артикулами, такие как добавление, редактирование, удаление и просмотр всех артикулов. При выборе этого пункта меню программа запрашивает у пользователя данные, такие как ID артикула, название, описание и цена, и передает их в методы сервиса ArticleService для обработки.
## Пример добавления артикула:
## System.out.print("Enter Article ID: ");
## String id = scanner.nextLine();
## System.out.print("Enter Article Name: ");
## String name = scanner.nextLine();
## System.out.print("Enter Article Description: ");
## String description = scanner.nextLine();
## System.out.print("Enter Article Price: ");
## double price = scanner.nextDouble();
## Article article = new Article(id, name, description, price);
## articleService.addArticle(article);
## Управление пользователями — manageUsers()
## Этот метод работает с пользователями системы. Он позволяет:
##    • Регистрировать новых пользователей.
##    • Авторизовывать их.
##   • Удалять пользователей.
##    • Просматривать список всех пользователей.
## Пример регистрации пользователя:
## System.out.print("Enter Username: ");
## tring username = scanner.nextLine();
## System.out.print("Enter Password: ");
## String password = scanner.nextLine();
## System.out.print("Enter Role (admin/user): ");
## String role = scanner.nextLine();
## userService.registerUser(username, password, role);
## Работа с CSV — handleCSV()
## Этот метод позволяет пользователю импортировать и экспортировать данные артикулов в CSV-файл. Программа запрашивает у пользователя путь к файлу, и далее вызывается метод из CSVService, который либо сохраняет артикулы в файл, либо загружает их из файла.
## Пример экспорта артикулов:
## System.out.print("Enter file path for export: ");
## String exportPath = scanner.nextLine();
## csvService.exportArticlesToCSV(exportPath, articleService.getAllArticles());
## Получение артикулов из API ГИС-МТ — fetchArticlesFromGisMt()
## Этот метод использует сервис API ГИС-МТ для выполнения GET-запроса, который получает список артикулов из системы ГИС-МТ. Перед выполнением запроса программа проверяет, был ли успешно получен токен аутентификации. Если да, то выполняется запрос на получение данных, и список артикулов выводится в консоль.
## Пример использования API для получения списка артикулов:
## apiService.fetchArticlesFromGisMt();
## APIGISMTService.java — сервис для работы с API ГИС-МТ
## Этот класс управляет подключением и взаимодействием с API ГИС-МТ. Вот что он делает:
##    1. Аутентификация — authenticate(): Этот метод выполняет POST-запрос к API для аутентификации пользователя. Он отправляет omsId, connectionId и cert, и в случае успешного запроса получает токен для дальнейшей работы с API.
## String authUrl = "https://example-gis-mt-api-url/authenticate"; // URL авторизации
## conn.setRequestProperty("OmsId", omsId);
## conn.setRequestProperty("ConnectionId", connectionId);
## conn.setRequestProperty("Cert", cert);
##    2. Получение списка артикулов — fetchArticlesFromGisMt(): Этот метод выполняет GET-запрос для получения списка артикулов. Запрос отправляется с использованием токена аутентификации, который был получен на этапе логина. Если запрос успешен, данные выводятся в консоль.
## conn.setRequestProperty("Authorization", "Bearer " + authToken);
## rticleService.java — управление артикулами
## Этот сервис управляет логикой работы с артикулами. В нем есть следующие методы:
##    • addArticle() — добавление артикула.
##    • deleteArticle() — удаление артикула по ID.
##    • editArticle() — редактирование данных артикула.
##    • getArticleById() — поиск артикула по ID.
##    • getAllArticles() — получение списка всех артикулов.
## Пример:
## public void addArticle(Article article) {
##    articles.add(article);
## }
## UserService.java — управление пользователями
## тот сервис управляет пользователями, предоставляя следующие методы:
##    • registerUser() — регистрация нового пользователя.
##    • loginUser() — авторизация пользователя.
##    • deleteUser() — удаление пользователя по имени.
##    • getAllUsers() — получение всех пользователей.
## Пример:
## public void registerUser(String username, String password, String role) {
##    User newUser = new User(username, password, role);
##    users.add(newUser);
## }
## CSVService.java — работа с CSV
## Этот сервис работает с CSV-файлами:
##    • exportArticlesToCSV() — экспортирует артикулы в CSV-файл.
##    • importArticlesFromCSV() — импортирует артикулы из CSV-файла.
## Пример:
## public void exportArticlesToCSV(String filePath, List<Article> articles) {
##    // Логика экспорта данных в файл
## }
## В целом:
##    • App.java отвечает за интерфейс программы и связывает логику разных сервисов.
##    • APIGISMTService.java управляет взаимодействием с внешним API ГИС-МТ.
##    • ArticleService.java и UserService.java управляют данными внутри программы.
##    • CSVService.java отвечает за работу с файлами.
