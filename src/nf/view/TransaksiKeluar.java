/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nf.view;


import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import nf.Koneksi;
import nf.entity.Barang;
import nf.entity.Keluar;
import nf.entity.User;
import nf.implement.ImplBarang;
import nf.implement.ImplKeluar;
import nf.implement.ImplUser;
import nf.interfaces.InterBarang;
import nf.interfaces.InterKeluar;
import nf.interfaces.InterUser;

/**
 *
 * @author ACER
 */
public class TransaksiKeluar extends javax.swing.JInternalFrame implements ActionListener{
    
    private List<Keluar> recKeluar = new ArrayList<Keluar>();
    private List<Barang> recBarang = new ArrayList<Barang>();
    InterBarang daoBarang;
    InterKeluar daoKeluar;
    InterUser daoUser;
    User u;
    //private int id;
    public TransaksiKeluar(String user) {
        try {
            initComponents();
            daoBarang = new ImplBarang();
            daoKeluar = new ImplKeluar();
            daoUser = new ImplUser();
            u = daoUser.getByUsername(user);
            System.out.println(u.getNama());
            LabelUser.setText(u.getNama());
        } catch (SQLException ex) {
            Logger.getLogger(TransaksiKeluar.class.getName()).log(Level.SEVERE, null, ex);
        }
        btnCetak.addActionListener(this);

        
        this.statusAwal();
        add(txtKet);
    }

    void isiTabelKeluar() {
        Object data[][] = new Object[recKeluar.size()][5];
        int x = 0;
        for (Keluar k : recKeluar) {
            data[x][0] = k.getBarang().getId();
            data[x][1] = k.getBarang().getNama();
            data[x][2] = k.getJumlah();
            data[x][3] = k.getBarang().getHarga();
            data[x][4] = k.getTotal();
            x++;
        }
        String judul[] = {"ID Barang","Nama", "Jumlah", "Harga","Total"};
        tabelPembelian.setModel(new DefaultTableModel(data, judul));
        jScrollPane1.setViewportView(tabelPembelian);
    }

    void kosongkanTabelKeluar() {
        tabelPembelian.setModel(new DefaultTableModel(new Object[][]{{"", "", "", "", "", ""}}, new String[]{"ID", "Nama", "Jumlah", "Harga","Total","Ket"}));
    }
    
    void KosongkanTabelCari() {
        tabelCari.setModel(new DefaultTableModel(new Object[][]{{"", "", "",""}}, new String[]{"ID","Nama","Stok","Harga"}));
    }
    
    void isiTabelCari() {
        Object data[][] = new Object[recBarang.size()][4];
        int x = 0;
        for (Barang b : recBarang) {
            data[x][0] = b.getId();
            data[x][1] = b.getNama();
            data[x][2] = b.getStok();
            data[x][3] = b.getHarga();
            x++;
        }
        String judul[] = {"ID","Nama","Stok","Harga"};
        tabelCari.setModel(new DefaultTableModel(data, judul));
        jScrollPane2.setViewportView(tabelCari);
    }    

    double totalHarga() {
        double t = 0;
        for (Keluar k : recKeluar) {
            t += k.getTotal();
        }
        return t;
    }

    void bayarHarga() {
    double xTotal = Double.parseDouble(txtTotal.getText());
    double xBayar = Double.parseDouble(txtBayar.getText());
    double xKembali = xBayar-xTotal;
    //String xtotal=Double.toString(xKembali);
    //txtKembali.setText(xKembali); 
    //txtKembali.setText(String.valueOf(xKembali));
    txtKembali.setText(Double.toString(xKembali));

    //System.out.println("tally = " + tally);
    }
    /**
    int cariID() {
        try {
            String sql = "select id from keluar order by id desc";
            Statement st = Koneksi.dbConnection().createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (SQLException ex) {
            Logger.getLogger(FormSupplier.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ++id;
    }
    */
        
