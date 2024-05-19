
# Телефонная книга
Данная программа написана для выполнения домашнего задания по изучению языка программирования java.
## Работа программы:
### ПОдключаем БД и HashMap.
```
import java.io.*;
import java.util.*;
```

### 1. Создаём класс телефонной книги b HashMap "contacts".
```
public class phonebook {
    private static HashMap<String, HashSet<String>> contacts = new HashMap<>();
```
### 2. Эта часть кода отвечает за подгрузку БД.
```
    public static void main(String[] args) {
        loadPhoneBook();
```
### 3. Создаём сканер для считывания пользовательского ввода и выводим варианты действий
```
        Scanner scanner = new Scanner(System.in);
        int choice = 0;

        while (choice != 4) {
            System.out.println("\nВыбор действия:");
            System.out.println("1. Добавить контакт");
            System.out.println("2. Просмотр контактов");
            System.out.println("3. Удалить контакт");
            System.out.println("4. Сохранить и выйти");         
            choice = scanner.nextInt();
            scanner.nextLine();
```
### 4. В данном блоке кода реализовано взаимодействие пользователя с программой. Непосредственно выбор действия.
```
            switch (choice) {
                case 1:
                    System.out.print("Введите имя: ");
                    String name = scanner.nextLine();
                    System.out.print("Введите номер телефона: ");
                    String phoneNumber = scanner.nextLine();
                    addContact(name, phoneNumber);
                    break;
                case 2:
                    displayContacts();
                    break;
                case 3:
                    System.out.print("Введите имя: ");
                    String delName = scanner.nextLine();
                    delContact(contacts, delName);
                    break;
                case 4:
                    savePhoneBook();
                    System.out.println("Изменения сохранены. Досвидания!");
                    choice = 4;
                    break;
                default:
                    System.out.println("Некорректный выбор! Пожалуйста попробуйте ещё.");
                    break;
            }
        }

        scanner.close();
    }
```

### 5. Реализация добавления контакта.
```
    private static void addContact(String name, String phoneNumber) {
        contacts.computeIfAbsent(name, k -> new HashSet<>()).add(phoneNumber);
    }
```
### 6. Вывод данных (всех контактов).

    private static void displayContacts() {
        contacts.entrySet().stream()
                .sorted((e1, e2) -> Integer.compare(e2.getValue().size(), e1.getValue().size()))
                .forEach(e -> {
                    System.out.print("Name: " + e.getKey());
                    e.getValue().forEach(phone -> System.out.println("   Phone number: " + phone));
                });
    }


### 7. Функция из пункта 2.

```
    private static void loadPhoneBook() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("phonebook.dat"))) {
            contacts = (HashMap<String, HashSet<String>>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Phone book file not found or could not be read. Starting with empty phone book.");
        }
    }
``` 
### 8. Функция для удаления контакта.

```
    public static void delContact(HashMap<String, HashSet<String>> contacts2, String name) {
        if (contacts2.containsKey(name)) {
            contacts2.remove(name);
            System.out.println("Контакт успешно удалён.");
        } else {
            System.out.println("Контакт с таким именем не существует.");
        }
    }
```
### 9. Сохранение изменений в БД и завершение программы(P.S. в пользовательском меню данный пункт(4) прерывает цикл while и завершает программу).

```
    private static void savePhoneBook() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("phonebook.dat"))) {
            oos.writeObject(contacts);
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении!");
            e.printStackTrace();
        }
    }
}
