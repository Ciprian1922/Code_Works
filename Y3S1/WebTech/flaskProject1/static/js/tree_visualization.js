function renderTree(treeData) {
    if (!treeData) return;  //exit if no data is passed

    //clear previous tree
    d3.select("#tree").selectAll("*").remove();

    //set up SVG canvas
    const svg = d3.select("#tree")
        .attr("width", 800)
        .attr("height", 600);

    const root = d3.hierarchy(treeData);  //D3 hierarchy conversion
    const treeLayout = d3.tree().size([600, 400]);  //tree layout with defined size
    treeLayout(root);

    //draw links (lines)
    svg.selectAll("line")
        .data(root.links())
        .enter()
        .append("line")
        .attr("x1", d => d.source.x + 100)
        .attr("y1", d => d.source.y + 50)
        .attr("x2", d => d.target.x + 100)
        .attr("y2", d => d.target.y + 50)
        .attr("stroke", "black");

    //draw nodes (circles)
    const nodes = svg.selectAll("g")
        .data(root.descendants())
        .enter()
        .append("g")
        .attr("transform", d => `translate(${d.x + 100}, ${d.y + 50})`);

    nodes.append("circle")  //append circles to represent nodes
        .attr("r", 20)
        .attr("fill", "lightblue")
        .attr("stroke", "black")
        .attr("stroke-width", 2);

    nodes.append("text")  //append text to show the node values
        .attr("dy", 5)  //adjust vertical alignment
        .attr("text-anchor", "middle")  //center the text in the circle
        .attr("font-size", "12px")
        .attr("fill", "black")
        .text(d => d.data.name);  //fetch the name for text
}


async function addNode() {
    const value = document.getElementById("nodeValue").value;
    const response = await fetch('/add_node', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ value: value })
    });
    const data = await response.json();
    renderTree(data.tree);
}

async function deleteNode() {
    const value = document.getElementById("nodeValue").value;
    const response = await fetch('/delete_node', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ value: value })
    });
    const data = await response.json();
    renderTree(data.tree);
}
