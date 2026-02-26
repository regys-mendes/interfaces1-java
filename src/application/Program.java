package application;

import model.entities.CarRental;
import model.entities.Vehicle;
import model.services.BrazilTaxService;
import model.services.RentalService;
import model.services.TaxService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;


public class Program {
    static void main(String[] args) {

        DateTimeFormatter dmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        System.out.println("Entre com os dados do aluguel");
        System.out.println("Modelo do carro: ");
        String model = sc.nextLine();
        System.out.println("Retirada (dd/MM/yyyy hh:mm): ");
        LocalDateTime start = LocalDateTime.parse(sc.nextLine(), dmt);
        System.out.println("Retorno (dd/MM/yyyy hh:mm): ");
        LocalDateTime finish = LocalDateTime.parse(sc.nextLine(), dmt);

        CarRental carRental = new CarRental(finish, start, new Vehicle(model));

        System.out.println("Entre com o preço por hora: ");
        double pricePerHour = sc.nextDouble();

        System.out.println("Entre com o preço por dia: ");
        double pricePerDay = sc.nextDouble();

        TaxService taxService = new BrazilTaxService();
        RentalService rentalService = new RentalService(pricePerDay, pricePerHour,taxService);

        rentalService.processInvoice(carRental);

        System.out.println("FATURA: ");
        System.out.println("Basic Payment: " + String.format("%.2f", carRental.getInvoice().getBasicPayment()));
        System.out.println("Tax: " + String.format("%.2f", carRental.getInvoice().getTax()));
        System.out.println("Total Payment: " + String.format("%.2f", carRental.getInvoice().getTotalPayment()));

        sc.close();
    }
}
