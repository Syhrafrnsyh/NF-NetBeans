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
import nf.entity.Kategori;
import nf.interfaces.InterKategori;

/**
 *
 * @author ACER
 */
public class ImplKategori implements InterKategori {
    
    public Kategori insert(Kategori o) throws SQLException {
        PreparedStatement ps = Koneksi.dbConnection().prepareStatement("insert into kategori values(?,?)");
        ps.setInt(1, o.getId());
        ps.setString(2, o.getNama());
        ps.executeUpdate();
        return o;
    }

    public void update(Kategori o) throws SQLException {
        PreparedStatement ps = Koneksi.dbConnection().prepareStatement("update kategori set nama=? where id_kategori=?");
        ps.setString(1, o.getNama());
        ps.setInt(2, o.getId());
        ps.executeUpdate();
    }

    public void delete(int o) throws SQLException {
        PreparedStatement ps = Koneksi.dbConnection().prepareStatement("delete from kategori where id_kategori=?");
        ps.setInt(1, o);
        ps.executeUpdate();
    }

    public List<Kategori> getAll() throws SQLException {
        Statement st = Koneksi.dbConnection().createStatement();
        ResultSet rs = st.executeQuery("select *from kategori");
        List<Kategori> list = new ArrayList<Kategori>();
        while(rs.next()){
            Kategori k = new Kategori();
            k.setId(rs.getInt("id_kategori"));
            k.setNama(rs.getString("nama"));
            list.add(k);
        }
        return list;
    }

    public Kategori getByNama(String o) throws SQLException {
        PreparedStatement ps = Koneksi.dbConnection().prepareStatement("select id_kategori from kategori where nama=?");
        ps.setString(1, o);
        ResultSet rs = ps.executeQuery();
        Kategori k = new Kategori();
        if(rs.next()){
            k.setId(rs.getInt("id_kategori"));
        }
        return k;
    } 
}
