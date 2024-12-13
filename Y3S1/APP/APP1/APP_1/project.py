from task import Task, DevTask, QATask, DocTask
from enums import ProjectStatus

class Project:
    def __init__(self, id, name, description, deadline=None, status=ProjectStatus.NOT_STARTED):
        self.id = id
        self.name = name
        self.description = description
        self.tasks = set()
        self.deadline = deadline
        self.status = status

    def add_task(self, task):
        if isinstance(task, Task):
            self.tasks.add(task)
        else:
            raise ValueError("Invalid task type")

    def remove_task(self, task):
        self.tasks.discard(task)

    def set_status(self, status):
        if isinstance(status, ProjectStatus):
            self.status = status
        else:
            raise ValueError("Invalid status type")

    def get_status(self):
        return self.status

    def to_dict(self):
        return {
            "id": self.id,
            "name": self.name,
            "description": self.description,
            "tasks": [task.to_dict() for task in self.tasks],
            "deadline": self.deadline,
            "status": self.status.name,
        }

    @classmethod
    def from_dict(cls, data):
        project = cls(
            data["id"],
            data["name"],
            data["description"],
            data.get("deadline"),
            ProjectStatus[data["status"]],
        )
        #handle deserialization of tasks
        task_classes = {"DevTask": DevTask, "QATask": QATask, "DocTask": DocTask}
        project.tasks = {
            task_classes[task["_type"]].from_dict(task) for task in data["tasks"]
        }
        return project

    def __str__(self):
        return f"Project(ID: {self.id}, Name: {self.name}, Status: {self.status.name}, Deadline: {self.deadline})"

    def __repr__(self):
        return self.__str__()