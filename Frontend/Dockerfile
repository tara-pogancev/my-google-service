FROM node:14 as node
WORKDIR /app
COPY . .
COPY environment.ts.template  ./src/environments/environment.prod.ts
RUN npm install
RUN npm run build --prod

FROM nginx:alpine
COPY --from=node /app/dist/my-google-service /usr/share/nginx/html
COPY ./nginx.conf /etc/nginx/nginx.conf
