package ppi.agenda.model;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.joda.time.DateTime;
import org.joda.time.Days;

public class Aluguel {
	
	private int id;
	private int idDoCliente;
	private long renavanDoCarro;
	
	private Calendar dataInicioAluguel;
	private Calendar dataFinalAluguel;
	private double tarifaBase;
	private double tarifaFinal;
	
	private String nomeCliente;
	private String cpfCliente;
	private String modeloDoCarro;
	

	

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdDoCliente() {
		return idDoCliente;
	}

	public void setIdDoCliente(int idDoCliente) {
		this.idDoCliente = idDoCliente;
	}

	
	public long getRenavanDoCarro() {
		return renavanDoCarro;
	}

	public void setRenavanDoCarro(long renavanDoCarro) {
		this.renavanDoCarro = renavanDoCarro;
	}

	public Calendar getDataInicioAluguel() {
		return dataInicioAluguel;
	}

	public void setDataInicioAluguel(Calendar dataInicioAluguel) {
		this.dataInicioAluguel = dataInicioAluguel;
	}

	public Calendar getDataFinalAluguel() {
		return dataFinalAluguel;
	}

	public void setDataFinalAluguel(Calendar dataFinalAluguel) {
		this.dataFinalAluguel = dataFinalAluguel;
	}

	public double getTarifaBase() {
		return tarifaBase;
	}

	public void setTarifaBase(double tarifaBase) {
		this.tarifaBase = tarifaBase;
	}


	public double getTarifaFinal() {
		return tarifaFinal;
	}
	

	public void setTarifaFinal(double tarifaFinal) {
		this.tarifaFinal = tarifaFinal;
	}


	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public String getCpfCliente() {
		return cpfCliente;
	}

	public void setCpfCliente(String cpfCliente) {
		this.cpfCliente = cpfCliente;
	}

	public String getModeloDoCarro() {
		return modeloDoCarro;
	}

	public void setModeloDoCarro(String modeloDoCarro) {
		this.modeloDoCarro = modeloDoCarro;
	}
	
	
	
	

	public double gerarValorTotal(Calendar dataFinalDevolucao) {
		
		
		double tarifaFinal;
		int diasCorridos;
		//int MILLIS_IN_DAY = 86400000;
		
		DateTime dataInicial = new DateTime(this.dataInicioAluguel);
		DateTime dataFinal = new DateTime(this.dataFinalAluguel);
		DateTime dataDaDevolucao = new DateTime(dataFinalDevolucao);
		
		diasCorridos = Days.daysBetween(dataInicial, dataFinal).getDays();
		
		if(dataFinal.equals(dataDaDevolucao) || dataFinal.isAfter(dataDaDevolucao))
			tarifaFinal = tarifaBase * diasCorridos;
		else {
			
			// Obtem a quantidade de dias atrasados
			int diasDeAtraso = Days.daysBetween(dataFinal, dataDaDevolucao).getDays();
			
			// Calcula a taxa de atrasp
			//Taxa de Atraso é 30% do valor total vezes a quantidade de dias de atraso
			//							30% da Tarifa correta			X	Dias de atraso
			double taxaDeAtraso = ((this.tarifaBase * diasCorridos) * 0.30) * diasDeAtraso;
			
						
			//					Tarifa Correta		+	Taxa pora traso
			tarifaFinal = (tarifaBase * diasCorridos) + taxaDeAtraso;
			
		}
		
		/*
		// Obtem a quantidade de dias do contrato de aluguel sem atraso
		diasCorretos = ( this.dataFinalAluguel.getTimeInMillis() - this.dataInicioAluguel.getTimeInMillis() ) / MILLIS_IN_DAY;
		
		
		// Testa se a data passada na hora de devolver é igual a data que foi dita no momento de alugar
		if(dataFinalDevolucao == this.dataFinalAluguel) {
			
			tarifaFinal = tarifaBase * diasCorretos;
			
		} else {
			
			diasCorretos = diasCorretos + ( (this.dataFinalAluguel.getTimeInMillis() - dataFinalDevolucao.getTimeInMillis()) / MILLIS_IN_DAY );
			
			tarifaFinal = tarifaBase * diasCorretos;
		}
		*/
		
		this.tarifaFinal = tarifaFinal;
		return tarifaFinal;
	}
	
	
	
	
	// Gets e Sets para auxilio


	// Get e set entre String(front) e Calendar(Objeto)

	
	
	// DATA INICIO ALUGUEL
	// Recebe a String da data e insere no objeto como calendar
	public void setDataInicioAluguelString(String dataInicioAluguel) {

		try {

			java.util.Date date = new SimpleDateFormat("dd/MM/yyyy").parse(dataInicioAluguel);
			Calendar dataCalendar = Calendar.getInstance();
			dataCalendar.setTime(date);

			this.dataInicioAluguel = dataCalendar;

		} catch (ParseException e) {
			e.printStackTrace();
		}


	}

	// Retorna String ja formatada
	public String getDataInicioAluguelString() {

		SimpleDateFormat data = new SimpleDateFormat("dd/MM/yyyy");

		return data.format(this.dataInicioAluguel.getTime());

	}
	
	
	
	
	// DATA FINAL ALUGUEL
	// Recebe a String da data e insere no objeto como calendar
	public void setDataFinalAluguelString(String dataFinalAluguel) {

		try {

			java.util.Date date = new SimpleDateFormat("dd/MM/yyyy").parse(dataFinalAluguel);
			Calendar dataCalendar = Calendar.getInstance();
			dataCalendar.setTime(date);

			this.dataFinalAluguel = dataCalendar;

		} catch (ParseException e) {
			e.printStackTrace();
		}


	}

	// Retorna String ja formatada
	public String getDataFinalAluguelString() {

		SimpleDateFormat data = new SimpleDateFormat("dd/MM/yyyy");

		return data.format(this.dataFinalAluguel.getTime());

	}
	
	
	

	//----------------------


	// Get e set para trabalhar com o banco

	// Trabalhar com a data Vindo ou Indo para o Banco

	
	
	// DATA INICIO ALUGUEL
	// Recebe a data do Sql e insere no formado Calendar
	public void setDataInicioAluguelFromSQL(Date dataInicioAluguel) {

		// montando a data através do Calendar
		Calendar data = Calendar.getInstance();
		data.setTime(dataInicioAluguel);
		this.dataInicioAluguel = data;

	}

	// Retorna a data Calendar em formato Date do SQL
	public Date getDataInicioAluguelToSQL() {
		return new Date(dataInicioAluguel.getTimeInMillis());
	}

	
	
	
	
	// DATA FINAL ALUGUEL dataFinalAluguel
	// Recebe a data do Sql e insere no formado Calendar
	public void setDataFinalAluguelFromSQL(Date dataFinalAluguel) {

		// montando a data através do Calendar
		Calendar data = Calendar.getInstance();
		data.setTime(dataFinalAluguel);
		this.dataFinalAluguel = data;

	}

	// Retorna a data Calendar em formato Date do SQL
	public Date getDataFinalAluguelToSQL() {
		return new Date(dataFinalAluguel.getTimeInMillis());
	}
	
	

}
