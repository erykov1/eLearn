package erykmarnik.eLearn.notification.domain;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

class InMemoryCreatedLearningObjectRepository implements CreatedLearningObjectRepository {
  Map<UUID, CreatedLearningObject> table = new ConcurrentHashMap<>();

  @Override
  public void flush() {

  }

  @Override
  public <S extends CreatedLearningObject> S saveAndFlush(S entity) {
    return null;
  }

  @Override
  public <S extends CreatedLearningObject> List<S> saveAllAndFlush(Iterable<S> entities) {
    return null;
  }

  @Override
  public void deleteAllInBatch(Iterable<CreatedLearningObject> entities) {

  }

  @Override
  public void deleteAllByIdInBatch(Iterable<UUID> uuids) {

  }

  @Override
  public void deleteAllInBatch() {

  }

  @Override
  public CreatedLearningObject getOne(UUID uuid) {
    return null;
  }

  @Override
  public CreatedLearningObject getById(UUID uuid) {
    return null;
  }

  @Override
  public CreatedLearningObject getReferenceById(UUID uuid) {
    return null;
  }

  @Override
  public <S extends CreatedLearningObject> Optional<S> findOne(Example<S> example) {
    return Optional.empty();
  }

  @Override
  public <S extends CreatedLearningObject> List<S> findAll(Example<S> example) {
    return null;
  }

  @Override
  public <S extends CreatedLearningObject> List<S> findAll(Example<S> example, Sort sort) {
    return null;
  }

  @Override
  public <S extends CreatedLearningObject> Page<S> findAll(Example<S> example, Pageable pageable) {
    return null;
  }

  @Override
  public <S extends CreatedLearningObject> long count(Example<S> example) {
    return 0;
  }

  @Override
  public <S extends CreatedLearningObject> boolean exists(Example<S> example) {
    return false;
  }

  @Override
  public <S extends CreatedLearningObject, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
    return null;
  }

  @Override
  public CreatedLearningObject save(CreatedLearningObject entity) {
    table.put(entity.getLearningObjectId(), entity);
    return entity;
  }

  @Override
  public <S extends CreatedLearningObject> List<S> saveAll(Iterable<S> entities) {
    return null;
  }

  @Override
  public Optional<CreatedLearningObject> findById(UUID uuid) {
    return table.values().stream()
        .filter(createdLearningObject -> createdLearningObject.getLearningObjectId().equals(uuid))
        .findFirst();
  }

  @Override
  public boolean existsById(UUID uuid) {
    return false;
  }

  @Override
  public List<CreatedLearningObject> findAll() {
    return null;
  }

  @Override
  public List<CreatedLearningObject> findAllById(Iterable<UUID> uuids) {
    return null;
  }

  @Override
  public long count() {
    return 0;
  }

  @Override
  public void deleteById(UUID uuid) {

  }

  @Override
  public void delete(CreatedLearningObject entity) {

  }

  @Override
  public void deleteAllById(Iterable<? extends UUID> uuids) {

  }

  @Override
  public void deleteAll(Iterable<? extends CreatedLearningObject> entities) {

  }

  @Override
  public void deleteAll() {

  }

  @Override
  public List<CreatedLearningObject> findAll(Sort sort) {
    return null;
  }

  @Override
  public Page<CreatedLearningObject> findAll(Pageable pageable) {
    return null;
  }
}
