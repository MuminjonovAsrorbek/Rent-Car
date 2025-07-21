package uz.dev.rentcar.service.template;

import uz.dev.rentcar.payload.FavoriteDTO;

import java.util.List;

public interface FavoriteService {

    List<FavoriteDTO> read(Long userId);

    FavoriteDTO create(FavoriteDTO favoriteDTO);

    void delete(Long id);
}
