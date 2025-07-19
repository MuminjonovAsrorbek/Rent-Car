package uz.dev.rentcar.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RecaptchaRespDTO {

    private boolean success;

    @JsonProperty("error-codes")
    private List<String> errorCodes;
}
