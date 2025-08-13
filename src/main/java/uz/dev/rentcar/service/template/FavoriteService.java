package uz.dev.rentcar.service.template;

import uz.dev.rentcar.entity.User;
import uz.dev.rentcar.payload.FavoriteDTO;
import uz.dev.rentcar.payload.TgFavoriteDTO;
import uz.dev.rentcar.payload.response.PageableDTO;

import java.util.List;

public interface FavoriteService {

    List<FavoriteDTO> read(Long userId);

    FavoriteDTO create(FavoriteDTO favoriteDTO);

    void delete(Long id);

    PageableDTO getMyFavorites(User currentUser, int page, int size);

    TgFavoriteDTO getCheck(Long userId, Long carId);
}
