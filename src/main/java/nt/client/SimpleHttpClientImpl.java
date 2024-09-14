package main.java.nt.client;

import main.java.nt.protocol.*;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class SimpleHttpClientImpl extends SimpleHttpClient {

    @Override
    public HttpResponse send(SimpleHttpRequest request) throws IOException {
        int port = resolvePort(request);

        try (Socket socket = new Socket(request.uri().getHost(), port);
             BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            sendHttpRequest(out, request);

            return readHttpResponse(in);

        }


    }

    private int resolvePort(SimpleHttpRequest request) {
        return request.uri().getPort() == -1 ? 80 : request.uri().getPort();
    }

    private void sendHttpRequest(BufferedWriter out, SimpleHttpRequest request) throws IOException {
        out.write(buildRequestLine(request));

        // Cabeçalhos obrigatórios e adicionais
        out.write("Host: " + request.uri().getHost() + "\r\n");
        writeHeaders(out, request);

        //  Corpo (se necessário)
        if (request.httpMethod() == HttpMethod.POST && request.body().isPresent()) {
            writeRequestBody(out, request);
        } else {
            out.write("\r\n"); // Linha em branco para terminar os cabeçalhos
        }

        out.flush();
    }

    private String buildRequestLine(SimpleHttpRequest request) {
        return request.httpMethod().name() + " " + request.uri().getPath() + " HTTP/1.1\r\n";
    }

    private void writeHeaders(BufferedWriter out, SimpleHttpRequest request) throws IOException {
        request.headers().map().forEach((key, value) -> {
            try {
                out.write(key + ": " + String.join(",", value) + "\r\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void writeRequestBody(BufferedWriter out, SimpleHttpRequest request) throws IOException {
        byte[] bodyContent = request.body().get().getContent();
        out.write("Content-Length: " + bodyContent.length + "\r\n");
        out.write("\r\n"); // Linha em branco para terminar os cabeçalhos
        out.write(new String(bodyContent)); // Corpo
    }

    private HttpResponse readHttpResponse(BufferedReader in) throws IOException {
        // Ler a linha de status
        String statusLine = in.readLine();
        if (statusLine == null) {
            throw new IOException("Nenhuma resposta recebida do servidor");
        }

        String[] statusParts = statusLine.split(" ");
        int statusCode = Integer.parseInt(statusParts[1]);

        // Ler os cabeçalhos da resposta
        Map<String, String> headers = readResponseHeaders(in);

        // Ler o corpo da resposta
        HttpEntity entity = readResponseBody(in, headers);

        return new HttpResponse(entity, statusCode, headers);
    }

    private Map<String, String> readResponseHeaders(BufferedReader in) throws IOException {
        Map<String, String> headers = new HashMap<>();
        String headerLine;
        while (!(headerLine = in.readLine()).isEmpty()) {
            String[] headerParts = headerLine.split(": ");
            headers.put(headerParts[0], headerParts[1]);
        }
        return headers;
    }

    private HttpEntity readResponseBody(BufferedReader in, Map<String, String> headers) throws IOException {
        StringBuilder bodyBuilder = new StringBuilder();
        String bodyLine;

        while ((bodyLine = in.readLine()) != null) {
            bodyBuilder.append(bodyLine).append("\n");
        }

        String contentType = headers.getOrDefault("Content-Type", "application/octet-stream");
        byte[] contentBytes = bodyBuilder.toString().getBytes();

        if (contentType.startsWith("text/")) {
            return new StringEntity(new String(contentBytes), contentType);
        } else {
            return new ByteArrayEntity(contentBytes, contentType);
        }
    }
}
