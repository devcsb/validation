package hello.itemservice.web.validation;

import hello.itemservice.web.validation.form.ItemSaveForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/validation/api/items")
public class ValidationItemApiController {

    /**
     * 요청 실패에는 두 가지 경우로 나뉜다.
     * 1. 실패 요청 : JSON을 객체로 생성하는 것 자체를 실패한 경우 (HttpMessageConverter 에서 실패-> 컨트롤러 호출 전에 예외 터져서 반환.)
     * 2. 검증 오류 요청 : JSON을 객체로 생성하는데는 성공했으나, Validation에서 실패함. (ObjectError, FieldError를 반환.)
     */
    @PostMapping("/add")
    public Object addItem(@RequestBody @Validated ItemSaveForm form, BindingResult bindingResult) {
        log.info("API 컨트롤러 호출");

        if (bindingResult.hasErrors()) {
            log.info("검증 오류 발생 errors={}", bindingResult);
            return bindingResult.getAllErrors(); // 실제로는, 필요한 데이터만 뽑아서 API 스펙을 정의하고 반환용 객체를 만들어 반환한다.
        }

        log.info("성공 로직 실행");
        return form;
    }
}
