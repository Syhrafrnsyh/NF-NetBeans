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
public class ReturnMasuk {
     
    private int id;
    private String noretrun;
    private Supplier supplier;
    private Date tanggalreturn;
    private Masuk masuk;
    private String notransaksi;
    private Barang barang;
    private Date tanggal;
    private double harga;
    private int jumlah;
    private double total;
    private String ket;
    

    public ReturnMasuk() {
    }

    public ReturnMasuk(int id, String noretrun, Date tanggalreturn, Supplier supplier, Barang barang, Date tanggal, Masuk masuk, String notransaksi, double harga, int jumlah, double total, String ket) {
        this.id = id;
        this.noretrun = noretrun;
        //this.namaretrun = namaretrun;
        this.tanggalreturn = tanggalreturn;
        this.supplier = supplier;
        this.barang = barang;
        this.notransaksi = notransaksi;
        this.tanggal = tanggal;
        this.harga = harga;
        this.jumlah = jumlah;
        this.total = total;
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
    
    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
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
    
    public Masuk getMasuk() {
        return masuk;
    }

    public void setMasuk(Masuk masuk) {
        this.masuk = masuk;
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

    /*
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    */   
}
