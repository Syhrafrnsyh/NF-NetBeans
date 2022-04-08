/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package nf.interfaces;

import java.sql.SQLException;
import java.util.List;
import nf.entity.User;

/**
 *
 * @author ACER
 */
public interface InterUser {
    User insert(User o)throws SQLException;

    void update(User o)throws SQLException;

    void delete(int o) throws SQLException;

    List<User>getAll() throws SQLException;

    User getByUsername(String o) throws SQLException;
    
    User getById(String o) throws SQLException;
}
