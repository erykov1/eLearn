package erykmarnik.eLearn.integration

import com.fasterxml.jackson.databind.ObjectMapper
import erykmarnik.eLearn.question.dto.CloseQuestionDto
import erykmarnik.eLearn.question.dto.CreateCloseQuestionDto
import erykmarnik.eLearn.question.dto.CreateOpenQuestionDto
import erykmarnik.eLearn.question.dto.EditQuestionDto
import erykmarnik.eLearn.question.dto.OpenQuestionDto
import erykmarnik.eLearn.quiz.dto.CreateQuizDto
import erykmarnik.eLearn.quiz.dto.NewQuizNameDto
import erykmarnik.eLearn.quiz.dto.QuizDifficultyDto
import erykmarnik.eLearn.quiz.dto.QuizDto
import erykmarnik.eLearn.quizassignation.dto.CreateQuizAssignationDto
import erykmarnik.eLearn.quizassignation.dto.DeleteQuizAssignationDto
import erykmarnik.eLearn.quizassignation.dto.QuizAssignationDto
import erykmarnik.eLearn.user.dto.CreateUserDto
import erykmarnik.eLearn.user.dto.UserDto
import org.springframework.http.MediaType
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import java.nio.charset.StandardCharsets

class ELearnApi {
  private final UserApi userApi
  private final QuestionApi questionApi
  private final QuizApi quizApi
  private final QuizAssignationApi quizAssignationApi

  ELearnApi(MockMvc mockMvc, ObjectMapper objectMapper) {
    this.userApi = new UserApi(mockMvc, objectMapper)
    this.questionApi = new QuestionApi(mockMvc, objectMapper)
    this.quizApi = new QuizApi(mockMvc, objectMapper)
    this.quizAssignationApi = new QuizAssignationApi(mockMvc, objectMapper)
  }

  UserApi user() {
    userApi
  }

  QuestionApi question() {
    questionApi
  }

  QuizApi quiz() {
    quizApi
  }

  QuizAssignationApi quizAssignation() {
    quizAssignationApi
  }

  class UserApi {
    private final MockMvc mvc
    private final ObjectMapper mapper

    UserApi(MockMvc mvc, ObjectMapper mapper) {
      this.mvc = mvc
      this.mapper = mapper
    }

    UserDto registerStudent(CreateUserDto createStudent) {
      ResultActions perform = mvc.perform(MockMvcRequestBuilders.post("/api/user/register")
          .contentType(MediaType.APPLICATION_JSON)
          .content(mapper.writeValueAsString(createStudent))
      )
      checkResponse(perform.andReturn().response)
      UserDto value = mapper.readValue(perform.andReturn().response.getContentAsString(StandardCharsets.UTF_8), mapper.getTypeFactory().constructType(UserDto.class))
      value
    }

    UserDto registerAdmin(CreateUserDto createAdmin) {
      ResultActions perform = mvc.perform(MockMvcRequestBuilders.post("/api/user/register/admin")
          .contentType(MediaType.APPLICATION_JSON)
          .content(mapper.writeValueAsString(createAdmin))
      )
      checkResponse(perform.andReturn().response)
      UserDto value = mapper.readValue(perform.andReturn().response.getContentAsString(StandardCharsets.UTF_8), mapper.getTypeFactory().constructType(UserDto.class))
      value
    }

    List<UserDto> getAll() {
      ResultActions perform = mvc.perform(MockMvcRequestBuilders.post("/api/user/all"))
      checkResponse(perform.andReturn().response)
      List<UserDto> value = mapper.readValue(perform.andReturn().response.getContentAsString(StandardCharsets.UTF_8),
          mapper.getTypeFactory().constructCollectionType(List.class, UserDto.class))
      value
    }

    void cleanup() {
      ResultActions perform = mvc.perform(MockMvcRequestBuilders.get("/api/user/cleanup"))
      checkResponse(perform.andReturn().response)
    }
  }

  class QuestionApi {
    private final MockMvc mvc
    private final ObjectMapper mapper

    QuestionApi(MockMvc mvc, ObjectMapper mapper) {
      this.mvc = mvc
      this.mapper = mapper
    }

    CloseQuestionDto createCloseQuestion(CreateCloseQuestionDto createCloseQuestion) {
      ResultActions perform = mvc.perform(MockMvcRequestBuilders.post("/api/question/create/closeQuestion")
          .contentType(MediaType.APPLICATION_JSON)
          .content(mapper.writeValueAsString(createCloseQuestion))
      )
      checkResponse(perform.andReturn().response)
      CloseQuestionDto value = mapper.readValue(perform.andReturn().response.getContentAsString(StandardCharsets.UTF_8), mapper.getTypeFactory().constructType(CloseQuestionDto.class))
      value
    }

    OpenQuestionDto createOpenQuestion(CreateOpenQuestionDto createCloseQuestion) {
      ResultActions perform = mvc.perform(MockMvcRequestBuilders.post("/api/question/create/openQuestion")
          .contentType(MediaType.APPLICATION_JSON)
          .content(mapper.writeValueAsString(createCloseQuestion))
      )
      checkResponse(perform.andReturn().response)
      OpenQuestionDto value = mapper.readValue(perform.andReturn().response.getContentAsString(StandardCharsets.UTF_8), mapper.getTypeFactory().constructType(OpenQuestionDto.class))
      value
    }

