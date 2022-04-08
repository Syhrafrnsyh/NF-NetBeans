/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package nf.interfaces;

import java.sql.SQLException;
import java.util.List;
import nf.entity.Keluar;

/**
 *
 * @author ACER
 */
public interface InterKeluar {
    Keluar insert(Keluar o) throws SQLException;

    void update(Keluar o)throws SQLException;
        
    Keluar getByNo(String o) throws SQLException;    

}
