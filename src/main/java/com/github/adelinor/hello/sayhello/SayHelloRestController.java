package com.github.adelinor.hello.sayhello;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.Attributes;
import java.util.jar.Attributes.Name;
import java.util.jar.Manifest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SayHelloRestController {

	@GetMapping(value = "/sayhello")
	public ResponseEntity<String> sayHello() {
		return ResponseEntity.ok().body("Hello from " + getRelease());
	}
	
	private String getRelease() {
		Manifest manifest;
		try {
			manifest = readManifest("spring-boot-jib-hello");

		} catch (IOException exc) {
			return ":( Problem: " + exc.getMessage();
		}
		
		Attributes attributes = manifest.getMainAttributes();
		return attributes.getValue(Name.IMPLEMENTATION_VERSION)
				+ " " +  attributes.getValue("Build-Time");					
	}
		
	private Manifest readManifest(String titleToMatch) throws IOException {
		Enumeration<URL> resources = getClass().getClassLoader()
					.getResources("META-INF/MANIFEST.MF");

		while (resources.hasMoreElements()) {
			URL url = resources.nextElement();
			try (InputStream inputStream = url.openStream();) {
				if (inputStream == null) {
					throw new FileNotFoundException("Could not read content "
							+ "of " + url);
				}
				Manifest manifest = new Manifest(inputStream);
				String title = manifest.getMainAttributes()
						.getValue(Name.IMPLEMENTATION_TITLE);
				if (titleToMatch.equals(title)) {
					return manifest;
				}
			}
		}
		return null;
	}
}
