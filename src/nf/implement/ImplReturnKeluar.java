/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nf.implement;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import nf.Koneksi;
import nf.entity.ReturnKeluar;
import nf.interfaces.InterReturnKeluar;


/**
 *
 * @author ACER
 */
public class ImplReturnKeluar implements InterReturnKeluar {
    
        public ReturnKeluar insert(ReturnKeluar o) throws SQLException {
        PreparedStatement ps = Koneksi.dbConnection().prepareStatement("insert into kembalikeluar values(null,?,?,?,?,?,?,?,?,?,?,?)");
        PreparedStatement ps2 = Koneksi.dbConnection().prepareStatement("update barang set stok=? where id_barang=?");
        ps.setString(1, o.getNoretrun());
        ps.setString(2, o.getNamaretrun());
        ps.setDate(3, new java.sql.Date(o.getTanggalReturn().getTime()));
        ps.setInt(4, o.getKeluar().getId());
        ps.setString(5, o.getNotransaksi());
        ps.setString(6, o.getBarang().getId());
        ps.setDate(7, new java.sql.Date(o.getTanggal().getTime()));
        ps.setInt(8, o.getJumlah());
        ps.setDouble(9, o.getTotal());
        ps.setInt(10, o.getUser().getId());
        ps.setString(11, o.getKet());
        
        //
        ps.executeUpdate();
        ps2.setInt(1, o.getBarang().getStok() - o.getJumlah());
        ps2.setString(2, o.getBarang().getId());
        ps2.executeUpdate();
        return o;
    }

    public void update(ReturnKeluar o) throws SQLException {
        //PreparedStatement ps = Koneksi.dbConnection().prepareStatement("update masuk set ");
    }
    
    public void delete(int o) throws SQLException {
        //PreparedStatement ps = Koneksi.dbConnection().prepareStatement("delete from keluar where=?");
        //ps.setInt(1, o);
        //ps.executeUpdate();
    }
    
}
