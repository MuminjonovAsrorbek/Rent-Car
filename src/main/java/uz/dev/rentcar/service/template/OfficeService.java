package uz.dev.rentcar.service.template;

import uz.dev.rentcar.payload.OfficeDTO;
import uz.dev.rentcar.payload.UserDTO;
import uz.dev.rentcar.payload.response.PageableDTO;

public interface OfficeService {

    OfficeDTO read(Long id);

    PageableDTO readAll(int page, int size);

    OfficeDTO create(OfficeDTO officeDTO);

    OfficeDTO update(Long id, OfficeDTO officeDTO);

    void delete(Long id);
}
