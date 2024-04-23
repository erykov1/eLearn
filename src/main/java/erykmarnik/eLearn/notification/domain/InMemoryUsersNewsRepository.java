package erykmarnik.eLearn.notification.domain;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

class InMemoryUsersNewsRepository implements UsersNewsRepository {
  Map<UUID, UsersNews> table = new ConcurrentHashMap<>();

  @Override
  public void flush() {

  }

  @Override
  public <S extends UsersNews> S saveAndFlush(S entity) {
    return null;
  }

  @Override
  public <S extends UsersNews> List<S> saveAllAndFlush(Iterable<S> entities) {
    return null;
  }

  @Override
  public void deleteAllInBatch(Iterable<UsersNews> entities) {

  }

  @Override
  public void deleteAllByIdInBatch(Iterable<UUID> uuids) {

  }

  @Override
  public void deleteAllInBatch() {

  }

  @Override
  public UsersNews getOne(UUID uuid) {
    return null;
  }

  @Override
  public UsersNews getById(UUID uuid) {
    return null;
  }

  @Override
  public UsersNews getReferenceById(UUID uuid) {
    return null;
  }

  @Override
  public <S extends UsersNews> Optional<S> findOne(Example<S> example) {
    return Optional.empty();
  }

  @Override
  public <S extends UsersNews> List<S> findAll(Example<S> example) {
    return null;
  }

  @Override
  public <S extends UsersNews> List<S> findAll(Example<S> example, Sort sort) {
    return null;
  }

  @Override
  public <S extends UsersNews> Page<S> findAll(Example<S> example, Pageable pageable) {
    return null;
  }

  @Override
  public <S extends UsersNews> long count(Example<S> example) {
    return 0;
  }

  @Override
  public <S extends UsersNews> boolean exists(Example<S> example) {
    return false;
  }

  @Override
  public <S extends UsersNews, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
    return null;
  }

  @Override
  public UsersNews save(UsersNews entity) {
    table.put(entity.dto().getUsersNewsId(), entity);
    return entity;
  }

  @Override
  public <S extends UsersNews> List<S> saveAll(Iterable<S> entities) {
    return null;
  }

  @Override
  public Optional<UsersNews> findById(UUID uuid) {
    return Optional.empty();
  }

  @Override
  public boolean existsById(UUID uuid) {
    return false;
  }

  @Override
  public List<UsersNews> findAll() {
    return new ArrayList<>(table.values());
  }

  @Override
  public List<UsersNews> findAllById(Iterable<UUID> uuids) {
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
  public void delete(UsersNews entity) {

  }

  @Override
  public void deleteAllById(Iterable<? extends UUID> uuids) {

  }

  @Override
  public void deleteAll(Iterable<? extends UsersNews> entities) {

  }

  @Override
  public void deleteAll() {

  }

  @Override
  public List<UsersNews> findAll(Sort sort) {
    return null;
  }

  @Override
  public Page<UsersNews> findAll(Pageable pageable) {
    return null;
  }
}
