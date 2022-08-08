package service;

import model.Customer;
import model.IRoom;
import model.Reservation;
import model.Room;

import java.util.*;
import java.util.stream.Collectors;

public class ReservationService {
    public static  Map<String, Room> mapOfRooms = new HashMap<String, Room>();
    public static  Map<String, Collection<Reservation>> reservations
            = new HashMap<String, Collection<Reservation>>();

    public static void addRoom(Map<String,Room> map, Room room) {
        map.put(room.getRoomNumber(), room);
    }
    public static Room getARoom(String roomNumber) {
        return mapOfRooms.get(roomNumber);
    }


    public Reservation reserveARoom(Customer customer, IRoom room, Date checkIn,
                                    Date checkOut) {
        Reservation reservation = new Reservation(customer, room, checkIn, checkOut);
        Collection<Reservation> CuReservations = getCustomersReservation(customer);
        //if reservation does not exist, create a new one
        if(CuReservations == null) {
            CuReservations = new LinkedList<>();
        }
        CuReservations.add(reservation);
        reservations.put(customer.getEmail(), CuReservations);
        return reservation;
    }

    public Collection<Reservation> getCustomersReservation(Customer customer) {
        return reservations.get(customer.getEmail());
    }

    public void printAllReservation() {
        Collection<Reservation> reservations = getAllReservation();

        if(reservations.isEmpty()) {
            System.out.println("No reservations found.");
        } else {
            for (Reservation reservation: reservations) {
                System.out.println(reservation + "\n");
            }
        }
    }

    public Collection<IRoom> findRooms(Date checkIn, Date checkOut) {
        Collection<Reservation> all = getAllReservation();
        Collection<IRoom> avaiableRoom = new LinkedList<>();

        for (Reservation reservation: all) {
            if (!Overlaps(reservation, checkIn, checkOut)) {
                avaiableRoom.add(reservation.getRoom());
            }
        }
        return avaiableRoom;
    }

    private boolean Overlaps(Reservation reservation, Date CheckIn, Date CheckOut) {
        return CheckIn.before(reservation.getCheckOutDate())
                && CheckOut.after(reservation.getCheckInDate());
    }
    private Collection<Reservation> getAllReservation() {
        Collection<Reservation> all = new LinkedList<>();
        for (Collection<Reservation> reservations: reservations.values()) {
            all.addAll(reservations);
        }
        return all;
    }
}
