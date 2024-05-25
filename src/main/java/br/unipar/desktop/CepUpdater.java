package br.unipar.desktop;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

public class CepUpdater {

    // Lista de CEPs
    private List<Cep> ceps;

    // Scheduler para atualizar o CEP a cada 5 minutos
    private final ScheduledExecutorService scheduler =
            Executors.newScheduledThreadPool(1);

    // Lock para garantir que apenas uma thread por vez
    private final ReentrantLock lock = new ReentrantLock();

    // Construtor
    public CepUpdater() {
        this.ceps = new ArrayList<>();
    }

    // Getter para a lista de CEPs
    public List<Cep> getCeps() {
        return ceps;
    }

    // Método para iniciar a atualização
    public CompletableFuture<List<Cep>> startUpdating() {
        CompletableFuture<List<Cep>> future = new CompletableFuture<>();

        final Runnable updater = () -> {
            try {
                lock.lock();
                try {
                    ceps.clear();
                    ceps.add(findCep());

                    future.complete(ceps);
                } finally {
                    lock.unlock();
                }
            } catch (Exception e) {
                future.completeExceptionally(e);
            }
        };
        scheduler.schedule(updater, 5, TimeUnit.SECONDS);
        return future;
    }

    // Método para parar a atualização
    public void stopUpdating(int timeout) {
        scheduler.shutdown();

        try {
            if(!scheduler.awaitTermination(timeout, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
        }
    }

    // Método para buscar o CEP
    private Cep findCep() throws IOException {
        URL url = new
                URL("https://viacep.com.br/ws/85900180/json/");

        BufferedReader in = new BufferedReader(
                new InputStreamReader(url.openStream()));

        String inputLine;
        String result = "";
        while ((inputLine = in.readLine()) != null) {
            result += inputLine;
        }
        in.close();

        Cep cep = Cep.unmarshalFromJson(result);

        return cep;
    }

    // Método para imprimir o CEP
    public void printString() {
        final Runnable print = new Runnable() {
            public void run() {
                for (Cep cep : ceps) {
                    System.out.println(cep.toString());
                }

                stopUpdating(1);
            }
        };
        scheduler.schedule(print, 10, TimeUnit.SECONDS);
    }
}
