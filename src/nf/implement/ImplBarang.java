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
import nf.entity.Barang;
import nf.interfaces.InterBarang;

/**
 *
 * @author ACER
 */
public class ImplBarang implements InterBarang{
    
    public Barang insert(Barang o) throws SQLException {
       PreparedStatement ps = Koneksi.dbConnection().prepareStatement("insert barang values(?,?,?,?,?,?)");
       ps.setString(1, o.getId());
       ps.setString(2, o.getKode());
       ps.setString(3, o.getNama());
       ps.setInt(4, o.getKategori().getId());
       //ps.setInt(4, Integer.parseInt(o.getKategori().getId()));
       //ps.setInt(4, o.getIdKategori());
       ps.setInt(5, o.getStok());
       ps.setDouble(6, o.getHarga());
       ps.executeUpdate();
        return o;
    }

    public void update(Barang o) throws SQLException {
        PreparedStatement ps = Koneksi.dbConnection().prepareStatement("update barang set kode=?,nama=?,"
                + "stok=?,harga=? where id_barang=?");
       ps.setString(1, o.getKode());
       ps.setString(2, o.getNama());
       //ps.setInt(3, o.getIdKategori());
       ps.setInt(3, o.getStok());
       ps.setDouble(4, o.getHarga());
       ps.setString(5, o.getId());
       ps.executeUpdate();
    }

    public void delete(String o) throws SQLException {
        PreparedStatement ps = Koneksi.dbConnection().prepareStatement("delete from barang where id_barang=?");
        ps.setString(1, o);
        ps.executeUpdate();
    }

    public List<Barang> getAll() throws SQLException {
        Statement st = Koneksi.dbConnection().createStatement();
        ResultSet rs = st.executeQuery("select *from barang");
        List<Barang> list=new ArrayList<Barang>();
        while(rs.next()){
            Barang b = new Barang();
            b.setId(rs.getString("id_barang"));
            b.setKode(rs.getString("kode"));
            b.setNama(rs.getString("nama"));
            //b.setIdKategori(rs.getInt("id_kategori"));
            b.setStok(rs.getInt("stok"));
            b.setHarga(rs.getDouble("harga"));
            list.add(b);
        }
        return list;
    }

/*
    public List<Barang> getByKategori(String kat) throws SQLException {
        PreparedStatement ps = Koneksi.dbConnection().prepareStatement("select *from barang where id_kategori like ?");
        ps.setString(1, "%"+kat+"%");
        ResultSet rs = ps.executeQuery();
        List<Barang> list = new ArrayList<Barang>();
        while(rs.next()){
            Barang b = new Barang();
            b.setId(rs.getString("id"));
            b.setKode(rs.getString("kode"));
            b.setNama(rs.getString("nama"));
            //b.setIdKategori(rs.getInt("id_kategori"));
            b.setStok(rs.getInt("stok"));
            b.setHarga(rs.getDouble("harga"));
            list.add(b);
        }
        return list;
    }
*/

    public List<Barang> getByName(String nam) throws SQLException {
        PreparedStatement ps = Koneksi.dbConnection().prepareStatement("select *from barang where nama like ?");
        ps.setString(1, "%"+nam+"%");
        ResultSet rs = ps.executeQuery();
        List<Barang> list=new ArrayList<Barang>();
        while(rs.next()){
            Barang b = new Barang();
            b.setId(rs.getString("id_barang"));
            b.setKode(rs.getString("kode"));
            b.setNama(rs.getString("nama"));
            //b.setIdKategori(rs.getInt("id_kategori"));
            b.setStok(rs.getInt("stok"));
            b.setHarga(rs.getDouble("harga"));
            list.add(b);
        }
        return list;
    }

    public List<Barang> getById(String o) throws SQLException {
        PreparedStatement ps = Koneksi.dbConnection().prepareStatement("select *from barang where id_barang like ?");
        ps.setString(1,"%"+o+"%");
        ResultSet rs = ps.executeQuery();
        List<Barang> list=new ArrayList<Barang>();
        if(rs.next()){
            Barang bg = new Barang();
            bg.setId(rs.getString("id_barang"));
            bg.setNama(rs.getString("nama"));
            bg.setKode(rs.getString("kode"));
            //bg.setIdKategori(rs.getInt("id_kategori"));
            bg.setStok(rs.getInt("stok"));
            bg.setHarga(rs.getDouble("harga"));
            list.add(bg);
        }
        return list;

    }

    public Barang getByOne(String o) throws SQLException {
        PreparedStatement ps = Koneksi.dbConnection().prepareStatement("select *from barang where id_barang=?");
        ps.setString(1,o);
        ResultSet rs = ps.executeQuery();
        Barang bg = new Barang();
        if(rs.next()){

            bg.setId(rs.getString("id_barang"));
            bg.setNama(rs.getString("nama"));
            //bg.setIdKategori(rs.getInt("id_kategori"));
            bg.setStok(rs.getInt("stok"));
            bg.setHarga(rs.getDouble("harga"));

        }
        return bg;
    }  
}
