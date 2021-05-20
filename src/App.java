import conexion.Conector;
import entidades.Asesor;
import entidades.Cliente;
import entidades.Consultas;
import interfazEntidades.ConsultasI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App {
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JPanel panelRaiz;
    private JTextField textField1;
    private JButton cerrarBaseDeDatosButton;

    public App(){
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Conector.conectar();
                if (Conector.conectar()==true){
                    textField1.setText("\t Conectado");
                }
                ConsultasI consulta = new ConsultasI();
                consulta.setVisible(true);
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Conector.conectar();
                if (Conector.conectar()==true){
                    textField1.setText("\t Conectado");
                }
                //Asesor asesor = new Asesor();
                //asesor.setVisible(true);
            }
        });

        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Conector.conectar();
                if (Conector.conectar()==true){
                    textField1.setText("\t Conectado");
                }
                //Cliente cliente = new Cliente();
                //cliente.setVisible(true);
            }
        });

        cerrarBaseDeDatosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Conector.cerrar();
                textField1.setText("\t Conexion cerrada");
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("App");
        frame.setContentPane(new App().panelRaiz);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setSize(1200,600);
        frame.setVisible(true);
    }
}
