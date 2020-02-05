package com.giza.adaay.performance.reportEngine.enums;

public enum ReportType {

	PDF(1),
	WORD(2),
	EXCEL(3),
	XLSX(4),
	RTF(5),
	HTML(6);
	
	private int type ;
	private ReportType(int type) {
		this.type = type;
	}
	public int getType() {
		return type;
	}
	
}
