/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nf.interfaces;

import java.sql.SQLException;
import java.util.List;
import nf.entity.Jasa;

/**
 *
 * @author ACER
 */
public interface InterJasa {
    Jasa insert(Jasa o) throws SQLException;

    void update(Jasa o) throws SQLException;

    void delete(String o) throws SQLException;

    List<Jasa> getAll() throws SQLException;
    
    List<Jasa> getByNama(String nam) throws SQLException;


}
