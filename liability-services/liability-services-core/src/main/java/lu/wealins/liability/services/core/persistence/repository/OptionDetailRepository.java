package lu.wealins.liability.services.core.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import lu.wealins.liability.services.core.persistence.entity.OptionDetailEntity;

public abstract interface OptionDetailRepository extends JpaRepository<OptionDetailEntity, Long> {
	
	@Query("select o from OptionDetailEntity o where trim(o.option.optId) = :option order by o.description")
	  public List<OptionDetailEntity> findByOption(@Param("option") String option);
	
	@Query("select o from OptionDetailEntity o where o.option.optId = :option and o.number = :number")
	  public OptionDetailEntity findByOptionAndByNumber(@Param("option") String option, @Param("number") int number);
	
	@Query("select o from OptionDetailEntity o where o.option.optId = :option and o.number IN :numbers")
	  public List<OptionDetailEntity> findByOptionAndByNumbers(@Param("option") String option, @Param("numbers") List<Integer> numbers);
	
}