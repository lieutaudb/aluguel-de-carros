package org.example.application;

import org.example.model.entities.CarRental;
import org.example.model.entities.Vehicle;
import org.example.model.services.BrazilTaxService;
import org.example.model.services.RentalService;

import java.sql.SQLOutput;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) throws ParseException {

        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        DateTimeFormatter fmt =  DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        System.out.println("Entre com os dados para aluguel: ");
        System.out.print("Modelo do carro: ");
        String carModel = sc.nextLine();
        System.out.print("Retirada (dd/MM/yyyy HH:mm): ");
        LocalDateTime start = LocalDateTime.parse(sc.nextLine(), fmt);
        System.out.print("Devolução (dd/MM/yyyy HH:mm): ");
        LocalDateTime finish = LocalDateTime.parse(sc.nextLine(), fmt);

        CarRental cr = new CarRental(start, finish, new Vehicle(carModel));

        System.out.print("Preço por hora: ");
        double pricePerHour = sc.nextDouble();
        System.out.print("Preço da diaria: ");
        double pricePerDay = sc.nextDouble();

        RentalService rentalService = new RentalService(pricePerDay, pricePerHour, new BrazilTaxService());

        rentalService.processInvoice(cr);

        System.out.println("RESUMO:");
        System.out.println("Valor do aluguel: " + String.format("%.2f", cr.getInvoice().getBasicPayment()));
        System.out.println("Taxas: " + String.format("%.2f", cr.getInvoice().getTax()));
        System.out.println("Pagamento total: " + String.format("%.2f", cr.getInvoice().getTotalPayment()));

        sc.close();
    }
}