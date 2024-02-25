import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class StudentInformationSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int option = 0;
        int countN = 0;

        try {
            BufferedReader fileReader = new BufferedReader(new FileReader("Example.txt"));
            String line;
            while ((line = fileReader.readLine()) != null) {
                countN++;
            }
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (option != 4) {
            System.out.println("\nAvailable operations: ");
            System.out.println("1. Add New Students");
            System.out.println("2. Student Login");
            System.out.println("3. Faculty Login");
            System.out.println("4. Exit");
            System.out.print("Enter option: ");
            option = scanner.nextInt();

            switch (option) {
                case 1:
                    try {
                        System.out.print("Enter the number of students: ");
                        int n = scanner.nextInt();
                        countN += n;

                        BufferedWriter writer = new BufferedWriter(new FileWriter("Example.txt", true));
                        scanner.nextLine(); 
                        for (int i = 0; i < n; i++) {
                            System.out.println("Enter details for student " + (i + 1) + ":");
                            System.out.print("Registration Number: ");
                            String regNo = scanner.nextLine();
                            writer.write(regNo + "\t");

                            System.out.print("Name: ");
                            String name = scanner.nextLine();
                            writer.write(name + "\t");

                            System.out.print("Proctor ID: ");
                            String proctor = scanner.nextLine();
                            writer.write(proctor + "\t");

                            System.out.print("Age: ");
                            String age = scanner.nextLine();
                            writer.write(age + "\t");

                            System.out.print("Date of Birth (DOB): ");
                            String dob = scanner.nextLine();
                            writer.write(dob + "\t");

                            System.out.print("Father's Name: ");
                            String fatherName = scanner.nextLine();
                            writer.write(fatherName + "\t");

                            System.out.print("Contact Number: ");
                            String contactNumber = scanner.nextLine();
                            writer.write(contactNumber + "\t");

                            System.out.print("Address: ");
                            String address = scanner.nextLine();
                            writer.write(address + "\t");

                            // Default marks
                            writer.write("00\t00\n");
                        }
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;

                case 2:
                    try {
                        System.out.print("Enter your registration number: ");
                        String regNo = scanner.next();
                        BufferedReader reader = new BufferedReader(new FileReader("Example.txt"));
                        String line;
                        boolean found = false;
                        while ((line = reader.readLine()) != null) {
                            String[] tokens = line.split("\t");
                            if (tokens[0].equals(regNo)) {
                                System.out.println("\nRegistration Number: " + tokens[0]);
                                System.out.println("Name: " + tokens[1]);
                                System.out.println("Proctor ID: " + tokens[2]);
                                double mark1 = Double.parseDouble(tokens[8]);
                                double mark2 = Double.parseDouble(tokens[9]);
                                double sum = mark1 + mark2;
                                double average = sum / 2;
                                System.out.println("CSE1001 mark: " + mark1);
                                System.out.println("CSE1002 mark: " + mark2);
                                System.out.println("Sum: " + sum);
                                System.out.println("Average: " + average);
                                found = true;
                                break;
                            }
                        }
                        reader.close();
                        if (!found) {
                            System.out.println("No such registration number found!");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;

                case 3:
                    try {
                        BufferedReader reader = new BufferedReader(new FileReader("Example.txt"));
                        System.out.print("Enter your proctor ID: ");
                        String proctorID = scanner.next();
                        String data;
                        boolean found = false;
                        while ((data = reader.readLine()) != null) {
                            String[] tokens = data.split("\t");
                            if (tokens.length >= 5 && tokens[2].equals(proctorID)) {
                                System.out.println("\nRegistration Number: " + tokens[0]);
                                System.out.println("Name: " + tokens[1]);
                                System.out.println("CSE1001 mark: " + tokens[8]);
                                System.out.println("CSE1002 mark: " + tokens[9]);
                                found = true;
                            }
                        }
                        if (!found) {
                            System.out.println("No students under this proctor ID!");
                        }
                        reader.close();

                        // Faculty input marks
                        scanner.nextLine();
                        System.out.print("Enter student registration number to input marks: ");
                        String regNo = scanner.nextLine();
                        reader = new BufferedReader(new FileReader("Example.txt"));
                        StringBuilder fileContent = new StringBuilder();
                        while ((data = reader.readLine()) != null) {
                            String[] tokens = data.split("\t");
                            if (tokens[0].equals(regNo)) {
                                System.out.print("Enter CSE1001 mark: ");
                                String mark1 = scanner.next();
                                scanner.nextLine(); // Consume newline
                                System.out.print("Enter CSE1002 mark: ");
                                String mark2 = scanner.next();
                                scanner.nextLine(); // Consume newline
                                tokens[8] = mark1;
                                tokens[9] = mark2;
                            }
                            fileContent.append(String.join("\t", tokens)).append("\n");
                        }
                        reader.close();

                        BufferedWriter writer = new BufferedWriter(new FileWriter("Example.txt"));
                        writer.write(fileContent.toString());
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;

                case 4:
                    System.out.println("Exiting the program...");
                    break;
                default:
                    System.out.println("Invalid option! Please try again.");
                    break;
            }
        }
        scanner.close();
    }
}
