package com.test.task.repository;

import com.test.task.model.CriteriaModel;
import com.test.task.model.Operation;
import com.test.task.model.entity.Task;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class TaskSpecification implements Specification<Task> {

    private final CriteriaModel criteriaModel;

    @Override
    public Predicate toPredicate(Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        checkCriteria(criteriaModel);

        var operation = criteriaModel.getOperation();
        var expression = root.get(criteriaModel.getField());
        var value = criteriaModel.getValue();

        switch (operation) {
            case EQ -> {
                return criteriaBuilder.equal(expression, value);
            }
            case LIKE -> {
                String likeString = "%" + value + "%";
                return criteriaBuilder.like(expression.as(String.class), likeString);
            }
            case GT -> {
                return criteriaBuilder.greaterThan(expression.as(LocalDateTime.class), LocalDateTime.parse(value));
            }
            case LT -> {
                return criteriaBuilder.lessThan(expression.as(LocalDateTime.class), LocalDateTime.parse(value));
            }
        }
        return null;
    }

    private void checkCriteria(CriteriaModel criteriaModel) {
        if (criteriaModel.getField().isBlank()) {
            //todo: custom exception
        }
        var operation = criteriaModel.getOperation();
        if (operation == null) {
            //todo: custom exception
        }
        if ((operation == Operation.LT || operation == Operation.GT) && !criteriaModel.getField().equals("created")) {
            //todo: custom exception
        }
        if (criteriaModel.getValue() == null) {
            //todo: custom exception
        }
    }
}
