package erykmarnik.eLearn.diagram;

import com.structurizr.Workspace;
import com.structurizr.model.Component;
import com.structurizr.model.Container;
import com.structurizr.view.ComponentView;
import com.structurizr.view.PaperSize;
import com.structurizr.view.ViewSet;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import java.util.function.Function;

@Getter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
class ELearnComponents {
  ModulesInteractor modulesInteractor = new ModulesInteractor();
  Component questionController;
  Component quizController;
  Component quizAssignationController;
  Component authController;
  Component userController;
  Component userAssignationController;
  Component questionFacade;
  Component quizFacade;
  Component quizAssignationFacade;
  Component userFacade;
  Component userAssignationFacade;
  Component userResultFacade;
  Component userResultController;

  ELearnComponents(Container eLearn) {
    questionController = eLearn.addComponent("Question controller");
    quizController = eLearn.addComponent("Quiz controller");
    quizAssignationController = eLearn.addComponent("Quiz assignation controller");
    authController = eLearn.addComponent("Auth controller");
    userController = eLearn.addComponent("User controller");
    userAssignationController = eLearn.addComponent("User assignation controller");
    questionFacade = eLearn.addComponent("Question facade");
    quizFacade = eLearn.addComponent("Quiz facade");
    quizAssignationFacade = eLearn.addComponent("Quiz assignation facade");
    userFacade = eLearn.addComponent("User facade");
    userAssignationFacade = eLearn.addComponent("User assignation facade");
    userResultFacade = eLearn.addComponent("User result facade");
    userResultController = eLearn.addComponent("User result controller");
  }

  void createUsages(External external) {
    modulesInteractor.createInteractors(external);
  }

  private class ModulesInteractor {
    void createInteractors(External external) {
      external.getGuest().uses(authController, "makes api call to login");
      authController.uses(userFacade, "asks for user");
      userFacade.uses(external.getDatabase(), "check if user exists");
      external.getStudent().uses(quizController, "makes api call to get quiz basic info");
      quizController.uses(quizFacade, "uses to get quiz basic info");
      quizFacade.uses(external.getDatabase(), "get quiz info");
      external.getAdmin().uses(quizController, "makes api call to create quiz");
      quizController.uses(quizFacade, "uses to create new quiz");
      quizFacade.uses(external.getDatabase(), "inserts new quiz");
      external.getAdmin().uses(quizController, "makes api call to change quiz name");
      quizController.uses(quizFacade, "uses to change quiz name");
      quizFacade.uses(external.getDatabase(), "updates quiz name");
      external.getAdmin().uses(quizController, "makes api call to change quiz difficulty");
      quizController.uses(quizFacade, "uses to change quiz difficulty");
      quizFacade.uses(external.getDatabase(), "changes quiz difficulty");
      external.getAdmin().uses(questionController, "makes api call to create question");
      questionController.uses(questionFacade, "uses to create question");
      questionFacade.uses(external.getDatabase(), "inserts new question data");
      external.getAdmin().uses(questionController, "makes api call to edit question");
      questionController.uses(questionFacade, "uses to edit question");
      questionFacade.uses(external.getDatabase(), "updates question");
      external.getStudent().uses(questionController, "makes api call to get question");
      questionController.uses(questionFacade, "uses to get question");
      questionFacade.uses(external.getDatabase(), "gets question data");
      external.getAdmin().uses(quizAssignationController, "makes api call to assign question to quiz");
      quizAssignationFacade.uses(quizFacade, "uses to check if quiz exists");
      quizAssignationFacade.uses(questionFacade, "uses to check if question exists");
      quizAssignationController.uses(quizAssignationFacade, "uses to assign question to quiz");
      quizAssignationFacade.uses(external.getDatabase(), "inserts new assignation data");
      external.getAdmin().uses(quizAssignationController, "makes api call to delete assignation");
      quizAssignationController.uses(quizAssignationFacade, "uses to delete assignation");
      quizAssignationFacade.uses(external.getDatabase(), "deletes quiz assignation");
      external.getStudent().uses(quizAssignationController, "makes api call to get all questions assigned to quiz");
      quizAssignationController.uses(quizAssignationFacade, "uses to get all questions assigned to quiz");
      quizAssignationFacade.uses(external.getDatabase(), "gets all questions assigned to quiz");
      external.getAdmin().uses(quizAssignationController, "makes api call to get quiz assignation");
      quizAssignationController.uses(quizAssignationFacade, "uses to get quiz assignation");
      quizAssignationFacade.uses(external.getDatabase(), "gets question assigned to quiz");
      external.getStudent().uses(userAssignationController, "makes api call to assign to learning object");
      userAssignationController.uses(userAssignationFacade, "uses to create assignment to learning object");
      userAssignationFacade.uses(quizFacade, "uses to check if quiz exists");
      userAssignationFacade.uses(userFacade, "uses to check if user exists");
      userAssignationFacade.uses(external.getDatabase(), "inserts new assignation");
      external.getStudent().uses(userAssignationController, "makes api call to update assignation status");
      userAssignationController.uses(userAssignationFacade, "uses to update assignation status");
      userAssignationFacade.uses(external.getDatabase(), "updates assignation status");
      external.getStudent().uses(userController, "makes api call to create new student/admin account");
      userController.uses(userFacade, "uses to create new user with student/admin role");
      userFacade.uses(external.getDatabase(), "inserts new user");
      userResultFacade.uses(userAssignationFacade, "creates or update user result when user is assigned to learning object or make some progress");
      userResultFacade.uses(external.getDatabase(), "inserts or update user result data");
      external.getStudent().uses(userResultController, "makes api call to save result from learning object");
      userResultController.uses(userResultFacade, "updates user result for given learning object");
      userResultFacade.uses(external.getDatabase(), "updates user result data");
    }
  }

  static void create(Workspace workspace, ELearnContainers eLearnContainers, External external, ELearnComponents eLearnComponents) {
    eLearnComponents.createUsages(external);
    Function<ViewSet, ComponentView> componentViewCreator = view ->
        view.createComponentView(eLearnContainers.getELearn(), "eLearn components", "eLearn components");
    ViewSet viewSet = workspace.getViews();
    ComponentView contextView = componentViewCreator.apply(viewSet);
    contextView.setPaperSize(PaperSize.A2_Landscape);
    contextView.add(eLearnComponents.getQuestionController());
    contextView.add(eLearnComponents.getQuizController());
    contextView.add(eLearnComponents.getQuizAssignationController());
    contextView.add(eLearnComponents.getAuthController());
    contextView.add(eLearnComponents.getUserController());
    contextView.add(eLearnComponents.getUserAssignationController());
    contextView.add(eLearnComponents.getQuestionFacade());
    contextView.add(eLearnComponents.getQuizFacade());
    contextView.add(eLearnComponents.getQuizAssignationFacade());
    contextView.add(eLearnComponents.getUserFacade());
    contextView.add(eLearnComponents.getUserAssignationFacade());
    contextView.add(eLearnComponents.getUserResultController());
    contextView.add(eLearnComponents.getUserResultFacade());
    contextView.add(external.getDatabase());
    contextView.add(external.getGuest());
    contextView.add(external.getStudent());
    contextView.add(external.getAdmin());
  }
}
