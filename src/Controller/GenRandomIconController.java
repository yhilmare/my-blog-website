package Controller;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Utils.RandomImageGenerator;

public class GenRandomIconController extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RandomImageGenerator random = new RandomImageGenerator();
		BufferedImage image = random.genetatorImage(130,30);
		String token = random.getTokenStr();
		HttpSession session = request.getSession(true);
		session.setAttribute("token", token);
		response.setContentType("image/jpg");
		ImageIO.write(image, "jpg", response.getOutputStream());
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
