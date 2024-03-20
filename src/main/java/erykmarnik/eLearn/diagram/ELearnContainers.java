package erykmarnik.eLearn.diagram;

import com.structurizr.model.Container;
import com.structurizr.model.SoftwareSystem;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
class ELearnContainers {
  Container eLearn;

  public ELearnContainers(SoftwareSystem eLearnSys) {
    this.eLearn = eLearnSys.addContainer("ELearn");
  }

  void createUsages(External external) {
    external.getGuest().uses(eLearn, "visits eLearn");
  }

  static ELearnContainers create(SoftwareSystem eLearnSys, External external) {
    ELearnContainers eLearnContainers = new ELearnContainers(eLearnSys);
    eLearnContainers.createUsages(external);
    return eLearnContainers;
  }
}
