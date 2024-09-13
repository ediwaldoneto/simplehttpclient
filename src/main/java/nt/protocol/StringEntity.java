package main.java.nt.protocol;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class StringEntity implements HttpEntity {

    private final String content;
    private final String contentType;

    public StringEntity(String content, String contentType) {
        this.content = content;
        this.contentType = contentType != null ? contentType : "text/plain";
    }

    @Override
    public byte[] getContent() {
        return content.getBytes();
    }

    @Override
    public long getContentLength() {
        return content.getBytes(StandardCharsets.UTF_8).length;
    }

    @Override
    public void writeTo(OutputStream outStream) throws Exception {
        outStream.write(content.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String getContentType() {
        return contentType;
    }
}
