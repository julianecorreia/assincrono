//package br.unipar.desktop;
//
//import java.io.IOException;
//import java.io.OutputStream;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//import java.util.concurrent.CompletableFuture;
//
//public class Main {
//
////    public static void main(String[] args) {
////        lerArquivoAssincrono("arquivo.txt");
////        System.out.println("Fim do método main.");
////
////    }
////
////    private static void lerArquivoAssincrono(String nomeArquivo) {
////        System.out.println("Iniciando a leitura do arquivo " + nomeArquivo);
////        new Thread(() -> {
////            try {
////                Thread.sleep(5000); //simulando uma leitura demorada
////            } catch (InterruptedException e) {
////                e.printStackTrace();
////            }
////            System.out.println("Leitura concluída com sucesso.");
////        }, "Leitura").start();
////        System.out.println("Leitura em andamento...");
////    }
//
//    //main para GET
////    public static void main(String[] args) {
////        System.out.println("Iniciando a aplicação...");
////
////        // Lista de CEPs utilizadas na aplicação
////        List<Cep> cepList = new ArrayList<>();
////
////        // Inicia o updater
////        CepUpdater cepUpdater = new CepUpdater();
////        CompletableFuture<List<Cep>> future = cepUpdater.startUpdating();
////
////        // Adiciona um listener para quando a atualização terminar
////        future.thenAccept(ret -> {
////            cepList.clear(); // Limpa a lista atual
////            cepList.addAll(ret); // Adiciona os novos CEPs
////            System.out.println("CEPs atualizados");
////
////            // Imprime os CEPs atualizados
////            for (Cep cep : cepList) {
////                System.out.println(cep.toString());
////            }
////        });
////
////        // Imprime os CEPs atuais (ocorre antes da atualização)
////        System.out.println("Ceps atuais");
////        for (Cep cep : cepList) {
////            System.out.println(cep.toString());
////        }
////
////        // Aguarda 10 segundos para fechar o updating,
////        // Caso a aplicação termine antes de realizar o update, nunca vai atualizar a lista.
////        cepUpdater.stopUpdating(10);
////        System.out.println("Fim da aplicação.");
////
////    }
//
//    //main para POST
//    public static void main(String[] args) {
//        try {
//            // Cria um objeto HttpURLConnection para a URL desejada
//            URL url = new URL("http://localhost:8080/api/usuarios");
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//
//            // Configura a solicitação para ser um POST
//            conn.setRequestMethod("POST");
//            conn.setDoOutput(true);
//            conn.setRequestProperty("Content-Type", "application/json; utf-8");
//
//            // Cria um objeto Cep
//            Cep cep = new Cep();
//            // Preencha os campos do objeto Cep aqui...
//
//            // Converte o objeto Cep para uma string JSON
//            String jsonInputString = Cep.marshalToJson(cep);
//
//            // Envia o objeto JSON para a URL
//            try(OutputStream os = conn.getOutputStream()) {
//                byte[] input = jsonInputString.getBytes("utf-8");
//                os.write(input, 0, input.length);
//            }
//
//            // Obtém a resposta da API
//            int code = conn.getResponseCode();
//            System.out.println(getResponseMessage(code));
//
//            conn.disconnect();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    // Método para obter a mensagem de resposta HTTP facilitada
//    public static String getResponseMessage(int responseCode) {
//        switch (responseCode) {
//            case HttpURLConnection.HTTP_OK:
//                return "OK (200): A solicitação foi bem-sucedida.";
//            case HttpURLConnection.HTTP_CREATED:
//                return "Created (201): A solicitação foi bem-sucedida e um novo recurso foi criado.";
//            case HttpURLConnection.HTTP_ACCEPTED:
//                return "Accepted (202): A solicitação foi aceita para processamento, mas o processamento ainda não foi concluído.";
//            case HttpURLConnection.HTTP_NO_CONTENT:
//                return "No Content (204): A solicitação foi bem-sucedida, mas não há representação para retornar.";
//            case HttpURLConnection.HTTP_BAD_REQUEST:
//                return "Bad Request (400): A solicitação não pôde ser entendida ou estava faltando parâmetros necessários.";
//            case HttpURLConnection.HTTP_UNAUTHORIZED:
//                return "Unauthorized (401): A autenticação falhou ou ainda não foi fornecida.";
//            case HttpURLConnection.HTTP_FORBIDDEN:
//                return "Forbidden (403): A autenticação foi fornecida, mas o usuário autenticado não tem acesso ao recurso solicitado.";
//            case HttpURLConnection.HTTP_NOT_FOUND:
//                return "Not Found (404): O recurso solicitado não pôde ser encontrado.";
//            case HttpURLConnection.HTTP_INTERNAL_ERROR:
//                return "Internal Server Error (500): Uma condição de erro inesperada foi encontrada e nenhuma informação mais específica está disponível.";
//            default:
//                return "Código de resposta desconhecido.";
//        }
//    }
//}