/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MyClass;

import com.mysql.jdbc.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author meone
 */
public class Activity {
    
    //untuk login
    public String login(String username, String password){
        String Nama = null;
        
        try {
            PreparedStatement ps;
            ResultSet rs;
            
            String Query = "SELECT `Usename`, `Password`, `Nama` FROM `activity` WHERE Usename = ? AND Password = ?";
            
            ps = (PreparedStatement) MyConnection.getConnection().prepareStatement(Query);
            ps.setString(1, username);
            ps.setString(2, password);
            
            rs = ps.executeQuery();
            
            if(rs.next()){
                JOptionPane.showMessageDialog(null, "Succesfully Login!!", "Success", 1);
                Nama = rs.getString(3);
            }else{
                JOptionPane.showMessageDialog(null, "Terjadi Kesalahan saat Login!!", "Failed", 2);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Activity.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Nama;
    }
    
    //untuk regis
    public void register(String username, String password,String profile, String nama, String alamat, String telepon){
        
        try {
            PreparedStatement ps;
            ResultSet rs;
            
            String query = "INSERT INTO `activity`(`Usename`, `Password`, `Profile`, `Nama`, `Alamat`, `No_HP`) VALUES (?,?,?,?,?,?)";
            
            ps = (PreparedStatement) MyConnection.getConnection().prepareStatement(query);
            
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, profile);
            ps.setString(4, nama);
            ps.setString(5, alamat);
            ps.setString(6, telepon);
            
//            rs = ps.executeQuery();
            if(ps.executeUpdate() > 0){
                JOptionPane.showMessageDialog(null, "Succesfully Register!!", "Success", 1);
            }else{
                JOptionPane.showMessageDialog(null, "Terjadi Kesalahan saat Regis!!", "Failed", 2);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Activity.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void InsertUpdateDelete(char Operation, String Kode, String Judul, String Pengarang, String Penerbit, String Status){
        PreparedStatement ps;
        String query = "INSERT INTO `inventory`(`Kode_Buku`, `Judul_Buku`, `Pengarang`, `Penerbit`, `Status`) VALUES (?,?,?,?,?)";
        if (Operation == 'i'){
            try {
                ps = (PreparedStatement) MyConnection.getConnection().prepareStatement(query);
                ps.setString(1, Kode);
                ps.setString(2, Judul);
                ps.setString(3, Pengarang);
                ps.setString(4, Penerbit);
                ps.setString(5, Status);
                
                if (ps.executeUpdate() > 0){
                    JOptionPane.showMessageDialog(null,"Data Berhasil Di Input");
                }
            } catch (java.sql.SQLException ex) {
                Logger.getLogger(Activity.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        else if (Operation == 'u'){ // u For Update or Edit Data
            try {
                ps = (PreparedStatement) MyConnection.getConnection().prepareStatement("UPDATE `inventory` SET `Judul_Buku`= ?,`Pengarang`= ?,`Penerbit`= ?,`Status`= ? WHERE Kode_Buku = ?");
                
                ps.setString(1, Judul);
                ps.setString(2, Pengarang);
                ps.setString(3, Penerbit);
                ps.setString(4, Status);
                ps.setString(5, Kode);
                
                if (ps.executeUpdate() > 0){
                    JOptionPane.showMessageDialog(null,"Data Berhasil Di Update");
                }
            } catch (java.sql.SQLException ex) {
                Logger.getLogger(Activity.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }else if (Operation == 'd'){ // d For Delete Data
            try {
                ps = (PreparedStatement) MyConnection.getConnection().prepareStatement("DELETE FROM `inventory` WHERE Kode_Buku = ?");
                ps.setString(1, Kode);
                
                if (ps.executeUpdate() > 0){
                    JOptionPane.showMessageDialog(null,"Data Berhasil Di Hapus");
                }
            } catch (java.sql.SQLException ex) {
                Logger.getLogger(Activity.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
    public void FillToTableInventory(JTable table){
        Connection c = MyConnection.getConnection();
        PreparedStatement ps;
        
        try {
            ps = (PreparedStatement) c.prepareStatement("SELECT * FROM `inventory`");
                        
            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            
            Object[] row;
            
            while (rs.next()){
                row = new Object[5];
                row[0] = rs.getString(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);
                row[3] = rs.getString(4);
                row[4] = rs.getString(5);
                
                model.addRow(row);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Activity.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void FillToTableInventory1(JTable table, String cari){
        Connection c = MyConnection.getConnection();
        PreparedStatement ps;
        
        try {
            ps = (PreparedStatement) c.prepareStatement("SELECT * FROM `inventory`");
                        
            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            
            Object[] row;
            
            while (rs.next()){
                row = new Object[5];
                if (rs.getString(5).equals("Dipinjam") == false){
                    row[0] = rs.getString(1);
                    row[1] = rs.getString(2);
                    row[2] = rs.getString(3);
                    row[3] = rs.getString(4);
                    row[4] = rs.getString(5);                   
                                
                    model.addRow(row);
                }
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Activity.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void UpdateStatus(String Kode, String Status){
        PreparedStatement ps;
        try {
            ps = (PreparedStatement) MyConnection.getConnection().prepareStatement("UPDATE `inventory` SET `Status`= ? WHERE Kode_Buku = ?");

            ps.setString(1, Status);
            ps.setString(2, Kode);

            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Peminjaman Telah Berhasil!");
            }
        } catch (java.sql.SQLException ex) {
            Logger.getLogger(Activity.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    public void InsertPeminjaman(String Username, String Nama, String Kode, String Judul, String Peminjaman, String Pengembalian, String Status){
        PreparedStatement ps;
        String query = "INSERT INTO `Peminjaman`(`Username`, `Nama`, `kodeBuku`, `judulBuku`, `borrowDate`, `pengembalianDate`) VALUES (?,?,?,?,?,?)";
        
        try {
            ps = (PreparedStatement) MyConnection.getConnection().prepareStatement(query);
            ps.setString(1, Username);
            ps.setString(2, Nama);
            ps.setString(3, Kode);
            ps.setString(4, Judul);
            ps.setString(5, Peminjaman);
            ps.setString(6, Pengembalian);

            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Sedang Dalam Pengajuan.....");
            }
            UpdateStatus(Kode, Status);
        } catch (java.sql.SQLException ex) {
            Logger.getLogger(Activity.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void FillToTableRiwayat(JTable table, String date){
        Connection c = MyConnection.getConnection();
        PreparedStatement ps;
        
        try {
            ps = (PreparedStatement) c.prepareStatement("SELECT * FROM `Peminjaman` WHERE borrowDate = ?");
            ps.setString(1, date);
                        
            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            
            Object[] row;
            
            while (rs.next()){
                row = new Object[5];
                row[0] = rs.getString(3);
                row[1] = rs.getString(4);
                row[2] = rs.getString(2);
                row[3] = rs.getString(5);
                row[4] = rs.getString(6);
                
                model.addRow(row);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Activity.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
