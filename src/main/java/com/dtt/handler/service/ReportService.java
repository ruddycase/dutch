package com.dtt.handler.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.dtt.handler.model.Report;
import com.dtt.handler.repository.ReportRepository;

@Service("reportService")
public class ReportService {

    private ReportRepository reportRepository;

    @Autowired
    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public List<Report> dashboardReports(int owner, int status) {
        return reportRepository.dashboardReports(owner, status, PageRequest.of(0, 5));
    }
    
    public Report getReportById(int id) {
    	return reportRepository.getReportById(id);
    }

}