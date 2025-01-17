package com.example.projectandroid.repository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.room.Room;

import com.example.projectandroid.dao.RoomDAO;
import com.example.projectandroid.database.AppDatabase;
import com.example.projectandroid.model.Booking;
import com.example.projectandroid.model.Rooms;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RoomRepo {

    private RoomDAO roomDAO;

    public RoomRepo(Context context) {
        AppDatabase database = Room.databaseBuilder(context, AppDatabase.class, "databasevnkye2").allowMainThreadQueries().build();
        roomDAO = database.roomDAO();
    }

    public void insert(Rooms room) {
        new InsertRoomAsyncTask(roomDAO).execute(room);
    }


    public void update(Rooms room) {
        new UpdateRoomAsyncTask(roomDAO).execute(room);
    }

    public void delete(Rooms room) {
        new DeleteRoomAsyncTask(roomDAO).execute(room);
    }

    public List<Rooms> getAll() {
        return roomDAO.getAllRoom();
    }

    //Lấy tất cả phòng đang dọn
    public List<Rooms> getAllBusyRoom() {
        return roomDAO.getRoomByStatus("Busy");
    }

    //Lấy tất cả phòng đang có người dùng
    public List<Rooms> getAllOnlineRoom() {
        return roomDAO.getRoomByStatus("Online");
    }

    //Lấy tất cả phòng đang có người đặt
    public List<Rooms> getAllBookingRoom() {
        return roomDAO.getRoomByStatus("Booking");
    }

    //Tìm kiếm tất cả room trong booking
    public List<Rooms> getAllRoomBookingByIdRoom(String idRoom) {
        return roomDAO.getAllRoomBookingByIdRoom(idRoom);
    }

    //Lấy tất cả các phòng trống

    public List<Rooms> getAllOfflineRoom() {
        List<Rooms> listRoom = new ArrayList<>();
        listRoom.addAll(roomDAO.getAllRoomNotBooking());
        listRoom.addAll(roomDAO.getAllRoomStatusOffline());

        return listRoom;
    }

    public Rooms getRoomById(String idRoom) {
        return roomDAO.getRoomById(idRoom).get(0);
    }

    private static class InsertRoomAsyncTask extends AsyncTask<Rooms, Void, Void> {

        RoomDAO roomDAO;

        InsertRoomAsyncTask(RoomDAO roomDAO) {
            this.roomDAO = roomDAO;
        }

        @Override
        protected Void doInBackground(Rooms... rooms) {

            roomDAO.insertRoom(rooms);

            return null;
        }

    }

    private static class UpdateRoomAsyncTask extends AsyncTask<Rooms, Void, Void> {

        RoomDAO roomDAO;

        UpdateRoomAsyncTask(RoomDAO roomDAO) {
            this.roomDAO = roomDAO;
        }

        @Override
        protected Void doInBackground(Rooms... rooms) {
            roomDAO.updateRoom(rooms);

            return null;
        }

    }

    private static class DeleteRoomAsyncTask extends AsyncTask<Rooms, Void, Void> {

        RoomDAO roomDAO;

        DeleteRoomAsyncTask(RoomDAO roomDAO) {
            this.roomDAO = roomDAO;
        }

        @Override
        protected Void doInBackground(Rooms... rooms) {
            roomDAO.deleteRoom(rooms);

            return null;
        }

    }

}
