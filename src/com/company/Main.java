package com.company;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

	/* CORRECCIONES:
	Verificar la utilidad del boolean "enVuelo" de la clase Avion.
	Para comprobar si esta disponible en una fecha dada debería implementarse una busqueda
	en vuelosPactados y si existe devolver false. El atributo de clase enVuelo no serviría.
	 */

    public static void main(String[] args) {

    	// declaro bases de datos en memoria
		ArrayList<Usuario> baseClientes = new ArrayList<>();
		ArrayList<Avion> flotaAviones = new ArrayList<>();
		ArrayList<Vuelo> vuelosPactados = new ArrayList<>();

		// leo persistencias en archivos y cargo las bases en memoria
		File archivo = new File("baseDatos.dat");
		try {
			if (archivo.exists()) {
				FileInputStream flujoEntrada = new FileInputStream(archivo);
				ObjectInputStream entradaObjeto = new ObjectInputStream(flujoEntrada);

				boolean finArchivo = false;
				while (!finArchivo) {
					Object aux = entradaObjeto.readObject();
					if (aux != null) {
						if (aux instanceof Usuario)
							baseClientes.add((Usuario) aux);
						else if (aux instanceof Avion)
							flotaAviones.add((Avion) aux);
						else if (aux instanceof Vuelo)
							vuelosPactados.add((Vuelo) aux);
					} else
						finArchivo = true;
				}
			}
			else { // si no hay archivo, cargo datos de ejemplo
				flotaAviones.add(new Gold(10000, 30, Propulsion.REACCION, false, true, true));
				flotaAviones.add(new Gold(7000, 20, Propulsion.PISTONES, false, true, true));
				flotaAviones.add(new Silver(3000, 15, Propulsion.HELICE, false, true));
			}
		}catch(IOException | ClassNotFoundException e){
			System.out.println("No se puede leer la base de datos: " + e.getMessage());
			e.printStackTrace();
		}

		File file = new File("");
		ObjectMapper

		// Interfaz del usuario
		System.out.println("Sistema de Contratación de Vuelos << AERO-TAXI >>\n");

		Usuario usrLogin = Main.authLogin(baseClientes);

		if (usrLogin != null) {
			Scanner teclado = new Scanner(System.in);
			int opcionMenu;

			do {
				System.out.println("1- Contratar vuelo.\n" +
						"2- Cancelar vuelo.\n" +
						"3- Ver base de clientes.\n" +
						"4- Ver vuelos programados.\n" +
						"5- Salir");

				try {
					opcionMenu = teclado.nextInt();
				} catch (InputMismatchException mensaje) {
					System.out.println("El valor ingresado debe ser un numero");
					opcionMenu = 0;
				}

				switch (opcionMenu) {
					case 1:
						/* metodo contratar vuelo */
						break;
					case 2:
						/* metodo cancelar vuelo */
						break;
					case 3:
						Main.listarClientes(baseClientes);
						/* falta agregar:
							Todos los datos personales.
							La categoría del mejor avión utilizado (Gold, Silver o Bronze).
							Total gastado de todos sus vuelos.
						 */
						break;
					case 4:
						/* metodo ver vuelos programados */
						break;
					default:
						break;
				}
			}
			while (opcionMenu != 5);
		}
	}


	// funciones de la clase Main
    public static Usuario authLogin(ArrayList<Usuario> baseClientes){

			Scanner teclado = new Scanner(System.in);

			int dni = 0;
			boolean dniInvalido;

			do{
				try {
					System.out.println("Ingrese su DNI: ");
					dni = teclado.nextInt();
					dniInvalido = false;
				} catch (InputMismatchException e) {
					System.out.println("Ingrese un valor numerico valido.");
					dniInvalido = true;
				}
			}
			while(dniInvalido);

			Usuario unUsuario = null;
			boolean usrRegistrado = false;

			if(!baseClientes.isEmpty()) {
				for (int i = 0; i < baseClientes.size(); i++) {
					unUsuario = baseClientes.get(i);
					if (unUsuario != null && unUsuario.getDni() == dni) {
						usrRegistrado = true;
						break;
					}
				}
			}
				/*
				Aqui se puede implementar control por contraseña
				 */
			if(baseClientes.isEmpty() || !usrRegistrado){
				unUsuario = Main.nuevoUsuario();
				baseClientes.add(unUsuario);
			}

			return unUsuario;
		}

    public static Usuario nuevoUsuario(){

    	Scanner teclado = new Scanner(System.in);
    	String nombre, apellido;
    	int dni, edad;

    	while(true){
			try {
				System.out.println("Ingrese primer nombre: ");
				nombre = teclado.nextLine();
				System.out.println("Ingrese apellido: ");
				apellido = teclado.nextLine();
				System.out.println("Ingrese DNI: ");
				dni = teclado.nextInt();
				System.out.println("Ingrese edad: ");
				edad = teclado.nextInt();

				return new Usuario(nombre,apellido,dni,edad);

			} catch (InputMismatchException e) {
				System.out.println("Los datos ingresados son incorrectos: (" + e + ")");
				teclado.nextLine(); // flush del buffer
			}
		}
	}

	public static void contratarVuelo(){
    	Scanner teclado = new Scanner(System.in);
    	System.out.println("Ingrese fecha de partida: ");
    	String fecha = teclado.nextLine();
		System.out.println("Ingrese fecha de llegada: ");
		String llegada = teclado.nextLine();
		System.out.println("Seleccione origen:");
		System.out.println("1- Buenos aires \n" +
							"2- Cordoba \n"+
							"3- Montevideo \n" +
							"4- Santiago de chile \n");
		Scanner nuevo = new Scanner(System.in);
		int num = nuevo.nextInt();
		Ciudad origen;
		if (num == 1)
		{origen = Ciudad.BUE;}
		if (num == 2)
		{origen = Ciudad.COR;}
		if (num == 3)
		{origen = Ciudad.MVD;}
		if (num == 4)
		{origen = Ciudad.SCL;}
		int a = nuevo.nextInt();
		Ciudad destino;
		do {
			System.out.println("Seleccione destino diferente al origen");
			System.out.println("1- Buenos aires \n" +
					"2- Cordoba \n"+
					"3- Montevideo \n" +
					"4- Santiago de chile \n");
			if (num == 1)
			{destino = Ciudad.BUE;}
			if (num == 2)							//swich
			{destino = Ciudad.COR;}
			if (num == 3)
			{destino = Ciudad.MVD;}
			if (num == 4)
			{destino = Ciudad.SCL;}
		}while (origen == destino);
		Main.mostrarAvionesDisp(fecha);
		Avion reservado = new Avion();
		reservado = Main.reservar();
		Vuelo vuelonuevo = new Vuelo(origen, destino, reservado, llegada);
		float costo = vuelonuevo.calcularCosto();
		System.out.println("El costo todal del vuelo es de :" + costo);
		System.out.println("¿Que desea realizar?\n" +
				"1- Contratar. \n" +
				"2- Cancelar.");
		Scanner conf = new Scanner(System.in);
		int confirmacion = conf.nextInt();
		if(confirmacion == 1)
		{
			System.out.println("Tu vuelo se ha reservado con exito, detalles del vuelo:" + vuelonuevo.toString());
			vuelosPactados.add(vuelonuevo);
		}

	}


	public static Avion reservar ()
	{
		Scanner aviondes = new Scanner(System.in);
		int flag = 0;
		while(flag == 0) {													//Pide el numero de avion a reservar
			System.out.println("Ingrese el numero del avion disponible que desee:");
			int avion = aviondes.nextInt();									//Si ya esta reservado te pide que busques otro
			if (flotaAviones.get(avion).enVuelo == true)
			{
				System.out.println("EL avion esta ocupado en esa fecha, elija otro");
			}
			else															//Si no lo reserva cambiando enVuelo a true
			{
				System.out.println("Avion elegido: " + flotaAviones.get(avion).toString());
				flotaAviones.get(avion).enVuelo = true;
				flag = 1;
			}
		}																	
		return flotaAviones.get(avion); 		 		 		 	 		//Devuelve el avion que se reservo
	}

	//Tratar de que solo muestre los aviones disponibles
	public static void mostrarAvionesDisp (String fecha)
	{
		int a = flotaAviones.size();
		int j = vuelosPactados.size();
		for (int i = 0; i <= a; i++)      //recorre la flota de aviones
		{
			System.out.println("Avion n*" + i);
			System.out.println(flotaAviones.get(i).tostring());   //la imprime
			int flag = 0;
			int p = 0;
			while(flag == 0 && a <= j)												//recorre los vuelos pactados
			{
				if (flotaAviones.get(i) == vuelosPactados.get(p).tipoAvion)		    //si el avion esta en la lista de vuelos pactados se fija si en esa fecha esta ocupado
				{
					if (fecha == vuelosPactados.get(p).partida){					//si esta ocupado enVuelo pasa a ser true
						System.out.println("Avion no disponible para esa fecha");
						flag = 1;
						flotaAviones.get(i).enVuelo = true;
					}
					if (fecha == vuelosPactados.get(p).llegada)
					{
						System.out.println("Avion no disponible para esa fecha");
						flag = 1;
						flotaAviones.get(i).enVuelo = true;
					}
				}
				a++;
			}

		}
	}




	public static void listarClientes(ArrayList<Usuario> base) {
		if (!base.isEmpty()) {
			for (Usuario aux : base) {
				System.out.println(aux);
			}
		} else
			System.out.println("La base de clientes está vacía.");
	}
}
