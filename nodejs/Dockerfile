FROM node:22-bookworm

WORKDIR /app

COPY package*.json ./
COPY app.js ./app.js

RUN npm install

ENTRYPOINT ["node", "app.js"]

EXPOSE 8000