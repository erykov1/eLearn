package erykmarnik.eLearn.diagram;

import com.structurizr.model.Model;
import com.structurizr.model.Person;
import com.structurizr.model.SoftwareSystem;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
class External {
  Person guest;
  Person admin;
  Person student;
  SoftwareSystem database;
  SoftwareSystem mail;

  External(Model model) {
    guest = model.addPerson("guest", "user not logged");
    admin = model.addPerson("admin", "eLearn admin");
    student = model.addPerson("student", "user with basic role");
    database = model.addSoftwareSystem("database", "PostgreSQL database");
    mail = model.addSoftwareSystem("mail", "mail service");
  }

  SoftwareSystem createUsages(SoftwareSystem eLearn) {
    guest.uses(eLearn, "creates account, login to system");
    admin.uses(eLearn, "creates quiz, question, assigns questions to learning objects, deletes question");
    student.uses(eLearn, "assigns to quiz, solves quiz");
    database.uses(eLearn, "data operation");
    mail.uses(eLearn, "sends emails");
    return eLearn;
  }
}
