package Proyec;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Programa {
	//hola 
	Scanner sc = new Scanner(System.in);
	RandomAccessFile fichero = null, entidades = null, atributos = null;
	private final String rutaBase = "C:\\Users\\jona9\\Desktop\\John\\Proyec\\";
	// cantidad de atributos, posicion donde inician los atributos 
	private final String rutaEntidades = "C:\\Users\\jona9\\Desktop\\John\\Proyec\\entidades.dat";
	// contiene indice de la entidad, nombre del atributo, tipo de dato, longitud =>
	private final String rutaAtributos = "C:\\Users\\jona9\\Desktop\\John\\Proyec\\atributos.dat";
	private final int totalBytes = 75, bytesEntidad = 45, bytesAtributo = 40;
	private final static String formatoFecha = "dd/MM/yyyy";
	static DateFormat format = new SimpleDateFormat(formatoFecha);
	private List<Entidades> listaEntidades = new ArrayList<>();
	public static void main(String[] args) {
		Programa ad = new Programa();
		if (ad.Definiciones()) {
			ad.menuPrincipal(true);
		} else {
			ad.menuPrincipal(false);
		}
		System.exit(0); // finalizar la aplicacion
	}
	
	private boolean Definiciones() {
		boolean resultado = false;
		try {
			entidades = new RandomAccessFile(rutaEntidades, "rw");
			atributos = new RandomAccessFile(rutaAtributos, "rw");
			long longitud = entidades.length();
			if (longitud <= 0) {
				System.out.println("No hay registros");
				resultado = false; // finalizar
			}
			if (longitud >= bytesEntidad) {
				// posicionarse al principio del archivo
				entidades.seek(0);
				// clase entidades
				Entidades e;
				while (longitud >= bytesEntidad) {
					e = new Entidades();
					e.setIndiceEntidad(entidades.readInt());
					byte[] bNombre = new byte[30]; // leer 30 bytes
					entidades.read(bNombre);
					e.setBytesNombre(bNombre);
					e.setCantidad(entidades.readInt());
					e.setBytes(entidades.readInt());
					e.setPosicion(entidades.readLong());
					entidades.readByte();// leer el cambio de linea
					longitud -= bytesEntidad;
					// leer atributos
					long longitudAtributos = atributos.length();
					if (longitudAtributos <= 0) {
						System.out.println("No hay registros");
						resultado = false;
						break;
					}
					atributos.seek(e.getPosicion());
					// clase atributos
					Atributos a;
					longitudAtributos = e.getCantidad() * bytesAtributo;
					while (longitudAtributos >= bytesAtributo) {
						a = new Atributos();
						a.setIndiceAtributo(atributos.readInt());
						byte[] bNombreAtributo = new byte[30]; // leer 30 bytes para el nombre
						atributos.read(bNombreAtributo);
						a.setBytesNombre(bNombreAtributo);
						a.setValorTipoDato(atributos.readInt());
						a.setLongitud(atributos.readInt());
						a.setNombreTipoDato();
						atributos.readByte();// leer el cambio de linea
						e.setAtributo(a);
						longitudAtributos -= bytesAtributo;
					}
					listaEntidades.add(e);
				}
				if (listaEntidades.size() > 0) {
					resultado = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultado;
	}

	private void mostrarEntidad(Entidades entidad) {
		System.out.println("Indice: " + entidad.getIndiceEntidad());
		System.out.println("Nombre: " + entidad.getNombreEntidad());
		System.out.println("Cantidad de atributos: " + entidad.getCantidad());
		System.out.println("Atributos:");
		int i = 1;
		for (Atributos atributo : entidad.getAtributos()) {
			System.out.println("\tNo. " + i);
			System.out.println("\tNombre: " + atributo.getNombreAtributo());
			System.out.println("\tTipo de dato: " + atributo.getNombreTipoDato());
			if (atributo.isRequiereLongitud()) {
				System.out.println("\tLongitud: " + atributo.getLongitud());
			}
			i++;
		}
	}

	private boolean agregarEntidad() {
		boolean resultado = false;
		try {
			Entidades entidad = new Entidades();
			entidad.setIndiceEntidad(listaEntidades.size() + 1);
			String strNombre = "";
			int longitud = 0;
			System.out.println("Ingrese el nombre de la entidad");
			do {
				strNombre = sc.nextLine();
				longitud = strNombre.length();
				if (longitud < 0 || longitud > 30) {
					System.out.println("La longitud del nombre no es valida [3 - 30]");
				} else {
					if (strNombre.contains(" ")) {
						System.out.println("El nombre no puede contener espacios, sustituya por guion bajo");
						longitud = 0;
					}
				}
			} while (longitud < 2 || longitud > 30);
			entidad.setNombreEntidad(strNombre);
			System.out.println("Atributos de la entidad");
			int bndDetener = 0;
			do {
				Atributos atributo = new Atributos();
				atributo.setIndiceAtributo(entidad.getIndiceEntidad());
				longitud = 0;
				System.out.println("Escriba Nombre del atributo no. " + (entidad.getCantidad() + 1));
				do {
					strNombre = sc.nextLine();
					longitud = strNombre.length();
					if (longitud < 0 || longitud > 30) {
						System.out.println("La longitud del nombre no es valida [3 - 30]");
					} else {
						if (strNombre.contains(" ")) {
							System.out.println(
									"El nombre no puede contener espacios, sustituya por guion bajo");
							longitud = 0;
						}
					}
				} while (longitud < 2 || longitud > 30);
				atributo.setNombreAtributo(strNombre);
				System.out.println("Seleccione el tipo de dato: ");
				System.out.println(TipoDeDatos.STRING.getValue() + " . " + TipoDeDatos.STRING.name());
				System.out.println(TipoDeDatos.INT.getValue() + " . " + TipoDeDatos.INT.name());
				System.out.println(TipoDeDatos.LONG.getValue() + " . " + TipoDeDatos.LONG.name());
				System.out.println(TipoDeDatos.DOUBLE.getValue() + " . " + TipoDeDatos.DOUBLE.name());
				System.out.println(TipoDeDatos.FLOAT.getValue() + " . " + TipoDeDatos.FLOAT.name());
				System.out.println(TipoDeDatos.DATE.getValue() + " . " + TipoDeDatos.DATE.name());
				atributo.setValorTipoDato(sc.nextInt());
				if (atributo.isRequiereLongitud()) {
					System.out.println("Ingrese la longitud");
					atributo.setLongitud(sc.nextInt());
				} else {
					atributo.setLongitud(0);
				}
				atributo.setNombreTipoDato();
				entidad.setAtributo(atributo);
				System.out.println("Desea agregar otro atributo presione 1, sino 0");
				bndDetener = sc.nextInt();
			} while (bndDetener != 0);
			System.out.println("Los datos a registrar son: ");
			mostrarEntidad(entidad);
			System.out.println("Desea guardar presione 1, sion 0 para cancelar");
			longitud = sc.nextInt();
			if (longitud == 1) {
				// guardar los atributos
				// establecer la posicion inicial donde se va a guardar
				entidad.setPosicion(atributos.length());
				atributos.seek(atributos.length());// calcular la longitud 
				for (Atributos atributo : entidad.getAtributos()) {
					atributos.writeInt(atributo.getIndiceAtributo());
					atributos.write(atributo.getBytesNombre());
					atributos.writeInt(atributo.getValorTipoDato());
					atributos.writeInt(atributo.getLongitud());
					atributos.write("\n".getBytes()); // cambio de linea
				}
				// guardar la entidad
				entidades.writeInt(entidad.getIndiceEntidad());
				entidades.write(entidad.getBytesNombre());
				entidades.writeInt(entidad.getCantidad());
				entidades.writeInt(entidad.getBytes());
				entidades.writeLong(entidad.getPosicion());
				entidades.write("\n".getBytes()); // cambio de linea 
				listaEntidades.add(entidad);
				resultado = true;
			} else {
				System.out.println("No se guardo la entidad");
				resultado = false;
			}
			System.out.println("Presione una tecla para continuar");
			System.in.read();
		} catch (Exception e) {
			resultado = false;
			e.printStackTrace();
		}
		return resultado;
	}


	private void menuPrincipal(boolean mostrarAgregarRegistro) {
		int opcion = 1;
		while (opcion != 0) {
			System.out.println("Elija su opcion");
			System.out.println("1. Agregar entidad");
			System.out.println("2. Listar entidades");
			if (mostrarAgregarRegistro) {
				System.out.println("3. Agregar registros");
			}
			System.out.println("4. Borrar bases de datos");
			System.out.println("0. Salir");
			opcion = sc.nextInt();
			switch (opcion) {
			case 0:
				System.out.println("Gracias por usar la aplicacion");
				break;
			case 1:
				if (agregarEntidad()) {
					System.out.println("Entidad agregada con exito");
					mostrarAgregarRegistro = true;
				}
				break;
			
			case 2:
				if (listaEntidades.size() > 0) {
					int tmpInt = 0;
					System.out.println("Desea imprimir los detalles. Si, presione 1. No, presione 0?");
					tmpInt = sc.nextInt();
					if (tmpInt == 1) {
						for (Entidades entidad : listaEntidades) {
							mostrarEntidad(entidad);
						}
					} else {
						for (Entidades entidad : listaEntidades) {
							System.out.println("Indice: " + entidad.getIndiceEntidad());
							System.out.println("Nombre: " + entidad.getNombreEntidad());
							System.out.println("Cantidad de atributos: " + entidad.getCantidad());
						}
					}
				} else {
					System.out.println("No hay entidades registradas");
				}
				break;
			case 3:
				int indice = 0;
				while (indice < 1 || indice > listaEntidades.size()) {
					for (Entidades entidad : listaEntidades) {
						System.out.println(entidad.getIndiceEntidad() + " ...... " + entidad.getNombreEntidad());
					}
					System.out.println("Seleccione la entidad que desea trabajar");
					indice = sc.nextInt();
				}
				iniciar(indice);
				break;
			case 4:
				int confirmar = 0;
				System.out.println(
						"Esta seguro de borrar los datos del archivo, Si, presione 1. No, presione 0");
				confirmar = sc.nextInt();
				if (confirmar == 1) {
					cerrarArchivos();
					if (borrarArchivos()) {
						listaEntidades = null;
						listaEntidades = new ArrayList<>();
						mostrarAgregarRegistro = false;
						System.out.println("Archivos borrados");
					}
				}
				break;
			default:
				System.out.println("Opcion no valida");
				break;
			}
		}
	}

	private void cerrarArchivos() {
		if (fichero != null) {
			try {
				fichero.close();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		if (atributos != null) {
			try {
				atributos.close();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		if (entidades != null) {
			try {
				entidades.close();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
	}

	private boolean borrarArchivos() {
		boolean res = false;
		try {
			File file;
			for (Entidades entidad : listaEntidades) {
				file = new File(rutaBase + entidad.getNombreEntidad().trim() + ".dat");
				if (file.exists()) {
					file.delete();
				}
				file = null;
			}
			file = new File(rutaAtributos);
			if (file.exists()) {
				file.delete();
			}
			file = null;
			file = new File(rutaEntidades);
			if (file.exists()) {
				file.delete();
			}
			file = null;
			res = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	private String formarNombreFichero(String nombre) {
		return nombre.trim() + ".txt";
	}

	// metodos para guardar registros segun la definicion
	private void iniciar(int indice) {
		int opcion = 0;
		String nombreFichero = "";
		try {
			Entidades entidad = null;
			for (Entidades e : listaEntidades) {
				if (indice == e.getIndiceEntidad()) {
					nombreFichero = formarNombreFichero(e.getNombreEntidad());
					entidad = e;
					break;
				}
			}
			fichero = new RandomAccessFile(rutaBase + nombreFichero, "rw");
			System.out.println("Bienvenido (a)");
			Atributos a = entidad.getAtributos().get(0);
			do {
				try {
					System.out.println("Seleccione su opcion");
					System.out.println("1.Agregar Datos");
					System.out.println("2.Listar Datos");
					System.out.println("3.Modificar Datos");
					System.out.println("4.Eliminar");
					System.out.println("0.Regresar al menu anterior");
					opcion = sc.nextInt();
					switch (opcion) {
					case 0:
						System.out.println("");
						break;
					case 1:
						grabarRegistro(entidad);
						break;
					case 2:
						listarRegistros(entidad);
						break;
					case 3:
						modificarRegistros(entidad);
						break;
					case 4:
						eliminarRegistros(entidad);
						break;
					default:
						System.out.println("Opcion no valida");
						break;
					}
				} catch (Exception e) { 
					System.out.println("Error: " + e.getMessage());
				}
			} while (opcion != 0);
			fichero.close();
		} catch (Exception e) { // capturar cualquier excepcion que ocurra
			System.out.println("Error: " + e.getMessage());
		}
	}

	private boolean grabarRegistro(Entidades entidad) {
		boolean resultado = false;
		try {
			// posicionarse al final para grabar
			fichero.seek(fichero.length());
			boolean valido;
			byte[] bytesString;
			String tmpString = "";
			for (Atributos atributo : entidad.getAtributos()) {
				valido = false;
				System.out.println("Ingrese " + atributo.getNombreAtributo().trim());
				while (!valido) {
					try {
						switch (atributo.getTipoDato()) {
						case STRING:
							int longitud = 0;
							do {
								tmpString = sc.nextLine();
								longitud = tmpString.length();
								if (longitud <= 1 || longitud > atributo.getLongitud()) {
									System.out.println("La longitud de " + atributo.getNombreAtributo().trim()+ " no es valida [1 - " + atributo.getLongitud() + "]");
								}
							} while (longitud <= 0 || longitud > atributo.getLongitud());
							// arreglo de bytes de longitud segun definida
							bytesString = new byte[atributo.getLongitud()];
							// convertir caracter por caracter a byte y agregarlo al arreglo
							for (int i = 0; i < tmpString.length(); i++) {
								bytesString[i] = (byte) tmpString.charAt(i);
							}
							fichero.write(bytesString);
							break;
						case INT:
							int tmpInt = sc.nextInt();
							fichero.writeInt(tmpInt);
							sc.nextLine();
							break;
						case LONG:
							long tmpLong = sc.nextLong();
							fichero.writeLong(tmpLong);
							break;
						case DOUBLE:
							double tmpDouble = sc.nextDouble();
							fichero.writeDouble(tmpDouble);
							break;
						case FLOAT:
							float tmpFloat = sc.nextFloat();
							fichero.writeFloat(tmpFloat);
							break;
						case DATE:
							Date date = null;
							tmpString = "";
							while (date == null) {
								System.out.println("Formato de fecha: " + formatoFecha);
								tmpString = sc.nextLine();
								date = strintToDate(tmpString);
							}
							bytesString = new byte[atributo.getBytes()];
							for (int i = 0; i < tmpString.length(); i++) {
								bytesString[i] = (byte) tmpString.charAt(i);
							}
							
							fichero.write(bytesString);
							break;
						}
						valido = true;
					} catch (Exception e) {
						System.out.println(
								"Error " + e.getMessage() + " al capturar tipo de dato, vuelva a ingresar el valor: ");
						sc.nextLine();
					}
				} // end while
			} // end for
			fichero.write("\n".getBytes()); // cambio de linea 
			resultado = true;

		} catch (Exception e) {
			resultado = false;
			System.out.println("Error al agregar el registro " + e.getMessage());
		}
		return resultado;
	}

	public void listarRegistros(Entidades entidad) {
		try {
			long longitud = fichero.length();
			if (longitud <= 0) {
				System.out.println("No hay registros");
				return; // finalizar el procedimiento
			}
			// posicionarse al principio del archivo
			fichero.seek(0);
			byte[] tmpArrayByte;
			String linea = "";
			for (Atributos atributo : entidad.getAtributos()) {
				linea += atributo.getNombreAtributo().toString().trim() + "\t\t";
			}
			
			System.out.println(linea);
			while (longitud >= entidad.getBytes()) {
				linea = "";
				for (Atributos atributo : entidad.getAtributos()) {
					switch (atributo.getTipoDato()) {
					case STRING:
						tmpArrayByte = new byte[atributo.getLongitud()];
						fichero.read(tmpArrayByte);
						String tmpString = new String(tmpArrayByte);
						linea += tmpString.trim() + "\t\t";
						break;
					case INT:
						int tmpInt = fichero.readInt();
						linea += String.valueOf(tmpInt) + "\t\t";
						break;
					case LONG:
						long tmpLong = fichero.readLong();
						linea += String.valueOf(tmpLong) + "\t\t";
						break;
					case DOUBLE:
						double tmpDouble = fichero.readDouble();
						linea += String.valueOf(tmpDouble) + "\t\t";
						break;
					case FLOAT:
						float tmpFloat = fichero.readFloat();
						linea += String.valueOf(tmpFloat) + "\t\t";
						break;
					case DATE:
						tmpArrayByte = new byte[atributo.getBytes()];
						fichero.read(tmpArrayByte);
						tmpString = new String(tmpArrayByte);
						linea += tmpString.trim() + "\t\t";
						break;
					}
				}
				fichero.readByte();// leer el cambio de linea
				// restar los bytes del registro leido
				longitud -= entidad.getBytes();
				System.out.println(linea);
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
	

	public Date strintToDate(String strFecha) {
		Date date = null;
		try {
			date = format.parse(strFecha);
		} catch (Exception e) {
			date = null;
			System.out.println("Error en fecha: " + e.getMessage());
		}
		return date;
	}

	public String dateToString(Date date) {
		String strFecha;
		strFecha = format.format(date);
		return strFecha;
	}

}
