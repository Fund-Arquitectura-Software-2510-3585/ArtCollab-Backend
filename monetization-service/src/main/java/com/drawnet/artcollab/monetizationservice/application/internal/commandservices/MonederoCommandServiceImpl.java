package com.drawnet.artcollab.monetizationservice.application.internal.commandservices;

import com.drawnet.artcollab.monetizationservice.domain.model.aggregates.Donacion;
import com.drawnet.artcollab.monetizationservice.domain.model.aggregates.Monedero;
import com.drawnet.artcollab.monetizationservice.domain.model.commands.CrearDonacionCommand;
import com.drawnet.artcollab.monetizationservice.infrastructure.persistence.jpa.repositories.DonacionRepository;
import com.drawnet.artcollab.monetizationservice.infrastructure.persistence.jpa.repositories.MonederoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MonederoCommandServiceImpl {

    public final MonederoRepository monederoRepository;
    public final DonacionRepository donacionRepository;

    public MonederoCommandServiceImpl(MonederoRepository monederoRepository, DonacionRepository donacionRepository) {
        this.monederoRepository = monederoRepository;
        this.donacionRepository = donacionRepository;
    }

    @Transactional
    public Donacion donar(Monedero donante, Monedero receptor, CrearDonacionCommand command) {
        donante.restarSaldo(command.monto());
        receptor.sumarSaldo(command.monto());
        monederoRepository.save(donante);
        monederoRepository.save(receptor);
        Donacion donacion = new Donacion(command);
        donacionRepository.save(donacion);
        return donacion;
    }
}
