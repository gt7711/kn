package com.example.city.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageDto {
    private int totalPages;
    private int totalElements;
    private int size;
}
