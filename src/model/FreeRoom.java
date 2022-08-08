package model;

public class FreeRoom extends Room{
    public FreeRoom(String roomNumber, RoomTypeEnumeration enumeration) {
        super(roomNumber, 0.0, enumeration);
    }
    public  String toString() {
        return "This is a Free room";
    }
}
