package ppi.agenda.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;

import ppi.agenda.jdbc.ConnectionFactory;
import ppi.agenda.model.Aluguel;
import ppi.agenda.model.Carro;

public class CarrosDao {

	private Connection connection;

	public CarrosDao() {
		this.connection = new ConnectionFactory().getConnection();
	}

	
	public List<Carro> obterListaCarrosDisponivel(String categoria, String dataInicial, String dataFinal) {

		
		List<Carro> CarrosDisponiveis = new ArrayList<Carro>();

		try {

			Date data = new SimpleDateFormat("dd/MM/yyyy").parse(dataInicial);
			String dataInicialFormatada = new SimpleDateFormat("yyyy/MM/dd").format(data);

			data = new SimpleDateFormat("dd/MM/yyyy").parse(dataFinal);
			String dataFinalFormatada = new SimpleDateFormat("yyyy/MM/dd").format(data);

			PreparedStatement stmt;
			try {
				stmt = this.connection.prepareStatement(

						"select * from carros where categoria like ? and id not in (select idDoCarro from aluguel where dataFinalAluguel between ? and ? ); ");
				stmt.setString(1, categoria);
				stmt.setString(2, dataInicialFormatada);
				stmt.setString(3, dataFinalFormatada);
				

				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {

					Carro novoCarro = new Carro();
					novoCarro.setRenavan(rs.getLong("renavan"));
					novoCarro.setModelo(rs.getString("modelo"));
					novoCarro.setCategoria(rs.getString("categoria"));
					novoCarro.setAnoFabricacao(rs.getString("anoFabricacao"));
					novoCarro.setTarifaDia(rs.getDouble("tarifaDia"));

					boolean existe = false;

					if (CarrosDisponiveis.isEmpty())
						CarrosDisponiveis.add(novoCarro);
					else {
						for (int j = 0; j < CarrosDisponiveis.size(); j++) {

							if (CarrosDisponiveis.get(j).getModelo().equals(novoCarro.getModelo())) {
								existe = true;
								break;
							}

						}

						if (!existe) {
							CarrosDisponiveis.add(novoCarro);

						}

					}

				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.getMessage();
			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}
		
		return CarrosDisponiveis;

		}



	

	public List<Carro> obterListaCarrosCompleta() {

		List<Carro> carros = new ArrayList<Carro>();

		try {

			PreparedStatement stmt = this.connection.prepareStatement("SELECT * FROM carros");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {

				Carro novoCarro = new Carro();

				novoCarro.setRenavan(rs.getLong("renavan"));
				novoCarro.setModelo(rs.getString("modelo"));
				novoCarro.setCategoria(rs.getString("categoria"));
				novoCarro.setAnoFabricacao(rs.getString("anoFabricacao"));
				novoCarro.setTarifaDia(rs.getDouble("tarifaDia"));

				carros.add(novoCarro);

			}

		} catch (SQLException e) {
			System.out.println("Erro ao obter lista de carros completa");
			System.out.println(e.getMessage());
		}

		return carros;

	}

	public Carro obterCarro(long renavan) {

		Carro novoCarro = new Carro();

		try {

			PreparedStatement stmt = this.connection.prepareStatement("SELECT * FROM carros WHERE RENAVAN = ?");
			stmt.setLong(1, renavan);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {

				novoCarro.setRenavan(rs.getLong("renavan"));
				novoCarro.setModelo(rs.getString("modelo"));
				novoCarro.setCategoria(rs.getString("categoria"));
				novoCarro.setAnoFabricacao(rs.getString("anoFabricacao"));
				novoCarro.setTarifaDia(rs.getDouble("tarifaDia"));

			}

		} catch (SQLException e) {
			System.out.println("Erro ao obter um carro");
			System.out.println(e.getMessage());
		}

		return novoCarro;

	}

	// SETTERs

	public void adicionaCarro(String modelo, String categoria, Long renavan, String anoFabricacao, double tarifaDia,
			int quantidadeMax, int quantidadeDisponivel) {

		try {

			PreparedStatement stmt = this.connection.prepareStatement(
					"INSERT INTO carros(renavan, modelo, categoria, anoFabricacao,tarifaDia) VALUES (?, ?, ?, ?, ?)");

			stmt.setLong(1, renavan);
			stmt.setString(2, modelo);
			stmt.setString(3, categoria);
			stmt.setString(4, anoFabricacao);
			stmt.setDouble(5, tarifaDia);

			stmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Erro ao inserir novo carro");
			System.out.println(e.getMessage());
		}

	}

	public void removerCarro(long renavan) {
		try {

			PreparedStatement stmt = this.connection.prepareStatement("Delete  from carros where renavan=" + renavan + ";");
			stmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// retorna a quantidade de carros armazenados por modelo
	public List<Object> getconsultarQuantidade() {

		try {

			PreparedStatement stmt = this.connection.prepareStatement(
					"SELECT COUNT(renavan) as 'quantidade', modelo, categoria FROM carros group by modelo, categoria;");

			ResultSet rs = stmt.executeQuery();

			List<Object> lista = new ArrayList<Object>();

			while (rs.next()) {

				Object[] c = new Object[3];

				c[0] = rs.getString("quantidade");
				c[1] = rs.getString("modelo");
				c[2] = rs.getString("categoria");

				lista.add(c);

			}

			return lista;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

