package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import database.CacheConnection;
import database.DataManager;
import pojo.BeanBranchInfo;
import pojo.BeanLoginInfo;
import utils.Constants;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		Map m = request.getParameterMap();
		Set s = m.entrySet();
		Iterator it = s.iterator();
		String username = null,password=null;//krle ab
		while (it.hasNext()) {

			Map.Entry<String, String[]> entry = (Map.Entry<String, String[]>) it.next();

			String key = entry.getKey();
			String[] value = entry.getValue();
			switch (key) {
			case Constants.username:
				username = value[0].toString();
				break;
				
		case "password":
			password = value[0].toString();
			break;
			}
		}
		CacheConnection.setVerbose(true);

		// Get a cached connection
		Connection connection = CacheConnection.checkOut("GetResult");
		try {
			BeanLoginInfo list=DataManager.checkLogin(connection, username, password);
			if(list!=null)
			{		JsonObject json = new JsonObject();
					json.addProperty("success", "1");
					json.addProperty("message", "Logged in");
					json.addProperty("ot", gson.toJson(list));
				

					response.getWriter().write(json.toString());
				} else {
					JsonObject json = new JsonObject();
					json.addProperty("success", "0");
					json.addProperty("message", "Invalid username/password");
					response.getWriter().write(json.toString());
				}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonObject json = new JsonObject();
			json.addProperty("success", "0");
			json.addProperty("message", "Invalid username/password");
			response.getWriter().write(json.toString());

			e.printStackTrace();
		}
		CacheConnection.checkIn(connection);
	}

	

}

