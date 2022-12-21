package seth.honeypot.JavaHoneyPot.contoller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.nio.charset.Charset;
import java.util.Map;

@RestController
public class HoneyPotController {
    @GetMapping(value="/")
    public ResponseEntity<StreamingResponseBody> honeyPot()
    {
        String line = "HelloWorld\n";
        StreamingResponseBody body = response ->{
            try{
                for (int i = 0; i < 20; i++) {
                    //byte[] data = (Integer.toHexString(line.length())+"\r\n"+line+"\r\n").getBytes("UTF-8");
                    byte[] data = (line).getBytes("UTF-8");
                    System.out.println(Integer.toHexString(line.length()) + " "+line);
                    response.write(data);
                    response.flush();
                    Thread.sleep(500);
                }
                response.close();
            }catch(Exception exception)
            {   System.out.printf("An error occurred during transmission "+exception);
            }
        };
        HttpHeaders headers = new HttpHeaders();
        //As we are using a "StreamingResponse" as the Type of ResponseEntity - Chunked automatically set.
        //Each write command automatically includes the chunk length and new line.
        headers.set(HttpHeaders.CONTENT_TYPE,MediaType.TEXT_EVENT_STREAM_VALUE);
        ResponseEntity<StreamingResponseBody> responseEntity = new ResponseEntity<>(body, headers, 200);
        return responseEntity;
    }
}
