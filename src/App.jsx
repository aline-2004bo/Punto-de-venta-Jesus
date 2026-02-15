import { useEffect, useState } from "react";
import "./App.css";

function App() {
  const [productos, setProductos] = useState([]);

  const obtenerProductos = async () => {
    const response = await fetch("http://localhost:8080/api/productos");
    const data = await response.json();
    setProductos(data);
  };

  useEffect(() => {
    obtenerProductos();

    const intervalo = setInterval(() => {
      obtenerProductos();
    }, 3000);

    return () => clearInterval(intervalo);
  }, []);

  return (
    <div className="container">
      <h1 className="title">Panel Administrativo - Tienda Tecnológica</h1>

      <div className="card">
        <table>
          <thead>
            <tr>
              <th>ID</th>
              <th>Nombre</th>
              <th>Precio</th>
              <th>Stock</th>
            </tr>
          </thead>
          <tbody>
            {productos.map((producto) => (
              <tr key={producto.id}>
                <td>{producto.id}</td>
                <td>{producto.nombre}</td>
                <td>${producto.precio}</td>
                <td
                  className={
                    producto.stock <= 5 ? "stock-low" : "stock-ok"
                  }
                >
                  {producto.stock}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      <div className="footer">
        Actualización automática cada 3 segundos
      </div>
    </div>
  );
}

export default App;
