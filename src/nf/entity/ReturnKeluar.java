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
public class ReturnKeluar {
    
    private int id;
    private String noretrun;
    private String namaretrun;
    private Date tanggalreturn;
    private Supplier supplier;
    private Keluar keluar;
    private String notransaksi;
    private Barang barang;
    private Date tanggal;
    //private Timestamp createdAt;
    private int jumlah;
    private double total;
    private User user;
    private String ket;
    

    public ReturnKeluar() {
    }

    public ReturnKeluar(int id, String noretrun, String namaretrun, Date tanggalreturn, Supplier supplier, Barang barang, Date tanggal, Keluar keluar, String notransaksi, int jumlah, double total, User user, String ket) {
        this.id = id;
        this.noretrun = noretrun;
        this.namaretrun = namaretrun;
        this.tanggalreturn = tanggalreturn;
        this.supplier = supplier;
        this.barang = barang;
        this.keluar = keluar;
        this.notransaksi = notransaksi;
        this.tanggal = tanggal;
        this.jumlah = jumlah;
        this.total = total;
        this.user = user;
        this.ket = ket;
    }
    
    public String getKet() {
        return ket;
    }

    public void setKet(String ket) {
        this.ket = ket;
    }
    
    public Date getTanggalReturn() {
        return tanggalreturn;
    }

    public void setTanggalReturn(Date tanggalreturn) {
        this.tanggalreturn = tanggalreturn;
    }
    
    public String getNamaretrun() {
        return namaretrun;
    }

    public void setNamaretrun(String namaretrun) {
        this.namaretrun = namaretrun;
    }
    
    public String getNoretrun() {
        return noretrun;
    }

    public void setNoretrun(String noretrun) {
        this.noretrun = noretrun;
    }

    public String getNotransaksi() {
        return notransaksi;
    }

    public void setNotransaksi(String notransaksi) {
        this.notransaksi = notransaksi;
    }
    
    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }
    
    public Keluar getKeluar() {
        return keluar;
    }

    public void setKeluar(Keluar keluar) {
        this.keluar = keluar;
    }

    public Barang getBarang() {
        return barang;
    }

    public void setBarang(Barang barang) {
        this.barang = barang;
    }
    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    /*
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    */
}
