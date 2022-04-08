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
import nf.Koneksi;
import nf.entity.Supplier;
import nf.interfaces.InterSupplier;

/**
 *
 * @author ACER
 */
public class ImplSupplier implements InterSupplier{
        
    public Supplier insert(Supplier o) throws SQLException {
        PreparedStatement ps = Koneksi.dbConnection().prepareStatement("insert into supplier values(?,?,?,?,?)");
        ps.setInt(1, o.getId());
        ps.setString(2, o.getKode());
        ps.setString(3, o.getNama());
        ps.setString(4, o.getAlamat());
        ps.setString(5, o.getTelepon());
        ps.executeUpdate();
        return o;
    }

    public void update(Supplier o) throws SQLException {
        PreparedStatement ps = Koneksi.dbConnection().prepareStatement("update supplier set kode=?,nama=?,alamat=?,telepon=? where id_supplier=?");
        ps.setString(1, o.getKode());
        ps.setString(2, o.getNama());
        ps.setString(3, o.getAlamat());
        ps.setString(4, o.getTelepon());
        ps.setInt(5, o.getId());
        ps.executeUpdate();
    }

    public void delete(int o) throws SQLException {
        PreparedStatement ps = Koneksi.dbConnection().prepareStatement("delete from supplier where=?");
        ps.setInt(1, o);
        ps.executeUpdate();
    }

    public List<Supplier> getAll() throws SQLException {
        Statement st = Koneksi.dbConnection().createStatement();
        ResultSet rs = st.executeQuery("select *from supplier");
        List<Supplier> list = new ArrayList<Supplier>();
        while(rs.next()){
            Supplier o = new Supplier();
            o.setId(rs.getInt("id_supplier"));
            o.setKode(rs.getString("kode"));
            o.setNama(rs.getString("nama"));
            o.setAlamat(rs.getString("alamat"));
            o.setTelepon(rs.getString("telepon"));
            list.add(o);
        }
        return list;
    }

    public Supplier getByNama(String o) throws SQLException {
        PreparedStatement ps = Koneksi.dbConnection().prepareStatement("select id_supplier from supplier where nama=?");
        ps.setString(1, o);
        ResultSet rs = ps.executeQuery();
        Supplier d = new Supplier();
        if(rs.next()){
            d.setId(rs.getInt("id_supplier"));
        }
        return d;
    }
}
