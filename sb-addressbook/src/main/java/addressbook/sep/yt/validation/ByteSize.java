package addressbook.sep.yt.validation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * 本アノテーション(@ByteSize)を利用する際に指定するパラメータ
 *
 * (必須)size 対象フィールドの許容するバイト数を整数値で指定する
 * encoding 対象フィールドのバイト数を検証する時に使用する文字エンコーディングの名前(指定しない場合:"SJIS")
 * message バリデーションエラー時のメッセージ
 */

@Documented
@Constraint(validatedBy = {ByteSizeValidator.class})
@Target({FIELD})
@Retention(RUNTIME)
public @interface ByteSize {
    String message() default "{validation.ByteSize.message}";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String encoding() default "SJIS";
    int size() ;

    @Target({FIELD})
    @Retention(RUNTIME)
    @Documented
    public @interface List {
        ByteSize[] value();
    }
}
