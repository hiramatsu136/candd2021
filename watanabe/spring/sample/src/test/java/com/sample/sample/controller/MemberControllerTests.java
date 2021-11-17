package com.sample.sample.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.sample.sample.entity.Member;
import com.sample.sample.form.MemberForm;
import com.sample.sample.form.MemberUpdForm;
import com.sample.sample.service.MemberService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@AutoConfigureMockMvc
@SpringBootTest
public class MemberControllerTests {
    // Httpリクエスト・レスポンスを扱うためのMockオブジェクト
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    private List<Member> members;

    @BeforeEach
    void setUp() {
        Date now = new Date();
        Member m1 = new Member();
        m1 = new Member();
        m1.setMail_address("1234@aaa.com");
        m1.setName("test_name");
        m1.setCreated_date(now);
        m1.setCreated_user("created_user");
        m1.setUpdated_date(now);
        m1.setUpdated_user("updated_user");

        Member m2 = new Member();
        m2 = new Member();
        m2.setMail_address("99999@aaa.com");
        m2.setName("aaaa");
        m2.setCreated_date(now);
        m2.setCreated_user("created_user2");
        m2.setUpdated_date(now);
        m2.setUpdated_user("updated_user2");

        members = new ArrayList<Member>();
        members.add(m1);
        members.add(m2);
    }

