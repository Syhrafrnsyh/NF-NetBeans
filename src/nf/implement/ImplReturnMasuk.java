/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nf.implement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import nf.Koneksi;
import nf.entity.ReturnMasuk;
import nf.interfaces.InterReturnMasuk;

/**
 *
 * @author ACER
 */
public class ImplReturnMasuk implements InterReturnMasuk{
  //static class FirstException extends Exception { }
  //static class SecondException extends Exception { }
  
    @Override
    public ReturnMasuk insert(ReturnMasuk o) throws SQLException {
    
        PreparedStatement ps = Koneksi.dbConnection().prepareStatement("insert into kembalimasuk values(null,?,?,?,?,?,?,?,?,?,?,?)");
        PreparedStatement ps2 = Koneksi.dbConnection().prepareStatement("update barang set stok=? where id_barang=?");
        ps.setString(1, o.getNoretrun());
        ps.setInt(2, o.getSupplier().getId());
        ps.setDate(3, new java.sql.Date(o.getTanggalReturn().getTime()));
        ps.setInt(4, o.getMasuk().getId());
        ps.setString(5, o.getNotransaksi());
        ps.setDate(6, new java.sql.Date(o.getTanggal().getTime()));
        ps.setString(7, o.getBarang().getId());
        ps.setDouble(8, o.getHarga());
        ps.setInt(9, o.getJumlah());
        ps.setDouble(10, o.getTotal());
        ps.setString(11, o.getKet());
        //
        ps.executeUpdate();
        ps2.setInt(1, o.getBarang().getStok() - o.getJumlah());
        ps2.setString(2, o.getBarang().getId());
        ps2.executeUpdate();
        //
        return o;   
    }

    @Override
    public void update(ReturnMasuk o) throws SQLException {
    /**
      if (o.equals("First")) {
          try {
              throw new FirstException();
          } catch (FirstException ex) {
              Logger.getLogger(ImplReturnMasuk.class.getName()).log(Level.SEVERE, null, ex);
          }
      } else {
          try {
              throw new SecondException();
          } catch (SecondException ex) {
              Logger.getLogger(ImplReturnMasuk.class.getName()).log(Level.SEVERE, null, ex);
          }
      }
      */
    }

    @Override
    public void delete(int o) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
