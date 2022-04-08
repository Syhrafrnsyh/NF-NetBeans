/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package nf.interfaces;

import java.sql.SQLException;
import nf.entity.ReturnKeluar;

/**
 *
 * @author ACER
 */
public interface InterReturnKeluar {
    
    ReturnKeluar insert(ReturnKeluar o) throws SQLException;
    
    void update(ReturnKeluar o) throws SQLException;
    
    void delete(int o) throws SQLException;
    
}
