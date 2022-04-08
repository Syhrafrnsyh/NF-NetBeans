/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package nf.interfaces;

import java.sql.SQLException;
import java.util.List;
import nf.entity.Kategori;

/**
 *
 * @author ACER
 */
public interface InterKategori {
    Kategori insert(Kategori o) throws SQLException;

    void update(Kategori o) throws SQLException;

    void delete(int o) throws SQLException;

    List<Kategori> getAll() throws SQLException;

    Kategori getByNama(String o) throws SQLException;
}
