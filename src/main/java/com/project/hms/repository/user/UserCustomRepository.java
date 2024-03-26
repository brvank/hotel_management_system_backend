package com.project.hms.repository.user;

import com.project.hms.model_rdb.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserCustomRepository {

    @Autowired
    EntityManager entityManager;

    @Autowired
    UserRepository userRepository;

    //create operations
    public void addUser(User user){
        userRepository.save(user);
    }

    public void addMultipleUsers(ArrayList<User> userArrayList){
        userRepository.saveAll(userArrayList);
    }

    //read operations
    public List<User> getUsers(int start, int size, int userLevel, String fullName, String userName, Integer queryUserLevel){
        if(start < 0){
            start = 0;
        }
        if(size < 0){
            size = 0;
        }
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<User> userCriteriaQuery = criteriaBuilder.createQuery(User.class);

        Root<User> userRoot = userCriteriaQuery.from(User.class);

        Predicate predicate = criteriaBuilder.conjunction();

        predicate = criteriaBuilder.ge(userRoot.get("user_user_level"), userLevel);

        if(fullName != null){
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(userRoot.get("user_full_name"), "%" + fullName + "%"));
        }

        if(userName != null){
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(userRoot.get("user_user_name"), "%" + userName + "%"));
        }

        if(queryUserLevel != null){
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(userRoot.get("user_user_level"), queryUserLevel));
        }

        userCriteriaQuery.select(userRoot);

        userCriteriaQuery.where(predicate);

        return entityManager.createQuery(userCriteriaQuery).setFirstResult(start).setMaxResults(size).getResultList();
    }

    public List<User> getUserByUserId(int userId){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<User> userCriteriaQuery = criteriaBuilder.createQuery(User.class);

        Root<User> userRoot = userCriteriaQuery.from(User.class);

        Predicate predicateUserId = criteriaBuilder.equal(userRoot.get("user_id"), userId);

        userCriteriaQuery.select(userRoot);

        userCriteriaQuery.where(predicateUserId);

        return entityManager.createQuery(userCriteriaQuery).getResultList();
    }

    public List<User> getUserByNameAndPassword(String username, String password){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<User> userCriteriaQuery = criteriaBuilder.createQuery(User.class);

        Root<User> userRoot = userCriteriaQuery.from(User.class);
        Predicate predicateUserNameFilter = criteriaBuilder.like(userRoot.get("user_name"), username);
        Predicate predicatePasswordFilter = criteriaBuilder.like(userRoot.get("user_password"), password);

        userCriteriaQuery.where(predicateUserNameFilter, predicatePasswordFilter);

        return entityManager.createQuery(userCriteriaQuery).getResultList();
    }
}