    CloseQuestionDto editCloseQuestion(EditQuestionDto editQuestion, Long questionId) {
      ResultActions perform = mvc.perform(MockMvcRequestBuilders.patch("/api/question/edit/closeQuestion/{questionId}", questionId)
          .contentType(MediaType.APPLICATION_JSON)
          .content(mapper.writeValueAsString(editQuestion))
      )
      checkResponse(perform.andReturn().response)
      CloseQuestionDto value = mapper.readValue(perform.andReturn().response.getContentAsString(StandardCharsets.UTF_8), mapper.getTypeFactory().constructType(CloseQuestionDto.class))
      value
    }

    OpenQuestionDto editOpenQuestion(EditQuestionDto editQuestion, Long questionId) {
      ResultActions perform = mvc.perform(MockMvcRequestBuilders.patch("/api/question/edit/openQuestion/{questionId}", questionId)
          .contentType(MediaType.APPLICATION_JSON)
          .content(mapper.writeValueAsString(editQuestion))
      )
      checkResponse(perform.andReturn().response)
      OpenQuestionDto value = mapper.readValue(perform.andReturn().response.getContentAsString(StandardCharsets.UTF_8), mapper.getTypeFactory().constructType(OpenQuestionDto.class))
      value
    }

    CloseQuestionDto getCloseQuestion(Long questionId) {
      ResultActions perform = mvc.perform(MockMvcRequestBuilders.get("/api/question/closeQuestion/{questionId}", questionId))
      checkResponse(perform.andReturn().response)
      CloseQuestionDto value = mapper.readValue(perform.andReturn().response.getContentAsString(StandardCharsets.UTF_8), mapper.getTypeFactory().constructType(CloseQuestionDto.class))
      value
    }

    OpenQuestionDto getOpenQuestion(Long questionId) {
      ResultActions perform = mvc.perform(MockMvcRequestBuilders.get("/api/question/openQuestion/{questionId}", questionId))
      checkResponse(perform.andReturn().response)
      OpenQuestionDto value = mapper.readValue(perform.andReturn().response.getContentAsString(StandardCharsets.UTF_8), mapper.getTypeFactory().constructType(OpenQuestionDto.class))
      value
    }

    void cleanup() {
      ResultActions perform = mvc.perform(MockMvcRequestBuilders.get("/api/question/cleanup"))
      checkResponse(perform.andReturn().response)
    }
  }

  class QuizApi {
    private final MockMvc mvc
    private final ObjectMapper mapper

    QuizApi(MockMvc mvc, ObjectMapper mapper) {
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

  class QuizAssignationApi {
    private final MockMvc mvc
    private final ObjectMapper mapper

    QuizAssignationApi(MockMvc mvc, ObjectMapper mapper) {
      this.mvc = mvc
      this.mapper = mapper
    }

    QuizAssignationDto createQuizAssignation(CreateQuizAssignationDto createQuizAssignation) {
      ResultActions perform = mvc.perform(MockMvcRequestBuilders.post("/api/quizAssignation/create")
          .contentType(MediaType.APPLICATION_JSON)
          .content(mapper.writeValueAsString(createQuizAssignation))
      )
      checkResponse(perform.andReturn().response)
      QuizAssignationDto value = mapper.readValue(perform.andReturn().response.getContentAsString(StandardCharsets.UTF_8), mapper.getTypeFactory().constructType(QuizAssignationDto.class))
      value
    }

    void deleteQuizAssignation(UUID quizId, Long questionId) {
      ResultActions perform = mvc.perform(MockMvcRequestBuilders.delete("/api/quizAssignation/delete/{quizId}/{questionId}", quizId, questionId)
          .contentType(MediaType.APPLICATION_JSON)
      )
      checkResponse(perform.andReturn().response)
    }

    List<Long> getAllQuestionsAssignedToQuiz(UUID quizId) {
      ResultActions perform = mvc.perform(MockMvcRequestBuilders.get("/api/quizAssignation/getQuestions/{quizId}", quizId)
          .contentType(MediaType.APPLICATION_JSON)
      )
      checkResponse(perform.andReturn().response)
      List<Long> value = mapper.readValue(perform.andReturn().response.getContentAsString(StandardCharsets.UTF_8),
          mapper.getTypeFactory().constructCollectionType(List.class, Long.class))
      value
    }

    QuizAssignationDto getQuizAssignation(Long assignationId) {
      ResultActions perform = mvc.perform(MockMvcRequestBuilders.get("/api/quizAssignation/get/{assignationId}", assignationId)
          .contentType(MediaType.APPLICATION_JSON)
      )
      checkResponse(perform.andReturn().response)
      QuizAssignationDto value = mapper.readValue(perform.andReturn().response.getContentAsString(StandardCharsets.UTF_8), mapper.getTypeFactory().constructType(QuizAssignationDto.class))
      value
    }

    void cleanup() {
      ResultActions perform = mvc.perform(MockMvcRequestBuilders.get("/api/quizAssignation/cleanup"))
      checkResponse(perform.andReturn().response)
    }
  }

  private static void checkResponse(MockHttpServletResponse response) {
    if(response.status != 200) {
      throw new RuntimeException(response.getIncludedUrl() + " failed with status " + response.status)
    }
  }
}
