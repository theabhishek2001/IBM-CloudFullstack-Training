import { useEffect, useState } from "react";

interface Product {
  _id: string;
  name: string;
  productCode: string;
  productAvailable: boolean;
  price: number;
  rating: number;
  imageUrl: string;
}

const ProductsList = () => {
  const [showImage, setShowImage] = useState(true);
  const [products, setProducts] = useState<Product[]>([]);
  const [filteredProducts, setFilteredProducts] = useState<Product[]>([]);

  useEffect(() => {
    fetch("http://localhost:5000/api/products")
      .then((response) => {
        if (!response.ok) {
          throw new Error("Failed to fetch products");
        }
        return response.json();
      })
      .then((data: Product[]) => {
        setProducts(data);
        setFilteredProducts(data);
      })
      .catch((error) => {
        console.error("Error:", error);
      });
  }, []);

  const handleFilterChange = (
    event: React.ChangeEvent<HTMLInputElement>
  ) => {
    const value = event.target.value.toLowerCase();

    const filtered = products.filter((product) =>
      product.name.toLowerCase().includes(value)
    );

    setFilteredProducts(filtered);
  };

  return (
    <div className="container-fluid py-4">
      <div className="card border-0 shadow-lg rounded-4">
        {/* Header */}
        <div className="card-header bg-white border-0 py-3">
          <div className="d-flex flex-column flex-md-row justify-content-between align-items-md-center gap-3">
            <h3 className="fw-bold mb-0">
              Products
              <span className="badge bg-primary ms-2">
                {filteredProducts.length}
              </span>
            </h3>

            <button
              className="btn btn-primary"
              onClick={() => setShowImage((prev) => !prev)}
            >
              {showImage ? "Hide Images" : "Show Images"}
            </button>
          </div>
        </div>

        {/* Body */}
        <div className="card-body">
          {/* Search */}
          <div className="row mb-4">
            <div className="col-12 col-md-6 col-lg-4">
              <input
                type="text"
                className="form-control"
                placeholder="🔍 Search Product..."
                onChange={handleFilterChange}
              />
            </div>
          </div>

          {/* Table */}
          <div
            className="table-responsive"
            style={{
              maxHeight: "70vh",
              overflowY: "auto",
            }}
          >
            <table className="table table-hover align-middle mb-0">
              <thead
                className="table-dark"
                style={{
                  position: "sticky",
                  top: 0,
                  zIndex: 2,
                }}
              >
                <tr>
                  {showImage && <th style={{ width: "110px" }}>Image</th>}
                  <th>Name</th>
                  <th>Code</th>
                  <th>Available</th>
                  <th className="text-end">Price</th>
                  <th className="text-center">Rating</th>
                </tr>
              </thead>

              <tbody>
                {filteredProducts.length > 0 ? (
                  filteredProducts.map((product) => (
                    <tr key={product._id}>
                      {showImage && (
                        <td>
                          <img
                            src={product.imageUrl}
                            alt={product.name}
                            className="rounded shadow-sm"
                            style={{
                              width: "70px",
                              height: "70px",
                              objectFit: "cover",
                            }}
                          />
                        </td>
                      )}

                      <td className="fw-semibold">{product.name}</td>

                      <td>
                        <span className="badge bg-secondary">
                          {product.productCode}
                        </span>
                      </td>

                      <td>
                        {product.productAvailable ? "Yes" : "No"}
                      </td>

                      <td className="text-end text-success fw-bold">
                        ${product.price.toFixed(2)}
                      </td>

                      <td className="text-center">
                        <span className="badge bg-warning text-dark">
                          ⭐ {product.rating.toFixed(1)}
                        </span>
                      </td>
                    </tr>
                  ))
                ) : (
                  <tr>
                    <td
                      colSpan={showImage ? 6 : 5}
                      className="text-center py-4"
                    >
                      No products found.
                    </td>
                  </tr>
                )}
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ProductsList;