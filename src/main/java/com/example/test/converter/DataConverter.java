package com.example.test.converter;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public interface DataConverter<E, D> {

    @NonNull
    default E toEntity(@NonNull D dto) {
        throw new UnsupportedOperationException("Not Implemented!");
    }

    @NonNull
    default D toDto(@NonNull E entity) {
        throw new UnsupportedOperationException("Not Implemented!");
    }

    @NonNull
    default List<E> toEntityList(@NonNull List<D> dtos) {
        return dtos.stream()
                .map(DataConverter.this::toEntity)
                .collect(Collectors.toList());
    }

    @NonNull
    default List<D> toDtoList(@NonNull List<E> entities) {
        return entities.stream()
                .map(DataConverter.this::toDto)
                .collect(Collectors.toList());
    }

    @NonNull
    default Page<D> toDtoPage(@NonNull Page<E> page) {
        final List<D> dtos = page
                .getContent()
                .stream()
                .map(DataConverter.this::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(dtos, page.getPageable(), page.getTotalElements());
    }

    @NonNull
    default Set<E> toEntitySet(@NonNull Set<D> dtos) {
        return dtos.stream()
                .map(DataConverter.this::toEntity)
                .collect(Collectors.toSet());
    }

    @NonNull
    default Set<D> toDtoSet(@NonNull Set<E> entities) {
        return entities.stream()
                .map(DataConverter.this::toDto)
                .collect(Collectors.toSet());
    }
}
