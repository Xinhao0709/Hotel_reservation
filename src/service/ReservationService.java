package service;

import model.Customer;
import model.IRoom;
import model.Reservation;
import model.Room;

import java.util.*;
import java.util.stream.Collectors;

public class ReservationService {
    public static  Map<String, IRoom> mapOfRooms = new HashMap<String, IRoom>();
    public static  Map<String, Collection<Reservation>> reservations
            = new HashMap<String, Collection<Reservation>>();
    private static ReservationService reservationService = null;
    private ReservationService() {};
    public static ReservationService getInstance() {
        if (null == reservationService) {
            reservationService = new ReservationService();
        }
        return reservationService;
    }

    public  void addRoom(IRoom room) {
        mapOfRooms.put(room.getRoomNumber(),room);
    }
    public  IRoom getARoom(String roomNumber) {
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
        Collection<IRoom> availableRoom = new LinkedList<>();

        for (Reservation reservation: all) {
            if (!Overlaps(reservation, checkIn, checkOut)) {
                availableRoom.add(reservation.getRoom());
            }
        }
        return availableRoom;
    }

    public Collection <IRoom> alternativeRooms(Date checkIn, Date checkOut) {
        return findRooms(addSevenDays(checkIn), addSevenDays(checkOut));
    }
    public Date addSevenDays(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 7);
        return calendar.getTime();
    }
    private boolean Overlaps(Reservation reservation, Date CheckIn, Date CheckOut) {
        if(CheckIn.equals(reservation.getCheckOutDate()) ||
        CheckOut.equals(reservation.getCheckInDate())) {
            return true;
        }
        return CheckIn.before(reservation.getCheckOutDate())
                && CheckOut.after(reservation.getCheckInDate());
        //if CheckIn == CheckOut
    }
    private Collection<Reservation> getAllReservation() {
        Collection<Reservation> all = new LinkedList<>();
        for (Collection<Reservation> reservations: reservations.values()) {
            all.addAll(reservations);
        }
        return all;
    }

    public Collection<IRoom> getAllRooms() {
        return mapOfRooms.values();
    }
}
