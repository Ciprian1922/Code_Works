import os
from werkzeug.utils import secure_filename

def get_directories(base_dir):
    return [d for d in os.listdir(base_dir) if os.path.isdir(os.path.join(base_dir, d))]

def add_artifact(directory, file, replace=False):
    os.makedirs(directory, exist_ok=True)
    file_path = os.path.join(directory, secure_filename(file.filename))
    if os.path.exists(file_path) and not replace:
        raise FileExistsError('File already exists there, use PUT to replace!')
    file.save(file_path)

def delete_directory(directory):
    if os.path.exists(directory):
        for file in os.listdir(directory):
            os.remove(os.path.join(directory, file))
        os.rmdir(directory)

def delete_artifact(file_path):
    if os.path.exists(file_path):
        os.remove(file_path)
