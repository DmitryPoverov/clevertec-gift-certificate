package ru.clevertec.ecl.mapper;

import org.mapstruct.*;
import ru.clevertec.ecl.dto.GiftCertificateDto;
import ru.clevertec.ecl.entities.GiftCertificate;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring",
//        uses = TagMapper.class,
        imports = LocalDateTime.class)
public interface GiftCertificateMapper {

    GiftCertificateDto giftCertificateToDto(GiftCertificate certificate);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createDate", expression = "java(LocalDateTime.now())")
    @Mapping(target = "lastUpdateDate", expression = "java(LocalDateTime.now())")
    GiftCertificate dtoToGiftCertificate(GiftCertificateDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "lastUpdateDate", expression = "java(LocalDateTime.now())")
    void updateFromDto(@MappingTarget GiftCertificate certificate, GiftCertificateDto dto);
}
