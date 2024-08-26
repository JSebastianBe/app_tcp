/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package app_tcp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author jusebema
 */
public class App_tcp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Hola manpinta");
        String Linea = "Hola|txt";
        
        String[] mensaje = Linea.split("\\|");
        System.out.println(mensaje[0]);
        
        
        DateFormat dateFormat = new SimpleDateFormat("HHmmss");
        Date date = new Date();
        System.out.println("Hora actual: " + dateFormat.format(date));
    }
    
}
