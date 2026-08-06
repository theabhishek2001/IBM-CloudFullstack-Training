import type React from "react";
interface RatingProps {
  rating: number;
  
}

const Ratings:React.FC<RatingProps> = ({ rating }:RatingProps) => {
  return (
    <div className="text-warning" >
      {[1, 2, 3, 4, 5].map((star) => {
        if (rating >= star) {
          return <i key={star} className="bi bi-star-fill"></i>;
        }

        if (rating >= star - 0.5) {
          return <i key={star} className="bi bi-star-half"></i>;
        }

        return <i key={star} className="bi bi-star"></i>;
      })}
    </div>
  );
};

export default Ratings;