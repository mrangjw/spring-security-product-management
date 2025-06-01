package com.example.assignment2.controller;

import com.example.assignment2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String adminDashboard(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        return "admin/dashboard";
    }

    @PostMapping("/makeAdmin/{userId}")
    public String makeAdmin(@PathVariable Long userId, RedirectAttributes redirectAttributes) {
        userService.makeAdmin(userId);
        redirectAttributes.addFlashAttribute("message", "사용자가 관리자로 승격되었습니다.");
        return "redirect:/admin";
    }
}