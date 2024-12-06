import json
from user import User
from project import Project
from task import DevTask, QATask, DocTask

class DataManager:
    @staticmethod
    def save_users(users, file_path):
        with open(file_path, 'w') as file:
            json.dump([user.__dict__ for user in users], file)

    @staticmethod
    def load_users(file_path):
        with open(file_path, 'r') as file:
            users_data = json.load(file)
            users = []
            for user_data in users_data:
                user = User(**user_data)
                users.append(user)
            return users
