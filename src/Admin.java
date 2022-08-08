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
                            for (Customer customer:allCustomers) {
                                System.out.println(customer.toString() + "\n");
                            }
                            break;
                        case '2':
                            Collection<IRoom> rooms
                                    = adminResource.getAllRooms();
                            for (IRoom room: rooms) {
                                System.out.println(room.toString() + "\n");
                            }
                            break;
                        case '3':
                            adminResource.displayAllReservations();
                            break;
                        case '4':
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
                        case '5':
                            MainMenu.printMain();
                            break;
                        default:
                            System.out.println("Unknown action\n");
                            break;

                    }
                }
            } while (command.charAt(0) != '5' || command.length()!= 1);
        } catch (StringIndexOutOfBoundsException ex) {
            System.out.println("Empty input\n Exiting hotel reservation program\n");
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
