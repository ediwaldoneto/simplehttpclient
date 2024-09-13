package main.java.nt.protocol;

import java.io.OutputStream;

public class ByteArrayEntity implements HttpEntity {

    private final byte[] content;
    private final String contentType;

    public ByteArrayEntity(byte[] content, String contentType) {
        this.content = content;
        this.contentType = contentType != null ? contentType : "application/octet-stream";
    }

    @Override
    public byte[] getContent() {
        return content;
    }

    @Override
    public long getContentLength() {
        return content.length;
    }

    @Override
    public void writeTo(OutputStream outStream) throws Exception {
        outStream.write(content);
    }

    @Override
    public String getContentType() {
        return contentType;
    }

}
