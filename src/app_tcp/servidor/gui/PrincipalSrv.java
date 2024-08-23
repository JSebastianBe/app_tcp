package app_tcp.servidor.gui;


import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Author: Vinni
 * changes: JSebastianB 2024
 */
public class PrincipalSrv extends javax.swing.JFrame {
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
        cbEnviarUnico = new javax.swing.JRadioButton();
        cbEnviarTodos = new javax.swing.JRadioButton();
        cbEnviarDirecto = new javax.swing.JRadioButton();
        bgRadios = new javax.swing.ButtonGroup();

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
        bIniciar.setBounds(100, 50, 250, 40);

        cbEnviarUnico.setFont(new java.awt.Font("Tahoma", 0, 14));
        cbEnviarUnico.setForeground(new java.awt.Color(0, 0, 0));
        cbEnviarUnico.setText("Comunicación única cliente-servidor");
        getContentPane().add(cbEnviarUnico);
        cbEnviarUnico.setBounds(90, 90, 300, 17);
        
        cbEnviarTodos.setFont(new java.awt.Font("Tahoma", 0, 14));
        cbEnviarTodos.setForeground(new java.awt.Color(0, 0, 0));
        cbEnviarTodos.setText("Enviar a todos los clientes conectados");
        getContentPane().add(cbEnviarTodos);
        cbEnviarTodos.setBounds(90, 110, 300, 17);
        
        cbEnviarDirecto.setFont(new java.awt.Font("Tahoma", 0, 14));
        cbEnviarDirecto.setForeground(new java.awt.Color(0, 0, 0));
        cbEnviarDirecto.setText("Enviar a un cliente específico");
        getContentPane().add(cbEnviarDirecto);
        cbEnviarDirecto.setBounds(90, 130, 300, 17);
        
