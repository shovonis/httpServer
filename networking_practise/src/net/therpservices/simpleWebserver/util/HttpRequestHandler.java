package net.therpservices.simpleWebserver.util;

import java.io.*;

/**
 * Created by rifatul on 5/7/14.
 */
public class HttpRequestHandler {
    private int length;
    private String[] requestHeader;
    private StringBuilder httpReqBuffer;
    private StringBuilder body;
    private BufferedReader inputStream = null;
    private FileInputStream fileInputStream = null;


    public HttpRequestHandler(InputStream socketInputStream) throws IOException {
        inputStream = new BufferedReader(new InputStreamReader(socketInputStream));
        httpReqBuffer = new StringBuilder();
        body = new StringBuilder();
        requestHeader = new String[0];
    }


    public void isRequestValid() throws IOException {
        String requestLine = inputStream.readLine();
        if (requestLine.startsWith("GET") || requestLine.startsWith("POST")) {
            requestHeader = requestLine.split(" ");
            httpReqBuffer.append(requestLine).append("\n");
            parseRequestFromSocket();

        } else {
            throw new IOException();
        }
    }

    private void parseRequestFromSocket() throws IOException {
        String httpRequest;
        while ((httpRequest = inputStream.readLine()) != null) {

            if (httpRequest.equals("")) {
                break;
            }
            if (httpRequest.startsWith("Content-Length: ")) {
                int index = httpRequest.indexOf(':') + 1;
                String len = httpRequest.substring(index).trim();
                length = Integer.parseInt(len);
            }
            httpReqBuffer.append(httpRequest).append("\n");
        }
    }

    public String getRequestHeader() throws IOException {
        return httpReqBuffer.toString();

    }

    public String getRequestType() throws IOException {
        return requestHeader[0];
    }

    public String getHttpRequest() throws IOException {
        String resourcePath = requestHeader[1];
        resourcePath = resourcePath.replaceFirst("/", "");
        if (resourcePath.equals("web/") || resourcePath.equals("web")) {
            resourcePath = "web/index.html";
        }
        if (resourcePath.equals("")) {
            throw new IOException();
        }
        return resourcePath;
    }

    public void closeStream() throws IOException {
        if (fileInputStream != null) {
            fileInputStream.close();
            inputStream.close();
        }
    }

    public String getPostData() throws IOException {
        if (length > 0) {
            int read;
            while ((read = inputStream.read()) != -1) {
                body.append((char) read);
                if (body.length() == length)
                    break;
            }
        }
        return body.toString();
    }
}
