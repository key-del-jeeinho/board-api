## Board API
소규모 📜게시판 API 입니다.

요구사항 명세서는 [노션](https://raul-dakgg.notion.site/Board-API-21cbeb8eb94c4235bcd9c269aa27c4de)을 참고해주세요!

### Deploy to Docker-compose

docker와 [docker-compose](https://github.com/key-del-jeeinho/board-api/blob/master/docker-compose.yml)가 설치된 환경에서 사용하실 수 있습니다.

이 경우, 별도의 Database환경 없이도, docker-compose를 통해 API와 Database를 함께 실행할 수 있습니다.
```bash
git clone https://github.com/key-del-jeeinho/board-api {DIR}
cd {DIR}
docker-compose up -d
```
**How to Customizing Configuration?**
 
 프로젝트 root path에 있는 docker-compose.yml 을 통해 여러 구성정보들을 세팅하실 수 있습니다.

| Key                   | Description                                | Default Value                       |
|-----------------------|--------------------------------------------|-------------------------------------|
| **MYSQL 구성정보**        ||
| `MYSQL_ROOT_PASSWORD` | MySQL root계정의 비밀번호입니다.                     | `root`                              |
| `MYSQL_DATABASE`      | MySQL에 생성될 데이터베이스 이름입니다.                   | `board_api`                         |
| `MYSQL_ROOT_HOST`     | MySQL root계정의 접근 가능한 호스트입니다.               | `%`                                 |
| **BOARD API 구성정보**    ||
| `MYSQL_USERNAME`      | 서버에서 사용할 MySQL Database의 사용자 이름입니다.        | `root`                              |
| `MYSQL_PASSWORD`      | 서버에서 사용할 MySQL Database의 사용자 비밀번호입니다.      | `root`                              |
| `MYSQL_URL`           | MySQL 서버의 URL입니다.                          | `jdbc:mysql://mysql:3306/board_api` |
| `REDIS_URL`           | Redis 서버의 URL입니다.                          | `redis`                             |
| `REDIS_PORT`          | Redis 서버의 Port입니다.                         | `6379`                              |
| `SPRING_PROFILE`      | Spring에서 사용할 profile 정보입니다.                | `dev`                               |
