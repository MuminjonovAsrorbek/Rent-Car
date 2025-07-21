package uz.dev.rentcar.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.dev.rentcar.entity.Car;
import uz.dev.rentcar.mapper.CarMapper;
import uz.dev.rentcar.payload.AttachmentDTO;
import uz.dev.rentcar.payload.CarDTO;
import uz.dev.rentcar.payload.request.CreateCarDTO;
import uz.dev.rentcar.payload.response.PageableDTO;
import uz.dev.rentcar.repository.CarRepository;
import uz.dev.rentcar.service.template.AttachmentService;
import uz.dev.rentcar.service.template.CarService;

import java.io.IOException;
import java.util.List;

/**
 * Created by: asrorbek
 * DateTime: 7/20/25 13:43
 **/

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;

    private final CarMapper carMapper;

    private final AttachmentService attachmentService;

    @Override
    @Transactional
    public CarDTO createCar(CreateCarDTO carDTO, MultipartFile[] images) {

        try {

            Car car = carMapper.toEntity(carDTO);

            Car savedCar = carRepository.save(car);

            List<AttachmentDTO> attachmentDTOS = attachmentService.uploadFiles(images, savedCar);

            CarDTO dto = carMapper.toDTO(savedCar);

            dto.setAttachments(attachmentDTOS);

            return dto;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public CarDTO getCarById(Long id) {

        Car car = carRepository.getByIdOrThrow(id);

        return carMapper.toDTO(car);
    }

    @Override
    public PageableDTO getAllCars(int page, int size) {

        Sort sort = Sort.by(Sort.Direction.ASC, "id");

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Car> carPage = carRepository.findAll(pageable);

        List<Car> cars = carPage.getContent();

        return new PageableDTO(
                carPage.getSize(),
                carPage.getTotalElements(),
                carPage.getTotalPages(),
                carPage.hasNext(),
                carPage.hasPrevious(),
                carMapper.toDTO(cars)
        );

    }

    @Override
    public CarDTO updateCar(Long id, CarDTO carDTO) {

        return null;

    }

    @Override
    @Transactional
    public void deleteCar(Long id) {

        Car car = carRepository.getByIdOrThrow(id);

        carRepository.delete(car);
    }
}
