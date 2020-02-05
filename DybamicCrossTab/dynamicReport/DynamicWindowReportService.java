package com.adaay.reporting.reportingmodule.dynamicReport;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.adaay.reporting.reportingmodule.dynamicReport.customAnnotation.ColumnName;
import com.adaay.reporting.reportingmodule.dynamicReport.customAnnotation.CrossTableID;
import com.adaay.reporting.reportingmodule.dynamicReport.reflectionDTO.ReflectionDTO;

@Service
public class DynamicWindowReportService {

	/**
	 * Created by Mohamed Ramadan on 26/01/2019.
	 */
	public ArrayList<ReflectionDTO> getClassData(List<Object> clazzList) {
		ArrayList<ReflectionDTO> objInfo = new ArrayList<ReflectionDTO>();
		ReflectionDTO reflectObject = null;
		for (Object clazz : clazzList) {
			reflectObject = new ReflectionDTO();
			Class aClass = clazz.getClass();
			int generalIdForClass = 0;
			List<Field> ids = Arrays.asList(aClass.getDeclaredFields()).stream()
					.filter(x -> x.isAnnotationPresent(CrossTableID.class) == true).collect(Collectors.toList());
			// begin id trx for id only trx Always = 1
			for (Field field : ids) {
				field.setAccessible(true);
				CrossTableID column = field.getAnnotation(CrossTableID.class);
				   if (column != null) {
					try {
						generalIdForClass =Integer.parseInt(field.get(clazz).toString());
						reflectObject.setId(Integer.parseInt(field.get(clazz).toString()));
					} catch (IllegalArgumentException | IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}//end catch
				   }// end id if
			}//end field loop
			// begin field trx for every class = columns - 1 ->id
			List<Field> fields = Arrays.asList(aClass.getDeclaredFields()).stream()
					.filter(x -> x.isAnnotationPresent(ColumnName.class) == true).collect(Collectors.toList());
//			
			for (Field field : fields) {
				field.setAccessible(true);
				ColumnName column = field.getAnnotation(ColumnName.class);
				   if (column != null) {
					try {
						reflectObject.setLabelAR(field.getAnnotation(ColumnName.class).labelAR());
						reflectObject.setLabelEN(field.getAnnotation(ColumnName.class).labelEN());
						reflectObject.setValue(field.get(clazz) == null ? "" : field.get(clazz).toString());
						objInfo.add(reflectObject);
						reflectObject = new ReflectionDTO();
						reflectObject.setId(generalIdForClass);
					} catch (IllegalArgumentException | IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}//end catch
				   }// end id if
			}//end field loop
			

		}
//		objInfo.forEach(obj -> {
//			System.out.println("id :"+obj.getId());
//			System.out.println("labelar:"+obj.getLabelAR());
//			System.out.println("labelen : "+obj.getLabelEN());
//			System.out.println("value : "+obj.getValue());
//			System.out.println("---------------------------");
//
//		});
		return objInfo;
	}
	
	
}
