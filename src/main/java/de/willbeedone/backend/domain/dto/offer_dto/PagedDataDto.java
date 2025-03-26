package de.willbeedone.backend.domain.dto.offer_dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PagedDataDto<T> {
    private List<T> data;
    private long total;
}
