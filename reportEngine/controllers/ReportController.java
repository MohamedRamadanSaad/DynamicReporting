package com.giza.adaay.performance.reportEngine.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.giza.adaay.performance.reportEngine.common.LoggerUtils;
import com.giza.adaay.performance.reportEngine.dto.JasperReportReadyDTO;
import com.giza.adaay.performance.reportEngine.dto.Receiver;
import com.giza.adaay.performance.reportEngine.dto.ReportDTO;
import com.giza.adaay.performance.reportEngine.services.ReportGeneratorService;

@RestController
@CrossOrigin
@RequestMapping("/reports")
public class ReportController {
	
	@Autowired
	ReportGeneratorService reportGenerator;
	
	@Autowired
	LoggerUtils loggerUtils;

	@GetMapping("/getAll")
	public ResponseEntity<List<ReportDTO>> gettAllReports(@RequestHeader("Accept-Language")String language) {
		try {
			loggerUtils.logInfoMessage(this.getClass(), "Start Get All Reports, lang :"+language); 
			List<ReportDTO> reportList = reportGenerator.getAllReportsNames(language);
			loggerUtils.logInfoMessage(this.getClass(),"End Get All Reports # "+reportList.size()+", lang :"+language+" Successfully");
			return ResponseEntity.ok(reportList);
		} catch (Exception e) {
			loggerUtils.logErrorMessage(this.getClass(),"Failure in GET All Reports Error: "+e.getMessage());
			return ResponseEntity.badRequest().build();
		}
	}
	
	@GetMapping("/params/{id}")
	public ResponseEntity<JasperReportReadyDTO> getReportParams(@PathVariable("id")Integer reportID,
			@RequestHeader("user-id")Integer userId,
			@RequestHeader("Accept-Language")String language){
		try {
			loggerUtils.logInfoMessage(this.getClass(),"Start Get Report Parameters, lang :"+language);
			JasperReportReadyDTO reportParams = reportGenerator.getReportParams(reportID,language);
			loggerUtils.logInfoMessage(this.getClass(),"End Get Report Parameters #, lang :"+language+" Successfully");
			return ResponseEntity.ok(reportParams);
		} catch (Exception e) {
			loggerUtils.logErrorMessage(this.getClass(),"Failure in Get Report Parameters Error: "+e.getMessage());
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PostMapping("/generate")
	public byte[] getGeneratedReport(@RequestBody Receiver reciver,@RequestHeader("Accept-Language")String language
) {
//		@RequestHeader("lang")String language
		try {
			loggerUtils.logInfoMessage(this.getClass(),"Start Report Generation Service");
			byte[] output = reportGenerator.generateJasperReport(reciver,language);
			loggerUtils.logInfoMessage(this.getClass(),"End Report Generation Successfully");
			return output;
		} catch (Exception e) {
			loggerUtils.logErrorMessage(this.getClass(),"Failure in Report Generation Error: "+e.getMessage());
			return "Failure".getBytes();
		}
	}

}
