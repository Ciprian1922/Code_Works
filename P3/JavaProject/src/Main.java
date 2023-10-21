import java.io.*;
import java.util.Scanner;

class Product {
    String productname, productcomp;
    int productid, price, Qnt;
}

public class ProductManagementSystem {
    public static void main(String[] args) {
        welcome();
        login();
    }

    public static void welcome() {
        System.out.println("Welcome to Product Management System");
        System.out.println("Press any key to continue...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }

    public static void login() {
        int attempts = 0;
        String username = "user";
        String password = "pass";

        while (attempts < 3) {
            System.out.print("Enter username: ");
            Scanner scanner = new Scanner(System.in);
            String inputUsername = scanner.next();
            System.out.print("Enter password: ");
            String inputPassword = scanner.next();

            if (username.equals(inputUsername) && password.equals(inputPassword)) {
                System.out.println("Login successful!");
                menu();
                return;
            } else {
                System.out.println("Login unsuccessful. Please try again.");
                attempts++;
            }
        }

        if (attempts >= 3) {
            System.out.println("You have entered the wrong username and password too many times.");
        }
    }

    public static void menu() {
        while (true) {
            System.out.println("\nProduct Management System Menu");
            System.out.println("1. Add Products");
            System.out.println("2. Delete Products");
            System.out.println("3. Search Products");
            System.out.println("4. Read Items");
            System.out.println("5. Edit Items");
            System.out.println("6. Exit");

            System.out.print("Enter your choice (1-6): ");
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addItem();
                    break;
                case 2:
                    deleteProduct();
                    break;
                case 3:
                    searchItem();
                    break;
                case 4:
                    readItems();
                    break;
                case 5:
                    editItem();
                    break;
                case 6:
                    System.out.println("Exiting the program.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number from 1 to 6.");
            }
        }
    }

    public static void addItem() {
        Product st = new Product();
        int index, valid = 0;
        char c = 0;

        do {
            System.out.println("Enter Product Details:");

            int ID;
            Scanner scanner = new Scanner(System.in);
            System.out.print("Product Code: ");
            ID = scanner.nextInt();
            st.productid = ID;

            System.out.print("Product Name: ");
            st.productname = scanner.next();
            st.productname = st.productname.substring(0, 1).toUpperCase() + st.productname.substring(1);

            for (index = 0; index < st.productname.length(); index++) {
                if (Character.isLetter(st.productname.charAt(index))) {
                    valid = 1;
                } else {
                    valid = 0;
                    break;
                }
            }

            if (valid == 0) {
                System.out.println("Name contains invalid characters. Please enter again.");
                continue;
            }

            System.out.print("Product Company: ");
            st.productcomp = scanner.next();
            st.productcomp = st.productcomp.substring(0, 1).toUpperCase() + st.productcomp.substring(1);

            for (index = 0; index < st.productcomp.length(); index++) {
                if (Character.isLetter(st.productcomp.charAt(index))) {
                    valid = 1;
                } else {
                    valid = 0;
                    break;
                }
            }

            if (valid == 0) {
                System.out.println("Company name contains invalid characters. Please enter again.");
                continue;
            }

            do {
                System.out.print("Price [10-5000] Rupees: ");
                st.price = scanner.nextInt();
                if (st.price < 10 || st.price > 5000) {
                    System.out.println("Invalid price. Please enter a price between 10 and 5000.");
                }
            } while (st.price < 10 || st.price > 5000);

            do {
                System.out.print("Quantity [1-500]: ");
                st.Qnt = scanner.nextInt();
                if (st.Qnt < 1 || st.Qnt > 500) {
                    System.out.println("Invalid quantity. Please enter a quantity between 1 and 500.");
                }
            } while (st.Qnt < 1 || st.Qnt > 500);

            try {
                FileWriter fw = new FileWriter("ProductFile.txt", true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter pw = new PrintWriter(bw);
                pw.println(st.productname + " " + st.productcomp + " " + st.price + " " + st.productid + " " + st.Qnt);
                pw.close();
                System.out.println("Product added successfully.");

                System.out.print("Press 'Enter' to add more items or any other key to go to the main menu: ");
                c = scanner.next().charAt(0);

            } catch (IOException e) {
                System.err.println("Error writing to file.");
            }
        } while (c == '\r');
    }

    public static void searchItem() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter name to search: ");
        String target = scanner.next();
        target = target.substring(0, 1).toUpperCase() + target.substring(1);

        try {
            FileReader fr = new FileReader("ProductFile.txt");
            BufferedReader br = new BufferedReader(fr);
            String line;
            boolean found = false;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts[0].equals(target)) {
                    System.out.println("Record found:");
                    System.out.println("Product Name: " + parts[0]);
                    System.out.println("Product Company: " + parts[1]);
                    System.out.println("Price: " + parts[2]);
                    System.out.println("Product Code: " + parts[3]);
                    System.out.println("Product Quantity: " + parts[4]);
                    found = true;
                    break;
                }
            }

            if (!found) {
                System.out.println("No record found.");
            }

            br.close();
        } catch (IOException e) {
            System.err.println("Error reading file.");
        }

