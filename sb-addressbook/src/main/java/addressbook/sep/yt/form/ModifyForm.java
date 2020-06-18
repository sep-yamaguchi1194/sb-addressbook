package addressbook.sep.yt.form;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import addressbook.sep.yt.validation.ByteSize;
import addressbook.sep.yt.validation.IsPhone;
import lombok.Data;

@Data
public class ModifyForm implements Serializable {

    private int id;

    @NotEmpty(message = "名前は必須項目です")
    @ByteSize(size = 40, message = "名前は全角20文字以内で入力してください")
    private String name;

    @NotEmpty(message = "住所は必須項目です")
    @ByteSize(size = 80, message = "住所は全角40文字以内で入力してください")
    private String address;

    @IsPhone(message = "電話番号は「000-0000-0000」の形式で入力してください")
    private String phone;

    @Pattern(regexp = "\\d")
    private String categoryId;
}
