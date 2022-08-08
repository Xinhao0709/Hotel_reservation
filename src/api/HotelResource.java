package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

public class HotelResource {
    private static HotelResource hotelResource = null;
    private HotelResource(){}
    public static HotelResource getInstance() {
        if (null == hotelResource) {
            hotelResource = new HotelResource();
        }
        return hotelResource;
    }

    private CustomerService customerService = CustomerService.getInstance();
    private ReservationService reservationService = ReservationService.getInstance();


    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    public void createACustomer(String email, String firstName, String lastName) {
        customerService.addCustomer(email, firstName, lastName);
    }

    public IRoom getRoom(String roomNum) {
        return reservationService.getARoom(roomNum);
    }

    public Reservation bookARoom(String Email, IRoom room,
                                 Date CheckIn, Date Checkout) {
        return reservationService.reserveARoom(getCustomer(Email)
                , room, CheckIn, Checkout);
    }
    public Collection<Reservation> getCustomerReservations (String Email) {
        Customer customer = getCustomer(Email);

        if (customer == null) {
            return Collections.emptyList();
        }

        return reservationService.getCustomersReservation(getCustomer(Email));
    }

    public Collection<IRoom> findARoom(Date checkIn, Date checkOut) {
        return reservationService.findRooms(checkIn, checkOut);
    }

    public Collection<IRoom> alternativeRooms(Date checkIn, Date checkOut) {
        return reservationService.findRooms(checkIn,checkOut);
    }

    public Date addSevenDays(Date date) {
        return reservationService.addSevenDays(date);
    }
}
