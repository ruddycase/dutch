package com.dtt.handler.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dtt.handler.model.Report;

@Repository("reportRepository")
public interface ReportRepository extends JpaRepository<Report, Integer> {

    @Query("SELECT r FROM Report r WHERE r.owner = :owner AND r.status = :status ORDER BY r.updated DESC")
	public List<Report> dashboardReports(@Param("owner") int owner, @Param("status") int status, Pageable page);
    
	public Report getReportById(int id);

}
