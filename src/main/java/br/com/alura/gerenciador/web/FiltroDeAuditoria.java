package br.com.alura.gerenciador.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.alura.gerenciador.Usuario;

@WebFilter(urlPatterns = "/*")
public class FiltroDeAuditoria implements Filter {
	
	private HttpServletResponse resp;
	
	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		// CAST PARA HTTP SERVLET REQUEST
		HttpServletRequest req = (HttpServletRequest) request;

		// PEGA A URI:
		String uri = req.getRequestURI();
		
		this.resp = (HttpServletResponse) response;
		
		System.out.println("Usuário " + this.getUsuario(req)
				+ " acessando a URI: " + uri);

		// CONTINUA COM A CADEIA EXECUÇÃO DE SERVLETS, FILTERS, ETC:
		chain.doFilter(request, this.resp);

	}

	// RECUPERA O USUÁRIO NO COOKIE SE EXISTIR:
	private String getUsuario(HttpServletRequest req) {
		
		// PEGA O USUÁRIO LOGADO GUARDADO NO ATRIBUTO DA SESSION:
		Usuario usuario = (Usuario) req.getSession().getAttribute("usuarioLogado");
		
		if(usuario == null) return "<deslogado>";
		return usuario.getEmail();
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}
