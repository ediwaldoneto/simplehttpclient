package main.java.nt.client;

public class SimpleHttpClientBuilderImpl implements SimpleHttpClient.Builder {

    public SimpleHttpClientBuilderImpl() {
        // TODO document why this constructor is empty
    }


    @Override
    public SimpleHttpClient build() {
        return new SimpleHttpClientImpl();
    }
}