        bgRadios.add(cbEnviarTodos);
        bgRadios.add(cbEnviarDirecto);
        bgRadios.add(cbEnviarUnico);

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
        if(!cbEnviarDirecto.isSelected() && !cbEnviarTodos.isSelected() && !cbEnviarUnico.isSelected())
        {
            mensajesTxt.append("Selecciona una de las opciones para iniciar el servidor.\n");
        }
        else
        {
            bIniciar.setEnabled(false);
            cbEnviarTodos.setEnabled(false);
            cbEnviarDirecto.setEnabled(false);
            cbEnviarUnico.setEnabled(false);
            new Thread(new Runnable() {
                public void run() {
                    try 
                    {
                        Set<Socket> listadoClientes = Collections.synchronizedSet(new HashSet<>());
                        InetAddress addr = InetAddress.getLocalHost();
                        int puerto = 0;
                        if(cbEnviarUnico.isSelected())
                        {
                            puerto = 12345; 
                        }
                        if(cbEnviarTodos.isSelected())
                        {
                            puerto = 12346; 
                        }
                        if(cbEnviarDirecto.isSelected())
                        {
                            puerto = 12347; 
                        }
                        serverSocket = new ServerSocket( puerto);
                        mensajesTxt.append("Servidor TCP en ejecución: "+ addr + " ,Puerto " + serverSocket.getLocalPort()+ "\n");
                        while (true) {
                            Socket clientSocket = serverSocket.accept();
                            listadoClientes.add(clientSocket);
                            System.out.println(listadoClientes.size());
                            new Thread(new Runnable(){
                                public void run(){
                                    try
                                    {
                                        mensajesTxt.append("Cliente conectado: " + clientSocket.getPort()+ "\n");
                                        if(cbEnviarTodos.isSelected())
                                        {
                                            enviarInfoTodos("Cliente conectado: " + clientSocket.getPort(), clientSocket);
                                            enviarMensajeTodos(clientSocket);
                                        }
                                        else if(cbEnviarDirecto.isSelected())
                                        {
                                            enviarInfoTodos("Cliente conectado: " + clientSocket.getPort(), clientSocket);
                                            enviarMensajeDirecto(clientSocket);
                                        }
                                        else
                                        {
                                            enviarMensaje(clientSocket);
                                            
                                        }
                                    } 
                                    catch(IOException ex)
                                    {
                                        /*mensajesTxt.append("Error en la comunicación con el cliente: " + ex.getMessage() + "\n");
                                        ex.printStackTrace();*/
                                    } 
                                    finally 
                                    {
                                        try
                                        {
                                            mensajesTxt.append("Cliente desconectado: " + clientSocket.getPort()+ "\n");
                                            clientSocket.close();
                                        }
                                        catch(IOException ex)
                                        {
                                            mensajesTxt.append("Error cerrando el socket del cliente: " + ex.getMessage() + "\n");
                                            ex.printStackTrace();
                                        } 
                                        
                                        listadoClientes.remove(clientSocket);
                                    }
                                }
                                
                                private void enviarMensajeDirecto(Socket remitente) throws IOException {
                                    String linea;
                                    BufferedReader in = new BufferedReader(new InputStreamReader(remitente.getInputStream()));
                                    while ((linea = in.readLine()) != null) 
                                    {
                                        mensajesTxt.append("Cliente "+ remitente.getPort() + ": " + linea + "\n");
                                        String mensaje = linea.split(":")[0];
                                        int puerto = Integer.valueOf(linea.split(":")[1]);
                                        for(Socket destinatario : listadoClientes)
                                        {
                                            if(destinatario.getPort() == puerto)
                                            {
                                                PrintWriter out_d = new PrintWriter(destinatario.getOutputStream(), true);
                                                out_d.println("Cliente "+ remitente.getPort() + ": " + mensaje + "");
                                            }   
                                        }

                                    }
                                }
                                
                                private void enviarMensajeTodos(Socket remitente) throws IOException {
                                    String linea;
                                    BufferedReader in = new BufferedReader(new InputStreamReader(remitente.getInputStream()));
                                    while ((linea = in.readLine()) != null) 
                                    {
                                        mensajesTxt.append("Cliente "+ remitente.getPort() + ": " + linea + "\n");
                                        String mensaje = linea.split(":")[0];
                                        for(Socket destinatario : listadoClientes)
                                        {
                                            if(destinatario != remitente)
                                            {
                                                PrintWriter out_d = new PrintWriter(destinatario.getOutputStream(), true);
                                                out_d.println("Cliente "+ remitente.getPort() + ": " + mensaje + "");
                                            }   
                                        }

                                    }
                                }

                                private void enviarInfoTodos(String linea, Socket remitente) throws IOException {
                                    for(Socket destinatario : listadoClientes)
                                    {
                                        if(destinatario != remitente)
                                        {
                                            PrintWriter out_d = new PrintWriter(destinatario.getOutputStream(), true);
                                            out_d.println(linea);
                                        }   
                                    }
                                }

                                private void enviarMensaje(Socket cliente) throws IOException {
                                    String linea;
                                    BufferedReader in = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
                                    PrintWriter out = new PrintWriter(cliente.getOutputStream(), true);
                                    while (!cliente.isClosed() && cliente.isConnected() && (linea = in.readLine()) != null) {
                                        String destinatario = linea.split(":")[0];
                                        String nombreArchivo = linea.split(":")[1];
                                        mensajesTxt.append("Cliente "+ cliente.getPort() + ": " + linea + "\n");
                                        /*
                                        // Manejo del archivo entrante
                                        try (DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
                                             FileOutputStream fos = new FileOutputStream("archivo_recibido.txt")) {

                                            // Leer el tamaño del archivo
                                            long fileSize = dis.readLong();
                                            byte[] buffer = new byte[4096];
                                            int bytesRead;
                                            long totalBytesRead = 0;

                                            // Leer el archivo en bloques
                                            while ((bytesRead = dis.read(buffer, 0, buffer.length)) != -1) {
                                                fos.write(buffer, 0, bytesRead);
                                                totalBytesRead += bytesRead;
                                                if (totalBytesRead >= fileSize) {
                                                    break;
                                                }
                                            }

                                            System.out.println("Archivo recibido y guardado.");
                                        } catch (IOException e) {
                                            System.err.println("Error al recibir el archivo: " + e.getMessage());
                                        }*/
                                            
                                        out.println("Cliente: "+ cliente.getPort() + "|| Arcvhivo: " + nombreArchivo + " || Destinatario: " + destinatario);
                                    }
                                    
                                    
                                }
                            }).start();

                        }
                    } catch (IOException ex) {
                        bIniciar.setEnabled(true);
                        cbEnviarTodos.setEnabled(true);
                        cbEnviarDirecto.setEnabled(true);
                        cbEnviarUnico.setEnabled(true);
                        mensajesTxt.append("Error en el servidor: " + ex.getMessage() + "\n");
                        ex.printStackTrace();
                    }
                }
            }).start();
        }
    }

    // Variables declaration - do not modify
    private javax.swing.JButton bIniciar;
    private javax.swing.JRadioButton cbEnviarUnico;
    private javax.swing.JRadioButton cbEnviarTodos;
    private javax.swing.JRadioButton cbEnviarDirecto;
    private javax.swing.ButtonGroup bgRadios;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextArea mensajesTxt;
    private javax.swing.JScrollPane jScrollPane1;
}
