# KCDC 2023 - Spring Workshop

*Notes in addition to those provided by the speakers*  

Speakers: Dan Vega and DeShaun Carter  

<https://github.com/KCDC-2023-Spring-Workshop>  

> Office hours - talk to Spring devs about issues
> Dan has blog and YouTube channel

Spring has JPA and JDBC  

Spring Shell can be used to write commandline programs that compile down to native instead of needing to run in a JVM  

"Make jar not war" -> jar files ship with server bundled with it while war files ship TO server  

**Look into spring.io and spring.academy**  

Command to start up Spring app in terminal: `./mvnw spring-boot:run`  

JDK comes with `jps` command that shows process PIDs for easy killing.  

`SERVER_PORT=8081 ./mvnw spring-boot:run` to start it up on a different port.  

Once deployed you can hit `http://localhost:8080/actuator/health` and it'll show JSON output.  

Don't add `@Authowired` to dependency declaration. Do it on the constructor instead.  

You don't have to use the @Autowired annotation - it's happening under the hood.  

Jackson in the Java world is a way to serialize/deserialize JSON to Java.  

## Bootstrapping data

To bootstrap the app with some data you can use the CommandLineRunner in the main application class.

```java
@Bean
CommandLineRunner commandLineRunner() {
    return args -> System.out.println("hello from the command line runner!')
}
```

> :star: Look into ListCrudRepository vs CrusRepository

There's no need to add `@Repository` to our interfaces which extend a repository class. It does nothing.  

> Spring has an image from `./mvnw spring-boot:build-image` -> **don't use custom Dockerfile**  

`./mvnw -Pnative spring-boot:build-image` to make a native executable with Spring Shell  


arm64 is much cheaper than x86. Java apps will work on either.  

> :star: Look into arm64 deployments

Cloudflare tunnel to access home computer without exposing ports
