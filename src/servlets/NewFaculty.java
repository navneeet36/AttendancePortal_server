package servlets;

import java.io.IOException;
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
import pojo.BeanFacultyInfo;
import pojo.BeanLoginInfo;
import utils.Constants;

/**
 * Servlet implementation class NewFaculty
 */
@WebServlet("/NewFaculty")
public class NewFaculty extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewFaculty() {
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
		StringBuilder sb = new StringBuilder();
		 String  data_content=null;
		
		Map m = request.getParameterMap();
		Set s = m.entrySet();
		Iterator it = s.iterator();
		BeanFacultyInfo data;
		
		while (it.hasNext()) {

			Map.Entry<String, String[]> entry = (Map.Entry<String, String[]>) it.next();

			String key = entry.getKey();
			String[] value = entry.getValue();
			switch (key) {
			case Constants.data:
				data_content = value[0].toString();
				break;

			}

		}
		CacheConnection.setVerbose(true);

		// Get a cached connection
		java.sql.Connection connection = CacheConnection.checkOut("create");
		boolean b = false;
		try {
			data=gson.fromJson(data_content, BeanFacultyInfo.class);
			b = DataManager.insertFacultyInfo(connection, data);
			if (b) {
				JsonObject json = new JsonObject();
				json.addProperty("success", "1");
				json.addProperty("message", "account created");
				response.getWriter().write(json.toString());
			} else if (!b) {
				JsonObject json = new JsonObject();
				json.addProperty("success", "0");
				json.addProperty("message", "branch not inserted");
				response.getWriter().write(json.toString());
}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonObject json = new JsonObject();
			json.addProperty("success", "0");
			json.addProperty("message", "branch not inserted");
			response.getWriter().write(json.toString());

			e.printStackTrace();
		}

		CacheConnection.checkIn(connection);

	}


	}

