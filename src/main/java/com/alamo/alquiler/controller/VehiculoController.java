package com.alamo.alquiler.controller;

import com.alamo.alquiler.model.Notificacion;
import com.alamo.alquiler.model.Vehiculo;
import com.alamo.alquiler.repository.NotificacionRepository;
import com.alamo.alquiler.repository.VehiculoRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoController extends AbstractCrudController<Vehiculo, Integer> {

    private final VehiculoRepository repo;
    private final NotificacionRepository notificacionRepo;

    public VehiculoController(VehiculoRepository repo, NotificacionRepository notificacionRepo) {
        this.repo = repo;
        this.notificacionRepo = notificacionRepo;
    }

    @Override
    protected JpaRepository<Vehiculo, Integer> repo() {
        return repo;
    }

    @Override
    public Vehiculo crear(Vehiculo entidad) {
        Vehiculo guardado = super.crear(entidad);
        try {
            Notificacion notif = Notificacion.builder()
                    .titulo("Vehículo Registrado")
                    .mensaje(guardado.getMarca() + " " + guardado.getModelo() + " [" + guardado.getPlaca() + "] añadido a la flota.")
                    .tipo("VEHICULO")
                    .build();
            notificacionRepo.save(notif);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return guardado;
    }

    @Override
    public Vehiculo actualizar(Integer id, Vehiculo entidad) {
        Vehiculo guardado = super.actualizar(id, entidad);
        try {
            Notificacion notif = Notificacion.builder()
                    .titulo("Vehículo Modificado")
                    .mensaje("Vehículo " + guardado.getMarca() + " [" + guardado.getPlaca() + "] actualizado.")
                    .tipo("VEHICULO")
                    .build();
            notificacionRepo.save(notif);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return guardado;
    }

    @Override
    public void eliminar(Integer id) {
        repo.findById(id).ifPresent(v -> {
            try {
                Notificacion notif = Notificacion.builder()
                        .titulo("Vehículo Eliminado")
                        .mensaje("Vehículo " + v.getMarca() + " [" + v.getPlaca() + "] removido de la flota.")
                        .tipo("VEHICULO")
                        .build();
                notificacionRepo.save(notif);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        super.eliminar(id);
    }

    @org.springframework.web.bind.annotation.GetMapping("/disponibles")
    public java.util.List<Vehiculo> listarDisponibles(
            @org.springframework.web.bind.annotation.RequestParam("fechaInicio") String fechaInicio,
            @org.springframework.web.bind.annotation.RequestParam("fechaFin") String fechaFin
    ) {
        java.time.LocalDate inicio = java.time.LocalDate.parse(fechaInicio);
        java.time.LocalDate fin = java.time.LocalDate.parse(fechaFin);
        return repo.findVehiculosDisponibles(inicio, fin);
    }
}
