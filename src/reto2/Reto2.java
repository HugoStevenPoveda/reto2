package reto2;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/*
* Recomendaciones Generales:
*
*    -> El método run() funcionará como nuestro método principal
*    -> No declarar objetos de tipo Scanner, utilizar el método read() para solicitar datos al usuario.
*    -> Si requiere utilizar varias clases, estas NO deben ser tipo public.
*/
class Reto2 {

	/**
	 * Este debe ser el único objeto de tipo Scanner en el código
	 */
	private final Scanner scanner = new Scanner(System.in);

	/**
	 * Este método es usado para solicitar datos al usuario
	 * 
	 * @return Lectura de la siguiente linea del teclado
	 */
	public String read() {
		return this.scanner.nextLine();
	}

	/**
	 * método principal
	 */
	public void run() {
		/*
		 * solución propuesta
		 */
		// construcion Base datos
		Productos manzanas = new Productos(1, "Manzanas", 8000.0, 65);
		Productos limones = new Productos(2, "Limones", 2300.0, 15);
		Productos granadilla = new Productos(3, "Granadilla", 2500.0, 38);
		Productos arandanos = new Productos(4, "Arandanos", 9300.0, 55);
		Productos tomates = new Productos(5, "Tomates", 2100.0, 42);
		Productos fresas = new Productos(6, "Fresas", 4100.0, 3);
		Productos helado = new Productos(7, "Helado", 4500.0, 41);
		Productos galletas = new Productos(8, "Galletas", 500.0, 8);
		Productos chocolates = new Productos(9, "Chocolates", 3500.0, 806);
		Productos jamon = new Productos(10, "Jamon", 15000.0, 10);

		BaseDatosProductos baseDatosProductos = new BaseDatosProductos();
		baseDatosProductos.agregar(manzanas);
		baseDatosProductos.agregar(limones);
		baseDatosProductos.agregar(granadilla);
		baseDatosProductos.agregar(arandanos);
		baseDatosProductos.agregar(tomates);
		baseDatosProductos.agregar(fresas);
		baseDatosProductos.agregar(helado);
		baseDatosProductos.agregar(galletas);
		baseDatosProductos.agregar(chocolates);
		baseDatosProductos.agregar(jamon);

		// fin de base datos

		String operacion = read();
		String datosProducto = read();
		String[] elementosProductos = datosProducto.split(" ");
		Integer codigoProducto = Integer.parseInt(elementosProductos[0]);
		String nombreProducto = elementosProductos[1];
		Double precioProducto = Double.parseDouble(elementosProductos[2]);
		Integer inventarioProducto = Integer.parseInt(elementosProductos[3]);
		Productos producto = new Productos(codigoProducto, nombreProducto, precioProducto, inventarioProducto);
		Boolean existenciaProducto = baseDatosProductos.verificarExistencia(producto);

		switch (operacion) {
		case "ACTUALIZAR":
			if (Boolean.TRUE.equals(existenciaProducto)) {
				baseDatosProductos.actualizar(producto);
				baseDatosProductos.generarInforme();
			}

			else {
				System.out.println("ERROR");
			}

			break;
		case "BORRAR":
			if (existenciaProducto) {
				baseDatosProductos.eliminar(producto);
				baseDatosProductos.generarInforme();
			} else {
				System.out.println("ERROR");
			}

			break;
		case "AGREGAR":
			if (!existenciaProducto) {

				baseDatosProductos.agregar(producto);
				baseDatosProductos.generarInforme();
			} else {
				System.out.println("ERROR");
			}

			break;
		default:
			System.out.println("ERROR Falta operacion");
			break;

		}

	}
}

class Productos {
	Integer codigo;
	String nombre;
	Double precio;
	Integer inventario;

	public Productos(Integer codigo, String nombre, Double precio, Integer inventario) {
		this.codigo = codigo;
		this.nombre = nombre;
		this.precio = precio;
		this.inventario = inventario;

	}

	public Integer mostrarId() {
		return codigo;

	}

