/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nf.implement;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import nf.Koneksi;
import nf.entity.Keluar;
import nf.interfaces.InterKeluar;

/**
 *
 * @author ACER
 */
public class ImplKeluar implements InterKeluar {
    
    public Keluar insert(Keluar o) throws SQLException {
        PreparedStatement ps = Koneksi.dbConnection().prepareStatement("insert into keluar values(null,?,?,?,?,?,?,?)");
        PreparedStatement ps2 = Koneksi.dbConnection().prepareStatement("update barang set stok=? where id_barang=?");
        ps.setString(1, o.getBarang().getId());
        ps.setInt(2, o.getUser().getId());
        ps.setInt(3, o.getJumlah());
        ps.setDouble(4, o.getTotal());
        ps.setDate(5, new java.sql.Date(o.getTanggal().getTime()));
        ps.setString(6, o.getNo());
        ps.setString(7, o.getKet());
        ps.executeUpdate();
        ps2.setInt(1, o.getBarang().getStok()- o.getJumlah());
        ps2.setString(2, o.getBarang().getId());
        ps2.executeUpdate();
        return o;
    }
    /*
    public void delete(int o) throws SQLException {
        PreparedStatement ps = Koneksi.dbConnection().prepareStatement("delete from keluar where id_keluar=?");
        ps.setInt(1, o);
        ps.executeUpdate();
    }
    */
    public void update(Keluar o) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Keluar getByNo(String o) throws SQLException {
        //PreparedStatement ps = Koneksi.dbConnection().prepareStatement("select id_keluar from keluar where no=?");
        PreparedStatement ps = Koneksi.dbConnection().prepareStatement("select *from keluar where id_keluar=?");
        ps.setString(1,o);
        ResultSet rs = ps.executeQuery();
        Keluar k = new Keluar();
        if(rs.next()){
            k.setId(rs.getInt("id_keluar"));
            k.setNo(rs.getString("no"));
            k.setTanggal(rs.getDate("tanggal"));
            k.setJumlah(rs.getInt("jumlah"));
            k.setTotal(rs.getDouble("total"));
            k.setKet(rs.getString("ket"));
            //k.setId(rs.getString("id_barang"));
            //bg.setIdKategori(rs.getInt("id_kategori"));
            //k.setStok(rs.getInt("stok"));
            //k.setHarga(rs.getDouble("harga"));

        }
        return k;
    }  
 
}
