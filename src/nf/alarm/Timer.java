/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nf.alarm;

import ds.desktop.notify.DesktopNotify;
import java.awt.HeadlessException;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javazoom.jl.player.Player;
import nf.Koneksi;
import nf.view.FormJasa;
import nf.view.MenuUtama;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @author ACER
 */
public class Timer implements Job {

    private static String filename;
    private static Player player;
    String nama;
    String menu;
    String keterangan;
    int i;
    
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        Connection con = Koneksi.dbConnection();
        String pattern = "yyyy-MM-dd HH:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);//Mendapatkan Tanggal dan Waktu Local
        String date = simpleDateFormat.format(new Date());
        String tanggal = date.substring(0, 10);//Membagi Tanggal Menjadi 2 Part
        String jam = date.substring(11, 16);
        try {
            //Pemeriksa Database Setiap Menit Untuk pengingat
            String query = "SELECT * FROM `jasa` WHERE `tanggal`=? AND `jam`=?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, tanggal);
            pst.setString(2, jam);
            ResultSet rs = pst.executeQuery();
            int count = 0;
            while (rs.next()) {
                count++;
                nama = rs.getString("nama");
                menu = rs.getString("menu");
                keterangan = rs.getString("keterangan");
                i = rs.getInt("id_jasa");
            }
            if (count == 1) {
                play();
                Object[] options = {"OK"};
                int n = JOptionPane.showOptionDialog(null,
                   "Maintenance", nama,
                   JOptionPane.PLAIN_MESSAGE,
                   JOptionPane.QUESTION_MESSAGE,
                   null,
                   options,
                   options[0]);
                DesktopNotify.showDesktopMessage("Keterangan", keterangan);
                if (n == 0) {
                    //new MenuUtama().setVisible(true);
                    //JOptionPane.showMessageDialog(null, "Next!");
                    close();
                }
            }
        } catch (HeadlessException | SQLException e) {
            System.err.println(e);
        }
    }

    public static void play() {
        String path;
        path = "src\\nf\\alarm\\Yellow Crystals.mp3";
        try {
            FileInputStream fis     = new FileInputStream(new File(path));
            BufferedInputStream bis = new BufferedInputStream(fis);
            player = new Player(bis);
        }
        catch (Exception e) {
            System.out.println("Problem playing file " + filename);
            System.out.println(e);
        }
        // Menjalankan Audia di Background
        new Thread() {
            @Override
            public void run() {
            try { 
            player.play();
            }
            catch (Exception e) { System.out.println(e); }
            }
        }.start();
    }

    public void close(){
    player.close();
    }
    
}
    

