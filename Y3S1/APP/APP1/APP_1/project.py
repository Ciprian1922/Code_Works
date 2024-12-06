from task import DevTask, QATask, DocTask

class Project:
    def __init__(self, id, name, description, deadline=None):
        self.id = id
        self.name = name
        self.description = description
        self.tasks = set()
        self.deadline = deadline

    def add_task(self, task):
        if isinstance(task, (DevTask, QATask, DocTask)):
            self.tasks.add(task)
        else:
            raise ValueError("Invalid task type")

    def remove_task(self, task):
        self.tasks.discard(task)

    def to_dict(self):
        return {
            "id": self.id,
            "name": self.name,
            "description": self.description,
            "tasks": [task.to_dict() for task in self.tasks],
            "deadline": self.deadline,
        }

    @classmethod
    def from_dict(cls, data):
        project = cls(data["id"], data["name"], data["description"], data.get("deadline"))
        task_classes = {"DevTask": DevTask, "QATask": QATask, "DocTask": DocTask}
        project.tasks = {
            task_classes[task["_type"]].from_dict(task) for task in data["tasks"]
        }
        return project

    def __str__(self):
        return f"Project: {self.name}, Deadline: {self.deadline}"

    def __repr__(self):
        return self.__str__()