    @DisplayName("displayListメソッドをテストする。/にアクセス")
    @Test
    @SuppressWarnings("unchecked")
    void test_01() {
        try {
            // mock set
            when(memberService.searchAll()).thenReturn(members);

            // getリクエストでviewを指定し、httpステータスでリクエストの成否を判定
            MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.get("/"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.view().name("member/list")).andReturn();
            Map<String, Object> model = result.getModelAndView().getModel();
            assertTrue(model.containsKey("memberlist"));
            assertNotNull(model.get("memberlist"));
            assertTrue(model.get("memberlist") instanceof List<?>);
            List<Member> actuaList = (List<Member>) model.get("memberlist");
            assertEquals(2, actuaList.size());
            assertArrayEquals(members.toArray(), actuaList.toArray());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @DisplayName("displayListメソッドをテストする。/member/listにアクセス")
    @Test
    @SuppressWarnings("unchecked")
    void test_02() {
        try {
            // mock set
            when(memberService.searchAll()).thenReturn(members);

            // getリクエストでviewを指定し、httpステータスでリクエストの成否を判定
            MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.get("/member/list"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.view().name("member/list")).andReturn();
            Map<String, Object> model = result.getModelAndView().getModel();
            assertTrue(model.containsKey("memberlist"));
            assertNotNull(model.get("memberlist"));
            assertTrue(model.get("memberlist") instanceof List<?>);
            List<Member> actuaList = (List<Member>) model.get("memberlist");
            assertEquals(2, actuaList.size());
            assertArrayEquals(members.toArray(), actuaList.toArray());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @DisplayName("displayAddメソッドをテストする")
    @Test
    void test_03() throws Exception {
        // getリクエストでviewを指定し、httpステータスでリクエストの成否を判定
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.get("/member/add"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("member/add")).andReturn();
        Map<String, Object> model = result.getModelAndView().getModel();
        assertTrue(model.containsKey("memberForm"));
        assertTrue(model.get("memberForm") instanceof MemberForm);
    }

    @DisplayName("createメソッドをテストする form未入力の場合")
    @Test
    @SuppressWarnings("unchecked")
    void test_04() throws Exception {
        // form
        MemberForm form = new MemberForm();
        // postリクエストでviewを指定し、httpステータスでリクエストの成否を判定
        MvcResult result = this.mockMvc
                .perform(MockMvcRequestBuilders.post("/member/create").flashAttr("memberForm", form))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("member/add")).andReturn();
        Map<String, Object> model = result.getModelAndView().getModel();
        assertTrue(model.containsKey("validationError"));
        assertTrue(model.get("validationError") instanceof List<?>);
        List<String> actualList = (List<String>) model.get("validationError");
        assertEquals(2, actualList.size());
        assertTrue(actualList.contains("メールアドレスを入力してください"));
        assertTrue(actualList.contains("名前を入力してください"));
    }

    @DisplayName("createメソッドをテストする form桁数overの場合")
    @Test
    @SuppressWarnings("unchecked")
    void test_05() throws Exception {
        // form
        MemberForm form = new MemberForm();
        form.setMailAddress(
                "mailAddressaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@123.vom");
        form.setName(
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        // postリクエストでviewを指定し、httpステータスでリクエストの成否を判定
        MvcResult result = this.mockMvc
                .perform(MockMvcRequestBuilders.post("/member/create").flashAttr("memberForm", form))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("member/add")).andReturn();
        Map<String, Object> model = result.getModelAndView().getModel();
        assertTrue(model.containsKey("validationError"));
        assertTrue(model.get("validationError") instanceof List<?>);
        List<String> actualList = (List<String>) model.get("validationError");
        assertEquals(2, actualList.size());
        assertTrue(actualList.contains("メールアドレスは255桁以内で入力してください"));
        assertTrue(actualList.contains("名前は255桁以内で入力してください"));
    }

    @DisplayName("createメソッドをテストする formメールアドレス形式以外の場合")
    @Test
    @SuppressWarnings("unchecked")
    void test_06() throws Exception {
        // form
        MemberForm form = new MemberForm();
        form.setMailAddress("1234");
        form.setName(members.get(0).getName());
        // postリクエストでviewを指定し、httpステータスでリクエストの成否を判定
        MvcResult result = this.mockMvc
                .perform(MockMvcRequestBuilders.post("/member/create").flashAttr("memberForm", form))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("member/add")).andReturn();
        Map<String, Object> model = result.getModelAndView().getModel();
        assertTrue(model.containsKey("validationError"));
        assertTrue(model.get("validationError") instanceof List<?>);
        List<String> actualList = (List<String>) model.get("validationError");
        assertEquals(1, actualList.size());
        assertTrue(actualList.contains("メールアドレスの形式で入力してください"));
    }

    @DisplayName("createメソッドをテストする 正常系")
    @Test
    void test_07() throws Exception {
        // form
        MemberForm form = new MemberForm();
        form.setMailAddress(members.get(0).getMail_address());
        form.setName(members.get(0).getName());

        // mock
        doNothing().when(memberService).create(form);

        // postリクエストでviewを指定し、httpステータスでリクエストの成否を判定
        this.mockMvc.perform(MockMvcRequestBuilders.post("/member/create").flashAttr("memberForm", form))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/member/list"));
    }

    @DisplayName("createメソッドをテストする DuplicateKeyException")
    @Test
    @SuppressWarnings("unchecked")
    void test_08() throws Exception {
        // form
        MemberForm form = new MemberForm();
        form.setMailAddress(members.get(0).getMail_address());
        form.setName(members.get(0).getName());

        // mock
        doThrow(new DuplicateKeyException("test")).when(memberService).create(form);

        // postリクエストでviewを指定し、httpステータスでリクエストの成否を判定
        MvcResult result = this.mockMvc
                .perform(MockMvcRequestBuilders.post("/member/create").flashAttr("memberForm", form))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("member/add")).andReturn();
        Map<String, Object> model = result.getModelAndView().getModel();
        assertTrue(model.containsKey("validationError"));
        assertTrue(model.get("validationError") instanceof List<?>);
        List<String> actualList = (List<String>) model.get("validationError");
        assertEquals(1, actualList.size());
        assertTrue(actualList.contains("登録済みのメールアドレスです。"));
    }

    @DisplayName("createメソッドをテストする Exception")
    @Test
    @SuppressWarnings("unchecked")
    void test_09() throws Exception {
        // form
        MemberForm form = new MemberForm();
        form.setMailAddress(members.get(0).getMail_address());
        form.setName(members.get(0).getName());

        // mock
        doThrow(new RuntimeException("test")).when(memberService).create(form);

        // postリクエストでviewを指定し、httpステータスでリクエストの成否を判定
        MvcResult result = this.mockMvc
                .perform(MockMvcRequestBuilders.post("/member/create").flashAttr("memberForm", form))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("member/add")).andReturn();
        Map<String, Object> model = result.getModelAndView().getModel();
        assertTrue(model.containsKey("validationError"));
        assertTrue(model.get("validationError") instanceof List<?>);
        List<String> actualList = (List<String>) model.get("validationError");
        assertEquals(1, actualList.size());
        assertTrue(actualList.contains("test"));
    }

