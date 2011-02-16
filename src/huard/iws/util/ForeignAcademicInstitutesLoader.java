package huard.iws.util;

import huard.iws.model.Institute;
import huard.iws.service.InstituteService;

import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;


	public class ForeignAcademicInstitutesLoader {
		private FileInputStream file = null;

		private String instituteUrl = "rmi://localhost:1199/InstituteService";

		public InstituteService lookupInstituteService()
		     throws RemoteException, NotBoundException,
		    MalformedURLException {
		   InstituteService instituteService = (InstituteService)
		     Naming.lookup(instituteUrl);
		   return instituteService;
		}

		public void loadFile() throws Exception{
			file = new FileInputStream("/home/yair/mop/docs/institutes/Colleges and Universities in Europe1.xls");
			HSSFWorkbook wb = new HSSFWorkbook(new POIFSFileSystem(file));

			RmiProxyFactoryBean factory = new RmiProxyFactoryBean();
			factory.setServiceInterface(InstituteService.class);
			factory.setServiceUrl("rmi://localhost:1199/InstituteService");
			factory.afterPropertiesSet();




			for (int j=14; j<15; j++){


				HSSFSheet sheet = wb.getSheetAt(j);
				Iterator rowIt = sheet.rowIterator();


				// iterate over the rows

				int counter = 0;
				while (rowIt!=null && rowIt.hasNext())
				{

					HSSFRow row = (HSSFRow) rowIt.next();
					if (row !=null){
					Iterator cellIterator = row.cellIterator();


					// for each row iterate over the cells
					Institute institute = new Institute();
					for (int i = 0; cellIterator!=null && cellIterator.hasNext(); i++){

						HSSFCell cell = (HSSFCell) cellIterator.next();
						if (cell!=null){
						String cellString = cell.getRichStringCellValue().getString();

						switch (i) {
						case 0:
							institute.setContinentId(2);

							InstituteService instituteService = (InstituteService) factory.getObject();

							int countryId = instituteService.insertCountry(2, cellString.replaceAll(" (.*?)", "$1"));
							institute.setCountryId(countryId);
							break;
						case 1:  institute.setName(cellString); break;
						case 3:  institute.setCity(cellString); break;
						default: break;
						}
						}
					}

					InstituteService instituteService = (InstituteService) factory.getObject();

					instituteService.insertInstitute(institute);
					}
					counter ++;
				}
			}
		}

	public static void main (String [] args){
		ForeignAcademicInstitutesLoader fail = new ForeignAcademicInstitutesLoader();
		try{
			fail.loadFile();
		}
		catch (Exception e){
			System.out.println(e);
		}
	}





}