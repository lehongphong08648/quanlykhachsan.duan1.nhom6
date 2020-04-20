package com.example.projectandroid.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.projectandroid.model.Invoice;

import java.util.Date;
import java.util.List;

@Dao
public interface InvoiceDAO {
    @Insert
    void insertInvoice(Invoice... invoices);

    @Update
    void updateInvoice(Invoice... invoices);

    @Delete
    void deleteInvoice(Invoice... invoices);

    @Query("SELECT * FROM INVOICE")
    List<Invoice> getAllInvoice();

    @Query("SELECT SUM(total) FROM INVOICE INNER JOIN BOOKING " +
            "ON INVOICE.idBooking = BOOKING.idBooking " +
            "WHERE BOOKING.dayGo Like :day")
    float getAllInvoiceByDay(Date day);
}
