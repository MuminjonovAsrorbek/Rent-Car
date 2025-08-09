package uz.dev.rentcar.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.dev.rentcar.entity.Car;
import uz.dev.rentcar.entity.Favorite;
import uz.dev.rentcar.entity.User;
import uz.dev.rentcar.mapper.FavoriteMapper;
import uz.dev.rentcar.payload.FavoriteDTO;
import uz.dev.rentcar.payload.TgFavoriteDTO;
import uz.dev.rentcar.repository.CarRepository;
import uz.dev.rentcar.repository.FavoriteRepository;
import uz.dev.rentcar.repository.UserRepository;
import uz.dev.rentcar.service.template.FavoriteService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by: asrorbek
 * DateTime: 7/30/25 22:44
 **/

@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository favoriteRepository;

    private final FavoriteMapper favoriteMapper;

    private final CarRepository carRepository;

    private final UserRepository userRepository;

    @Override
    public List<FavoriteDTO> read(Long userId) {

        List<Favorite> favorites = favoriteRepository.findAllByUserId(userId);

        return favorites.stream()
                .map(favoriteMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public FavoriteDTO create(FavoriteDTO favoriteDTO) {

        Car car = carRepository.getByIdOrThrow(favoriteDTO.getCarId());

        User user = userRepository.findByIdOrThrowException(favoriteDTO.getUserId());

        Favorite favorite = new Favorite();

        favorite.setCar(car);
        favorite.setUser(user);

        favoriteRepository.save(favorite);

        return favoriteMapper.toDTO(favorite);
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public void delete(Long id) {

        Favorite favorite = favoriteRepository.getByIdOrThrow(id);
        favoriteRepository.delete(favorite);
        favoriteRepository.flush();

    }

    @Override
    public List<FavoriteDTO> getMyFavorites(User currentUser) {

        List<Favorite> favorites = favoriteRepository.findAllByUserId(currentUser.getId());

        return favoriteMapper.toDTO(favorites);
    }

    @Override
    public TgFavoriteDTO getCheck(Long userId, Long carId) {

        Optional<Favorite> optionalFavorite = favoriteRepository.findByUserIdAndCarId(userId, carId);

        return optionalFavorite.map(favorite -> new TgFavoriteDTO(favorite.getId(), true)).orElseGet(() -> new TgFavoriteDTO(null, false));

    }

}
