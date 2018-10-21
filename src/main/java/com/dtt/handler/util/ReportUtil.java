package com.dtt.handler.util;

import java.util.ArrayList;
import java.util.List;

import com.dtt.handler.model.Report;

public class ReportUtil {

	public static final List<Report> findReportsByStatus(List<Report> reports, int status) {
		List<Report> matchingReports = new ArrayList<>();
		for(Report report : reports) {
			if(report.getStatus() == status) {
				matchingReports.add(report);
			}
		}
		return matchingReports;
	}
}
