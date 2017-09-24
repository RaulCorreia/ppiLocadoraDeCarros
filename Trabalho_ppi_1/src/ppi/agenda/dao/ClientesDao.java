package ppi.agenda.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ppi.agenda.jdbc.ConnectionFactory;
import ppi.agenda.model.Cliente;





public class ClientesDao {
	
	
	private Connection connection;
	
	
	public ClientesDao() {
		this.connection = new ConnectionFactory().getConnection();
	}
	
	
	
	//GETTERs
	
	public List<Cliente> obterListaClientesCompleta() {


		List<Cliente> clientes = new ArrayList<Cliente>();

		try {

			PreparedStatement stmt = this.connection.prepareStatement("SELECT * FROM clientes");
			ResultSet rs = stmt.executeQuery();

			while(rs.next()) {

				Cliente novoCliente = new Cliente();

				novoCliente.setId(rs.getInt("id"));
				novoCliente.setNome(rs.getString("nome"));
				novoCliente.setCpf(rs.getString("cpf"));
				novoCliente.setEmail(rs.getString("email"));
				novoCliente.setDataNascimentoFromSQL(rs.getDate("dataNascimento"));
				novoCliente.setTelefone(rs.getString("telefone"));

				clientes.add(novoCliente);

			}

		} catch (SQLException e) {
			System.out.println("Erro ao obter lista de clientes completa");
			System.out.println(e.getMessage());
		}


		return clientes;

	}
	
	
	
	
	public Cliente obterCliente(int id) {


		Cliente novoCliente = new Cliente();

		try {

			PreparedStatement stmt = this.connection.prepareStatement("SELECT * FROM clientes WHERE ID = ?");
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();

			if(rs.next()) {


				novoCliente.setId(rs.getInt("id"));
				novoCliente.setNome(rs.getString("nome"));
				novoCliente.setCpf(rs.getString("cpf"));
				novoCliente.setEmail(rs.getString("email"));
				novoCliente.setEndereco(rs.getString("endereco"));
				novoCliente.setDataNascimentoFromSQL(rs.getDate("dataNascimento"));
				novoCliente.setTelefone(rs.getString("telefone"));


			}

		} catch (SQLException e) {
			System.out.println("Erro ao obter um cliente");
			System.out.println(e.getMessage());
		}


		return novoCliente;

	}
	
	
	
	
	
	// SETTERs
	
	
	
public int adicionaCliente(Cliente novoCliente) {
		
		
		try {
			
			PreparedStatement stmt = this.connection.prepareStatement("INSERT INTO clientes(nome, cpf, email, endereco, dataNascimento, telefone) VALUES (?, ?, ?, ?, ?, ?)");
			
			stmt.setString(1, novoCliente.getNome());
			stmt.setString(2, novoCliente.getCpf());
			stmt.setString(3, novoCliente.getEmail());
			stmt.setString(4, novoCliente.getEndereco());
			stmt.setDate(5,novoCliente.getDataNascimentoToSQL());
			stmt.setString(6, novoCliente.getTelefone());

			
			stmt.executeUpdate();
			
			stmt =  this.connection.prepareStatement("select * from clientes where cpf = ?; ");
			stmt.setString(1, novoCliente.getCpf());
			
			
			int idCliente=0;
			
			/*
			ResultSet rs = stmt.getGeneratedKeys();
			if(rs.next())
				idCliente= rs.getInt("id");*/
			
			ResultSet rs = stmt.executeQuery();

			if(rs.next()) {
				novoCliente.setId(rs.getInt("id"));
			}
			
			return novoCliente.getId();
			
		} catch (SQLException e) {
			System.out.println("Erro ao inserir novo cliente");
			System.out.println(e.getMessage());
		}
		return 0;
		
		
		
		
	}
}
