/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MyClass;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import java.util.List;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author meone
 */
public class PeminjamanSQLImpl implements PeminjamanSQL {
    
    @Override
    public List<Peminjaman> getDataPeminjaman() throws SQLException{
        Connection con = (Connection) MyClass.MyConnection.getConnection();
        Statement ps = null;
        ResultSet rs = null;
        String query = "SELECT * FROM Peminjaman";
        List<Peminjaman> list = new ArrayList<>();
        try{    
            ps = (Statement) con.createStatement();           
            rs = ps.executeQuery(query);
            
            while(rs.next()){
                
                list.add(new Peminjaman(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));
                
            }
        }catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (con != null) {
                con.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (rs != null) {
                rs.close();
            }
        }
        return list;
    }
    
}
