package com.project.hms.repository.addon;

import com.project.hms.model_rdb.AddOn;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AddOnCustomRepository {

    @Autowired
    AddOnRepository addOnRepository;

    @Autowired
    EntityManager entityManager;

    public void add(AddOn addOn){
        addOnRepository.save(addOn);
    }

    public List<AddOn> get(){
        List<AddOn> addOnList = new ArrayList<>();

        addOnRepository.findAll().forEach(addOnList::add);

        return addOnList;
    }

    public AddOn get(int id){
        return addOnRepository.findById(id).orElse(null);
    }

    @Transactional
    public void update(AddOn addOn){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaUpdate<AddOn> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(AddOn.class);

        Root<AddOn> addOnRoot = criteriaUpdate.from(AddOn.class);

        criteriaUpdate.set("addon_name", addOn.getAddon_name());
        criteriaUpdate.set("addon_price", addOn.getAddon_price());
        criteriaUpdate.set("addon_info", addOn.getAddon_info());

        Predicate predicateAddOnId = criteriaBuilder.equal(addOnRoot.get("addon_id"), addOn.getAddon_id());

        criteriaUpdate.where(predicateAddOnId);

        entityManager.createQuery(criteriaUpdate).executeUpdate();
    }
}
