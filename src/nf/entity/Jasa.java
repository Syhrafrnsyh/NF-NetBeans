/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nf.entity;

/**
 *
 * @author ACER
 */
public class Jasa {
    public int id;
    private String nama;
    private String telepon;
    private String alamat;
    private String keterangan;
    private String menu;
    public String tanggal;
    public String jam;
    
    public Jasa() {
    }

    public Jasa(int id, String nama, String telepon, String alamat, String keterangan, String menu, String tanggal, String jam) {
        this.id = id;
        this.nama = nama;
        this.telepon = telepon;
        this.alamat = alamat;
        this.keterangan = keterangan;
        this.menu = menu;
        this.tanggal = tanggal;
        this.jam = jam;
        
    }

    public String getJam() {
        return tanggal;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }
    
    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }
    
    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }
    
    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTelepon() {
        return telepon;
    }

    public void setTelepon(String telepon) {
        this.telepon = telepon;
    }
}
