package Proyec;

public enum TipoDeDatos {

	STRING(1), INT(2), LONG(3), DOUBLE(4), FLOAT(5), DATE(6);

	private final int evaluar;

	private TipoDeDatos (int evaluar) {
		this.evaluar = evaluar;
	}

	public int getValue() {
		return evaluar;
	}

}
