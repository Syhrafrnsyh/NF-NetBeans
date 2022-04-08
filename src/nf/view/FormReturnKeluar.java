/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nf.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import nf.entity.Barang;
import nf.entity.Keluar;
import nf.entity.ReturnKeluar;
import nf.entity.Supplier;
import nf.entity.User;
import nf.implement.ImplBarang;
import nf.implement.ImplKeluar;
import nf.implement.ImplReturnKeluar;
import nf.implement.ImplSupplier;
import nf.implement.ImplUser;
import nf.interfaces.InterBarang;
import nf.interfaces.InterKeluar;
import nf.interfaces.InterReturnKeluar;
import nf.interfaces.InterSupplier;
import nf.interfaces.InterUser;

/**
 *
 * @author ACER
 */
public class FormReturnKeluar extends javax.swing.JInternalFrame {

    nf.Koneksi Koneksi = new nf.Koneksi();
    private List<User> recUser = new ArrayList<User>();
    private List<Supplier> recSupplier = new ArrayList<Supplier>();
    private List<Barang> recBarang = new ArrayList<Barang>();
    private List<Keluar> recKeluar = new ArrayList<Keluar>();
    private List<ReturnKeluar> recReturnKeluar = new ArrayList<ReturnKeluar>();
    InterUser daoUser;
    InterSupplier daoSupplier;
    InterBarang daoBarang;
    InterKeluar daoKeluar;
    InterReturnKeluar daoReturnKeluar;

