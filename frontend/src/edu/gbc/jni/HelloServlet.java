 // To save as "<CATALINA_HOME>\webapps\helloservlet\WEB-INF\src\mypkg\HelloServlet.java"
package edu.gbc.jni;
 
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;

import com.google.gson.Gson;
 
public class HelloServlet extends HttpServlet {
   ServletContext context;
   @Override
   
   
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
	   
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("This is a GET request. You should be doing a POST request. Go back and try again!");
		out.close(); // Always close the output writer
	}
   
   
    //We LOVE errors. So always toss all exceptions up to the server, so we can read them easily from the web broswer. No try catches here.
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		context = getServletContext();

		// Set the response message's MIME type
		res.setContentType("text/html;charset=UTF-8");
		// Allocate a output writer to write the response message into the
		// network socket
		PrintWriter out = res.getWriter();

		
		//Used for the point cloud that was given
		String kinectID = req.getParameter("kinectID");
		String pointCloudWidth = req.getParameter("pointCloudWidth");
		String pointCloudHeight = req.getParameter("pointCloudHeight");
		String pointCloud = req.getParameter("pointCloud");
		
		//Used for the model
		String model = req.getParameter("model");
		String modelWidth = req.getParameter("modelWidth");
		String modelHeight = req.getParameter("modelHeight");

		
		//If a model has been sent as part of the request, store it on the server,
		//And end the request
		if (req.getParameter("model") != null) {
			context.setAttribute("model",model);
			context.setAttribute("modelWidth",modelWidth);	
			context.setAttribute("modelHeight",modelHeight);
			
//			//Also reset all the kinects, because we have a new model
//			//This is dumb code, doesn't do anything
//			context.setAttribute(kinectID,null);
//			context.setAttribute(kinectID + "width",null);
//			context.setAttribute(kinectID + "height",null);
//			
			out.println("Model recieved and stored.");
			out.println("<br>");
			out.println("Model:" + model);
			out.close();
			return;
		}
		
//		String startingPointCloud;
//		int startingPointCloudHeight;
//		int startingPointCloudWidth;

		
//		String endingPointCloud;
//		int endingPointCloudHeight;
//		int endingPointCloudWidth;
		
//		// Set the ending point cloud as the current input  
//		String endingPointCloud = pointCloud;
//		
//
//		//If there is no current point cloud on file for the kinectID,
//		//Give it a starting point of the model
//		//Otherwise, give the starting point cloud the last recorded
//		//Point cloud transmitted to the server
//		
//		if(context.getAttribute(kinectID) == null) {
//			startingPointCloud = (String) context.getAttribute("model");
//			startingPointCloudWidth = gson.fromJson((String) context.getAttribute("modelWidth"),Integer.class);
//			startingPointCloudHeight =  gson.fromJson((String) context.getAttribute("modelHeight"),Integer.class);
//		} else {
//			startingPointCloud = (String) context.getAttribute(kinectID);
//			startingPointCloudWidth = gson.fromJson((String) context.getAttribute(kinectID + "width"),Integer.class);
//			startingPointCloudHeight = gson.fromJson((String) context.getAttribute(kinectID + "height"),Integer.class);
//		}

		Gson gson = new Gson();

		int pointCloudHeight_int = gson.fromJson(pointCloudHeight,Integer.class);
		int pointCloudWidth_int = gson.fromJson(pointCloudWidth,Integer.class);
		float[] pointcloud_float = gson.fromJson(pointCloud, float[].class);
		
		int modelPointCloudWidth = gson.fromJson((String) context.getAttribute("modelWidth"),Integer.class);
		int modelPointCloudHeight =  gson.fromJson((String) context.getAttribute("modelHeight"),Integer.class);
		float[] modelPointCloud = gson.fromJson((String) context.getAttribute("model"), float[].class);
		
//		float[] endingPoints_float = gson.fromJson(endingPointCloud, float[].class);

		
//		//Output to browser; DEBUG ONLY
//		out.println("Kinect ID:" + kinectID);
//		out.println("<br>");
//		out.println("Starting points length:" + startingPoints_float.length);
//		out.println("<br>");
//		out.println("Starting Points:");
//		for (int i = 0; i < startingPoints_float.length; i++) {
//				out.print(String.valueOf(startingPoints_float[i]));
//				out.print(" ");
//			}
//		out.println("");
//		out.println("<br>");
//		out.println("Ending points length:" + endingPoints_float.length);
//		out.println("<br>");
//		out.println("Ending Points:");
//		for (int i = 0; i < endingPoints_float.length; i++) {
//			out.print(String.valueOf(endingPoints_float[i]));
//			out.print(" ");
//		}
//		out.println("<br>");
		
		//The IcpHandler takes in a starting and ending point cloud, along with corresponding widths,
		//And then calculates the proper transformation matrix between the two
		Matrix transformation = IcpHandler.iterativeClosestPoint(pointCloudWidth_int,pointCloudHeight_int,pointcloud_float,modelPointCloudWidth,modelPointCloudHeight,modelPointCloud);
		out.println(transformation.toString());
		
		//Store the incoming HTTP request point cloud into the kinect's point cloud data on the server
		context.setAttribute(kinectID,pointCloud);
		context.setAttribute(kinectID + "width",pointCloudWidth);
		context.setAttribute(kinectID + "height",pointCloudHeight);
		
		out.close(); // Always close the output writer
	}
}