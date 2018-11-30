/**
 * 
 */
package lu.wealins.liability.services.core.persistence.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;

import lu.wealins.liability.services.core.persistence.entity.HolidayEntity;


public interface HolidayRepository extends JpaRepository<HolidayEntity, Date> {

}
