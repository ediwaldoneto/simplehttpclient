package main.java.nt.protocol;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

public class FileEntity implements HttpEntity {

    private final File file;
    private final String contentType;

    public FileEntity(File file, String contentType) {
        this.file = file;
        this.contentType = contentType != null ? contentType : "application/octet-stream";
    }

    @Override
    public byte[] getContent() {
        return null;
    }

    @Override
    public long getContentLength() {
        return file.length();
    }

    @Override
    public void writeTo(OutputStream outStream) throws Exception {
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }
        }
    }

    @Override
    public String getContentType() {
        return contentType;
    }
}
