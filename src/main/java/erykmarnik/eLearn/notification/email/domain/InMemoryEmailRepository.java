package erykmarnik.eLearn.notification.email.domain;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

class InMemoryEmailRepository implements EmailRepository {
  Map<Long, Email> table = new ConcurrentHashMap<>();

  @Override
  public void flush() {

  }

  @Override
  public <S extends Email> S saveAndFlush(S entity) {
    return null;
  }

  @Override
  public <S extends Email> List<S> saveAllAndFlush(Iterable<S> entities) {
    return null;
  }

  @Override
  public void deleteAllInBatch(Iterable<Email> entities) {

  }

  @Override
  public void deleteAllByIdInBatch(Iterable<Long> longs) {

  }

  @Override
  public void deleteAllInBatch() {

  }

  @Override
  public Email getOne(Long aLong) {
    return null;
  }

  @Override
  public Email getById(Long aLong) {
    return null;
  }

  @Override
  public Email getReferenceById(Long aLong) {
    return null;
  }

  @Override
  public <S extends Email> Optional<S> findOne(Example<S> example) {
    return Optional.empty();
  }

  @Override
  public <S extends Email> List<S> findAll(Example<S> example) {
    return null;
  }

  @Override
  public <S extends Email> List<S> findAll(Example<S> example, Sort sort) {
    return null;
  }

  @Override
  public <S extends Email> Page<S> findAll(Example<S> example, Pageable pageable) {
    return null;
  }

  @Override
  public <S extends Email> long count(Example<S> example) {
    return 0;
  }

  @Override
  public <S extends Email> boolean exists(Example<S> example) {
    return false;
  }

  @Override
  public <S extends Email, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
    return null;
  }

  @Override
  public Email save(Email entity) {
    if(entity.dto().getEmailId() == null) {
      Long emailId = new Random().nextLong();
      entity = entity.toBuilder()
          .emailId(emailId)
          .build();

    }
    table.put(entity.dto().getEmailId(), entity);
    return entity;
  }

  @Override
  public <S extends Email> List<S> saveAll(Iterable<S> entities) {
    return null;
  }

  @Override
  public Optional<Email> findById(Long aLong) {
    return Optional.empty();
  }

  @Override
  public boolean existsById(Long aLong) {
    return false;
  }

  @Override
  public List<Email> findAll() {
    return new ArrayList<>(table.values());
  }

  @Override
  public List<Email> findAllById(Iterable<Long> longs) {
    return null;
  }

  @Override
  public long count() {
    return 0;
  }

  @Override
  public void deleteById(Long aLong) {

  }

  @Override
  public void delete(Email entity) {

  }

  @Override
  public void deleteAllById(Iterable<? extends Long> longs) {

  }

  @Override
  public void deleteAll(Iterable<? extends Email> entities) {

  }

  @Override
  public void deleteAll() {

  }

  @Override
  public List<Email> findAll(Sort sort) {
    return null;
  }

  @Override
  public Page<Email> findAll(Pageable pageable) {
    return null;
  }
}
