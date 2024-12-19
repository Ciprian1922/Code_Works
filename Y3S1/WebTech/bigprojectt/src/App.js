import logo from './logo.svg';
import './App.css';

import React, { useState } from "react";
import AVLTree from "./trees/AVLTree";
import TreeVisualizer from "./components/TreeVisualizer";

function App() {
  const [tree] = useState(new AVLTree());
  const [input, setInput] = useState("");

  const handleInsert = () => {
    tree.add(parseInt(input));
    setInput("");
  };

  return (
    <div>
      <h1>Interactive AVL Tree Visualization</h1>
      <input
        type="number"
        value={input}
        onChange={(e) => setInput(e.target.value)}
      />
      <button onClick={handleInsert}>Insert Node</button>
      <TreeVisualizer tree={tree} />
    </div>
  );
}

export default App;

