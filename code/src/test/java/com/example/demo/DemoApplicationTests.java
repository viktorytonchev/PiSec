package com.example.demo;


import org.junit.Test;

public class DemoApplicationTests {

	@Test
	public void testHelloWorld(){
		Thread app = new Thread(new DemoApplication());

		app.start();
	}
}
