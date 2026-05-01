### Swagger
http://localhost:8080/swagger-ui/index.html

# Отчет по дампам потоков

## Информация о сборе данных
- **Дата снятия**: 1 мая 2026 г.
- **PID процесса**: 9980
- **Команда снятия дампа**: `jstack -l 9980 > thread_dump.txt`

## Топ-3 потока по нагрузке CPU

| Название потока                                             | Время жизни потока (elapsed, ms) | Время работы потока (cpu_ms) | Процент нагрузки |
|-------------------------------------------------------------|----------------------------------|------------------------------|------------------|
| "Reference Handler" #9 [19468] daemon prio=10 os_prio=2     | 69620ms                          | 15.62ms                      | 0.0224%          |
| "C2 CompilerThread0" #15 [2076] daemon prio=9 os_prio=2     | 69620ms                          | 6031.25ms                    | 8.66%            |
| "http-nio-8080-exec-9" #50 [16052] daemon prio=5 os_prio=0  | 59100ms                          | 15.62ms                      | 0.0264%          |

## Расчет процента нагрузки

cpu_ms = 15.62 ms
elapsed_ms = 69620 ms
Процент = (15.62 / 69620) × 100% = 0.0224%

cpu_ms = 6031.25 ms
elapsed_ms = 69620 ms
Процент = (6031.25 / 69620) × 100% = 8.66%

cpu_ms = 15.62 ms
elapsed_ms = 59100 ms
Процент = (15.62 / 59100) × 100% = 0.0264%

### Формула:
Процент нагрузки = (cpu_ms / elapsed_ms) × 100%

### Prometheus и Grafana
![img.png](images%2Fimg.png)

![img_1.png](images%2Fimg_1.png)

![img_2.png](images%2Fimg_2.png)

![img_3.png](images%2Fimg_3.png)

![img_4.png](images%2Fimg_4.png)

## Пользование
Actuator: http://localhost:8080/actuator
Prometheus: http://localhost:9090
Grafana: http://localhost:3000 (admin/admin)

- Поднять мониторинг: docker-compose up -d
- Создать релиз:
git tag v1.0.0
git push origin v1.0.0

