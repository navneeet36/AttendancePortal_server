package utils;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.apache.tomcat.util.http.fileupload.IOUtils;

public class FaceUtils {
	public static String getEncoding(HttpServletRequest request) throws IOException, ServletException {
		//For registering face for the first time
		Part filePart = request.getPart("file");
		String fileName = filePart.getSubmittedFileName();
		InputStream fileContent = filePart.getInputStream();
		FileOutputStream f = new FileOutputStream("/home/arpitkh96/python/" + fileName);
		IOUtils.copy(fileContent, f);
		String path = "/home/arpitkh96/python/";
		final Process p = Runtime.getRuntime().exec("python3 " + path + "generate_encoding.py " + path + fileName);
		BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line = null;
			String s = "[";
			try {
				while ((line = input.readLine()) != null) {
					s += ((line)) + ",";
				}
				s = s.substring(0, s.length() - 2);
				s = s + "]";
			} catch (IOException e) {
				e.printStackTrace();
			}
			int suc;
			try {
				suc=p.waitFor();
			} catch (InterruptedException e) {
				e.printStackTrace();
				suc=-1;
			}
			if(suc!=0)s="-1";
			return s;
	}
	public static boolean matchFace(HttpServletRequest request,String encodingFromDB) throws IOException, ServletException{
		Part filePart = request.getPart("file");
		String fileName = filePart.getSubmittedFileName();
		InputStream fileContent = filePart.getInputStream();
		FileOutputStream f = new FileOutputStream("/home/arpitkh96/python/" + fileName);
		IOUtils.copy(fileContent, f);
		String path = "/home/arpitkh96/python/";
		String comm[]=new String[]{"python3", path + "encoding_matcher.py" ,encodingFromDB,path + fileName};
		final Process p = Runtime.getRuntime().exec(comm);
		BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
		BufferedReader error = new BufferedReader(new InputStreamReader(p.getErrorStream()));
		String line = null;

			String s="";
			try {
				while ((line = input.readLine()) != null) {
					s += ((line))+"\n";
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			System.out.println(s);
			String s1="";
			try {
				while ((line = error.readLine()) != null) {
					s1 += ((line))+"\n";
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println(s1);
			try {
				p.waitFor();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return s.contains("It's a picture of me!");
		
	}
}
