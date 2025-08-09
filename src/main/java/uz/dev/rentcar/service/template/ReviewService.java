package uz.dev.rentcar.service.template;

import uz.dev.rentcar.entity.User;
import uz.dev.rentcar.payload.ReviewDTO;
import uz.dev.rentcar.payload.response.PageableDTO;

public interface ReviewService {

    ReviewDTO read(Long id);

    PageableDTO readAll(Long carId, int page, int size);

    ReviewDTO create(ReviewDTO reviewDTO);

    ReviewDTO update(Long id, ReviewDTO reviewDTO, User currentUser);

    void delete(Long id, User currentUser);

}
