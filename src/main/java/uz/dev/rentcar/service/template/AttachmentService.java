package uz.dev.rentcar.service.template;

import org.springframework.web.multipart.MultipartFile;
import uz.dev.rentcar.entity.Attachment;
import uz.dev.rentcar.entity.Car;
import uz.dev.rentcar.payload.AttachmentDTO;

import java.io.IOException;
import java.util.List;

/**
 * Created by: asrorbek
 * DateTime: 7/21/25 16:01
 **/

public interface AttachmentService {
    List<AttachmentDTO> uploadFiles(MultipartFile[] images, Car savedCar) throws IOException;

    Attachment downloadFile(Long id);
}
