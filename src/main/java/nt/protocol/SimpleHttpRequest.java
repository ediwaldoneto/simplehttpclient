package main.java.nt.protocol;

import java.net.URI;
import java.net.http.HttpHeaders;
import java.util.Optional;

public abstract class SimpleHttpRequest {

    protected SimpleHttpRequest() {
    }

    public abstract HttpMethod httpMethod();

    public abstract URI uri();

    public abstract HttpHeaders headers();

    public abstract Optional<HttpEntity> body();


    public interface Builder {

        public Builder uri(URI uri);

        public Builder GET();

        public Builder POST(HttpEntity entity);

        public Builder DELETE();

        public Builder PUT(HttpEntity entity);

        public Builder header(String key, String value);

        public Builder setHeader(String name, String value);

        public SimpleHttpRequest build();


        public static SimpleHttpRequest.Builder newBuilder() {
            return new SimpleHttpRequestBuilderImpl();
        }
    }
}
