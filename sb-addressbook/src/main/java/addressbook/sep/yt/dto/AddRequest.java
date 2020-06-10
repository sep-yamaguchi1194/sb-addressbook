package addressbook.sep.yt.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import addressbook.sep.yt.validation.ByteSize;
import addressbook.sep.yt.validation.IsPhone;
import lombok.Data;

@Data
public class AddRequest implements Serializable {
    /**
     * 名前
     */
    @NotEmpty(message = "名前を入力してください")
    @ByteSize(size = 40, message = "名前は40桁以内で入力してください")
    private String name;

    /**
     * 住所
     */
    @NotEmpty(message = "住所を入力してください")
    @ByteSize(size = 80, message = "住所は80桁以内で入力してください")
    private String address;

    /**
     * 電話番号
     */
    @IsPhone(message = "電話番号の形式で入力してください")
    private String phone;

    /**
     * カテゴリ
     */
    @Pattern(regexp = "\\d")
    private String category;
}
