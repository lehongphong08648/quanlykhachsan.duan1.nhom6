package com.example.projectandroid.ui.bookingRoom;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectandroid.R;
import com.example.projectandroid.adapter.AdapterBooking;
import com.example.projectandroid.model.Booking;
import com.example.projectandroid.repository.BookingRepo;
import com.example.projectandroid.ui.systemManager.FragmentRoom;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class FragmentBookingRoom extends Fragment {

    FloatingActionButton btn_frm_add_booking;
    RecyclerView lv_booking;
    AdapterBooking adapterBooking;
    BookingRepo bookingRepo;
    List<Booking> bookingList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booking_rom,container,false);
//TODO: anh tạo getBooking đặt phòng trước và chưa check in nhé;
        bookingRepo = new BookingRepo(getContext());
        bookingList = bookingRepo.getListBookingUnderDay();
        adapterBooking = new AdapterBooking(bookingList,getContext());
        btn_frm_add_booking = view.findViewById(R.id.btn_frm_add_booking);
        btn_frm_add_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),FragmentAddBooing.class));
            }
        });

        lv_booking = view.findViewById(R.id.lv_bookingRoom);
        lv_booking.setAdapter(adapterBooking);
        lv_booking.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }
}
