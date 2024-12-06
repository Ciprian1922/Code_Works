from project import Project
from sequencer import Sequencer


class User:
    def __init__(self, id, name, surname, email):
        self.id = id
        self.name = name
        self.surname = surname
        self.email = email
        self.projects = set()

    def create_project(self, name, description, deadline=None):
        project = Project(Sequencer.generate_sequence(), name, description, deadline)
        self.projects.add(project)
        return project

    def add_project(self, project):
        if isinstance(project, Project):
            self.projects.add(project)
        else:
            raise ValueError("Invalid project type")

    def remove_project(self, project):
        self.projects.discard(project)

    def to_dict(self):
        return {
            "id": self.id,
            "name": self.name,
            "surname": self.surname,
            "email": self.email,
            "projects": [project.to_dict() for project in self.projects],
        }

    @classmethod
    def from_dict(cls, data):
        user = cls(data["id"], data["name"], data["surname"], data["email"])
        user.projects = {Project.from_dict(project) for project in data["projects"]}
        return user

    def __str__(self):
        return f"User: {self.name} {self.surname}"

    def __repr__(self):
        return self.__str__()
