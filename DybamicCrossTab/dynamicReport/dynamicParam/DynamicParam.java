package com.adaay.reporting.reportingmodule.dynamicReport.dynamicParam;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.adaay.reporting.reportingmodule.common.reportGenerator.ReportFormat;
import com.adaay.reporting.reportingmodule.enums.FileTypes;
import org.springframework.stereotype.Service;

import com.adaay.reporting.reportingmodule.dto.shared.AdvancedSearchDTO;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class DynamicParam {

	private String language;
	private int type=1;
	private String suffix = "";

	public void getParameterString(String advancedListString, Map<String, Object> jasperParams,
			Map<String, String> replacedValues, Map<String, String> listValues) {
		try {

			List<AdvancedSearchDTO> advancedList = new ArrayList<AdvancedSearchDTO>();
			ObjectMapper mapper = new ObjectMapper();
			if (advancedListString != null) {

				advancedListString = URLDecoder.decode(advancedListString, "UTF-8");

				advancedList = Arrays.asList(mapper.readValue(advancedListString, AdvancedSearchDTO[].class));
			}
			String parametersString = "";
			int line = 1;
			String space = "  ";
			if (advancedListString != null && advancedListString.length() > 2) {

				for (AdvancedSearchDTO obj : advancedList) {

					if(obj.getValue()!=null)
					{
						if(isArabicLanguage())
						{
							obj.setValue(obj.getValue()+":");
						}
					for (Map.Entry<String, String> entry : replacedValues.entrySet()) {

						if (entry.getKey().equals(obj.getPropertyName())) {
							if (!entry.getValue().startsWith("List_")&&!entry.getValue().startsWith("Date_")) {
								parametersString += entry.getValue() + ":  "
										+ ((obj.getValue() != "" && obj.getValue() != null) ? obj.getValue() : suffix);
								jasperParams.put("p_line" + line, parametersString + space);
								parametersString = "";
								line++;
							} else if (entry.getValue().startsWith("Date_")) {
								String dateFormated = obj.getValue() != null ? (new SimpleDateFormat("dd/MM/yyyy")
										.format(new SimpleDateFormat("yyyy-MM-dd").parse(obj.getValue()))) : "";
								parametersString += entry.getValue().substring(5) + ":  "
										+ ((obj.getValue() != "" && obj.getValue() != null) ? dateFormated : suffix);
								jasperParams.put("p_line" + line, parametersString + space);
								parametersString = "";
								line++;
							} else if (entry.getValue().startsWith("List_")) {
								for (Map.Entry<String, String> listvalue : listValues.entrySet()) {
									if (obj.getValue() == null) {
										parametersString += entry.getValue().substring(5) + ":  " + suffix;
										jasperParams.put("p_line" + line, parametersString + space);
										parametersString = "";
										line++;
									} else if (listvalue.getKey().startsWith(entry.getValue().substring(5))) {
										boolean isAdded = false;
										if (listvalue.getKey().substring(listvalue.getKey().indexOf("$") + 1)
												.equals(obj.getValue()) && !isAdded) {
											parametersString += entry.getValue().substring(5) + ":  "
													+ (listvalue.getValue() != null ? listvalue.getValue() : suffix);
											jasperParams.put("p_line" + line, parametersString + space);
											parametersString = "";
											line++;
											isAdded = true;
										}
									}

								}
							}
						}

					}
					}
					else {// object value is null
						for (Map.Entry<String, String> entry : replacedValues.entrySet()) {
							if(entry.getKey().equals(obj.getPropertyName())) {
							boolean isAdded = false;
							if (!entry.getValue().startsWith("List")&&!entry.getValue().startsWith("Date_")) {
								parametersString += entry.getValue() + ":  " + suffix;
								jasperParams.put("p_line" + line, parametersString + space);
								parametersString = "";
								line++;
							} else if (entry.getValue().startsWith("List")&&!isAdded){
								parametersString += entry.getValue().substring(5) + ":  " + suffix;
								jasperParams.put("p_line" + line, parametersString + space);
								parametersString = "";
								line++;
								isAdded=true;
							}else if (entry.getValue().startsWith("Date_")) {
								parametersString += entry.getValue().substring(5) + ":  " + suffix;
								jasperParams.put("p_line" + line, parametersString + space);
								parametersString = "";
								line++;
							}
						}
						}
					}
				}
			} else {//parameters are empty
				for (Map.Entry<String, String> entry : replacedValues.entrySet()) {
					boolean isAdded = false;
					if (!entry.getValue().startsWith("List")&&!entry.getValue().startsWith("Date_")) {
						parametersString += entry.getValue() + ":  " + suffix;
						jasperParams.put("p_line" + line, parametersString + space);
						parametersString = "";
						line++;
					} else if (entry.getValue().startsWith("List")&&!isAdded){
						parametersString += entry.getValue().substring(5) + ":  " + suffix;
						jasperParams.put("p_line" + line, parametersString + space);
						parametersString = "";
						line++;
						isAdded=true;
					}else if (entry.getValue().startsWith("Date_")) {
						parametersString += entry.getValue().substring(5) + ":  " + suffix;
						jasperParams.put("p_line" + line, parametersString + space);
						parametersString = "";
						line++;
					}
				}
			}
			if (!isArabicLanguage())
				jasperParams.put("p_line" + line,
						"Report Date: " + new SimpleDateFormat("dd/MM/yyyy  hh:mm a").format(new java.util.Date()));
			if (isArabicLanguage()) {

				 if (getType()== FileTypes.DOCX.getId()) {

				 	String st1="تاريخ";
				 	String st2=" التقرير";
				 	String st3=new SimpleDateFormat("dd/MM/yyyy  hh:mm a").format(new java.util.Date())+": ";
					 jasperParams.put("p_line" + line,st1+""+st2+""+st3);
				 }
				 else
				 	jasperParams.put("p_line" + line,
						new SimpleDateFormat("dd/MM/yyyy  hh:mm a").format(new java.util.Date()) +": "+"تاريخ التقرير");
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	public boolean isArabicLanguage() {
		if (getLanguage() == null)// default english
			return false;
		else if (getLanguage() != null && getLanguage().equalsIgnoreCase("en"))
			return false;
		else {

			return true;
		}
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
		if (language.equalsIgnoreCase("ar")) {
			suffix = "الكل";
			return;
		}
		suffix = "All";
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
