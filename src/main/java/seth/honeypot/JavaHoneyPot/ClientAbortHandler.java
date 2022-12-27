/*
 * Honey Pot Web Server - A Spring basic implementation which intercepts all GET requests and
 * sends an infinite stream of text data to an HTTP client program or browser. The port,
 * hostname and seconds between each line of text data can be overridden in the config.ini

 * Author: Seth Hall
 * Date: 28 December 2022
 */
package seth.honeypot.JavaHoneyPot;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.connector.ClientAbortException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.net.http.HttpResponse;

//Controller advice to handle exceptions generated when client aborts the connection.
@ControllerAdvice
public class ClientAbortHandler extends ResponseEntityExceptionHandler{
    @ExceptionHandler(ClientAbortException.class)
    public void handleClientAbort() {
        System.out.println("A connected client has finally aborted - handled  by controller advice");
    }
}
