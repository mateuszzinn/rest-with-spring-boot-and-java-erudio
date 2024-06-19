package br.com.mateus.services;

import br.com.mateus.data.vo.v1.PersonVO;
import br.com.mateus.data.vo.v2.PersonVOV2;
import br.com.mateus.domain.Person;
import br.com.mateus.exception.ResourceNotFoundException;
import br.com.mateus.mapper.Mapper;
import br.com.mateus.mapper.custom.PersonMapper;
import br.com.mateus.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class PersonServices {

    private final Logger logger = Logger.getLogger(PersonServices.class.getName());

    private final PersonRepository repository;
    private final PersonMapper mapper;
    @Autowired
    public PersonServices(PersonRepository repository, PersonMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<PersonVO> findAll() {
        logger.info("Finding all people!");
        return Mapper.parseListObjects(repository.findAll(), PersonVO.class);
    }

    public PersonVO findById(Long id){
        logger.info("Findind one person!");
        var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found this ID!"));
        return Mapper.parseObject(entity, PersonVO.class);
    }

    public PersonVO create(PersonVO person){
        logger.info("Creating one person!");
        var entity = Mapper.parseObject(person, Person.class);
        return Mapper.parseObject(repository.save(entity), PersonVO.class);
    }

    public PersonVOV2 createV2(PersonVOV2 person){
        logger.info("Creating one person with V2!");
        var entity = mapper.convertVoToEntity(person);
        return mapper.convertEntityToVo(repository.save(entity));
    }

    public PersonVO update(PersonVO person){
        logger.info("Updating one person!");

        Person entity = repository.findById(person.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        return Mapper.parseObject(repository.save(entity), PersonVO.class);
    }

    public void delete(Long id){
        logger.info("Deleting one person!");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        repository.delete(entity);
    }
}
