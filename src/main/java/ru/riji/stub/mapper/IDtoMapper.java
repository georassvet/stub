package ru.riji.stub.mapper;

public interface IDtoMapper<E,D> {
    D mapEntityToDto(E entity);
}
