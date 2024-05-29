package br.unipar.desktop;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class TesteAPI {

    public static void main(String[] args) {
        try {
            URL url = new URL("http://localhost:8080/pdv/cliente/get/tudo");
            HttpURLConnection connection =
                    (HttpURLConnection) url.openConnection();

            BufferedReader in = new
                    BufferedReader(new InputStreamReader(url.openStream()));

            String inputLine;
            String result = "";
            while ((inputLine = in.readLine()) != null) {
                result += inputLine;
            }

            in.close();
            List<Cliente> clienteList = Cliente.unmarshalFromJson(result);

            for (Cliente cliente : clienteList) {
                System.out.println(cliente.toString());
            }

            int code = connection.getResponseCode();
            System.out.println("Response code: " + code);

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

//        try {
//            URL url = new URL("http://localhost:8080/pdv/cliente/post");
//            HttpURLConnection connection =
//                    (HttpURLConnection) url.openConnection();
//
//            connection.setRequestMethod("POST");
//            connection.setDoOutput(true);
//            connection.setRequestProperty("Content-Type", "application/json");
//
//            Cliente cliente = new Cliente("João", "jao@unipar.br", "(45) 99558-5566");
//
//            String json = Cliente.marshalToJson(cliente);
//
//            try (OutputStream os = connection.getOutputStream()) {
//                byte[] input = json.getBytes("utf-8");
//                os.write(input, 0, input.length);
//            }
//
//            int code = connection.getResponseCode();
//            System.out.println("Response code: " + code);
//
//            connection.disconnect();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
