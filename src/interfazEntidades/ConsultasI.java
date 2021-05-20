package interfazEntidades;

import conexion.Conector;
import entidades.Consultas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;

public class ConsultasI extends JFrame{
    private JTextField textField1;
    private JTextField textField2;
    private JButton consultarConsultaButton;
    private JButton nuevaConsultaButton;
    private JButton cambiarConsultaButton;
    private JButton deshacerConsultaButton;
    private JTable table1;
    private JTextField textField3;
    private JPanel panelRaiz;
    DefaultTableModel modelo;

    public ConsultasI(){
        JFrame frame = new JFrame("Clientes");
        frame.setContentPane(panelRaiz);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(900,900);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        modelo = new DefaultTableModel();
        modelo.addColumn("colAsesor");
        modelo.addColumn("colCliente");
        modelo.addColumn("colFecha");
        modelo.addRow(new Object[] {
                "<html><div style='font-size: 10px;'><b>ID Asesor</b></div></html>",
                "<html><div style='font-size: 10px;'><b>ID Cliente</b></div></html>",
                "<html><div style='font-size: 10px;'><b>Fecha</b></div></html>"
        });
        table1.setModel(modelo);
        mostrarDatos();


        consultarConsultaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Integer idPeticion = Integer.parseInt(JOptionPane.showInputDialog(null, "Introduce el ID del cliente"));
                try {
                    ResultSet resultado = Conector.consulta("SELECT * FROM Consultas where idCliente=" + idPeticion);
                    while (resultado.next()) {
                        if (idPeticion.toString().equals(resultado.getString("idCliente"))) {
                            JOptionPane.showMessageDialog(null, resultado.getString("idAsesor"));
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Error en la consulta" + e);

                }
            }
        });

        nuevaConsultaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Consultas consulta = recuperarDatos();
                String insert = String.format("insert into consultas(idCliente,idAsesor,fecha) " +
                        "VALUES ('%s','%s','%s')", consulta.getIdAsesor(), consulta.getIdCliente(), consulta.getFecha());
                Conector.sentenciaSql(insert);
                mostrarDatos();
                textField1.setText("");
                textField2.setText("");
                textField3.setText("");
            }
        });

        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                try {
                    if (mouseEvent.getClickCount() == 1) {
                        JTable receptor = (JTable) mouseEvent.getSource();
                        textField1.setText(receptor.getModel().getValueAt(receptor.getSelectedRow(), 0).toString());
                        textField2.setText(receptor.getModel().getValueAt(receptor.getSelectedRow(), 1).toString());
                        textField3.setText(receptor.getModel().getValueAt(receptor.getSelectedRow(), 2).toString());
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                }

            }
        });

        deshacerConsultaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Consultas consulta = recuperarDatos();
                String delete = String.format("DELETE FROM Consultas WHERE idCliente = %d ", consulta.getIdCliente());
                Conector.sentenciaSql(delete);
                mostrarDatos();
            }
        });

        cambiarConsultaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Consultas consulta = recuperarDatos();
                String edit = String.format("UPDATE Consultas SET fecha='%s' WHERE idAsesor =  %o and idCliente = %o", consulta.getFecha(), consulta.getIdAsesor(), consulta.getIdCliente());
                Conector.sentenciaSql(edit);
                textField1.setText("");
                textField2.setText("");
                textField3.setText("");
                mostrarDatos();
            }
        });


    }


    public void mostrarDatos() {
        while (modelo.getRowCount() > 1) {
            modelo.removeRow(1);
        }

        try {
            ResultSet resultado = Conector.consulta("SELECT * FROM Consultas");
            while (resultado.next()) {

                Object[] oConsulta = {resultado.getString("idAsesor"), resultado.getString("idCliente"), resultado.getString("fecha")};
                modelo.addRow(oConsulta);
            }

        } catch (Exception e) {
            System.out.println("Error en la consulta" + e);

        }
    }

    public Consultas recuperarDatos() {
        Consultas consulta = new Consultas();
        consulta.setIdCliente((textField2.getText().isEmpty()) ? 0 : Integer.parseInt(textField2.getText()));
        consulta.setIdAsesor(Integer.parseInt(textField1.getText()));
        consulta.setFecha(textField3.getText());
        return consulta;
    }

}
