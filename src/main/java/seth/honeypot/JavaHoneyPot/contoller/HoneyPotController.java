/*
 * Honey Pot Web Server - A Spring basic implementation which intercepts all GET requests and
 * sends an infinite stream of text data to an HTTP client program or browser. The port,
 * hostname and seconds between each line of text data can be overridden in the config.ini

 * Author: Seth Hall
 * Date: 28 December 2022
 */

package seth.honeypot.JavaHoneyPot.contoller;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.catalina.connector.ClientAbortException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

//Rest controller to intercept any get requests - connected client is handled by this class in which they are
//then sent an "infinite" stream of text in "chunked" transfer mode
@RestController
public class HoneyPotController {
    private final String[] responses = {"...Sorry Not Found...", "Or maybe it is found?...", "Keep waiting and maybe it will come...",
            "Any second now..", "This is SO NOT a honeypot...", "Alright here it is...",
            "Almost there...", "Almost...", "keep waiting...", "loading now...",
            "Such juicy content is on its way...", "you are being so patient...",
            "Next line, I promise it will be here...","here it is!!!..."};
    private final int MS_DELAY = 1000;

    @GetMapping(value="/")
    public ResponseEntity<StreamingResponseBody> honeyPot()
    {
        //Formulate a Streaming response body to stream text with a delay between each line
        //As we are using a "StreamingResponse" as the Type of ResponseEntity - Chunked transfer mode is
        //automatically set. Each write command automatically includes the chunk length and the \r\n terminators.
        StreamingResponseBody body = response -> {
            try {
                int lineNumber = 0;
                while (true) {
                    byte[] data = (responses[lineNumber] + "\n").getBytes("UTF-8");
                    response.write(data);
                    response.flush();
                    try {
                        Thread.sleep(MS_DELAY);
                    } catch (InterruptedException e) {
                    }
                    lineNumber = (lineNumber + 1) % responses.length;
                }
                //unreachable close - just left here for testing while loop with finite values
                //response.close();
            } catch (ClientAbortException exception) {
                System.out.println("The connected client finally gave up ");
            }
        };
        //create header for the response, set response entity with header and streaming response body
        HttpHeaders header = new HttpHeaders();
        header.set(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_EVENT_STREAM_VALUE);
        ResponseEntity<StreamingResponseBody> responseEntity = new ResponseEntity<>(body, header, 200);
        return responseEntity;
    }
}
