package Controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import Utils.UniqueCode;


public class UploadImageController extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");
		factory.setRepository(new File(this.getServletContext().getRealPath("/temp")));
		String docPath = this.getServletContext().getRealPath("/uploadFile");
		String url = "";
		try {
			List<FileItem> list = upload.parseRequest(request);
			for(FileItem item : list){
				if(item.isFormField()){
					String name = item.getFieldName();
					String value = item.getString(response.getCharacterEncoding());
					System.out.println(name + " : " + value);
				}else{
					String name = item.getName();
					if(name == null || name.trim().equals(""))return;
					String fileName = name.substring(name.lastIndexOf("\\") + 1);
					fileName = UniqueCode.getUUID() + "_" + fileName;
					String realPath = getRealPath(docPath, fileName);
					url = getURL(fileName);
					FileOutputStream out = new FileOutputStream(realPath + "/" + fileName);
					InputStream in = item.getInputStream();
					byte[] buffer = new byte[1024];
					int len = in.read(buffer);
					while(len != -1){
						out.write(buffer, 0, len);
						len = in.read(buffer);
					}
					in.close();
					out.close();
					item.delete();
				}
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
		url = "/" + this.getServletContext().getServletContextName() + "/" + "uploadFile" + url;
		response.getWriter().write(url);
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	public String getURL(String fileName){
		int hashCode = fileName.hashCode();
		int dir1 = (hashCode&0xf);
		int dir2 = (hashCode&0xf0)>>4;
		return "/" + dir1 + "/" + dir2 + "/" + fileName;
	}
	public String getRealPath(String docBase, String fileName){
		int hashCode = fileName.hashCode();
		int dir1 = (hashCode&0xf);
		int dir2 = (hashCode&0xf0)>>4;
		File file = new File(docBase + "/" + dir1 + "/" + dir2);
		if(!file.exists()){
			file.mkdirs();
		}
		return file.getPath();
	}

}
