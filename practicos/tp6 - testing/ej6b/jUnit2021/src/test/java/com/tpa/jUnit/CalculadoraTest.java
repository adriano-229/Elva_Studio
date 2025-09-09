package com.tpa.jUnit;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.RepeatedTest;

import static org.hamcrest.CoreMatchers.is;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.test.annotation.Repeat;


@TestMethodOrder(OrderAnnotation.class)
public class CalculadoraTest {
	
	Calculadora calc;
	
	@BeforeAll
	public static void BeforeAll() {
		System.out.println("beforeAll");
	}
	
	@AfterAll
	public static void AfterAll() {
		System.out.println("afterAll");
		
	}
	
	@AfterEach
	public void AfterEach(TestInfo testInfo) {
		System.out.println("finaliza " + testInfo.getDisplayName());
	}
	
	@BeforeEach
	public void BeforeEach(TestInfo testInfo) {
		calc = new Calculadora();
		System.out.println("inicia " + testInfo.getDisplayName());
	}
	
	
	
	@Test
	@Order(1)
	public void testSumar() {
		int a=2, b=3, c=5, resultado=0;
				
		resultado = calc.sumar(a, b);
		
		System.out.println("resultado " + resultado);
		
	}
	
	@Test
	@Order(2)
	public void testDividir() {
		Double a=2.0, b=3.0, esperado= 0.6666666666666666, resultado=0.0;
				
		try {
			resultado = calc.dividir(a, b);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		Assertions.assertEquals(esperado,resultado);
				
		System.out.println("resultado " + resultado);
		
	}
	
	@Disabled
	@Test
	@Order(3)
	public void testDividirAssertTrue() {
		Double a=2.0, b=0.0, esperado= 0.6666666666666666, resultado=0.0;
				
		try {
			resultado = calc.dividir(a, b);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Assertions.assertFalse(resultado == esperado);
		
		System.out.println("resultado " + resultado);
		
	}
	
	/*@Test cuando usamos repeat, sacamos test pq sino agrega una ejecucion extra*/
	@RepeatedTest(5)
	public void testDividirCheckException() {
		Double a=2.0, b=0.0, esperado= 0.6666666666666666, resultado=0.0;
				
		try {
			Exception exception = assertThrows(Exception.class, () -> {
				calc.dividir(a, b);
			});
			
			assertEquals("denominador invalido", exception.getMessage());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Assertions.assertFalse(resultado == esperado);
		
		System.out.println("resultado " + resultado);
		
	}
		
}
