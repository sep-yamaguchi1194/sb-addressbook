package addressbook.sep.yt.validation;

import java.io.UnsupportedEncodingException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ByteSizeValidator implements ConstraintValidator<ByteSize, String> {
    /**
     * 文字エンコーディング
     */
    private String encoding;

    /**
     * 許容する最大バイト数
     */
    private int size;

    @Override
    public void initialize(ByteSize byteSize) {
        encoding = byteSize.encoding();
        size = byteSize.size();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        return isByteSizeValid(value);
    }

    /**
     * @param value 検証する文字列
     * @return true 検証する文字列が指定バイト数以下である
     */
    private boolean isByteSizeValid(String value) {

        try {
            byte[] bytes = value.getBytes(encoding);

            if (bytes.length > size) {
                return false;
            }
        } catch (UnsupportedEncodingException e) {
            return false;
        }

        return true;
    }

}
