package gym;

import java.io.*;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static MEMBERSHIP_STATUS status;
    public static final Path customerFile = Path.of("customers.txt");


    public static void main(String[] args) {
        List<Customer> customerList = getListWithCustomers(customerFile);
        boolean shouldContinue = true;

        while (shouldContinue) {
            int customerID = 0;
            String response = "";
            Scanner sc = new Scanner(System.in);
            System.out.println("Search for member with a full name or a 10 digit social security number: " + "" +
                    "\nTo exit, write \"Stop\".");
            if (sc.hasNextLine()) {
                String input = sc.nextLine();
                if (input.equalsIgnoreCase("stop")) {
                    shouldContinue = false;
                } else {
                    for (int i = 0; i < customerList.size(); i++) {
                        response = hasMembership(input,customerList.get(i));
                        customerID = i;
                        if (status != MEMBERSHIP_STATUS.NOT_A_MEMBER) {
                            break;
                        }
                    }
                    System.out.println(response);
                    if (status == MEMBERSHIP_STATUS.ACTIVE_MEMBER) {
                        registerVisit(getVisitInformation(customerID, customerList));
                    }
                }
            }
        }
    }

    public static List<Customer> getListWithCustomers(Path filePath) {
        List<Customer> list = new ArrayList<>();
        try(Scanner sc = new Scanner(customerFile)) {
            while (sc.hasNext()) {
                String line = sc.nextLine();
                int index = line.indexOf(",");
                String socialSecurityNumber = line.substring(0, index);
                String name = line.substring(index+1).trim();
                line = sc.nextLine();
                LocalDate lastPayment = LocalDate.parse(line);
                list.add(new Customer(socialSecurityNumber, name, lastPayment));
            }
        } catch (IOException ioException) {
            System.out.println("Could not read file.");
        } catch (Exception e) {
            System.out.println("Something went wrong.");
        }
        return list;
    }

    public static String getVisitInformation(int customerID, List<Customer> customerList) {
        return "Name: " + customerList.get(customerID).getName() + "\nSocial Security number: "
                + customerList.get(customerID).getSocialSecurityNumber() +
                "\nDate: " + LocalDate.now() + "\nTime: " + LocalTime.now().truncatedTo(ChronoUnit.SECONDS) +"\n\n";
    }

    public static void registerVisit(String visitInformation) {
        try(BufferedWriter out = new BufferedWriter(new FileWriter("Visit_Information.txt", true))){
            out.write(visitInformation);
        } catch (Exception e) {
            System.out.println("Could not write to file.");
        }
    }

    public static String hasMembership(String term, Customer customer) {
        LocalDate today = LocalDate.now();
        String message = "";
        if (isPreviousCustomer(term, customer)) {
            if (customer.getLastPaymentDate().isAfter(today.minusYears(1))) {
                status = MEMBERSHIP_STATUS.ACTIVE_MEMBER;
                message = "This customer has an active membership.";
            } else if (customer.getLastPaymentDate().isBefore(today.minusYears(1))) {
                status = MEMBERSHIP_STATUS.OLD_MEMBER;
                message = "This customers membership has ran out.";
            }
        } else {
            status = MEMBERSHIP_STATUS.NOT_A_MEMBER;
            message = "This person has never been a customer.";
        }
        return message;
    }

    public static boolean isPreviousCustomer(String term, Customer customer){
        if (customer.getName().equalsIgnoreCase(term)) {
            return true;
        } else if (customer.getSocialSecurityNumber().equalsIgnoreCase(term)) {
            return true;
        }
        else return false;
    }
}
