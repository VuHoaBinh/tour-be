package sf.travel.helper;

import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public abstract class Specifications<T> {
    public Specification<T> createSpecification(String field, Object value) {
        return (root, query, builder) -> builder.equal(root.get(field), value);
    }

    public Specification<T> createLikeSpecification(String field, String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get(field)), '%' + value + '%');
    }

    public Specification<T> findByTimeInterval(String field, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return (root, query, builder) -> {
            if (startDateTime != null && endDateTime != null) {
                return builder.between(root.get(field), startDateTime, endDateTime);
            } else if (startDateTime != null) {
                return builder.greaterThanOrEqualTo(root.get(field), startDateTime);
            } else if (endDateTime != null) {
                return builder.lessThanOrEqualTo(root.get(field), endDateTime);
            } else {
                return null;
            }
        };
    }

    public Specification<T> findByTimePeriod(String fieldStartDate, String fieldEndDate, LocalDateTime searchDateTime) {
        return (root, query, builder) -> {
            if (searchDateTime != null) {
                return builder.and(
                        builder.lessThanOrEqualTo(root.get(fieldStartDate), searchDateTime),
                        builder.greaterThanOrEqualTo(root.get(fieldEndDate), searchDateTime)
                );
            } else {
                return null;
            }
        };
    }
}
