import ExcepcionesPersonalizadas.ExcepcionTorneoInvalido;
import ExcepcionesPersonalizadas.ExceptionEquipoNoEncontrado;
import ExcepcionesPersonalizadas.ExceptionNombreEquipoInvalido;
import ExcepcionesPersonalizadas.ExceptionPartidoNoEncontrado;
import ExcepcionesPersonalizadas.ExceptionJugadorInvalido;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;

public class InterfazMenu extends JFrame {

    private final Color COLOR_FONDO        = new Color(11, 19, 43);
    private final Color COLOR_BOTON        = new Color(28, 40, 75);
    private final Color COLOR_BOTON_HOVER  = new Color(40, 55, 95);
    private final Color COLOR_BLANCO       = new Color(255, 255, 255);
    private final Color COLOR_AZUL         = new Color(130, 150, 200);
    private final Color COLOR_CAMPO        = new Color(20, 30, 60);

    private Torneo torneo;
    private int idTorneoActual = -1; // ID en MySQL del torneo activo

    public InterfazMenu() {
        setTitle("Tu Liga Futbolera");
        setSize(520, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(COLOR_FONDO);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        JLabel lbl1 = new JLabel("TU LIGA", SwingConstants.CENTER);
        lbl1.setFont(new Font("SansSerif", Font.BOLD, 48));
        lbl1.setForeground(COLOR_BLANCO);
        lbl1.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lbl2 = new JLabel("FUTBOLERA", SwingConstants.CENTER);
        lbl2.setFont(new Font("SansSerif", Font.BOLD, 48));
        lbl2.setForeground(COLOR_BLANCO);
        lbl2.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblSub = new JLabel("GESTIONA TU COMPETICIÓN", SwingConstants.CENTER);
        lblSub.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblSub.setForeground(COLOR_AZUL);
        lblSub.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(Box.createVerticalStrut(40));
        add(lbl1); add(lbl2);
        add(Box.createVerticalStrut(8));
        add(lblSub);
        add(Box.createVerticalStrut(40));

        JPanel btnTorneo    = crearBoton("+",  "AÑADIR TORNEO",         "Crea un nuevo torneo");
        JPanel btnEquipo    = crearBoton("👥", "AÑADIR EQUIPO",         "Registra un equipo");
        JPanel btnArbitro   = crearBoton("🟨", "AÑADIR ÁRBITRO",        "Registra un árbitro");
        JPanel btnJugador   = crearBoton("👟", "AÑADIR JUGADOR",        "Añade jugador a un equipo");
        JPanel btnPartido   = crearBoton("⚽", "CREAR PARTIDO",         "Programa un encuentro");
        JPanel btnResultado = crearBoton("📝", "INTRODUCIR RESULTADO",  "Actualiza el marcador");
        JPanel btnTabla     = crearBoton("📊", "TABLA DE PUNTOS",       "Consulta la clasificación");

        btnTorneo   .addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent e) { abrirDialogoTorneo();    }});
        btnEquipo   .addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent e) { abrirDialogoEquipo();    }});
        btnTabla    .addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent e) { abrirDialogoTabla();     }});
        /*
        btnArbitro  .addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent e) { abrirDialogoArbitro();   }});
        btnJugador  .addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent e) { abrirDialogoJugador();   }});
        btnPartido  .addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent e) { abrirDialogoPartido();   }});
        btnResultado.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent e) { abrirDialogoResultado(); }});
        */





        add(btnTorneo);    add(Box.createVerticalStrut(12));
        add(btnEquipo);    add(Box.createVerticalStrut(12));
        add(btnArbitro);   add(Box.createVerticalStrut(12));
        add(btnJugador);   add(Box.createVerticalStrut(12));
        add(btnPartido);   add(Box.createVerticalStrut(12));
        add(btnResultado); add(Box.createVerticalStrut(12));
        add(btnTabla);     add(Box.createVerticalStrut(40));
    }


    // ================================================================
    //  DIÁLOGO: AÑADIR TORNEO
    // ================================================================
    private void abrirDialogoTorneo() {
        JDialog dialog = crearDialog("Añadir Torneo");
        JTextField txtNombre    = crearCampo();
        JTextField txtTemporada = crearCampo();

        JPanel form = crearFormulario(
                new String[]{"Nombre del torneo:", "Temporada (ej. 2026):"},
                new JTextField[]{txtNombre, txtTemporada}
        );

        JButton btnConfirmar = crearBotonAccion("Crear Torneo");
        JLabel  lblInfo      = infoLabel();

        btnConfirmar.addActionListener(e -> {

            //TORNEO

            try {
                // 1. Valida con tu clase Java (lanza ExcepcionTorneoInvalido si algo falla)
                torneo = Torneo.crearTorneo(
                        txtNombre.getText().trim(),
                        txtTemporada.getText().trim()
                );

                // 2. Solo si la validación pasa, guarda en MySQL
                idTorneoActual = MetodosDAOGuay.guardarTorneo(
                        txtNombre.getText().trim(),
                        txtTemporada.getText().trim()
                );

                mostrarExito(lblInfo, "Torneo guardado en BD con id " + idTorneoActual);
                txtNombre.setText(""); txtTemporada.setText("");

            } catch (ExcepcionTorneoInvalido ex) {
                mostrarError(lblInfo, ex.getMessage()); // ← Tu excepción salta aquí
            } catch (SQLException ex) {
                mostrarError(lblInfo, "Error de base de datos: " + ex.getMessage());
            }
        });

        ensamblarDialog(dialog, form, btnConfirmar, lblInfo);
    }

    // ================================================================
    //  DIÁLOGO: AÑADIR EQUIPO
    // ================================================================
    private void abrirDialogoEquipo() {
        if (torneo == null) { sinTorneo(); return; }

        JDialog dialog = crearDialog("Añadir Equipo");
        JTextField txtNombre = crearCampo();
        JTextField txtCiudad = crearCampo();

        JPanel form = crearFormulario(
                new String[]{"Nombre del equipo:", "Ciudad:"},
                new JTextField[]{txtNombre, txtCiudad}
        );

        JButton btnConfirmar = crearBotonAccion("Añadir Equipo");
        JLabel  lblInfo      = infoLabel();

        btnConfirmar.addActionListener(e -> {
            try {
                String nombre = txtNombre.getText().trim();
                String ciudad = txtCiudad.getText().trim();

                // 1. Valida con tu método (lanza ExceptionNombreEquipoInvalido)
                torneo.añadirEquipo(nombre);

                // 2. Guarda en MySQL
                int idEquipo = MetodosDAOGuay.guardarEquipo(nombre);

                mostrarExito(lblInfo, "Equipo \"" + nombre + "\" guardado (id " + idEquipo + ")");
                txtNombre.setText(""); txtCiudad.setText("");

            } catch (ExceptionNombreEquipoInvalido ex) {
                mostrarError(lblInfo, ex.getMessage());
            } catch (SQLException ex) {
                mostrarError(lblInfo, "Error de base de datos: " + ex.getMessage());
            }
        });

        ensamblarDialog(dialog, form, btnConfirmar, lblInfo);
    }

    // ================================================================
    //  DIÁLOGO: MOSTRAR TABLA
    // ================================================================
    private void abrirDialogoTabla() {

        if (torneo == null) {
            sinTorneo();
            return;
        }

        JDialog dialog = crearDialog("Tabla de Clasificación");
        dialog.setSize(700, 400);

        String[] columnas = {
                "Equipo", "Pts", "PJ", "G", "E", "P", "GF", "GC"
        };

        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

        ArrayList<Equipo> equipos = torneo.getEquipos();

        equipos.sort((a, b) -> {
            if (b.getPuntos() != a.getPuntos()) {
                return b.getPuntos() - a.getPuntos();
            }
            return b.getDiferenciaGoles() - a.getDiferenciaGoles();
        });

        for (Equipo equipo : equipos) {

            modelo.addRow(new Object[]{
                    equipo.getNombre(),
                    equipo.getPuntos(),
                    equipo.getPartidosJugados(),
                    equipo.getGanados(),
                    equipo.getEmpatados(),
                    equipo.getPerdidos(),
                    equipo.getGolesFavor(),
                    equipo.getGolesContra()
            });
        }

        JTable tabla = new JTable(modelo);

        JScrollPane scroll = new JScrollPane(tabla);

        dialog.add(scroll, BorderLayout.CENTER);

        dialog.setVisible(true);
    }
    // ================================================================
    //  HELPERS DE UI
    // ================================================================
    private JDialog crearDialog(String titulo) {
        JDialog d = new JDialog(this, titulo, true);
        d.setSize(440, 340);
        d.setLocationRelativeTo(this);
        d.setLayout(new BorderLayout(10, 10));
        d.getContentPane().setBackground(COLOR_FONDO);
        return d;
    }

    private JTextField crearCampo() {
        JTextField tf = new JTextField();
        tf.setBackground(COLOR_CAMPO);
        tf.setForeground(COLOR_BLANCO);
        tf.setCaretColor(COLOR_BLANCO);
        tf.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BOTON_HOVER),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        tf.setFont(new Font("SansSerif", Font.PLAIN, 13));
        return tf;
    }

    private JPanel crearFormulario(String[] etiquetas, JTextField[] campos) {
        JPanel panel = new JPanel(new GridLayout(etiquetas.length, 2, 8, 10));
        panel.setBackground(COLOR_FONDO);
        panel.setBorder(new EmptyBorder(20, 25, 10, 25));
        for (int i = 0; i < etiquetas.length; i++) {
            JLabel lbl = new JLabel(etiquetas[i]);
            lbl.setForeground(COLOR_AZUL);
            lbl.setFont(new Font("SansSerif", Font.PLAIN, 13));
            panel.add(lbl);
            panel.add(campos[i]);
        }
        return panel;
    }

    private JButton crearBotonAccion(String texto) {
        JButton btn = new JButton(texto);
        btn.setBackground(new Color(50, 100, 200));
        btn.setForeground(COLOR_BLANCO);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private JLabel infoLabel() {
        JLabel lbl = new JLabel(" ");
        lbl.setForeground(COLOR_AZUL);
        lbl.setHorizontalAlignment(SwingConstants.CENTER);
        return lbl;
    }

    private void ensamblarDialog(JDialog dialog, JPanel form, JButton btn, JLabel lblInfo) {
        JPanel sur = new JPanel(new GridLayout(2, 1, 0, 5));
        sur.setBackground(COLOR_FONDO);
        sur.setBorder(new EmptyBorder(0, 25, 20, 25));
        sur.add(btn);
        sur.add(lblInfo);
        dialog.add(form, BorderLayout.CENTER);
        dialog.add(sur, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private void mostrarError(JLabel lbl, String msg) {
        lbl.setForeground(new Color(255, 80, 80));
        lbl.setText("✗ " + msg);
    }

    private void mostrarExito(JLabel lbl, String msg) {
        lbl.setForeground(new Color(80, 200, 120));
        lbl.setText("✓ " + msg);
    }

    private void sinTorneo() {
        JOptionPane.showMessageDialog(this,
                "Primero debes crear un torneo.",
                "Sin torneo activo", JOptionPane.WARNING_MESSAGE);
    }

    private JPanel crearBoton(String icono, String titulo, String subtitulo) {
        JPanel panel = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.dispose();
            }
        };
        panel.setLayout(new BorderLayout(15, 0));
        panel.setBackground(COLOR_BOTON);
        panel.setOpaque(false);
        panel.setMaximumSize(new Dimension(420, 75));
        panel.setBorder(new EmptyBorder(10, 15, 10, 15));
        panel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel lblIcono = new JLabel(icono, SwingConstants.CENTER);
        lblIcono.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblIcono.setForeground(COLOR_BLANCO);

        JPanel panelIcono = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(45, 60, 100));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                g2.dispose();
            }
        };
        panelIcono.setOpaque(false);
        panelIcono.setPreferredSize(new Dimension(55, 55));
        panelIcono.add(lblIcono, BorderLayout.CENTER);

        JPanel textos = new JPanel(new GridLayout(2, 1));
        textos.setOpaque(false);

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 17));
        lblTitulo.setForeground(COLOR_BLANCO);

        JLabel lblSub = new JLabel(subtitulo);
        lblSub.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblSub.setForeground(COLOR_AZUL);

        textos.add(lblTitulo);
        textos.add(lblSub);

        JLabel flecha = new JLabel("→");
        flecha.setFont(new Font("SansSerif", Font.PLAIN, 24));
        flecha.setForeground(COLOR_AZUL);

        panel.add(panelIcono, BorderLayout.WEST);
        panel.add(textos, BorderLayout.CENTER);
        panel.add(flecha, BorderLayout.EAST);

        panel.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { panel.setBackground(COLOR_BOTON_HOVER); panel.repaint(); }
            public void mouseExited (MouseEvent e) { panel.setBackground(COLOR_BOTON);       panel.repaint(); }
        });

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InterfazMenu().setVisible(true));
    }
}