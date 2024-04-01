package erykmarnik.eLearn.userassignation.domain

import erykmarnik.eLearn.quiz.domain.QuizConfiguration
import erykmarnik.eLearn.quiz.domain.QuizCreator
import erykmarnik.eLearn.quiz.domain.QuizFacade
import erykmarnik.eLearn.quiz.domain.QuizSample
import erykmarnik.eLearn.user.domain.UserConfiguration
import erykmarnik.eLearn.user.domain.UserFacade
import erykmarnik.eLearn.user.domain.UserSample
import erykmarnik.eLearn.userassignation.samples.UserAssignationSample
import erykmarnik.eLearn.utils.InstantProvider
import erykmarnik.eLearn.utils.samples.InstantSample
import org.springframework.context.ApplicationEventPublisher
import spock.lang.Specification

class UserAssignationBaseSpec extends Specification implements InstantSample, UserSample, QuizSample, UserAssignationSample {
  InstantProvider instantProvider = new InstantProvider()
  QuizFacade quizFacade = new QuizConfiguration().quizFacade(new QuizCreator(instantProvider))
  UserFacade userFacade = new UserConfiguration().userFacade()
  ApplicationEventPublisher applicationEventPublisher = Stub()
  UserAssignationFacade userAssignationFacade = new UserAssignationConfiguration().userAssignationFacade(instantProvider, userFacade, quizFacade, applicationEventPublisher)
}
