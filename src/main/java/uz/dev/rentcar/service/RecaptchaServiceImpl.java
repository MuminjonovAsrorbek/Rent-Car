package uz.dev.rentcar.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uz.dev.rentcar.payload.response.RecaptchaRespDTO;
import uz.dev.rentcar.service.template.RecaptchaService;

@Service
@RequiredArgsConstructor
public class RecaptchaServiceImpl implements RecaptchaService {

    @Value("${google.recaptcha.secret}")
    private String recaptchaSecret;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public boolean verify(String token) {

        String url = "https://www.google.com/recaptcha/api/siteverify?secret=" + recaptchaSecret + "&response=" + token;

        try {
            ResponseEntity<RecaptchaRespDTO> response =
                    restTemplate.postForEntity(url, null, RecaptchaRespDTO.class);

            return response.getBody() != null && response.getBody().isSuccess();

        } catch (Exception e) {
            return false;
        }
    }
}
