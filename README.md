# JavaHoneyPot
A Simple Spring-based Java *honey pot* web server which intercepts *GET* requests and responds with a text stream of 
messages using *chunked transfer encoding*. The idea behind the honey pot is to lure any malicious bots which could 
be scraping the site for vulnerabilities. Once the request is established - the response starts with an appropriate 
header and in the body sends infinite UTF-8 encoded text as *chunks* with a delay `MS_DELAY` 
between each chunk - effectively wasting the resources of the connected bot.

Each text chunk is encoded as follows in the response:
>{*length in bytes of data in hex*}\r\n{*data*}\r\n

This repeats infinitely but could be modified to terminate the connection by sending a 0:
>0\r\n\r\n

The program uses the Java *Spring Boot* framework and was tested using Java *JDK 17* environment and *Tomcat* as the embedded
application server. Clients were tested using *curl*, and *Chrome* web browser. The web server uses a *RestController* `HoneyPotController` to 
handle incoming client GET requests. Limitations of the program depend on host server hardware and Tomcat embedded application server. 

### Clone the repository
```
> git clone https://github.com/SethGHall/JavaHoneyPot.git
```

### Build and run project using maven:
Inside the project root folder: From the terminal build the jar using *Maven* (making sure *Maven* is installed and
is inside the *Path*).
```
> mvn package
```
This creates a *JavaHoneyPot-0.0.1-SNAPSHOT.jar* file inside the *target* folder. To run the application from the
terminal simply issue the following command from inside the *target* folder.

```
> java -jar .\JavaHoneyPot-0.0.1-SNAPSHOT.jar
```


### Running with Docker:
To run the program from within Docker navigate to the root of the project which contains the supplied *DockerFile*. 
This sets the virtual java environment to OPEN-JDK 17 and builds the recipe for the image.

To build the Docker image with the tag called *java-honeypot*.
```
> docker build -t java-honeypot .
```

To run the container:
```
> docker run -it -p 8080:8080 java-honeypot 
```
The flag `-it` is set for interactive mode (otherwise use `-d` for detached mode) and port `-p` which maps a container 
port to a port on the Docker host. By default this is set to `8080`.


### Client side testing
To test the program as a client, use a browser (tested only with chrome) or curl.
> http://localhost:8080/