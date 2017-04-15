package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import pojo.BeanAttendance;
import pojo.BeanBranchInfo;
import pojo.BeanDates;
import pojo.BeanFacultyInfo;
import pojo.BeanFacultySemInfo;
import pojo.BeanLoginInfo;
import pojo.BeanStudentInfo;
import pojo.BeanStudentSemInfo;
import pojo.BeanSubjectInfo;
import pojo.BeanTable;

public class DataManager {
	public static boolean insertLoginInfo(Connection connection, BeanLoginInfo data) {
		String strInsert = "Insert into LoginInfo values (?,?,?,?,?,?,?,?) ";
		try {
			PreparedStatement ps = null;
			ps = connection.prepareStatement(strInsert.toLowerCase());
			ps.setString(1, data.getRollNo());
			ps.setString(2, data.getFacultyID());
			ps.setString(3, data.getUserName());
			ps.setString(4, data.getPassword());
			ps.setString(5, data.getRoleName());
			ps.setString(6, data.getEmailID());
			ps.setString(7, data.getSecurityQuestion());
			ps.setString(8, data.getSecurityAnswer());

			int i = ps.executeUpdate();

			if (i > 0)
				return true;
			else
				return false;
		}

		catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public static boolean insertBranch(Connection connection, BeanBranchInfo data) {
		String strInsert = "Insert into BranchInfo values(?,?,?)";
		try {
			PreparedStatement ps = connection.prepareStatement(strInsert.toLowerCase());

			ps.setString(1, data.getBranchID());
			ps.setString(2, data.getBranchName());
			ps.setString(3, data.getTotalSem());

			int i = ps.executeUpdate();

			if (i > 0)
				return true;
			else
				return false;
		} catch (Exception ex) {
			ex.printStackTrace();

			return false;
		}

	}

	public static BeanBranchInfo searchBranch(Connection connection, String BranchID) {
		String strSearch = "select * from BranchInfo where BranchID=?";
		try {
			PreparedStatement ps = connection.prepareStatement(strSearch.toLowerCase());

			ps.setString(1, BranchID);

			BeanBranchInfo data = null;
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				data = new BeanBranchInfo();
				data.setBranchName(rs.getString(2));
				data.setTotalSem(rs.getString(3));
			}
			return data;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}

	}

