package com.sample.sample.controller;

import java.util.Arrays;
import java.util.List;

import com.sample.sample.entity.Member;
import com.sample.sample.service.MemberService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MemberController {
    @Autowired
    private MemberService memberService;

    @GetMapping(value = "/member/list")
    public String displayList(Model model) {
      List<Member> memberlist = memberService.searchAll();
      System.out.println("--------Member　取得できたか--------");
      System.out.println(Arrays.toString(memberlist.toArray()));
      System.out.println(memberlist.size());
      for (Member member : memberlist) {
          System.out.println(member.getMail_address());
          System.out.println(member.getName());
          System.out.println(member.getCreated_date());
          System.out.println(member.getCreated_user());
          System.out.println(member.getUpdated_date());
          System.out.println(member.getUpdated_user());
      }
      System.out.println("-----------------------------------");
      model.addAttribute("memberlist", memberlist);
      return "member/list";
    }

    @GetMapping(value = "/member/add")
    public String displayAdd(Model model) {
      return "member/add";
    }

    @GetMapping("/member/{mailAdress}")
    public String displayView(@PathVariable String mailAdress, Model model) {
      return "member/view";
    }
}
