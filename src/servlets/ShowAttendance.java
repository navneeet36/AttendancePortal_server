package servlets;

import java.io.IOException;
import java.util.ArrayList;
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
import pojo.BeanAttendance;
import pojo.BeanDates;
import utils.Constants;

/**
 * Servlet implementation class ShowAttendance
 */
@WebServlet("/ShowAttendance")
public class ShowAttendance extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowAttendance() {
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
		Gson gson = new Gson();
		StringBuilder sb = new StringBuilder();
		 String facultyid=null,subjectid=null,attendancedate=null;
		
		Map m = request.getParameterMap();
		Set s = m.entrySet();
		Iterator it = s.iterator();

		while (it.hasNext()) {

			Map.Entry<String, String[]> entry = (Map.Entry<String, String[]>) it.next();

			String key = entry.getKey();
			String[] value = entry.getValue();
			switch (key) {
			case Constants.facultyid:
				facultyid = value[0].toString();
				break;
			case Constants.subjectid:
				subjectid = value[0].toString();
				break;		
			case Constants.attendancedate:
				attendancedate=value[0].toString();
			
			}

		}
		CacheConnection.setVerbose(true);

		// Get a cached connection
		java.sql.Connection connection = CacheConnection.checkOut("create");
		try {
			ArrayList<BeanAttendance> list=DataManager.receiveAttendance(connection, facultyid, subjectid, attendancedate);
			int count=0;
			if(list.size()>count)
			{	
				JsonObject json = new JsonObject();
					json.addProperty("success", "1");
					json.addProperty("message", "attendance ");
						json.addProperty("attendance",gson.toJson(list));
					response.getWriter().write(json.toString());
				
			}
			else {
					JsonObject json = new JsonObject();
					json.addProperty("success", "0");
					json.addProperty("message", "No attendance list found for this faculty");
					response.getWriter().write(json.toString());
				}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonObject json = new JsonObject();
			json.addProperty("success", "0");
			json.addProperty("message", "No attendance list found for this faculty");
			response.getWriter().write(json.toString());
			e.printStackTrace();

			
			}
		CacheConnection.checkIn(connection);
	}

	
}

