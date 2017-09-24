package ppi.locadora.logica;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ppi.agenda.dao.AlugarDao;
import ppi.agenda.dao.CarrosDao;
import ppi.agenda.dao.ClientesDao;
import ppi.agenda.model.Carro;
import ppi.agenda.model.Cliente;

public class AlugarCarro implements Logica {

	@Override
	public String executa(HttpServletRequest req, HttpServletResponse res) throws Exception {

		String[] dados = new String[5];

		// recupera dos dados da reserva
		dados[0] = req.getParameter("dataretirada");
		dados[1] = req.getParameter("horaretirada");
		dados[2] = req.getParameter("datadevolucao");
		dados[3] = req.getParameter("horadevolucao");
		dados[4] = req.getParameter("combo");
		
		
		// recupera os dados do cliente
		String nome = req.getParameter("clientenome");
		String cpf = req.getParameter("clientecpf");
		String endereco = req.getParameter("clienteendereco");
		String email = req.getParameter("clienteemail");
		String carroSelect = req.getParameter("carro");
		String telefone = req.getParameter("clientetelefone");
		String dataNascimento = req.getParameter("clientenascimento");

		
		Cliente clientenovo = new Cliente();
		clientenovo.setNome(nome);
		clientenovo.setCpf(cpf);
		clientenovo.setDataNascimentoString(dataNascimento);
		clientenovo.setTelefone(telefone);
		clientenovo.setEmail(email);
		clientenovo.setEndereco(endereco);


		// adiciona o cliente e armazena seu id
		ClientesDao cliente = new ClientesDao();
		int idCliente = cliente.adicionaCliente(clientenovo);
		clientenovo.setId(idCliente);

		Carro carro = new CarrosDao().obterCarro(Long.parseLong(carroSelect));

		AlugarDao alugar = new AlugarDao();

		int idAluguel = alugar.alugaCarro(Long.parseLong(carroSelect), idCliente, dados[0], dados[2],
				carro.getTarifaDia());

		req.setAttribute("idCliente", idCliente);
		req.setAttribute("idAluguel", idAluguel);

		System.out.println(idAluguel);
		System.out.println(idAluguel);

		return "confirmacaoReserva.jsp";
	}

}
