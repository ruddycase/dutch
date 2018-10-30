package com.dtt.handler.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dtt.handler.model.Report;
import com.dtt.handler.model.ReportStatus;

@Repository("reportRepository")
public interface ReportRepository extends JpaRepository<Report, Integer> {

    @Query("SELECT r FROM Report r WHERE r.owner = :owner AND r.status = :status ORDER BY r.updated DESC")
    public List<Report> getReportsByOwnerAndStatus(@Param("owner") Long owner, @Param("status") ReportStatus status, Pageable page);
    
    public Report getReportById(Long id);

}
