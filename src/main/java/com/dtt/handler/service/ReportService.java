package com.dtt.handler.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.dtt.handler.model.Report;
import com.dtt.handler.model.ReportStatus;
import com.dtt.handler.repository.ReportRepository;

@Service("reportService")
public class ReportService {

    private ReportRepository reportRepository;

    @Autowired
    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public List<Report> dashboardReports(Long id, ReportStatus status) {
        return reportRepository.getReportsByOwnerAndStatus(id, status, PageRequest.of(0, 5));
    }
    
    public Report getReportById(Long id) {
    	return reportRepository.getReportById(id);
    }

	public Report save(Report report) {
		return reportRepository.save(report);
	}
	
	public void delete(Report report) {
		reportRepository.delete(report);
	}
	
	public Report getReportWithDefaults(Long id) {
		List<Report> reports = reportRepository.getReportsByOwnerAndStatus(id, ReportStatus.DEFAULT, PageRequest.of(0, 1));
		return reports.size() == 1 ? reports.get(0) : new Report();
	}

}