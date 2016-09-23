package api;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import db.DBConnection;
import db.MongoDBConnection;
import db.MySQLDBConnection;

/**
 * Servlet implementation class SearchRestaurants
 */
@WebServlet("/restaurants")
public class SearchRestaurants extends HttpServlet {
	private static final long serialVersionUID = 1L;



	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SearchRestaurants() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		// response.getWriter().append("Served at:
		// ").append(request.getContextPath());
		/*
		 * response.setContentType("application/json");
		 * response.addHeader("Access-Control-Allow-Origin", "*");// everyone
		 * can read this response page String username = ""; String age = "";
		 * PrintWriter out = response.getWriter(); if
		 * (request.getParameter("username") != null) { username =
		 * request.getParameter("username"); } if (request.getParameter("age")
		 * != null) { age = request.getParameter("age"); } out.println("Hello "
		 * + username); out.print("Your age is " + age); out.flush();
		 * out.close();
		 */

		/*
		 * response.setContentType("text/html"); PrintWriter out =
		 * response.getWriter(); out.println("<html><body>");
		 * out.println("<h1>This is a HTML page</h1>");
		 * out.println("</body></html>"); out.flush(); out.close();
		 */

		/*
		 * response.setContentType("application/json");
		 * response.addHeader("Access-Control-Allow-Origin", "*");
		 * 
		 * String username = ""; if (request.getParameter("username") != null) {
		 * username = request.getParameter("username"); } JSONObject obj = new
		 * JSONObject(); try { obj.put("username", username); } catch
		 * (JSONException e) { e.printStackTrace(); } PrintWriter out =
		 * response.getWriter(); out.print(obj); out.flush(); out.close();
		 * 
		 */
		HttpSession session = request.getSession();
		if (session.getAttribute("user") == null) {
			response.setStatus(403);
			return;
		}

		JSONArray array = new JSONArray();
		DBConnection connection = new MySQLDBConnection();//
	//	DBConnection connection = new MongoDBConnection();

		// modified version, no need exception handling
		// exception handled in inner level, in searchRestaurants
		
		if (request.getParameterMap().containsKey("user_id") && request.getParameterMap().containsKey("lat")
				&& request.getParameterMap().containsKey("lon")) {
			String userId = request.getParameter("user_id");
			double lat = Double.parseDouble(request.getParameter("lat"));
			double lon = Double.parseDouble(request.getParameter("lon"));
			array = connection.searchRestaurants(userId, lat, lon);

		}

		RpcParser.writeOutput(response, array);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		if (session.getAttribute("user") == null) {
			response.setStatus(403);
			return;
		}
		doGet(request, response);
	}

}
