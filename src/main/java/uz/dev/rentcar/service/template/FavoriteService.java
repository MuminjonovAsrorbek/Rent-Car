package uz.dev.rentcar.service.template;

import uz.dev.rentcar.entity.User;
import uz.dev.rentcar.payload.FavoriteDTO;
import uz.dev.rentcar.payload.TgFavoriteDTO;

import java.util.List;

public interface FavoriteService {

    List<FavoriteDTO> read(Long userId);

    FavoriteDTO create(FavoriteDTO favoriteDTO);

    void delete(Long id);

    List<FavoriteDTO> getMyFavorites(User currentUser);

    TgFavoriteDTO getCheck(Long userId, Long carId);
}
