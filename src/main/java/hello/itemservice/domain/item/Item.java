package hello.itemservice.domain.item;

import lombok.Data;

//하이버네이트 validator 구현체를 사용할 때만 제공되는 검증 기능.(org.hibernate.validator...)
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.ScriptAssert;

// 특정 구현에 관계없이 제공되는 표준 인터페이스(javax.validation...)
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

// 실무에서의 복잡한 검증 조건을 @ScriptAssert 로 검증하기엔 기능이 너무 부족하다. 오브젝트 검증의 경우는 직접 자바 코드로 작성하는 편이 낫다.
@Data
//@ScriptAssert(lang="javascript", script = "_this.price * _this.quantity >= 10000", message = "최소 주문금액은 10000원 이상입니다.")
public class Item {

    private Long id;

    @NotBlank(message = "공백은 허용하지 않습니다!") // 이렇게 메세지 정의할 수도 있고, properties 파일 바깥으로 빼낼 수도 있다.
    private String itemName;

    @NotNull
    @Range(min = 1000, max = 1000000)  // 하이버네이트 validator 구현체를 사용할 때만 제공되는 검증기능.
    private Integer price;

    @NotNull
    @Max(9999)
    private Integer quantity;

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
