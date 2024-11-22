package com.test.task.repository;

import com.test.task.exception.RequestException;
import com.test.task.model.CriteriaModel;
import com.test.task.model.entity.Operation;
import com.test.task.model.entity.Task;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
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
            throw new RequestException("The field in the filter must not be empty");
        }
        var operation = criteriaModel.getOperation();
        if (operation == null) {
            throw new RequestException("The operation in the filter must not be empty");
        }
        if ((operation == Operation.LT || operation == Operation.GT) && !criteriaModel.getField().equals("created")) {
            throw new RequestException("The greatThan and lessThan operations can only be applied to the created field");
        }
        if (criteriaModel.getValue() == null) {
            throw new RequestException("The value in the filter must not be empty");
        }
    }
}
