import React, { useEffect, useRef } from "react";
import * as d3 from "d3";

const TreeVisualizer = ({ tree }) => {
  const svgRef = useRef();

  useEffect(() => {
    const svg = d3.select(svgRef.current);
    svg.selectAll("*").remove(); // Clear previous tree

    const renderTree = (node, x, y, depth) => {
      if (!node) return;

      const gap = 100 / (depth + 1); // Adjust gap dynamically
      const yOffset = 100;

      // Draw a node
      svg
        .append("circle")
        .attr("cx", x)
        .attr("cy", y)
        .attr("r", 20)
        .style("fill", "lightblue");

      svg
        .append("text")
        .attr("x", x)
        .attr("y", y)
        .attr("dy", ".35em")
        .attr("text-anchor", "middle")
        .text(node.key);

      // Recursive rendering
      renderTree(node.left, x - gap, y + yOffset, depth + 1);
      renderTree(node.right, x + gap, y + yOffset, depth + 1);
    };

    if (tree.root) {
      renderTree(tree.root, 300, 50, 0); // Start from the top
    }
  }, [tree]);

  return <svg ref={svgRef} width={600} height={400} />;
};

export default TreeVisualizer;
