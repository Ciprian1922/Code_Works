from flask import Flask, render_template
import xml.etree.ElementTree as ET

app = Flask(__name__)

class Building:
    def __init__(self, category, year, profile, owner, space, children, teachers):
        self.category = category
        self.year = year
        self.profile = profile
        self.owner = owner
        self.space = space
        self.children = children
        self.teachers = teachers

def parse_xml(file):
    tree = ET.parse(file)
    root = tree.getroot()
    buildings = []
    for building in root.findall('building'):
        bldg = Building(
            category=building.get('category'),
            year=int(building.get('year')),
            profile=building.get('profile'),
            owner=building.get('owner'),
            space=int(building.find('space').text),
            children=int(building.find('children').text),
            teachers=int(building.find('teachers').text)
        )
        buildings.append(bldg)
    return buildings

@app.route('/')
def display_content():
    buildings = parse_xml('Laboratory3.xml')
    return render_template('Laboratory3.html', buildings=buildings)

if __name__ == '_main_':
    app.run(debug=True)