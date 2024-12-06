from enums import Priority, Status

class Task:
    def __init__(self, id, name, description, priority=Priority.LOW, status=Status.NOT_STARTED, user=None):
        self.id = id
        self.name = name
        self.description = description
        self.priority = priority
        self.status = status
        self.user = user

    def to_dict(self):
        return {
            "id": self.id,
            "name": self.name,
            "description": self.description,
            "priority": self.priority.name,
            "status": self.status.name,
        }

    @classmethod
    def from_dict(cls, data):
        raise NotImplementedError("This method should be implemented by subclasses.")

    def __str__(self):
        return f"{self.name} ({self.priority.name}): {self.status.name}"

    def __repr__(self):
        return self.__str__()

class DevTask(Task):
    def __init__(self, id, name, description, priority, status, user, language):
        super().__init__(id, name, description, priority, status, user)
        self.language = language

    def to_dict(self):
        task_dict = super().to_dict()
        task_dict.update({"language": self.language, "_type": "DevTask"})
        return task_dict

    @classmethod
    def from_dict(cls, data):
        return cls(
            data["id"],
            data["name"],
            data["description"],
            Priority[data["priority"]],
            Status[data["status"]],
            None,
            data["language"],
        )

class QATask(Task):
    def __init__(self, id, name, description, priority, status, user, test_type):
        super().__init__(id, name, description, priority, status, user)
        self.test_type = test_type

    def to_dict(self):
        task_dict = super().to_dict()
        task_dict.update({"test_type": self.test_type, "_type": "QATask"})
        return task_dict

    @classmethod
    def from_dict(cls, data):
        return cls(
            data["id"],
            data["name"],
            data["description"],
            Priority[data["priority"]],
            Status[data["status"]],
            None,
            data["test_type"],
        )

class DocTask(Task):
    def __init__(self, id, name, description, priority, status, user, document):
        super().__init__(id, name, description, priority, status, user)
        self.document = document

    def to_dict(self):
        task_dict = super().to_dict()
        task_dict.update({"document": self.document, "_type": "DocTask"})
        return task_dict

    @classmethod
    def from_dict(cls, data):
        return cls(
            data["id"],
            data["name"],
            data["description"],
            Priority[data["priority"]],
            Status[data["status"]],
            None,
            data["document"],
        )
