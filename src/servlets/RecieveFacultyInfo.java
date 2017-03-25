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
import pojo.BeanFacultyInfo;
import pojo.BeanStudentInfo;

/**
 * Servlet implementation class RecieveFacultyInfo
 */
@WebServlet("/RecieveFacultyInfo")
public class RecieveFacultyInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RecieveFacultyInfo() {
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
		String facultyid = null;
		while (it.hasNext()) {

			Map.Entry<String, String[]> entry = (Map.Entry<String, String[]>) it.next();

			String key = entry.getKey();
			String[] value = entry.getValue();
			switch (key) {
			case "facultyid":
				facultyid = value[0].toString();
				break;
				
		
			}
		}
		CacheConnection.setVerbose(true);

		// Get a cached connection
		Connection connection = CacheConnection.checkOut("GetResult");
		try {
			BeanFacultyInfo list=DataManager.receiveFacultyInfo(connection, facultyid);
		
			if(list!=null)
			{		JsonObject json = new JsonObject();
					json.addProperty("success", "1");
					json.addProperty("message", "faculty info");
					json.addProperty("faculty info", gson.toJson(list));
				

					response.getWriter().write(json.toString());
				} else {
					JsonObject json = new JsonObject();
					json.addProperty("success", "0");
					json.addProperty("message", "No user found");
					response.getWriter().write(json.toString());
				}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JsonObject json = new JsonObject();
			json.addProperty("success", "0");
			json.addProperty("message", "No user found");
			response.getWriter().write(json.toString());
		}
		CacheConnection.checkIn(connection);
	}

	

}

