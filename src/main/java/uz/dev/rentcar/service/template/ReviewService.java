package uz.dev.rentcar.service.template;

import uz.dev.rentcar.entity.User;
import uz.dev.rentcar.payload.ReviewDTO;

import java.util.List;

public interface ReviewService {

    ReviewDTO read(Long id);

    List<ReviewDTO> readAll(Long carId);

    ReviewDTO create(ReviewDTO reviewDTO);

    ReviewDTO update(Long id, ReviewDTO reviewDTO, User currentUser);

    void delete(Long id, User currentUser);

}
