FROM node:18.17.1-alpine AS build
WORKDIR /app
COPY package*.json ./

RUN npm install jwt-decode
RUN npm install --force
RUN npx ngcc --properties es2023 browser module main --first-only --create-ivy-entry-points
COPY . .
RUN npm run build
FROM nginx:stable
COPY --from=build /app/dist/sgrh-app/browser/ /usr/share/nginx/html
EXPOSE 80
