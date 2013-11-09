package huard.website.viewer.page;

import huard.iws.model.PageBodyImage;
import huard.iws.service.PageBodyImageService;
import huard.website.model.TabledInfoPage;
import huard.website.util.PageAccessLog;
import huard.website.util.Utils;
import huard.website.viewer.profile.NewsProfile;
import huard.website.viewer.profile.ProfilesDbHandler;

import java.util.List;

import org.springframework.remoting.rmi.RmiProxyFactoryBean;

public class MainPageViewer {
	private final String categoryTableNameHeb = "harashut_lemop";
	private final String categoryTableNameEng = "RD_authority";
	
	public static boolean IS_MAIN_PAGE = true;

	private String pictureName;

	private String caption;

	private String englishCaption;

	private String[] pictureNameArray;

	private String[] captionArray;

	private String[] englishCaptionArray;

	private TabledInfoPage[] infoPagesToRollingMessages;

	private TabledInfoPage[] infoPagesWithCloseSubmission;

	PagesDbHandler dbHandler;

	ProfilesDbHandler profilesDbHandler;

	public MainPageViewer() {
		dbHandler = new PagesDbHandler();
		pictureNameArray = dbHandler.getPictureName();
		captionArray = dbHandler.getCaption();
		englishCaptionArray = dbHandler.getEnglishCaption();
		profilesDbHandler = new ProfilesDbHandler();
	}

	public void logAccessToPage(boolean heb, String ip) {
		PageAccessLog.logAccesToPage("MainPage", 100, "עמוד ראשי", heb, ip);
	}

	public String getCategory(boolean heb) {
		return heb ? categoryTableNameHeb : categoryTableNameEng;
	}

	public String getPictureName() {
		return pictureName;
	}

	public void setPictureName(String string) {
		pictureName = string;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String string) {
		caption = string;
	}

	public String getEnglishCaption() {
		return englishCaption;
	}

	public void setEnglishCaption(String string) {
		englishCaption = string;
	}

	public String[] getPictureNameArray() {
		return pictureNameArray;
	}

	public String[] getCaptionArray() {
		return captionArray;

	}

	public String[] getEnglishCaptionArray() {
		return englishCaptionArray;

	}

	public String getVersionName() {
		return Utils.getVersionName();
	}

	public boolean isHebrew(String str) {
		return ProfilesDbHandler.isHebrew(str);
	}

	/*
	 * public TabledInfoPage[] getNewInfoPagesToRollingMessages(){ if
	 * (infoPagesToRollingMessages == null){ NewsProfile newsProfile= new
	 * NewsProfile(); TabledInfoPage newInfoPages [] =
	 * newsProfile.getNewInfoPages(); int numOfRollingInfoPages =
	 * Utils.getNumOfLastPublishedInfoPagesToRoll() < newInfoPages.length ?
	 * Utils.getNumOfLastPublishedInfoPagesToRoll() : newInfoPages.length;
	 * infoPagesToRollingMessages = new TabledInfoPage[numOfRollingInfoPages];
	 * System.arraycopy(newInfoPages, 0, infoPagesToRollingMessages, 0,
	 * numOfRollingInfoPages); } return infoPagesToRollingMessages; }
	 */

	public TabledInfoPage[] getInfoPagesWithCloseSubmission() {
		if (infoPagesWithCloseSubmission == null) {
			long until = System.currentTimeMillis()
					+ Utils.getNumOfDaysBeforeSubmissionToRollInfoPage()
					* Utils.DAY;
			infoPagesWithCloseSubmission = profilesDbHandler
					.getInfoPagesByRangeOfSubDates(System.currentTimeMillis(),
							until, false);
		}
		return infoPagesWithCloseSubmission;
	}

	public TabledInfoPage[] getInfoPagesToRollingMessages() {
		if (infoPagesToRollingMessages == null) {
			NewsProfile newsProfile = new NewsProfile();
			infoPagesToRollingMessages = newsProfile
					.getInfoPagesToRollingMessages();
		}
		return infoPagesToRollingMessages;
	}

	public String markApostrofWithBackSlash(String str) {
		return str.replaceAll("'", "\\'");
	}

	public String getFormatedDate(long date) {
		return Utils.getFormatedDate(date);
	}

	public String getSiteLastUpdate() {
		return dbHandler.getSiteLastUpdate();
	}

	public String[] getIWSImageIdsArray() {
		RmiProxyFactoryBean factory = new RmiProxyFactoryBean();
		factory.setServiceInterface(PageBodyImageService.class);
		factory.setServiceUrl("rmi://localhost:1199/PageBodyImageService");
		factory.afterPropertiesSet();

		String [] pictureIdsArray = null;
		PageBodyImageService pageBodyImageService = (PageBodyImageService) factory.getObject();
	    List<PageBodyImage> pageBodyImages =  pageBodyImageService.getApprovedPageBodyImages();
		  
		if (pageBodyImages.size()>0){
			pictureIdsArray = new String[pageBodyImages.size()];
			for (int i = 0; i < pictureIdsArray.length; i++) {
				pictureIdsArray[i] = (String.valueOf(pageBodyImages.get(i)
						.getId()));
			}
		} else {
			pictureIdsArray = new String[1];
			pictureIdsArray[0] = new String();
		}
		return pictureIdsArray;
	}

	public String[] getIWSImageHebNamesArray() {
		RmiProxyFactoryBean factory = new RmiProxyFactoryBean();
		factory.setServiceInterface(PageBodyImageService.class);
		factory.setServiceUrl("rmi://localhost:1199/PageBodyImageService");
		factory.afterPropertiesSet();

		String [] pictureNamesArray = null;
		PageBodyImageService pageBodyImageService = (PageBodyImageService) factory.getObject();
	    List<PageBodyImage> pageBodyImages =  pageBodyImageService.getApprovedPageBodyImages();
		  
		if (pageBodyImages.size()>0){
			pictureNamesArray = new String[pageBodyImages.size()];
			for (int i = 0; i < pictureNamesArray.length; i++) {
				pictureNamesArray[i] = (String) (pageBodyImages.get(i)
						.getCaptionHebrew());
			}
		} else {
			pictureNamesArray = new String[1];
			pictureNamesArray[0] = new String();
		}
		return pictureNamesArray;
	}

	public String[] getIWSImageEngNamesArray() {
		RmiProxyFactoryBean factory = new RmiProxyFactoryBean();
		factory.setServiceInterface(PageBodyImageService.class);
		factory.setServiceUrl("rmi://localhost:1199/PageBodyImageService");
		factory.afterPropertiesSet();

		String [] pictureNamesArray = null;
		PageBodyImageService pageBodyImageService = (PageBodyImageService) factory.getObject();
	    List<PageBodyImage> pageBodyImages =  pageBodyImageService.getApprovedPageBodyImages();
		  
		if (pageBodyImages.size()>0){
			pictureNamesArray = new String[pageBodyImages.size()];
			for (int i = 0; i < pictureNamesArray.length; i++) {
				pictureNamesArray[i] = (String) (pageBodyImages.get(i)
						.getCaptionEnglish());
			}
		} else {
			pictureNamesArray = new String[1];
			pictureNamesArray[0] = new String();
		}
		return pictureNamesArray;
	}
}
