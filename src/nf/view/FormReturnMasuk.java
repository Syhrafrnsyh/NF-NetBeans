/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nf.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import nf.Koneksi;
import nf.entity.Barang;
import nf.entity.Masuk;
import nf.entity.ReturnMasuk;
import nf.entity.Supplier;
import nf.implement.ImplBarang;
import nf.implement.ImplMasuk;
import nf.implement.ImplReturnMasuk;
import nf.implement.ImplSupplier;
import nf.interfaces.InterBarang;
import nf.interfaces.InterMasuk;
import nf.interfaces.InterReturnMasuk;
import nf.interfaces.InterSupplier;



/**
 *
 * @author ACER
 */
public class FormReturnMasuk extends javax.swing.JInternalFrame {

    nf.Koneksi Koneksi = new nf.Koneksi();
    private List<Supplier> recSupplier = new ArrayList<Supplier>();
    private List<Barang> recBarang = new ArrayList<Barang>();
    private List<Masuk> recMasuk = new ArrayList<Masuk>();
    private List<ReturnMasuk> recReturnMasuk = new ArrayList<ReturnMasuk>();
    InterSupplier daoSupplier;
    InterBarang daoBarang;
    InterMasuk daoMasuk;
    InterReturnMasuk daoReturnMasuk;
    private String className = "FormReturnMasuk";

