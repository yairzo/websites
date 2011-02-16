package huard.iws.web;

import huard.iws.bean.PersonBean;
import huard.iws.service.RecordProtectService;
import huard.iws.util.RequestWrapper;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

public class BusyRecordsController  extends GeneralController {

	//private static final Logger logger = Logger.getLogger(BusyRecordsController.class);

	@SuppressWarnings("unchecked")
	public ModelAndView handleRequest(RequestWrapper request, HttpServletResponse response,
			Map <String, Object> model, PersonBean userPersonBean) throws Exception {
		List<String> busyRecords = recordProtectService.getBusyRecords();
		model.put("busyRecords", busyRecords);
		return new ModelAndView("busyRecords",model);
	}

	private RecordProtectService recordProtectService;

	public void setRecordProtectService(RecordProtectService recordProtectService) {
		this.recordProtectService = recordProtectService;
	}
}
