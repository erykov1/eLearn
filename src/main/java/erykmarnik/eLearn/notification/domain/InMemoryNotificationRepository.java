package erykmarnik.eLearn.notification.domain;

import erykmarnik.eLearn.notification.dto.NotificationStatusDto;
import erykmarnik.eLearn.notification.dto.NotificationTypeDto;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

class InMemoryNotificationRepository implements NotificationRepository {
  Map<Long, Notification> table = new ConcurrentHashMap<>();

  @Override
  public void flush() {

  }

  @Override
  public <S extends Notification> S saveAndFlush(S entity) {
    return null;
  }

  @Override
  public <S extends Notification> List<S> saveAllAndFlush(Iterable<S> entities) {
    return null;
  }

  @Override
  public void deleteAllInBatch(Iterable<Notification> entities) {

  }

  @Override
  public void deleteAllByIdInBatch(Iterable<Long> longs) {

  }

  @Override
  public void deleteAllInBatch() {

  }

  @Override
  public Notification getOne(Long aLong) {
    return null;
  }

  @Override
  public Notification getById(Long aLong) {
    return null;
  }

  @Override
  public Notification getReferenceById(Long aLong) {
    return null;
  }

  @Override
  public <S extends Notification> Optional<S> findOne(Example<S> example) {
    return Optional.empty();
  }

  @Override
  public <S extends Notification> List<S> findAll(Example<S> example) {
    return null;
  }

  @Override
  public <S extends Notification> List<S> findAll(Example<S> example, Sort sort) {
    return null;
  }

  @Override
  public <S extends Notification> Page<S> findAll(Example<S> example, Pageable pageable) {
    return null;
  }

  @Override
  public <S extends Notification> long count(Example<S> example) {
    return 0;
  }

  @Override
  public <S extends Notification> boolean exists(Example<S> example) {
    return false;
  }

  @Override
  public <S extends Notification, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
    return null;
  }

  @Override
  public Notification save(Notification entity) {
    if (entity.dto().getNotificationId() == null) {
      Long notificationId = new Random().nextLong();
      entity = entity.toBuilder()
          .notificationId(notificationId)
          .build();
    }
    table.put(entity.dto().getNotificationId(), entity);
    return entity;
  }

  @Override
  public <S extends Notification> List<S> saveAll(Iterable<S> entities) {
    entities.forEach(this::save);
    return (List<S>) entities;
  }

  @Override
  public Optional<Notification> findById(Long aLong) {
    return table.values().stream()
        .filter(notification -> notification.dto().getNotificationId().equals(aLong))
        .findFirst();
  }

  @Override
  public boolean existsById(Long aLong) {
    return false;
  }

  @Override
  public List<Notification> findAll() {
    return new ArrayList<>(table.values());
  }

  @Override
  public List<Notification> findAllById(Iterable<Long> longs) {
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
  public void delete(Notification entity) {

  }

  @Override
  public void deleteAllById(Iterable<? extends Long> longs) {

  }

  @Override
  public void deleteAll(Iterable<? extends Notification> entities) {

  }

  @Override
  public void deleteAll() {

  }

  @Override
  public List<Notification> findAll(Sort sort) {
    return null;
  }

  @Override
  public Page<Notification> findAll(Pageable pageable) {
    return null;
  }

  @Override
  public List<Notification> findAllPendingNews() {
    return table.values().stream()
        .filter(notification -> notification.dto().getNotificationType().equals(NotificationTypeDto.NEWS))
        .filter(notification -> notification.dto().getNotificationStatusDto().equals(NotificationStatusDto.PENDING))
        .collect(Collectors.toList());
  }

  @Override
  public List<Notification> findAllPendingReminders() {
    return table.values().stream()
        .filter(notification -> notification.dto().getNotificationType().equals(NotificationTypeDto.REMINDER))
        .filter(notification -> notification.dto().getNotificationStatusDto().equals(NotificationStatusDto.PENDING))
        .collect(Collectors.toList());
  }
}
