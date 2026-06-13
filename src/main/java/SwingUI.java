import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SwingUI {

    //Atributos
    private Torneo torneo;
    private JPanel mainPanel;
    private JButton añadirTorneoButton;
    private JButton crearPartidoButton;
    private JButton introducirResultadosButton;
    private JButton tablaDePuntosButton;
    private JButton BotonAceptar;

    //Contructor
    public SwingUI() {

        torneo = new Torneo("Liga Santander");

        añadirTorneoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {

                    torneo.añadirEquipo("BarcelonaFC");

                } catch (Exception ex) {

                    JOptionPane.showMessageDialog(
                            null,
                            ex.getMessage()
                    );

                }

            }
        });

    }


    //Metodos
    public static void main(String[] args) {
        JFrame frame = new JFrame("SwingUI");
        frame.setContentPane(new SwingUI().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setSize(400,400);
        frame.setVisible(true);

    }
}
