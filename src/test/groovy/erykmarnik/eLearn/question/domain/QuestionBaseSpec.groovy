package erykmarnik.eLearn.question.domain

import erykmarnik.eLearn.question.samples.CloseQuestionSample
import erykmarnik.eLearn.question.samples.ImageLinkSample
import erykmarnik.eLearn.question.samples.OpenQuestionSample
import spock.lang.Specification

class QuestionBaseSpec extends Specification implements CloseQuestionSample, OpenQuestionSample, ImageLinkSample {
  QuestionFacade questionFacade = new QuestionConfiguration().questionFacade()
}
