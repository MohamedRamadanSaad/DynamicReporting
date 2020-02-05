package com.giza.adaay.performance.reportEngine.services;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.giza.adaay.performance.reportEngine.common.DBUtils;
import com.giza.adaay.performance.reportEngine.common.DatabasaDataTypesMapping;
import com.giza.adaay.performance.reportEngine.common.LoggerUtils;
import com.giza.adaay.performance.reportEngine.dto.JasperReportReadyDTO;
import com.giza.adaay.performance.reportEngine.dto.Parameter;
import com.giza.adaay.performance.reportEngine.dto.ParameterValue;
import com.giza.adaay.performance.reportEngine.dto.Receiver;
import com.giza.adaay.performance.reportEngine.dto.ReportDTO;
import com.giza.adaay.performance.reportEngine.entities.Report;
import com.giza.adaay.performance.reportEngine.entities.ReportParameter;
import com.giza.adaay.performance.reportEngine.enums.ReportType;
import com.giza.adaay.performance.reportEngine.repos.ReportParametersRepo;
import com.giza.adaay.performance.reportEngine.repos.ReportsRepo;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;

@Service
public class ReportGeneratorService {

	@Autowired
	ReportsRepo reportsRepo;

	@Autowired
	ReportParametersRepo paramsrepo;

	@Autowired
	LoggerUtils loggerUtils;

	@Autowired
	DBUtils dbUtils;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<ReportDTO> getAllReportsNames(String lang) {
		List<ReportDTO> reportsDTO = new ArrayList<ReportDTO>();
		List<Report> reports = reportsRepo.findByOrderById();
		boolean isEnglish = lang != null && lang.equalsIgnoreCase("en");
		for (Report report : reports) {
			ReportDTO dto = new ReportDTO();
			dto.setId(report.getId());
			dto.setName(isEnglish ? report.getDisplayNameEN() : report.getDisplayNameAR());
			reportsDTO.add(dto);
		}
		return reportsDTO;
	}

	public JasperReportReadyDTO getReportParams(int reportID, String lang) {
		JasperReportReadyDTO outParams = new JasperReportReadyDTO();
		Report report = reportsRepo.findById(reportID).get();
		if (report == null)
			loggerUtils.logErrorAndThrowException(this.getClass(), "Report Not Found");

		outParams.setId(report.getId());
		List<ReportParameter> params = report.getParameters();
		if (params == null)
			loggerUtils.logErrorAndThrowException(this.getClass(), "No Parameters Found");

		// to not repeat check in every loop
		boolean haveParams = (params != null);
		boolean isEnglish = lang != null && lang.equalsIgnoreCase("en");

		// validate language
		if (haveParams)
			for (ReportParameter reportParameter : params) {
				Parameter parameter = new Parameter();
				parameter.setName(isEnglish ? reportParameter.getLabelEN() : reportParameter.getLabelAR());
				parameter.setType(reportParameter.getCompenent());
				parameter.setMandatory(reportParameter.getIsRequiered()?true:false);
				if (reportParameter.getSelectStatment() != null && reportParameter.getSelectStatment().length() > 0
						&& !reportParameter.getSelectStatment().equals("")) {
					String sql = reportParameter.getSelectStatment();
					try {
						List<Map<String, Object>> queryForList = jdbcTemplate.queryForList(sql);
						ParameterValue values = null;
						for (Map<String, Object> query : queryForList) {
							int i = 0;
							values = new ParameterValue();
							for (Map.Entry<String, Object> entry : query.entrySet()) {
								if (i == 0)
									values.setValue(entry.getValue().toString());
								if (i == 1)
									values.setName(entry.getValue().toString());
								i++;
							}
							parameter.getValues().add(values);

						}
						outParams.getParams().add(parameter);
					} catch (Exception e) {
						loggerUtils.logErrorAndThrowExceptionPrintStackTrace(this.getClass(),
								"Error in returning parameter values", e);
					}

				} else {
					// else no sql query found
					outParams.getParams().add(parameter);
				}
			}
		else
			loggerUtils.logErrorAndThrowException(this.getClass(), "Error in handling params.");
		return outParams;
	}

