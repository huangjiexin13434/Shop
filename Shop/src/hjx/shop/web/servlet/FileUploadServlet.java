package hjx.shop.web.servlet;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * Servlet implementation class FileUploadServlet
 */
public class FileUploadServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		DiskFileItemFactory factory=new DiskFileItemFactory();
		ServletFileUpload fileUpload=new ServletFileUpload(factory);
		try {
			 List<FileItem> parseRequest = fileUpload.parseRequest(request);
			 for(FileItem fileItem:parseRequest) {
				 boolean isForField= fileItem.isFormField();
				 if(isForField) {
					 String fieldName= fileItem.getFieldName();
					// System.out.println(fileItem.getName());
					 System.out.println(fieldName);
				 }else {
					 
					 String name= fileItem.getName();
					 //System.out.println(request.getContextPath()+"/upload");
					 InputStream in= fileItem.getInputStream();
					 //String path=this.getServletContext().getRealPath("upload");
					 String path="e:\\upload";
					 OutputStream out=new FileOutputStream(path+"/"+name);
					 int i=0;
					 while((i=in.read())!=-1) {
						 out.write(i);
					 }
					 
				 }
			 }
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
