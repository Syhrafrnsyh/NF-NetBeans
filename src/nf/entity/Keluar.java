/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nf.entity;

import java.util.Date;

/**
 *
 * @author ACER
 */
public class Keluar {
    private int id;
    private Barang barang;
    private User user;
    private int jumlah;
    private double total;
    private Date tanggal;
    private String no;
    private String ket;

    public Keluar() {
    }

    public Keluar(int id, Barang barang, User user, int jumlah, double total, Date tanggal, String no, String ket) {
        this.id = id;
        this.barang = barang;
        this.user = user;
        this.jumlah = jumlah;
        this.total = total;
        this.tanggal = tanggal;
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
    
    public Barang getBarang() {
        return barang;
    }

    public void setBarang(Barang barang) {
        this.barang = barang;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
