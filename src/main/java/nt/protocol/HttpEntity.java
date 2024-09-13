package main.java.nt.protocol;

import java.io.OutputStream;

public interface HttpEntity {

    byte[] getContent();

    long getContentLength();

    void writeTo(OutputStream outStream) throws Exception;

    String getContentType();
}
