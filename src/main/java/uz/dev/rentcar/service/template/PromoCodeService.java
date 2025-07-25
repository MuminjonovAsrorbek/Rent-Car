package uz.dev.rentcar.service.template;

import org.apache.coyote.BadRequestException;
import uz.dev.rentcar.payload.PromoCodeDTO;
import uz.dev.rentcar.payload.response.PageableDTO;

public interface PromoCodeService {

    PromoCodeDTO read(Long id);

    PageableDTO readAll(int page, int size);

    boolean codeValidate(String code);

    PromoCodeDTO create(PromoCodeDTO promoCodeDTO);

    PromoCodeDTO update(Long id, PromoCodeDTO promoCodeDTO);

    void delete(Long id);

}
