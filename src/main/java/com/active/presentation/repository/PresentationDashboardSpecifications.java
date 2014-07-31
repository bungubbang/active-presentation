package com.active.presentation.repository;

import com.active.presentation.domain.PresentationDashboard;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 6/13/14
 */
public class PresentationDashboardSpecifications {

    public static Specification<PresentationDashboard> findFetchQuestion(final Long id) {
        return new Specification<PresentationDashboard>() {
            @Override
            public Predicate toPredicate(Root<PresentationDashboard> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                root.fetch("questions", JoinType.INNER);
                return cb.equal(root.get("id"), id);
            }
        };
    }

    public static Specification<PresentationDashboard> findFetchTags(final Long id) {
        return new Specification<PresentationDashboard>() {
            @Override
            public Predicate toPredicate(Root<PresentationDashboard> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                root.fetch("tags", JoinType.INNER);
                return cb.equal(root.get("id"), id);
            }
        };
    }
}
