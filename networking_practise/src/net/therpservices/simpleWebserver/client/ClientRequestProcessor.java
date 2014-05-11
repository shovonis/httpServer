package net.therpservices.simpleWebserver.client;

import net.therpservices.simpleWebserver.util.HttpRequestHandler;
import net.therpservices.simpleWebserver.util.HttpResponseHandler;
import net.therpservices.simpleWebserver.util.StatusCode;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;

/**
 * Created with IntelliJ IDEA.
 * User: rifatul.islam
 * Date: 5/6/14
 * Time: 10:47 AM
 * To change this template use File | Settings | File Templates.
 */
public class ClientRequestProcessor implements Runnable {
    HttpRequestHandler httpRequestHandler = null;
    HttpResponseHandler httpResponseHandler = null;
    private Socket clientSocket;

    public ClientRequestProcessor(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;

    }

    @Override
    public void run() {
        try {
            httpResponseHandler = new HttpResponseHandler(clientSocket.getOutputStream());
            httpRequestHandler = new HttpRequestHandler(clientSocket.getInputStream());
            httpRequestHandler.isRequestValid();

            System.out.println(httpRequestHandler.getRequestHeader());
            String httpRequest = httpRequestHandler.getHttpRequest();
            String requestType = httpRequestHandler.getRequestType();

            if (requestType.equals("POST")) {
                System.out.println(httpRequestHandler.getPostData());
            }
            //System.out.println("httpRequest = " + httpRequest);
            if (!httpRequest.isEmpty())
                httpResponseHandler.writeFileToOutputStream(httpRequest, StatusCode.OK);

        } catch (FileNotFoundException ex) {
            try {
                httpResponseHandler.writeToSocketStream(StatusCode.FILE_NOT_FOUND, StatusCode.getFileNotFoundErrMsg());

            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (IOException e) {
            try {
                httpResponseHandler.writeToSocketStream(StatusCode.BAD_REQUEST, StatusCode.getBadRequestErrMsg());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (httpResponseHandler != null) {
                    httpResponseHandler.closeStream();
                    httpRequestHandler.closeStream();
                    clientSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