	public static boolean updateBranch(Connection connection, BeanBranchInfo data) {
		String strUpdate = "update BranchInfo set BranchName=?,TotalSem=? where BranchID=?";
		try {
			PreparedStatement ps = connection.prepareStatement(strUpdate.toLowerCase());

			ps.setString(1, data.getBranchName());
			ps.setString(2, data.getTotalSem());
			ps.setString(3, data.getBranchID());

			int i = ps.executeUpdate();

			if (i > 0)
				return true;
			else
				return false;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public static boolean insertDates(Connection connection, BeanDates data) {

		String strInsert = "Insert into dates values(now(),?,?)";

		try {
			PreparedStatement ps = connection.prepareStatement(strInsert.toLowerCase());

			ps.setString(1, data.getFacultyID());
			ps.setString(2, data.getSubjectID());

			int i = ps.executeUpdate();
			if (i > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public static boolean insertFacultyInfo(Connection connection, BeanFacultyInfo data) {
		String strInsert = "Insert into FacultyInfo values (?,?,?,?,?,?,?,?)";

		try {
			PreparedStatement ps = connection.prepareStatement(strInsert.toLowerCase());
			ps.setString(1, data.getFacultyID());
			ps.setString(2, data.getFacultyName());
			ps.setString(3, data.getQualification());
			ps.setString(4, data.getDOB());
			ps.setString(5, data.getFathersName());
			ps.setString(6, data.getWorkingDate());
			ps.setString(7, data.getMothersName());
			ps.setString(8, data.getGender());

			int i = ps.executeUpdate();

			if (i > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public static boolean insertStudentInfo(Connection connection, BeanStudentInfo data) {
		String strInsert = "Insert into StudentInfo values(?,?,?,?,?,?,?)";

		try {
			PreparedStatement ps = connection.prepareStatement(strInsert.toLowerCase());

			ps.setString(1, data.getRollNo());
			ps.setString(2, data.getName());
			ps.setString(3, data.getFathersName());
			ps.setString(4, data.getMothersName());
			ps.setString(5, data.getBranchID());
			ps.setString(6, data.getDOB());
			ps.setString(7, data.getAdmissionDate());

			int i = ps.executeUpdate();
			if (i > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public static ArrayList<BeanStudentSemInfo> receiveStudents(Connection connection, String BranchID, String SemNo,
			String SubjectID) {
		String strReceive = "select * from StudentSemInfo where BranchID=? and SemNo=? and SubjectID=?";
		// String strReceive="select * from StudentSemInfo";

		try {
			PreparedStatement ps = connection.prepareStatement(strReceive.toLowerCase());

			ps.setString(1, BranchID);
			ps.setString(2, SemNo);
			ps.setString(3, SubjectID);

			ResultSet rs = ps.executeQuery();

			ArrayList<BeanStudentSemInfo> list = null;
			if (rs.next()) {
				rs.beforeFirst();
				list = new ArrayList();

				while (rs.next()) {
					BeanStudentSemInfo data = new BeanStudentSemInfo();

					data.setRollNo(rs.getString(1));
					data.setBranchID(rs.getString(2));
					data.setSemNo(rs.getString(3));
					data.setSubjectID(rs.getString(5));

					list.add(data);
				}
			}
			return list;
		}

		catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}

	}

	public static boolean insertAttendance(Connection connection, BeanAttendance data) {
		String strInsert = "insert into StudentAttendance values (?,?,now(),?,?,?,?)";
		try {
			PreparedStatement ps = connection.prepareStatement(strInsert.toLowerCase());

			ps.setString(1, data.getRollNo());
			ps.setString(2, data.getFacultyID());

			ps.setString(3, data.getIsPresent());
			ps.setString(4, data.getSubjectID());
			ps.setString(5, data.getBranchID());
			ps.setString(6, data.getSemNo());

			int i = ps.executeUpdate();
			if (i > 0) {
				return true;
			} else {
				return false;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}

	}

	public static BeanFacultyInfo receiveFacultyInfo(Connection connection, String FacultyID) {
		String strSelect = "select * from FacultyInfo where FacultyID=? ";

		try {
			PreparedStatement ps = connection.prepareStatement(strSelect.toLowerCase());
			ps.setString(1, FacultyID);

			BeanFacultyInfo data = null;
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {

				data = new BeanFacultyInfo();
				data.setFacultyID(rs.getString(1));
				data.setFacultyName(rs.getString(2));
				data.setQualification(rs.getString(3));
				data.setDOB(rs.getString(4));
				data.setFathersName(rs.getString(5));
				data.setWorkingDate(rs.getString(6));
				data.setMothersName(rs.getString(7));
				data.setGender(rs.getString(8));

			}
			return data;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}

	}

	public static BeanFacultySemInfo receiveFacultySemInfo(Connection connection, String FacultyID) {
		String strSelect = "select * from FacultySemInfo where FacultyID=? ";

		try {
			PreparedStatement ps = connection.prepareStatement(strSelect.toLowerCase());
			ps.setString(1, FacultyID);

			BeanFacultySemInfo data = null;
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				data = new BeanFacultySemInfo();
				data.setFacultyID(rs.getString(1));
				data.setIsTeaching(rs.getString(4));

			}
			return data;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}

	}

	public static ArrayList<BeanDates> receiveDates(Connection connection, String SubjectID, String FacultyID) {
		String strReceive = "select * from dates where subjectID=? and FacultyID=?";
		try {
			PreparedStatement ps = connection.prepareStatement(strReceive.toLowerCase());
			ps.setString(1, SubjectID);
			ps.setString(2, FacultyID);

			ResultSet rs = ps.executeQuery();

			ArrayList<BeanDates> list = null;
			if (rs.next()) {
				rs.beforeFirst();
				list = new ArrayList();

				while (rs.next()) {
					BeanDates data = new BeanDates();

					data.setAttendanceDate(rs.getString(1));

					list.add(data);
				}
			}
			return list;
		}

		catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}

	}

	public static ArrayList<BeanFacultySemInfo> receiveSubjects(Connection connection, String FacultyID) {
		String strReceive = "select * from facultyseminfo where FacultyID=?";
		try {
			PreparedStatement ps = connection.prepareStatement(strReceive.toLowerCase());

			ps.setString(1, FacultyID);

			ResultSet rs = ps.executeQuery();

			ArrayList<BeanFacultySemInfo> list = null;
			if (rs.next()) {
				rs.beforeFirst();
				list = new ArrayList();

				while (rs.next()) {
					BeanFacultySemInfo data = new BeanFacultySemInfo();

					data.setSubjectID(rs.getString(2));

					list.add(data);
				}
			}
			return list;
		}

		catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}

	}

	public static ArrayList<BeanSubjectInfo> receiveSubjects(Connection connection) {
		String strReceive = "select * from SubjectInfo";
		try {
			PreparedStatement ps = connection.prepareStatement(strReceive.toLowerCase());

			ResultSet rs = ps.executeQuery();

			ArrayList<BeanSubjectInfo> list = null;
			if (rs.next()) {
				rs.beforeFirst();
				list = new ArrayList();

				while (rs.next()) {
					BeanSubjectInfo data = new BeanSubjectInfo();

					data.setSubjectName(rs.getString(2));
					data.setSubjectID(rs.getString(1));
					data.setBranchID(rs.getString(3));
					data.setSemNo(rs.getString(4));

					list.add(data);
				}
			}
			return list;
		}

		catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}

	}

	public static boolean insertFacultySemInfo(Connection connection, BeanFacultySemInfo data) {
		String strInsert = "insert into FacultySemInfo(FacultyID,SubjectID,SemNo,IsTeaching,BranchID) values (?,?,?,?,?)";
		try {
			PreparedStatement ps = connection.prepareStatement(strInsert.toLowerCase());

			ps.setString(1, data.getFacultyID());
			ps.setString(2, data.getSubjectID());
			ps.setString(3, data.getSemNo());
			ps.setString(4, data.getIsTeaching());
			ps.setString(5, data.getBranchID());

			int i = ps.executeUpdate();
			if (i > 0) {
				return true;
			} else {
				return false;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public static BeanLoginInfo receiveData(Connection connection, String uname) {
		String strReceive = "select * from logininfo where UserName=?";

		try {
			PreparedStatement ps = connection.prepareStatement(strReceive.toLowerCase());

			ps.setString(1, uname);

			BeanLoginInfo data = null;
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				data = new BeanLoginInfo();

				data.setSecurityQuestion(rs.getString(7));
				data.setSecurityAnswer(rs.getString(8));

			}
			return data;

		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}

	}

	public static boolean updatePass(Connection connection, String pass, String uname) {
		String strUpdate = "update LoginInfo set Password=? where UserName=?";
		try {
			PreparedStatement ps = connection.prepareStatement(strUpdate.toLowerCase());

			ps.setString(1, pass);
			ps.setString(2, uname);

			int i = ps.executeUpdate();

			if (i > 0)
				return true;
			else
				return false;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}

	}

	public static BeanLoginInfo checkLogin(Connection connection, String UserName, String Password) {
		String strSelect = "select * from LoginInfo where UserName=? and Password=? ";

		try {
			PreparedStatement ps = connection.prepareStatement(strSelect.toLowerCase());
			ps.setString(1, UserName);
			ps.setString(2, Password);

			BeanLoginInfo data = null;
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				data = new BeanLoginInfo();
				data.setRoleName(rs.getString(5));
				data.setEmailID(rs.getString(6));
				data.setSecurityQuestion(rs.getString(7));
				data.setSecurityAnswer(rs.getString(8));

				if (data.getRoleName().equalsIgnoreCase("faculty")) {
					data.setFacultyID(rs.getString(2).toString());
				}

				else if (data.getRoleName().equalsIgnoreCase("student")) {
					data.setRollNo(rs.getString(1));
				}

			}
			return data;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static BeanStudentInfo receiveStudentInfo(Connection connection, String RollNo) {
		String strSelect = "select * from StudentInfo where RollNo=? ";

		try {
			PreparedStatement ps = connection.prepareStatement(strSelect.toLowerCase());
			ps.setString(1, RollNo);

			BeanStudentInfo data = null;
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				data = new BeanStudentInfo();
				data.setRollNo(rs.getString(1));
				data.setName(rs.getString(2));
				data.setFathersName(rs.getString(3));
				data.setMothersName(rs.getString(4));
				data.setBranchID(rs.getString(5));
				data.setDOB(rs.getString(6));
				data.setAdmissionDate(rs.getString(7));

			}
			return data;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}

	}

	public static BeanStudentSemInfo receiveStudentSemInfo(Connection connection, String RollNo) {
		String strSelect = "select * from StudentSemInfo where RollNo=? ";

		try {
			PreparedStatement ps = connection.prepareStatement(strSelect.toLowerCase());
			ps.setString(1, RollNo);

			BeanStudentSemInfo data = null;
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				data = new BeanStudentSemInfo();
				data.setRollNo(rs.getString(1));
				data.setBranchID(rs.getString(2));
				data.setSemNo(rs.getString(3));
				data.setIsActive(rs.getString(4));

			}
			return data;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}

	}

	public static ArrayList<BeanStudentSemInfo> receiveSubject(Connection connection, String RollNo) {
		String strReceive = "select * from StudentSemInfo where RollNo=?";
		try {
			PreparedStatement ps = connection.prepareStatement(strReceive.toLowerCase());
			ps.setString(1, RollNo);

			ResultSet rs = ps.executeQuery();

			ArrayList<BeanStudentSemInfo> list = null;
			if (rs.next()) {
				rs.beforeFirst();
				list = new ArrayList();

				while (rs.next()) {
					BeanStudentSemInfo data = new BeanStudentSemInfo();

					data.setSubjectID(rs.getString(5));

					list.add(data);
				}
			}
			return list;
		}

		catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static ArrayList<BeanSubjectInfo> receiveSubjects(Connection connection, String BranchID, String SemNo) {
		String strReceive = "select * from SubjectInfo where BranchID=? and SemNo=?";
		try {
			PreparedStatement ps = connection.prepareStatement(strReceive.toLowerCase());

			ps.setString(1, BranchID);
			ps.setString(2, SemNo);

			ResultSet rs = ps.executeQuery();

			ArrayList<BeanSubjectInfo> list = null;
			if (rs.next()) {
				rs.beforeFirst();
				list = new ArrayList();

				while (rs.next()) {
					BeanSubjectInfo data = new BeanSubjectInfo();

					data.setSubjectName(rs.getString(2));
					data.setSubjectID(rs.getString(1));

					list.add(data);
				}
			}
			return list;
		}

		catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}

	}

	public static boolean insertStudentSemInfo(Connection connection, BeanStudentSemInfo data) {
		String strInsert = "insert into StudentSemInfo(RollNo,BranchID,SemNo,IsActive,SubjectID) values (?,?,?,?,?)";
		try {
			PreparedStatement ps = connection.prepareStatement(strInsert.toLowerCase());

			ps.setString(1, data.getRollNo());
			ps.setString(2, data.getBranchID());
			ps.setString(3, data.getSemNo());
			ps.setString(4, data.getIsActive());
			ps.setString(5, data.getSubjectID());

			int i = ps.executeUpdate();
			if (i > 0) {
				return true;
			} else {
				return false;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}

	}

	public static boolean insertSubject(Connection connection, BeanSubjectInfo data) {
		String strInsert = "Insert into SubjectInfo values (?,?,?,?)";

		try {
			PreparedStatement ps = connection.prepareStatement(strInsert.toLowerCase());
			ps.setString(1, data.getSubjectID());
			ps.setString(2, data.getSubjectName());
			ps.setString(3, data.getBranchID());
			ps.setString(4, data.getSemNo());

			int i = ps.executeUpdate();

			if (i > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;

		}

	}

	public static BeanSubjectInfo searchSubject(Connection connection, String SubjectID) {
		String strSearch = "select * from subjectinfo where SubjectID=?";

		try {
			PreparedStatement ps = connection.prepareStatement(strSearch.toLowerCase());
			ps.setString(1, SubjectID);

			ResultSet rs = ps.executeQuery();
			BeanSubjectInfo data = null;
			if (rs.next()) {
				data = new BeanSubjectInfo();
				data.setSubjectName(rs.getString(2));
				data.setBranchID(rs.getString(3));
				data.setSemNo(rs.getString(4));
			}

			return data;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;

		}

	}

	public static boolean updateSubject(Connection connection, BeanSubjectInfo data) {
		String strUpdate = "update SubjectInfo set SubjectName=?,BranchID=?,SemNo=? where SubjectID=?";

		try {
			PreparedStatement ps = connection.prepareStatement(strUpdate.toLowerCase());

			ps.setString(1, data.getSubjectName());
			ps.setString(2, data.getBranchID());
			ps.setString(3, data.getSemNo());
			ps.setString(4, data.getSubjectID());

			int i = ps.executeUpdate();

			if (i > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;

		}

	}

	public static boolean deleteSubject(Connection connection, String SubjectID) {
		String strUpdate = "delete from SubjectInfo where SubjectID=?";

		try {
			PreparedStatement ps = connection.prepareStatement(strUpdate.toLowerCase());

			ps.setString(1, SubjectID);

			int i = ps.executeUpdate();

			if (i > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;

		}

	}

	public static BeanFacultyInfo searchFacultyRecord(Connection connection, String FacultyID) {
		String strSearch = "select * from FacultyInfo where FacultyID=?";
		try {
			PreparedStatement ps = connection.prepareStatement(strSearch.toLowerCase());

			ps.setString(1, FacultyID);

			BeanFacultyInfo data = null;
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				data = new BeanFacultyInfo();
				data.setFacultyName(rs.getString(2));
				data.setQualification(rs.getString(3));
				data.setDOB(rs.getString(4));
				data.setFathersName(rs.getString(5));
				data.setWorkingDate(rs.getString(6));
				data.setMothersName(rs.getString(7));
				data.setGender(rs.getString(8));
			}
			return data;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}

	}

	public static boolean updateFacultyRecord(Connection connection, BeanFacultyInfo data) {
		String strUpdate = "update FacultyInfo set FacultyName=?,Qualification=?,DOB=?,FathersName=?,WorkingDate=?,MothersName=?,Gender=? where FacultyID=?";
		try {
			PreparedStatement ps = connection.prepareStatement(strUpdate.toLowerCase());

			ps.setString(1, data.getFacultyName());
			ps.setString(2, data.getQualification());
			ps.setString(3, data.getDOB());
			ps.setString(4, data.getFathersName());
			ps.setString(5, data.getWorkingDate());
			ps.setString(6, data.getMothersName());
			ps.setString(7, data.getGender());
			ps.setString(8, data.getFacultyID());

			int i = ps.executeUpdate();

			if (i > 0)
				return true;
			else
				return false;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}

	}

	public static BeanStudentInfo searchStudentRecord(Connection connection, String RollNo) {
		String strSearch = "select * from StudentInfo where RollNo=?";
		try {
			PreparedStatement ps = connection.prepareStatement(strSearch.toLowerCase());

			ps.setString(1, RollNo);

			BeanStudentInfo data = null;
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				data = new BeanStudentInfo();
				data.setName(rs.getString(2));
				data.setFathersName(rs.getString(3));
				data.setMothersName(rs.getString(4));
				data.setBranchID(rs.getString(5));
				data.setDOB(rs.getString(6));
				data.setAdmissionDate(rs.getString(7));
			}
			return data;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}

	}

	public static boolean updateStudentRecord(Connection connection, BeanStudentInfo data) {
		String strUpdate = "update StudentInfo set Name=?,FathersName=?,MothersName=?,BranchID=?,DOB=?,AdmissionDate=? where RollNo=?";
		try {
			PreparedStatement ps = connection.prepareStatement(strUpdate.toLowerCase());

			ps.setString(1, data.getName());
			ps.setString(2, data.getFathersName());
			ps.setString(3, data.getMothersName());
			ps.setString(4, data.getBranchID());
			ps.setString(5, data.getDOB());
			ps.setString(6, data.getAdmissionDate());
			ps.setString(7, data.getRollNo());

			int i = ps.executeUpdate();

			if (i > 0)
				return true;
			else
				return false;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}

	}

	public static boolean deleteFaculty(Connection connection, String facultyid) {

		String strUpdate = "delete from FacultyInfo where FacultyID=?";

		try {
			PreparedStatement ps = connection.prepareStatement(strUpdate.toLowerCase());

			ps.setString(1, facultyid);

			int i = ps.executeUpdate();

			if (i > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;

		}

	}

	public static boolean deleteFacultySemInfo(Connection connection, String facultyid) {

		String strUpdate = "delete from FacultySemInfo where FacultyID=?";

		try {
			PreparedStatement ps = connection.prepareStatement(strUpdate.toLowerCase());

			ps.setString(1, facultyid);

			int i = ps.executeUpdate();

			if (i > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;

		}

	}

	public static boolean deleteStudent(Connection connection, String rollno) {

		String strUpdate = "delete from StudentInfo where RollNo=?";

		try {
			PreparedStatement ps = connection.prepareStatement(strUpdate.toLowerCase());

			ps.setString(1, rollno);

			int i = ps.executeUpdate();

			if (i > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;

		}
	}

	public static boolean deleteStudentSemInfo(Connection connection, String rollno) {

		String strUpdate = "delete from StudentSemInfo where RollNo=?";

		try {
			PreparedStatement ps = connection.prepareStatement(strUpdate.toLowerCase());

			ps.setString(1, rollno);

			int i = ps.executeUpdate();

			if (i > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;

		}
	}

	public static boolean deleteBranch(Connection connection, String branchid) {

		String strUpdate = "delete from BranchInfo where BranchID=?";

		try {
			PreparedStatement ps = connection.prepareStatement(strUpdate.toLowerCase());

			ps.setString(1, branchid);

			int i = ps.executeUpdate();

			if (i > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;

		}
	}

	public static ArrayList<BeanBranchInfo> Branchlist(Connection connection) {

		try {
			String strSelect = "select BranchID,BranchName,TotalSem  from BranchInfo";
			PreparedStatement ps = connection.prepareStatement(strSelect.toLowerCase());
			ResultSet rs = ps.executeQuery();
			ArrayList<BeanBranchInfo> list = null;
			if (rs.next()) {
				rs.beforeFirst();
				list = new ArrayList<>();
				while (rs.next()) {
					BeanBranchInfo data = new BeanBranchInfo();

					data.setBranchID(rs.getString(1));
					data.setBranchName(rs.getString(2));
					data.setTotalSem(rs.getString(3));
					list.add(data);
				}
			}
			return list;
		}

		catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}

	}

	public static ArrayList<BeanSubjectInfo> Subjectlist(Connection connection) {

		try {
			String strSelect = "select SubjectID,SubjectName,BranchID,SemNo  from SubjectInfo";
			PreparedStatement ps = connection.prepareStatement(strSelect.toLowerCase());
			ResultSet rs = ps.executeQuery();
			ArrayList<BeanSubjectInfo> list = null;
			if (rs.next()) {
				rs.beforeFirst();
				list = new ArrayList<>();
				while (rs.next()) {
					BeanSubjectInfo data = new BeanSubjectInfo();

					data.setBranchID(rs.getString(3));
					data.setSubjectName(rs.getString(2));
					data.setSemNo(rs.getString(4));
					data.setSubjectID(rs.getString(1));
					list.add(data);
				}
			}
			return list;
		}

		catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}

	}

	public static ArrayList<BeanStudentInfo> Studentlist(Connection connection) {

		try {
			String strSelect = "select RollNo,Name,BranchID  from studentinfo";
			PreparedStatement ps = connection.prepareStatement(strSelect.toLowerCase());
			ResultSet rs = ps.executeQuery();
			ArrayList<BeanStudentInfo> list = null;
			if (rs.next()) {
				rs.beforeFirst();
				list = new ArrayList<>();
				while (rs.next()) {
					BeanStudentInfo data = new BeanStudentInfo();

					data.setBranchID(rs.getString(3));
					data.setName(rs.getString(2));
					data.setRollNo(rs.getString(1));
					list.add(data);
				}
			}
			return list;
		}

		catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}

	}

	public static ArrayList<BeanFacultyInfo> Facultylist(Connection connection) {

		try {
			String strSelect = "select FacultyID,FacultyName from facultyinfo";
			PreparedStatement ps = connection.prepareStatement(strSelect.toLowerCase());
			ResultSet rs = ps.executeQuery();
			ArrayList<BeanFacultyInfo> list = null;
			if (rs.next()) {
				rs.beforeFirst();
				list = new ArrayList<>();
				while (rs.next()) {
					BeanFacultyInfo data = new BeanFacultyInfo();

					data.setFacultyName(rs.getString(2));
					data.setFacultyID(rs.getString(1));
					list.add(data);
				}
			}
			return list;
		}

		catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}

	}

	public static ArrayList<BeanAttendance> receiveAttendance(Connection connection, String FacultyID, String SubjectID,
			String AttendanceDate) {
		String strSelect = "select RollNo,IsPresent  from studentattendance where FacultyID=? and SubjectID=? and AttendanceDate=?";
		try {
			PreparedStatement ps = connection.prepareStatement(strSelect.toLowerCase());

			ps.setString(1, FacultyID);
			ps.setString(2, SubjectID);
			ps.setString(3, AttendanceDate);

			ResultSet rs = ps.executeQuery();

			ArrayList<BeanAttendance> list = null;
			if (rs.next()) {
				rs.beforeFirst();
				list = new ArrayList();

				while (rs.next()) {
					BeanAttendance data = new BeanAttendance();
					data.setRollNo(rs.getString(1));
					data.setIsPresent(rs.getString(2));
					list.add(data);
				}
			}
			return list;
		}

		catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}

	}

	public static ArrayList<BeanAttendance> receivesubjectAttendance(Connection connection, String SubjectID,
			String rollno) {
		String strSelect = "select AttendanceDate,IsPresent from studentattendance where RollNo=? and SubjectID=?";
		try {
			PreparedStatement ps = connection.prepareStatement(strSelect.toLowerCase());

			ps.setString(1, rollno);
			ps.setString(2, SubjectID);

			ResultSet rs = ps.executeQuery();

			ArrayList<BeanAttendance> list = null;
			if (rs.next()) {
				rs.beforeFirst();
				list = new ArrayList();

				while (rs.next()) {
					BeanAttendance data = new BeanAttendance();
					data.setAttendanceDate(rs.getString(1));
					data.setIsPresent(rs.getString(2));
					list.add(data);
				}
			}
			return list;
		}

		catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static ArrayList<BeanTable> receivetable2(Connection connection, String rollno) {

		String strSelect = "SELECT distinct SUBJECTID ,(select COUNT(ISPRESENT) from studentattendance where rollno=? and subjectid = sa.subjectid )  as TotalLectures,(select COUNT(ISPRESENT) from studentattendance where rollno=? and subjectid = sa.subjectid and Ispresent='Yes')  as Present from studentattendance sa where rollno=?";

		try {
			PreparedStatement ps = connection.prepareStatement(strSelect.toLowerCase());

			ps.setString(1, rollno);
			ps.setString(2, rollno);
			ps.setString(3, rollno);

			ResultSet rs = ps.executeQuery();

			ArrayList<BeanTable> list = null;
			if (rs.next()) {
				rs.beforeFirst();
				list = new ArrayList();

				while (rs.next()) {
					BeanTable data = new BeanTable();
					data.setSubjectId(rs.getString(1));
					data.setTotalLecture(rs.getString(2));
					data.setIsPresent(rs.getString(3));
					list.add(data);
				}
			}
			return list;
		}

		catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}

	}

	public static String getFaceEncoding(Connection connection, String rollno) {
		String strSearch = "select * from face_info where roll_no=?";
		try {
			PreparedStatement ps = connection.prepareStatement(strSearch.toLowerCase());
			ps.setString(1, rollno);
			ResultSet rs = ps.executeQuery();
			String data = "-1";
			if (rs.next()) {
				data = rs.getString(2);
			}
			return data;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	public static void addFaceEncoding(Connection connection, String rollno,String encoding) throws Exception {
		String strSearch = "insert into face_info values (?,?)";
		try {
			PreparedStatement ps = connection.prepareStatement(strSearch.toLowerCase());
			ps.setString(1, rollno);
			ps.setString(2, encoding);
			int rs = ps.executeUpdate();
			
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}
}
