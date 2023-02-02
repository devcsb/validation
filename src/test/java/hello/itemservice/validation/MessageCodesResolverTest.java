package hello.itemservice.validation;


import org.junit.jupiter.api.Test;
import org.springframework.validation.DefaultMessageCodesResolver;
import org.springframework.validation.MessageCodesResolver;

import static org.assertj.core.api.Assertions.assertThat;

public class MessageCodesResolverTest {
    MessageCodesResolver codesResolver = new DefaultMessageCodesResolver();

    @Test
    void messageCodesResolverObject() {
        // messageCodesResolver에 errorCode로 "required"를, objectName으로 "item"을 넣으면,
        // 아래와 같이 messageCode를 자세한 것부터 rough한 순으로 만들어준다.
        // messageCode = required.item
        // messageCode = required
        String[] messageCodes = codesResolver.resolveMessageCodes("required", "item");
        assertThat(messageCodes).containsExactly("required.item", "required");
    }

    @Test
    void messageCodesResolverField() {
        String[] messageCodes = codesResolver.resolveMessageCodes("required", "item", "itemName", String.class);
        assertThat(messageCodes).containsExactly(
                "required.item.itemName",
                "required.itemName",
                "required.java.lang.String",
                "required"
        );

        for (String messageCode : messageCodes) {
            System.out.println("messageCode = " + messageCode);
            //messageCode = required.item.itemName
            //messageCode = required.itemName
            //messageCode = required.java.lang.String
            //messageCode = required
        }

        //bindingResult.rejectValue("itemName", "required");  << 코드 호출시 내부 동작 흐름
        //1. rejectValue 호출시 넘겨받은 errorCode 인자를 통해, MessageCodesResolver의 resolveMessageCodes를 호출하여, 계층별 error 코드 배열을 만든다.
        //2. FieldError 객체를 만들 때, codes[]인자 값으로 1의 값을 넣어, FieldError를 만들어 반환한다.

        // AbstractBindingResult.java 의 rejectValue 부분 참고.
    }
/*
    **DefaultMessageCodesResolver의 기본 메시지 생성 규칙**

    객체 오류의 경우 다음 순서로 2가지 생성
        1.: code + "." + object name
        2.: code
    예) 오류 코드: required, object name: item
        1.: required.item
        2.: required

    필드 오류의 경우 다음 순서로 4가지 메시지 코드 생성
        1.: code + "." + object name + "." + field
        2.: code + "." + field
        3.: code + "." + field type
        4.: code
    예) 오류 코드: typeMismatch, object name "user", field "age", field type: int
        1. "typeMismatch.user.age"
        2. "typeMismatch.age"
        3. "typeMismatch.int"
        4. "typeMismatch"
*/
}
