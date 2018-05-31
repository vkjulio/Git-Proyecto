package Proyec;

public class Atributos {

	private int indiceAtributo;
	private String nombreAtributo;  //Nombre de los atributos
	private int valorTipoDato;
	private String nombreTipoDato;
	private int longitud;
	private int bytes;
	private boolean requiereLongitud;
	private byte[] bytesNombre;
	private TipoDeDatos tipoDato;


	public int getIndiceAtributo() {
		return indiceAtributo;
	}

	public void setIndiceAtributo(int indiceAtributo) {
		this.indiceAtributo = indiceAtributo;
	}

	public String getNombreAtributo() {
		return nombreAtributo;
	}

	public void setNombreAtributo(String nombreAtributo) {
		this.nombreAtributo = nombreAtributo;
		bytesNombre = new byte[30]; 
		for (int i = 0; i < nombreAtributo.length(); i++) { //Convierte los caracteres a bytes.
			bytesNombre[i] = (byte) nombreAtributo.charAt(i);
		}
	}

	public byte[] getBytesNombre() {
		return bytesNombre;
	}

	public void setBytesNombre(byte[] bytesNombre) {
		this.bytesNombre = bytesNombre;
		nombreAtributo = new String(bytesNombre);
	}

	public int getValorTipoDato() {
		return valorTipoDato;
	}

	public void setValorTipoDato(int valorTipoDato) {
		this.valorTipoDato = valorTipoDato;
		if (valorTipoDato == TipoDeDatos.STRING.getValue()) {
			this.requiereLongitud = true;
		}
	}

	public int getLongitud() {
		return longitud;
	}

	public void setLongitud(int longitud) {
		this.longitud = longitud;
	}

	public boolean isRequiereLongitud() {
		return requiereLongitud;
	}

	public String getNombreTipoDato() {
		return nombreTipoDato;
	}

	public void setNombreTipoDato() {
		if (this.valorTipoDato == TipoDeDatos.STRING.getValue()) {
			this.nombreTipoDato = TipoDeDatos.STRING.name();
			this.bytes = this.longitud;
			tipoDato = TipoDeDatos.STRING;
		}
		if (this.valorTipoDato == TipoDeDatos.INT.getValue()) {
			this.nombreTipoDato = TipoDeDatos.INT.name();
			this.bytes = 4;
			tipoDato = TipoDeDatos.INT;
		}
		if (this.valorTipoDato == TipoDeDatos.LONG.getValue()) {
			this.nombreTipoDato = TipoDeDatos.LONG.name();
			this.bytes = 8;
			tipoDato = TipoDeDatos.LONG;
		}
		if (this.valorTipoDato == TipoDeDatos.DOUBLE.getValue()) {
			this.nombreTipoDato = TipoDeDatos.DOUBLE.name();
			this.bytes = 8;
			tipoDato = TipoDeDatos.DOUBLE;
		}
		if (this.valorTipoDato == TipoDeDatos.FLOAT.getValue()) {
			this.nombreTipoDato = TipoDeDatos.FLOAT.name();
			this.bytes = 4;
			tipoDato = TipoDeDatos.FLOAT;
		}
		if (this.valorTipoDato == TipoDeDatos.DATE.getValue()) {
			this.nombreTipoDato = TipoDeDatos.DATE.name();
			this.bytes = 28;
			tipoDato = TipoDeDatos.DATE;
		}

	}

	public int getBytes() {
		return bytes;
	}

	public TipoDeDatos getTipoDato() {
		return tipoDato;
	}

}
