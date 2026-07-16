package com.alamo.alquiler.controller;

import com.alamo.alquiler.model.SoporteTicket;
import com.alamo.alquiler.model.Notificacion;
import com.alamo.alquiler.repository.SoporteTicketRepository;
import com.alamo.alquiler.repository.NotificacionRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/soporte")
public class SoporteTicketController extends AbstractCrudController<SoporteTicket, Integer> {

    private final SoporteTicketRepository repo;
    private final NotificacionRepository notificacionRepo;

    public SoporteTicketController(SoporteTicketRepository repo, NotificacionRepository notificacionRepo) {
        this.repo = repo;
        this.notificacionRepo = notificacionRepo;
    }

    @Override
    protected JpaRepository<SoporteTicket, Integer> repo() {
        return repo;
    }

    @Override
    public SoporteTicket crear(SoporteTicket entidad) {
        SoporteTicket guardado = super.crear(entidad);
        try {
            Notificacion notif = Notificacion.builder()
                    .titulo("Nuevo Ticket")
                    .mensaje(guardado.getCliente() + " reportó: " + guardado.getAsunto())
                    .tipo("SOPORTE")
                    .build();
            notificacionRepo.save(notif);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return guardado;
    }

    @Override
    public SoporteTicket actualizar(Integer id, SoporteTicket entidad) {
        SoporteTicket guardado = super.actualizar(id, entidad);
        try {
            Notificacion notif = Notificacion.builder()
                    .titulo(guardado.getEstado().equals("RESUELTO") ? "Ticket Resuelto" : "Ticket Modificado")
                    .mensaje("El ticket de " + guardado.getCliente() + " fue marcado como " + guardado.getEstado() + ".")
                    .tipo("SOPORTE")
                    .build();
            notificacionRepo.save(notif);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return guardado;
    }

    @Override
    public void eliminar(Integer id) {
        repo.findById(id).ifPresent(t -> {
            try {
                Notificacion notif = Notificacion.builder()
                        .titulo("Ticket Eliminado")
                        .mensaje("Ticket #" + t.getIdTicket() + " (" + t.getAsunto() + ") ha sido removido.")
                        .tipo("SOPORTE")
                        .build();
                notificacionRepo.save(notif);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        super.eliminar(id);
    }
}
