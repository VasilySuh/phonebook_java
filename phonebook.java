// import java.util.HashMap;
// import java.util.Map;
// import java.util.Scanner;
//import java.io.*;

// public class phonebook{
//     public static void main(String[] args){
//         HashMap<String, String> contactBook = new HashMap<>();
//         // System.out.println("Что необходимо сделать?\n1) Просмотр контактов \n2) Добавить контакт \n3) Удалить контакт\n4) Найти контакт \n5) Редактировать контакт");
        
//         String choice;
//         Scanner scanner = new Scanner(System.in);
//         do {
//             System.out.println("Меню:");
//             System.out.println("1. Просмотр контактов");
//             System.out.println("2. Добавить контакт");
//             System.out.println("3. Удалить контакт");
//             System.out.println("4. Найти контакт");
//             System.out.println("5. Редактировать контакт");
//             System.out.println("6. Выйти");
//             System.out.print("Выберите действие: ");
//             choice = scanner.nextLine();
        
//             switch (choice) {
//                 case "1":
//                     showAllContacts(contactBook);
//                     break;
//                 case "2":
//                     System.out.print("Введите имя контакта: ");
//                     String name = scanner.nextLine();
//                     System.out.print("Введите номер телефона: ");
//                     String phoneNumber = scanner.nextLine().replaceAll("[^0-9]", ""); // очистка от лишних символов
                    
//                     contactBook.put(name, phoneNumber);
//                     System.out.println("Контакт успешно добавлен!");
                    
//                     System.out.println("1. Добавить еще контакт");
//                     System.out.println("2. Вернуться в главное меню");
//                     choice = scanner.nextLine();
//                     break;
//                 case "3":
//                     System.out.println("Введите имя контакта для удаления:");
//                     String delName = scanner.nextLine();
//                     delContact(contactBook, delName);
//                     break;
//                 case "4":
//                     System.out.println("Введите имя контакта для поиска:");
//                     String findName = scanner.nextLine();
//                     findContact(contactBook, findName);
//                     break;
//                 case "5":
//                     System.out.println("Введите имя контакта для редактирования:");
//                     String editName = scanner.nextLine();
//                     editContact(contactBook, editName);
//                     break;
//                 case "6":
//                     System.out.println("Выход из программы");
//                     break;
//                 default:
//                     System.out.println("Неверный выбор. Пожалуйста, попробуйте снова.");
//             }
//         } while (!choice.equals("6"));
        
//         scanner.close();
//     }
// // //Вывод контактов
//     public static void showAllContacts(HashMap<String, String> contactBook) {
//     for(Map.Entry<String, String> entry : contactBook.entrySet()) {
//         String name = entry.getKey();
//         String phoneNumber = entry.getValue();
//         System.out.println("Name: " + name + ", Phone Number: " + phoneNumber);
//         }
//     }
//     // Добавить контакт
//     public static void addContact(HashMap<String, String> contactBook, String name, String phoneNumber) {
//         if (!contactBook.containsKey(name)) {
//             contactBook.put(name, phoneNumber);
//             System.out.println("Контакт успешно добавлен.");
//         } else {
//             System.out.println("Контакт с таким именем уже существует.");
//         }
//     }
//     //Удалить контакт
    // public static void delContact(HashMap<String, String> contactBook, String name) {
    //     if (contactBook.containsKey(name)) {
    //         contactBook.remove(name);
    //         System.out.println("Контакт успешно удалён.");
    //     } else {
    //         System.out.println("Контакт с таким именем не существует.");
    //     }
    // }
//     //Найти контакт
//     public static void findContact(HashMap<String, String> contactBook, String name) {
//         if (contactBook.containsKey(name)) {
//             String phoneNumber = contactBook.get(name);
//             System.out.println("Номер телефона для контакта '" + name + "': " + phoneNumber);
//         } 
//         else {
//             System.out.println("Контакт не найден");
//         }
//     }
//     //Редактировать контакт
//     public static void editContact(HashMap<String, String> contactBook, String name) {
//         if (contactBook.containsKey(name)) {
//             Scanner editScanner = new Scanner(System.in);
//             System.out.println("Введите новый номер телефона для контакта " + name + ":");
//             String newPhoneNumber = editScanner.nextLine();
//             contactBook.put(name, newPhoneNumber);
//             System.out.println("Информация о контакте " + name + " успешно изменена.");
//             editScanner.close();
//          } 
//          else {
//              System.out.println("Контакт не найден");
//         }
//     }
// }


import java.io.*;
import java.util.*;

public class phonebook {
    private static HashMap<String, HashSet<String>> contacts = new HashMap<>();

    public static void main(String[] args) {
        loadPhoneBook();

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

    private static void addContact(String name, String phoneNumber) {
        contacts.computeIfAbsent(name, k -> new HashSet<>()).add(phoneNumber);
    }

    private static void displayContacts() {
        contacts.entrySet().stream()
                .sorted((e1, e2) -> Integer.compare(e2.getValue().size(), e1.getValue().size()))
                .forEach(e -> {
                    System.out.print("Name: " + e.getKey());
                    e.getValue().forEach(phone -> System.out.println("   Phone number: " + phone));
                });
    }

    private static void loadPhoneBook() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("phonebook.dat"))) {
            contacts = (HashMap<String, HashSet<String>>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Phone book file not found or could not be read. Starting with empty phone book.");
        }
    }

    public static void delContact(HashMap<String, HashSet<String>> contacts2, String name) {
        if (contacts2.containsKey(name)) {
            contacts2.remove(name);
            System.out.println("Контакт успешно удалён.");
        } else {
            System.out.println("Контакт с таким именем не существует.");
        }
    }

    private static void savePhoneBook() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("phonebook.dat"))) {
            oos.writeObject(contacts);
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении!");
            e.printStackTrace();
        }
    }
}