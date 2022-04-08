/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package nf.interfaces;

import java.sql.SQLException;
import java.util.List;
import nf.entity.Supplier;

/**
 *
 * @author ACER
 */
public interface InterSupplier {
    Supplier insert(Supplier o) throws SQLException;

    void update(Supplier o) throws SQLException;

    void delete(int o) throws SQLException;

    List<Supplier> getAll() throws SQLException;

    Supplier getByNama(String o) throws SQLException;
}