    public void Auto(){
        try{
            //String sql = "select id from keluar order by id desc";
            //String sql="select no from keluar order by id asc";
            String sql = "SELECT id_keluar AS no FROM keluar ORDER BY no DESC";
            Statement st = Koneksi.dbConnection().createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()){
                int No = Integer.parseInt(rs.getString("no")) + 1;
                //NoTXT.setText(Integer.toString(No));
                NoTXT.setText("NF-"+No);
            }else{
                int No = 1;
                //NoTXT.setText(Integer.toString(No));
                NoTXT.setText("NF-"+No);
            }
            rs.close();
        }catch (Exception e){

        }   
    }
    
    void statusAwal() {
        this.kosongkanTabelKeluar();
        this.KosongkanTabelCari();
        txtTotal.setText("");
        Auto();
        //cariID();
        //NoTXT.setText(String.valueOf(cariID()));
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog1 = new javax.swing.JDialog();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        bCari = new javax.swing.JButton();
        txtCari = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelCari = new javax.swing.JTable();
        bMasukan = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        txtJumlah = new javax.swing.JTextField();
        bLihat = new javax.swing.JButton();
        bAmbil = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelPembelian = new javax.swing.JTable();
        bHapus = new javax.swing.JButton();
        txtTotal = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtBayar = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtKembali = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtTanggal = new javax.swing.JFormattedTextField();
        LabelUser = new javax.swing.JLabel();
        bProses = new javax.swing.JButton();
        btnCetak = new javax.swing.JButton();
        NoTXT = new javax.swing.JTextField();
        txtKet = new javax.swing.JTextField();

        jDialog1.setTitle("Pencarian Barang");
        jDialog1.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                jDialog1WindowOpened(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Pencarian"));

        jLabel5.setText("ID Barang :");

        bCari.setText("Cari");
        bCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCariActionPerformed(evt);
            }
        });

        txtCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCariKeyPressed(evt);
            }
        });

        tabelCari.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabelCari.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelCariMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabelCari);

        bMasukan.setText("Masukan");
        bMasukan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bMasukanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCari)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bCari, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(bMasukan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(bCari)
                    .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bMasukan)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Barang Keluar");
        setVisible(true);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Barang Keluar"));

        jLabel1.setText("ID Barang :");

        jLabel2.setText("Jumlah :");

        bLihat.setText("Lihat");
        bLihat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bLihatActionPerformed(evt);
            }
        });

        bAmbil.setText("Ambil");
        bAmbil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAmbilActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtID)
                    .addComponent(txtJumlah))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(bAmbil, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bLihat, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bLihat))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bAmbil))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Tabel Barang"));

        tabelPembelian.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tabelPembelian);

        bHapus.setText("Hapus");
        bHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bHapusActionPerformed(evt);
            }
        });

        txtTotal.setEditable(false);

        jLabel3.setText("Total :");

        txtBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBayarActionPerformed(evt);
            }
        });
        txtBayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBayarKeyReleased(evt);
            }
        });

        jLabel6.setText("Bayar :");

        jLabel4.setText("Kembalian :");

        jLabel7.setText("*Tekan enter pada field bayar");

        txtTanggal.setEditable(false);
        txtTanggal.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("dd MMMM yyyy"))));
        txtTanggal.setValue(new java.util.Date());

        LabelUser.setText("User");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(bHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LabelUser, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(10, 63, Short.MAX_VALUE)
                        .addComponent(jLabel4))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
                    .addComponent(txtBayar)
                    .addComponent(txtKembali))
                .addContainerGap())
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 474, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(191, 191, 191)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(bHapus)
                    .addComponent(LabelUser))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtKembali, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 96, Short.MAX_VALUE)))
        );

        bProses.setText("Proses");
        bProses.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bProsesActionPerformed(evt);
            }
        });

        btnCetak.setText("Cetak Struk");

        txtKet.setText("TERJUAL");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(NoTXT, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtKet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCetak, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bProses, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bProses)
                    .addComponent(btnCetak)
                    .addComponent(NoTXT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtKet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bLihatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bLihatActionPerformed
        // TODO add your handling code here:
        jDialog1.setSize(350, 280);
        jDialog1.setVisible(true);
    }//GEN-LAST:event_bLihatActionPerformed

    private void bAmbilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAmbilActionPerformed
        try {
            // TODO add your handling code here:
            Keluar k = new Keluar();
            Barang b = daoBarang.getByOne(txtID.getText());
            double total = Double.parseDouble(txtJumlah.getText()) * b.getHarga();
            k.setBarang(b);
            k.setJumlah(Integer.parseInt(txtJumlah.getText()));
            k.setTanggal((Date) txtTanggal.getValue());
            k.setTotal(total);
            k.setUser(u);
            k.setNo(String.valueOf(NoTXT.getText()));
            k.setKet(String.valueOf(txtKet.getText()));
            recKeluar.add(k);
        } catch (SQLException ex) {
            Logger.getLogger(TransaksiKeluar.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.isiTabelKeluar();
        //txtTotal.setText(Double.toString(totalHarga()));
        txtTotal.setText(String.valueOf(totalHarga()));
        txtID.setText("");
        txtJumlah.setText("");
        txtID.requestFocus();
    }//GEN-LAST:event_bAmbilActionPerformed

    private void bHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bHapusActionPerformed
        // TODO add your handling code here:
        recKeluar.remove(tabelPembelian.getSelectedRow());
        this.isiTabelKeluar();
    }//GEN-LAST:event_bHapusActionPerformed

    private void txtCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariKeyPressed
        try {
            // TODO add your handling code here:
            recBarang = daoBarang.getById(txtCari.getText());
            recBarang = daoBarang.getByName(txtCari.getText());
            this.isiTabelCari();
        } catch (SQLException ex) {
            Logger.getLogger(TransaksiKeluar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_txtCariKeyPressed

    private void bCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCariActionPerformed
        try {
            // TODO add your handling code here:
            recBarang = daoBarang.getById(txtCari.getText());
            recBarang = daoBarang.getByName(txtCari.getText());
            this.isiTabelCari();
        } catch (SQLException ex) {
            Logger.getLogger(TransaksiKeluar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_bCariActionPerformed

    private void bMasukanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bMasukanActionPerformed
        // TODO add your handling code here:
        Barang b = recBarang.get(tabelCari.getSelectedRow());
        txtID.setText(b.getId());
        txtJumlah.requestFocus();
        txtCari.setText("");
        recBarang.clear();
        jDialog1.setVisible(false);
    }//GEN-LAST:event_bMasukanActionPerformed

    private void bProsesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bProsesActionPerformed
        // TODO add your handling code here:
        if(!recKeluar.isEmpty()){
        for (Keluar k : recKeluar) {
            try {
                daoKeluar.insert(k);
            } catch (SQLException ex) {
                Logger.getLogger(TransaksiKeluar.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        recKeluar.clear();
        JOptionPane.showMessageDialog(this, "Pembelian Berhasil Total harga " +txtTotal.getText(), "Sukses", JOptionPane.INFORMATION_MESSAGE);
        this.statusAwal();
        }else{
            JOptionPane.showMessageDialog(this, "Tidak ada transaksi pembelian", "Kesalahan", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_bProsesActionPerformed

    private void txtBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBayarActionPerformed
        // TODO add your handling code here:
        bayarHarga();
    }//GEN-LAST:event_txtBayarActionPerformed

    private void txtBayarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBayarKeyReleased
        // TODO add your handling code here:
     if (evt.getKeyCode() == 10){
     txtKembali.requestFocus();}
    }//GEN-LAST:event_txtBayarKeyReleased

    private void jDialog1WindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_jDialog1WindowOpened
        // TODO add your handling code here:
        //isiTabelCari();
        try {
            recBarang = daoBarang.getAll();
            // TODO add your handling code here:
            //recBarang = daoBarang.getById(txtCari.getText());
            //recBarang = daoBarang.getByName(txtCari.getText());
            this.isiTabelCari();
        } catch (SQLException ex) {
            Logger.getLogger(TransaksiKeluar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jDialog1WindowOpened

    private void tabelCariMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelCariMouseClicked
        // TODO add your handling code here:
        //DefaultTableModel model = (DefaultTableModel) tabelCari.getModel();
        int selectedRowIndex = tabelCari.getSelectedRow();
        //txtCari.setText(tabelCari.getValueAt(selectedRowIndex, 1).toString());
        txtID.setText(tabelCari.getValueAt(selectedRowIndex, 0).toString());
    }//GEN-LAST:event_tabelCariMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LabelUser;
    private javax.swing.JTextField NoTXT;
    private javax.swing.JButton bAmbil;
    private javax.swing.JButton bCari;
    private javax.swing.JButton bHapus;
    private javax.swing.JButton bLihat;
    private javax.swing.JButton bMasukan;
    private javax.swing.JButton bProses;
    private javax.swing.JButton btnCetak;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tabelCari;
    private javax.swing.JTable tabelPembelian;
    private javax.swing.JTextField txtBayar;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtJumlah;
    private javax.swing.JTextField txtKembali;
    private javax.swing.JTextField txtKet;
    private javax.swing.JFormattedTextField txtTanggal;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables

public Object getValueAt(JTable tabelPembelian,int rowIndex, int columnIndex)
{
    return tabelPembelian.getModel().getValueAt(rowIndex, columnIndex);
}

public void actionPerformed(ActionEvent act) {
    Locale locale = new Locale("id", "ID");
    //DateFormat df = new SimpleDateFormat("EEEE, dd MMMMM yyyy hh:mm:ss a", locale);
    DateFormat df = new SimpleDateFormat("EEEE, dd MMMMM yyyy hh:mm:ss", locale);
    NumberFormat nf = NumberFormat.getCurrencyInstance(locale);
    String b = NoTXT.getText();
    String us = LabelUser.getText();
    Document doc = new Document();
    
try
{
int tou = 8;
int siz = 380;
System.out.println("siz"+siz);
Rectangle test = new Rectangle(223,siz);
doc.setPageSize(test);
int count = tabelPembelian.getRowCount();
double sumBerat =0;
double sumTinggi =0;
PdfWriter.getInstance(doc, new FileOutputStream("D://Struk//NF-"+b+".pdf"));
//PdfWriter.getInstance(doc, new FileOutputStream("D://NF-.pdf"));

doc.open();

doc.add(new Paragraph("            NF AQUATIC",FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Font.NORMAL,BaseColor.BLACK)));
doc.add(new Paragraph("               Jl. Mampang Prapatan VII No.10",FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL,BaseColor.BLACK)));
doc.add(new Paragraph("--------------------------------------------------------",FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL,BaseColor.BLACK)));
doc.add(new Paragraph(""+df.format(new Date()),FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL,BaseColor.BLACK)));
doc.add(new Paragraph(""+NoTXT.getText(),FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL,BaseColor.BLACK)));
doc.add(new Paragraph(""+us+"",FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL,BaseColor.BLACK)));
doc.add(new Paragraph("--------------------------------------------------------",FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL,BaseColor.BLACK)));
//doc.add(new Paragraph(" "));
//
PdfPTable pdfTable = new PdfPTable(4);
pdfTable.setWidthPercentage(100f);
pdfTable.setSpacingBefore(0f);
pdfTable.setSpacingAfter(0f);

//"1.5f, 1f, 0.2f, 0.2f, 0.2f, 0.2f, 0.2f"
float[] columnWidths = { 1f, 0.5f, 1f, 1f }; // Second column will be                                // twice as first and third
pdfTable.setWidths(columnWidths);
PdfPCell cell = new PdfPCell(new Phrase("Barang",FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD,BaseColor.BLACK)));
cell.setBorder(Rectangle.NO_BORDER);
pdfTable.addCell(cell);
//cell.setPhrase(new Phrase("Barang",FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL,BaseColor.BLACK)));
//pdfTable.addCell(cell);
cell.setPhrase(new Phrase("Qty",FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD,BaseColor.BLACK)));
pdfTable.addCell(cell);
cell.setPhrase(new Phrase("Harga",FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD,BaseColor.BLACK)));
pdfTable.addCell(cell);
cell.setPhrase(new Phrase("Total",FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD,BaseColor.BLACK)));
pdfTable.addCell(cell);


