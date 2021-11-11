package com.sample.sample.controller;

import java.util.List;

import com.sample.sample.entity.Member;
import com.sample.sample.service.MemberService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * member情報 Controller
 */
@Controller
public class MemberController {

  /**
   * member Service
   */
  @Autowired
  private MemberService memberService;

  /**
   * メンバー情報一覧画面表示
   * @param model Model
   * @return メンバー情報一覧画面
   */
  @GetMapping(value = "/member/list")
  public String displayList(Model model) {
    List<Member> memberlist = memberService.searchAll();
    model.addAttribute("memberlist", memberlist);
    return "member/list";
  }

  /**
   * 新規登録画面
   * @param model Model
   * @return 新規登録画面
   */
  @GetMapping(value = "/member/add")
  public String displayAdd(Model model) {
    return "member/add";
  }

  /**
   * メンバー情報詳細画面表示
   * @param mailAdress メールアドレス
   * @param model Model
   * @return メンバー情報詳細画面(dummy)
   */
  @GetMapping("/member/{mailAdress}")
  public String displayView(@PathVariable String mailAdress, Model model) {
    return "member/view";
  }
}
