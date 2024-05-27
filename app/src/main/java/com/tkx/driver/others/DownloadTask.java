package com.tkx.driver.others;

import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Bhuvneshwar on 11/10/2016.
 */
public class DownloadTask extends AsyncTask<String, Void, String> {

    // Baixando dados em thread não UI
    @Override
    protected String doInBackground(String... url) {

        // Para armazenar dados do serviço web
        String data = "";

        try {
            // Buscando os dados do serviço web
            data = downloadUrl(url[0]);
        } catch (Exception e) {
            Log.d("Background Task", e.toString());
        }
        return data;
    }

    // Executa no thread UI, após a execução de doInBackground()
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        ParserTask parserTask = new ParserTask();
        // Invoca o thread para analisar os dados JSON
        parserTask.execute(result);
    }

    // Um método para baixar dados JSON do URL

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);
            // Criando uma conexão http para se comunicar com url
            urlConnection = (HttpURLConnection) url.openConnection();
            // Conectando ao URL
            urlConnection.connect();
            // Lendo dados do URL
            iStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
            StringBuffer sb = new StringBuffer();
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            data = sb.toString();
            br.close();
        } catch (Exception e) {
            Log.e("Exception", e + "");
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    // Uma classe para analisar o Google Directions em formato JSON

    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;
            try {
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();
                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        // Executa no thread da UI, após o processo de análise
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;

            // Percorrendo todas as rotas
            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();

                // Buscando a i-ésima rota
                List<HashMap<String, String>> path = result.get(i);

                // Buscando todos os pontos na i-ésima rota
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);
                    points.add(position);
                }

                // Adicionando todos os pontos da rota às opções de linha
                lineOptions.addAll(points);
                lineOptions.width(20);
                lineOptions.color(Color.parseColor("#3498db"));
            }

            // Desenhando polilinha no Google Map para a i-ésima rota
        }
    }

    public class DirectionsJSONParser {
        // Recebe um JSONObject e retorna uma lista de listas contendo latitude e longitude

        public List<List<HashMap<String, String>>> parse(JSONObject jObject) {
            List<List<HashMap<String, String>>> routes = new ArrayList<>();
            JSONArray jRoutes = null;
            JSONArray jLegs = null;
            JSONArray jSteps = null;

            try {
                jRoutes = jObject.getJSONArray("routes");

                // Percorrendo todas as rotas
                for (int i = 0; i < jRoutes.length(); i++) {
                    jLegs = ((JSONObject) jRoutes.get(i)).getJSONArray("legs");
                    List path = new ArrayList<>();

                    // Atravessando todas as pernas
                    for (int j = 0; j < jLegs.length(); j++) {
                        jSteps = ((JSONObject) jLegs.get(j)).getJSONArray("steps");

                        // Percorrendo todas as etapas
                        for (int k = 0; k < jSteps.length(); k++) {
                            String polyline = "";
                            polyline = (String) ((JSONObject) ((JSONObject) jSteps.get(k)).get("polyline")).get("points");
                            List<LatLng> list = decodePoly(polyline);

                            // Atravessando todos os pontos
                            for (int l = 0; l < list.size(); l++) {
                                HashMap<String, String> hm = new HashMap<>();
                                hm.put("lat", Double.toString(((LatLng) list.get(l)).latitude));
                                hm.put("lng", Double.toString(((LatLng) list.get(l)).longitude));
                                path.add(hm);
                            }
                        }
                        routes.add(path);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
            }
            return routes;
        }
    }

    //    Método para decodificar pontos de polilinha

    //    Courtesy : jeffreysambells.com/2010/05/27/decoding-polylines-from-google-maps-direction-api-with-java

    private List<LatLng> decodePoly(String encoded) {

        List<LatLng> poly = new ArrayList<>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }
        return poly;
    }
}
