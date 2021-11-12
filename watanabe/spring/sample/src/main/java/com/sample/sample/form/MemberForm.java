package com.sample.sample.form;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

/**
 * member request dto
 */
@Data
public class MemberForm implements Serializable {

    /**
     * メールアドレス
     */
    @NotEmpty(message = "メールアドレスを入力してください")
    @Size(max = 255, message = "メールアドレスは255桁以内で入力してください")
    @Pattern(regexp = "^[a-zA-Z0-9_+-]+(.[a-zA-Z0-9_+-]+)*@([a-zA-Z0-9][a-zA-Z0-9-]*[a-zA-Z0-9]*\\.)+[a-zA-Z]{2,}$", message = "メールアドレスの形式で入力してください")
    private String mailAddress;

    /**
     * 名前
     */
    @NotEmpty(message = "名前を入力してください")
    @Size(max = 255, message = "名前は255桁以内で入力してください")
    private String name;
}
