package com.amarjeet;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;

public class HttpClientUseCase {
	public static void main(String[] args) throws Exception {
		// Step 1: Create HttpClient
		HttpClient client = HttpClient.newHttpClient();

		// Step 2: Build the Request
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://jsonplaceholder.typicode.com/posts"))
				.GET().build();

		// Step 3: Send the request and get the response
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

		// Step 4: Print the response body
		System.out.println("Response Code: " + response.statusCode());
		System.out.println("Response Body:");
		System.out.println(response.body());
	}
}
