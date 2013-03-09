/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mailverifier;
/**
 *
 * @author Aek
 */
public class ThSendDB extends Thread {

    String name;
    MSSQL_Connect mSSQL_Connect = null;
    public ThSendDB(String name) {
        this.name = name;
        mSSQL_Connect = new MSSQL_Connect();
    }

    @Override
    public void run() {
        try {
           while(true){
             mSSQL_Connect.checkUpdate();
             Thread.sleep(2000);
           }
        } catch (Exception e) {//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
    }
}