	public Double mostrarPrecio() {
		return precio;
	}

	public Integer mostrarInventario() {
		return inventario;
	}

	@Override
	public String toString() {
		return nombre;
	}
}

class BaseDatosProductos {
	// agregar, actualizar y eliminar

	Map<Integer, Productos> listaProductos = new HashMap<Integer, Productos>();
	Map<Integer, Double> listaPrecios = new HashMap<Integer, Double>();

	public void crearListaPrecio() {
		for (Integer codigo : listaProductos.keySet()) {
			listaPrecios.put(codigo, listaProductos.get(codigo).mostrarPrecio());
		}
	}

	public Boolean verificarExistencia(Productos producto) {

		if (listaProductos.containsKey(producto.mostrarId())) {
			return true;
		} else {
			return false;
		}

	}

	public void agregar(Productos producto) {

		listaProductos.put(producto.mostrarId(), producto);

	}

	public void actualizar(Productos producto) {

		listaProductos.replace(producto.mostrarId(), producto);

	}

	public void eliminar(Productos producto) {

		listaProductos.remove(producto.mostrarId());

	}

	public Integer[] mayorMenor() {
		crearListaPrecio();
		// objeto me permite tener un aaray a agregarle un valor a mayor y menor
		Object[] preciosLista = listaPrecios.values().toArray();

		// almacena la llave producto mayor y la llave producto menor
		// primer llave mayor luego llave menor
		Integer[] keysMayorMenor = new Integer[2];

		Double mayor;
		Double menor;
		Integer keyMayor = 0;
		Integer keyMenor = 0;
		menor = (Double) preciosLista[0];
		mayor = (Double) preciosLista[0];

		for (Double precio : listaPrecios.values()) {
			if (precio > mayor) {

				mayor = precio;
			}
			if (precio < menor) {

				menor = precio;
			}
		}
		for (Integer key : listaPrecios.keySet()) {
			if (listaPrecios.get(key).equals(menor)) {

				keyMenor = key;
			}
			if (listaPrecios.get(key).equals(mayor)) {

				keyMayor = key;
			}
		}

		keysMayorMenor[0] = keyMayor;
		keysMayorMenor[1] = keyMenor;

		return keysMayorMenor;

	}

	public void imprimeMayorMenor() {
		Integer[] mayorMenor = mayorMenor();
		System.out.print(listaProductos.get(mayorMenor[0]));
		System.out.print(" ");
		System.out.print(listaProductos.get(mayorMenor[1]));

	}

	public Double promedioPrecio() {

		Double suma = (double) 0;
		Double promedio = (double) 0;
		int cantidaProductos = listaProductos.size();
		for (int key : listaProductos.keySet()) {
			suma += listaProductos.get(key).mostrarPrecio();
		}
		promedio = suma / cantidaProductos;
		// permite mostrar con solo un decimal
		String resultado = String.format("%.1f", promedio);
		resultado = resultado.replace(",", ".");
		promedio = Double.parseDouble(resultado);
		return promedio;
	}

	public void imprimirPromedio() {
		System.out.print(promedioPrecio());

	}

	public Double totalInventario() {
		Double suma = (double) 0;
		Double precioProducto = (double) 0;
		Integer inventarioProducto = 0;
		Double producto = (double) 0;

		for (int key : listaProductos.keySet()) {
			precioProducto = listaProductos.get(key).mostrarPrecio();
			inventarioProducto = listaProductos.get(key).mostrarInventario();
			producto = precioProducto * inventarioProducto;
			suma += producto;
		}
		String resultado = String.format("%.1f", suma);
		resultado = resultado.replace(",", ".");
		suma = Double.parseDouble(resultado);

		return suma;
	}

	public void imprimirTotalInventario() {
		System.out.print(totalInventario());
	}

	public void generarInforme() {
		imprimeMayorMenor();
		System.out.print(" ");
		imprimirPromedio();
		System.out.print(" ");
		imprimirTotalInventario();

	}

}
