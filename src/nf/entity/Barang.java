/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nf.entity;

/**
 *
 * @author ACER
 */
public class Barang {
    private String id;
    private String kode;
    private String nama;
    //private int id_kategori;
    private Kategori kategori;
    private int stok;
    private double harga;
    //class buku
    public Barang() {
    }

    public Barang(String id, String kode, String nama, Kategori kategori, int stok, double harga) {
        this.id = id;
        this.kode = kode;
        this.nama = nama;
        this.kategori = kategori;
        this.stok = stok;
        this.harga = harga;
    }


    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public Kategori getKategori() {
        return kategori;
    }

    public void setKategori(Kategori kategori) {
        this.kategori = kategori;
    }

    /**
    public int getIdKategori() {
        return id_kategori;
    }

    public void setIdKategori(int id_kategori) {
        this.id_kategori = id_kategori;
    }
    */

    public int getStok() {
        return stok;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }

}
