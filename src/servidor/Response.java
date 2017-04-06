package servidor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class Hello
 */
@WebServlet("/Response")
public class Response extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Response() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
		this.destroy();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		PrintWriter out = response.getWriter();
		out.println(
			  "<!DOCTYPE html>\n"
			+ "<html>"
			+ 	"<head><title>Hello</title></head>"
			+ 	"<body>"
			+ 		"<h1>Hello, " + request.getParameter("nome") + "</h1>"
			+ 	"</body>"					  
			+ "</html>"
		);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		Map<String,String[]> m = request.getParameterMap();
		Formulario form = new Formulario(m);
		Simplex.form = form;
		float[][] resposta = Simplex.PegarEquacao();
		PrintWriter out = response.getWriter();
		out.println(
			  "<!DOCTYPE html>\n"
			+ "<html>"
			+ 	"<head>"
			+ 		"<title>Otimizador de desenhos</title>"
			+ 		"<meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />"
			+ 		"<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css' />"
			+ 		"<link rel='stylesheet' href='style.css' />"
			+ 	"</head>"
			+ 	"<body>"
			+ 		"<center>"
			+ 			"<div class='col-xs-12'> <img src='titulo.png' class='img-responsive' /></div>"
			+ 			"<table>");
			for(int i=0; i<resposta.length; i++) {
				out.print("<tr style='padding:10px'>");
				for(int j=0; j<resposta[i].length; j++) {
					out.print("<td style='padding:10px'>" + resposta[i][j] + "</td>");
				}
				out.print("</tr>");
			}
		
			out.println("</table></center></body></html>");
	}
	
	public String matrizToString(float[][] m) {
		String s = "";
		for(int i=0; i<m.length; i++) {
			for(int j=0; j<m[i].length; j++) {
				s += m[i][j] + " "; 
			}
			s += "\n";
		}
		return s;
	}

}
