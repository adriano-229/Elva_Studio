package testconjunit.services;

import java.awt.geom.Area;

import testconjunit.entities.Rectangulo;

public class RectanguloService {

	public RectanguloService() {
		
	}
	
	public double calcularArea(Rectangulo r) {
		return r.getAncho() * r.getLargo();
		
	}
	
	public double calcularPerimetro(Rectangulo r) {
		return  2 * (r.getAncho() + r.getLargo());
		
	}
}
