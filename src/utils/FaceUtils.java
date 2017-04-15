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
	String getEncoding(HttpServletRequest request) throws IOException, ServletException {
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
		if (p.exitValue() == 1) {
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
			try {
				p.waitFor();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return s;
		} else
			return "-1";
	}
	boolean matchFace(HttpServletRequest request,String encodingFromDB) throws IOException, ServletException{
		Part filePart = request.getPart("file");
		String fileName = filePart.getSubmittedFileName();
		InputStream fileContent = filePart.getInputStream();
		FileOutputStream f = new FileOutputStream("/home/arpitkh96/python/" + fileName);
		IOUtils.copy(fileContent, f);
		String path = "/home/arpitkh96/python/";
		final Process p = Runtime.getRuntime().exec("python3 " + path + "encoding_matcher.py "+encodingFromDB+" " + path + fileName);
		BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line = null;
		if (p.exitValue() == 1) {

			String s="";
			try {
				while ((line = input.readLine()) != null) {
					s += ((line));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				p.waitFor();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return s.contains("It's a picture of me!");
		} else
			return false;
		
	}
}
