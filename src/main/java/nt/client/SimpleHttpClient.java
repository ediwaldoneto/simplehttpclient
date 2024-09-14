package main.java.nt.client;

import main.java.nt.protocol.HttpResponse;
import main.java.nt.protocol.SimpleHttpRequest;

import java.io.IOException;

public abstract class SimpleHttpClient {


    protected SimpleHttpClient() {
    }

    public abstract HttpResponse send(SimpleHttpRequest request) throws IOException;


    public interface Builder {

        public SimpleHttpClient build();


        public static SimpleHttpClient.Builder newBuilder() {
            return new SimpleHttpClientBuilderImpl();
        }
    }
}
