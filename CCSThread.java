/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ccsthread;

import mailverifier.MSSQL_Connect;
import mailverifier.ThSendDB;

/**
 *
 * @author Aek
 */
public class CCSThread {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
      new ThSendDB("CCS").start();
    }
}
