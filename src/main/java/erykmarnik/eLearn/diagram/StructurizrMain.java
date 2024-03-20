package erykmarnik.eLearn.diagram;

import com.structurizr.Workspace;
import com.structurizr.api.StructurizrClientException;
import com.structurizr.model.Model;
import com.structurizr.model.SoftwareSystem;

class StructurizrMain {
  public static void main(String[] args) throws StructurizrClientException {
    Workspace workspace = createWorkspace();
    ViewCreator.setupStyles(workspace);
    ViewUploader.upload(workspace);
  }

  private static Workspace createWorkspace() {
    Workspace workspace = new Workspace("eLearn", "e-learning platform");
    Model model = workspace.getModel();
    SoftwareSystem eLearn = model.addSoftwareSystem("eLearn system");
    External external = ELearnContextDiagram.create(workspace, model, eLearn);
    ELearnContainers eLearnContainers = ELearnContainers.create(eLearn, external);
    ELearnComponents eLearnComponents = new ELearnComponents(eLearnContainers.getELearn());
    ELearnComponents.create(workspace, eLearnContainers, external, eLearnComponents);
    return workspace;
  }
}
