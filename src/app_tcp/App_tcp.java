/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package app_tcp;

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
        String Linea = "Hola:12345";
        
        String[] mensaje = Linea.split(":");
        System.out.println(mensaje[0]);
    }
    
}
