package net.therpservices.simpleWebserver.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created with IntelliJ IDEA.
 * User: rifatul.islam
 * Date: 5/8/14
 * Time: 10:20 AM
 * To change this template use File | Settings | File Templates.
 */
public class HttpResponseHandler {

    private FileInputStream fileInputStream = null;
    private OutputStream outputStream = null;

    public HttpResponseHandler(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void writeToSocketStream(String status, byte[] message) throws IOException {
        outputStream.flush();
        addResponseHeader(status);
        outputStream.write(message);
    }

    public void writeFileToOutputStream(String fileName, String status) throws IOException {
        File file = new File(fileName);
        byte[] bFile = new byte[(int) file.length()];
        fileInputStream = new FileInputStream(file);
        fileInputStream.read(bFile);

        outputStream.flush();
        addResponseHeader(status);
        outputStream.write(bFile, 0, bFile.length);
    }

    public void addResponseHeader(String status) throws IOException {
        outputStream.write(status.getBytes());
    }

    public void closeStream() throws IOException {
        if (fileInputStream != null) {
            fileInputStream.close();
            outputStream.close();
        }
    }
}
