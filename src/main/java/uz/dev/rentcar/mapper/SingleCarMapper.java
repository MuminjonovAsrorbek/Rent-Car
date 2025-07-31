package uz.dev.rentcar.mapper;

import org.springframework.stereotype.Component;
import uz.dev.rentcar.entity.Car;
import uz.dev.rentcar.payload.request.UpdateCarDTO;
import uz.dev.rentcar.utils.CommonUtils;

/**
 * Created by: asrorbek
 * DateTime: 7/25/25 17:55
 **/

@Component
public class SingleCarMapper {

    public Car updateCar(UpdateCarDTO carDTO, Car car) {

        if (carDTO == null) {
            return car;
        }

        car.setBrand(CommonUtils.getOrDef(carDTO.getBrand(), car.getBrand()));
        car.setModel(CommonUtils.getOrDef(carDTO.getModel(), car.getModel()));
        car.setYear(CommonUtils.getOrDef(carDTO.getYear(), car.getYear()));
        car.setPricePerDay(CommonUtils.getOrDef(carDTO.getPricePerDay(), car.getPricePerDay()));
        car.setSeats(CommonUtils.getOrDef(carDTO.getSeats(), car.getSeats()));
        car.setFuelType(CommonUtils.getOrDef(carDTO.getFuelType(), car.getFuelType()));
        car.setTransmission(CommonUtils.getOrDef(carDTO.getTransmission(), car.getTransmission()));
        car.setFuelConsumption(CommonUtils.getOrDef(carDTO.getFuelConsumption(), car.getFuelConsumption()));

        return car;

    }

}
