/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package nf.interfaces;

import java.sql.SQLException;
import nf.entity.ReturnMasuk;

/**
 *
 * @author ACER
 */
public interface InterReturnMasuk {
    
    ReturnMasuk insert(ReturnMasuk o) throws SQLException;
            
    void update(ReturnMasuk o) throws SQLException;
    
    void delete(int o) throws SQLException;
    
}