    public FormReturnMasuk() {
        initComponents();
        daoSupplier = new ImplSupplier();
        daoBarang = new ImplBarang();
        daoMasuk = new ImplMasuk();
        daoReturnMasuk = new ImplReturnMasuk();
    this.statusAwal();
        BarCodeLengthDocumentListener lengthListener = new BarCodeLengthDocumentListener(7, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String text = e.getActionCommand();
            JOptionPane.showMessageDialog(FormReturnMasuk.this, text);
        }
    });
    txtKet.getDocument().addDocumentListener(lengthListener);
    //
    Rt.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            //
            txtKet.setText(null);
            txtKet.requestFocusInWindow();
            Thread t = new Thread(new Simulator());
            t.start();
        }
    });
    //
    }
    
    public final void dataMasuk(){
    DefaultTableModel model = new DefaultTableModel();
    model.addColumn("ID");
    model.addColumn("NO");
    model.addColumn("TANGGAL");
    model.addColumn("ID_BARANG");
    model.addColumn("NAMA_BARANG");
    model.addColumn("HARGA");
    model.addColumn("JUMLAH");
    model.addColumn("TOTAL");
    model.addColumn("ID_SUPPLIER");
    model.addColumn("NAMA_SUPPLIER");
    model.addColumn("KETERANGAN");
        try {
            Connection con = Koneksi.dbConnection();
            java.sql.Statement stm = con.createStatement();
            String sql = "SELECT masuk.id_masuk, masuk.no, masuk.tanggal, "
            + "barang.id_barang as id_barang, barang.nama as nama_barang, barang.harga as harga_barang, "
            + "masuk.jumlah, masuk.total, "
            + "supplier.id_supplier as id_supplier, supplier.nama as nama_supplier, "
            + "masuk.ket FROM masuk JOIN barang ON barang.id_barang = masuk.id_barang "
            + "JOIN supplier ON supplier.id_supplier = masuk.id_supplier";
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
                    rs.getString(10),
                    rs.getString(11)
                });
            }
            tabelCariMasuk.setModel(model);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Ada kesalahan" + e);
        }
    }
    
    void isiTabelReturnMasuk(){
        Object data[][] = new Object[recReturnMasuk.size()][11];
        int x = 0;
        for (ReturnMasuk rm : recReturnMasuk) {
            data[x][0] = rm.getNoretrun();
            data[x][1] = rm.getSupplier().getId();
            data[x][2] = rm.getTanggalReturn();
            data[x][3] = rm.getMasuk().getId();
            data[x][4] = rm.getMasuk().getNo();
            data[x][5] = rm.getMasuk().getTanggal();
            data[x][6] = rm.getBarang().getId();
            data[x][7] = rm.getBarang().getHarga();
            data[x][8] = rm.getJumlah();
            data[x][9] = rm.getTotal();
            data[x][10] = rm.getKet();
            x++;
        }
        String judul[] = {"NoRetrun","IDsupplier","TanggalReturn","IDmasuk","NoTransaksi","Tanggal","IDBarang","HargaBarang","Jumlah","Total","KETERANGAN"};
        tabelReturnMasuk.setModel(new DefaultTableModel(data, judul));
        jScrollPane1.setViewportView(tabelReturnMasuk);
    }
    
    void kosongkanTabel() {
        tabelReturnMasuk.setModel(new DefaultTableModel(new Object[][]{{"","","","","","","","","",""}}, new String[]{"NoRetrun","IDsupplier","TanggalReturn","IDmasuk","NoTransaksi","Tanggal","IDBarang","HargaBarang","Jumlah","Total","KETERANGAN"}));
    }

    void AutoN() {
        try {
            String sql = "select * from kembalimasuk order by noretrun desc";
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
    
    void setTanggal(){
    Locale INDONESIA = new java.util.Locale("id", "ID");
        Date sekarang = Calendar.getInstance().getTime();
        Jtanggal.setDate(sekarang);
    }
    
    void loadCombo() {
        cmbSupplier.removeAllItems();
        try {
            recSupplier = daoSupplier.getAll();
            for (Supplier s : recSupplier) {
                cmbSupplier.addItem(s.getNama());
            }
        } catch (SQLException ex) {
            Logger.getLogger(FormReturnMasuk.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void getSum()
    {
        
        double sum = 0;
        for(int X = 0; X < tabelCariMasuk.getRowCount(); X++)
        {
            sum = sum + Double.parseDouble(tabelCariMasuk.getValueAt(X, 7).toString());
        }
        String m = String.format("%,.2f", sum);
        LT.setText(m);
    }

    void statusAwal(){
    
    loadCombo();
    AutoN();
    dataMasuk();
    setTanggal();
    getSum();
    kosongkanTabel();
    this.kosongkanTabel();
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
        tabelCariMasuk = new javax.swing.JTable();
        LT = new javax.swing.JLabel();
        LID = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        Jtanggal = new com.toedter.calendar.JDateChooser();
        txtNO = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        cmbSupplier = new javax.swing.JComboBox();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelReturnMasuk = new javax.swing.JTable();
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
        txtHarga = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtKet = new javax.swing.JTextField();
        Rt = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        txtProses = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        MID = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        jDialog1.setTitle("Pencarian Barang\n");

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Data Barang Masuk", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        tabelCariMasuk.setModel(new javax.swing.table.DefaultTableModel(
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
        tabelCariMasuk.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelCariMasukMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabelCariMasuk);

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

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Return"));

        jLabel2.setText("Tanggal :");

        jLabel4.setText("Nama :");

        jLabel11.setText("No :");

        cmbSupplier.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

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
                    .addComponent(cmbSupplier, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNO))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cmbSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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

        tabelReturnMasuk.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tabelReturnMasuk);

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

        Rt.setText("Return");

        jLabel10.setText("Harga :");

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
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtHarga)
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
                    .addComponent(btnAmbil)
                    .addComponent(txtHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(txtTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtKet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
                .addContainerGap())
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

        jLabel1.setText("ID MASUK");

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
                        .addComponent(MID)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)
                        .addGap(177, 177, 177)
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
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnHapus)
                    .addComponent(txtProses)
                    .addComponent(MID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18))
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
            ReturnMasuk rm = new ReturnMasuk();
            Barang b = daoBarang.getByOne(txtID.getText());
            Masuk m = daoMasuk.getByNo(MID.getText());
            Supplier d = daoSupplier.getByNama((String) cmbSupplier.getSelectedItem());
            rm.setSupplier(d);
            rm.setBarang(b);
            rm.setMasuk(m);
            //
            rm.setNotransaksi(String.valueOf(txtNoTransaksi.getText()));
            rm.setNoretrun(String.valueOf(txtNO.getText()));
            rm.setTanggalReturn(Jtanggal.getDate());
            rm.setTanggal((Date) txtTanggal.getValue());
            rm.setHarga(Double.parseDouble(txtHarga.getText()));
            rm.setJumlah(Integer.parseInt(txtJumlah.getText()));
            rm.setTotal(Double.parseDouble(txtTotal.getText()));
            rm.setKet(String.valueOf(txtKet.getText()));
            recReturnMasuk.add(rm);
        } catch (SQLException ex) {
            Logger.getLogger(FormReturnMasuk.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.isiTabelReturnMasuk();
        txtID.setText("");
        txtNoTransaksi.setText("");
        txtJumlah.setText("");
        //MID.setText("");
        txtTotal.setText("");
        txtTanggal.setText("");
        txtHarga.setText("");
        txtID.requestFocus();
        //MID.requestFocus();
    }//GEN-LAST:event_btnAmbilActionPerformed

    private void tabelCariMasukMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelCariMasukMouseClicked
        DefaultTableModel model = (DefaultTableModel) tabelCariMasuk.getModel();
        int selectedRowIndex = tabelCariMasuk.getSelectedRow();
        LID.setText(tabelCariMasuk.getValueAt(selectedRowIndex, 0).toString());
        MID.setText(tabelCariMasuk.getValueAt(selectedRowIndex, 0).toString());
        txtNoTransaksi.setText(tabelCariMasuk.getValueAt(selectedRowIndex, 1).toString());
        txtTanggal.setText(tabelCariMasuk.getValueAt(selectedRowIndex, 2).toString());
        txtID.setText(tabelCariMasuk.getValueAt(selectedRowIndex, 3).toString());
        txtHarga.setText(tabelCariMasuk.getValueAt(selectedRowIndex, 5).toString());
        txtJumlah.setText(tabelCariMasuk.getValueAt(selectedRowIndex, 6).toString());
        txtTotal.setText(tabelCariMasuk.getValueAt(selectedRowIndex, 7).toString());
        cmbSupplier.setSelectedItem(tabelCariMasuk.getValueAt(selectedRowIndex, 9).toString());
        txtKet.setText(tabelCariMasuk.getValueAt(selectedRowIndex, 10).toString());
        

    }//GEN-LAST:event_tabelCariMasukMouseClicked

    private void txtProsesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProsesActionPerformed
    // TODO add your handling code here:
        for (ReturnMasuk rm : recReturnMasuk) {
            try {
            daoReturnMasuk.insert(rm);
            System.out.println(rm.getTotal());
            } catch (SQLException ex) {
            Logger.getLogger(FormReturnKeluar.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        recBarang.clear();
        JOptionPane.showMessageDialog(this, "Proses Berhasil..", "Informasi", JOptionPane.INFORMATION_MESSAGE);
        txtID.setText("");
        this.statusAwal();
        //
        int i = 0;
        PreparedStatement p = null;
        try {
            String sqlUpdate = "UPDATE masuk SET ket=? WHERE id_masuk=?";
            for (ReturnMasuk rm : recReturnMasuk){
               p = Koneksi.dbConnection().prepareStatement(sqlUpdate);       
               p .setString(1, rm.getKet());
               p .setInt(2, rm.getMasuk().getId());
               p .executeUpdate();
               i++;
            }
            //dbConnection().commit();
            System.out.println(i + " Records updated!");
            Koneksi.closekoneksi();
            dataMasuk();
        } catch (SQLException ex) {
            Logger.getLogger(FormReturnMasuk.class.getName()).log(Level.SEVERE, null, ex);
        }

        //
    }//GEN-LAST:event_txtProsesActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        // TODO add your handling code here:
        recReturnMasuk.remove(tabelReturnMasuk.getSelectedRow());
        this.isiTabelReturnMasuk();

    }//GEN-LAST:event_btnHapusActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser Jtanggal;
    private javax.swing.JLabel LID;
    private javax.swing.JLabel LT;
    private javax.swing.JTextField MID;
    private javax.swing.JButton Rt;
    private javax.swing.JButton btnAmbil;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnLihat;
    private javax.swing.JComboBox cmbSupplier;
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
    private javax.swing.JTable tabelCariMasuk;
    private javax.swing.JTable tabelReturnMasuk;
    private javax.swing.JTextField txtHarga;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtJumlah;
    private javax.swing.JTextField txtKet;
    private javax.swing.JTextField txtNO;
    private javax.swing.JTextField txtNoTransaksi;
    private javax.swing.JButton txtProses;
    private javax.swing.JFormattedTextField txtTanggal;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables

}
