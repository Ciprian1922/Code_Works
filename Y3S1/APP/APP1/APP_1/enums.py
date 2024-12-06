from enum import Enum

class Priority(Enum):
    LOW = 1
    MEDIUM = 2
    HIGH = 3
    CRITICAL = 4

class Status(Enum):
    NOT_STARTED = 1
    IN_PROGRESS = 2
    COMPLETED = 3
