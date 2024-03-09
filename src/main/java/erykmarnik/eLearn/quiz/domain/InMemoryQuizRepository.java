package erykmarnik.eLearn.quiz.domain;

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

class InMemoryQuizRepository implements QuizRepository {
  Map<UUID, Quiz> table = new ConcurrentHashMap<>();

  @Override
  public Optional<Quiz> findByQuizId(UUID quizId) {
    return table.values().stream()
        .filter(quiz -> quiz.dto().getQuizId().equals(quizId))
        .findFirst();
  }

  @Override
  public void flush() {

  }

  @Override
  public <S extends Quiz> S saveAndFlush(S entity) {
    return null;
  }

  @Override
  public <S extends Quiz> List<S> saveAllAndFlush(Iterable<S> entities) {
    return null;
  }

  @Override
  public void deleteAllInBatch(Iterable<Quiz> entities) {

  }

  @Override
  public void deleteAllByIdInBatch(Iterable<UUID> uuids) {

  }

  @Override
  public void deleteAllInBatch() {

  }

  @Override
  public Quiz getOne(UUID uuid) {
    return null;
  }

  @Override
  public Quiz getById(UUID uuid) {
    return null;
  }

  @Override
  public Quiz getReferenceById(UUID uuid) {
    return null;
  }

  @Override
  public <S extends Quiz> Optional<S> findOne(Example<S> example) {
    return Optional.empty();
  }

  @Override
  public <S extends Quiz> List<S> findAll(Example<S> example) {
    return null;
  }

  @Override
  public <S extends Quiz> List<S> findAll(Example<S> example, Sort sort) {
    return null;
  }

  @Override
  public <S extends Quiz> Page<S> findAll(Example<S> example, Pageable pageable) {
    return null;
  }

  @Override
  public <S extends Quiz> long count(Example<S> example) {
    return 0;
  }

  @Override
  public <S extends Quiz> boolean exists(Example<S> example) {
    return false;
  }

  @Override
  public <S extends Quiz, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
    return null;
  }

  @Override
  public Quiz save(Quiz entity) {
    table.put(entity.dto().getQuizId(), entity);
    return entity;
  }

  @Override
  public <S extends Quiz> List<S> saveAll(Iterable<S> entities) {
    return null;
  }

  @Override
  public Optional<Quiz> findById(UUID uuid) {
    return Optional.empty();
  }

  @Override
  public boolean existsById(UUID uuid) {
    return false;
  }

  @Override
  public List<Quiz> findAll() {
    return table.values().stream().toList();
  }

  @Override
  public List<Quiz> findAllById(Iterable<UUID> uuids) {
    return null;
  }

  @Override
  public long count() {
    return 0;
  }

  @Override
  public void deleteById(UUID uuid) {
    table.remove(uuid);
  }

  @Override
  public void delete(Quiz entity) {

  }

  @Override
  public void deleteAllById(Iterable<? extends UUID> uuids) {

  }

  @Override
  public void deleteAll(Iterable<? extends Quiz> entities) {

  }

  @Override
  public void deleteAll() {

  }

  @Override
  public List<Quiz> findAll(Sort sort) {
    return null;
  }

  @Override
  public Page<Quiz> findAll(Pageable pageable) {
    return null;
  }
}
