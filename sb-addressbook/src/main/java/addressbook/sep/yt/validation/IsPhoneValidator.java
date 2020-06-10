package addressbook.sep.yt.validation;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsPhoneValidator implements ConstraintValidator<IsPhone, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        /**
         * 検証対象フィールドの値がnullではない場合、更にisEmptyで空かチェックを行う。
         * 検証対象フィールドの値が空でない場合isPhoneValidで電話番号の形式に則っているかチェックを行う。
         */
        if(value == null) {
            return true;
        }

        if(value.isEmpty()) {
            return true;
        } else {
            return isPhoneValid(value);
        }
    }

    /**
     *
     * @param value 検証するフィールドの値
     * @return true の場合値の形式が"000-0000-0000"の形式に則っている
     */
    private boolean isPhoneValid(String value) {
        String regex = "\\d{3}-\\d{4}-\\d{4}";
        if(Pattern.matches(regex, value)) {
            return true;
        }

        return false;
    }

}
