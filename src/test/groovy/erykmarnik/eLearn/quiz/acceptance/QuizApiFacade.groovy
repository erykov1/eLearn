package erykmarnik.eLearn.quiz.acceptance

import com.fasterxml.jackson.databind.ObjectMapper
import erykmarnik.eLearn.integration.ELearnApi
import erykmarnik.eLearn.quiz.dto.CreateQuizDto
import erykmarnik.eLearn.quiz.dto.QuizDifficultyDto
import erykmarnik.eLearn.quiz.dto.QuizDto
import org.springframework.http.MediaType
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders

import java.nio.charset.StandardCharsets

class QuizApiFacade extends ELearnApi {
  private final MockMvc mvc
  private final ObjectMapper mapper

  QuizApiFacade(MockMvc mvc, ObjectMapper mapper) {
    this.mvc = mvc
    this.mapper = mapper
  }

  QuizDto createQuiz(CreateQuizDto createQuiz) {
    ResultActions perform = mvc.perform(MockMvcRequestBuilders.post("/api/quiz/create")
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(createQuiz))
    )
    checkResponse(perform.andReturn().response)
    QuizDto value = mapper.readValue(perform.andReturn().response.getContentAsString(StandardCharsets.UTF_8), mapper.getTypeFactory().constructType(QuizDto.class))
    value
  }

  QuizDto changeQuizDifficulty(UUID quizId, QuizDifficultyDto quizDifficulty) {
    ResultActions perform = mvc.perform(MockMvcRequestBuilders.put("/api/quiz/difficulty/{quizId}", quizId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(quizDifficulty))
    )
    checkResponse(perform.andReturn().response)
    QuizDto value = mapper.readValue(perform.andReturn().response.getContentAsString(StandardCharsets.UTF_8), mapper.getTypeFactory().constructType(QuizDto.class))
    value
  }

  QuizDto findById(UUID quizId) {
    ResultActions perform = mvc.perform(MockMvcRequestBuilders.get("/api/quiz/{quizId}", quizId))
    checkResponse(perform.andReturn().response)
    QuizDto value = mapper.readValue(perform.andReturn().response.getContentAsString(StandardCharsets.UTF_8), mapper.getTypeFactory().constructType(QuizDto.class))
    value
  }

  QuizDto changeQuizName(UUID quizId, String quizName) {
    ResultActions perform = mvc.perform(MockMvcRequestBuilders.put("/api/quiz/name/{quizId}", quizId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(quizName))
    )
    checkResponse(perform.andReturn().response)
    QuizDto value = mapper.readValue(perform.andReturn().response.getContentAsString(StandardCharsets.UTF_8), mapper.getTypeFactory().constructType(QuizDto.class))
    value
  }

  void cleanup() {
    ResultActions perform = mvc.perform(MockMvcRequestBuilders.get("/api/quiz/cleanup"))
    checkResponse(perform.andReturn().response)
  }
}
