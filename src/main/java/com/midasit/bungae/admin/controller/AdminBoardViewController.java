package com.midasit.bungae.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/admin")
public class AdminBoardViewController {
    @GetMapping(path = "/main")
    public String showMain() {
        return "/admin/admin_main";
    }

    @GetMapping(path = "/createNoticeForm")
    public String showCreateNoticeForm() {
        return "/admin/admin_notice";
    }

    @GetMapping(path = "/detailForm/{noticeNo}")
    public String showNoticeDetail(@PathVariable int noticeNo,
                                   Model model) {
        model.addAttribute("noticeNo", noticeNo);

        return "/admin/admin_detail";
    }

    @GetMapping(path = "/modifyForm/{noticeNo}")
    public String showModifyForm(@PathVariable int noticeNo,
                                 Model model) {
        model.addAttribute("noticeNo", noticeNo);

        return "/admin/admin_modify";
    }
}
