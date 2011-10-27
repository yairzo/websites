package huard.iws.servlet;


import huard.iws.service.PageBodyImageService;
import huard.iws.util.ApplicationContextProvider;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class ImageViewer extends HttpServlet{
	private int DEFAULT_IMG_ID = 1;
	private static final Logger logger = Logger.getLogger(ImageViewer.class);


	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		logger.info("Starting to view image");

		Object obj = ApplicationContextProvider.getContext().getBean("pageBodyImageService");
		PageBodyImageService pageBodyImageService = (PageBodyImageService)obj;

		String id;
		int aId=0;
		if ((id = request.getParameter("imageId"))!=null){
			aId = Integer.parseInt(id);
		}

		String attachType;
		if ((attachType = request.getParameter("attachType")) == null) return;

		try{

			if (aId != 0) {

				byte [] file = null;
				logger.info("Starting to load image: " + aId);
				file = pageBodyImageService.getPageBodyImage(aId).getImage();
				if (file ==null || file.length == 0)
					file = pageBodyImageService.getPageBodyImage(DEFAULT_IMG_ID).getImage();
				response.setContentType("image/gif");
				response.setStatus(HttpServletResponse.SC_OK);
				ServletOutputStream out = response.getOutputStream();
				out.write(file);
				out.flush();
				out.close();
			}
			/*else{
				if (attachType.equals("registration")){
					String aPhraseId;
					if ((aPhraseId = request.getParameter("phraseId"))==null) return;
					int phraseId = Integer.parseInt(aPhraseId);
					obj = ApplicationContextProvider.getContext().getBean("registrationService");
					RegistrationService registrationService = (RegistrationService)obj;
					String randomText = registrationService.getPhrase(phraseId);
					BufferedImage img = registrationService.generateImage(randomText);
					response.setContentType("image/jpeg");
					ImageIO.write(img, "jpg", response.getOutputStream());
				}
			}*/

		}catch(Exception e){
			System.out.println("Exception------>"+e);
		}


	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
	throws ServletException, IOException {
		System.out.println("=========servlet dopost....");
		doGet(req,res);
	}


}
