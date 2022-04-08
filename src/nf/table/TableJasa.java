/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nf.table;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import nf.entity.Jasa;

/**
 *
 * @author ACER
 */
public class TableJasa extends AbstractTableModel {
        private List<Jasa> list = new ArrayList<>();

    public void insert(Jasa jasa) {
        list.add(jasa);
        fireTableDataChanged();
    }

    public void update(int row, Jasa jasa) {
        list.set(row, jasa);
        fireTableDataChanged();
    }

    public void delete(int row) {
        list.remove(row);
        fireTableDataChanged();
    }

    public Jasa get(int row) {
        return list.get(row);
    }

    public void setList(List<Jasa> list) {
        this.list = list;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public int getColumnCount() {
        return 8;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
       
        switch (columnIndex) {
            case 0:
                return  list.get(rowIndex).getId();
            case 1:
                return list.get(rowIndex).getNama();
            case 2:
                return list.get(rowIndex).getTanggal();
            case 3:
                return list.get(rowIndex).getJam();
            case 4:
                return list.get(rowIndex).getTelepon();
            case 5:
                return list.get(rowIndex).getAlamat();
            case 6:
                return list.get(rowIndex).getKeterangan();
            case 7:
                return list.get(rowIndex).getMenu();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return  "ID";
            case 1:
                return "Nama";
            case 2:
                return "Tanggal";
            case 3:
                return "Jam";
            case 4:
                return "Telepon";
            case 5:
                return "Alamat";
            case 6:
                return "Keterangan";
            case 7:
                return "Menu";
            default:
                return null;
        }
    }

}