	public byte[] generateJasperReport(Receiver jasperReportDTO, String language) {
		Connection conn = dbUtils.getConnection();
		if (jasperReportDTO.getId() == 0)
			loggerUtils.logErrorAndThrowException(this.getClass(), "Report ID not Found.");
		Report report = reportsRepo.findById(jasperReportDTO.getId()).get();
		String dir = "";
		String path = "";
		String subResources_DIR = "";
		try {
			// last index in dir must be // file seperator come from db
			dir ="../JasperTemplates/Source/ParametrizedReports/"+report.getName()+"/";
			path = dir + report.getName() + ".jrxml";
//			dir = report.getDirectory()+report.getName()+"\\";
//			path = dir + report.getName() + ".jrxml";
//			subResourses_DIR = dir ;
		} catch (Exception e) {
			loggerUtils.logErrorAndThrowException(this.getClass(),
					"Check path configuration for report [" + report.getDisplayNameEN() + "]");
		}
		File file = null;
		JasperReport jasperReport = null;
		JasperPrint jasperPrint = null;
		try {
			file = ResourceUtils.getFile(path);
			String absolutePath = file.getAbsolutePath();
			jasperReport = JasperCompileManager.compileReport(absolutePath);
			subResources_DIR= absolutePath.substring(0, absolutePath.lastIndexOf(File.separator) )+File.separator;
			Map<String, Object> parameters = fillReportParameters(report.getParameters(), jasperReportDTO, path,
					subResources_DIR, language);

			jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, conn);
			return getBytes(jasperPrint, jasperReportDTO, report);
		} catch (FileNotFoundException e) {
			loggerUtils.logErrorAndThrowException(this.getClass(), "Can not find report.");
		} catch (JRException e) {
			loggerUtils.logErrorAndThrowException(this.getClass(), "Fill Report Error: " + e.getMessage());
		}finally {
			try {
				// note > connection will not br closed here ,spring will return to the connection pool 
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	private Map<String, Object> fillReportParameters(List<ReportParameter> dbParamsRecords, Receiver reciver,
			String path, String subResources_DIR, String language) {
		Map<String, Object> jasperParameters = new HashMap<>();
		Map<String, Object> values = reciver.getParams();
		boolean isEnglish = (language != null && language.equals("en"));
		for (ReportParameter dbParam : dbParamsRecords) {
			for (Map.Entry<String, Object> entry : values.entrySet()) {
				if (isEnglish)
					if (dbParam.getLabelEN().equals(entry.getKey())) {
						checkForParameterType(dbParam.getName(), dbParam.getType(), entry.getValue(), jasperParameters);
						break;
					}
				if (!isEnglish)
					if (dbParam.getLabelAR().equals(entry.getKey())) {
						jasperParameters.put(dbParam.getName(), entry.getValue());
						break;
					}
			}

		}
		jasperParameters.put("SubResources_DIR", subResources_DIR);
		jasperParameters.put("Printed_By ", reciver.getPrintedBy());

		if (ReportType.EXCEL.getType() == reciver.getType())
			jasperParameters.put("IS_IGNORE_PAGINATION", true);

		return jasperParameters;
	}

	private void checkForParameterType(String name, String type, Object value, Map<String, Object> jasperParameters) {
		if (type.equals(DatabasaDataTypesMapping.NUMBER))
			jasperParameters.put(name, Integer.parseInt( String.valueOf(value)));
		else if (type.equals(DatabasaDataTypesMapping.CHARACTER))
			jasperParameters.put(name, String.valueOf(value));
		else if (type.equals(DatabasaDataTypesMapping.DATE)) { // in stc report is designed that date returns in string values
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
			SimpleDateFormat format2 = new SimpleDateFormat("dd-MM-yyyy");
			Date date =null;
			try {
				date = format1.parse(String.valueOf(value));
				Calendar c = Calendar.getInstance(); 
				c.setTime(date); 
				c.add(Calendar.DATE, 1);
				date = c.getTime();
			} catch (ParseException e) {
				loggerUtils.logErrorAndThrowException(this.getClass(), " Date Parameter Converting Error :" + e.getMessage());
			}
			System.out.println(format2.format(date));
			jasperParameters.put(name,format2.format(date));
		}
			

	}

	private byte[] getBytes(JasperPrint jasperPrint, Receiver jasperReportDTO, Report report) {
		try {
			if (ReportType.PDF.getType() == jasperReportDTO.getType()) {
				return JasperExportManager.exportReportToPdf(jasperPrint);
			} else if (ReportType.WORD.getType() == jasperReportDTO.getType()) {
				JRDocxExporter docxExporter = new JRDocxExporter();
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
				docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, os);
				docxExporter.exportReport();
				return os.toByteArray();
			} else if (ReportType.EXCEL.getType() == jasperReportDTO.getType()) {
				JRXlsxExporter exporter = new JRXlsxExporter();
				exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, os);
				exporter.exportReport();
				return os.toByteArray();
			} else if (ReportType.RTF.getType() == jasperReportDTO.getType()) {
				JRRtfExporter exporter = new JRRtfExporter();
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
				exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, os);
				exporter.exportReport();
				return os.toByteArray();
			}
		} catch (JRException e) {
			loggerUtils.logErrorAndThrowException(this.getClass(), "Compile Report Error: " + e.getMessage());
		}

		return null;
	}

}
