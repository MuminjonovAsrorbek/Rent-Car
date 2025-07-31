package uz.dev.rentcar.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.dev.rentcar.config.CaffeineCacheConfig;
import uz.dev.rentcar.entity.Office;
import uz.dev.rentcar.entity.template.AbsLongEntity;
import uz.dev.rentcar.mapper.OfficeMapper;
import uz.dev.rentcar.payload.OfficeDTO;
import uz.dev.rentcar.payload.response.PageableDTO;
import uz.dev.rentcar.repository.OfficeRepository;
import uz.dev.rentcar.service.template.OfficeService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OfficeServiceImpl implements OfficeService {

    private final OfficeMapper officeMapper;
    private final OfficeRepository officeRepository;

    @Override
    @Cacheable(value = CaffeineCacheConfig.OFFICES, key = "#id")
    public OfficeDTO read(Long id) {

        Office office = officeRepository.getByIdOrThrow(id);

        return officeMapper.toDTO(office);
    }

    @Override
    @Cacheable(value = CaffeineCacheConfig.OFFICES, key = "#page + '-' + #size")
    public PageableDTO readAll(int page, int size) {

        Sort sort = Sort.by(AbsLongEntity.Fields.id).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Office> officePage = officeRepository.findAll(pageable);

        List<Office> offices = officePage.getContent();

        return new PageableDTO(
                officePage.getSize(),
                officePage.getTotalElements(),
                officePage.getTotalPages(),
                officePage.hasNext(),
                officePage.hasPrevious(),
                officeMapper.toDTO(offices)
        );
    }

    @Override
    @Transactional
    @Caching(
            put = {@CachePut(value = CaffeineCacheConfig.OFFICES, key = "#result.id")},
            evict = {
                    @CacheEvict(value = CaffeineCacheConfig.OFFICES, allEntries = true)
            }
    )
    public OfficeDTO create(OfficeDTO officeDTO) {

        Office office = officeMapper.toEntity(officeDTO);

        officeRepository.save(office);

        return officeMapper.toDTO(office);
    }

    @Override
    @Transactional
    @Caching(
            put = {@CachePut(value = CaffeineCacheConfig.OFFICES, key = "#id")},
            evict = {
                    @CacheEvict(value = CaffeineCacheConfig.OFFICES, allEntries = true)
            }
    )
    public OfficeDTO update(Long id, OfficeDTO officeDTO) {

        Office office = officeRepository.getByIdOrThrow(id);

        office.setName(officeDTO.getName());
        office.setAddress(officeDTO.getAddress());
        office.setLatitude(officeDTO.getLatitude());
        office.setLongitude(officeDTO.getLongitude());

        officeRepository.save(office);

        return officeMapper.toDTO(office);
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = CaffeineCacheConfig.OFFICES, key = "#id"),
            @CacheEvict(value = CaffeineCacheConfig.OFFICES, allEntries = true)
    })
    public void delete(Long id) {

        Office office = officeRepository.getByIdOrThrow(id);

        officeRepository.delete(office);
    }
}
