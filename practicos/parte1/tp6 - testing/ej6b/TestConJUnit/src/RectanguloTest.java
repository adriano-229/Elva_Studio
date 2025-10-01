/**
 * 
 */

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import testconjunit.entities.Rectangulo;
import testconjunit.services.RectanguloService;

/**
 * 
 */
class RectanguloTest {
	
	public RectanguloTest() {
		
	}
	
	RectanguloService rs;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		rs = new RectanguloService();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

		
	@Test
	public void deberiaInicializarConColor() {
		assertNotNull(new Rectangulo(10,10).getColor());
		
	}

	@Test
	public void deberiaCalcularArea() {
		//int esperado = 100;
		assertEquals(100, rs.calcularArea(new Rectangulo(10,10)), 0);
		assertEquals(20, rs.calcularArea(new Rectangulo(4,5)), 0);
		assertEquals(1, rs.calcularArea(new Rectangulo(1,1)), 0);
		
	}
	
	@Test
	public void deberiaCalcularPerimetro() {
		assertEquals(40, rs.calcularPerimetro(new Rectangulo(10,10)),0);
		assertEquals(100, rs.calcularPerimetro(new Rectangulo(20,30)),0);
		assertEquals(30, rs.calcularPerimetro(new Rectangulo(5,10)),0);
	}
	
	@Test
	public void deberiaActivarODesactivar() {
		Rectangulo r = new Rectangulo(5,5);
		assertTrue(r.isActive());
		r.setActive(false);
		assertFalse(r.isActive());
	}
}
