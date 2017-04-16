package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import database.CacheConnection;
import database.DataManager;
import pojo.BeanAttendance;
import utils.Constants;
import utils.FaceUtils;

/**
 * Servlet implementation class InsertAttendance
 */
@WebServlet("/InsertAttendanceFace")
@MultipartConfig
public class InsertAttendanceFace extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public InsertAttendanceFace() {
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
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		StringBuilder sb = new StringBuilder();
		String data_content = null;

		Map m = request.getParameterMap();
		Set s = m.entrySet();
		Iterator it = s.iterator();
		BeanAttendance data;

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
		System.out.println(data_content);
		CacheConnection.setVerbose(true);	
		// Get a cached connection
		java.sql.Connection connection = CacheConnection.checkOut("create");
		try {
			boolean success = false;
			
			BeanAttendance att = gson.fromJson(data_content, BeanAttendance.class);
			String encodingFromDB = DataManager.getFaceEncoding(connection, att.getRollNo());

			System.out.println(encodingFromDB);
			if (encodingFromDB.equals("-1")) {
				JsonObject json = new JsonObject();
				json.addProperty("success", "1");
				json.addProperty("message", "Face not registered for " + att.getRollNo());
				response.getWriter().write(json.toString());
			} else if (FaceUtils.matchFace(request, encodingFromDB)) {
				if (DataManager.insertAttendance(connection, att,false))
					success = true;
				if (success) {
					JsonObject json = new JsonObject();
					json.addProperty("success", "1");
					json.addProperty("message", "Attendance marked for " + att.getRollNo());
					json.addProperty("data", gson.toJson(att));
					response.getWriter().write(json.toString());
				} else {
					JsonObject json = new JsonObject();
					json.addProperty("success", "0");
					json.addProperty("message", "Attendance not marked");
					response.getWriter().write(json.toString());
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonObject json = new JsonObject();
			json.addProperty("success", "0");
			json.addProperty("message", "Attendance not marked");
			response.getWriter().write(json.toString());
			e.printStackTrace();

		}

		CacheConnection.checkIn(connection);

	}

}
