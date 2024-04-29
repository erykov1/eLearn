package erykmarnik.eLearn.userresult.acceptance

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import erykmarnik.eLearn.integration.ELearnApi
import erykmarnik.eLearn.userresult.dto.PageInfoDto
import erykmarnik.eLearn.userresult.dto.ResultProgressChangedDto
import erykmarnik.eLearn.userresult.dto.UserResultDto
import erykmarnik.eLearn.userresult.dto.UserResultVisibilityTypeDto
import org.apache.tomcat.util.json.JSONParser
import org.json.JSONObject
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders

import java.nio.charset.StandardCharsets

class UserResultApiFacade extends ELearnApi {
  private final MockMvc mvc
  private final ObjectMapper mapper

  UserResultApiFacade(MockMvc mvc, ObjectMapper mapper) {
    this.mvc = mvc
    this.mapper = mapper
  }

  UserResultDto saveUserResultProgressChanged(UUID id, ResultProgressChangedDto resultProgressChanged) {
    ResultActions perform = mvc.perform(MockMvcRequestBuilders.put("/api/userResult/{id}", id)
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(resultProgressChanged))
    )
    checkResponse(perform.andReturn().response)
    UserResultDto value = mapper.readValue(perform.andReturn().response.getContentAsString(StandardCharsets.UTF_8),
        mapper.getTypeFactory().constructType(UserResultDto.class))
    value
  }

  List<UserResultDto> getAllUserResults() {
    ResultActions perform = mvc.perform(MockMvcRequestBuilders.get("/api/userResult/all")
        .contentType(MediaType.APPLICATION_JSON)
    )
    checkResponse(perform.andReturn().response)
    List<UserResultDto> value = mapper.readValue(perform.andReturn().response.getContentAsString(StandardCharsets.UTF_8),
        mapper.getTypeFactory().constructCollectionType(List.class, UserResultDto.class))
    value
  }

  List<UserResultDto> getPublicUserResults(PageInfoDto pageable) {
    ResultActions perform = mvc.perform(MockMvcRequestBuilders.get("/api/userResult/public")
        .contentType(MediaType.APPLICATION_JSON)
        .queryParam("pageSize", String.valueOf(pageable.pageSize))
        .queryParam("pageNumber", String.valueOf(pageable.pageNumber))
    )
    checkResponse(perform.andReturn().response)
    List<UserResultDto> value = parsePageToCollection(perform, new TypeReference<List<UserResultDto>>() {})
    value
  }

  UserResultDto changeUserResultVisibility(UUID id, UserResultVisibilityTypeDto userResultVisibilityType) {
    ResultActions perform = mvc.perform(MockMvcRequestBuilders.put("/api/userResult/visibility/{id}", id)
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(userResultVisibilityType))
    )
    checkResponse(perform.andReturn().response)
    UserResultDto value = mapper.readValue(perform.andReturn().response.getContentAsString(StandardCharsets.UTF_8),
        mapper.getTypeFactory().constructType(UserResultDto.class))
    value
  }

  private Collection<UserResultDto> parsePageToCollection(ResultActions perform, TypeReference typeReference) {
    JSONParser parser = new JSONParser(perform.andReturn().response.getContentAsString())
    JSONObject jsonObject = parser.parse() as JSONObject
    return (Collection) mapper.readValue(jsonObject.get("content").toString(), typeReference)
  }

  void cleanup() {
    ResultActions perform = mvc.perform(MockMvcRequestBuilders.get("/api/userResult/cleanup"))
    checkResponse(perform.andReturn().response)
  }
}
