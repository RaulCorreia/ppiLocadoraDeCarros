package ppi.agenda.model;

public class Carro {
	
	private long renavan;
	private String modelo;
	private String categoria;
	private String anoFabricacao;
	private double tarifaDia;

	
	
	
	public void setRenavan(long renavan) {
		this.renavan = renavan;
	}

	public String getModelo() {
		return modelo;
	}
	
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	
	public String getCategoria() {
		return categoria;
	}
	
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
	
	public String getAnoFabricacao() {
		return anoFabricacao;
	}
	
	public void setAnoFabricacao(String anoFabricacao) {
		this.anoFabricacao = anoFabricacao;
	}
	
	public double getTarifaDia() {
		return tarifaDia;
	}
	
	public void setTarifaDia(double tarifaDia) {
		this.tarifaDia = tarifaDia;
	}



}
