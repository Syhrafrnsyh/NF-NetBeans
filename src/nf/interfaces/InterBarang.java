/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package nf.interfaces;

import java.sql.SQLException;
import java.util.List;
import nf.entity.Barang;

/**
 *
 * @author ACER
 */
public interface InterBarang {
    Barang insert(Barang o) throws SQLException;

    void update(Barang o) throws SQLException;

    void delete(String o) throws SQLException;

    List<Barang> getAll() throws SQLException;
    //public List<Barang> getAllBarang();

    //List<Barang> getByKategori(String kat) throws SQLException;

    List<Barang> getByName(String nam) throws SQLException;

    List<Barang> getById(String o) throws SQLException;

    Barang getByOne(String o) throws SQLException;
}
