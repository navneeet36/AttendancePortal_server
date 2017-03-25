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
import pojo.BeanStudentInfo;

/**
 * Servlet implementation class SearchStudent
 */
@WebServlet("/SearchStudent")
public class SearchStudent extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchStudent() {
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
		String rollno = null;
		while (it.hasNext()) {

			Map.Entry<String, String[]> entry = (Map.Entry<String, String[]>) it.next();

			String key = entry.getKey();
			String[] value = entry.getValue();
			switch (key) {
			case "rollno":
				rollno = value[0].toString();
				break;
				}
		}
		CacheConnection.setVerbose(true);

		// Get a cached connection
		Connection connection = CacheConnection.checkOut("GetResult");
		try {
			BeanStudentInfo list=DataManager.searchStudentRecord(connection, rollno);
			if(list!=null)
			{		JsonObject json = new JsonObject();
					json.addProperty("success", "1");
					json.addProperty("message", "results successfully fetched");
					json.addProperty("name", list.getName());
					json.addProperty("mother name", list.getMothersName());
					json.addProperty("father name", list.getFathersName());
					json.addProperty("dob", list.getDOB());
					json.addProperty("admission date", list.getAdmissionDate());
					json.addProperty("branch id", list.getBranchID());
					

					response.getWriter().write(json.toString());
				} else {
					JsonObject json = new JsonObject();
					json.addProperty("success", "0");
					json.addProperty("message", "No results found");
					response.getWriter().write(json.toString());
				}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JsonObject json = new JsonObject();
			json.addProperty("success", "0");
			json.addProperty("message", "No results found");
			response.getWriter().write(json.toString());
		}
		CacheConnection.checkIn(connection);
	}

	

}


