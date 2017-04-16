package servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.google.gson.reflect.TypeToken;

import database.CacheConnection;
import database.DataManager;
import pojo.BeanAttendance;
import pojo.BeanDates;
import pojo.BeanStudentSemInfo;
import utils.Constants;

/**
 * Servlet implementation class StartFaceAttendance
 */
@WebServlet("/StartFaceAttendance")
public class StartFaceAttendance extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public StartFaceAttendance() {
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
		String branchid = null, semno = null, fid = null,date=null, subjectid = null;

		Map m = request.getParameterMap();
		Set s = m.entrySet();
		Iterator it = s.iterator();

		while (it.hasNext()) {

			Map.Entry<String, String[]> entry = (Map.Entry<String, String[]>) it.next();

			String key = entry.getKey();
			String[] value = entry.getValue();
			switch (key) {
			case Constants.branchid:
				branchid = value[0].toString();
				break;
			case Constants.semno:
				semno = value[0].toString();
				break;
			case Constants.subjectid:
				subjectid = value[0].toString();
				break;
			case "fid":
				fid = value[0].toString();
				break;
			case "date":
				date= value[0].toString();
			}

		}
		CacheConnection.setVerbose(true);

		// Get a cached connection
		java.sql.Connection connection = CacheConnection.checkOut("create");
		try {
			System.out.println(subjectid+" "+semno+" "+branchid+" "+fid+" "+date);
			ArrayList<BeanStudentSemInfo> list = DataManager.receiveStudents(connection, branchid, semno, subjectid);
			BeanDates b = new BeanDates();
			b.setFacultyID(fid);
			b.setSubjectID(subjectid);
			ArrayList<BeanAttendance> arra = new ArrayList<BeanAttendance>();
			for (int j = 0; j < list.size(); j++) {
				BeanStudentSemInfo info = list.get(j);
				BeanAttendance b1 = new BeanAttendance();
				b1.setFacultyID(fid);
				b1.setRollNo(info.getRollNo());
				b1.setSubjectID(subjectid);
				b1.setBranchID(branchid);
				b1.setIsPresent("no");
				b1.setSemNo(semno);
				arra.add(b1);
			}
			boolean suc = DataManager.insertDates(connection, b);
			if(suc){
				for (BeanAttendance bean : arra)
					if (!DataManager.insertAttendance(connection, bean,true))
						suc = false;
			
			}
			
			if (suc) {
				ArrayList<BeanAttendance> att=DataManager.receiveAttendance(connection, fid, subjectid, date);
				JsonObject json = new JsonObject();
				json.addProperty("success", "1");
				json.addProperty("message", "success");
				json.addProperty("list", gson.toJson(att));
				response.getWriter().write(json.toString());

			} else {
				JsonObject json = new JsonObject();
				json.addProperty("success", "0");
				json.addProperty("message", "failed");
				response.getWriter().write(json.toString());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			JsonObject json = new JsonObject();
			json.addProperty("success", "0");
			json.addProperty("message", "Error");
			response.getWriter().write(json.toString());
		}
		CacheConnection.checkIn(connection);
	}

}
