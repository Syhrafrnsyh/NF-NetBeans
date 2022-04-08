/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nf.view;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import nf.Koneksi;
import nf.entity.Barang;
import nf.entity.Masuk;
import nf.entity.Supplier;
import nf.entity.User;
import nf.implement.ImplBarang;
import nf.implement.ImplMasuk;
import nf.implement.ImplSupplier;
import nf.implement.ImplUser;
import nf.interfaces.InterBarang;
import nf.interfaces.InterMasuk;
import nf.interfaces.InterSupplier;
import nf.interfaces.InterUser;

/**
 *
 * @author ACER
 */
public class TransaksiMasuk extends javax.swing.JInternalFrame {

    private List<User> recUser = new ArrayList<User>();
    private List<Supplier> recSupplier = new ArrayList<Supplier>();
    private List<Barang> recBarang = new ArrayList<Barang>();
    private List<Masuk> recMasuk = new ArrayList<Masuk>();
    InterUser daoUser;
    InterSupplier daoSupplier;
    InterBarang daoBarang;
    InterMasuk daoMasuk;


    public TransaksiMasuk() {
        initComponents();
        daoUser = new ImplUser();
        daoSupplier = new ImplSupplier();
        daoBarang = new ImplBarang();
        daoMasuk = new ImplMasuk();
        this.statusAwal();
        add(txtTot);
        add(txtKett);
        
    }

