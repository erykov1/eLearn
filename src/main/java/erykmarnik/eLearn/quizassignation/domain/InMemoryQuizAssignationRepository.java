package erykmarnik.eLearn.quizassignation.domain;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

class InMemoryQuizAssignationRepository implements QuizAssignationRepository {
  Map<Long, QuizAssignation> table = new ConcurrentHashMap<>();

  @Override
  public List<QuizAssignation> getByQuizIdAndQuestionId(UUID quizId, Long questionId) {
    return table.values().stream()
        .filter(result -> result.dto().getQuestionId().equals(questionId) && result.dto().getQuizId().equals(quizId))
        .collect(Collectors.toList());
  }

  @Override
  public void flush() {

  }

  @Override
  public <S extends QuizAssignation> S saveAndFlush(S entity) {
    return null;
  }

  @Override
  public <S extends QuizAssignation> List<S> saveAllAndFlush(Iterable<S> entities) {
    return null;
  }

  @Override
  public void deleteAllInBatch(Iterable<QuizAssignation> entities) {

  }

  @Override
  public void deleteAllByIdInBatch(Iterable<Long> longs) {

  }

  @Override
  public void deleteAllInBatch() {

  }

  @Override
  public QuizAssignation getOne(Long aLong) {
    return null;
  }

  @Override
  public QuizAssignation getById(Long aLong) {
    return null;
  }

  @Override
  public QuizAssignation getReferenceById(Long aLong) {
    return null;
  }

  @Override
  public <S extends QuizAssignation> Optional<S> findOne(Example<S> example) {
    return Optional.empty();
  }

  @Override
  public <S extends QuizAssignation> List<S> findAll(Example<S> example) {
    return null;
  }

  @Override
  public <S extends QuizAssignation> List<S> findAll(Example<S> example, Sort sort) {
    return null;
  }

  @Override
  public <S extends QuizAssignation> Page<S> findAll(Example<S> example, Pageable pageable) {
    return null;
  }

  @Override
  public <S extends QuizAssignation> long count(Example<S> example) {
    return 0;
  }

  @Override
  public <S extends QuizAssignation> boolean exists(Example<S> example) {
    return false;
  }

  @Override
  public <S extends QuizAssignation, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
    return null;
  }

  @Override
  public QuizAssignation save(QuizAssignation entity) {
    if(entity.dto().getAssignationId() == null) {
      Long assignationId = new Random().nextLong();
      entity = entity.toBuilder()
          .assignationId(assignationId)
          .build();
    }
    table.put(entity.dto().getAssignationId(), entity);
    return entity;
  }

  @Override
  public <S extends QuizAssignation> List<S> saveAll(Iterable<S> entities) {
    return null;
  }

  @Override
  public Optional<QuizAssignation> findById(Long aLong) {
    return table.values().stream()
        .filter(result -> result.dto().getAssignationId().equals(aLong))
        .findFirst();
  }

  @Override
  public boolean existsById(Long aLong) {
    return false;
  }

  @Override
  public List<QuizAssignation> findAll() {
    return table.values().stream().toList();
  }

  @Override
  public List<QuizAssignation> findAllById(Iterable<Long> longs) {
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
  public void delete(QuizAssignation entity) {

  }

  @Override
  public void deleteAllById(Iterable<? extends Long> longs) {

  }

  @Override
  public void deleteAll(Iterable<? extends QuizAssignation> entities) {
    table.values().removeAll((Collection<QuizAssignation>) entities);
  }

  @Override
  public void deleteAll() {

  }

  @Override
  public List<QuizAssignation> findAll(Sort sort) {
    return null;
  }

  @Override
  public Page<QuizAssignation> findAll(Pageable pageable) {
    return null;
  }
}
