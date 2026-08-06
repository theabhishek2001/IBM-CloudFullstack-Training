import { NavLink, Outlet } from "react-router-dom";

const AppLayout: React.FC = () => {
    return (
        <>
        <nav className="navbar navbar-expand-lg navbar-dark bg-dark px-3">
 

  <div className="navbar-nav">
    
    <NavLink
      className="nav-link"
      to="/products">
      Products
    </NavLink>
 
    <NavLink
            to="/about"
            className="nav-link">
            About
          </NavLink>

          <NavLink
            to="/help"
            className="nav-link"
          >
            Help
          </NavLink>

          <NavLink
            to="/cart"
            className="nav-link" 
          >
            View Cart
          </NavLink>

        </div>
      </nav>


<main className="container mt-5 mt-3">
 
      <Outlet />
 

 
</main>

<footer ></footer>
</>

    );
}

export default AppLayout;