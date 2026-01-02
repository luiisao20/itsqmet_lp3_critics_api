package com.itsqmet.service;

import com.itsqmet.entity.Actor;
import com.itsqmet.repository.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActorService {
    @Autowired
    private ActorRepository actorRepository;

    public List<Actor> findAll() {
        return actorRepository.findAll();
    }

    public Optional<Actor> findById(Long id) {
        return actorRepository.findById(id);
    }

    public Actor saveActor(Actor actor) {
        return actorRepository.save(actor);
    }

    public Actor updateActor(Long id, Actor actor) {
        Actor oldActor = findById(id).orElseThrow(() -> new RuntimeException("Actor not found"));
        oldActor.setName(actor.getName());
        return actorRepository.save(oldActor);
    }

    public void deleteById(Long id) {
        actorRepository.deleteById(id);
    }
}
