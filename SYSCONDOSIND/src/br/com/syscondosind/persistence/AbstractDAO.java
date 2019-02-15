/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.syscondosind.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author User
 */
public abstract class AbstractDAO {

    protected Connection getConnection() throws SQLException {

        String url = "jdbc:mysql://localhost:3306/urna_eletronica_db";
//        String url = "jdbc:mysql://192.168.0.2:3306/urna_eletronica_db";
        String user = "root";
        String password = "root";

        Connection cn = DriverManager.getConnection(url, user, password);

        return cn;
    }

    protected void closeResources(Connection cn, Statement st, ResultSet rs) {

        if (rs != null) {
            try {
                rs.close();
            } catch (Exception e) {
                // nao faca nada
            }
        }
        if (st != null) {
            try {
                st.close();
            } catch (Exception e) {
                // nao faca nada
            }
        }
        if (cn != null) {
            try {
                cn.close();
            } catch (Exception e) {
                // nao faca nada
            }
        }
    }

}
