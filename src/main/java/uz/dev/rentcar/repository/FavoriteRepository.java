package uz.dev.rentcar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import uz.dev.rentcar.entity.Favorite;
import uz.dev.rentcar.exceptions.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    List<Favorite> findAllByUserId(Long userId);

    default Favorite getByIdOrThrow(Long id) {
        return findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Favorite not found with id : " + id, HttpStatus.NOT_FOUND));
    }


    Optional<Favorite> findByUserIdAndCarId(Long userId, Long carId);
}