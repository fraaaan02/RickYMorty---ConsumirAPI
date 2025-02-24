package rickYMorty;

import rickYMorty.controller.Controller;
import rickYMorty.model.Model;
import rickYMorty.view.View;

/**
 * MAIN CLASS
 * @author Francisco Joaquín López Ros
 */
public class Main {
    public static void main(String[] args) {
        new Controller(new Model(), new View());
    }
}
