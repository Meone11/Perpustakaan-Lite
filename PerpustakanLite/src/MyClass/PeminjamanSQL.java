/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MyClass;

import MyClass.Peminjaman;
import java.util.List;
import java.sql.SQLException;
/**
 *
 * @author meone
 */
public interface PeminjamanSQL {
    
    public List<Peminjaman> getDataPeminjaman() throws SQLException;
    
}
