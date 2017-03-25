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
import pojo.BeanLoginInfo;
import pojo.BeanStudentInfo;
import utils.Constants;

/**
 * Servlet implementation class NewStudent
 */
@WebServlet("/NewStudent")
public class NewStudent extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewStudent() {
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
		doGet(request, response);
		Gson gson = new Gson();
		StringBuilder sb = new StringBuilder();
		 String rollno=null,name=null,fathersname=null, mothersname=null, branchid=null,dob=null, admissiondate=null;
		
		Map m = request.getParameterMap();
		Set s = m.entrySet();
		Iterator it = s.iterator();

		while (it.hasNext()) {

			Map.Entry<String, String[]> entry = (Map.Entry<String, String[]>) it.next();

			String key = entry.getKey();
			String[] value = entry.getValue();
			switch (key) {
			case Constants.rollno:
				rollno = value[0].toString();
				break;
			case Constants.name:
				name = value[0].toString();
				break;
			case Constants.fathersname:
				fathersname = value[0].toString();
				break;
			case Constants.mothersname:
				mothersname = value[0].toString();
				break;
			case Constants.branchid:
			branchid = value[0].toString();
				break;
			case Constants.dob:
				dob = value[0].toString();
				break;
			case Constants.admissiondate:
				admissiondate = value[0].toString();
				break;
				
			
			}

		}
		CacheConnection.setVerbose(true);

		// Get a cached connection
		java.sql.Connection connection = CacheConnection.checkOut("create");
		boolean b = false;
		try {
			
			BeanStudentInfo data=new  BeanStudentInfo();
			 data.setRollNo(rollno);
		        data.setName(name);
		        data.setFathersName(fathersname);
		        data.setMothersName(mothersname);
		        data.setBranchID(branchid);
		        data.setDOB(dob);
		        data.setAdmissionDate(admissiondate);

			
			b = DataManager.insertStudentInfo(connection, data);
			if (b) {
				JsonObject json = new JsonObject();
				json.addProperty("success", "1");
				json.addProperty("message", "data inserted");
				response.getWriter().write(json.toString());
			} else if (!b) {
				JsonObject json = new JsonObject();
				json.addProperty("success", "0");
				json.addProperty("message", "data not inserted");
				response.getWriter().write(json.toString());

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JsonObject json = new JsonObject();
			json.addProperty("success", "0");
			json.addProperty("message", "data not inserted");
			response.getWriter().write(json.toString());

			e.printStackTrace();
		}

		CacheConnection.checkIn(connection);


	}

}
