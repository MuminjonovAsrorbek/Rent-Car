package uz.dev.rentcar.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.dev.rentcar.entity.Car;
import uz.dev.rentcar.entity.Review;
import uz.dev.rentcar.entity.User;
import uz.dev.rentcar.entity.template.AbsLongEntity;
import uz.dev.rentcar.enums.BookingStatusEnum;
import uz.dev.rentcar.enums.RoleEnum;
import uz.dev.rentcar.mapper.ReviewMapper;
import uz.dev.rentcar.payload.ReviewDTO;
import uz.dev.rentcar.payload.response.PageableDTO;
import uz.dev.rentcar.repository.BookingRepository;
import uz.dev.rentcar.repository.CarRepository;
import uz.dev.rentcar.repository.ReviewRepository;
import uz.dev.rentcar.repository.UserRepository;
import uz.dev.rentcar.service.template.ReviewService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    private final ReviewMapper reviewMapper;

    private final UserRepository userRepository;

    private final CarRepository carRepository;

    private final BookingRepository bookingRepository;

    @Override
    public ReviewDTO read(Long id) {

        Review review = reviewRepository.getByIdOrThrow(id);

        return reviewMapper.toDTO(review);
    }

    @Override
    public PageableDTO readAll(Long carId, int page, int size) {

        Sort sort = Sort.by(AbsLongEntity.Fields.id).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Review> reviewPage = reviewRepository.findByCarId(carId, pageable);

        List<Review> reviews = reviewPage.getContent();

        return new PageableDTO(
                reviewPage.getSize(),
                reviewPage.getTotalElements(),
                reviewPage.getTotalPages(),
                reviewPage.hasNext(),
                reviewPage.hasPrevious(),
                reviewMapper.toDTO(reviews)
        );
    }

    @Override
    @Transactional
    public ReviewDTO create(ReviewDTO reviewDTO) {

        Review review = reviewMapper.toEntity(reviewDTO);

        User user = userRepository.findByIdOrThrowException(reviewDTO.getUserId());

        Car car = carRepository.getByIdOrThrow(reviewDTO.getCarId());

        if (!bookingRepository.existsByCarIdAndUserIdAndStatus(reviewDTO.getCarId(), user.getId(), BookingStatusEnum.COMPLETED)) {

            throw new AccessDeniedException("You can only review cars you have booked");
        }

        review.setRating(reviewDTO.getRating());
        review.setComment(reviewDTO.getComment());
        review.setCar(car);
        review.setUser(user);

        reviewRepository.save(review);

        return reviewMapper.toDTO(review);
    }

    @Override
    @Transactional
    public ReviewDTO update(Long id, ReviewDTO reviewDTO, User currentUser) {

        Review review = reviewRepository.getByIdOrThrow(id);

        if (!review.getUser().equals(currentUser) && !currentUser.getRole().equals(RoleEnum.ADMIN))
            throw new AccessDeniedException("You are not allowed to update this review");

        review.setRating(reviewDTO.getRating());

        review.setComment(reviewDTO.getComment());

        reviewRepository.save(review);

        return reviewMapper.toDTO(review);
    }

    @Override
    @Transactional
    public void delete(Long id, User currentUser) {

        Review review = reviewRepository.getByIdOrThrow(id);

        if (!review.getUser().equals(currentUser) && !currentUser.getRole().equals(RoleEnum.ADMIN))
            throw new AccessDeniedException("You are not allowed to delete this review");

        reviewRepository.delete(review);
    }
}
