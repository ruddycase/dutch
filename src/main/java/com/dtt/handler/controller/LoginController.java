package com.dtt.handler.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dtt.handler.model.Report;
import com.dtt.handler.model.User;
import com.dtt.handler.service.ReportService;
import com.dtt.handler.service.UserService;

@Controller
public class LoginController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private UserService userService;
    
    @GetMapping(value={"/", "/login"})
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }


    @GetMapping(value="/registration")
    public ModelAndView registration(){
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @PostMapping(value = "/registration")
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserByEmail(user.getEmail());
        if (userExists != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the email provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        } else {
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("registration");

        }
        return modelAndView;
    }

    @GetMapping(value="/admin/home")
    public ModelAndView home(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        Long userId = user.getId();

        return backToDashboard(userId);
    }
    
	/*
	 * Data necessary to go back to the dashboard
	 */
	private ModelAndView backToDashboard(Long userId) {
        List<Report> progress = reportService.dashboardReports(userId, 0);
        List<Report> accepted = reportService.dashboardReports(userId, 1);
        List<Report> submitted = reportService.dashboardReports(userId, 2);
        List<Report> rejected = reportService.dashboardReports(userId, 3);

		ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("progressReports", progress);
        modelAndView.addObject("rejectedReports", rejected);
        modelAndView.addObject("acceptedReports", accepted);
        modelAndView.addObject("submittedReports", submitted);
        modelAndView.setViewName("admin/home");
		return modelAndView;
	}
    
}
