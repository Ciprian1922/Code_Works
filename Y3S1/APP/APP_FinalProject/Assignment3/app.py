from flask import Flask, request, jsonify, send_from_directory
import os
from utils import get_directories, add_artifact, delete_directory, delete_artifact

app = Flask(__name__)
ARTIFACTS_DIR = 'directories'

#check if the artifacts folder exists
os.makedirs(ARTIFACTS_DIR, exist_ok=True)

@app.route('/artefacts/', methods=['GET'])
def list_directories():
    directories = get_directories(ARTIFACTS_DIR)
    return jsonify(directories)

@app.route('/artefacts/<directory>/', methods=['GET', 'POST', 'DELETE'])
def handle_directory(directory):
    dir_path = os.path.join(ARTIFACTS_DIR, directory)
    if request.method == 'GET':
        if not os.path.exists(dir_path):
            return jsonify({'error': 'Directory not found dude'}), 404
        return jsonify(os.listdir(dir_path))
    elif request.method == 'POST':
        file = request.files.get('file')
        if not file:
            return jsonify({'error': 'No file provided...'}), 400
        add_artifact(dir_path, file)
        return jsonify({'message': 'File added completely!'})
    elif request.method == 'DELETE':
        delete_directory(dir_path)
        return jsonify({'message': 'Directory deleted!'})

@app.route('/artefacts/<directory>/<artifact_id>', methods=['GET','DELETE','PUT'])
def handle_artifact(directory, artifact_id):
    file_path = os.path.join(ARTIFACTS_DIR, directory, artifact_id)
    if request.method == 'GET':
        if not os.path.exists(file_path):
            return jsonify({'error': 'File not found :/'}), 404
        return send_from_directory(os.path.join(ARTIFACTS_DIR, directory), artifact_id)
    elif request.method == 'DELETE':
        delete_artifact(file_path)
        return jsonify({'message': 'File deleted :D'})
    elif request.method == 'PUT':
        file = request.files.get('file')
        if not file:
            return jsonify({'error': 'No file provided...'}), 400
        add_artifact(os.path.join(ARTIFACTS_DIR, directory), file, replace=True)
        return jsonify({'message': 'File replaced completely!'})

if __name__ == '__main__':
    app.run(debug=True)
