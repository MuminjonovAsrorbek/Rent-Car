package uz.dev.rentcar.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.dev.rentcar.entity.Car;
import uz.dev.rentcar.entity.Favorite;
import uz.dev.rentcar.entity.User;
import uz.dev.rentcar.mapper.FavoriteMapper;
import uz.dev.rentcar.payload.FavoriteDTO;
import uz.dev.rentcar.repository.CarRepository;
import uz.dev.rentcar.repository.FavoriteRepository;
import uz.dev.rentcar.repository.UserRepository;
import uz.dev.rentcar.service.template.FavoriteService;

import java.util.List;
import java.util.stream.Collectors;

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
    @Transactional
    public void delete(Long id) {

        Favorite favorite = favoriteRepository.getByIdOrThrow(id);

        favoriteRepository.delete(favorite);
    }

}
