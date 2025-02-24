package rickYMorty.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.List;

/**
 * Model Class, works with the API the Rick & Morty
 */
public class Model {

    //ATTRIBUTE
    private static HttpClient client = null;
    private Set<String> episodios;
    private String urlEpisode = "https://rickandmortyapi.com/api/episode";
    private final String KEY_NAME = "name";

    //CONSTRUCTOR
    public Model() {
        client = HttpClient.newHttpClient();
    }

    /**
     * Method for collect data the API for specific season, name and episode complete
     *
     * @return Map the String(Season in format S0X) and List<String>(Name the episodes)
     */
    public Map<String, List<String>> obtenerEpisodios() {
        Map<String, List<String>> episodiosPorTemporada = new TreeMap<>();
        episodios = new TreeSet<>();

        while (urlEpisode != null) {
            JSONObject jsonResponse = realizarPetitionHttp(urlEpisode);
            assert (jsonResponse != null) : "Json Null";

            String KEY_RESULTS = "results";
            JSONArray results = jsonResponse.getJSONArray(KEY_RESULTS);

            for (int i = 0; i < results.length(); i++) {
                JSONObject episodeJson = results.getJSONObject(i);
                String KEY_EPISODE = "episode";
                String episodio = episodeJson.getString(KEY_EPISODE);
                String nombre = episodeJson.getString(KEY_NAME);
                String temporada = episodio.substring(0, 3);

                episodios.add(episodio);
                // Validation if not exists, add temporada and nombre(ArrayList)
                episodiosPorTemporada.computeIfAbsent(temporada, _ -> new ArrayList<>()).add(nombre);
            }
            urlEpisode = jsonResponse.isNull("info") ? null : jsonResponse.getJSONObject("info").optString("next", null);
        }
        return episodiosPorTemporada;
    }

    /**
     * Method for collect characters in the API
     *
     * @param episodioCompleto Catch episode complete in this format S0XE0X
     * @return A list of specific characters from String
     */
    public List<String> obtenerPersonajesDelEpisodio(String episodioCompleto) {
        List<String> personajes = new ArrayList<>();
        String url = "https://rickandmortyapi.com/api/episode/?episode=" + episodioCompleto;

        JSONObject jsonResponse = realizarPetitionHttp(url);
        assert (jsonResponse != null) : "Json Null";

        JSONArray results = jsonResponse.getJSONArray("results");

        for (int i = 0; i < results.length(); i++) {
            JSONArray characters = results.getJSONObject(i).getJSONArray("characters");
            for (int j = 0; j < characters.length(); j++) {
                personajes.add(characters.getString(j));
            }
        }
        return personajes;
    }

    /**
     * Method for collect characters details
     *
     * @param personajeUrl Catch the Url from characters
     * @return A list of characters details from format String
     */
    public List<String> obtenerInfoPersonaje(String personajeUrl) {
        List<String> infoPersonaje = new ArrayList<>();
        JSONObject jsonResponse = realizarPetitionHttp(personajeUrl);
        assert (jsonResponse != null) : "Json Null";

        infoPersonaje.add(jsonResponse.getString(KEY_NAME));
        infoPersonaje.add(jsonResponse.getString("status"));
        infoPersonaje.add(jsonResponse.getString("species"));

        JSONObject origin = jsonResponse.getJSONObject("origin");
        infoPersonaje.add(origin.getString("name"));

        JSONObject location = jsonResponse.getJSONObject("location");
        infoPersonaje.add(location.getString("name"));

        infoPersonaje.add(jsonResponse.getString("image"));

        return infoPersonaje;
    }

    /**
     * Default JSONObject in this model
     *
     * @param url URL I need
     * @return new JSONObject with the body response
     */
    private static JSONObject realizarPetitionHttp(String url) {
        try {
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return new JSONObject(response.body());
            } else {
                System.out.println("Error en la solicitud HTTP: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    /**
     * Method for collect url image from name in API
     *
     * @param nombrePersonaje Name the characters
     * @return Url the characters
     */
    public static String obtenerUrlImagen(String nombrePersonaje) {
        String urlAPI = "https://rickandmortyapi.com/api/character/?name=" + URLEncoder.encode(nombrePersonaje, StandardCharsets.UTF_8);

        JSONObject jsonResponse = realizarPetitionHttp(urlAPI);
        assert (jsonResponse != null) : "Json Null";

        JSONArray results = jsonResponse.getJSONArray("results");
        assert (results != null) : "Results Null";

        return results.getJSONObject(0).getString("image");
    }

    //GETTERS
    public Set<String> getEpisodios() {
        return episodios;
    }
}
