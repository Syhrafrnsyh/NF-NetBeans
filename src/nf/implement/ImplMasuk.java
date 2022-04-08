/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nf.implement;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import nf.Koneksi;
import nf.entity.Masuk;
import nf.interfaces.InterMasuk;

/**
 *
 * @author ACER
 */
public class ImplMasuk implements InterMasuk {
    
    public Masuk insert(Masuk o) throws SQLException {
        PreparedStatement ps = Koneksi.dbConnection().prepareStatement("insert into masuk values(null,?,?,?,?,?,?,?)");
        PreparedStatement ps2 = Koneksi.dbConnection().prepareStatement("update barang set stok=? where id_barang=?");
        ps.setInt(1, o.getSupplier().getId());
        ps.setString(2, o.getBarang().getId());
        ps.setInt(3, o.getJumlah());
        ps.setDouble(4, o.getTotal());
        ps.setDate(5, new java.sql.Date(o.getTanggal().getTime()));
        ps.setString(6, o.getNo());
        ps.setString(7, o.getKet());
        ps.executeUpdate();
        ps2.setInt(1, o.getBarang().getStok() + o.getJumlah());
        ps2.setString(2, o.getBarang().getId());
        ps2.executeUpdate();
        return o;
    }

    public void update(Masuk o) throws SQLException {
        //PreparedStatement ps = Koneksi.dbConnection().prepareStatement("update masuk set ");
    }
    
    public Masuk getByNo(String o) throws SQLException {
        PreparedStatement ps = Koneksi.dbConnection().prepareStatement("select *from masuk where id_masuk=?");
        ps.setString(1,o);
        ResultSet rs = ps.executeQuery();
        Masuk k = new Masuk();
        if(rs.next()){
            k.setId(rs.getInt("id_masuk"));
            k.setNo(rs.getString("no"));
            k.setTanggal(rs.getDate("tanggal"));
            k.setJumlah(rs.getInt("jumlah"));
            k.setTotal(rs.getDouble("total"));
            k.setKet(rs.getString("ket"));
        }
        return k;
    } 
}
