package com.example.projectandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectandroid.R;
import com.example.projectandroid.model.Booking;
import com.example.projectandroid.model.Client;
import com.example.projectandroid.model.KindOfRoom;
import com.example.projectandroid.model.Rooms;
import com.example.projectandroid.repository.BookingRepo;
import com.example.projectandroid.repository.ClientRepo;
import com.example.projectandroid.repository.KorRepo;
import com.example.projectandroid.repository.RoomRepo;
import com.example.projectandroid.ui.Login;
import com.example.projectandroid.ui.checkInOut.CheckInOutActivity;
import com.example.projectandroid.ui.checkInOut.CheckOutActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdapterRentectRoom extends BaseAdapter {

    LayoutInflater mLayoutInflater;
    List<Booking> bookingList;
    Context context;
    Rooms rooms;
    BookingRepo bookingRepo;
    RoomRepo roomRepo;
    ClientRepo clientRepo;
    Client clients;
    Booking booking;

    KorRepo korRepo;
    KindOfRoom kindOfRooms;
    List<Rooms> listRooms;
    public AdapterRentectRoom(Context context, List<Rooms> listRooms) {
        this.context = context;
        this.listRooms = listRooms;
    }

    @Override
    public int getCount() {
        return listRooms.size();
    }

    @Override
    public Object getItem(int position) {
        return listRooms.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (mLayoutInflater == null){
            mLayoutInflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null){
            convertView = mLayoutInflater.inflate(R.layout.row_item_rentect_room,null);
        }



        roomRepo = new RoomRepo(context);
        bookingRepo = new BookingRepo(context);
        String idRoom = listRooms.get(position).getId();
        booking =bookingRepo.getBookingByIdRoom(idRoom,"Online");
        clientRepo = new ClientRepo(context);
        int idClient = booking.getIdClient();
        clients =clientRepo.getClientById(idClient);
        korRepo = new KorRepo(context);
        kindOfRooms = korRepo.getKindOfRoomById(listRooms.get(position).getIdKOR());



        TextView tv_ngayDen_Rentect = convertView.findViewById(R.id.tv_ngayDen_Rentect);
        TextView tv_tenPhongThue = convertView.findViewById(R.id.tv_tenPhong_rentect);
        TextView tvOptionDigitRentect = convertView.findViewById(R.id.tvOptionDigitRentect);
        TextView edt_nameClientRentect = convertView.findViewById(R.id.tv_nameClientRentect);
        TextView tv_tienCocRentect = convertView.findViewById(R.id.tv_tienCocRentect);
        TextView tv_timeRentect = convertView.findViewById(R.id.tv_timeRentect);
        TextView tv_tienPhaiTraRentect = convertView.findViewById(R.id.tv_tienPhaiTraRentect);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        SimpleDateFormat getNgay = new SimpleDateFormat("dd");
        SimpleDateFormat getGio = new SimpleDateFormat("HH");
        SimpleDateFormat getThang = new SimpleDateFormat("MM");
        SimpleDateFormat getNam = new SimpleDateFormat("yy");
        Date dateNgayDen = booking.getDayCome();
        Date dateNgayDi = booking.getDayGo();
        int ngayDi = Integer.parseInt(getNgay.format(dateNgayDi));
        int thangDi = Integer.parseInt(getThang.format(dateNgayDi));
        int gioDi = Integer.parseInt(getGio.format(dateNgayDi));
        int namDi = Integer.parseInt(getNam.format(dateNgayDi));
        int mGioDi = (namDi*365) +(thangDi * 30) + (ngayDi * 24) + gioDi;

        int ngayDen = Integer.parseInt(getNgay.format(dateNgayDen));
        int thangDen = Integer.parseInt(getThang.format(dateNgayDen));
        int gioDen = Integer.parseInt(getGio.format(dateNgayDen));
        int namDen = Integer.parseInt(getNam.format(dateNgayDen));
        int mGioDen = (namDen*365) +(thangDen * 30) + (ngayDen * 24) + gioDen;

        int thoiGianThue = mGioDi - mGioDen;

        String mNgayDen = simpleDateFormat.format(dateNgayDen);

        tv_tienCocRentect.setText(String.valueOf(booking.getDeposit()));

        Float tienPhong = kindOfRooms.getPriceOneHour() ;

        tv_ngayDen_Rentect.setText(mNgayDen);
        tv_tenPhongThue.setText(listRooms.get(position).getId());
        edt_nameClientRentect.setText(clients.getFullName());
        tv_timeRentect.setText(String.valueOf(thoiGianThue));
        tv_tienPhaiTraRentect.setText(String.valueOf(tienPhong * thoiGianThue));
        tvOptionDigitRentect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(parent.getContext(),tvOptionDigitRentect);
                popupMenu.inflate(R.menu.rentect_room);

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()){


                            case R.id.traPhong:
                                Intent intent = new Intent(context, CheckOutActivity.class);
                                Bundle bundle = new Bundle();

                                SimpleDateFormat setBirthOfDay = new SimpleDateFormat("dd/MM/yyyy");
                                String bitrhDay = setBirthOfDay.format(clients.getBirthOfDate());

                                bundle.putString("hoTenKhacHang",clients.getFullName());
                                bundle.putString("idBooking",String.valueOf(booking.getIdBooking()));
                                bundle.putString("cmnn",clients.getIdentityCard());
                                bundle.putString("quocTich",clients.getNation());
                                bundle.putString("ngaySinh",bitrhDay);
                                bundle.putString("sdt",String.valueOf(clients.getPhoneNumber()));
                                bundle.putString("diaChi",clients.getAddress());
                                bundle.putString("vip",String.valueOf(clients.getVip()));
                                bundle.putString("email",clients.getEmail());
                                bundle.putString("tenPhong",listRooms.get(position).getId());
                                bundle.putString("ngayDen",String.valueOf(booking.getDayCome()));
                                bundle.putString("ngayDi",String.valueOf(booking.getDayGo()));
                                bundle.putString("tienCoc",String.valueOf(booking.getDeposit()));
                                bundle.putString("tongTien",String.valueOf((tienPhong * thoiGianThue) - (booking.getDeposit())));
                                bundle.putString("tienPhong",String.valueOf(tienPhong*thoiGianThue));
                                intent.putExtras(bundle);
                                context.startActivity(intent);
                                Toast.makeText(parent.getContext(),"Trả phòng",Toast.LENGTH_SHORT).show();
                                break;

                            case R.id.doiPhong:
                                Toast.makeText(parent.getContext(),"Đổi phòng",Toast.LENGTH_SHORT).show();
                                break;

                            default:
                                break;
                        }


                        return false;
                    }

                });
                popupMenu.show();
            }
        });

        // to be continued
        return convertView;
    }
}
