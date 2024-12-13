import json
from user import User
from project import Project
from task import DevTask, QATask, DocTask
from enums import Priority, Status, ProjectStatus
from sequencer import Sequencer

#in-memory storage for users
users = []

#JSON file to save and load data
DATA_FILE = "task_management_data.json"

#utility functions
def save_data():
    """Save all users, projects, and tasks to a JSON file."""
    with open(DATA_FILE, "w") as file:
        data = [user.to_dict() for user in users]
        json.dump(data, file, indent=4)

def load_data():
    """Load users, projects, and tasks from a JSON file."""
    global users
    try:
        with open(DATA_FILE, "r") as file:
            content = file.read().strip()  # Strip any extra whitespace
            if not content:  # Handle empty file
                print("No previous data found, starting fresh...")
                return
            data = json.loads(content)  # Parse the JSON data
            for user_data in data:
                user = User.from_dict(user_data)
                users.append(user)
    except FileNotFoundError:
        print("No data file found, starting fresh...")
    except json.JSONDecodeError:
        print("Data file is corrupted, starting fresh...")


#menu Functionality
def add_user():
    name = input("Enter first name: ")
    surname = input("Enter surname: ")
    email = input("Enter email: ")
    user = User(Sequencer.generate_sequence(), name, surname, email)
    users.append(user)
    print(f"User {name} {surname} created.")
    save_data()

def list_users():
    if users:
        print("Users:")
        for user in users:
            print(user)
    else:
        print("No users found.")

def remove_user():
    list_users()
    user_id = int(input("Enter User ID to remove: "))
    user = next((u for u in users if u.id == user_id), None)
    if user:
        users.remove(user)
        print(f"User {user.name} {user.surname} removed.")
        save_data()
    else:
        print("User not found.")

def add_project():
    list_users()
    user_id = int(input("Enter User ID to assign the project: "))
    user = next((u for u in users if u.id == user_id), None)
    if user:
        name = input("Enter project name: ")
        description = input("Enter project description: ")
        deadline = input("Enter deadline (YYYY-MM-DD, leave blank for default): ") or None
        project = user.create_project(name, description, deadline)
        print(f"Project {name} created and assigned to {user.name}.")
        save_data()
    else:
        print("User not found.")

def list_projects():
    for user in users:
        print(f"{user.name} {user.surname}'s Projects:")
        for project in user.projects:
            print(f"  {project}")

def remove_project():
    list_projects()
    project_id = int(input("Enter Project ID to remove: "))
    for user in users:
        project = next((p for p in user.projects if p.id == project_id), None)
        if project:
            user.remove_project(project)
            print(f"Project {project.name} removed.")
            save_data()
            return
    print("Project not found.")

def add_task():
    list_projects()
    project_id = int(input("Enter Project ID to add a task: "))
    for user in users:
        project = next((p for p in user.projects if p.id == project_id), None)
        if project:
            name = input("Enter task name: ")
            description = input("Enter task description: ")
            priority = Priority[input("Enter priority (LOW, MEDIUM, HIGH, CRITICAL): ").upper()]
            status = Status.NOT_STARTED
            task_type = input("Enter task type (DevTask, QATask, DocTask): ").strip()
            if task_type == "DevTask":
                language = input("Enter programming language: ")
                task = DevTask(Sequencer.generate_sequence(), name, description, priority, status, None, language)
            elif task_type == "QATask":
                test_type = input("Enter test type: ")
                task = QATask(Sequencer.generate_sequence(), name, description, priority, status, None, test_type)
            elif task_type == "DocTask":
                document = input("Enter document type: ")
                task = DocTask(Sequencer.generate_sequence(), name, description, priority, status, None, document)
            else:
                print("Invalid task type!")
                return
            project.add_task(task)
            print(f"Task {name} added to project {project.name}.")
            save_data()
            return
    print("Project not found.")

def list_tasks():
    list_projects()
    project_id = int(input("Enter Project ID to list tasks: "))
    for user in users:
        project = next((p for p in user.projects if p.id == project_id), None)
        if project:
            print(f"Tasks in {project.name}:")
            for task in project.tasks:
                print(task)
            return
    print("Project not found.")

def remove_task():
    list_tasks()
    task_id = int(input("Enter Task ID to remove: "))
    for user in users:
        for project in user.projects:
            task = next((t for t in project.tasks if t.id == task_id), None)
            if task:
                project.remove_task(task)
                print(f"Task {task.name} removed.")
                save_data()
                return
    print("Task not found.")

def edit_task():
    list_tasks()
    task_id = int(input("Enter Task ID to edit: "))
    for user in users:
        for project in user.projects:
            task = next((t for t in project.tasks if t.id == task_id), None)
            if task:
                task.name = input(f"Enter new name (current: {task.name}): ") or task.name
                task.description = input(f"Enter new description (current: {task.description}): ") or task.description
                task.priority = Priority[input(f"Enter new priority (current: {task.priority.name}): ").upper()] or task.priority
                print(f"Task {task.name} updated.")
                save_data()
                return
    print("Task not found.")

def edit_user():
    list_users()
    user_id = int(input("Enter User ID to edit: "))
    user = next((u for u in users if u.id == user_id), None)
    if user:
        user.name = input(f"Enter new name (current: {user.name}): ") or user.name
        user.surname = input(f"Enter new surname (current: {user.surname}): ") or user.surname
        user.email = input(f"Enter new email (current: {user.email}): ") or user.email
        print(f"User {user.name} updated.")
        save_data()
    else:
        print("User not found.")

def edit_project():
    list_projects()
    project_id = int(input("Enter Project ID to edit: "))
    for user in users:
        project = next((p for p in user.projects if p.id == project_id), None)
        if project:
            project.name = input(f"Enter new name (current: {project.name}): ") or project.name
            project.description = input(f"Enter new description (current: {project.description}): ") or project.description
            project.deadline = input(f"Enter new deadline (current: {project.deadline}): ") or project.deadline
            print(f"Project {project.name} updated.")
            save_data()
            return
    print("Project not found.")

def update_project_status():
    list_projects()
    project_id = int(input("Enter Project ID to update status: "))
    for user in users:
        project = next((p for p in user.projects if p.id == project_id), None)
        if project:
            print("Current Status:", project.get_status().name)
            new_status = input("Enter new status (NOT_STARTED, IN_PROGRESS, COMPLETED): ").upper()
            try:
                project.set_status(ProjectStatus[new_status])
                print(f"Project status updated to {new_status}.")
                save_data()
            except KeyError:
                print("Invalid status entered.")
            return
    print("Project not found.")


# Menu Options
menus = {
    1: add_user,
    2: add_project,
    3: list_users,
    4: list_projects,
    5: remove_user,
    6: remove_project,
    7: add_task,
    8: edit_task,
    9: remove_task,
    10: list_tasks,
    11: edit_user,
    12: edit_project,
    13: update_project_status,  # New option to update project status
    14: lambda: (save_data(), print("Saving data and exiting..."), exit(0)),  # Save and Exit option
}



def main():
    load_data()
    while True:
        print("\nMenu:")
        for key, value in menus.items():
            # If the option is the lambda for exit, manually set the name to "Save and Exit"
            if key == 14:
                print(f"{key}. Save and Exit")
            else:
                print(f"{key}. {value.__name__.replace('_', ' ').title()}")

        try:
            choice = int(input("Enter your choice: "))
            func = menus.get(choice, lambda: print("Invalid option!"))
            func()
        except ValueError:
            print("Invalid input. Please enter a number corresponding to the menu option.")
        except Exception as e:
            print(f"An unexpected error occurred: {e}")



if __name__ == "__main__":
    main()
