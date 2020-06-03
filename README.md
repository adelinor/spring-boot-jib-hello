Spring Boot Jib Hello web app
=============================

### How this app was created
1) This app is generated with Spring Initialzr with the added dependency `Spring Reactive Web`.

2) Configure jar-maven-plugin to set Build date and Implementation version

3) Add `/sayhello` service that greets you with the version and build time taken from MANIFEST.MF . This class iterates through available MANIFEST.MF files as suggested
in this [stackoverflow post](https://stackoverflow.com/a/1273196/2371545) and uses the
title to find the correct file.

The implementation is tested manually by packaging the app:

```sh
mvn clean package -Dbuild.version=1.2.3
```

... running it with

```sh
java -jar target/spring-boot-jib-hello-0.0.1-SNAPSHOT.jar
```

... and finally accessing it with http://localhost:8080/sayhello . It shows something like
`Hello from 1.2.3 2020-06-03T09:25:58Z`

4) Add Jib configuration for creating an image that runs the packaged JAR
Creating an image is done for instance with:

```sh
mvn clean package jib:dockerBuild -Dbuild.version=1.2.3
```

Running the created image with

```sh
docker run -d -p 8080:8080 spring-boot-jib-hello:1.2.3
```

... should bring the text `Hello from 1.2.3 2020-06-03T12:22:44Z` when accessing http://localhost:8080/sayhello.

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.3.0.RELEASE/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.3.0.RELEASE/maven-plugin/reference/html/#build-image)
* [Jib - Containerize your Java application](https://github.com/GoogleContainerTools/jib#jib)

