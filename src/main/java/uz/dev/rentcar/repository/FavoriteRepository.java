package uz.dev.rentcar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.dev.rentcar.entity.Favorite;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
}