    public FormReturnKeluar() {
        initComponents();
        daoUser = new ImplUser();
        daoSupplier = new ImplSupplier();
        daoBarang = new ImplBarang();
        daoKeluar = new ImplKeluar();
        daoReturnKeluar = new ImplReturnKeluar();
    this.statusAwal();
    //
    BarCodeLengthDocumentListener lengthListener = new BarCodeLengthDocumentListener(7, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String text = e.getActionCommand();
            JOptionPane.showMessageDialog(FormReturnKeluar.this, text);
        }
    });
    txtKet.getDocument().addDocumentListener(lengthListener);
    //
    Rt.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            txtKet.setText(null);
            txtKet.requestFocusInWindow();
            Thread t = new Thread(new Simulator());
            t.start();
        }
    });
    //
    //
    }

    public final void tampildata() {
    DefaultTableModel model = new DefaultTableModel();
    //model.setColumnIdentifiers(new Object[]{"ID", "NO", "ID_BARANG", "NAMA BARANG", "TANGGAL", "JUMLAH", "TOTAL", "ID USER", "NAMA USER"});
    model.addColumn("ID");
    model.addColumn("NO");
    model.addColumn("ID_BARANG");
    model.addColumn("NAMA_BARANG");
    model.addColumn("TANGGAL");
    model.addColumn("JUMLAH");
    model.addColumn("TOTAL");
    model.addColumn("ID_USER");
    model.addColumn("NAMA_USER");
    model.addColumn("KETERANGAN");
        try {
            Connection con = Koneksi.dbConnection();
            java.sql.Statement stm = con.createStatement();
            String sql = "SELECT keluar.id_keluar, keluar.no, barang.id_barang as id_barang, barang.nama as nama_barang, keluar.tanggal, keluar.jumlah, keluar.total, user.id_user as id_user, user.username as nama_user, keluar.ket FROM keluar JOIN barang ON barang.id_barang = keluar.id_barang JOIN user ON user.id_user = keluar.id_user";
            //java.sql.ResultSet sql = stm.executeQuery("SELECT tmbarang.id, tmbarang.kode, tmbarang.nama, tmkategori.nama as kategori, tmbarang.stok, tmbarang.satuan FROM tmbarang JOIN tmkategori ON tmkategori.id = tmbarang.id_kategori");
            //java.sql.ResultSet rs = stm.executeQuery("SELECT keluar.id_keluar, keluar.no, barang.id_barang as id_barang, barang.nama as nama_barang, keluar.tanggal, keluar.jumlah, keluar.total FROM keluar JOIN barang ON barang.id_barang = keluar.id_barang");
            java.sql.ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getString(7),
                    rs.getString(8),
                    rs.getString(9),
                    rs.getString(10)
                });
            }
            tabelCari.setModel(model);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Ada kesalahan" + e);
        }
    }
    
    void isiTabelReturn(){
        Object data[][] = new Object[recReturnKeluar.size()][11];
        int x = 0;
        for (ReturnKeluar r : recReturnKeluar) {
            data[x][0] = r.getNoretrun();
            data[x][1] = r.getNamaretrun();
            data[x][2] = r.getTanggalReturn();
            data[x][3] = r.getKeluar().getId();
            data[x][4] = r.getKeluar().getNo();
            data[x][5] = r.getBarang().getId();
            data[x][6] = r.getKeluar().getTanggal();
            data[x][7] = r.getKeluar().getJumlah();
            data[x][8] = r.getKeluar().getTotal();
            data[x][9] = r.getUser().getId();
            data[x][10] = r.getKet();
            x++;
        }
        String judul[] = {"NoRetrun","Nama","TanggalReturn","IDkeluar","NoTransaksi","NamaBarang","Tanggal","Jumlah","Total","User","KETERANGAN"};
        tabelReturn.setModel(new DefaultTableModel(data, judul));
        jScrollPane1.setViewportView(tabelReturn);
    }
    
    void kosongkanTabel() {
        tabelReturn.setModel(new DefaultTableModel(new Object[][]{{"","","","","","","","","",""}}, new String[]{"NoRetrun","Nama","TanggalReturn","NoTransaksi","NamaBarang","Tanggal","Jumlah","Total","User","Keterangan"}));
    }

    void setTanggal(){
    Locale INDONESIA = new java.util.Locale("id", "ID");
        Date sekarang = Calendar.getInstance().getTime();
        Jtanggal.setDate(sekarang);
    }
    
    void NoA() {
        try {
            String sql = "select * from kembalikeluar order by noretrun desc";
            Statement st = Koneksi.dbConnection().createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                String No = rs.getString("noretrun").substring(1);
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
                txtNO.setText("R" + Nol + AN);//sesuaikan dengan variable namenya
            } else {
                txtNO.setText("R0001");//sesuaikan dengan variable namenya
            }
            rs.close();
            //con.close();
        } catch (Exception e) {
            e.printStackTrace();//penanganan masalah
        }
    }
    
    public void getSum()
    {
        double sum = 0;
        for(int X = 0; X < tabelCari.getRowCount(); X++)
        {
            sum = sum + Double.parseDouble(tabelCari.getValueAt(X, 6).toString());
        }
        //double xBayar = Double.parseDouble(txtBayar.getText());
        //double xKembali = xBayar-sumTinggi;
        String m = String.format("%,.2f", sum);
        LT.setText(m);
    }

    void statusAwal(){
        NoA();
        tampildata();
        getSum();
        setTanggal();
        kosongkanTabel();
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
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelCari = new javax.swing.JTable();
        LT = new javax.swing.JLabel();
        LID = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        Jtanggal = new com.toedter.calendar.JDateChooser();
        txtN = new javax.swing.JTextField();
        txtNO = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelReturn = new javax.swing.JTable();
        btnLihat = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        txtNoTransaksi = new javax.swing.JTextField();
        txtJumlah = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        btnAmbil = new javax.swing.JButton();
        txtTotal = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtTanggal = new javax.swing.JFormattedTextField();
        txtUser = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtKet = new javax.swing.JTextField();
        Rt = new javax.swing.JButton();
        txtProses = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        KID = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        jDialog1.setTitle("Pencarian Barang\n");

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Data Barang Keluar", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

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
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 471, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
                .addContainerGap())
        );

        LT.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        LT.setText("Total");

        LID.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        LID.setText("ID");

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jDialog1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(LID, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(LT, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LT)
                    .addComponent(LID))
                .addContainerGap())
        );

        setClosable(true);
        setIconifiable(true);
        setTitle("Return");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Return-\"Nama : Customer\""));

        jLabel2.setText("Tanggal :");

        txtN.setText("Customer");

        jLabel4.setText("Nama :");

        jLabel11.setText("No :");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Jtanggal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 449, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtN, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNO, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(Jtanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Tabel"));

        tabelReturn.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tabelReturn);

        btnLihat.setText("Lihat");
        btnLihat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLihatActionPerformed(evt);
            }
        });

        jLabel5.setText("No :");

        jLabel6.setText("Jumlah :");

        jLabel7.setText("Total :");

        btnAmbil.setText("Ambil");
        btnAmbil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAmbilActionPerformed(evt);
            }
        });

        jLabel8.setText("ID :");

        txtTanggal.setEditable(false);
        txtTanggal.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("dd MMMM yyyy"))));
        txtTanggal.setValue(new java.util.Date());

        jLabel9.setText("Tanggal :");

        jLabel10.setText("User :");

        Rt.setText("Return");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 504, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addComponent(jLabel7))
                            .addComponent(jLabel6)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtUser)
                            .addComponent(txtTanggal)
                            .addComponent(txtTotal)
                            .addComponent(txtJumlah)
                            .addComponent(txtNoTransaksi)
                            .addComponent(txtID)
                            .addComponent(txtKet))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnLihat, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
                            .addComponent(btnAmbil, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
                            .addComponent(Rt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLihat)
                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNoTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(Rt))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(btnAmbil))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtKet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        txtProses.setText("Proses");
        txtProses.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProsesActionPerformed(evt);
            }
        });

        btnHapus.setText("Hapus");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        jLabel1.setText("ID KELUAR");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(KID)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)
                        .addGap(175, 175, 175)
                        .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
                    .addComponent(btnHapus)
                    .addComponent(KID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jPanel1.getAccessibleContext().setAccessibleName("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLihatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLihatActionPerformed
        // TODO add your handling code here:
        jDialog1.setSize(400, 350);
        jDialog1.setVisible(true);
    }//GEN-LAST:event_btnLihatActionPerformed

    private void btnAmbilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAmbilActionPerformed
            try {
            // TODO add your handling code here:
            ReturnKeluar r = new ReturnKeluar();
            Barang b = daoBarang.getByOne(txtID.getText());
            Keluar k = daoKeluar.getByNo(KID.getText());
            User u = daoUser.getById(txtUser.getText());
            r.setBarang(b);
            r.setKeluar(k);
            r.setUser(u);
            //
            r.setNotransaksi(String.valueOf(txtNoTransaksi.getText()));
            r.setNoretrun(String.valueOf(txtNO.getText()));
            r.setNamaretrun(String.valueOf(txtN.getText()));
            r.setTanggalReturn(Jtanggal.getDate());
            r.setTanggal((Date) txtTanggal.getValue());
            r.setJumlah(Integer.parseInt(txtJumlah.getText()));
            r.setTotal(Double.parseDouble(txtTotal.getText()));
            r.setKet(String.valueOf(txtKet.getText()));
            recReturnKeluar.add(r);
        } catch (SQLException ex) {
            Logger.getLogger(FormReturnKeluar.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.isiTabelReturn();
        txtID.setText("");
        txtNoTransaksi.setText("");
        txtJumlah.setText("");
        txtTotal.setText("");
        txtTanggal.setText("");
        //txtNO.setText("");
        txtN.setText("");
        txtUser.setText("");
        txtID.requestFocus();
    }//GEN-LAST:event_btnAmbilActionPerformed

    private void tabelCariMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelCariMouseClicked
        //try{
        DefaultTableModel model = (DefaultTableModel) tabelCari.getModel();
        int selectedRowIndex = tabelCari.getSelectedRow();
        LID.setText(tabelCari.getValueAt(selectedRowIndex, 0).toString());
        KID.setText(tabelCari.getValueAt(selectedRowIndex, 0).toString());
        txtNoTransaksi.setText(tabelCari.getValueAt(selectedRowIndex, 1).toString());
        txtID.setText(tabelCari.getValueAt(selectedRowIndex, 2).toString());
        txtTanggal.setText(tabelCari.getValueAt(selectedRowIndex, 4).toString());
        txtJumlah.setText(tabelCari.getValueAt(selectedRowIndex, 5).toString());
        txtTotal.setText(tabelCari.getValueAt(selectedRowIndex, 6).toString());
        txtUser.setText(tabelCari.getValueAt(selectedRowIndex, 7).toString());
        txtKet.setText(tabelCari.getValueAt(selectedRowIndex, 9).toString());
        
        /**
        Date date = new SimpleDateFormat("MMMMM d, yyyy").parse((String)Model.getValueAt(selectedRowIndex, 4));
        TanggalX.setDate(date);
        } catch (ParseException ex) {
            Logger.getLogger(TReturn.class.getName()).log(Level.SEVERE, null, ex);
        }
        */

    }//GEN-LAST:event_tabelCariMouseClicked

    private void txtProsesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProsesActionPerformed
    // TODO add your handling code here:
        for (ReturnKeluar r : recReturnKeluar) {
            try {
            daoReturnKeluar.insert(r);
            System.out.println(r.getJumlah());

            } catch (SQLException ex) {
            Logger.getLogger(FormReturnKeluar.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        recBarang.clear();
        JOptionPane.showMessageDialog(this, "Proses Berhasil..", "Informasi", JOptionPane.INFORMATION_MESSAGE);
        txtID.setText("");
        //KID.setText("");
        this.statusAwal();
        //
        int zz = 0;
        PreparedStatement pr = null;
        try {
            String sqlUpdate = "UPDATE keluar SET ket=? WHERE id_keluar=?";
            for (ReturnKeluar r : recReturnKeluar){
               pr = Koneksi.dbConnection().prepareStatement(sqlUpdate);       
               pr .setString(1, r.getKet());
               pr .setInt(2, r.getKeluar().getId());
               pr .executeUpdate();
               zz++;
            }
            //dbConnection().commit();
            System.out.println(zz + " Records updated!");
            Koneksi.closekoneksi();
            tampildata();
        } catch (SQLException ex) {
            Logger.getLogger(FormReturnMasuk.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
    }//GEN-LAST:event_txtProsesActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        // TODO add your handling code here:
        recReturnKeluar.remove(tabelReturn.getSelectedRow());
        this.isiTabelReturn();
    }//GEN-LAST:event_btnHapusActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser Jtanggal;
    private javax.swing.JTextField KID;
    private javax.swing.JLabel LID;
    private javax.swing.JLabel LT;
    private javax.swing.JButton Rt;
    private javax.swing.JButton btnAmbil;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnLihat;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tabelCari;
    private javax.swing.JTable tabelReturn;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtJumlah;
    private javax.swing.JTextField txtKet;
    private javax.swing.JTextField txtN;
    private javax.swing.JTextField txtNO;
    private javax.swing.JTextField txtNoTransaksi;
    private javax.swing.JButton txtProses;
    private javax.swing.JFormattedTextField txtTanggal;
    private javax.swing.JTextField txtTotal;
    private javax.swing.JTextField txtUser;
    // End of variables declaration//GEN-END:variables

}
