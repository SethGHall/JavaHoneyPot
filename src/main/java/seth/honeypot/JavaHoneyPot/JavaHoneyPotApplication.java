/*
 * Honey Pot Web Server - A Spring basic implementation which intercepts all GET requests and
 * sends an infinite stream of text data to an HTTP client program or browser. The port,
 * hostname and seconds between each line of text data can be overridden in the config.ini

 * Author: Seth Hall
 * Date: 28 December 2022
*/

package seth.honeypot.JavaHoneyPot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//Main entry point to honey pot web application
@SpringBootApplication
public class JavaHoneyPotApplication {
	public static void main(String[] args) {
		SpringApplication.run(JavaHoneyPotApplication.class, args);
	}
}
