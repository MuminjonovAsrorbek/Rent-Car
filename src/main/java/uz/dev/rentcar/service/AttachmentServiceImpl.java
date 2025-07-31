package uz.dev.rentcar.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import uz.dev.rentcar.entity.Attachment;
import uz.dev.rentcar.entity.Car;
import uz.dev.rentcar.exceptions.EntityNotFoundException;
import uz.dev.rentcar.mapper.AttachmentMapper;
import uz.dev.rentcar.mapper.CarMapper;
import uz.dev.rentcar.payload.AttachmentDTO;
import uz.dev.rentcar.payload.CarDTO;
import uz.dev.rentcar.repository.AttachmentRepository;
import uz.dev.rentcar.repository.CarRepository;
import uz.dev.rentcar.service.template.AttachmentService;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Created by: asrorbek
 * DateTime: 7/21/25 16:01
 **/

@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {

    private final AttachmentRepository attachmentRepository;

    private final CarRepository carRepository;

    private final CarMapper carMapper;

    private final AttachmentMapper attachmentMapper;

    @Value("${myapp.upload-path}")
    private String UPLOAD_PATH;

    @Override
    @Transactional
    public CarDTO uploadFiles(List<MultipartFile> images, Long carId) throws IOException {

        Car car = carRepository.getByIdOrThrow(carId);

        boolean isFirst = true;

        if (images != null && !images.isEmpty()) {

            for (MultipartFile image : images) {

                String originalFilename = image.getOriginalFilename();

                long size = image.getSize();

                String contentType = image.getContentType();

                if (!Objects.requireNonNull(contentType).startsWith("image/")) {

                    throw new RuntimeException("Only image files are allowed");

                }

                assert originalFilename != null;

                String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

                String fileNewName = UUID.randomUUID() + extension;

                Path path = Path.of(UPLOAD_PATH, fileNewName);

                Files.copy(image.getInputStream(), path);

                Attachment attachment = new Attachment();

                attachment.setPath(path.toString());
                attachment.setOriginalName(originalFilename);
                attachment.setContentType(contentType);
                attachment.setSize(size);
                attachment.setCar(car);
                attachment.setPrimary(isFirst);

                Attachment saved = attachmentRepository.save(attachment);

                if (isFirst) {

                    car.setImageUrl(
                            "/api/attachments/download/" + saved.getId()
                    );

                }

                isFirst = false;

            }

            Car savedCar = carRepository.save(car);

            return carMapper.toDTO(savedCar);

        }

        throw new EntityNotFoundException("Images must not be empty", HttpStatus.NOT_FOUND);

    }

    @Override
    public Attachment downloadFile(Long id) {

        try {

            Attachment attachment = attachmentRepository.findByIdOrThrowException(id);

            Path path = Path.of(attachment.getPath());

            if (!Files.exists(path)) {
                throw new FileNotFoundException("File not found on server with ID: " + id);
            }

            return attachment;

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while accessing file", e);
        }

    }

    @Override
    @Transactional
    public List<AttachmentDTO> createImages(List<MultipartFile> files) throws IOException {

        List<Attachment> attachments = new ArrayList<>();

        for (MultipartFile file : files) {

            String originalFilename = file.getOriginalFilename();
            long size = file.getSize();
            String contentType = file.getContentType();

            if (!Objects.requireNonNull(contentType).startsWith("image/")) {
                throw new RuntimeException("Only image files are allowed");
            }

            assert originalFilename != null;

            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String fileNewName = UUID.randomUUID() + extension;

            Path path = Path.of(UPLOAD_PATH, fileNewName);

            Files.copy(file.getInputStream(), path);

            Attachment attachment = new Attachment();
            attachment.setPath(path.toString());
            attachment.setOriginalName(originalFilename);
            attachment.setContentType(contentType);
            attachment.setSize(size);

            attachments.add(attachmentRepository.save(attachment));
        }

        return attachmentMapper.toDTO(attachments);

    }
}