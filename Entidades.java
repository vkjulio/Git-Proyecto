package Proyec;

import java.util.ArrayList;
import java.util.List;

public class Entidades {

		private int indiceEntidad;
		private String nombreEntidad; //Nombre de la entidades
		private int cantidad;
		private long posicion; // posicion donde inician sus atributos
		private byte[] bytesNombre;
		private int bytes = 1; // inicia en uno que representa el cambio de linea

		private List<Atributos> atributos;

		public int getIndiceEntidad() {
			return indiceEntidad;
		}

		public void setIndiceEntidad(int indiceEntidad) {
			this.indiceEntidad = indiceEntidad;
		}

		public String getNombreEntidad() {
			return nombreEntidad;
		}

		public void setNombreEntidad(String nombreEntidad) {
			this.nombreEntidad = nombreEntidad;
			bytesNombre = new byte[30]; // arreglo de bytes de longitud 30
			// convertir caracter por caracter a byte y agregarlo al arreglo
			for (int i = 0; i < nombreEntidad.length(); i++) {
				bytesNombre[i] = (byte) nombreEntidad.charAt(i);
			}
		}

		public byte[] getBytesNombre() {
			return bytesNombre;
		}

		public void setBytesNombre(byte[] bytesNombre) {
			this.bytesNombre = bytesNombre;
			nombreEntidad = new String(bytesNombre);
		}

		public int getCantidad() {
			return cantidad;
		}

		public void setCantidad(int cantidad) {
			this.cantidad = cantidad;
		}

		public List<Atributos> getAtributos() {
			return atributos;
		}

		public void setAtributos(List<Atributos> atributos) {
			this.atributos = atributos;
		}

		public void setAtributo(Atributos atributo) {
			if (this.atributos == null) {
				this.atributos = new ArrayList<>();
			}
			this.atributos.add(atributo);
			this.cantidad = this.atributos.size();
		}

		public void removeAtributo(Atributos atributo) {
			if (this.atributos != null) {
				if (this.atributos.size() > 0) {
					this.atributos.remove(atributo);
					this.cantidad = this.atributos.size();
				}
			}
		}

		public long getPosicion() {
			return posicion;
		}

		public void setPosicion(long posicion) {
			this.posicion = posicion;
		}

		public int getBytes() {
			bytes = 1;
			for (Atributos atributo : atributos) {
				bytes += atributo.getBytes();
			}
			return bytes;
		}

		public void setBytes(int bytes) {
			this.bytes = bytes;
		}

	}
