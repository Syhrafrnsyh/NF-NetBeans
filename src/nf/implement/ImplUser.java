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
import nf.entity.User;
import nf.interfaces.InterUser;

/**
 *
 * @author ACER
 */
public class ImplUser implements InterUser {

    public User insert(User o) throws SQLException {
        PreparedStatement ps = Koneksi.dbConnection().prepareStatement("insert into user values(null,?,?,?,?,?,?,?)");
        ps.setString(1, o.getNama());
        ps.setString(2, o.getAlamat());
        ps.setString(3, o.getTelepon());
        ps.setString(4, o.getStatus());
        ps.setString(5, o.getUsername());
        ps.setString(6, o.getPassword());
        ps.setString(7, o.getAkses());
        ps.executeUpdate();
        return o;
    }

    public void update(User o) throws SQLException {
        PreparedStatement ps = Koneksi.dbConnection().prepareStatement("update user set nama=?,alamat=?,telepon=?,status=?,"
                + "username=?,password=?,akses=? where id_user=?");
        ps.setString(1, o.getNama());
        ps.setString(2, o.getAlamat());
        ps.setString(3, o.getTelepon());
        ps.setString(4, o.getStatus());
        ps.setString(5, o.getUsername());
        ps.setString(6, o.getPassword());
        ps.setString(7, o.getAkses());
        ps.setInt(8, o.getId());
        ps.executeUpdate();
    }

    public void delete(int o) throws SQLException {
        PreparedStatement ps = Koneksi.dbConnection().prepareStatement("delete from user where id_user=?");
        ps.setInt(1, o);
        ps.executeUpdate();
    }

    public List<User> getAll() throws SQLException {
        Statement st = Koneksi.dbConnection().createStatement();
        ResultSet rs = st.executeQuery("select *from user");
        List<User>list=new ArrayList<User>();
        while(rs.next()){
            User o=new User();
            o.setId(rs.getInt("id_user"));
            o.setNama(rs.getString("nama"));
            o.setAlamat(rs.getString("alamat"));
            o.setTelepon(rs.getString("telepon"));
            o.setStatus(rs.getString("status"));
            o.setUsername(rs.getString("username"));
            o.setPassword(rs.getString("password"));
            o.setAkses(rs.getString("akses"));
            list.add(o);
        }
        return list;
    }

    public User getByUsername(String o) throws SQLException {
        PreparedStatement ps = Koneksi.dbConnection().prepareStatement("select *from user where username=?");
        ps.setString(1, o);
        ResultSet rs = ps.executeQuery();
        User user = new User();
        if(rs.next()){
            user.setId(rs.getInt("id_user"));
            user.setNama(rs.getString("nama"));
            user.setAlamat(rs.getString("alamat"));
            user.setTelepon(rs.getString("telepon"));
            user.setStatus(rs.getString("status"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setAkses(rs.getString("akses"));
        }
        return user;
    }
    
        public User getById(String o) throws SQLException {
        PreparedStatement ps = Koneksi.dbConnection().prepareStatement("select *from user where id_user=?");
        ps.setString(1, o);
        ResultSet rs = ps.executeQuery();
        User user = new User();
        if(rs.next()){
            user.setId(rs.getInt("id_user"));
            user.setNama(rs.getString("nama"));
            user.setAlamat(rs.getString("alamat"));
            user.setTelepon(rs.getString("telepon"));
            user.setStatus(rs.getString("status"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setAkses(rs.getString("akses"));
        }
        return user;
    }
}
