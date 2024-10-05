package com.av.first;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@SpringBootApplication
public class FirstApplication {

	public static void main(String[] args) {
		SpringApplication.run(FirstApplication.class, args);
	}

}


public class FipeApi {

    private static final String BASE_URL = "https://parallelum.com.br/fipe/api/v1/carros/";

    public static void main(String[] args) {
        // Obter lista de marcas
        JsonArray marcas = obterMarcas();
        System.out.println("Marcas: " + marcas);

        // Obter modelos da marca com ID 59
        JsonArray modelos = obterModelos(59);
        System.out.println("Modelos da marca 59: " + modelos);

        // Consultar anos do modelo com ID 5940 da marca 59
        JsonArray anos = consultarAnos(59, 5940, 2014);
        System.out.println("Anos do modelo 5940: " + anos);
    }

    private static JsonArray obterMarcas() {
        String url = BASE_URL + "marcas";
        return fazerRequisicao(url);
    }

    private static JsonArray obterModelos(int marcaId) {
        String url = BASE_URL + "marcas/" + marcaId + "/modelos";
        return fazerRequisicao(url);
    }

    private static JsonArray consultarAnos(int marcaId, int modeloId, int ano) {
        String url = BASE_URL + "marcas/" + marcaId + "/modelos/" + modeloId + "/anos/" + ano + "-3";
        return fazerRequisicao(url);
    }

    private static JsonArray fazerRequisicao(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                System.out.println("Erro: " + conn.getResponseCode());
                return null;
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            Gson gson = new Gson();
            return gson.fromJson(response.toString(), JsonArray.class);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
