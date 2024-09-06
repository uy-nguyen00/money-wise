FROM node:21

WORKDIR /app

COPY . /app

RUN npm install -g @angular/cli@17

RUN npm install

CMD ["ng", "serve", "--host", "0.0.0.0"]