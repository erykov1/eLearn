package erykmarnik.eLearn.userresult.domain;

import org.springframework.data.domain.*;
import org.springframework.data.repository.query.FluentQuery;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

class InMemoryUserResultRepository implements UserResultRepository {
  Map<UUID, UserResult> table = new ConcurrentHashMap<>();

  @Override
  public void flush() {

  }

  @Override
  public <S extends UserResult> S saveAndFlush(S entity) {
    return null;
  }

  @Override
  public <S extends UserResult> List<S> saveAllAndFlush(Iterable<S> entities) {
    return null;
  }

  @Override
  public void deleteAllInBatch(Iterable<UserResult> entities) {

  }

  @Override
  public void deleteAllByIdInBatch(Iterable<UUID> uuids) {

  }

  @Override
  public void deleteAllInBatch() {

  }

  @Override
  public UserResult getOne(UUID uuid) {
    return null;
  }

  @Override
  public UserResult getById(UUID uuid) {
    return null;
  }

  @Override
  public UserResult getReferenceById(UUID uuid) {
    return null;
  }

  @Override
  public <S extends UserResult> Optional<S> findOne(Example<S> example) {
    return Optional.empty();
  }

  @Override
  public <S extends UserResult> List<S> findAll(Example<S> example) {
    return null;
  }

  @Override
  public <S extends UserResult> List<S> findAll(Example<S> example, Sort sort) {
    return null;
  }

  @Override
  public <S extends UserResult> Page<S> findAll(Example<S> example, Pageable pageable) {
    return null;
  }

  @Override
  public <S extends UserResult> long count(Example<S> example) {
    return 0;
  }

  @Override
  public <S extends UserResult> boolean exists(Example<S> example) {
    return false;
  }

  @Override
  public <S extends UserResult, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
    return null;
  }

  @Override
  public UserResult save(UserResult entity) {
    table.put(entity.dto().getId(), entity);
    return entity;
  }

  @Override
  public <S extends UserResult> List<S> saveAll(Iterable<S> entities) {
    return null;
  }

  @Override
  public Optional<UserResult> findById(UUID uuid) {
    return Optional.empty();
  }

  @Override
  public boolean existsById(UUID uuid) {
    return false;
  }

  @Override
  public List<UserResult> findAll() {
    return table.values().stream().toList();
  }

  @Override
  public List<UserResult> findAllById(Iterable<UUID> uuids) {
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
  public void delete(UserResult entity) {

  }

  @Override
  public void deleteAllById(Iterable<? extends UUID> uuids) {

  }

  @Override
  public void deleteAll(Iterable<? extends UserResult> entities) {

  }

  @Override
  public void deleteAll() {

  }

  @Override
  public List<UserResult> findAll(Sort sort) {
    return null;
  }

  @Override
  public Page<UserResult> findAll(Pageable pageable) {
    return new PageImpl<>(new ArrayList<>(table.values()), pageable, table.size());
  }

  @Override
  public Optional<UserResult> findUserResultById(UUID id) {
    return table.values().stream()
        .filter(result -> result.dto().getId().equals(id))
        .findFirst();
  }

  @Override
  public Optional<UserResult> findUserResultByUserIdAndLearningObjectId(Long userId, UUID learningObjectId) {
    return table.values().stream()
        .filter(result -> result.dto().getUserId().equals(userId))
        .filter(result -> result.dto().getLearningObjectId().equals(learningObjectId))
        .findFirst();
  }

  @Override
  public List<UserResult> findAllUserResultsByUserId(Long userId) {
    return table.values().stream()
        .filter(result -> result.dto().getUserId().equals(userId))
        .collect(Collectors.toList());
  }

  @Override
  public Page<UserResult> findAllUserResultsByUserResultVisibilityType(UserResultVisibilityType userResultVisibilityType, Pageable pageable) {
    List<UserResult> results = table.values().stream()
        .filter(userResult -> userResult.dto().getUserResultVisibilityType().equals(userResultVisibilityType.dto()))
        .toList();
    return new PageImpl<>(new ArrayList<>(results), pageable, results.size());
  }
}
