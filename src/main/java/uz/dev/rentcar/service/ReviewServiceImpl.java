package uz.dev.rentcar.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.dev.rentcar.entity.Car;
import uz.dev.rentcar.entity.Review;
import uz.dev.rentcar.entity.User;
import uz.dev.rentcar.exceptions.EntityNotFoundException;
import uz.dev.rentcar.mapper.ReviewMapper;
import uz.dev.rentcar.payload.ReviewDTO;
import uz.dev.rentcar.repository.CarRepository;
import uz.dev.rentcar.repository.ReviewRepository;
import uz.dev.rentcar.repository.UserRepository;
import uz.dev.rentcar.service.template.ReviewService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final UserRepository userRepository;
    private final CarRepository carRepository;

    @Override
    public ReviewDTO read(Long id) {

        Review review = reviewRepository.getByIdOrThrow(id);

        return reviewMapper.toDTO(review);
    }

    @Override
    public List<ReviewDTO> readAll(Long carId) {

        List<Review> reviews = reviewRepository.findByCarId(carId);

        return reviews.stream()
                .map(reviewMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ReviewDTO create(ReviewDTO reviewDTO) {

        Review review = reviewMapper.toEntity(reviewDTO);

        User user = userRepository.findByIdOrThrowException(reviewDTO.getUserId());
        Car car = carRepository.getByIdOrThrow(reviewDTO.getCarId());

        review.setRating(reviewDTO.getRating());
        review.setComment(reviewDTO.getComment());
        review.setCar(car);
        review.setUser(user);

        reviewRepository.save(review);

        return reviewMapper.toDTO(review);
    }

    @Override
    @Transactional
    public ReviewDTO update(Long id, ReviewDTO reviewDTO) {

        Review review = reviewRepository.getByIdOrThrow(id);

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found " + email));

        if (!review.getUser().getId().equals(user.getId()))
            throw new AccessDeniedException("You are not allowed to update this review");

        review.setRating(reviewDTO.getRating());
        review.setComment(reviewDTO.getComment());

        reviewRepository.save(review);

        return reviewMapper.toDTO(review);
    }

    @Override
    @Transactional
    public void delete(Long id) {

        Review review = reviewRepository.getByIdOrThrow(id);

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found " + email));

        if (!review.getUser().getId().equals(user.getId()))
            throw new AccessDeniedException("You are not allowed to update this review");

        reviewRepository.delete(review);
    }
}
