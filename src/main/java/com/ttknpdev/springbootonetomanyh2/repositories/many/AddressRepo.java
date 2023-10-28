package com.ttknpdev.springbootonetomanyh2.repositories.many;

import com.ttknpdev.springbootonetomanyh2.entities.many.Address;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AddressRepo extends JpaRepository<Address,String> {
    @Modifying
    @Transactional
    @Query(value = "insert into addresses(aid,eid,city,country,details) values (:aid , :eid , :city, :country,:details) ",nativeQuery = true)
    void createToForeignKey(@Param("aid") String aid ,
                            @Param("eid") String eid ,
                            @Param("city") String city ,
                            @Param("country") String country ,
                            @Param("details") String details);

    @Query(value = "select * from addresses where eid = :eid" , nativeQuery = true)
    List<Address> readsByEmployeeId(@Param("eid") String eid);
    @Query(value = "delete from addresses where eid = :eid" , nativeQuery = true)
    @Modifying
    @Transactional
    void deleteAllByEmployeeId(@Param("eid") String eid);
}
