package erykmarnik.eLearn.question.domain;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

class InMemoryOpenQuestionRepository implements OpenQuestionRepository {
  Map<Long, OpenQuestion> table = new ConcurrentHashMap<>();

  @Override
  public void flush() {

  }

  @Override
  public <S extends OpenQuestion> S saveAndFlush(S entity) {
    return null;
  }

  @Override
  public <S extends OpenQuestion> List<S> saveAllAndFlush(Iterable<S> entities) {
    return null;
  }

  @Override
  public void deleteAllInBatch(Iterable<OpenQuestion> entities) {

  }

  @Override
  public void deleteAllByIdInBatch(Iterable<Long> longs) {

  }

  @Override
  public void deleteAllInBatch() {

  }

  @Override
  public OpenQuestion getOne(Long aLong) {
    return null;
  }

  @Override
  public OpenQuestion getById(Long aLong) {
    return null;
  }

  @Override
  public OpenQuestion getReferenceById(Long aLong) {
    return null;
  }

  @Override
  public <S extends OpenQuestion> Optional<S> findOne(Example<S> example) {
    return Optional.empty();
  }

  @Override
  public <S extends OpenQuestion> List<S> findAll(Example<S> example) {
    return null;
  }

  @Override
  public <S extends OpenQuestion> List<S> findAll(Example<S> example, Sort sort) {
    return null;
  }

  @Override
  public <S extends OpenQuestion> Page<S> findAll(Example<S> example, Pageable pageable) {
    return null;
  }

  @Override
  public <S extends OpenQuestion> long count(Example<S> example) {
    return 0;
  }

  @Override
  public <S extends OpenQuestion> boolean exists(Example<S> example) {
    return false;
  }

  @Override
  public <S extends OpenQuestion, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
    return null;
  }

  @Override
  public OpenQuestion save(OpenQuestion entity) {
    if (entity.dto().getQuestionId() == null) {
      Long questionId = new Random().nextLong();
      entity = entity.toBuilder()
          .questionId(questionId)
          .build();
    }
    table.put(entity.dto().getQuestionId(), entity);
    return entity;
  }

  @Override
  public <S extends OpenQuestion> List<S> saveAll(Iterable<S> entities) {
    return null;
  }

  @Override
  public Optional<OpenQuestion> findById(Long aLong) {
    return Optional.empty();
  }

  @Override
  public boolean existsById(Long aLong) {
    return false;
  }

  @Override
  public List<OpenQuestion> findAll() {
    return null;
  }

  @Override
  public List<OpenQuestion> findAllById(Iterable<Long> longs) {
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
  public void delete(OpenQuestion entity) {

  }

  @Override
  public void deleteAllById(Iterable<? extends Long> longs) {

  }

  @Override
  public void deleteAll(Iterable<? extends OpenQuestion> entities) {

  }

  @Override
  public void deleteAll() {

  }

  @Override
  public List<OpenQuestion> findAll(Sort sort) {
    return null;
  }

  @Override
  public Page<OpenQuestion> findAll(Pageable pageable) {
    return null;
  }

  @Override
  public Optional<OpenQuestion> findByQuestionId(Long questionId) {
    return table.values().stream()
        .filter(question -> question.dto().getQuestionId().equals(questionId))
        .findFirst();
  }
}
