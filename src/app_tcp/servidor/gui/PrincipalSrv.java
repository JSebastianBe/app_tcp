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
    private List<Socket> listadoClientes;


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
        cbEnviarTodos = new javax.swing.JCheckBox();

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
        bIniciar.setBounds(100, 60, 250, 40);

        cbEnviarTodos.setFont(new java.awt.Font("Tahoma", 0, 14));
        cbEnviarTodos.setForeground(new java.awt.Color(0, 0, 0));
        cbEnviarTodos.setText("Enviar a todos los clientes conectados");
        getContentPane().add(cbEnviarTodos);
        cbEnviarTodos.setBounds(90, 110, 300, 17);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 0, 0));
        jLabel1.setText("SERVIDOR TCP");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(150, 10, 160, 17);

        mensajesTxt.setColumns(25);
        mensajesTxt.setRows(20);
        mensajesTxt.setEnabled(false);

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
        listadoClientes = new ArrayList<>();
        bIniciar.setEnabled(false);
        cbEnviarTodos.setEnabled(false);
        new Thread(new Runnable() {
            public void run() {
                try {
                    
                    InetAddress addr = InetAddress.getLocalHost();
                    int puerto = PORT;
                    if(cbEnviarTodos.isSelected()){
                        puerto +=1; 
                    }
                    serverSocket = new ServerSocket( puerto);
                    mensajesTxt.append("Servidor TCP en ejecución: "+ addr + " ,Puerto " + serverSocket.getLocalPort()+ "\n");
                    while (true) {
                        Socket clientSocket = serverSocket.accept();
                        listadoClientes.add(clientSocket);
                        new Thread(new Runnable(){
                            public void run(){
                                try{
                                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                                    mensajesTxt.append("Cliente conectado: " + clientSocket.getPort()+ "\n");
                                    
                                    if(cbEnviarTodos.isSelected()){
                                        for(Socket cliente_rem : listadoClientes){
                                            BufferedReader in_cr = new BufferedReader(new InputStreamReader(cliente_rem.getInputStream()));
                                            String linea;
                                            while ((linea = in_cr.readLine()) != null) {
                                                mensajesTxt.append("Cliente "+ cliente_rem.getPort() + ": " + linea + "\n");

                                                for(Socket cliente_des : listadoClientes){
                                                    PrintWriter out_d = new PrintWriter(cliente_des.getOutputStream(), true);
                                                    out_d.println("Cliente "+ cliente_rem.getPort() + ": " + linea + "\n");
                                                }
                                                
                                            }
                                        }
                                    }else{
                                    enviarMensaje(in, out, clientSocket);
                                    }
                                    

                                } 
                                catch(IOException ex){
                                    mensajesTxt.append("Error en la comunicación con el cliente: " + ex.getMessage() + "\n");
                                    ex.printStackTrace();
                                } 
                                finally {
                                    try{
                                        mensajesTxt.append("Cliente desconectado: " + clientSocket.getPort()+ "\n");
                                        clientSocket.close();
                                    }
                                    catch(IOException ex){
                                        mensajesTxt.append("Error cerrando el socket del cliente: " + ex.getMessage() + "\n");
                                        ex.printStackTrace();
                                    } 
                                    listadoClientes.remove(clientSocket);
                                }
                            }

                            private void enviarMensaje(BufferedReader in, PrintWriter out, Socket cliente) throws IOException {
                                
                            }
                        }).start();
                        
                    }
                } catch (IOException ex) {
                    bIniciar.setEnabled(true);
                    cbEnviarTodos.setEnabled(true);
                    mensajesTxt.append("Error en el servidor: " + ex.getMessage() + "\n");
                    ex.printStackTrace();
                    
                }
            }
        }).start();
    }

    // Variables declaration - do not modify
    private javax.swing.JButton bIniciar;
    private javax.swing.JCheckBox cbEnviarTodos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextArea mensajesTxt;
    private javax.swing.JScrollPane jScrollPane1;
}
