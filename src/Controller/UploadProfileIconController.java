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
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import Service.BlogHolder2DBService;
import Service.BlogVisitor2DBService;
import Utils.UniqueCode;
import domain.blog_holder;
import domain.blog_visitor;


public class UploadProfileIconController extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session == null){
			response.getWriter().write("您没有登录，或者登陆超时");
			return;
		}
		blog_holder holder = (blog_holder) session.getAttribute("holder");
		if(holder == null){
			response.getWriter().write("您没有登录");
			return;
		}
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding(request.getCharacterEncoding());
		factory.setRepository(new File(this.getServletContext().getRealPath("/temp")));
		String docPath = this.getServletContext().getRealPath("/uploadFile");
		String url = "";
		try {
			List<FileItem> list = upload.parseRequest(request);
			for(FileItem item : list){
				if(item.isFormField()){
					
				}else{
					String name = item.getName();
					if(name == null || name.trim().equals(""))return;
					String fileName = name.substring(name.lastIndexOf("\\") + 1);
					fileName = UniqueCode.getUUID() + "_" + fileName;
					String realPath = getRealPath(docPath, fileName);
					url = getPath(fileName);
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
			response.getWriter().write("修改头像失败");
		}
		holder.setHolder_img("uploadFile" + url);
		
		BlogVisitor2DBService ser = new BlogVisitor2DBService();
		blog_visitor visitor = ser.selectVisitor("IL MARE");
		if (visitor == null) {
			visitor = new blog_visitor();
			visitor.setVisitor_nickname("IL MARE");
			visitor.setVisitor_gender("男");
			visitor.setProvince(holder.getHolder_province_zh());
			visitor.setCity(holder.getHolder_city_zh());
			visitor.setFigureurl("/blog/" + holder.getHolder_img());
			visitor.setFigureurl_1("/blog/" + holder.getHolder_img());
			visitor.setFigureurl_2("/blog/" + holder.getHolder_img());
			visitor.setFigureurl_qq("/blog/" + holder.getHolder_img());
			visitor.setFigureurl_qq_1("/blog/" + holder.getHolder_img());
			visitor.setFigureurl_qq_2("/blog/" + holder.getHolder_img());
			if (ser.insertVisitor(visitor) != 1) {
				response.getWriter().write("visitor对象插入失败，错误");
				return;
			}
		}else {
			visitor.setProvince(holder.getHolder_province_zh());
			visitor.setCity(holder.getHolder_city_zh());
			visitor.setFigureurl("/blog/" + holder.getHolder_img());
			visitor.setFigureurl_1("/blog/" + holder.getHolder_img());
			visitor.setFigureurl_2("/blog/" + holder.getHolder_img());
			visitor.setFigureurl_qq("/blog/" + holder.getHolder_img());
			visitor.setFigureurl_qq_1("/blog/" + holder.getHolder_img());
			visitor.setFigureurl_qq_2("/blog/" + holder.getHolder_img());
			if (ser.updateVisitor(visitor) != 1) {
				response.getWriter().write("visitor对象更新失败，错误");
				return;
			}
		}
		
		BlogHolder2DBService service = new BlogHolder2DBService();
		if(service.updateHolder(holder) >= 1){
			this.getServletContext().setAttribute("holder", holder);
			session.setAttribute("holder", holder);
			response.getWriter().write("修改头像成功");
		}else{
			response.getWriter().write("修改头像失败");
		}
		
	}
	
	public String getPath(String fileName){
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
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
