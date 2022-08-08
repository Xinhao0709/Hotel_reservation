import api.AdminResource;
import model.Customer;
import model.IRoom;
import model.Room;
import model.RoomTypeEnumeration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class Admin {
    private static AdminResource adminResource = AdminResource.getInstance();


    public static void admin() {
        String command = "?";
        Scanner scanner = new Scanner(System.in);

        printMenu();

        try {
            do {
                command = scanner.nextLine();
                if(command.length() == 1) {
                    switch (command.charAt(0)) {
                        case '1':
                            Collection<Customer> allCustomers
                                    = adminResource.getAllCustomers();
                            if(allCustomers.isEmpty()) {
                                System.out.println("There is no Customers.");
                                break;
                            }
                            for (Customer customer:allCustomers) {
                                System.out.println(customer.toString() + "\n");
                            }
                            break;
                        case '2':
                            Collection<IRoom> rooms
                                    = adminResource.getAllRooms();
                            if (rooms.isEmpty()) {
                                System.out.println("There is no rooms");
                                break;
                            }
                            for (IRoom room: rooms) {
                                System.out.println(room.toString() + "\n");
                            }
                            break;
                        case '3':
                            adminResource.displayAllReservations();
                            break;
                        case '4':
                            addRoom();
                            break;
                        case '5':
                            System.out.println("Back to the Main Menu");
                            MainMenu.printMain();
                            break;
                        default:
                            System.out.println("Unknown action\n");
                            break;

                    }
                }
                else {
                    System.out.println("Invalid Input\n Enter Number from 1-5\n");
                }
            } while (command.charAt(0) != '5' || command.length()!= 1);
        } catch (StringIndexOutOfBoundsException ex) {
            System.out.println("Empty input\n Exiting hotel reservation program\n");
        }

    }

    private static void addRoom() {
        Scanner scanner1 = new Scanner(System.in);
        System.out.println("Enter room number: \n");
        String roomNum = scanner1.nextLine();
        System.out.println("Enter room price: \n");
        Double roomPri = Double.parseDouble(scanner1.nextLine());
        System.out.println("Enter room type (SINGLE/DOUBLE): \n");
        RoomTypeEnumeration roomType
                = RoomTypeEnumeration.valueOf(scanner1.nextLine());

        List<IRoom> roomToAdd = new ArrayList<>();

        IRoom newRoom = new Room(roomNum, roomPri, roomType);
        roomToAdd.add(newRoom);
        adminResource.addRoom(roomToAdd);
        System.out.println("Room is being added\n");
        System.out.println("Would you like to add another room? Y/N");
        anotherRoom();
    }

    private static void anotherRoom() {
        Scanner scanner = new Scanner(System.in);

        try {
            String newRoom;
            newRoom = scanner.nextLine();

            while ((newRoom.charAt(0) != 'Y' && newRoom.charAt(0) != 'N')||
                    newRoom.length() !=1) {
                System.out.println("Please enter Y (Yes) or N (NO)");
                newRoom = scanner.nextLine();
            }

            if (newRoom.charAt(0) == 'Y') {
                addRoom();
            }
            else if (newRoom.charAt(0) == 'N') {
                printMenu();
            }
        } catch (StringIndexOutOfBoundsException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
    }
    private static void printMenu() {
        System.out.print("\nAdmin Menu\n" +
                "--------------------------------------\n" +
                "1. See all Customers\n" +
                "2. See all Rooms\n" +
                "3. See all Reservations\n" +
                "4. Add a Room\n" +
                "5. Back to Main Menu\n" +
                "--------------------------------------\n"  +
                "Please select a number for the menu option:\n");
    }
}