        System.out.print("Press any key to go to the main menu.");
        scanner.nextLine();
        scanner.nextLine();
    }

    public static void deleteProduct() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter name to delete: ");
        String target = scanner.next();
        target = target.substring(0, 1).toUpperCase() + target.substring(1);

        try {
            FileReader fr = new FileReader("ProductFile.txt");
            BufferedReader br = new BufferedReader(fr);
            FileWriter fw = new FileWriter("TempFile.txt", true);
            PrintWriter pw = new PrintWriter(fw);

            String line;
            boolean found = false;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                if (!parts[0].equals(target)) {
                    pw.println(line);
                } else {
                    found = true;
                }
            }

            br.close();
            pw.close();

            if (found) {
                File oldFile = new File("ProductFile.txt");
                File newFile = new File("TempFile.txt");
                oldFile.delete();
                newFile.renameTo(oldFile);
                System.out.println("Record deleted.");
            } else {
                System.out.println("Record not found.");
            }
        } catch (IOException e) {
            System.err.println("Error reading/writing files.");
        }

        System.out.print("Press any key to go to the main menu.");
        scanner.nextLine();
        scanner.nextLine();
    }

    public static void readItems() {
        try {
            FileReader fr = new FileReader("ProductFile.txt");
            BufferedReader br = new BufferedReader(fr);

            String line;
            int q = 0;
            int i;

            System.out.println("\nProduct List");
            System.out.println("Product Name\tProduct Company\tPrice\tProduct Code\tProduct Quantity");
            System.out.println("-----------------------------------------------------------------------");

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                System.out.print(parts[0] + "\t" + parts[1] + "\t" + parts[2] + "\t" + parts[3] + "\t" + parts[4] + "\n");
            }

            br.close();
        } catch (IOException e) {
            System.err.println("Error reading file.");
        }

        System.out.print("Press any key to go to the main menu.");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }

    public static void editItem() {
        Scanner scanner = new Scanner(System.in);
        int id;

        System.out.print("Enter Product Code for edit: ");
        id = scanner.nextInt();

        try {
            FileReader fr = new FileReader("ProductFile.txt");
            BufferedReader br = new BufferedReader(fr);

            FileWriter fw = new FileWriter("TempFile.txt", true);
            PrintWriter pw = new PrintWriter(fw);

            String line;
            boolean found = false;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                Product st = new Product();
                st.productname = parts[0];
                st.productcomp = parts[1];
                st.price = Integer.parseInt(parts[2]);
                st.productid = Integer.parseInt(parts[3]);
                st.Qnt = Integer.parseInt(parts[4]);

                if (id == st.productid) {
                    found = true;
                    System.out.println("Record Found:");
                    System.out.println("Product Name: " + st.productname);
                    System.out.println("Product Company: " + st.productcomp);
                    System.out.println("Price: " + st.price);
                    System.out.println("Product Code: " + st.productid);
                    System.out.println("Product Quantity: " + st.Qnt);

                    System.out.println("\nNew Record:");
                    int valid;

                    System.out.print("New Product Name: ");
                    st.productname = scanner.next();
                    st.productname = st.productname.substring(0, 1).toUpperCase() + st.productname.substring(1);

                    for (int index = 0; index < st.productname.length(); index++) {
                        if (Character.isLetter(st.productname.charAt(index)) || Character.isDigit(st.productname.charAt(index))) {
                            valid = 1;
                        } else {
                            valid = 0;
                            break;
                        }
                    }

                    if (valid == 0) {
                        System.out.println("New name contains invalid characters. Please enter again.");
                        continue;
                    }

                    System.out.print("New Product Company: ");
                    st.productcomp = scanner.next();
                    st.productcomp = st.productcomp.substring(0, 1).toUpperCase() + st.productcomp.substring(1);

                    for (int index = 0; index < st.productcomp.length(); index++) {
                        if (Character.isLetter(st.productcomp.charAt(index)) || Character.isDigit(st.productcomp.charAt(index))) {
                            valid = 1;
                        } else {
                            valid = 0;
                            break;
                        }
                    }

                    if (valid == 0) {
                        System.out.println("New company name contains invalid characters. Please enter again.");
                        continue;
                    }

                    do {
                        System.out.print("New Price [10-5000] Rupees: ");
                        st.price = scanner.nextInt();
                        if (st.price < 10 || st.price > 5000) {
                            System.out.println("Invalid price. Please enter a price between 10 and 5000.");
                        }
                    } while (st.price < 10 || st.price > 5000);

                    do {
                        System.out.print("New Quantity [1-500]: ");
                        st.Qnt = scanner.nextInt();
                        if (st.Qnt < 1 || st.Qnt > 500) {
                            System.out.println("Invalid quantity. Please enter a quantity between 1 and 500.");
                        }
                    } while (st.Qnt < 1 || st.Qnt > 500);

                    System.out.print("Press 'y' to edit the existing record or any other key to cancel: ");
                    char edit = scanner.next().charAt(0);

                    if (edit == 'y' || edit == 'Y') {
                        pw.println(st.productname + " " + st.productcomp + " " + st.price + " " + st.productid + " " + st.Qnt);
                        System.out.println("Your record is successfully edited.");
                    }
                } else {
                    pw.println(line);
                }
            }

            br.close();
            pw.close();

            if (!found) {
                System.out.println("This product doesn't exist.");
            } else {
                File oldFile = new File("ProductFile.txt");
                File newFile = new File("TempFile.txt");
                oldFile.delete();
                newFile.renameTo(oldFile);
            }
        } catch (IOException e) {
            System.err.println("Error reading/writing files.");
        }

        System.out.print("Press any key to go to the main menu.");
        scanner.nextLine();
        scanner.nextLine();
    }

}
