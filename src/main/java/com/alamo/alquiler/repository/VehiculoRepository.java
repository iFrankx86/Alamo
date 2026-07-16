package com.alamo.alquiler.repository;

import com.alamo.alquiler.model.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, Integer> {

    @Query("SELECT v FROM Vehiculo v WHERE v.idVehiculo NOT IN (" +
           "  SELECT c.vehiculo.idVehiculo FROM ContratoAlquiler c " +
           "  WHERE c.fechaInicio <= :fechaFin AND c.fechaFin >= :fechaInicio" +
           ")")
    List<Vehiculo> findVehiculosDisponibles(
        @Param("fechaInicio") LocalDate fechaInicio, 
        @Param("fechaFin") LocalDate fechaFin
    );
}
