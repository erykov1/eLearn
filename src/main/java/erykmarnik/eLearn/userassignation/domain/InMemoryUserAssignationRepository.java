package erykmarnik.eLearn.userassignation.domain;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

class InMemoryUserAssignationRepository implements UserAssignationRepository {
  Map<Long, UserAssignation> table = new ConcurrentHashMap<>();

  @Override
  public Optional<UserAssignation> findUserAssignationById(Long id) {
    return table.values().stream()
        .filter(userAssignation -> userAssignation.dto().getId().equals(id))
        .findFirst();
  }

  @Override
  public Optional<UserAssignation> findUserAssignationByUserIdAndLearningObjectId(Long userId, UUID learningObjectId) {
    return table.values().stream()
        .filter(userAssignation -> userAssignation.dto().getUserId().equals(userId))
        .filter(userAssignation -> userAssignation.dto().getLearningObjectId().equals(learningObjectId))
        .findFirst();
  }

  @Override
  public void flush() {

  }

  @Override
  public <S extends UserAssignation> S saveAndFlush(S entity) {
    return null;
  }

  @Override
  public <S extends UserAssignation> List<S> saveAllAndFlush(Iterable<S> entities) {
    return null;
  }

  @Override
  public void deleteAllInBatch(Iterable<UserAssignation> entities) {

  }

  @Override
  public void deleteAllByIdInBatch(Iterable<Long> longs) {

  }

  @Override
  public void deleteAllInBatch() {

  }

  @Override
  public UserAssignation getOne(Long aLong) {
    return null;
  }

  @Override
  public UserAssignation getById(Long aLong) {
    return null;
  }

  @Override
  public UserAssignation getReferenceById(Long aLong) {
    return null;
  }

  @Override
  public <S extends UserAssignation> Optional<S> findOne(Example<S> example) {
    return Optional.empty();
  }

  @Override
  public <S extends UserAssignation> List<S> findAll(Example<S> example) {
    return null;
  }

  @Override
  public <S extends UserAssignation> List<S> findAll(Example<S> example, Sort sort) {
    return null;
  }

  @Override
  public <S extends UserAssignation> Page<S> findAll(Example<S> example, Pageable pageable) {
    return null;
  }

  @Override
  public <S extends UserAssignation> long count(Example<S> example) {
    return 0;
  }

  @Override
  public <S extends UserAssignation> boolean exists(Example<S> example) {
    return false;
  }

  @Override
  public <S extends UserAssignation, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
    return null;
  }

  @Override
  public UserAssignation save(UserAssignation entity) {
    if (entity.dto().getId() == null) {
      Long id = new Random().nextLong();
      entity = entity.toBuilder()
          .id(id)
          .build();
    }
    table.put(entity.dto().getId(), entity);
    return entity;
  }

  @Override
  public <S extends UserAssignation> List<S> saveAll(Iterable<S> entities) {
    return null;
  }

  @Override
  public Optional<UserAssignation> findById(Long aLong) {
    return Optional.empty();
  }

  @Override
  public boolean existsById(Long aLong) {
    return false;
  }

  @Override
  public List<UserAssignation> findAll() {
    return new ArrayList<>(table.values());
  }

  @Override
  public List<UserAssignation> findAllById(Iterable<Long> longs) {
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
  public void delete(UserAssignation entity) {

  }

  @Override
  public void deleteAllById(Iterable<? extends Long> longs) {

  }

  @Override
  public void deleteAll(Iterable<? extends UserAssignation> entities) {

  }

  @Override
  public void deleteAll() {

  }

  @Override
  public List<UserAssignation> findAll(Sort sort) {
    return null;
  }

  @Override
  public Page<UserAssignation> findAll(Pageable pageable) {
    return null;
  }
}
