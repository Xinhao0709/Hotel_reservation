import api.HotelResource;
import model.IRoom;
import model.Reservation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Scanner;

public class MainMenu {

    private static HotelResource hotelResource = HotelResource.getInstance();
    private static  String DEFAULT_DATE_FORMAT = "MM/dd/yyyy";

    public static void mainMenu() {
        String command ="?";
        Scanner scanner = new Scanner(System.in);

        printMain();

        try {
            do {
                command = scanner.nextLine();

                if(command.length() == 1) {
                    switch (command.charAt(0)) {
                        case '1':
                            findAndReserveRoom();
                            break;
                        case '2':
                            seeMyReservations();
                            break;
                        case '3':
                            createAccount();
                            break;
                        case '4':
                            Admin.admin();
                            break;
                        case '5':
                            System.out.println("Exiting the program......\n");
                            break;
                        default:
                            System.out.println("Unknown action\n");
                            break;

                    }
                }
            } while (command.charAt(0) != '5' || command.length() != 1);
        } catch (StringIndexOutOfBoundsException ex) {
            System.out.println("Empty input\n Exiting hotel reservation program\n");
        }
    }

    public static void createAccount() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Email format: name@domain.com\n");
        String Email = scanner.nextLine();

        System.out.println("First Name:\n");
        String firstName = scanner.nextLine();

        System.out.println("Last Name:\n");
        String lastName = scanner.nextLine();

        try {
            hotelResource.createACustomer(Email,firstName,lastName);
            System.out.println("Account is created");
            printMain();
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getLocalizedMessage());
            createAccount();
        }
    }

    private static void findAndReserveRoom() {
        final Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Check-In Date mm/dd/yyyy example: 02/01/2020");
        Date checkIn = getInputDate(scanner);

        System.out.println("Enter Check-Out Date mm/dd/yyyy example: 02/21/2020");
        Date checkOut = getInputDate(scanner);

        if (checkIn != null && checkOut != null) {
            Collection<IRoom> availableRooms = hotelResource.findARoom(checkIn, checkOut);

            if (availableRooms.isEmpty()) {
                Collection<IRoom> alternativeRooms
                        = hotelResource.alternativeRooms(checkIn, checkOut);

                if (alternativeRooms.isEmpty()) {
                    System.out.println("No rooms found.");
                } else {
                    final Date alternativeCheckIn
                            = hotelResource.addSevenDays(checkIn);
                    final Date alternativeCheckOut
                            = hotelResource.addSevenDays(checkOut);
                    System.out.println("We've only found rooms on alternative dates:"
                            +
                            "\nCheck-In Date:" + alternativeCheckIn +
                            "\nCheck-Out Date:" + alternativeCheckOut);

                    printRooms(alternativeRooms);
                    reserveRoom(scanner, alternativeCheckIn,
                            alternativeCheckOut, alternativeRooms);
                }
            } else {
                printRooms(availableRooms);
                reserveRoom(scanner, checkIn, checkOut, availableRooms);
            }
        }
    }

    private static void findAndReserve() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Check-In Date mm/dd/yyyy example 02/01/2020");
        Date checkIn = getInputDate(scanner);
        System.out.println("Enter Check-Out Date mm/dd/yyyy example 02/21/2020");
        Date checkOut = getInputDate(scanner);

        if (checkIn != null && checkOut != null) {
            Collection<IRoom> rooms = hotelResource.findARoom(checkIn, checkOut);

            if(rooms.isEmpty()) {
                Collection<IRoom> alternative = hotelResource
                        .alternativeRooms(checkIn, checkOut);
                if (alternative.isEmpty()) {
                    System.out.println("No rooms are found");
                } else {
                    Date alternativeCheckIn = hotelResource.addSevenDays(checkIn);
                    Date alternativeCheckOut = hotelResource.addSevenDays(checkOut);
                    System.out.println("We have only found rooms on alternative date:\n" +
                            "Check-In Date: \n" + alternativeCheckIn +
                            "Check-Out Date: \n" + alternativeCheckOut);
                    printRooms(alternative);
                    reserveRoom(scanner, alternativeCheckIn, alternativeCheckOut, alternative);
                }
            }
            else {
                printRooms(rooms);
                reserveRoom(scanner, checkIn, checkOut, rooms);
            }
        }
    }

    private static void reserveRoom(Scanner scanner, Date checkIn, Date checkOut,
                                    Collection<IRoom> rooms) {
        System.out.println("Would you like to book a room y/n \n");
        String yOrNRoom = scanner.nextLine();

        if("y".equals(yOrNRoom)) {
            System.out.println("Do you have an account with us? y/n \n");
            String yOrNoAccount = scanner.nextLine();

            if ("y".equals(yOrNoAccount)) {
                System.out.println("Enter email format: name@doamin.com\n");
                String Email = scanner.nextLine();
                if (hotelResource.getCustomer(Email) == null) {
                    System.out.println("Customer not found.\n");
                } else {
                    System.out.println("What roomnumber would you like to reserve?\n");
                    String roomNum = scanner.nextLine();
                    if (rooms.stream().anyMatch(room -> room.getRoomNumber()
                            .equals(roomNum))) {
                        IRoom room = hotelResource.getRoom(roomNum);
                        Reservation reservation = hotelResource
                                .bookARoom(Email, room, checkIn, checkOut);
                        System.out.println("Reservation created successfully!\n");
                        System.out.println(reservation);
                    } else {
                        System.out.println("Room number is not avaiable.\n " +
                                "Start reservation again.\n");
                    }
                }
                printMain();
            } else {
                System.out.println("Please create an account.\n");
                printMain();
            }
        }
        else if ("n".equals(yOrNRoom)) {
            printMain();
        } else {
            reserveRoom(scanner, checkIn, checkOut, rooms);
        }
    }

    private static void printRooms(Collection<IRoom> rooms) {
        if(rooms.isEmpty()) {
            System.out.println("No room is found.\n");
        } else {
            rooms.forEach(System.out::println);
        }
    }

    private static Date getInputDate(final Scanner scanner) {
        try {
            return new SimpleDateFormat(DEFAULT_DATE_FORMAT).parse(scanner.nextLine());
        } catch (ParseException ex) {
            System.out.println("Error: Invalid date.");
            findAndReserveRoom();
        }
        return null;
    }

    public static void seeMyReservations() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your Email format: name@domain.com\n");
        String Email = scanner.nextLine();

        printAllReservations(hotelResource.getCustomerReservations(Email));
    }
    public static void printAllReservations(Collection<Reservation> reservations) {
        if(reservations == null || reservations.isEmpty()) {
            System.out.println("The Customer does not have reservations");
        }
        else {
            reservations.forEach(reservation -> System.out.println("\n" + reservation));
        }
    }

    public static void printMain() {
        System.out.println("Welcome to the Hotel Reservation Application\n" +
                "----------------------------------------------\n" +
                "1. Find and reserve a room\n" +
                "2. See my reservation\n" +
                "3. Create an Account\n" +
                "4. Admin\n" +
                "5. Exit\n" +
                "----------------------------------------------\n" +
                "Please select a number for the menu option\n");
    }
}