for (int a=0;a<count;a++){
//model.setColumnIdentifiers(new Object[]{"ID Barang","Nama", "Jumlah", "Harga","Total"});
        //Object a0 = getValueAt(table, a, 0);
        Object a1 = getValueAt(tabelPembelian, a, 1);
        Object a2 = getValueAt(tabelPembelian, a, 2);
        Object a3 = getValueAt(tabelPembelian, a, 3);
        Object a4 = getValueAt(tabelPembelian, a, 4);
        
        double dataTinggi = Double.valueOf(tabelPembelian.getValueAt(a, 4).toString());
        sumTinggi += dataTinggi;
        //String aq = a0.toString();
        String bb = a1.toString();
        String c = a2.toString();
        String d = a3.toString();
        String e = a4.toString();
//
//cell.setPhrase(new Phrase(aq,FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL,BaseColor.BLACK)));
//pdfTable.addCell(cell);
//
cell.setPhrase(new Phrase(bb,FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL,BaseColor.BLACK)));
pdfTable.addCell(cell);
//
cell.setPhrase(new Phrase(c,FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL,BaseColor.BLACK)));
pdfTable.addCell(cell);
//
cell.setPhrase(new Phrase(d,FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL,BaseColor.BLACK)));
pdfTable.addCell(cell);
//
cell.setPhrase(new Phrase(e,FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL,BaseColor.BLACK)));
pdfTable.addCell(cell);
}

