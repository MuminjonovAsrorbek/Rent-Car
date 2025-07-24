package uz.dev.rentcar.service.template;

import org.springframework.web.multipart.MultipartFile;
import uz.dev.rentcar.entity.Attachment;
import uz.dev.rentcar.payload.AttachmentDTO;
import uz.dev.rentcar.payload.CarDTO;

import java.io.IOException;
import java.util.List;

/**
 * Created by: asrorbek
 * DateTime: 7/21/25 16:01
 **/

public interface AttachmentService {
    CarDTO uploadFiles(List<MultipartFile> images, Long carId) throws IOException;

    Attachment downloadFile(Long id);

    List<AttachmentDTO> createImages(List<MultipartFile> files) throws IOException;
}