    @DisplayName("displayViewメソッドをテストする")
    @Test
    void test_10() throws Exception {
        // mock set
        when(memberService.searchByMailAddress(members.get(0).getMail_address())).thenReturn(members.get(0));
        // getリクエストでviewを指定し、httpステータスでリクエストの成否を判定
        MvcResult result = this.mockMvc
                .perform(MockMvcRequestBuilders.get("/member/" + members.get(0).getMail_address()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("member/view")).andReturn();
        Map<String, Object> model = result.getModelAndView().getModel();
        assertTrue(model.containsKey("memberUpdForm"));
        assertTrue(model.get("memberUpdForm") instanceof MemberUpdForm);
        MemberUpdForm actual = (MemberUpdForm) model.get("memberUpdForm");
        assertEquals(members.get(0).getMail_address(), actual.getMailAddress());
        assertEquals(members.get(0).getName(), actual.getName());
        assertEquals(members.get(0).getMail_address(), actual.getBeforeMailAddress());
    }

    @DisplayName("updateメソッドをテストする form未入力の場合")
    @Test
    @SuppressWarnings("unchecked")
    void test_11() throws Exception {
        // form
        MemberUpdForm form = new MemberUpdForm();
        // postリクエストでviewを指定し、httpステータスでリクエストの成否を判定
        MvcResult result = this.mockMvc
                .perform(MockMvcRequestBuilders.post("/member/update").flashAttr("memberUpdForm", form))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("member/view")).andReturn();
        Map<String, Object> model = result.getModelAndView().getModel();
        assertTrue(model.containsKey("validationError"));
        assertTrue(model.get("validationError") instanceof List<?>);
        List<String> actualList = (List<String>) model.get("validationError");
        assertEquals(2, actualList.size());
        assertTrue(actualList.contains("メールアドレスを入力してください"));
        assertTrue(actualList.contains("名前を入力してください"));
    }

    @DisplayName("updateメソッドをテストする form桁数overの場合")
    @Test
    @SuppressWarnings("unchecked")
    void test_12() throws Exception {
        // form
        MemberUpdForm form = new MemberUpdForm();
        form.setMailAddress(
                "mailAddressaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@123.vom");
        form.setName(
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        // postリクエストでviewを指定し、httpステータスでリクエストの成否を判定
        MvcResult result = this.mockMvc
                .perform(MockMvcRequestBuilders.post("/member/update").flashAttr("memberUpdForm", form))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("member/view")).andReturn();
        Map<String, Object> model = result.getModelAndView().getModel();
        assertTrue(model.containsKey("validationError"));
        assertTrue(model.get("validationError") instanceof List<?>);
        List<String> actualList = (List<String>) model.get("validationError");
        assertEquals(2, actualList.size());
        assertTrue(actualList.contains("メールアドレスは255桁以内で入力してください"));
        assertTrue(actualList.contains("名前は255桁以内で入力してください"));
    }

