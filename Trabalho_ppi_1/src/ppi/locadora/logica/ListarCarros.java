package ppi.locadora.logica;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ppi.agenda.dao.CarrosDao;
import ppi.agenda.model.Carro;

public class ListarCarros implements Logica{

	@Override
	public String executa(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// TODO Auto-generated method stub
		
		String tipo = req.getParameter("tipo");
		
		CarrosDao carro = new CarrosDao();
		
		// mostrar todos os carros disponiveis
		if(tipo.equals("all")) {
			List<Carro> carros =  carro.obterListaCarrosCompleta();
			
			req.setAttribute("carros", carros);
			return "carrosDisponiveis.jsp";
			
			// quantidade de carros armazendos por modelo
		} else if(tipo.equals("qnt")) {
			 List<Object> carros = carro.getconsultarQuantidade();
			 req.setAttribute("carros", carros);
			 return "listagemcarrosquantidade.jsp";

		}
		
		
		return null;
	}
}
