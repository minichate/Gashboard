package com.christroup.gashboard.wrapper.server;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProxyServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6564883437932580889L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		handleRequest(request, response, false);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		handleRequest(request, response, true);
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	private void handleRequest(HttpServletRequest request, HttpServletResponse response, boolean isPost) throws ServletException, IOException {
		String reqString = request.getHeader("Requested-URL");
		
		try {
            URL url = new URL(reqString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            
            if (isPost) {
	            connection.setDoOutput(true);
	            connection.setRequestMethod("POST");
	
	            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
	            Map<String, String> params = request.getParameterMap();
	            
	            for (String key : params.keySet()) {
	            	writer.write(key + "=" + params.get(key));
	            }
	            writer.close();
            } else {
            	connection.setRequestMethod("GET");
            }
    
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            	DataInputStream reader = new DataInputStream(connection.getInputStream());
            	String line;
            	while ((line = reader.readLine()) != null) {
                    response.getWriter().write(line);
                }
            	reader.close();
            	response.addHeader("Content-type", connection.getContentType());
            	response.addHeader("Requested-From", reqString);
            	
            } else {
                // Server returned HTTP error code.
            }
        } catch (MalformedURLException e) {
            // ...
        } catch (IOException e) {
            // ...
        }
//		
//		URL url = new URL(reqString);
//		InputStreamReader isr = new InputStreamReader(url.openStream());
//		BufferedReader reader = new BufferedReader(isr);
//        String line;
//
//        while ((line = reader.readLine()) != null) {
//            response.getWriter().write(line);
//        }
//
//        response.setCharacterEncoding(isr.getEncoding());
//        response.addHeader("Requested-From", reqString);
//        
//        reader.close();

	}
	
}
