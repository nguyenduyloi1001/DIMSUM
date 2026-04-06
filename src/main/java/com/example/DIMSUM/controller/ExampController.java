package com.example.DIMSUM.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/qrmenu")
public class ExampController {
    @GetMapping
    public String handleQRScan(@RequestParam(value = "table", required = false) String tableNumber,
                               Model model) {
        if (tableNumber != null) {
            model.addAttribute("message", "Xin chào! Bạn đang ở bàn " + tableNumber);
            model.addAttribute("tableNumber", tableNumber);
        } else {
            model.addAttribute("message", "Xin chào!");
        }
        return "qr-page"; // file qr-page.html
    }
}
