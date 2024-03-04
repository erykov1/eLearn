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

class InMemoryCloseQuestionRepository implements CloseQuestionRepository {
  Map<Long, CloseQuestion> table = new ConcurrentHashMap<>();

  @Override
  public void flush() {

  }

  @Override
  public <S extends CloseQuestion> S saveAndFlush(S entity) {
    return null;
  }

  @Override
  public <S extends CloseQuestion> List<S> saveAllAndFlush(Iterable<S> entities) {
    return null;
  }

  @Override
  public void deleteAllInBatch(Iterable<CloseQuestion> entities) {

  }

  @Override
  public void deleteAllByIdInBatch(Iterable<Long> longs) {

  }

  @Override
  public void deleteAllInBatch() {

  }

  @Override
  public CloseQuestion getOne(Long aLong) {
    return null;
  }

  @Override
  public CloseQuestion getById(Long aLong) {
    return null;
  }

  @Override
  public CloseQuestion getReferenceById(Long aLong) {
    return null;
  }

  @Override
  public <S extends CloseQuestion> Optional<S> findOne(Example<S> example) {
    return Optional.empty();
  }

  @Override
  public <S extends CloseQuestion> List<S> findAll(Example<S> example) {
    return null;
  }

  @Override
  public <S extends CloseQuestion> List<S> findAll(Example<S> example, Sort sort) {
    return null;
  }

  @Override
  public <S extends CloseQuestion> Page<S> findAll(Example<S> example, Pageable pageable) {
    return null;
  }

  @Override
  public <S extends CloseQuestion> long count(Example<S> example) {
    return 0;
  }

  @Override
  public <S extends CloseQuestion> boolean exists(Example<S> example) {
    return false;
  }

  @Override
  public <S extends CloseQuestion, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
    return null;
  }

  @Override
  public CloseQuestion save(CloseQuestion entity) {
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
  public <S extends CloseQuestion> List<S> saveAll(Iterable<S> entities) {
    return null;
  }

  @Override
  public Optional<CloseQuestion> findById(Long aLong) {
    return Optional.empty();
  }

  @Override
  public boolean existsById(Long aLong) {
    return false;
  }

  @Override
  public List<CloseQuestion> findAll() {
    return null;
  }

  @Override
  public List<CloseQuestion> findAllById(Iterable<Long> longs) {
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
  public void delete(CloseQuestion entity) {

  }

  @Override
  public void deleteAllById(Iterable<? extends Long> longs) {

  }

  @Override
  public void deleteAll(Iterable<? extends CloseQuestion> entities) {

  }

  @Override
  public void deleteAll() {

  }

  @Override
  public List<CloseQuestion> findAll(Sort sort) {
    return null;
  }

  @Override
  public Page<CloseQuestion> findAll(Pageable pageable) {
    return null;
  }

  @Override
  public Optional<CloseQuestion> findByQuestionId(Long questionId) {
    return table.values()
        .stream()
        .filter(question -> question.dto().getQuestionId().equals(questionId))
        .findFirst();
  }
}
