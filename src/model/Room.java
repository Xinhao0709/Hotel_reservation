package model;

public class Room implements IRoom {
    private String roomNumber;
    protected Double price;
    private RoomTypeEnumeration enumeration;


    public Room(String roomNum, Double pri, RoomTypeEnumeration enuma) {
        super();
        this.roomNumber = roomNum;
        this.price = pri;
        this.enumeration = enuma;
    }
    @Override
    public String getRoomNumber() {
        return roomNumber;
    }

    @Override
    public Double getRoomPrice() {
         return price;
    }

    @Override
    public RoomTypeEnumeration getRoomType() {
        return enumeration;
    }

    @Override
    // need to implement is Free
    public boolean isFree() {
        return price!= null && price.equals(0.0);
    }

    public String toString() {
        return "The room number is: " + roomNumber + " The room type is: " + enumeration
                + " The room price is: " + price;
    }
}
