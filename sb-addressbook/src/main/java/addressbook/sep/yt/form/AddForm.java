package addressbook.sep.yt.form;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import addressbook.sep.yt.validation.ByteSize;
import addressbook.sep.yt.validation.IsPhone;
import lombok.Data;

@Data
public class AddForm implements Serializable{

    @NotEmpty(message = "名前を入力してください")
    @ByteSize(size = 40, message = "名前は40桁以内で入力してください")
    private String name;

    @NotEmpty(message = "住所を入力してください")
    @ByteSize(size = 80, message = "住所は80桁以内で入力してください")
    private String address;

    @IsPhone(message = "電話番号の形式で入力してください「000-0000-0000」")
    private String phone;

    @Pattern(regexp = "\\d")
    private String categoryId;
}