    void loadCombo() {
        cmbSupplier.removeAllItems();
        try {
            recSupplier = daoSupplier.getAll();
            for (Supplier s : recSupplier) {
                cmbSupplier.addItem(s.getNama());
            }
        } catch (SQLException ex) {
            Logger.getLogger(TransaksiMasuk.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void kosongkanTabel() {
        tabelMasuk.setModel(new DefaultTableModel(new Object[][]{{null, null, null, null}}, new String[]{"ID", "Nama", "Stok", "Jumlah"}));
    }

    void isiTabelMasuk() {
        Object data[][] = new Object[recMasuk.size()][4];
        int x = 0;
        for (Masuk p : recMasuk){
            data[x][0] = p.getBarang().getId();
            data[x][1] = p.getBarang().getNama();
            data[x][2] = p.getBarang().getStok();
            data[x][3] = p.getJumlah();
            x++;
        }
        String judul[] = {"ID", "Nama", "Stok", "Jumlah"};
        tabelMasuk.setModel(new DefaultTableModel(data, judul));
        jScrollPane1.setViewportView(tabelMasuk);
    }

    void isiTabelCari() {
        Object data[][] = new Object[recBarang.size()][5];
        int x = 0;
        for (Barang b : recBarang) {
            data[x][0] = b.getId();
            data[x][1] = b.getKode();
            data[x][2] = b.getNama();
            data[x][3] = b.getStok();
            data[x][4] = b.getHarga();
            x++;
        }
        String judul[] = {"ID", "Kode", "Nama", "Stok", "Harga"};
        tabelCari.setModel(new DefaultTableModel(data, judul));
        jScrollPane2.setViewportView(tabelCari);
    }

    void setTanggal(){
    
    Locale INDONESIA = new java.util.Locale("id", "ID");
        Date sekarang = Calendar.getInstance().getTime();
        //
        //SimpleDateFormat df = 
        //new SimpleDateFormat("EEEE, dd MMMMM yyyy HH:mm:ss",new java.util.Locale("id"));
        //String formattedDate = df.format(sekarang.getTime());
        //txtT.setText(formattedDate);
        //
        txtTanggal.setDate(sekarang);
        
        //
        //Convert Calendar to Date 
        //Calendar.getInstance(Locale.getDefault());
        //Calendar sekarang = Calendar.getInstance();
        //sekarang.add(Calendar.DATE, 0);
        //Date date = sekarang.getTime();
        //SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",new java.util.Locale("id"));
        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM dd, yyyy HH:mm:ss", Locale.getDefault());
        //JOptionPane.showMessageDialog(null, ft.format(date));
        //txtTanggal.setDate(date);
        // Creating calendar objects
        //Locale INDONESIA = new java.util.Locale("id", "ID");
        //Calendar calendar = Calendar.getInstance(INDONESIA);
        //java.util.Date currentDate = calendar.getTime();
        
        
  //DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
  //Calendar cal = Calendar.getInstance();
  //System.out.println(dateFormat.format(cal.getTime()));
  //txtTanggal.setDate(cal);
		//Calendar cal = Calendar.getInstance();
		//Date currentDateTime = cal.getTime();
		//jLabel1.setText("Current Date Time : "+currentDateTime);
                //JOptionPane.showMessageDialog(null, currentDateTime);
                //txtTanggal.setDate(currentDateTime);
        //Date date = new Date();
        //Date sqlDate = new Date(date.getTime());
        //java.sql.Date sqlDate=new java.sql.Date(date.getTime());
        //Timestamp sqlTime = new Timestamp(date.getTime());
        //txtTanggal.setDate(sqlTime);
    }
    
    void NoAuto() {
        try {
            String sql = "select * from masuk order by no desc";
            Statement st = Koneksi.dbConnection().createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                String No = rs.getString("no").substring(1);
                //String AN = "" + (Integer.parseInt(txtNO.getText()) + 1);
                String AN = "" + (Integer.parseInt(No) + 1);
                String Nol = "";

                if(AN.length()==1)
                    {Nol = "000";
                }else if(AN.length()==2){
                    Nol = "00";
                }else if(AN.length()==3){
                    Nol = "0";
                }else if(AN.length()==4){
                    Nol = "";
                }
                txtNO.setText("T" + Nol + AN);//sesuaikan dengan variable namenya
            } else {
                txtNO.setText("T0001");//sesuaikan dengan variable namenya
            }
            rs.close();
            //con.close();
        } catch (Exception e) {
            e.printStackTrace();//penanganan masalah
        }
    }
    
    double totalHarga() {
        double t = 0;
        for (Masuk mk : recMasuk) {
            t += mk.getTotal();
        }
        return t;
    }

    void statusAwal() {
        loadCombo();
        setTanggal();
        NoAuto();
        kosongkanTabel();
        txtTot.setText("");
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
        jLabel3 = new javax.swing.JLabel();
        txtCari = new javax.swing.JTextField();
        btnCari = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelCari = new javax.swing.JTable();
        btnOk1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cmbSupplier = new javax.swing.JComboBox();
        txtTanggal = new com.toedter.calendar.JDateChooser();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelMasuk = new javax.swing.JTable();
        btnLihat = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        btnAmbil = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        txtJumlah = new javax.swing.JTextField();
        txtT = new javax.swing.JLabel();
        txtNO = new javax.swing.JTextField();
        txtProses = new javax.swing.JButton();
        txtKett = new javax.swing.JTextField();
        txtTot = new javax.swing.JTextField();

        jDialog1.setTitle("Pencarian Barang\n");
        jDialog1.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                jDialog1WindowOpened(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Cari berdasarkan ID Nama"));

        jLabel3.setText("Barang :");

        txtCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCariKeyPressed(evt);
            }
        });

        btnCari.setText("Cari");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCari)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCari, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCari))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Tabel Cari"));

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

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnOk1.setText("Masukan ID");
        btnOk1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOk1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDialog1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnOk1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnOk1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setClosable(true);
        setIconifiable(true);
        setTitle("Barang Masuk");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Barang Masuk"));

        jLabel1.setText("Supplier :");

        jLabel2.setText("Tanggal :");

        cmbSupplier.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

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
                    .addComponent(cmbSupplier, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtTanggal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cmbSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(txtTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Tabel"));

        tabelMasuk.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tabelMasuk);

        btnLihat.setText("Lihat");
        btnLihat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLihatActionPerformed(evt);
            }
        });

        btnHapus.setText("Hapus");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        btnAmbil.setText("Ambil");
        btnAmbil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAmbilActionPerformed(evt);
            }
        });

        jLabel5.setText("ID Barang :");

        jLabel6.setText("Jumlah :");

        txtT.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        txtT.setText("Tanggal");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 449, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtJumlah)
                            .addComponent(txtID))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnAmbil, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnLihat, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(52, 52, 52)
                        .addComponent(txtNO, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLihat)
                    .addComponent(jLabel5)
                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnAmbil)
                        .addComponent(jLabel6))
                    .addComponent(txtJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnHapus)
                    .addComponent(txtT)
                    .addComponent(txtNO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        txtProses.setText("Proses");
        txtProses.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProsesActionPerformed(evt);
            }
        });

        txtKett.setText("ACCEPT");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(txtTot, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtKett, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(184, 184, 184)
                        .addComponent(txtProses, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                    .addComponent(txtProses)
                    .addComponent(txtKett, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jPanel1.getAccessibleContext().setAccessibleName("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLihatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLihatActionPerformed
        // TODO add your handling code here:
        jDialog1.setSize(400, 350);
        jDialog1.setVisible(true);
    }//GEN-LAST:event_btnLihatActionPerformed

    private void txtCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariKeyPressed
        try {
            // TODO add your handling code here:
            recBarang = daoBarang.getById(txtCari.getText());
            recBarang = daoBarang.getByName(txtCari.getText());
            this.isiTabelCari();
        } catch (SQLException ex) {
            Logger.getLogger(TransaksiMasuk.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_txtCariKeyPressed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        // TODO add your handling code here:
        recMasuk.remove(tabelMasuk.getSelectedRow());
        this.isiTabelMasuk();
    }//GEN-LAST:event_btnHapusActionPerformed

    private void txtProsesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProsesActionPerformed
        // TODO add your handling code here:
        for (Masuk m : recMasuk) {
            try {
            daoMasuk.insert(m);
            System.out.println(m.getJumlah());
            } catch (SQLException ex) {
                Logger.getLogger(TransaksiMasuk.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        recBarang.clear();
        JOptionPane.showMessageDialog(this, "Proses Transaksi Berhasil..", "Informasi", JOptionPane.INFORMATION_MESSAGE);
        txtID.setText("");
        this.statusAwal();
    }//GEN-LAST:event_txtProsesActionPerformed

    private void btnAmbilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAmbilActionPerformed
        // TODO add your handling code here:
        try {
            Barang b = daoBarang.getByOne(txtID.getText());
            Masuk p = new Masuk();
            Supplier d = daoSupplier.getByNama((String) cmbSupplier.getSelectedItem());
            p.setSupplier(d);
            p.setBarang(b);
            p.setJumlah(Integer.parseInt(txtJumlah.getText()));
            p.setTanggal(txtTanggal.getDate());
            double total = Double.parseDouble(txtJumlah.getText()) * b.getHarga();
            p.setTotal(total);
            //java.sql.Timestamp tanggal = new java.sql.Timestamp(new java.util.Date().getTime());
            //Calendar calendarInstance = Calendar.getInstance();
            ///java.sql.Date timestamp = new java.sql.Date(calendarInstance.getTime());
            //p.setTanggal(timestamp);
            //java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
            //p.setCreatedAt(date);
            p.setNo(String.valueOf(txtNO.getText()));
            p.setKet(String.valueOf(txtKett.getText()));
            //p.setNo(txtNO.getText());
            txtTot.setText(String.valueOf(totalHarga()));
            recMasuk.add(p);
            txtCari.setText("");
            txtJumlah.setText("");
            txtID.setText("");
            this.isiTabelMasuk();
        } catch (SQLException ex) {
            Logger.getLogger(TransaksiMasuk.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnAmbilActionPerformed

    private void btnOk1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOk1ActionPerformed
        // TODO add your handling code here:
        Barang b = recBarang.get(tabelCari.getSelectedRow());
        txtID.setText(b.getId());
        txtJumlah.requestFocus();
        jDialog1.setVisible(false);
    }//GEN-LAST:event_btnOk1ActionPerformed

    private void jDialog1WindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_jDialog1WindowOpened
        // TODO add your handling code here:
        try {
            // TODO add your handling code here:
            recBarang = daoBarang.getById(txtCari.getText());
            recBarang = daoBarang.getByName(txtCari.getText());
            this.isiTabelCari();
        } catch (SQLException ex) {
            Logger.getLogger(TransaksiMasuk.class.getName()).log(Level.SEVERE, null, ex);
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
    private javax.swing.JButton btnAmbil;
    private javax.swing.JButton btnCari;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnLihat;
    private javax.swing.JButton btnOk1;
    private javax.swing.JComboBox cmbSupplier;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tabelCari;
    private javax.swing.JTable tabelMasuk;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtJumlah;
    private javax.swing.JTextField txtKett;
    private javax.swing.JTextField txtNO;
    private javax.swing.JButton txtProses;
    private javax.swing.JLabel txtT;
    private com.toedter.calendar.JDateChooser txtTanggal;
    private javax.swing.JTextField txtTot;
    // End of variables declaration//GEN-END:variables
}
