package erykmarnik.eLearn.notification.sample

import erykmarnik.eLearn.quiz.dto.CreatedLearningObjectEvent
import erykmarnik.eLearn.quiz.dto.QuizDifficultyDto

trait CreatedLearningObjectSample {
  static CREATED_LEARNING_OBJECT_ID = UUID.fromString("196e0c50-a2a2-4c31-85f2-51c88c6c8798")
  static PYTHON_CREATED_LEARNING_OBJECT_ID = UUID.fromString("00bcdfb5-d2c9-42e8-929b-eda5ded6e11e")

  private static Map<String, Object> DEFAULT_CREATED_LEARNING_OBJECT_DATA = [
      learningObjectId: CREATED_LEARNING_OBJECT_ID,
      learningObjectName: "New learning object",
      learningObjectType: QuizDifficultyDto.BEGINNER
  ] as Map<String, Object>

  CreatedLearningObjectEvent createLearningObjectEvent(Map<String, Object> changes = [:]) {
    def changesWithDefaults = DEFAULT_CREATED_LEARNING_OBJECT_DATA + changes
    CreatedLearningObjectEvent.builder()
      .learningObjectId(changesWithDefaults.learningObjectId as UUID)
      .learningObjectName(changesWithDefaults.learningObjectName as String)
      .learningObjectType(changesWithDefaults.learningObjectType as QuizDifficultyDto)
      .build()
  }
}