/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nf.implement;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.TimePicker;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;
import nf.Koneksi;
import nf.entity.Jasa;
import static nf.view.FormJasa.dateTimePicker1;
import static nf.view.FormJasa.tabelJasa;
import static nf.view.FormJasa.txtID;
import static nf.view.FormJasa.txtJam;
import static nf.view.FormJasa.txtTgl;

/**
 *
 * @author ACER
 */
public class ImplJasa{

    private String className = "ImplJasa";
    
    public void refresh() {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            String sqlSelect = "SELECT * FROM jasa";
            statement = Koneksi.dbConnection().createStatement();
            resultSet = statement.executeQuery(sqlSelect);
            tabelJasa.setModel(DbUtils.resultSetToTableModel(resultSet));
        } catch (SQLException a) {
            System.err.println(a);
        }
    }
    
    public List<Jasa> getListData() {
        List<Jasa> list = new ArrayList<>();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            String sqlSelect = "SELECT * FROM jasa";
            statement = Koneksi.dbConnection().createStatement();
            resultSet = statement.executeQuery(sqlSelect);
            while (resultSet.next()) {
                Jasa jasa = new Jasa();
                jasa.setId(resultSet.getInt("id_jasa"));
                jasa.setNama(resultSet.getString("nama"));
                jasa.setTanggal(resultSet.getString("tanggal"));
                jasa.setJam(resultSet.getString("jam"));
                jasa.setTelepon(resultSet.getString("telepon"));
                jasa.setAlamat(resultSet.getString("alamat"));
                jasa.setKeterangan(resultSet.getString("keterangan"));
                jasa.setMenu(resultSet.getString("menu"));
                list.add(jasa);
            }
            statement.close();
            resultSet.close();
        } catch (Exception error) {
            System.err.println("Terjadi Kesalahan pada class " + className + ", methode function getListJasa \n Detail : " + error);
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan pada class " + className + ", function getListJasa");
        }
        return list;
    }
    
        public List<Jasa> getListDataByParameter(String searchParameter) {
        List<Jasa> list = new ArrayList<>();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            String sqlSelect = "SELECT * FROM jasa WHERE nama LIKE '%" + searchParameter + "%' OR nama LIKE '%" + searchParameter + "%' ORDER BY id_jasa";

            statement = Koneksi.dbConnection().createStatement();
            resultSet = statement.executeQuery(sqlSelect);

            while (resultSet.next()) {
                Jasa jasa = new Jasa();
                jasa.setId(resultSet.getInt("id_jasa"));
                jasa.setNama(resultSet.getString("nama"));
                jasa.setTanggal(resultSet.getString("tanggal"));
                jasa.setJam(resultSet.getString("jam"));
                jasa.setTelepon(resultSet.getString("telepon"));
                jasa.setAlamat(resultSet.getString("alamat"));
                jasa.setKeterangan(resultSet.getString("keterangan"));
                jasa.setMenu(resultSet.getString("menu"));
                jasa.setTanggal(resultSet.getString("tanggal"));
                jasa.setJam(resultSet.getString("jam"));
                list.add(jasa);
            }

            statement.close();
            resultSet.close();
        } catch (Exception error) {
            System.err.println("Terjadi Kesalahan pada class " + className + ", function getListGudangByParameter \n Detail : " + error);
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan pada class " + className + ", function getListGudangByParameter");
        }
        return list;
    }
    
    public String insertData(Jasa jasa) {
        DatePicker dt = dateTimePicker1.getDatePicker();
        TimePicker tm = dateTimePicker1.getTimePicker();
        String tanggal = dt.toString();
        String jam = tm.toString();
        String message = "";
        PreparedStatement preparedStatement = null;
        try {
            String sqlInsert = "INSERT INTO jasa VALUES (?,?,?,?,?,?,?,?)";
            preparedStatement = Koneksi.dbConnection().prepareStatement(sqlInsert);
            preparedStatement.setInt(1, jasa.getId());
            preparedStatement.setString(2, jasa.getNama());
            preparedStatement.setString(3, jasa.tanggal);
            preparedStatement.setString(4, jasa.jam);
            preparedStatement.setString(5, jasa.getTelepon());
            preparedStatement.setString(6, jasa.getAlamat());
            preparedStatement.setString(7, jasa.getKeterangan());
            preparedStatement.setString(8, jasa.getMenu());
            int isSuccess = preparedStatement.executeUpdate();
            if (isSuccess == 1) {
                message = "Data Berhasil ditambah";
            } else {
                message = "Data Gagal ditambah";
            }
            preparedStatement.close();
        } catch (SQLException error) {
            System.err.println("Terjadi Kesalahan pada class " + className + ", function insertJasa \n Detail : " + error);
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan pada class " + className + ", function insertJasa");
        }
        return message;
    }
    
    public String updateData(Jasa jasa) {
        String jam = txtJam.getText();
        String tanggal = txtTgl.getText();
        String idx = txtID.getText();
        int id = Integer.parseInt(idx);
        String message = "";
        PreparedStatement preparedStatement = null;
        try {
        String sqlUpdate = "UPDATE jasa SET nama=?, tanggal=?, jam=?, telepon=?, alamat=?," 
        + " keterangan=?, menu=? WHERE id_jasa=?";
        preparedStatement = Koneksi.dbConnection().prepareStatement(sqlUpdate);
            preparedStatement.setString(1, jasa.getNama());
            preparedStatement.setString(2, jasa.tanggal);
            preparedStatement.setString(3, jasa.jam);  
            preparedStatement.setString(4, jasa.getTelepon());
            preparedStatement.setString(5, jasa.getAlamat());
            preparedStatement.setString(6, jasa.getKeterangan());
            preparedStatement.setString(7, jasa.getMenu()); 
            preparedStatement.setInt(8, jasa.getId());
            //preparedStatement.setInt(8, jasa.id);
            int isSuccess = preparedStatement.executeUpdate();
            if (isSuccess == 1) {
                message = "Data Berhasil diubah";
            } else {
                message = "Data Gagal diubah";
            }
            preparedStatement.close();
        } catch (SQLException error) {
            System.err.println("Terjadi Kesalahan pada class " + className + ", function updateJasa \n Detail : " + error);
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan pada class " + className + ", function updateJasa");
        }
        return message;
    }
    
    public String deleteData(int id) {
        String message = "";
        PreparedStatement preparedStatement = null;
        try {
            String sqlDelete = "DELETE FROM jasa WHERE id_jasa = ?";
            preparedStatement = Koneksi.dbConnection().prepareStatement(sqlDelete);
            preparedStatement.setInt(1, id);
            int isSuccess = preparedStatement.executeUpdate();
            if (isSuccess == 1) {
                message = "Data Berhasil dihapus";
            } else {
                message = "Data Gagal dihapus";
            }
            preparedStatement.close();
        } catch (SQLException error) {
            System.err.println("Terjadi Kesalahan pada class " + className + ", function deleteJasa \n Detail : " + error);
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan pada class " + className + ", function deleteJasa");
        }
        return message;
    }

}
