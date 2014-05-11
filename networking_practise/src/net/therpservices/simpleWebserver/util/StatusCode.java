package net.therpservices.simpleWebserver.util;

/**
 * Created by rifatul on 5/7/14.
 */
public class StatusCode {
    public static final String OK = "HTTP/1.1 200 OK\n\n";
    public static final String FILE_NOT_FOUND = "HTTP/1.1 404  Not Found\n\n";
    public static final String BAD_REQUEST = "HTTP/1.1 400  Bad Request\n\n";
    public static final String ACCESS_DENIED = "HTTP/1.1 403  Access denied\n\n";


    public static byte[] getFileNotFoundErrMsg() {
        return "<html><h1>404 FILE NOT FOUND</h1></html>".getBytes();
    }

    public static byte[] getBadRequestErrMsg() {
        return "<html><h1>400 Bad Request</h1></html>".getBytes();
    }

    public static byte[] getAccessDeniedErrMsg() {
        return "<html><h1>403 Access Denied</h1></html>".getBytes();
    }
}
