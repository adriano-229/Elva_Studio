package com.tpa.jUnit;

public class Calculadora {

	public Integer sumar(Integer a,Integer b) {
		if(a==null) {
			a = 0;
		}
		
		if(b==null) {
			b = 0;
		}
		
		return a+b;
	}
	
	public Double dividir(Double numerador,Double denominador) throws Exception {
		if(denominador==null || denominador == 0) {
			throw new Exception("denominador invalido");
			
		}
		
		Double i = numerador / denominador;
		return i;
	}
	
	
	/*  NO SE HACE:
	 public static void main(String[] arg){
	 	Calculadora cal = new Calculadora();
		cal.sumar(1,2);}*/
}
