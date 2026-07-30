import logo from './logo.svg';
import './App.css';
import { useState } from 'react';

function App() {

  let [count,setCount]  = useState(0);

  let course="React";
  // let count=0;

  // function counter(){
  //   setCount(count+1);
    
  // }

  // function deccounter(){
  //   setCount(count-1);
    
  // }


const op={
  increament:()=>setCount(count+1),
  decreament:()=>setCount(count-1)
}

function greet(){
  return "Hello React";
}

  function handleClick(){
    alert(`Cloud ${course}`)
  }
  return (

    <>
    
      <div className="App">
        
        
        <h1>Learning {course} </h1>
        <h3>Counter : {count}</h3>
        <button onClick={op.increament}>Increase counter</button>
        <button onClick={op.decreament}>Decrease counter</button>
        <button onClick={handleClick}>Submit</button>
      </div>

      <div>
        <h2 >Today we learn only basics of React</h2>
        <p style={{color:'blue',fontSize:"15px"}}>React: A JavaScript library for building interactive user interfaces.
          Component: A reusable piece of UI (e.g., Navbar, Footer).
          JSX: HTML-like syntax written inside JavaScript.
          Virtual DOM: A lightweight copy of the real DOM that updates only changed elements for better performance.
          Props: Read-only data passed from a parent component to a child component.
          State: Data managed within a component that can change over time.
          Hooks: Special functions that let functional components use React features.</p>
        
      </div>

    </>
  );
}

export default App;
