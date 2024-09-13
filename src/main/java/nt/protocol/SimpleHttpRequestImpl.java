package main.java.nt.protocol;

import java.net.URI;
import java.net.http.HttpHeaders;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SimpleHttpRequestImpl extends SimpleHttpRequest {

    private final URI uri;
    private final HttpMethod httpMethod;
    private final Map<String, List<String>> headers;
    private final HttpEntity body;

    public SimpleHttpRequestImpl(HttpEntity body, Map<String, List<String>> headers, HttpMethod httpMethod, URI uri) {
        this.body = body;
        this.headers = headers;
        this.httpMethod = httpMethod;
        this.uri = uri;
    }

    @Override
    public HttpMethod httpMethod() {
        return httpMethod;
    }

    @Override
    public URI uri() {
        return uri;
    }

    @Override
    public HttpHeaders headers() {
        return HttpHeaders.of(headers, (k, v) -> true);
    }

    @Override
    public Optional<HttpEntity> body() {
        return Optional.ofNullable(body);
    }
}
