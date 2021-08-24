package com.example.SpringBootApp1.repository;

import com.example.SpringBootApp1.model.Emp;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/*
    Spring Data Repository
    Spring Data JPA provides three repositories are as follows:

    CrudRepository: It offers standard create, read, update, and delete It contains method like findOne(), findAll(), save(), delete(), etc.
    PagingAndSortingRepository: It extends the CrudRepository and adds the findAll methods. It allows us to sort and retrieve the data in a paginated way.
    JpaRepository: It is a JPA specific repository It is defined in Spring Data Jpa. It extends the both repository CrudRepository and PagingAndSortingRepository. It adds the JPA-specific methods, like flush() to trigger a flush on the persistence context.

    https://www.baeldung.com/spring-data-jpa-query
 */

public interface IEmpRepository extends CrudRepository<Emp, Integer>
{
    public Emp findByFname(String name);

    @Query(value = "select * from EMPLOYEES s where s.first_name = ?1", nativeQuery = true)
    public List<Emp> getByFname(String name);

    @Query(value = "select * from EMPLOYEES s where s.last_name = :lname", nativeQuery = true)
    public List<Emp> getByLname(@Param("lname") String name);

    @Query(value = "SELECT count(*) FROM EMPLOYEES", nativeQuery = true)
    public Long getEmpCount();

    /* This will be executed from named query {Emp.fetchByName} defined in Emp class.
        The name is formatted as ClassName.QueryName
    **/
    List<Emp> fetchByName(@Param("fname") String fname, @Param("lname") String lname);

    /* The below query is native SQL query. It is JPQL. Hence, we have used the Class name and field name.
       It will allow to update the first name.
    */

    @Transactional  /* it is required, else give compilation error */
    @Modifying
    @Query("update Emp u set u.fname = :fname where u.empId = :empId")
    /* @Query(value = "update Users u set u.status = ? where u.name = ?", nativeQuery = true) */
    int updateEmpFirstName(@Param("empId") int empId,
                           @Param("fname") String name);

    @Transactional  /* It is required, else gives compilation error */
    @Modifying
    @Query(value ="insert into EMPLOYEES (first_name, last_name, salary, email) values (:fname, :lname, :salary, :email)",nativeQuery = true)
    void insertEmp(@Param("fname") String fname, @Param("lname") String lname,   @Param("salary") double salary, @Param("email") String email);

}
