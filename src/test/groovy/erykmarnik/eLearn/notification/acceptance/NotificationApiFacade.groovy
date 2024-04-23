package erykmarnik.eLearn.notification.acceptance

import com.fasterxml.jackson.databind.ObjectMapper
import erykmarnik.eLearn.integration.ELearnApi
import erykmarnik.eLearn.notification.dto.CreateNotificationDto
import erykmarnik.eLearn.notification.dto.EnrollForNewsDto
import erykmarnik.eLearn.notification.dto.NotificationDto
import erykmarnik.eLearn.notification.dto.NotificationStatusDto
import erykmarnik.eLearn.notification.dto.UsersNewsDto
import org.springframework.http.MediaType
import org.springframework.security.core.parameters.P
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import java.nio.charset.StandardCharsets

class NotificationApiFacade extends ELearnApi {
  private final MockMvc mvc
  private final ObjectMapper mapper

  NotificationApiFacade(MockMvc mvc, ObjectMapper mapper) {
    this.mvc = mvc
    this.mapper = mapper
  }

  UsersNewsDto enrollForNews(EnrollForNewsDto enrollForNews) {
    ResultActions perform = mvc.perform(MockMvcRequestBuilders.post("/api/notification/enroll")
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(enrollForNews.userMail))
    )
    checkResponse(perform.andReturn().response)
    UsersNewsDto value = mapper.readValue(perform.andReturn().response.getContentAsString(StandardCharsets.UTF_8), mapper.getTypeFactory().constructType(UsersNewsDto.class))
    value
  }

  List<NotificationDto> getAllNotificationsWithStatus(NotificationStatusDto notificationStatus) {
    ResultActions perform = mvc.perform(MockMvcRequestBuilders.get("/api/notification/all/{status}", notificationStatus))
    checkResponse(perform.andReturn().response)
    List<NotificationDto> value = mapper.readValue(perform.andReturn().response.getContentAsString(StandardCharsets.UTF_8),
        mapper.getTypeFactory().constructCollectionType(List.class, NotificationDto.class))
    value
  }

  NotificationDto createNotification(CreateNotificationDto createNotification) {
    ResultActions perform = mvc.perform(MockMvcRequestBuilders.post("/api/notification/reminder")
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(createNotification))
    )
    checkResponse(perform.andReturn().response)
    NotificationDto value = mapper.readValue(perform.andReturn().response.getContentAsString(StandardCharsets.UTF_8), mapper.getTypeFactory().constructType(NotificationDto.class))
    value
  }

  void unrollFromNews(UUID usersNewsId) {
    ResultActions perform = mvc.perform(MockMvcRequestBuilders.delete("/api/notification/unroll/{usersNewsId}", usersNewsId))
    checkResponse(perform.andReturn().response)
  }

  void executeScheduler() {
    ResultActions perform = mvc.perform(MockMvcRequestBuilders.get("/api/notification/execute"))
    checkResponse(perform.andReturn().response)
  }

  void cleanup() {
    ResultActions perform = mvc.perform(MockMvcRequestBuilders.get("/api/notification/cleanup"))
    checkResponse(perform.andReturn().response)
  }
}
