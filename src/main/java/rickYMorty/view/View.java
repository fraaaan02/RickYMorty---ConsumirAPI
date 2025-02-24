package rickYMorty.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * View Class, view basic with two combos, button and table for print data.
 */
public class View extends JFrame {

    // ATTRIBUTES
    private DefaultTableModel tableModel;
    private JTable table;
    private JComboBox<String> cbEpisodios;
    private JComboBox<String> cbTemporadas;
    private JButton btAceptar;
    private JLabel lbImagen;

    // CONSTRUCTOR
    public View() {
        defaultView();
        principalView();
        defaultTable();
    }

    /**
     * Default Table (Title)
     */
    public void defaultTable() {
        tableModel.addColumn("Nombre");
        tableModel.addColumn("Estado");
        tableModel.addColumn("Especie");
        tableModel.addColumn("Origen");
        tableModel.addColumn("Lugar");
    }

    /**
     * View with GridBagConstraints, inicializate attributes
     */
    private void principalView() {
        JLabel lbTemporadasYEpisodios = new JLabel("Temporadas y Episodios");
        cbTemporadas = new JComboBox<>();

        cbEpisodios = new JComboBox<>();

        tableModel = new DefaultTableModel(0, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JScrollPane scrollPane = getJScrollPane();

        btAceptar = new JButton("Aceptar");

        //IMAGE PANEL
        JPanel panelImagen = new JPanel();
        panelImagen.setLayout(new BorderLayout());
        panelImagen.setPreferredSize(new Dimension(220, 220));

        JLabel lbTituloImagen = new JLabel("Imagen", SwingConstants.CENTER);
        lbImagen = new JLabel();
        lbImagen.setHorizontalAlignment(JLabel.CENTER);
        lbImagen.setVerticalAlignment(JLabel.CENTER);
        lbImagen.setPreferredSize(new Dimension(200, 200));
        lbImagen.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        panelImagen.add(lbTituloImagen, BorderLayout.NORTH);
        panelImagen.add(lbImagen, BorderLayout.CENTER);

        //PRINCIPAL PANEL
        JPanel panelPrincipal = new JPanel(new GridBagLayout());
        GridBagConstraints gbcPanel = new GridBagConstraints();
        gbcPanel.insets = new Insets(5, 10, 5, 10);
        gbcPanel.anchor = GridBagConstraints.WEST;

        gbcPanel.gridx = 0;
        gbcPanel.gridy = 0;
        panelPrincipal.add(lbTemporadasYEpisodios, gbcPanel);

        gbcPanel.gridx = 1;
        panelPrincipal.add(cbTemporadas, gbcPanel);

        gbcPanel.gridx = 2;
        panelPrincipal.add(cbEpisodios, gbcPanel);

        gbcPanel.gridx = 3;
        panelPrincipal.add(btAceptar, gbcPanel);

        //TABLE
        gbcPanel.gridx = 0;
        gbcPanel.gridy = 1;
        gbcPanel.gridwidth = 3;
        gbcPanel.fill = GridBagConstraints.BOTH;
        gbcPanel.weightx = 0.7;
        gbcPanel.weighty = 1;
        panelPrincipal.add(scrollPane, gbcPanel);

        //IMAGE
        gbcPanel.gridx = 3;
        gbcPanel.gridwidth = 2;
        gbcPanel.weightx = 0.3;
        gbcPanel.fill = GridBagConstraints.NONE;
        gbcPanel.anchor = GridBagConstraints.CENTER;
        panelPrincipal.add(panelImagen, gbcPanel);

        //ADD PRINCIPAL PANEL
        this.add(panelPrincipal, BorderLayout.CENTER);
    }

    /**
     * Defined configuration the table
     *
     * @return New JScrollPane with table predefined
     */
    private JScrollPane getJScrollPane() {
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowSelectionAllowed(true);
        table.setColumnSelectionAllowed(false);

        return new JScrollPane(table);
    }

    /**
     * Method for convert url an image
     *
     * @param imageUrl Url the image
     */
    public void cargarImagenEnLabel(String imageUrl) {
        try {
            URL url = new URI(imageUrl).toURL();
            ImageIcon icon = new ImageIcon(url);
            Image img = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            lbImagen.setIcon(new ImageIcon(img));
        } catch (MalformedURLException e) {
            System.out.println("URL mal formada: " + e.getMessage());
        } catch (URISyntaxException e) {
            System.out.println("URI syntax mal formada: " + e.getMessage());
        }
    }

    /**
     * Default view, parameters basics
     */
    private void defaultView() {
        this.setTitle("Ricky & Morty");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(350, 200, 1000, 600);
        this.setLayout(new BorderLayout());
        this.setResizable(false);
    }

    // GETTERS
    public JTable getTable() {
        return table;
    }

    public JButton getBtAceptar() {
        return btAceptar;
    }

    public JComboBox<String> getCbEpisodios() {
        return cbEpisodios;
    }

    public JComboBox<String> getCbTemporadas() {
        return cbTemporadas;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }
}
