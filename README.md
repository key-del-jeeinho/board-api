## Board API
์๊ท๋ชจ ๐๊ฒ์ํ API ์๋๋ค.

์๊ตฌ์ฌํญ ๋ช์ธ์๋ [๋ธ์](https://raul-dakgg.notion.site/Board-API-21cbeb8eb94c4235bcd9c269aa27c4de)์ ์ฐธ๊ณ ํด์ฃผ์ธ์!

### Deploy to Docker-compose

docker์ [docker-compose](https://github.com/key-del-jeeinho/board-api/blob/master/docker-compose.yml)๊ฐ ์ค์น๋ ํ๊ฒฝ์์ ์ฌ์ฉํ์ค ์ ์์ต๋๋ค.

์ด ๊ฒฝ์ฐ, ๋ณ๋์ Databaseํ๊ฒฝ ์์ด๋, docker-compose๋ฅผ ํตํด API์ Database๋ฅผ ํจ๊ป ์คํํ  ์ ์์ต๋๋ค.
```bash
git clone https://github.com/key-del-jeeinho/board-api {DIR}
cd {DIR}
docker-compose up -d
```
**How to Customizing Configuration?**
 
 ํ๋ก์ ํธ root path์ ์๋ docker-compose.yml ์ ํตํด ์ฌ๋ฌ ๊ตฌ์ฑ์ ๋ณด๋ค์ ์ธํํ์ค ์ ์์ต๋๋ค.

| Key                   | Description                                | Default Value                       |
|-----------------------|--------------------------------------------|-------------------------------------|
| **MYSQL ๊ตฌ์ฑ์ ๋ณด**        ||
| `MYSQL_ROOT_PASSWORD` | MySQL root๊ณ์ ์ ๋น๋ฐ๋ฒํธ์๋๋ค.                     | `root`                              |
| `MYSQL_DATABASE`      | MySQL์ ์์ฑ๋  ๋ฐ์ดํฐ๋ฒ ์ด์ค ์ด๋ฆ์๋๋ค.                   | `board_api`                         |
| `MYSQL_ROOT_HOST`     | MySQL root๊ณ์ ์ ์ ๊ทผ ๊ฐ๋ฅํ ํธ์คํธ์๋๋ค.               | `%`                                 |
| **BOARD API ๊ตฌ์ฑ์ ๋ณด**    ||
| `MYSQL_USERNAME`      | ์๋ฒ์์ ์ฌ์ฉํ  MySQL Database์ ์ฌ์ฉ์ ์ด๋ฆ์๋๋ค.        | `root`                              |
| `MYSQL_PASSWORD`      | ์๋ฒ์์ ์ฌ์ฉํ  MySQL Database์ ์ฌ์ฉ์ ๋น๋ฐ๋ฒํธ์๋๋ค.      | `root`                              |
| `MYSQL_URL`           | MySQL ์๋ฒ์ URL์๋๋ค.                          | `jdbc:mysql://mysql:3306/board_api` |
| `REDIS_URL`           | Redis ์๋ฒ์ URL์๋๋ค.                          | `redis`                             |
| `REDIS_PORT`          | Redis ์๋ฒ์ Port์๋๋ค.                         | `6379`                              |
| `SPRING_PROFILE`      | Spring์์ ์ฌ์ฉํ  profile ์ ๋ณด์๋๋ค.                | `dev`                               |
