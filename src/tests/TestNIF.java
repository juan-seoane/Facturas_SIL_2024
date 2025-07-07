package tests;

import org.junit.Test;

import modelo.records.NIF;

public class TestNIF {

	@Test
	public void dameLetraNIFok(){
		long num=44454841;

		String letra = NIF.dameLetraNIF(num);
		System.out.println("[testNIF] " + num + " - " + letra);
	}
}
