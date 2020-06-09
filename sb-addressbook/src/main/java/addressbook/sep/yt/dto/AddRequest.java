package addressbook.sep.yt.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class AddRequest implements Serializable {
    /**
     * 名前
     */
    @NotEmpty(message = "名前を入力してください")
    @Size(max = 40, message = "名前は40桁以内で入力してください")
    private String name;

    /**
     * 住所
     */
    @Size(max = 80, message = "住所は80桁以内で入力してください")
    private String address;

    /**
     * 電話番号
     */
    @Pattern(regexp = "\\d{1,4}-\\d{1,4}-\\d{4}", message = "電話番号の形式で入力してください")
    private String phone;
}
