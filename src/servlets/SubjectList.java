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

import pojo.BeanSubjectInfo;

/**
 * Servlet implementation class SubjectList
 */
@WebServlet("/SubjectList")
public class SubjectList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubjectList() {
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
		
		
		Map m = request.getParameterMap();
		Set s = m.entrySet();
		Iterator it = s.iterator();

	
		CacheConnection.setVerbose(true);

		// Get a cached connection
		java.sql.Connection connection = CacheConnection.checkOut("create");
		try {
			ArrayList<BeanSubjectInfo> list=DataManager.Subjectlist(connection);
			int count=0;
			if(list.size()>count)
			{	
				JsonObject json = new JsonObject();
					json.addProperty("success", "1");
					json.addProperty("message", "subject list is found ");
						json.addProperty("subject_list",gson.toJson(list));
					response.getWriter().write(json.toString());
				
			}
			else {
					JsonObject json = new JsonObject();
					json.addProperty("success", "0");
					json.addProperty("message", "No subject list found ");
					response.getWriter().write(json.toString());
				}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			JsonObject json = new JsonObject();
			json.addProperty("success", "0");
			json.addProperty("message", "No subject list found ");
			response.getWriter().write(json.toString());
			}
		CacheConnection.checkIn(connection);
	}

	

}