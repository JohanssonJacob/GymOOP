package gym;

import java.time.LocalDate;

public class Customer {
        String socialSecurityNumber;
        String name;
        LocalDate lastPaymentDate;

    public Customer(String socialSecurityNumber, String name, LocalDate lastPaymentDate) {
        this.socialSecurityNumber = socialSecurityNumber;
        this.name = name;
        this.lastPaymentDate = lastPaymentDate;
    }

    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    public String getName() {
        return name;
    }

    public LocalDate getLastPaymentDate() {
        return lastPaymentDate;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", socialSecurityNumber='" + socialSecurityNumber + '\'' +
                ", lastPaymentDate='" + lastPaymentDate + '\'' +
                '}';
    }
}
