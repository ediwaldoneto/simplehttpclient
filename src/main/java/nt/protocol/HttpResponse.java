package main.java.nt.protocol;

import java.util.Map;

public class HttpResponse {

    private final int statusCode;
    private final Map<String, String> headers;
    private final HttpEntity entity;

    public HttpResponse(HttpEntity entity, int statusCode, Map<String, String> headers) {
        this.entity = entity;
        this.statusCode = statusCode;
        this.headers = headers;
    }

    public HttpEntity getEntity() {
        return entity;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getBodyAsString() {
        if (entity != null) {
            return new String(entity.getContent());
        }
        return null;
    }

    public byte[] getBodyAsBytes() {
        if (entity != null) {
            return entity.getContent();
        }
        return null;
    }
}
