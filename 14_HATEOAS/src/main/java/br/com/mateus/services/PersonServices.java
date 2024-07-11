package br.com.mateus.services;

import br.com.mateus.data.vo.v1.PersonVO;
import br.com.mateus.domain.Person;
import br.com.mateus.exception.ResourceNotFoundException;
import br.com.mateus.mapper.Mapper;
import br.com.mateus.repositories.PersonRepository;
import br.com.mateus.resources.PersonController;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class PersonServices {

    private Logger logger = Logger.getLogger(PersonServices.class.getName());

    private final PersonRepository repository;
    @Autowired
    public PersonServices(PersonRepository repository) {
        this.repository = repository;
    }

    public List<PersonVO> findAll() {
        logger.info("Finding all people!");
        var persons = Mapper.parseListObjects(repository.findAll(), PersonVO.class);
        for (PersonVO p : persons) {
            p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel());
        }
        return persons;
    }

    public PersonVO findById(Long id){
        logger.info("Findind one person!");
        var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found this ID!"));
        var vo = Mapper.parseObject(entity, PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
        return vo;
    }

    public PersonVO create(PersonVO person){
        logger.info("Creating one person!");
        var entity = Mapper.parseObject(person, Person.class);
        var vo = Mapper.parseObject(repository.save(entity), PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public PersonVO update(PersonVO person){
        logger.info("Updating one person!");

        var entity = repository.findById(person.getKey())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        var vo = Mapper.parseObject(repository.save(entity), PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public void delete(Long id) {
        logger.info("Deleting one person!");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        repository.delete(entity);
    }
}
