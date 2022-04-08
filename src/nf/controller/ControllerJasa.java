/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nf.controller;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.TimePicker;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import nf.entity.Jasa;
import nf.implement.ImplJasa;
import nf.table.TableJasa;
import nf.view.FormJasa;
import static nf.view.FormJasa.btnTambahUpdate;
import static nf.view.FormJasa.dateTimePicker1;
import static nf.view.FormJasa.labelJam;
import static nf.view.FormJasa.txtAlamat;
import static nf.view.FormJasa.txtID;
import static nf.view.FormJasa.txtJam;
import static nf.view.FormJasa.txtKet;
import static nf.view.FormJasa.txtNama;
import static nf.view.FormJasa.txtTgl;
import static nf.view.FormJasa.txtTlp;
import nf.alarm.Validator;

/**
 *
 * @author ACER
 */
public class ControllerJasa {
    private static String className = "ControllerJasa";
    public static TableJasa tablejasa = new TableJasa();
    public static ImplJasa impljasa = new ImplJasa();
    
    public void setMaximumFrame(FormJasa formjasa) {
        try {
            formjasa.setMaximum(true);
        } catch (Exception error) {
            System.err.println("Error At : Class = " + className + ", Methode = setMaximumFrame \n& " + error);
            JOptionPane.showMessageDialog(formjasa, error.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void setTableModel(FormJasa formjasa) {
        try {
            FormJasa.tabelJasa.setModel(tablejasa);
        } catch (Exception error) {
            System.err.println("Terjadi Kesalahan pada class " + className + ", methode setTableModel \n Detail : " + error);
            JOptionPane.showMessageDialog(formjasa, "Terjadi kesalahan pada class " + className + ", methode setTableModel");
        }
    }
    
    public void loadData(FormJasa formjasa) {
        try {
            List<Jasa> list = impljasa.getListData();
            tablejasa.setList(list);
        } catch (Exception error) {
            System.err.println("Terjadi Kesalahan pada class " + className + ", methode loadData \n Detail : " + error);
            JOptionPane.showMessageDialog(formjasa, "Terjadi kesalahan pada class " + className + ", methode loadData");
        }
    }
    /**
    public void searchData(FormJasa formjasa) {
        try {
            String searchParameter = FormJasa.txtCari.getText();
            List<Jasa> list = impljasa.getListDataByParameter(searchParameter);
            tablejasa.setList(list);
        } catch (Exception error) {
            System.err.println("Terjadi Kesalahan pada class " + className + ", methode searchData \n Detail : " + error);
            JOptionPane.showMessageDialog(formjasa, "Terjadi kesalahan pada class " + className + ", methode searchData");
        }
    }
    */
    public void btnDeleteAction(FormJasa formjasa) {
        try {
            int rowSelected = FormJasa.tabelJasa.getSelectedRow();
            if (rowSelected == -1) {
                JOptionPane.showMessageDialog(formjasa, "Silahkan seleksi data yang ingin dihapus");
            } else {
                int id = tablejasa.get(rowSelected).getId();
                String nama = tablejasa.get(rowSelected).getNama();

                int confirm = JOptionPane.showConfirmDialog(formjasa, "Apakah anda yakin menghapus data "
                        + nama + "?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    String message = impljasa.deleteData(id);
                    JOptionPane.showMessageDialog(formjasa, message);
                }
                refresh(formjasa);
            }
        } catch (Exception error) {
            System.err.println("Terjadi Kesalahan pada class " + className + ", methode buttonHapusAction \n Detail : " + error);
            JOptionPane.showMessageDialog(formjasa, "Terjadi kesalahan pada class " + className + ", methode buttonHapusAction");
        }
    }
        
    public void btnTambahUpdateAction(FormJasa formjasa) {                                         
        // TODO add your handling code here:
        try {
            if (btnTambahUpdate.getText().equals("Tambah")) {
                if (tidakBolehKosong()) {
                //  
                DatePicker dt = dateTimePicker1.getDatePicker();
                TimePicker tm = dateTimePicker1.getTimePicker();
                String tanggal = dt.toString();
                String jam = tm.toString();
                String nama = txtNama.getText();
                boolean flag = Validator.validator(tanggal, jam, nama);// calling date time validator fuction
                //
                if (flag == true) {
                System.out.println(tanggal);
                System.out.println(jam);
                System.out.println(nama);    
                Jasa jasa = new Jasa();
                /*
                if (FormJasa.cmbMenu.getSelectedIndex() == 0) {
                    jasa.setMenu("Perawatan");
                } else {
                    jasa.setMenu("Setting");
                }
                */
                //jasa.setId(Integer.valueOf(FormJasa.txtID.getText()));
                jasa.setNama(nama);
                jasa.setTanggal(tanggal);
                jasa.setJam(jam);
                jasa.setTelepon(FormJasa.txtTlp.getText());
                jasa.setAlamat(FormJasa.txtAlamat.getText());
                jasa.setKeterangan(FormJasa.txtKet.getText());
                jasa.setMenu(FormJasa.cmbMenu.getSelectedItem().toString());
                impljasa.insertData(jasa);
                    JOptionPane.showMessageDialog(null, " Insert");
                    } else {
                    JOptionPane.showMessageDialog(null, " Err");
                    } 
                } else {
                    JOptionPane.showMessageDialog(formjasa, "text tidak boleh ada yang kosong", "Kesalahan", JOptionPane.ERROR_MESSAGE);
                }
            } else if (tidakBolehKosong()) {
                String jam = txtJam.getText();
                String tanggal = txtTgl.getText();
                String nama = txtNama.getText();
                boolean flag = Validator.validator(tanggal, jam, nama);// calling date time validator fuction
                //
                String idx = txtID.getText();
                int id = Integer.parseInt(idx);
                if (flag == true) { 
                Jasa jasa = new Jasa();
                /*
                if (FormJasa.cmbMenu.getSelectedIndex() == 0) {
                    jasa.setMenu("Perawatan");
                } else {
                    jasa.setMenu("Setting");
                }
                */
                ///jasa.setId(Integer.valueOf(FormJasa.txtID.getText()));
                jasa.setNama(nama);
                jasa.setTanggal(tanggal);
                jasa.setJam(jam);
                jasa.setTelepon(FormJasa.txtTlp.getText());
                jasa.setAlamat(FormJasa.txtAlamat.getText());
                jasa.setKeterangan(FormJasa.txtKet.getText());
                jasa.setMenu(FormJasa.cmbMenu.getSelectedItem().toString());
                jasa.setId(id);
                impljasa.updateData(jasa);
                    JOptionPane.showMessageDialog(null, " Update");
                    } else {
                    JOptionPane.showMessageDialog(null, " Err");
                    }
            } else {
                JOptionPane.showMessageDialog(formjasa, "text tidak boleh ada yang kosong", "Kesalahan", JOptionPane.ERROR_MESSAGE);
            }
            refresh(formjasa);
        } catch (Exception error) {
            System.err.println("Terjadi Kesalahan pada class " + className + ", methode buttonSimpanAction \n Detail : " + error);
        }
    }                                        

    public void tableDataAction(FormJasa formjasa) {
        FormJasa.tabelJasa.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                btnTambahUpdate.setText("Ubah");
                int row = FormJasa.tabelJasa.getSelectedRow();
                if (row != -1) {
                    Jasa jasa = tablejasa.get(row);
                    FormJasa.txtID.setText(String.valueOf(jasa.getId()));
                    FormJasa.txtNama.setText(jasa.getNama());
                    FormJasa.txtTgl.setText(jasa.getTanggal());
                    //FormJasa.txtJam.setText(jasa.getJam());
                    //FormJasa.txtTgl.setText(tablejasa.getValueAt(row,2).toString());
                    //FormJasa.txtJam.setText(tablejasa.getValueAt(row, 3).toString());
                    FormJasa.txtTlp.setText(jasa.getTelepon());
                    FormJasa.txtAlamat.setText(jasa.getAlamat());
                    FormJasa.txtKet.setText(jasa.getKeterangan());
                    FormJasa.cmbMenu.setSelectedItem(jasa.getMenu());
                  txtTgl.setVisible(true);
                  txtJam.setVisible(true);
                  labelJam.setVisible(true);
                  dateTimePicker1.setVisible(false);

                }
            }
        });
    }
    
    boolean tidakBolehKosong() {
        if (!txtNama.getText().isEmpty()
                && !txtTlp.getText().isEmpty()
                && !txtAlamat.getText().isEmpty()
                && !txtKet.getText().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
    
    public void pres(FormJasa formjasa){
        impljasa.refresh();
    }
    
    public void refresh(FormJasa formjasa) {
        try {
            FormJasa.txtNama.setText("");
            FormJasa.txtTlp.setText("");
            FormJasa.txtAlamat.setText("");
            FormJasa.txtKet.setText("");
            FormJasa.txtCari.setText("");
            FormJasa.tabelJasa.clearSelection();
            loadData(formjasa);
            impljasa.refresh();
        } catch (Exception error) {
            System.err.println("Terjadi Kesalahan pada class " + className + ", methode refresh \n Detail : " + error);
            JOptionPane.showMessageDialog(formjasa, "Terjadi kesalahan pada class " + className + ", methode refresh");
        }
    }

}
