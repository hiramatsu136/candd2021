package com.sample.sample.controller;

import java.util.ArrayList;
import java.util.List;

import com.sample.sample.entity.Member;
import com.sample.sample.form.MemberForm;
import com.sample.sample.service.MemberService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
   * 
   * @param model Model
   * @return メンバー情報一覧画面
   */
  @GetMapping(value = { "/", "/member/list" })
  public String displayList(Model model) {
    List<Member> memberlist = memberService.searchAll();
    model.addAttribute("memberlist", memberlist);
    return "member/list";
  }

  /**
   * 新規登録画面
   * 
   * @param model Model
   * @return 新規登録画面
   */
  @GetMapping(value = "/member/add")
  public String displayAdd(Model model) {
    model.addAttribute("memberForm", new MemberForm());
    return "member/add";
  }

  /**
   * member新規登録
   * 
   * @param memberForm 入力フォーム
   * @param result     エラーハンドラ
   * @param model      Model
   * @return 正常：メンバー情報一覧画面、エラー：新規登録画面
   */
  @RequestMapping(value = "/member/create", method = RequestMethod.POST)
  public String create(@Validated @ModelAttribute MemberForm memberForm, BindingResult result, Model model) {
    // 引数について勉強用にメモを残す。
    // @Validatedは付与したFormに対してController#inputハンドラが呼び出される前にバリデーションチェックを行う。
    // 結果はBindingResult resultに格納される。
    // @ModelAttributeはPOSTされたFormをオブジェクトに紐付ける。

    List<String> errList = new ArrayList<String>();
    // 入力チェックエラー確認
    if (result.hasErrors()) {
      // エラーの場合
      for (ObjectError error : result.getAllErrors()) {
        // エラーメッセージをリストに格納
        errList.add(error.getDefaultMessage());
      }
      // 新規登録画面へ
      model.addAttribute("validationError", errList);
      return "member/add";
    }
    try {
      // 登録
      memberService.create(memberForm);
    } catch (DuplicateKeyException e) {
      // エラーメッセージ格納
      errList.add("登録済みのメールアドレスです。");
    } catch (Exception e) {
      // エラーメッセージ格納
      errList.add(e.getMessage());
    }
    if (!errList.isEmpty()) {
      // エラーの場合新規登録画面へ
      model.addAttribute("validationError", errList);
      return "member/add";
    }
    // 一覧画面へ
    return "redirect:/member/list";
  }

  /**
   * メンバー情報詳細画面表示
   * 
   * @param mailAdress メールアドレス
   * @param model      Model
   * @return メンバー情報詳細画面(dummy)
   */
  @GetMapping("/member/{mailAdress}")
  public String displayView(@PathVariable String mailAdress, Model model) {
    return "member/view";
  }
}
