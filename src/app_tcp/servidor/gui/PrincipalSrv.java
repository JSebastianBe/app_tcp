package app_tcp.servidor.gui;


import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Vinni
 */
public class PrincipalSrv extends javax.swing.JFrame {
    private final int PORT = 12345;
    private ServerSocket serverSocket;


    /**
     * Creates new form Principal1
     */
    public PrincipalSrv() {
        initComponents();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {
        this.setTitle("Servidor ...");

        bIniciar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        mensajesTxt = new JTextArea();
        jScrollPane1 = new javax.swing.JScrollPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        bIniciar.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        bIniciar.setText("INICIAR SERVIDOR");
        bIniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bIniciarActionPerformed(evt);
            }
        });
        getContentPane().add(bIniciar);
        bIniciar.setBounds(100, 90, 250, 40);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 0, 0));
        jLabel1.setText("SERVIDOR TCP");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(150, 10, 160, 17);

        mensajesTxt.setColumns(25);
        mensajesTxt.setRows(20);

        jScrollPane1.setViewportView(mensajesTxt);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(20, 160, 410, 500);

        setSize(new java.awt.Dimension(491, 700));
        setLocationRelativeTo(null);
    }// </editor-fold>

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PrincipalSrv().setVisible(true);
            }
        });

    }
    private void bIniciarActionPerformed(java.awt.event.ActionEvent evt) {
        iniciarServidor();
    }
    
    private void iniciarServidor() {
        bIniciar.setEnabled(false);
        new Thread(new Runnable() {
            public void run() {
                try {
                    InetAddress addr = InetAddress.getLocalHost();
                    serverSocket = new ServerSocket( PORT);
                    mensajesTxt.append("Servidor TCP en ejecución: "+ addr + " ,Puerto " + serverSocket.getLocalPort()+ "\n");
                    while (true) {
                        Socket clientSocket = serverSocket.accept();
                        new Thread(new Runnable(){
                            public void run(){
                                try{
                                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                                    mensajesTxt.append("Cliente conectado: " + clientSocket.getPort()+ "\n");
                                    String linea;


                                    while ((linea = in.readLine()) != null) {
                                        mensajesTxt.append("Cliente "+ clientSocket.getPort() + ": " + linea + "\n");

                                        out.println("Mensaje recibido en el server " );
                                    }

                                } 
                                catch(IOException ex){
                                    mensajesTxt.append("Error en la comunicación con el cliente: " + ex.getMessage() + "\n");
                                    ex.printStackTrace();
                                } 
                                finally {
                                    try{
                                        clientSocket.close();
                                    }
                                    catch(IOException ex){
                                        mensajesTxt.append("Error cerrando el socket del cliente: " + ex.getMessage() + "\n");
                                        ex.printStackTrace();
                                    } 
                                }
                            }
                        }).start();
                        
                    }
                } catch (IOException ex) {
                    bIniciar.setEnabled(true);
                    mensajesTxt.append("Error en el servidor: " + ex.getMessage() + "\n");
                    ex.printStackTrace();
                    
                }
            }
        }).start();
    }

    // Variables declaration - do not modify
    private javax.swing.JButton bIniciar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextArea mensajesTxt;
    private javax.swing.JScrollPane jScrollPane1;
}
