/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package nf.interfaces;

import java.sql.SQLException;
import nf.entity.Masuk;

/**
 *
 * @author ACER
 */
public interface InterMasuk {
    Masuk insert(Masuk o) throws SQLException;

    void update(Masuk o) throws SQLException;
    
    Masuk getByNo(String o) throws SQLException; 
}
