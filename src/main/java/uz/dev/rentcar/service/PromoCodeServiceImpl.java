package uz.dev.rentcar.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.dev.rentcar.entity.PromoCode;
import uz.dev.rentcar.entity.template.AbsLongEntity;
import uz.dev.rentcar.exceptions.EntityAlreadyExistException;
import uz.dev.rentcar.exceptions.EntityNotFoundException;
import uz.dev.rentcar.mapper.PromoCodeMapper;
import uz.dev.rentcar.payload.PromoCodeDTO;
import uz.dev.rentcar.payload.response.PageableDTO;
import uz.dev.rentcar.repository.PromoCodeRepository;
import uz.dev.rentcar.service.template.PromoCodeService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PromoCodeServiceImpl implements PromoCodeService {

    private final PromoCodeRepository promoCodeRepository;
    private final PromoCodeMapper promoCodeMapper;

    @Override
    public PromoCodeDTO read(Long id) {

        PromoCode promoCode = promoCodeRepository.getByIdOrThrow(id);

        return promoCodeMapper.toDTO(promoCode);

    }

    @Override
    public PageableDTO readAll(int page, int size) {

        Sort sort = Sort.by(AbsLongEntity.Fields.id).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<PromoCode> promoCodePage = promoCodeRepository.findAll(pageable);

        List<PromoCode> promoCodes = promoCodePage.getContent();

        return new PageableDTO(
                promoCodePage.getSize(),
                promoCodePage.getTotalElements(),
                promoCodePage.getTotalPages(),
                promoCodePage.hasNext(),
                promoCodePage.hasPrevious(),
                promoCodeMapper.toDTO(promoCodes)
        );
    }

    @Override
    public PromoCodeDTO codeValidate(String code) {

        LocalDateTime now = LocalDateTime.now();

        PromoCode promoCode = promoCodeRepository.findByCode(code)
                .orElseThrow(() -> new EntityNotFoundException("Code not found :" + code, HttpStatus.NOT_FOUND));

        if (now.isBefore(promoCode.getValidFrom()))
            throw new RuntimeException("Promo code is not active yet");

        if (now.isAfter(promoCode.getValidTo()))
            throw new RuntimeException("Promo code has expired");

        return promoCodeMapper.toDTO(promoCode);
    }

    @Override
    @Transactional
    public PromoCodeDTO create(PromoCodeDTO promoCodeDTO) {

        PromoCode promoCode = promoCodeMapper.toEntity(promoCodeDTO);

        if (promoCodeRepository.existsByCode(promoCodeDTO.getCode()) && !promoCode.getCode().equals(promoCodeDTO.getCode()))
            throw new EntityAlreadyExistException(promoCodeDTO.getCode(), HttpStatus.CONFLICT);

        promoCode.setCode(promoCodeDTO.getCode());

        promoCodeRepository.save(promoCode);

        return promoCodeMapper.toDTO(promoCode);
    }

    @Override
    @Transactional
    public PromoCodeDTO update(Long id, PromoCodeDTO promoCodeDTO) {

        PromoCode promoCode = promoCodeRepository.getByIdOrThrow(id);

        if (promoCodeRepository.existsByCodeAndIdNot(promoCodeDTO.getCode(), id))
            throw new EntityAlreadyExistException(promoCodeDTO.getCode(), HttpStatus.CONFLICT);

        promoCode.setCode(promoCodeDTO.getCode());
        promoCode.setDiscount(promoCodeDTO.getDiscount());
        promoCode.setValidFrom(promoCodeDTO.getValidFrom());
        promoCode.setValidTo(promoCodeDTO.getValidTo());

        promoCodeRepository.save(promoCode);

        return promoCodeMapper.toDTO(promoCode);
    }

    @Override
    @Transactional
    public void delete(Long id) {

        PromoCode promoCode = promoCodeRepository.getByIdOrThrow(id);

        promoCodeRepository.delete(promoCode);
    }

}
