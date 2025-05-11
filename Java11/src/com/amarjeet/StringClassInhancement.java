package com.amarjeet;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class StringClassInhancement {
	public static void main(String[] args) throws IOException {
//		String str1="";
//	    System.out.println(str1.isEmpty()); //java6
//	    System.out.println(str1.isBlank()); //java11

//		String multiline = "Java\nPython\nC++";
//		multiline.lines().forEach(System.out::println); //java11

//		String str = "Amarjeet";
//		System.out.println(str.repeat(5));// java11
//		
//		Optional<String> optional=Optional.ofNullable(null);
//		System.out.println(optional.isEmpty()); //java11
//		System.out.println(optional.isPresent()); 

		// System.out.println(" Hello ".strip()); // "Hello"
		// System.out.println(" Hello ".stripLeading()); // "Hello "
		// System.out.println(" Hello ".stripTrailing()); // " Hello"

		// 1. Run Single-File Programs Without Compilation //java11
		// 2. You need to replace javax APIs with Jakarta EE or external dependencies

//		Path path = Path.of("sample.txt");
//		Files.writeString(path, "Hello, Java 11!");
//		String content = Files.readString(path);
//		System.out.println(content); // Hello, Java 11!

	}

}
