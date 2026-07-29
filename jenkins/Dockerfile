# Use a lightweight official Node.js Alpine base image
FROM node:20-alpine

# Create and define the application working directory
WORKDIR /usr/src/app

# Copy dependency definition files
COPY package*.json ./

# Install only production dependencies for a smaller, secure image
RUN npm ci --only=production

# Copy the core application source code
COPY app.js ./

# Expose the port the Express application listens on
EXPOSE 3000

# Set environment variables
ENV NODE_ENV=production
ENV PORT=3000

# Use node to execute the application
CMD [ "node", "app.js" ]