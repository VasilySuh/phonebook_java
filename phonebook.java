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