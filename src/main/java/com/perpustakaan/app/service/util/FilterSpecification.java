package com.perpustakaan.app.service.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.perpustakaan.app.model.Filter;

public final class FilterSpecification {
    
    public final static <T> Specification<T> where(List<Filter> filters) {
        if (filters.size() <= 0) {
            return null;
        }
        Specification<T> specs = createSpecification(filters.remove(0));
        for (Filter fill : filters) {
            specs = specs.and(createSpecification(fill));
        }
        return specs;
    }

    public final static <T> Specification<T> query(List<Specification<T>> list) {
        Specification<T> specs = list.remove(0);
        for (Specification<T> spec : list) {
            specs = specs.and(spec);
        }
        return Specification.where(specs);
    }

    private final static <T> Specification<T> createSpecification(Filter filter) {
        switch (filter.getOperator()) {
        case EQUALS_IGNORE_CASE:
            return (root, query, criteriaBuilder) -> criteriaBuilder.equal(
                    criteriaBuilder.lower(root.get(filter.getField())),
                        String.valueOf(filter.getValue()).toLowerCase());

        case EQUALS:
            return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(filter.getField()),
                    castToRequiredType(root.get(filter.getField()), filter.getValue()));

        case NOT_EQUALS:
            return (root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get(filter.getField()),
                    castToRequiredType(root.get(filter.getField()), filter.getValue()));

        case GREATER_THAN:
            return (root, query, criteriaBuilder) -> criteriaBuilder.gt(root.get(filter.getField()),
                    (Number) castToRequiredType(root.get(filter.getField()), filter.getValue()));

        case LESS_THAN:
            return (root, query, criteriaBuilder) -> criteriaBuilder.lt(root.get(filter.getField()),
                    (Number) castToRequiredType(root.get(filter.getField()), filter.getValue()));

        case LIKE:
            return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(filter.getField()),
                    "%" + filter.getValue() + "%");

        case LIKE_IGNORE_CASE:
            return (root, query, criteriaBuilder) -> criteriaBuilder.like(
                    criteriaBuilder.lower(root.get(filter.getField())), "%" + filter.getValue().toLowerCase() + "%");

        case IN:
            return (root, query, criteriaBuilder) -> criteriaBuilder.in(root.get(filter.getField()))
                    .value(castToRequiredType(root.get(filter.getField()).getJavaType(), filter.getValues()));

        default:
            throw new RuntimeException("Operation not supported yet");
        }
    }

    private final static <T> Object castToRequiredType(T fieldType, String value) {
        if (fieldType instanceof Double field) {
            return field;
        } else if (fieldType instanceof Integer field) {
            return field;
        } else if (fieldType instanceof Boolean field) {
            return field;
        } else if (fieldType instanceof Enum field) {
            return field;
        } else if (fieldType instanceof String field) {
            return field;
        }
        return null;
    }

    private final static <T> Object castToRequiredType(T fieldType, List<String> values) {
        List<Object> lists = new ArrayList<>();
        for (String s : values) {
            lists.add(castToRequiredType(fieldType, s));
        }
        return lists;
    }
    
}
