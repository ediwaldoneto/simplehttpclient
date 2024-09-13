package main.java.nt.protocol;


import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.requireNonNull;

public class SimpleHttpRequestBuilderImpl implements SimpleHttpRequest.Builder {

    private URI uri;
    private HttpMethod method;
    private HttpEntity body;
    private Map<String, List<String>> headers;


    public SimpleHttpRequestBuilderImpl(URI uri) {
        requireNonNull(uri, "uri cannot be null");
        this.uri = uri;
        this.method = HttpMethod.GET;
    }


    public SimpleHttpRequestBuilderImpl() {
        this.method = HttpMethod.GET;
        this.headers = new HashMap<>();
    }

    @Override
    public SimpleHttpRequest.Builder uri(URI uri) {
        requireNonNull(uri, "uri cannot be null");
        this.uri = uri;
        return this;
    }

    @Override
    public SimpleHttpRequest.Builder GET() {
        this.method = HttpMethod.GET;
        return this;
    }

    @Override
    public SimpleHttpRequest.Builder POST(HttpEntity entity) {
        this.method = HttpMethod.POST;
        this.body = entity;
        return this;
    }

    @Override
    public SimpleHttpRequest.Builder DELETE() {
        this.method = HttpMethod.DELETE;
        return this;
    }

    @Override
    public SimpleHttpRequest.Builder PUT(HttpEntity entity) {
        this.method = HttpMethod.PUT;
        this.body = entity;
        return this;
    }

    @Override
    public SimpleHttpRequest.Builder header(String key, String value) {
        headers.computeIfAbsent(key, k -> new java.util.ArrayList<>()).add(value);
        return this;
    }

    @Override
    public SimpleHttpRequest.Builder setHeader(String name, String value) {
        headers.put(name, List.of(value));
        return this;
    }

    @Override
    public SimpleHttpRequest build() {
        if (uri == null) {
            throw new IllegalArgumentException("uri not provided");
        }
        return new SimpleHttpRequestImpl(body, headers, method, uri);
    }

}
