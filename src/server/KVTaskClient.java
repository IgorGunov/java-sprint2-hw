package server;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KVTaskClient {
    private final String url;
    private final String apiKey;

    public KVTaskClient(String url){
        this.url = url;
        apiKey = getKey();
    }

    public String getKey() {
        String line = "";
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create(url + "register");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            line = response.body();
        } catch (IOException | InterruptedException e) {
            System.out.println("Во время выполнения запроса по url-адресу: '" + uri + "', возникла ошибка.\n" +
                    "Проверьте адрес и повторите попытку.");
        }
        return line;
    }

    public void put(String key, String json) {
        URI uri = URI.create(url + "save" + "/" + key  + "/" + "?" + "API_KEY=" + apiKey);
        HttpClient client = HttpClient.newHttpClient() ;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .version(HttpClient.Version.HTTP_1_1)
                .header("Accept", "application/json")
                .build();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        try {
            client.send(request, handler);
        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    public String load(String key) {
        String line = "";
        URI uri = URI.create(url + "load" + "/" + key  + "/" + "?" + "API_KEY=" + apiKey);
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .build();
        HttpClient client = HttpClient.newHttpClient() ;
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            line = response.body();
        } catch (IOException | InterruptedException e) {
            System.out.println("Во время выполнения запроса по url-адресу: '" + uri + "', возникла ошибка.\n" +
                    "Проверьте адрес и повторите попытку.");
        }
        return line;
    }
}
