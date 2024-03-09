package erykmarnik.eLearn.quizassignation.domain;

import erykmarnik.eLearn.question.domain.QuestionFacade;
import erykmarnik.eLearn.quiz.domain.QuizFacade;
import erykmarnik.eLearn.quizassignation.dto.CreateQuizAssignationDto;
import erykmarnik.eLearn.quizassignation.dto.QuizAssignationDto;
import erykmarnik.eLearn.quizassignation.exception.QuizAssignationNotFoundException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Builder
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class QuizAssignationFacade {
  QuizAssignationRepository quizAssignationRepository;
  QuizFacade quizFacade;
  QuestionFacade questionFacade;
  QuizAssignationCreator quizAssignationCreator;

  public QuizAssignationDto createQuizAssignation(CreateQuizAssignationDto createQuizAssignation) {
    quizFacade.checkIfExists(createQuizAssignation.getQuizId());
    questionFacade.checkIfExists(createQuizAssignation.getQuestionId());
    log.info("adding question {} to quiz {}", createQuizAssignation.getQuestionId(), createQuizAssignation.getQuizId());
    return quizAssignationRepository.save(quizAssignationCreator.createQuizAssignation(createQuizAssignation)).dto();
  }

  public void deleteQuizAssignation(UUID quizId, Long questionId) {
    log.info("deleting quiz assignation with quiz {} and question {}", quizId, questionId);
    quizFacade.checkIfExists(quizId);
    questionFacade.checkIfExists(questionId);
    List<QuizAssignation> delete = quizAssignationRepository.getByQuizIdAndQuestionId(quizId, questionId);
    quizAssignationRepository.deleteAll(delete);
  }

  public List<QuizAssignationDto> findAllAssignations() {
    return quizAssignationRepository.findAll().stream()
        .map(QuizAssignation::dto)
        .collect(Collectors.toList());
  }

  public QuizAssignationDto findByQuizAssignation(Long quizAssignationId) {
    return quizAssignationRepository.findById(quizAssignationId)
        .orElseThrow(() -> new QuizAssignationNotFoundException("Quiz assignation not found")).dto();
  }

  public List<Long> findAllAssignedQuestionsToQuiz(UUID quizId) {
    log.info("finding all assigned questions to quiz {}", quizId);
    return quizAssignationRepository.findAll().stream()
        .filter(result -> result.dto().getQuizId().equals(quizId))
        .map(result -> result.dto().getQuestionId())
        .collect(Collectors.toList());
  }

  public void cleanup() {
    quizAssignationRepository.deleteAll();
  }
}
