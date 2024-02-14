package ru.riji.stub.mapper;

import org.springframework.stereotype.Component;
import ru.riji.stub.dto.ServiceDto;
import ru.riji.stub.entities.ServiceEntity;

@Component
public class ServiceMapper implements IDtoMapper<ServiceEntity, ServiceDto> {
    @Override
    public ServiceDto mapEntityToDto(ServiceEntity entity) {
        return new ServiceDto();
    }
}