doc.add(pdfTable);

String sum1 = String.format("%,.2f", sumTinggi);
doc.add(new Paragraph("--------------------------------------------------------",FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL,BaseColor.BLACK)));
doc.add(new Paragraph("                     Total Pembelian = "+sum1+"",FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL,BaseColor.BLACK)));

//double xTotal = Double.parseDouble(txtTotal.getText());
double xBayar = Double.parseDouble(txtBayar.getText());
double xKembali = xBayar-sumTinggi;
String byr = String.format("%,.2f", xBayar);
doc.add(new Paragraph("                                    Bayar  = "+byr+"",FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL,BaseColor.BLACK)));
String kmbli = String.format("%,.2f", xKembali);

doc.add(new Paragraph("                                    Kembali = "+kmbli+"",FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL,BaseColor.BLACK)));
            doc.add(new Paragraph(" "));
            doc.add(new Paragraph("                      Terima Kasih",FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL,BaseColor.BLACK)));
            doc.add(new Paragraph("--------------------------------------------------------",FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.NORMAL,BaseColor.BLACK)));

//
doc.close();
JOptionPane.showMessageDialog(null,"Data berhasil di Export ke PDF ","Pesan",JOptionPane.INFORMATION_MESSAGE);
}
catch(Exception ex)
{
        System.out.println(ex);
}

}
    
}
