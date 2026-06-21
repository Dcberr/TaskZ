package dcberr.taskz.common.dto;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

public record PageResponse<T>(
        List<T> content,
        int page,
        int size,
        long totalElements,
        int totalPages,
        int numberOfElements,
        boolean first,
        boolean last,
        String sortBy,
        Sort.Direction sortDirection
) {

    public static <T> PageResponse<T> from(Page<T> page) {
        Sort.Order order = page.getSort().stream()
                .findFirst()
                .orElse(null);

        return new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.getNumberOfElements(),
                page.isFirst(),
                page.isLast(),
                order == null ? null : order.getProperty(),
                order == null ? null : order.getDirection()
        );
    }
}
