package model;

import java.util.Date;

public class Reservation {
    Customer customer;
    IRoom room;
    Date checkInDate;
    Date checkOutDate;

    public Reservation(Customer customer, IRoom room, Date checkIn, Date checkOut) {
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkIn;
        this.checkOutDate = checkOut;
    }
    public IRoom getRoom() {
        return room;
    }
    public Date getCheckInDate() {
        return checkInDate;
    }
    public Date getCheckOutDate() {
        return checkOutDate;
    }
    public String toString() {
        return "Customer: " + customer.toString() +
                "\nRoom: " + room.toString() +
                "\nCheckInDate: " + checkInDate +
                "\nCheckOutDate: " + checkOutDate;
    }
}
