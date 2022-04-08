/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nf.entity;

import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author ACER
 */
public class Masuk {
    private int id;
    private Supplier supplier;
    private Barang barang;
    private Date tanggal;
    //private Timestamp createdAt;
    private int jumlah;
    private double total;
    private String no;
    private String ket;

    public Masuk() {
    }

    public Masuk(int id, Supplier supplier, Barang barang, Date tanggal,/*Timestamp createdAt,*/ int jumlah, double total, String no, String ket) {
        this.id = id;
        this.supplier = supplier;
        this.barang = barang;
        this.tanggal = tanggal;
        this.jumlah = jumlah;
        this.total = total;
        this.no = no;
        this.ket = ket;
    }

    public String getKet() {
        return ket;
    }

    public void setKet(String ket) {
        this.ket = ket;
    }
    
    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }
    
    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public Barang getBarang() {
        return barang;
    }

    public void setBarang(Barang barang) {
        this.barang = barang;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
