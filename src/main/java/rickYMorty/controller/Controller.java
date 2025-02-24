package rickYMorty.controller;

import rickYMorty.model.Model;
import rickYMorty.view.View;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Controller Class, communicate view and model
 */
public class Controller {

    //ATTRIBUTES
    private final Model model;
    private final View view;
    private final String defaultItemCombo = "Selecciona...";

    //CONSTRUCTOR
    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;

        loadData();

        view.setVisible(true);
    }

    /**
     * Initialize the method for load in Controller
     */
    private void loadData() {
        cargarDatos();

        view.getBtAceptar().addActionListener(_ -> cargarTabla());

        view.getTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                seleccionarPersonaje();
            }
        });
    }

    /**
     * Collect the row select, catch the url and load image in the label
     */
    private void seleccionarPersonaje() {
        int filaSeleccionada = view.getTable().getSelectedRow();

        if (filaSeleccionada != -1) {
            String nombrePersonaje = (String) view.getTable().getValueAt(filaSeleccionada, 0);
            String urlImagen = Model.obtenerUrlImagen(nombrePersonaje);

            assert urlImagen != null : "No se puede seleccionar una imagen";
            view.cargarImagenEnLabel(urlImagen);
        }
    }


    /**
     * Loads data in the tableModel
     * Throw a Thread so that JSwing doesn't stop
     * Throw another Thread so load information
     */
    private void cargarTabla() {
        String epi = recogerDatos();
        List<String> personajes = model.obtenerPersonajesDelEpisodio(epi);

        assert epi != null : "Episodios no encontrado";
        assert personajes != null : "Personajes no encontrado";

        view.getTableModel().setRowCount(0);

        new Thread(() -> {
            for (String personaje : personajes) {
                new Thread(() -> {
                    List<String> infoPersonaje = model.obtenerInfoPersonaje(personaje);

                    view.getTableModel().addRow(new Object[]{
                            infoPersonaje.get(0),
                            infoPersonaje.get(1),
                            infoPersonaje.get(2),
                            infoPersonaje.get(3),
                            infoPersonaje.get(4),
                    });
                }).start();
            }
        }).start();
    }

    /**
     * Collect data combos
     *
     * @return null if not find the episode but yes find return the season and episode in this format S0XE0X
     */
    private String recogerDatos() {
        String temporada = Objects.requireNonNull(view.getCbTemporadas().getSelectedItem()).toString();
        int episodio = view.getCbEpisodios().getSelectedIndex() + 1;

        for (String epi : model.getEpisodios()) {
            if (temporada.equals(epi.substring(0, 3))) {
                if (episodio == (Integer.parseInt(epi.substring(4, 6)))) {
                    return epi;
                }
            }
        }
        return null;
    }

    /**
     * Method for up data in the combos
     * Default add defaultItemCombo
     * Listener for when you select the season, the episode loads
     */
    private void cargarDatos() {
        Map<String, List<String>> episodiosPorTemporada = model.obtenerEpisodios();

        assert episodiosPorTemporada != null : "Temporada y Episodios no encontrados";

        view.getCbTemporadas().removeAllItems();
        view.getCbEpisodios().removeAllItems();

        view.getCbTemporadas().addItem(defaultItemCombo);
        view.getCbEpisodios().addItem(defaultItemCombo);

        for (String temporada : episodiosPorTemporada.keySet()) {
            view.getCbTemporadas().addItem(temporada);
        }

        view.getCbTemporadas().addActionListener(_ -> {
            String temporadaSeleccionada = (String) view.getCbTemporadas().getSelectedItem();
            view.getCbEpisodios().removeAllItems();

            if (temporadaSeleccionada != null && !temporadaSeleccionada.equals(defaultItemCombo) &&
                    episodiosPorTemporada.containsKey(temporadaSeleccionada)) {

                for (String episodio : episodiosPorTemporada.get(temporadaSeleccionada)) {
                    view.getCbEpisodios().addItem(episodio);
                }
            } else {
                view.getCbEpisodios().addItem(defaultItemCombo);
            }
        });
    }
}