    @DisplayName("updateメソッドをテストする formメールアドレス形式以外の場合")
    @Test
    @SuppressWarnings("unchecked")
    void test_13() throws Exception {
        // form
        MemberUpdForm form = new MemberUpdForm();
        form.setMailAddress("1234");
        form.setName(members.get(0).getName());
        // postリクエストでviewを指定し、httpステータスでリクエストの成否を判定
        MvcResult result = this.mockMvc
                .perform(MockMvcRequestBuilders.post("/member/update").flashAttr("memberUpdForm", form))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("member/view")).andReturn();
        Map<String, Object> model = result.getModelAndView().getModel();
        assertTrue(model.containsKey("validationError"));
        assertTrue(model.get("validationError") instanceof List<?>);
        List<String> actualList = (List<String>) model.get("validationError");
        assertEquals(1, actualList.size());
        assertTrue(actualList.contains("メールアドレスの形式で入力してください"));
    }

    @DisplayName("updateメソッドをテストする 正常系")
    @Test
    void test_14() throws Exception {
        // form
        MemberUpdForm form = new MemberUpdForm();
        form.setMailAddress("3.14py@abc.com");
        form.setName(members.get(0).getName());
        form.setBeforeMailAddress(members.get(0).getMail_address());
        // mock
        doNothing().when(memberService).updateByMailAddress(form);

        // postリクエストでviewを指定し、httpステータスでリクエストの成否を判定
        this.mockMvc.perform(MockMvcRequestBuilders.post("/member/update").flashAttr("memberUpdForm", form))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/member/list"));
    }

    @DisplayName("updateメソッドをテストする DuplicateKeyException")
    @Test
    @SuppressWarnings("unchecked")
    void test_15() throws Exception {
        // form
        MemberUpdForm form = new MemberUpdForm();
        form.setMailAddress("3.14py@abc.com");
        form.setName(members.get(0).getName());
        form.setBeforeMailAddress(members.get(0).getMail_address());

        // mock
        doThrow(new DuplicateKeyException("test")).when(memberService).updateByMailAddress(form);

        // postリクエストでviewを指定し、httpステータスでリクエストの成否を判定
        MvcResult result = this.mockMvc
                .perform(MockMvcRequestBuilders.post("/member/update").flashAttr("memberUpdForm", form))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("member/view")).andReturn();
        Map<String, Object> model = result.getModelAndView().getModel();
        assertTrue(model.containsKey("validationError"));
        assertTrue(model.get("validationError") instanceof List<?>);
        List<String> actualList = (List<String>) model.get("validationError");
        assertEquals(1, actualList.size());
        assertTrue(actualList.contains("登録済みのメールアドレスです。更新できません。"));
    }

    @DisplayName("updateメソッドをテストする Exception")
    @Test
    @SuppressWarnings("unchecked")
    void test_16() throws Exception {
        // form
        MemberUpdForm form = new MemberUpdForm();
        form.setMailAddress("3.14py@abc.com");
        form.setName(members.get(0).getName());
        form.setBeforeMailAddress(members.get(0).getMail_address());

        // mock
        doThrow(new RuntimeException("test")).when(memberService).updateByMailAddress(form);

        // postリクエストでviewを指定し、httpステータスでリクエストの成否を判定
        MvcResult result = this.mockMvc
                .perform(MockMvcRequestBuilders.post("/member/update").flashAttr("memberUpdForm", form))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("member/view")).andReturn();
        Map<String, Object> model = result.getModelAndView().getModel();
        assertTrue(model.containsKey("validationError"));
        assertTrue(model.get("validationError") instanceof List<?>);
        List<String> actualList = (List<String>) model.get("validationError");
        assertEquals(1, actualList.size());
        assertTrue(actualList.contains("test"));
    }

    @DisplayName("deleteメソッドをテストする。正常系")
    @Test
    void test_17() throws Exception {
        // mock set
        doNothing().when(memberService).deleteByMailAddress("3.14py@abc.com");

        // getリクエストでviewを指定し、httpステータスでリクエストの成否を判定
        this.mockMvc.perform(MockMvcRequestBuilders.get("/member/3.14py@abc.com/delete"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/member/list"));
    }

    @DisplayName("deleteメソッドをテストする。異常系")
    @Test
    @SuppressWarnings("unchecked")
    void test_18() {
        try {
            // mock set
            doThrow(new RuntimeException("test")).when(memberService).deleteByMailAddress(members.get(0).getMail_address());
    
            // getリクエストでviewを指定し、httpステータスでリクエストの成否を判定
            MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.get("/member/" + members.get(0).getMail_address() + "/delete"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.view().name("member/error")).andReturn();

            Map<String, Object> model = result.getModelAndView().getModel();
            assertTrue(model.containsKey("errorList"));
            assertTrue(model.get("errorList") instanceof List<?>);
            List<String> actualList = (List<String>) model.get("errorList");
            assertEquals(1, actualList.size());
            assertTrue(actualList.contains("test"));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}
