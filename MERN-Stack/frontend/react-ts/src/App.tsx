import { useState } from 'react'
import ProdutList from './components/productslist'
import './App.css'
import { BrowserRouter, Route , Routes } from 'react-router-dom'
import AppLayout from './components/AppLayout'

function App() {
 

  return (
    <>
    <BrowserRouter>
      <Routes>
        <Route element={<AppLayout />} >
          <Route path="/products" element={<ProdutList />} />
          <Route path="/about" element={<div>About Us</div>} />
          <Route path="/help" element={<div>Help</div>} />
          <Route path="/cart" element={<div>Cart</div>} />
        </Route>
    
      </Routes>
      
    </BrowserRouter>
    </>
  )
}

export default App
