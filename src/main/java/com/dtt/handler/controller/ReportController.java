package com.dtt.handler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.dtt.handler.service.ReportService;

@Controller
public class ReportController {
	
    @Autowired
    private ReportService reportService;

	@RequestMapping(value = "/admin/report/{id}", method = RequestMethod.GET)
	public ModelAndView getReport(@PathVariable int id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("report", reportService.getReportById(id));
        modelAndView.setViewName("admin/report");
        return modelAndView;
	}
}
