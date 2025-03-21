//binary Tree data
let binary_tree = {
    nodes: [],
    clear: function () {
        this.nodes = [];
    },
};

function renderTree(treeNodes) {
    const svg = d3.select("#tree");


    const nodes = svg.selectAll("g.node")//bind data to nodes
        .data(treeNodes, d => d.value);


    const enterNodes = nodes.enter()//enter,add new nodes
        .append("g")
        .attr("class", "node")
        .attr("transform", d => `translate(${d.x}, ${d.y})`);

    enterNodes.append("circle")
        .attr("r", 20)
        .style("fill", "#4CAF50");

    enterNodes.append("text")
        .attr("dy", -30)
        .attr("text-anchor", "middle")
        .style("font-size", "12px")
        .text(d => d.value);


    nodes
        .transition()       //update existing nodes
        .duration(750)
        .attr("transform", d => `translate(${d.x}, ${d.y})`);


    nodes.exit().remove();    //remove old nodes
}

// Add Node
async function addNode() {
    const value = document.getElementById("nodeValue").value;
    const response = await fetch('/binary_tree/add_node', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ value: value })
    });
    const data = await response.json();
    renderTree(data.tree);
}

// Delete Node
async function deleteNode() {
    const value = document.getElementById("nodeValue").value;
    const response = await fetch('/binary_tree/delete_node', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ value: value })
    });
    const data = await response.json();
    renderTree(data.tree);
}

// Save Tree
async function saveTree() {
    const name = prompt("Enter a name for the tree:");
    if (name) {
        const response = await fetch('/binary_tree/save_tree', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ name: name })
        });
        const data = await response.json();
        alert(data.message || "Tree saved successfully!");
    }
}

//load tree list
async function loadTrees() {
    const response = await fetch('/binary_tree/load_trees');
    const data = await response.json();
    const dropdown = document.getElementById("treeDropdown");
    dropdown.innerHTML = ""; // Clear options

    data.trees.forEach(tree => {
        const option = document.createElement("option");
        option.value = tree;
        option.text = tree;
        dropdown.add(option);
    });
}

//load tree by name
async function loadTree() {
    const name = document.getElementById("treeDropdown").value;
    const response = await fetch('/binary_tree/load_tree', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ name: name })
    });
    const data = await response.json();
    renderTree(data.tree);
}

//reset
function resetGraph() {
    d3.select("#tree").selectAll("*").remove();
    binary_tree.clear();
    alert("Graph and tree have been reset.");
}

//traverse BFS
async function traverseBFS() {
    const response = await fetch('/binary_tree/traverse_bfs', { method: 'POST' });
    const data = await response.json();
    if (data.nodes) {
        animateTraversal(data.nodes, 'bfs');
    }
}

//traverse DFS
async function traverseDFS() {
    const response = await fetch('/binary_tree/traverse_dfs', { method: 'POST' });
    const data = await response.json();
    if (data.nodes) {
        animateTraversal(data.nodes, 'dfs');
    }
}

//animation(try))
function animateTraversal(nodes, traversalType) {
    let index = 0;

    d3.selectAll("circle").style("fill", "#4CAF50");
    d3.select(`#${traversalType}-row`).remove();

    const rowContainer = d3.select("body").append("div")
        .attr("id", traversalType + "-row")
        .style("display", "flex")
        .style("justify-content", "center")
        .style("margin-top", "20px");

    const interval = setInterval(() => {
        if (index < nodes.length) {
            const nodeValue = nodes[index];

            d3.select(`#node-${nodeValue}`)
                .transition().duration(500)
                .style("fill", "green");

            rowContainer.append("div")
                .text(nodeValue)
                .style("margin", "0 10px")
                .style("font-size", "18px")
                .style("color", "green");

            index++;
        } else {
            clearInterval(interval);
        }
    }, 1000);
}
