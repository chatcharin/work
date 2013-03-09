/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mailverifier;

import java.sql.*;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Aek
 */
public class MSSQL_Connect {

    Connection con;
    ResultSet rs;
    boolean status = false;
    public static ResultSet share;
    public MSSQL_Connect(){
         try {
             Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
             con = DriverManager.getConnection("jdbc:sqlserver://203.154.232.7:1433;databaseName=Formula1","sa","P@ssword");
         } catch (Exception e) {
            System.out.println(e);
         }
    }
    public String sql_one(String sql) {
        String obj="";
        try {
            Statement stmt = con.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY,java.sql.ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery(sql);
            rs.first();
            obj = rs.getObject(1).toString();
            //lb1.setText(rs.getString("memID"));
            //lb2.setText(rs.getString("fname"));
        } catch (Exception e) {
            System.out.println(e);
        }
         return obj;
    }
    public void getEmail(String sql){
       try {
            Statement stmt = con.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY,java.sql.ResultSet.CONCUR_READ_ONLY);
            share = stmt.executeQuery(sql);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void closeEmail(){
        try {
            share.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public Vector sql_getVector(String sql){
       Vector obj=new Vector();
        try {
            Statement stmt = con.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY,java.sql.ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                obj.add(rs.getObject(2).toString());
            }
            //lb1.setText(rs.getString("memID"));
            //lb2.setText(rs.getString("fname"));
        } catch (Exception e) {
            System.out.println(e);
        }
         return obj;
    }
    public void insert(String sql){
        try {
            Statement stmt = con.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY,java.sql.ResultSet.CONCUR_READ_ONLY);
                      stmt.execute(sql);
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    int number_tmp =0;
    public void checkUpdate(){
        try{
            
           if(!status){ 
            String number = getNumber();
            String count  = countRecord();
            number_tmp = Integer.parseInt(count);
            if(Integer.parseInt(number)< Integer.parseInt(count)){
               // Call Service Sync Data to WMS
               log(" Call Service ");
               new CallService().synRunJob();
               status = true;
            }
           }else{
               Thread.sleep(10000);
               String number = getNumber();
               String count  = countRecord();
               if(Integer.parseInt(number) == Integer.parseInt(count)){
                   status = false;
               }else if(number_tmp < Integer.parseInt(count)){
                   status = false;
               }
           }
        }catch(Exception e){ System.out.println(e.getMessage()); }
        
    }
    
    public String getNumber(){
        String count = "";
          try {
            Statement stmt = con.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY,java.sql.ResultSet.CONCUR_READ_ONLY);
            // get data from WMS
            rs = stmt.executeQuery("select top 1 id from dbo.MY ");
            if(rs.next()){
                count = rs.getString("id");
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.out.println(e);
        }
          return count;
    }
    
    
    public String countRecord(){
        boolean c = true;
        String count = "";
          try {
            Statement stmt = con.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY,java.sql.ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery("select count(*) as name from dbo.MY ");
            if(rs.next()){
                count = rs.getString("name");
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.out.println(e);
        }
          return count;
    }
    public void log(String text){
        System.out.println("-=-=-=-= : "+text+" : -==-=-=-=-=-=-");
    }
}
