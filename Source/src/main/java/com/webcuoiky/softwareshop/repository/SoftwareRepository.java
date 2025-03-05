package com.webcuoiky.softwareshop.repository;

import com.webcuoiky.softwareshop.model.Software;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SoftwareRepository extends JpaRepository<Software, Integer> {
    List<Software> findByCategory(String category);
    List<Software> findByPrice(Double  price);      //YES
    List<Software> findByPriceNot(Double  price); //NOT
    List<Software> findByName(String name);

    //chuyển toàn bộ thành chữ thường -> tìm cái like CONCAT('%', :name, '%') với '%' là kí tự đại diện
    @Query("SELECT s FROM Software s WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Software> findByNameContaining(@Param("name") String name);

    List<Software> findByNameContainingAndCategory(String name, String category);
}
