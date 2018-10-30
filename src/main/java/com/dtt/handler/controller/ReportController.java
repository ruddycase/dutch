package com.dtt.handler.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.dtt.handler.model.Report;
import com.dtt.handler.model.ReportHistory;
import com.dtt.handler.model.ReportStatus;
import com.dtt.handler.model.User;
import com.dtt.handler.service.ReportService;
import com.dtt.handler.service.UserService;
import com.dtt.handler.util.ReportUtil;

@Controller
public class ReportController {

	@Autowired
	private ReportService reportService;

	@Autowired
	private UserService userService;

	/*
	 * Accessing individual report screen from dashboard
	 */
	@GetMapping(value = "/admin/report/{id}")
	public ModelAndView getReport(@PathVariable Long id) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("report", reportService.getReportById(id));
		modelAndView.setViewName("admin/report");
		return modelAndView;
	}

	/*
	 * Accessing individual read-only report screen from dashboard
	 */
	@GetMapping(value = "/admin/roreport/{id}")
	public ModelAndView getReadonlyReport(@PathVariable Long id) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("report", reportService.getReportById(id));
		modelAndView.setViewName("admin/roreport");
		return modelAndView;
	}

	/*
	 * Clicking Save button on report screen
	 */
	@PostMapping(value = "/process", params = "save")
	public ModelAndView saveReport(@ModelAttribute Report report) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		report.setOwner(user.getId());
		report.setStatus(ReportStatus.PROGRESS);
		reportService.save(report);

		return backToDashboard(user.getId());
	}

	/*
	 * Clicking Submit(approval) button on report screen
	 */
	@PostMapping(value = "/process", params = "approval")
	public ModelAndView submitReport(@ModelAttribute Report report) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		report.setOwner(user.getId());
		report.setStatus(ReportStatus.SUBMITTED);
		ReportHistory history = new ReportHistory();
		history.setModifier(user);
		history.setReason(ReportUtil.REPORT_APPROVAL);
		history.setStatus(report.getStatus());
		report.getHistory().add(history);
		reportService.save(report);

		return backToDashboard(user.getId());
	}

	/*
	 * Clicking Cancel button on report screen
	 */
	@PostMapping(value = "/process", params = "cancel")
	public ModelAndView cancelReport(@ModelAttribute Report report) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());

		return backToDashboard(user.getId());
	}

	/*
	 * Clicking Cancel button on report screen
	 */
	@PostMapping(value = "/process", params = "delete")
	public ModelAndView deleteReport(@ModelAttribute Report report) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		reportService.delete(report);

		return backToDashboard(user.getId());
	}

	/*
	 * Clicking 'set defaults' button on report screen
	 */
	@PostMapping(value = "/process", params = "defaults")
	public ModelAndView setDefaultReport(@ModelAttribute Report report) {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		Long originalId = report.getId();
		Report defaultReport = reportService.getReportWithDefaults(user.getId());

		if (defaultReport != null) {
			report.setId(defaultReport.getId());
		}

		report.setOwner(user.getId());
		report.setStatus(ReportStatus.DEFAULT);
		reportService.save(report);
		report.setId(originalId);
		modelAndView.setViewName("admin/report");
		return modelAndView;
	}

	/*
	 * Accessing report screen from 'Create Report' navbar
	 */
	@GetMapping(value = "/admin/report")
	public ModelAndView createReport() {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		Report report = reportService.getReportWithDefaults(user.getId());
		report.setId(null);
		modelAndView.addObject("report", report);
		modelAndView.setViewName("admin/report");
		return modelAndView;
	}

	/*
	 * Approving or Rejecting a submitted report
	 */
	@PostMapping(value = "/approval")
	public ModelAndView reportApproval(@RequestParam(required=false) String approve,
			@RequestParam String reason,
			@RequestParam Long id) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		Report report = reportService.getReportById(id);
		ReportHistory history = new ReportHistory();
		history.setModifier(user);
		history.setReason(reason);
		if (approve != null) {
			history.setStatus(ReportStatus.APPROVED);
			report.setStatus(ReportStatus.APPROVED);
		} else {
			history.setStatus(ReportStatus.REJECTED);
			report.setStatus(ReportStatus.REJECTED);
		}
		report.getHistory().add(history);
		reportService.save(report);
		return backToDashboard(user.getId());
	}

	/*
	 * Data necessary to go back to the dashboard
	 */
	private ModelAndView backToDashboard(Long userId) {
		List<Report> progress = reportService.dashboardReports(userId, ReportStatus.PROGRESS);
		List<Report> approved = reportService.dashboardReports(userId, ReportStatus.APPROVED);
		List<Report> submitted = reportService.dashboardReports(userId, ReportStatus.SUBMITTED);
		List<Report> rejected = reportService.dashboardReports(userId, ReportStatus.REJECTED);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("progressReports", progress);
		modelAndView.addObject("rejectedReports", rejected);
		modelAndView.addObject("approvedReports", approved);
		modelAndView.addObject("submittedReports", submitted);
		modelAndView.setViewName("admin/home");
		return modelAndView;
	}

}
